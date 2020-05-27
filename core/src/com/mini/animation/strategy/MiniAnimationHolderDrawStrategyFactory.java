package com.mini.animation.strategy;

import com.mini.animation.MiniAnimationHolderDrawMode;
import com.mini.animation.MiniAnimationHolderDrawStrategy;

import java.util.HashMap;
import java.util.Map;

public class MiniAnimationHolderDrawStrategyFactory {

    private Map<MiniAnimationHolderDrawMode, MiniAnimationHolderDrawStrategy> drawStrategyMap = new HashMap<>();

    public MiniAnimationHolderDrawStrategyFactory() {
        drawStrategyMap.put(MiniAnimationHolderDrawMode.NORMAL, MiniAnimationHolderDrawNormalStrategy.me);
        drawStrategyMap.put(MiniAnimationHolderDrawMode.LOOP, MiniAnimationHolderDrawLoopStrategy.me);
    }

    public MiniAnimationHolderDrawStrategy getMiniAnimationHolderDrawStrategy(MiniAnimationHolderDrawMode mode) {
        return drawStrategyMap.get(mode);
    }
}
