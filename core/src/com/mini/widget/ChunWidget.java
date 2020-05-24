package com.mini.widget;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.mini.constant.MiniGameScreenSetting;
import com.mini.game.MiniGame;

// TODO
public class ChunWidget {
    public Image image_widget;
    private Texture texture;
    private TextureRegion textureRegion;

    public ChunWidget() {
        super();
        this.init();
    }

    private void init() {
        texture = MiniGame.assetManager.get("widgets/chunli_sign.png", Texture.class);
        textureRegion = new TextureRegion(texture);

        image_widget = new Image(textureRegion);
        MoveToAction imageMoveto = Actions.moveTo(MiniGameScreenSetting.VIEW_W, 0, 3.0f);
        Action imageEnd = Actions.run(new Runnable() {

            @Override
            public void run() {
                image_widget.getStage().getActors().removeValue(image_widget, true);
            }
        });
        SequenceAction imageSeq = Actions.sequence(imageMoveto, imageEnd);
        image_widget.setPosition(-textureRegion.getRegionWidth(), 0);
        image_widget.addAction(imageSeq);
    }
}