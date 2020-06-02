package com.mini.screen.stage;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.HashMap;
import java.util.LinkedHashMap;

/**
 * 自定义舞台的基类
 */
public abstract class BaseStage extends Stage implements Runnable {

    /**
     * 键盘事件集合
     */
    private HashMap<Integer, KeyAct> keyActMap = new LinkedHashMap<>();

    public BaseStage(Viewport viewport, Batch batch) {
        super(viewport, batch);
    }

    /**
     * Override@创建
     */
    public abstract void create();

    @Override
    public void act() {
        update();
        super.act();
    }

    /**
     * Override@更新
     */
    protected void update() {
    }

    @Override
    public void run() {
    }

    public abstract int getViewW();

    public abstract int getViewH();

    /**
     * 配置键盘事件
     *
     * @param key    {@link com.badlogic.gdx.Input.Keys}
     * @param keyAct 事件函数
     */
    public final void setKeyAct(int key, KeyAct keyAct) {
        keyActMap.put(key, keyAct);
    }

    /**
     * 配置键盘事件 按下
     *
     * @param key
     * @param keyActDown
     */
    public final void setKeyActDown(int key, KeyActDown keyActDown) {
        setKeyAct(key, keyActDown);
    }

    /**
     * 配置键盘事件 弹起
     *
     * @param key
     * @param keyActUp
     */
    public final void setKeyActUp(int key, KeyActUp keyActUp) {
        setKeyAct(key, keyActUp);
    }

    @Override
    public boolean keyDown(int keyCode) {
        KeyAct keyAct = keyActMap.get(keyCode);
        if (keyAct != null) {
            keyAct.keyDown();
        }

        return super.keyDown(keyCode);
    }

    @Override
    public boolean keyUp(int keyCode) {
        KeyAct keyAct = keyActMap.get(keyCode);
        if (keyAct != null) {
            keyAct.keyUp();
        }

        return super.keyUp(keyCode);
    }


    /**
     * 定义键盘动作
     */
    public interface KeyAct {
        void keyDown();

        void keyUp();
    }

    public interface KeyActDown extends KeyAct {
        @Override
        default void keyUp() {
        }
    }

    public interface KeyActUp extends KeyAct {
        @Override
        default void keyDown() {
        }
    }
}
