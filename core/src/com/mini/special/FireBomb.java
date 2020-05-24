package com.mini.special;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;

public class FireBomb extends BaseEffect {
    public FireBomb(float pos_x, float pos_y) {
        this.pos_x = pos_x;
        this.pos_y = pos_y;

        this.effect = new ParticleEffect();
        this.effect.load(Gdx.files.internal("effect/fire.p"), Gdx.files.internal("effect/"));
    }
}
