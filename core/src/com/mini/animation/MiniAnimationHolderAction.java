package com.mini.animation;

public interface MiniAnimationHolderAction {

    default void beOverrideAct(MiniAnimationHolder holder) {
    }

    default void doOverrideAct(MiniAnimationHolder holder) {
    }

    default void doFinishAct(MiniAnimationHolder holder) {
    }
}
