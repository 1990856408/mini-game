package com.mini.animation.strategy;

import com.mini.animation.MiniAnimationHolder;
import com.mini.animation.MiniAnimationHolderDrawStrategy;

public class MiniAnimationHolderDrawLoopStrategy implements MiniAnimationHolderDrawStrategy {

    public static final MiniAnimationHolderDrawStrategy me = new MiniAnimationHolderDrawLoopStrategy();

    private MiniAnimationHolderDrawLoopStrategy() {
    }

    @Override
    public void doFinishAct(MiniAnimationHolder holder) {
        holder.resetStatus();
    }
}
