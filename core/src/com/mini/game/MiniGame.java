package com.mini.game;

import com.alibaba.fastjson.JSONObject;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.google.common.collect.Lists;
import com.mini.constant.MiniGameSourcePath;
import com.mini.screen.BaseScreen;
import com.mini.tool.MiniThreadFactory;
import com.mini.tool.SoundPlayer;

import java.io.File;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 游戏起始入口，自定义游戏时继承该类
 */
public abstract class MiniGame extends Game {

    // 线程执行器
    public static ExecutorService executorService = Executors.newCachedThreadPool(new MiniThreadFactory());
    // 随机生成器
    public static Random random = new Random();
    // 音乐播放器
    public static SoundPlayer soundPlayer = new SoundPlayer();
    // 资源管理器
    public static AssetManager assetManager;

    // 全局属性配置
    private static JSONObject attributes = new JSONObject();

    // 图像精灵
    private SpriteBatch batch;
    // 图形相机
    private OrthographicCamera camera;

    private Screen currScreen;

    public static void setAttribute(String name, Object value) {
        attributes.put(name, value);
    }

    public static byte getAttributeByte(String name) {
        return attributes.getByte(name);
    }

    public static short getAttributeShort(String name) {
        return attributes.getShort(name);
    }

    public static int getAttributeInt(String name) {
        return attributes.getInteger(name);
    }

    public static float getAttributeFloat(String name) {
        return attributes.getFloat(name);
    }

    public static double getAttributeDouble(String name) {
        return attributes.getDouble(name);
    }

    public static String getAttributeString(String name) {
        return attributes.getString(name);
    }

    public static Object getAttribute(String name) {
        return attributes.get(name);
    }

    @Override
    public final void create() {
        initAssets();
        initTools();
        initCustom();

        resetScreen(getInitScreen());
    }

    /**
     * 提供一个初始化屏幕，作为游戏的初始呈现，需要在子类中实现
     *
     * @return
     */
    protected abstract BaseScreen getInitScreen();

    /**
     * 初始化资源
     */
    private void initAssets() {
        // 加载配置文件
        MiniGameConfig.load();

        assetManager = new AssetManager();

        // 初始化图片资源
        List<String> picturePaths = Lists.newArrayList(MiniGameSourcePath.PICTURE_PATHS);
        picturePaths.addAll(getPicturePaths());
        for (String path : picturePaths) {
            File picDir = new File(path);
            for (File pic : Objects.requireNonNull(picDir.listFiles())) {
                assetManager.load(pic.getPath(), Texture.class);
            }
        }

        // 初始化音效资源
        List<String> soundPaths = Lists.newArrayList(MiniGameSourcePath.SOUND_PATHS);
        soundPaths.addAll(getSoundPaths());
        for (String path : soundPaths) {
            File soundDir = new File(path);
            for (File sound : Objects.requireNonNull(soundDir.listFiles())) {
                String soundPath = sound.getPath();
                if (soundPath.matches(".*ogg$")) {
                    assetManager.load(soundPath, Sound.class);
                }
                if (soundPath.matches(".*mp3$")) {
                    assetManager.load(soundPath, Music.class);
                }
            }
        }

        assetManager.finishLoading();
    }

    /**
     * 初始化工具，这里的工具包括精灵和相机
     */
    private void initTools() {
        batch = new SpriteBatch();

        camera = new OrthographicCamera();
        camera.setToOrtho(false, getViewW(), getViewH());
    }

    /**
     * Override@自定义初始化
     */
    protected void initCustom() {

    }

    public final void resetScreen(Screen screen) {
        resetScreen(screen, null, true);
    }

    public final void resetScreen(Screen screen, MiniGameResetScreenFunction function) {
        resetScreen(screen, function, true);
    }

    /**
     * 重置屏幕
     *
     * @param screen            替换的屏幕
     * @param function          替换屏幕之后的执行函数
     * @param disposeCurrScreen 是否释放当前屏幕
     */
    public final void resetScreen(Screen screen, MiniGameResetScreenFunction function, boolean disposeCurrScreen) {
        if (disposeCurrScreen && currScreen != null) {
            currScreen.dispose();
        }
        setScreen(screen);
        if (function != null) {
            function.execute();
        }
        currScreen = screen;
    }

    @Override
    public final void dispose() {
        soundPlayer.dispose();
        assetManager.dispose();

        batch.dispose();

        currScreen.dispose();

        disposeCustom();
    }

    /**
     * Override@自定义销毁
     */
    protected void disposeCustom() {
    }

    public final SpriteBatch getBatch() {
        return batch;
    }

    public final OrthographicCamera getCamera() {
        return camera;
    }

    public int getViewW() {
        return MiniGameConfig.getScreenSettingViewW();
    }

    public int getViewH() {
        return MiniGameConfig.getScreenSettingViewH();
    }

    /**
     * Override@自定义图片资源路径
     */
    public List<String> getPicturePaths() {
        return Lists.newArrayList();
    }

    /**
     * Override@自定义音效资源路径
     */
    public List<String> getSoundPaths() {
        return Lists.newArrayList();
    }
}
