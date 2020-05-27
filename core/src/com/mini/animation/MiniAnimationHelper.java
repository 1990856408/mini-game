package com.mini.animation;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.mini.animation.strategy.MiniAnimationHolderDrawStrategyFactory;

public class MiniAnimationHelper {

    private MiniAnimationHolderDrawStrategyFactory strategyFactory = new MiniAnimationHolderDrawStrategyFactory();

    private MiniAnimationHolder currHolder;

    public void draw(Batch batch, float delta, MiniAnimationHolder holder, float x, float y) {
        if (currHolder == null || currHolder.equals(holder)) {
            currHolder = holder;
        } else if (currHolder.isCanOverride()) {
            if (currHolder.getAction() != null) {
                currHolder.getAction().doOverrideAct();
            }
            currHolder = holder;
        }

        batch.begin();

        MiniAnimation miniAnimation = holder.getMiniAnimation();
        if (miniAnimation == null) {
            return;
        }

        Animation animation = miniAnimation.getAnimation();
        switch (animation.getPlayMode()) {
            case NORMAL:
            case REVERSED:
                if (holder.isFinished()) {
                    break;
                }

                holder.incDelta(Gdx.graphics.getDeltaTime());
                delta = holder.getDelta();
                if (animation.isAnimationFinished(delta)) {
                    if (miniAnimation.getAction() != null) {
                        miniAnimation.getAction().doFinishAct();
                    }

                    if (holder.isLastIndex()) {
                        holder.setFinished(true);
                        strategyFactory.getMiniAnimationHolderDrawStrategy(holder.getMode()).doFinishAct(holder);
                        if (holder.getAction() != null) {
                            holder.getAction().doFinishAct();
                        }
                    } else {
                        holder.nextIndex();
                        holder.resetDelta();
                    }
                } else {
                    batch.draw(animation.getKeyFrame(delta), x, y, miniAnimation.getW(), miniAnimation.getH());
                }
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