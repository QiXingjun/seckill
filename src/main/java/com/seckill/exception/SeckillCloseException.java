package com.seckill.exception;

/**
 * @Author XingJun Qi
 * @MyBlog www.qixingjun.tech
 * @Version 1.0.0
 * @Date 2017/2/12
 * @Description 秒杀关闭异常
 */
public class SeckillCloseException extends SeckillException{

    public SeckillCloseException() {
    }

    public SeckillCloseException(String message) {
        super(message);
    }

    public SeckillCloseException(String message, Throwable cause) {
        super(message, cause);
    }
}
