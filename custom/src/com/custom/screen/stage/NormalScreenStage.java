package com.custom.screen.stage;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.custom.screen.NormalScreen;
import com.mini.game.MiniGame;
import com.mini.screen.stage.BaseStage;

public class NormalScreenStage extends BaseStage {

    private NormalScreen normalScreen;

    private ActorGestureListener buttonListener;
    private ProgressBar barHP, barMP;
    private Label labelScoreVolume;


    public NormalScreenStage(NormalScreen normalScreen) {
        super(new StretchViewport(normalScreen.getViewW(), normalScreen.getViewH()), normalScreen.getBatch());
        this.normalScreen = normalScreen;
    }

    @Override
    public void create() {
        createTouchPad();
        createButton();
        createProgressBar();
        createLabel();
    }

    private void createTouchPad() {
        Texture texture = MiniGame.assetManager.get("menus/control.png", Texture.class);
        TextureRegionDrawable pane = new TextureRegionDrawable(new TextureRegion(texture, 0, 0, 318, 318));
        TextureRegionDrawable knob = new TextureRegionDrawable(new TextureRegion(texture, 318, 135, 52, 52));

        Touchpad pad = new Touchpad(7, new Touchpad.TouchpadStyle(pane, knob));
        pad.setColor(0.5f, 0.5f, 0.5f, 1.0f);
        pad.setBounds(0, 0, 170, 170);
        addActor(pad);
    }

    private void createButton() {
        initButtonListener();

        Texture texture = MiniGame.assetManager.get("menus/menu1.png", Texture.class);
        TextureRegion[][] regions = TextureRegion.split(texture, 64, 64);

        Image buttonB = new Image(regions[1][0]);
        buttonB.setWidth(75f);
        buttonB.setHeight(buttonB.getWidth());
        buttonB.setPosition(getViewW() - buttonB.getWidth(), buttonB.getHeight() * 1.4f);
        buttonB.addListener(buttonListener);
        addActor(buttonB);

        Image buttonA = new Image(regions[0][2]);
        buttonA.setWidth(buttonB.getWidth());
        buttonA.setHeight(buttonB.getHeight());
        buttonA.setPosition(getViewW() - buttonA.getWidth() * 2.0f, buttonA.getHeight() * 0.1f);
        buttonA.addListener(buttonListener);
        addActor(buttonA);

        Image buttonI = new Image(new TextureRegion(MiniGame.assetManager.get("menus/menu2.png", Texture.class), 26, 14, 400, 400));
        buttonI.setWidth(buttonB.getWidth());
        buttonI.setHeight(buttonB.getHeight());
        buttonI.setPosition(getViewW() - buttonI.getWidth(), buttonI.getHeight() * 3.4f);
        buttonI.addListener(buttonListener);
        addActor(buttonI);

        Image buttonP = new Image(new TextureRegion(MiniGame.assetManager.get("menus/menu2.png", Texture.class), 432, 14, 400, 400));
        buttonP.setWidth(buttonB.getWidth());
        buttonP.setHeight(buttonB.getHeight());
        buttonP.setPosition(getViewW() - buttonP.getWidth(), buttonP.getHeight() * 4.7f);
        buttonP.addListener(buttonListener);
        addActor(buttonP);
    }

    private void initButtonListener() {
        buttonListener = new ActorGestureListener() {
            @Override
            public void touchDown(InputEvent event, float x, float y,
                                  int pointer, int button) {
                //如果主角死亡，则不必响应触摸事件
//                if (!prota.isAlive)
//                    return;
//
//                if (event.getTarget() == prota.btn_jump) {
//                    NormalScreen2.this.handle_K2(velocity_x, velocity_y);
//                } else if (event.getTarget() == prota.btn_other) {
//                    NormalScreen2.this.handle_J(velocity_x, velocity_y);
//                } else if (event.getTarget() == prota.btn_i) {
//                    NormalScreen2.this.handle_I(velocity_x, velocity_y);
//                } else if (event.getTarget() == prota.btn_p) {
//                    NormalScreen2.this.handle_P(velocity_x, velocity_y);
//                }
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
//                if (!prota.isAlive)
//                    return;
//                if (event.getTarget() == prota.btn_jump) {
//                    prota.incJumpCurCount(-1);
//                }
            }
        };
    }

