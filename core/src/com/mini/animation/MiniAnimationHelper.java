package com.mini.animation;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.mini.animation.strategy.MiniAnimationHolderDrawStrategyFactory;

/**
 * 迷你动画辅助器~渲染
 */
public class MiniAnimationHelper {

    private MiniAnimationHolderDrawStrategyFactory strategyFactory = new MiniAnimationHolderDrawStrategyFactory();

    // 当前的迷你动画持有者
    private MiniAnimationHolder currHolder;

    public void draw(Batch batch, float delta, MiniAnimationHolder holder, float x, float y) {
        if (currHolder == null || currHolder.equals(holder)) {
            currHolder = holder;
        } else if (currHolder.isCanOverride()) {
            if (currHolder.getAction() != null) {
                currHolder.getAction().beOverrideAct(currHolder);
            }
            currHolder = holder;
            if (currHolder.getAction() != null) {
                currHolder.getAction().doOverrideAct(currHolder);
            }
        }

        batch.begin();

        MiniAnimation miniAnimation = currHolder.getMiniAnimation();
        if (miniAnimation == null) {
            return;
        }

        Animation animation = miniAnimation.getAnimation();
        switch (animation.getPlayMode()) {
            case NORMAL:
            case REVERSED:
                if (currHolder.isFinished()) {
                    break;
                }

                currHolder.incDelta(Gdx.graphics.getDeltaTime());
                delta = currHolder.getDelta();
                if (animation.isAnimationFinished(delta)) {
                    if (miniAnimation.getAction() != null) {
                        miniAnimation.getAction().doFinishAct(miniAnimation);
                    }

                    if (currHolder.isLastIndex()) {
                        currHolder.setFinished(true);
                        strategyFactory.getMiniAnimationHolderDrawStrategy(currHolder.getMode()).doFinishAct(currHolder);
                        if (currHolder.getAction() != null) {
                            currHolder.getAction().doFinishAct(currHolder);
                        }
                    } else {
                        currHolder.nextIndex();
                        currHolder.resetDelta();
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