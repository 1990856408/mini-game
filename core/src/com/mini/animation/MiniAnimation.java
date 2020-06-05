package com.mini.animation;

import com.badlogic.gdx.graphics.g2d.Animation;

/**
 * 迷你动画
 * 可配置{@link MiniAnimationAction}做增强处理
 */
public class MiniAnimation {

    // 动画
    private Animation animation;

    // 绘制宽高
    private float w, h;

    // 执行函数
    private MiniAnimationAction action;

    public MiniAnimation(Animation animation, float w, float h) {
        this.animation = animation;
        this.w = w;
        this.h = h;
    }

    public Animation getAnimation() {
        return animation;
    }

    public float getW() {
        return w;
    }

    public float getH() {
        return h;
    }

    public MiniAnimationAction getAction() {
        return action;
    }

    public void setAction(MiniAnimationAction action) {
        this.action = action;
    }
}