package com.mini.special;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public abstract class BaseEffect {
    protected float pos_x;
    protected float pos_y;
    protected ParticleEffect effect;

    public BaseEffect() {
    }

    public void draw(SpriteBatch batch) {
        effect.setPosition(pos_x, pos_y);
        effect.draw(batch, Gdx.graphics.getDeltaTime());
    }

    public void setPosition(float pos_x, float pos_y) {
        this.pos_x = pos_x;
        this.pos_y = pos_y;
    }

    public void dispose() {
        effect.dispose();
    }
}
