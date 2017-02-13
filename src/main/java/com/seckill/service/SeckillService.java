package com.seckill.service;

import com.seckill.dto.Exposer;
import com.seckill.dto.SeckillExecution;
import com.seckill.entity.Seckill;
import com.seckill.exception.RepeatKillException;
import com.seckill.exception.SeckillCloseException;
import com.seckill.exception.SeckillException;

import java.util.List;

/**
 * @Author XingJun Qi
 * @MyBlog www.qixingjun.tech
 * @Version 1.0.0
 * @Date 2017/2/12
 * @Description 业务接口
 * 业务接口的设计原则：要站在“使用者”的角度设计接口，具体涉及以下三个方面：
 * 方法定义的粒度，参数，返回类型（return 类型/抛出异常）
 */
public interface SeckillService {
    /**
     * 查询所有的秒杀记录
     * @return
     */
    List<Seckill> getSeckillList();

    /**
     * 查询单个的秒杀记录
     * @param seckillId
     * @return
     */
    Seckill getSeckillbyId(long seckillId);

    /**
     * 秒杀开始的时候输出秒杀接口地址，
     * 否则输出秒杀时间和系统当前的时间
     * @param seckillId
     */
    Exposer exportSeckillUrl(long seckillId);

    /**
     * 执行秒杀操作
     * @param seckillId
     * @param userPhone
     * @param md5
     * @return
     */
    SeckillExecution executeSeckill(long seckillId, long userPhone, String md5)
            throws SeckillException,SeckillCloseException,RepeatKillException;
}
