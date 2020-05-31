package com.mini.animation;

/**
 * 迷你动画的行为
 */
public interface MiniAnimationAction {

    // 当迷你动画渲染完毕时执行
    void doFinishAct(MiniAnimation miniAnimation);
}