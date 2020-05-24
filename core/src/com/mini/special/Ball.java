package com.mini.special;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;

public class Ball extends BaseEffect {

    public Ball(float pos_x, float pos_y) {
        this.pos_x = pos_x;
        this.pos_y = pos_y;

        this.effect = new ParticleEffect();
        this.effect.load(Gdx.files.internal("effect/ball.p"), Gdx.files.internal("effect/"));
    }
}
