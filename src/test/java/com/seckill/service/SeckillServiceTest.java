package com.seckill.service;

import com.seckill.dto.Exposer;
import com.seckill.dto.SeckillExecution;
import com.seckill.entity.Seckill;
import com.seckill.exception.RepeatKillException;
import com.seckill.exception.SeckillCloseException;
import com.seckill.exception.SeckillException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * @Author XingJun Qi
 * @MyBlog www.qixingjun.tech
 * @Version 1.0.0
 * @Date 2017/2/13
 * @Description
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({
        "classpath:spring/spring-dao.xml",
        "classpath:spring/spring-service.xml",
})
public class SeckillServiceTest {

    //采用slf4j管理日志
    private final Logger logger = LoggerFactory.getLogger("this.getClass()");

    @Autowired
    private SeckillService seckillService;

    @Test
    public void testGetSeckillList() throws Exception {
        List<Seckill> seckillList = seckillService.getSeckillList();
        logger.info("seckillList={}", seckillList);
    }

    @Test
    public void testGetSeckillbyId() throws Exception {
        long seckillId = 1000;
        Seckill seckill = seckillService.getSeckillbyId(seckillId);
        logger.info("seckill={}", seckill);
    }

//    @Test
//    public void testExportSeckillUrl() throws Exception {
//        long seckillId = 1001;
//        Exposer exposer = seckillService.exportSeckillUrl(seckillId);
//        logger.info(exposer.toString());
//    }
//
//    @Test
//    public void testExecuteSeckill() throws Exception {
//        long seckillId = 1001;
//        long userPhone = 18862141551L;
//        String md5="3f3024481ae6d1c45b890130b4ff0943";
//        try{
//            SeckillExecution executionException = seckillService.executeSeckill(seckillId,userPhone,md5);
//            logger.info("executionException result={}",executionException);
//        }catch (RepeatKillException e){
//            logger.error(e.getMessage());
//        }catch (SeckillCloseException e){
//            logger.error(e.getMessage());
//        }
//    }

    /**
     * 为了集成测试的完整性，可以将testExportSeckillUrl()和testExecuteSeckill()方法联合起来
     * 测试，这样才可以完整的测试整个秒杀流程
     * 本测试可以重复执行
     * @throws Exception
     */
    @Test
    public void testSeckillLogic() throws Exception {
        long seckillId = 1025;
        long userPhone = 18862141551L;
        Exposer exposer = seckillService.exportSeckillUrl(seckillId);
        if (exposer.isExposed()) {
            logger.info("exposer={}", exposer.toString());
            String md5 = exposer.getMd5() ;
            try {
                SeckillExecution executionException = seckillService.executeSeckill(seckillId, userPhone, md5);
                logger.info("executionException result={}", executionException);
            } catch (RepeatKillException e) {
                logger.error(e.getMessage());
            } catch (SeckillCloseException e) {
                logger.error(e.getMessage());
            }
        }else{
            //秒杀未开启
            logger.warn("exposer={}",exposer);
        }
    }

    @Test
    public void executeSeckillProcedure(){
        long seckillId = 1026;
        long phone = 18862150258L;
        Exposer exposer = seckillService.exportSeckillUrl(seckillId);
        if (exposer.isExposed()){
            String md5 = exposer.getMd5();
            SeckillExecution execution = seckillService.executeSeckillProcedure(seckillId,phone,md5);
            logger.info("execution.getStateInfo()="+execution.getStateInfo());
        }
    }
}