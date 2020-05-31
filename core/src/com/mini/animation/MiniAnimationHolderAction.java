package com.mini.animation;

/**
 * 迷你动画持有者的行为
 */
public interface MiniAnimationHolderAction {

    default void beOverrideAct(MiniAnimationHolder holder) {
    }

    default void doOverrideAct(MiniAnimationHolder holder) {
    }

    default void doFinishAct(MiniAnimationHolder holder) {
    }
}
