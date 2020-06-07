package com.mini.member.helper;

import com.mini.member.GameSprite;

/**
 * @Author: zhaojn
 * @Date: 2020/6/6 16:40
 */
public class GameSpriteHolderHelper {

    public static final GameSpriteHolderHelper me = new GameSpriteHolderHelper();

    private GameSpriteHolderHelper() {
    }

    public void pushCreateTask(GameSpriteHolder gameSpriteHolder, GameSprite gameSprite, float initX, float initY) {
        gameSpriteHolder.pushCreateTask(buildCreateTask(gameSprite, initX, initY));
    }

    public void pushCreateTask(GameSpriteHolder gameSpriteHolder, GameSprite gameSprite, float initX, float initY, GameSpriteHolder.CreateAction createAction) {
        gameSpriteHolder.pushCreateTask(buildCreateTask(gameSprite, initX, initY, createAction));
    }

    public GameSpriteHolder.CreateTask buildCreateTask(GameSprite gameSprite, float initX, float initY) {
        return buildCreateTask(gameSprite, initX, initY, null);
    }

    public GameSpriteHolder.CreateTask buildCreateTask(GameSprite gameSprite, float initX, float initY, GameSpriteHolder.CreateAction createAction) {
        return new GameSpriteHolder.CreateTask() {
            @Override
            public GameSprite getGameSprite() {
                return gameSprite;
            }

            @Override
            public float getInitX() {
                return initX;
            }

            @Override
            public float getInitY() {
                return initY;
            }

            @Override
            public GameSpriteHolder.CreateAction getCreateAction() {
                return createAction;
            }
        };
    }
}