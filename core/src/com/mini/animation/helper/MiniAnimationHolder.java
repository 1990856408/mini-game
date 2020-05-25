package com.mini.animation.helper;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.mini.animation.MiniAnimation;
import com.mini.animation.MiniAnimationChain;

public class MiniAnimationHolder {

    public void draw(Batch batch, float delta, MiniAnimationChain miniAnimationChain, float x, float y) {
        MiniAnimation miniAnimation = miniAnimationChain.getMiniAnimations().peek();
        if (miniAnimation == null) {
            return;
        }

        batch.begin();
        Animation animation = miniAnimation.getAnimation();
        switch (animation.getPlayMode()) {
            case NORMAL:
            case REVERSED:
                batch.draw(animation.getKeyFrame(delta), x, y, miniAnimation.getW(), miniAnimation.getH());
                break;
            case LOOP:
            case LOOP_REVERSED:
            case LOOP_PINGPONG:
            case LOOP_RANDOM:
                batch.draw(animation.getKeyFrame(delta), x, y, miniAnimation.getW(), miniAnimation.getH());
                break;
        }

        batch.end();
    }
}