package com.custom.game;

import com.custom.constant.CustomGameAttributeNames;
import com.custom.screen.NormalScreen;
import com.mini.game.MiniGame;
import com.mini.screen.BaseScreen;

public class CustomGame extends MiniGame {

    static {

        MiniGame.setAttribute(CustomGameAttributeNames.CURRENT_LEVEL, 1);
    }

    @Override
    protected BaseScreen getInitScreen() {
        return new NormalScreen(this);
    }
}