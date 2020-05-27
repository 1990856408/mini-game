package com.mini.assist;

import com.mini.animation.MiniAnimation;
import com.mini.animation.MiniAnimationHolder;

public class MiniAnimationHolderAssist {

    public static MiniAnimationHolder createMiniAnimationHolder(MiniAnimation miniAnimation) {
        MiniAnimationHolder miniAnimationHolder = new MiniAnimationHolder(miniAnimation.hashCode() + "");
        miniAnimationHolder.putMiniAnimation(miniAnimation);
        return miniAnimationHolder;
    }
}
