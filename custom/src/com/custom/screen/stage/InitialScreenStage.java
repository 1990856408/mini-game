package com.custom.screen.stage;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.custom.screen.InitialScreen;
import com.mini.game.MiniGame;
import com.mini.screen.stage.BaseStage;

public class InitialScreenStage extends BaseStage {

    // 资源加载条格式
    private static final String assetsBarCurrFormat = "...%4.1f%%";

    private InitialScreen initialScreen;

    private InputListener inputListener;

    // 游戏开始，游戏继续，游戏介绍
    private Image btnStart, btnContinue, btnDesc;

    private Label assetsBar;

    private float assetsBarCurr = 0;

    private boolean assetsBarCompleted;

    public InitialScreenStage(InitialScreen initialScreen) {
        super(new StretchViewport(initialScreen.getViewW(), initialScreen.getViewH()), initialScreen.getBatch());
        this.initialScreen = initialScreen;
    }

    @Override
    public void create() {
        initInputListener();

        // 设置背景图片
        Image image = new Image(MiniGame.assetManager.get("widgets/init.jpg", Texture.class));
        image.setBounds(0, 0, getViewW(), getViewH());
        addActor(image);

        // 设置资源加载条
        BitmapFont assetsBarFont = new BitmapFont();
        assetsBar = new Label(String.format(assetsBarCurrFormat, assetsBarCurr), new Label.LabelStyle(assetsBarFont, assetsBarFont.getColor()));
        assetsBar.setBounds(0, getViewH() - assetsBarFont.getLineHeight(), 0, assetsBarFont.getLineHeight());
        addActor(assetsBar);

        // 设置按钮
        Texture texture = MiniGame.assetManager.get("widgets/init_btn.png", Texture.class);

        btnStart = new Image(new TextureRegion(texture, 0, 0, 143, 30));
        btnStart.setBounds(getViewW() * 0.1f, 0, 170, 70);
        btnStart.addListener(inputListener);
        addActor(btnStart);

        btnContinue = new Image(new TextureRegion(texture, 0, 29, 143, 30));
        btnContinue.setBounds(getViewW() - getViewW() * 0.1f - btnStart.getWidth(), 0, btnStart.getWidth(), btnStart.getHeight());
        btnContinue.addListener(inputListener);
        addActor(btnContinue);

        btnDesc = new Image(new TextureRegion(texture, 0, 57, 143, 30));
        btnDesc.setBounds(getViewW() * 0.5f - btnStart.getWidth() * 0.5f, 0, btnStart.getWidth(), btnStart.getHeight());
        btnDesc.addListener(inputListener);
        addActor(btnDesc);
    }

    private void initInputListener() {
        inputListener = new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                // TODO 如果资源还没有加载完成，则提示
                if (!assetsBarCompleted) {
                    return true;
                }

                // 获取当前产生事件的演员
                Actor actor = event.getTarget();
                if (actor.equals(btnStart)) {
                    initialScreen.startGame();
                } else if (actor.equals(btnContinue)) {
                    initialScreen.continueGame();
                } else if (actor.equals(btnDesc)) {
                    // TODO 简介
                }

                return true;
            }
        };
    }

    @Override
    protected void update() {
        // 更新资源加载百分比
        if (assetsBarCompleted) {
            return;
        }

        assetsBarCurr = 100 * MiniGame.assetManager.getProgress();
        assetsBar.setText(String.format(assetsBarCurrFormat, assetsBarCurr));

        if (assetsBarCurr >= 100) {
            assetsBarCompleted = true;
        }
    }

    @Override
    public int getViewW() {
        return initialScreen.getViewW();
    }

    @Override
    public int getViewH() {
        return initialScreen.getViewH();
    }
}