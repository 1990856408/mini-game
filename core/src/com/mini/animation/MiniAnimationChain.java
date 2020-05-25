package com.mini.animation;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedDeque;

public class MiniAnimationChain {

    private Queue<MiniAnimation> miniAnimations = new ConcurrentLinkedDeque<>();

    public void putMiniAnimation(MiniAnimation miniAnimation) {
        miniAnimations.offer(miniAnimation);
    }

    public Queue<MiniAnimation> getMiniAnimations() {
        return miniAnimations;
    }
}
