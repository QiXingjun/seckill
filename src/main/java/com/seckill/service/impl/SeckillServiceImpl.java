package com.seckill.service.impl;

import com.seckill.dao.SeckillDao;
import com.seckill.dao.SuccessKilledDao;
import com.seckill.dto.Exposer;
import com.seckill.dto.SeckillExecution;
import com.seckill.entity.Seckill;
import com.seckill.entity.SuccessKilled;
import com.seckill.enums.SeckillStateEnum;
import com.seckill.exception.RepeatKillException;
import com.seckill.exception.SeckillCloseException;
import com.seckill.exception.SeckillException;
import com.seckill.service.SeckillService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import java.util.Date;
import java.util.List;

/**
 * @Author XingJun Qi
 * @MyBlog www.qixingjun.tech
 * @Version 1.0.0
 * @Date 2017/2/12
 * @Description
 */

//除了@Service，还有@Component，@Dao，@Controller。其中，在不知道某个类到底是service，
// 还是dao或者controller的时候，可以使用@component
@Service
public class SeckillServiceImpl implements SeckillService {

    //日志对象
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    //除了@AutoWired，还有@Resource,@Inject
    //其中@AutoWired是Spring提供的，后面的两个是java提供的。
    @Autowired
    private SeckillDao seckillDao;

    @Autowired
    private SuccessKilledDao successKilledDao;
    // /MD5加密的盐值，值可以随机取,用户混淆MD5
    private final String salt = "fadsfadfeqwru9qewru9e0fpdlsaf;./.;!@#$%^&";

    public List<Seckill> getSeckillList() {
        return seckillDao.queryAll(0,15);
    }

    public Seckill getSeckillbyId(long seckillId) {
        return seckillDao.queryById(seckillId);
    }

    /**
     * 生成MD5
     * @param seckillId
     * @return
     */
    private String getMD5(long seckillId){
        String base = seckillId + "/" +salt;
        //DigestUtils是Spring提供的生成md5的工具类
        String md5 = DigestUtils.md5DigestAsHex(base.getBytes());
        return md5;
    }

    public Exposer exportSeckillUrl(long seckillId) {
        Seckill seckill = seckillDao.queryById(seckillId);
        if (seckill==null){
            return new Exposer(false,seckillId);
        }
        Date startTime = seckill.getStartTime();
        Date endTime = seckill.getEndTime();
        Date nowTime = new Date();
        if (startTime.getTime()>nowTime.getTime()||endTime.getTime()<nowTime.getTime()){
            return new Exposer(false,seckillId,nowTime.getTime(),startTime.getTime(),endTime.getTime());
        }
        //MD5加密是不可逆的
        String md5 = getMD5(seckillId);
        return new Exposer(true,md5,seckillId);
    }

    /**
     * 使用注解来进行事务声明的优点：
     * 1.明确标注事务方法的编程风格，可以使得开发团队达成一致
     * 2.可以保证事务方法的执行时间尽可能的短，不要穿插其他的网络操作，例如：Http请求，RPC等。
     * 如果确实需要，将相关操作剥离至方法之外。
     * 3.不是所有的方法都需要事务，比如：只有一条修改操作或者是只读操作。
     */
    @Transactional
    public SeckillExecution executeSeckill(long seckillId, long userPhone, String md5) throws SeckillException, SeckillCloseException, RepeatKillException {
        if (md5==null||!md5.equals(getMD5(seckillId))){
            throw new SeckillException("seckill被重写了！");
        }

        //执行秒杀逻辑：减库存+记录用户购买行为
        //减库存
        Date nowTime = new Date();
        try{
            int updateCount = seckillDao.reduceNumber(seckillId,nowTime);
            if (updateCount<=0){
                //说明没有更新到记录，秒杀结束
                throw new SeckillCloseException("秒杀已结束!");
            }else{
                //记录购买行为
                int insertCount = successKilledDao.insertSuccessKilled(seckillId,userPhone);
                if (insertCount<=0){
                    //重复秒杀
                    throw new RepeatKillException("秒杀重复了！");
                }else {
                    //秒杀成功
                    SuccessKilled successKilled = successKilledDao.queryByIdWithSeckill(seckillId,userPhone);
                    return new SeckillExecution(seckillId, SeckillStateEnum.SUCCESS,successKilled);
                }
            }
        }catch (SeckillCloseException e1){
            throw e1;
        }catch (RepeatKillException e2){
            throw e2;
        }catch (Exception e){
            logger.error(e.getMessage(),e);
            //所有编译期异常转换成运行期异常,这样在发生错误的时候，会进行回滚
            throw new SeckillException("seckill内部错误："+e.getMessage());
        }
    }
}
