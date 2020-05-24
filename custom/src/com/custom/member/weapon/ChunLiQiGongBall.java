package com.custom.member.weapon;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.World;
import com.custom.member.constant.MemberFixtureAttribute;
import com.custom.member.constant.MemberName;
import com.mini.constant.MiniGameScreenSetting;
import com.mini.member.GameSprite;
import com.mini.member.GameSpriteCategory;
import com.mini.member.helper.GameSpriteHelper;

public class ChunLiQiGongBall extends GameSprite {

    private ParticleEffect particleEffect;

    @Override
    protected Body createBody(World world, float initX, float initY) {
        GameSpriteHelper.BodyConfig config = new GameSpriteHelper.BodyConfig();
        config.setInitX(initX).setInitY(initY)
                .setBodyType(BodyDef.BodyType.DynamicBody).setGravityScale(0).setWorld(world)
                .setShapeType(Shape.Type.Circle).setDrawR(getDrawR()).setCategory(category).setIsSensor(true)
                .setHost(this);

        return GameSpriteHelper.me.createBody(config);
    }

    @Override
    protected void initAnimation() {
        particleEffect = new ParticleEffect();
        particleEffect.load(Gdx.files.internal("specials/ball.p"), Gdx.files.internal("specials/"));
    }

    @Override
    public void renderCustom(SpriteBatch batch, float delta) {
        batch.begin();
        particleEffect.setPosition(getDrawX(), getDrawY());
        particleEffect.draw(batch, Gdx.graphics.getDeltaTime());
        batch.end();
    }

    @Override
    public GameSpriteCategory getGameSpriteCategory() {
        if (category != null) {
            return category;
        }
        return GameSpriteCategory.build(MemberFixtureAttribute.CHUNLI_QI_GONG_BALL, MemberFixtureAttribute._CHUNLI_QI_GONG_BALL, MemberName.CHUNLI_QI_GONG_BALL);
    }

    @Override
    public float getDrawX() {
        return getPosX() * MiniGameScreenSetting.VIEW_RATE;
    }

    @Override
    public float getDrawY() {
        return getPosY() * MiniGameScreenSetting.VIEW_RATE;
    }

    @Override
    public float getDrawR() {
        return 97;
    }
}
