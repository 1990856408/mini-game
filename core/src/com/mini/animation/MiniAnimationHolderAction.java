package com.mini.animation;

/**
 * 迷你动画持有者的函数
 */
public interface MiniAnimationHolderAction {

    /**
     * 被覆盖时执行
     *
     * @param holder 被覆盖的持有者
     */
    default void beOverrideAct(MiniAnimationHolder holder) {
    }

    /**
     * 覆盖时执行
     *
     * @param holder 当前的持有者
     */
    default void doOverrideAct(MiniAnimationHolder holder) {
    }

    /**
     * 渲染完毕时执行
     *
     * @param holder 当前的持有者
     */
    default void doFinishAct(MiniAnimationHolder holder) {
    }
}
