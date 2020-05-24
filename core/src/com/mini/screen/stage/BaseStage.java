package com.mini.screen.stage;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.Viewport;

public abstract class BaseStage extends Stage implements Runnable{

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
    protected void update(){
    }

    @Override
    public void run() {
    }

    public abstract int getViewW();

    public abstract int getViewH();
}
