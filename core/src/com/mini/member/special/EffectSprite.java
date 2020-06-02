package com.mini.member.special;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mini.game.MiniGameConfig;
import com.mini.member.GameSprite;

/**
 * 特效精灵
 */
public abstract class EffectSprite extends GameSprite {

    private ParticleEffect particleEffect;

    @Override
    protected void initAnimation() {
        particleEffect = new ParticleEffect();

        EffectSpriteConfig effectSpriteConfig = getEffectSpriteConfig();
        particleEffect.load(Gdx.files.internal(effectSpriteConfig.effectPath), Gdx.files.internal(effectSpriteConfig.imagesPath));
    }

    @Override
    protected void renderCustom(SpriteBatch batch, float delta) {
        batch.begin();
        particleEffect.setPosition(getDrawX(), getDrawY());
        particleEffect.draw(batch, Gdx.graphics.getDeltaTime());
        batch.end();
    }

    @Override
    public float getDrawX() {
        return getPosX() * MiniGameConfig.getScreenSettingViewRate();
    }

    @Override
    public float getDrawY() {
        return getPosY() * MiniGameConfig.getScreenSettingViewRate();
    }

    protected abstract EffectSpriteConfig getEffectSpriteConfig();
}
