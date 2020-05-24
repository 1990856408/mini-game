package com.custom.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.custom.constant.CustomGameAttributeNames;
import com.mini.constant.MiniGameScreenSetting;
import com.mini.game.MiniGame;
import com.mini.screen.BaseScreen;

public class TransformScreen extends BaseScreen {

    private Label labelInfo;

    public TransformScreen(MiniGame mainGame) {
        super(mainGame);
    }

    @Override
    protected void initCustom() {
        BitmapFont bitmapFont = new BitmapFont(Gdx.files.internal("scripts/inf.fnt"));
        Label.LabelStyle style = new Label.LabelStyle(bitmapFont, bitmapFont.getColor());
        labelInfo = new Label(String.format("level -- %02d", MiniGame.getAttributeInt(CustomGameAttributeNames.CURRENT_LEVEL)), style);
        labelInfo.setFontScale(2.0f);
        labelInfo.setPosition(getViewW() / 2.0f - 120.0f, getViewH() / 2.0f);
    }

    @Override
    protected void updateCustom() {
        if (getDelta() >= 0.5) {
            miniGame.resetScreen(new NormalScreen(miniGame));
        }
    }

    @Override
    protected void renderCustom() {
        Batch batch = getBatch();

        batch.begin();
        labelInfo.draw(batch, 1.0f);
        batch.end();
    }
}
