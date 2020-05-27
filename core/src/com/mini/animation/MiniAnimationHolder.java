package com.mini.animation;

import java.util.ArrayList;
import java.util.List;

public class MiniAnimationHolder {

    // 编号
    private String code;

    private List<MiniAnimation> miniAnimations = new ArrayList<>();

    // 下标
    private int index;

    // 能否被覆盖
    private boolean canOverride;

    private MiniAnimationHolderAction miniAnimationHolderAction;

    public MiniAnimationHolder(String code) {
        this(code, true);
    }

    public MiniAnimationHolder(String code, boolean canOverride) {
        this.code = code;
        this.canOverride = canOverride;
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

    public void next() {
        index++;
    }

    public boolean isCanOverride() {
        return canOverride;
    }

    public MiniAnimationHolderAction getMiniAnimationHolderAction() {
        return miniAnimationHolderAction;
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
