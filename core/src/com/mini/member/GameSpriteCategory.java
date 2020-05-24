package com.mini.member;

/**
 * 游戏精灵属性 · 物理世界
 */
public class GameSpriteCategory {

    // 定义码
    public short bits;

    // 碰撞码
    public short maskBits;

    // 名称
    public String name;

    private GameSpriteCategory() {
    }

    public static GameSpriteCategory build(short bits, short maskBits, String name) {
        GameSpriteCategory gameSpriteCategory = new GameSpriteCategory();

        gameSpriteCategory.bits = bits;
        gameSpriteCategory.maskBits = maskBits;
        gameSpriteCategory.name = name;

        return gameSpriteCategory;
    }
}
