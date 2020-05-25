package com.custom.screen;

import com.badlogic.gdx.Gdx;
import com.custom.constant.CustomGameAttributeNames;
import com.custom.screen.stage.InitialScreenStage;
import com.mini.game.MiniGame;
import com.mini.screen.BaseScreen;

/**
 * 首页
 */
public class InitialScreen extends BaseScreen {

    // 这个舞台用来绘制界面和实现监听
    private InitialScreenStage initialStage;

    public InitialScreen(MiniGame miniGame) {
        super(miniGame);
    }

    @Override
    protected void initStages() {
        initialStage = new InitialScreenStage(this);
        initialStage.create();

        // 使舞台获得输入焦点
        Gdx.input.setInputProcessor(initialStage);
    }

    @Override
    protected void initCustom() {
        MiniGame.soundPlayer.playMusic("sounds/init.mp3");
    }

    @Override
    protected void renderStages() {
        initialStage.act();
        initialStage.draw();
    }

    @Override
    public void dispose() {
        initialStage.dispose();
    }

    public void startGame() {
        miniGame.resetScreen(new TransformScreen(miniGame), () -> {
            MiniGame.setAttribute(CustomGameAttributeNames.CURRENT_LEVEL, 1);
            MiniGame.soundPlayer.stopMusic();
        });
    }

    public void continueGame() {
        miniGame.resetScreen(new TransformScreen(miniGame), () -> {
            MiniGame.soundPlayer.stopMusic();
        });
    }
}
