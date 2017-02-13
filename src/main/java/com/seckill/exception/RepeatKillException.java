package com.seckill.exception;

/**
 * @Author XingJun Qi
 * @MyBlog www.qixingjun.tech
 * @Version 1.0.0
 * @Date 2017/2/12
 * @Description 重复秒杀异常（运行期异常）
 * Spring的声明式事务只支持运行期异常
 */
public class RepeatKillException extends SeckillException{

    public RepeatKillException() {
    }

    public RepeatKillException(String message) {
        super(message);
    }

    public RepeatKillException(String message, Throwable cause) {
        super(message, cause);
    }
}
