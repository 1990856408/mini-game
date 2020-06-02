package com.mini.animation;

public enum MiniAnimationHolderDrawMode {

    // 普通模式
    NORMAL(1, "normal"),
    // 循环模式
    LOOP(2, "loop");

    private int type;

    private String name;

    MiniAnimationHolderDrawMode(int type, String name) {
        this.type = type;
        this.name = name;
    }

    public int getType() {
        return type;
    }

    public String getName() {
        return name;
    }
}
