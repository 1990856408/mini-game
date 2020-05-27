package com.mini.animation;

public enum MiniAnimationHolderDrawMode {

    NORMAL(1, "normal"),
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
