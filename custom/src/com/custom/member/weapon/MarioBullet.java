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
import com.mini.game.MiniGame;
import com.mini.game.MiniGameConfig;
import com.mini.handler.MiniContactReaction;
import com.mini.member.GameSprite;
import com.mini.member.GameSpriteCategory;
import com.mini.member.GameSpriteHealth;
import com.mini.member.MiniUserData;

import java.util.HashMap;
import java.util.Map;

public class MarioBullet extends GameSprite {

    public static String getMiniContactName() {
        return MemberName.MARIO_BULLET;
    }

    public static Map<String, MiniContactReaction> getMiniContactReactionMap() {
        Map<String, MiniContactReaction> miniContactReactionMap = new HashMap<>();
        miniContactReactionMap.put(MemberName.MAP_FLOOR, new MiniContactReaction() {
            @Override
            public void reactBegin(MiniUserData data) {
                MarioBullet marioBullet = (MarioBullet) data.mine;
                marioBullet.getHealth().incHP(-1);
            }
        });

        return miniContactReactionMap;
    }

    @Override
    protected Body createBody(World world, float initX, float initY) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(initX * MiniGameConfig.getPhysicalSettingViewRate(), initY * MiniGameConfig.getPhysicalSettingViewRate());
        bodyDef.type = BodyType.DynamicBody;

        Body body = world.createBody(bodyDef);
        body.setGravityScale(0);

        CircleShape shape = new CircleShape();
        shape.setRadius(getDrawR() * MiniGameConfig.getPhysicalSettingMemberViewRate());

        FixtureDef fixTureDef = new FixtureDef();
        fixTureDef.shape = shape;
        fixTureDef.restitution = 1.0f;
        fixTureDef.filter.categoryBits = category.bits;
        fixTureDef.filter.maskBits = category.maskBits;
        fixTureDef.isSensor = false;

        Fixture fixture = body.createFixture(fixTureDef);

        MiniUserData data = new MiniUserData();
        data.name = category.name;
        data.body = body;
        data.fixture = fixture;
        data.mine = this;

        body.setUserData(data);
        fixture.setUserData(data);

        return body;
    }

    @Override
    protected void initStatus() {
        health = new GameSpriteHealth(3, 0);
    }

    @Override
    protected void initAnimations() {
        Texture texture = MiniGame.assetManager.get("members/mario1.png", Texture.class);
        currentAnimation = AnimationAssist.createAnimation(texture, Lists.newArrayList(
                new AnimationAssist.Bound(287, 287, 18, 15)
        ), MiniGameConfig.getScreenSettingFrameDuration(), 0, Animation.PlayMode.LOOP);
    }

    @Override
    protected void updateStatus() {
        if (isAlive && health.hp.get() <= 0) {
            isAlive = false;
        }
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
        return getPosX() * MiniGameConfig.getScreenSettingViewRate() - getDrawR() / 2;
    }

    @Override
    public float getDrawY() {
        return getPosY() * MiniGameConfig.getScreenSettingViewRate() - getDrawR() / 2;
    }

    @Override
    public float getDrawR() {
        return 23;
    }
}