package com.custom.member.monster;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.custom.member.constant.MemberFixtureAttribute;
import com.custom.member.constant.MemberName;
import com.custom.member.status.DuckStatus;
import com.google.common.collect.Lists;
import com.mini.assist.AnimationAssist;
import com.mini.assist.CustomUserData;
import com.mini.constant.MiniGamePhysicalSetting;
import com.mini.constant.MiniGameScreenSetting;
import com.mini.game.MiniGame;
import com.mini.member.GameSpriteCategory;
import com.mini.member.helper.ApplyVelocityStrategy;
import com.mini.member.monster.MonsterSprite;
import com.mini.member.status.GameSpriteDirection;

import java.util.HashMap;
import java.util.Map;

public class Duck extends MonsterSprite {

    protected DuckStatus status;

    private Map<DuckStatus, Map<GameSpriteDirection, Animation>> animationMap = new HashMap<>();

    private ApplyVelocityStrategy applyVelocityStrategy;

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
    protected void preInit() {
        ruleReverseDirection.setMinScopeX(initPosX+getDrawR() * MiniGamePhysicalSetting.VIEW_RATE * -3);
        ruleReverseDirection.setMaxScopeX(initPosX+getDrawR() * MiniGamePhysicalSetting.VIEW_RATE * +3);

        applyVelocityStrategy = new ApplyVelocityStrategy(this);
        applyVelocityStrategy.setVelocity(1.0f, 0f);
    }

    @Override
    protected void initAnimation() {
        Texture texture = MiniGame.assetManager.get("members/mario1.png", Texture.class);

        insertAnimation(DuckStatus.QUIET, GameSpriteDirection.L, AnimationAssist.createAnimation(texture, Lists.newArrayList(
                new AnimationAssist.Bound(207, 243, 17, 23)
        ), MiniGameScreenSetting.FRAME_DURATION, 0, Animation.PlayMode.LOOP));

        insertAnimation(DuckStatus.QUIET, GameSpriteDirection.R, AnimationAssist.createAnimation(texture, Lists.newArrayList(
                new AnimationAssist.Bound(207, 243, 17, 23)
        ), MiniGameScreenSetting.FRAME_DURATION, 1, Animation.PlayMode.LOOP));

        insertAnimation(DuckStatus.FLY, GameSpriteDirection.L, AnimationAssist.createAnimation(texture, Lists.newArrayList(
                new AnimationAssist.Bound(127, 243, 17, 23),
                new AnimationAssist.Bound(167, 243, 17, 23),
                new AnimationAssist.Bound(207, 243, 17, 23),
                new AnimationAssist.Bound(247, 243, 17, 23)
        ), MiniGameScreenSetting.FRAME_DURATION, 0, Animation.PlayMode.LOOP));

        insertAnimation(DuckStatus.FLY, GameSpriteDirection.R, AnimationAssist.createAnimation(texture, Lists.newArrayList(
                new AnimationAssist.Bound(127, 243, 17, 23),
                new AnimationAssist.Bound(167, 243, 17, 23),
                new AnimationAssist.Bound(207, 243, 17, 23),
                new AnimationAssist.Bound(247, 243, 17, 23)
        ), MiniGameScreenSetting.FRAME_DURATION, 1, Animation.PlayMode.LOOP));
    }

    private void insertAnimation(DuckStatus duckStatus, GameSpriteDirection gameSpriteDirection, Animation animation) {
        Map<GameSpriteDirection, Animation> directionMap = animationMap.get(duckStatus);
        if (directionMap == null) {
            directionMap = new HashMap<>();
        }
        directionMap.put(gameSpriteDirection, animation);

        animationMap.put(duckStatus, directionMap);
    }

    @Override
    protected void initStatus() {
        direction = GameSpriteDirection.R;

        status = DuckStatus.FLY;
    }

    @Override
    public void autoExecute() {
        ruleReverseDirection.react();

        applyVelocityStrategy.execute();
    }

    @Override
    protected void updateAnimation() {
        currentAnimation = animationMap.get(status).get(direction);
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
        return GameSpriteCategory.build(MemberFixtureAttribute.DUCK, MemberFixtureAttribute._DUCK, MemberName.DUCK);
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
        return 45;
    }
}