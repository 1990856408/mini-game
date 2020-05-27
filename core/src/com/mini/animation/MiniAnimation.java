package com.mini.animation;

import com.badlogic.gdx.graphics.g2d.Animation;

public class MiniAnimation {

    private Animation animation;

    private float w, h;

    private MiniAnimationAction miniAnimationAction;

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

    public MiniAnimationAction getMiniAnimationAction() {
        return miniAnimationAction;
    }

    public void setMiniAnimationAction(MiniAnimationAction miniAnimationAction) {
        this.miniAnimationAction = miniAnimationAction;
    }
}