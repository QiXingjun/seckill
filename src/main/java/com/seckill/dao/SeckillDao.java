package com.seckill.dao;

import com.seckill.entity.Seckill;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Author XingJun Qi
 * @MyBlog www.qixingjun.tech
 * @Version 1.0.0
 * @Date 2017/2/11
 * @Description
 */
public interface SeckillDao {
    /**
     * 减库存操作
     * @param seckillId 秒杀商品的ID
     * @param killTime 秒杀的时间
     * @return 更新的记录行数，如果>=1，表示成功；否则表示更新失败；
     */
    int reduceNumber(@Param("seckillId") long seckillId, @Param("killTime") Date killTime);

    /**
     * 根据Id查询秒杀对象
     * @param seckillId 秒杀商品的ID
     * @return
     */
    Seckill queryById(long seckillId);

    /**
     * 根据偏移量查询所有的秒杀商品
     * @param offset 偏移量
     * @param limit 偏移量之后取多少条记录
     * @return
     */
    List<Seckill> queryAll(@Param("offset") int offset, @Param("limit") int limit);

    /**
     * 使用存储过程执行秒杀
     * @param paramMap
     */
    void killByProcedure(Map<String,Object> paramMap);
}
