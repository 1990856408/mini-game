package com.mini.member.helper;

import com.mini.member.GameSprite;

/**
 * @Author: zhaojn
 * @Date: 2020/5/31 22:12
 * <p>
 * 使得可以在{@link GameSpriteHolder}外面定义处理{@link GameSprite}的函数
 */
public interface GameSpriteHolderExecutor {

    /**
     * 游戏精灵处理
     *
     * @param gameSprite 游戏精灵
     */
    void execute(GameSprite gameSprite);
}