    private void createProgressBar() {
//        Texture texture = MiniGame.assetManager.get("menus/status.png", Texture.class);
//        ProgressBar.ProgressBarStyle styleHP = new ProgressBar.ProgressBarStyle();
//        styleHP.background = new TextureRegionDrawable(new TextureRegion(texture, 0, 200, 5, 20));
//        styleHP.knob = new TextureRegionDrawable(new TextureRegion(texture, 0, 200, 5, 20));
//        styleHP.knobBefore = new TextureRegionDrawable(new TextureRegion(texture, 0, 0, 428, 20));
//        barHP = new ProgressBar(0.0f, 100, 0.1f, false, styleHP);
//        barHP.setSize(340.0f, 0.0f);
//        barHP.setPosition(30.0f, ScreenSetting.VIEW_H - barHP.getHeight() - 20.0f);
//        barHP.setVisible(true);
//        barHP.setValue(0);
//        addActor(barHP);
//
//        ProgressBar.ProgressBarStyle styleMP = new ProgressBar.ProgressBarStyle();
//        styleMP.background = new TextureRegionDrawable(new TextureRegion(texture, 0, 200, 5, 20));
//        styleMP.knob = new TextureRegionDrawable(new TextureRegion(texture, 0, 200, 5, 20));
//        styleMP.knobBefore = new TextureRegionDrawable(new TextureRegion(texture, 0, 22, 428, 20));
//        barMP = new ProgressBar(0.0f, 100, 0.1f, false, styleMP);
//        barMP.setSize(barHP.getWidth(), barHP.getHeight());
//        barMP.setPosition(barHP.getX(), barHP.getY() - 20 - 15f);
//        barMP.setVisible(true);
//        barMP.setValue(barHP.getValue());
//        addActor(barMP);
    }

    private void createLabel() {
        BitmapFont bitmapFont = new BitmapFont(Gdx.files.internal("scripts/inf.fnt"));
        Label.LabelStyle style = new Label.LabelStyle(bitmapFont, bitmapFont.getColor());

        Label labelHP = new Label("HP:", style);
        labelHP.setSize(labelHP.getText().length * style.font.getSpaceWidth(), style.font.getLineHeight());
        labelHP.setPosition(0, getViewH() - labelHP.getHeight());
        addActor(labelHP);

        Label labelMP = new Label("MP:", style);
        labelMP.setSize(labelHP.getText().length * style.font.getSpaceWidth(), style.font.getLineHeight());
        labelMP.setPosition(0, getViewH() - labelHP.getHeight() - labelMP.getHeight());
        addActor(labelMP);

        Image imageScore = new Image(new TextureRegion(MiniGame.assetManager.get("menus/score.png", Texture.class), 295, 171, 475, 242));
        imageScore.setSize(300, 67);
        imageScore.setPosition(getViewW() - imageScore.getWidth(), getViewH() - imageScore.getHeight());
        addActor(imageScore);

        labelScoreVolume = new Label("000000", style);
        labelScoreVolume.setSize(labelScoreVolume.getText().length * style.font.getSpaceWidth(), 7);
        labelScoreVolume.setPosition(getViewW() - imageScore.getWidth() / 2 - labelScoreVolume.getWidth() / 2, getViewH() - 57);
        addActor(labelScoreVolume);
    }

    @Override
    protected void update() {
//        barHP.setValue(normalScreen.prota.currentHP);
//        barMP.setValue(normalScreen.prota.currentMP);

        labelScoreVolume.setText(String.format("%06d", normalScreen.curScore));
    }

    @Override
    public int getViewW() {
        return normalScreen.getViewW();
    }

    @Override
    public int getViewH() {
        return normalScreen.getViewH();
    }
}
