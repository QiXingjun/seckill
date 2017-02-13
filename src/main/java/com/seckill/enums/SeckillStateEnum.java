package com.seckill.enums;

/**
 * @Author XingJun Qi
 * @MyBlog www.qixingjun.tech
 * @Version 1.0.0
 * @Date 2017/2/12
 * @Description 使用枚举表述常量数据字段
 */
public enum SeckillStateEnum {
    SUCCESS("秒杀成功",1),
    END("秒杀结束",0),
    REPEAT_KILL("重复秒杀",-1),
    INNER_ERROR("系统异常",-2),
    DATA_REWRITE("数据篡改",-3);

    private int state;
    private String stateInfo;

    SeckillStateEnum(String stateInfo, int state) {
        this.stateInfo = stateInfo;
        this.state = state;
    }

    public int getState() {
        return state;
    }

    public String getStateInfo() {
        return stateInfo;
    }

    public static SeckillStateEnum stateOf(int index){
        for (SeckillStateEnum state:values()) {
            if (state.getState()==index){
                return state;
            }
        }
        return null;
    }
}
