package com.mini.animation;

import java.util.ArrayList;
import java.util.List;

public class MiniAnimationHolder {

    // 编号，判断两个holder是否相等
    private String code;

    // 动画集合
    private List<MiniAnimation> miniAnimations = new ArrayList<>();

    // 当前动画的下标
    private int index;

    // 当前动画的播放时间
    private float delta;

    // 当前动画集合是否全部渲染完毕
    private boolean finished;

    // 该holder可否被覆盖
    private boolean canOverride;

    // 绘制模式
    private MiniAnimationHolderDrawMode mode;

    // 该holder的执行函数
    private MiniAnimationHolderAction action;

    public MiniAnimationHolder(String code) {
        this(code, true, MiniAnimationHolderDrawMode.NORMAL);
    }

    public MiniAnimationHolder(String code, boolean canOverride, MiniAnimationHolderDrawMode mode) {
        this.code = code;
        index = 0;
        delta = 0;
        finished = false;
        this.canOverride = canOverride;
        this.mode = mode;
        action = new MiniAnimationHolderAction() {
            @Override
            public void doOverrideAct() {
                resetStatus();
            }

            @Override
            public void doFinishAct() {

            }
        };
    }

    public void putMiniAnimation(MiniAnimation miniAnimation) {
        miniAnimations.add(miniAnimation);
    }

    public String getCode() {
        return code;
    }

    public MiniAnimation getMiniAnimation() {
        return miniAnimations.get(index);
    }

    public void nextIndex() {
        index++;
    }

    public void resetIndex() {
        index = 0;
    }

    public boolean isLastIndex() {
        return index == miniAnimations.size() - 1;
    }

    public void incDelta(float delta) {
        this.delta += delta;
    }

    public float getDelta() {
        return delta;
    }

    public void resetDelta() {
        delta = 0;
    }

    public boolean isFinished() {
        return finished;
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
    }

    public void resetStatus() {
        resetIndex();
        resetDelta();
        finished = false;
    }

    public boolean isCanOverride() {
        return canOverride;
    }

    public MiniAnimationHolderDrawMode getMode() {
        return mode;
    }

    public MiniAnimationHolderAction getAction() {
        return action;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof MiniAnimationHolder) {
            return code != null && code.equals(((MiniAnimationHolder) obj).getCode());
        }

        return false;
    }
}
