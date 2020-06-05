package com.mini.animation;

import java.util.ArrayList;
import java.util.List;

/**
 * 迷你动画持有者~链模式
 * 丰富的动画持有者，将{@link MiniAnimation}串成一条链，
 * 通过{@link MiniAnimationHelper}渲染，可对链中的各个元素以及整条链做增强处理，
 * 配合替换器{@link com.mini.tool.MiniReplacerTool}与非阻塞队列{@link com.mini.tool.MiniNonBlockQueue}
 * 可保证属性、刚体、夹具、动画的一致性
 */
public final class MiniAnimationHolder {

    // 编号，判断两个holder是否相等
    private String code;

    // 动画链
    private List<MiniAnimation> miniAnimations = new ArrayList<>();

    // 当前渲染动画的下标
    private int index;

    // 当前渲染动画的播放时间
    private float delta;

    // 当前动画集合是否全部渲染完毕
    private boolean finished;

    // 该holder可否被覆盖
    private boolean canOverride;

    // 绘制模式，用来决定动画集合全部渲染完毕时的执行策略
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
            public void beOverrideAct(MiniAnimationHolder holder) {
                holder.resetStatus();
            }
        };
    }

    public void putMiniAnimation(MiniAnimation miniAnimation) {
        miniAnimations.add(miniAnimation);
    }

    public String getCode() {
        return code;
    }

    // 取当前渲染的动画
    public MiniAnimation getMiniAnimation() {
        return miniAnimations.get(index);
    }

    // 渲染动画的下标向后推一个
    public void nextIndex() {
        index++;
    }

    // 重置动画的渲染下标
    public void resetIndex() {
        index = 0;
    }

    // 当前渲染的动画是否为最后一个
    public boolean isLastIndex() {
        return index == miniAnimations.size() - 1;
    }

    // 渲染时间增量处理，非循环动画需要该参数
    public void incDelta(float delta) {
        this.delta += delta;
    }

    public float getDelta() {
        return delta;
    }

    // 重置渲染时间
    public void resetDelta() {
        delta = 0;
    }

    public boolean isFinished() {
        return finished;
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
    }

    // 重置渲染相关的参数
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

    public void setAction(MiniAnimationHolderAction action) {
        this.action = action;
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