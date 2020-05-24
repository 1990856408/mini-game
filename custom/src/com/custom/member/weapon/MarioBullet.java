package com.custom.member.weapon;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.custom.member.constant.MemberFixtureAttribute;
import com.custom.member.constant.MemberName;
import com.google.common.collect.Lists;
import com.mini.assist.AnimationAssist;
import com.mini.assist.CustomUserData;
import com.mini.constant.MiniGamePhysicalSetting;
import com.mini.constant.MiniGameScreenSetting;
import com.mini.game.MiniGame;
import com.mini.member.GameSprite;
import com.mini.member.GameSpriteCategory;

public class MarioBullet extends GameSprite {

    @Override
    protected Body createBody(World world, float initX, float initY) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(initX * MiniGamePhysicalSetting.VIEW_RATE, initY * MiniGamePhysicalSetting.VIEW_RATE);
        bodyDef.type = BodyType.DynamicBody;

        Body body = world.createBody(bodyDef);
        body.setGravityScale(0);

        CircleShape shape = new CircleShape();
        shape.setRadius(getDrawR() * MiniGamePhysicalSetting.MEMBER_VIEW_RATE);

        FixtureDef fixTureDef = new FixtureDef();
        fixTureDef.shape = shape;
        fixTureDef.filter.categoryBits = category.bits;
        fixTureDef.filter.maskBits = category.maskBits;
        fixTureDef.isSensor = false;

        Fixture fixture = body.createFixture(fixTureDef);

        CustomUserData data = new CustomUserData();
        data.name = category.name;
        data.body = body;
        data.fixture = fixture;
        data.mine = this;

        body.setUserData(data);
        fixture.setUserData(data);

        return body;
    }

    @Override
    protected void initAnimation() {
        Texture texture = MiniGame.assetManager.get("members/mario1.png", Texture.class);
        currentAnimation = AnimationAssist.createAnimation(texture, Lists.newArrayList(
                new AnimationAssist.Bound(287, 287, 18, 15)
        ), getFrameDuration(), 0, Animation.PlayMode.LOOP);
    }

    @Override
    public void renderCustom(SpriteBatch batch, float delta) {
        batch.begin();
        batch.draw(getFrameCurrent(delta), getDrawX(), getDrawY(), getDrawR(), getDrawR());
        batch.end();
    }

    @Override
    public GameSpriteCategory getGameSpriteCategory() {
        if (category != null) {
            return category;
        }
        return GameSpriteCategory.build(MemberFixtureAttribute.MARIO_BULLET, MemberFixtureAttribute._MARIO_BULLET, MemberName.MARIO_BULLET);
    }

    @Override
    public float getDrawX() {
        return getPosX() * MiniGameScreenSetting.VIEW_RATE - getDrawR() / 2;
    }

    @Override
    public float getDrawY() {
        return getPosY() * MiniGameScreenSetting.VIEW_RATE - getDrawR() / 2;
    }

    @Override
    public float getDrawR() {
        return 23;
    }
}
