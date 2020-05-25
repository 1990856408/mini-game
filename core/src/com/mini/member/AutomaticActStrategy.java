package com.mini.member;

/**
 * 智能执行策略
 */
public interface AutomaticActStrategy {

    /**
     * 策略实现，在{@link AutomaticAct#autoExecute()}中调用
     */
    void execute();
}
