package com.mini.member.status;

/**
 * 游戏精灵当前朝向
 */
public enum GameSpriteDirection {

    U(1, "up"),
    D(2, "down"),
    L(3, "left"),
    R(4, "right"),
    F(5, "forward"),
    B(6, "backward");

    private final int type;

    private final String name;

    GameSpriteDirection(int type, String name) {
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
