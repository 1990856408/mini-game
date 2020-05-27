package com.mini.animation.strategy;

import com.mini.animation.MiniAnimationHolder;
import com.mini.animation.MiniAnimationHolderDrawStrategy;

public class MiniAnimationHolderDrawNormalStrategy implements MiniAnimationHolderDrawStrategy {

    public static final MiniAnimationHolderDrawStrategy me = new MiniAnimationHolderDrawNormalStrategy();

    private MiniAnimationHolderDrawNormalStrategy() {
    }

    @Override
    public void doFinishAct(MiniAnimationHolder holder) {

    }
}
