package com.mini.animation;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;

public class MiniAnimationHelper {

    private MiniAnimationHolder currHolder;

    public void draw(Batch batch, float delta, MiniAnimationHolder holder, float x, float y) {
        if (currHolder == null || currHolder.equals(holder)) {
            currHolder = holder;
        } else if (currHolder.isCanOverride()) {
            if (currHolder.getMiniAnimationHolderAction() != null) {
                currHolder.getMiniAnimationHolderAction().doOverrideAct();
            }
            currHolder = holder;
        }

        batch.begin();

        MiniAnimation miniAnimation = holder.getMiniAnimation();
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