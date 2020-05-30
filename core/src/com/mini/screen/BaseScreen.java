package com.mini.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mini.game.MiniGame;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 自定义场景的基类
 */
public abstract class BaseScreen implements Screen, Runnable {

    // 游戏
    protected MiniGame miniGame;

    // 游戏时间
    private float delta;

    private HashMap<Integer, KeyAct> keyActMap = new LinkedHashMap<>();

    public BaseScreen(MiniGame miniGame) {
        this.miniGame = miniGame;
    }

    @Override
    public void show() {
        init();
    }

    // 初始化
    final void init() {
        initMap();
        initMembers();
        initStages();
        initCustom();
        initKeyActs();
    }

    // 初始化地图
    protected void initMap() {
    }

    // 初始化成员
    protected void initMembers() {
    }

    // 初始化舞台
    protected void initStages() {
    }

    // 初始化自定义
    protected void initCustom() {
    }

    // 初始化输入事件
    protected void initKeyActs() {
    }

    private void setKeyAct(int key, KeyAct keyAct) {
        keyActMap.put(key, keyAct);
    }

    protected final void setKeyActPress(int key, KeyActPress keyActPress) {
        setKeyAct(key, keyActPress);
    }

    protected final void setKeyActJust(int key, KeyActJust keyActJust) {
        setKeyAct(key, keyActJust);
    }

    @Override
    public void render(float delta) {
        this.delta += delta;

        handleInputPre();
        handleInput();
        update();

        // 清屏
        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        renderMap();
        renderMembers();
        renderStages();
        renderCustom();
    }

    // 捕获输入前
    protected void handleInputPre() {

    }

    // 捕获输入
    private void handleInput() {
        for (Map.Entry<Integer, KeyAct> entry : keyActMap.entrySet()) {
            if (Gdx.input.isKeyPressed(entry.getKey())) {
                entry.getValue().act();
            }
            if (Gdx.input.isKeyJustPressed(entry.getKey())) {
                entry.getValue().actJust();
            }
        }
    }

    // 更新属性
    private void update() {
        updateMap();
        updateMembers();
        updateStages();
        updateCamera();
        updateCustom();
    }

    // 更新舞台
    @Deprecated
    protected void updateStages() {
    }

    // 更新地图
    protected void updateMap() {
    }

    // 更新成员
    protected void updateMembers() {
    }

    // 更新相机
    protected void updateCamera() {

    }

    // 更新自定义
    protected void updateCustom() {
    }

    // 渲染地图
    protected void renderMap() {

    }

    // 渲染成员
    protected void renderMembers() {

    }

    // 渲染舞台
    protected void renderStages() {

    }

    // 渲染自定义
    protected void renderCustom() {

    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }

    public void run() {
    }

    public final SpriteBatch getBatch() {
        return miniGame.getBatch();
    }

    public final OrthographicCamera getCamera() {
        return miniGame.getCamera();
    }

    public int getViewW() {
        return miniGame.getViewW();
    }

    public int getViewH() {
        return miniGame.getViewH();
    }

    public final float getDelta() {
        return delta;
    }

    /**
     * 定义键盘动作
     */
    public interface KeyAct {
        void act();

        void actJust();
    }

    public interface KeyActPress extends KeyAct {
        @Override
        default void actJust() {
        }
    }

    public interface KeyActJust extends KeyAct {
        @Override
        default void act() {
        }
    }
}
