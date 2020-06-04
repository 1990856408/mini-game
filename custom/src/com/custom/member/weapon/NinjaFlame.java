package com.custom.member.weapon;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.World;
import com.custom.member.constant.MemberFixtureAttribute;
import com.custom.member.constant.MemberName;
import com.google.common.collect.Lists;
import com.mini.assist.AnimationAssist;
import com.mini.game.MiniGame;
import com.mini.game.MiniGameConfig;
import com.mini.member.GameSprite;
import com.mini.member.GameSpriteCategory;
import com.mini.member.helper.GameSpriteHelper;
import com.mini.member.status.GameSpriteDirection;

import java.util.HashMap;
import java.util.Map;

public class NinjaFlame extends GameSprite {

    private Map<GameSpriteDirection, Animation> animationMap = new HashMap<>();

    @Override
    protected Body createBody(World world, float initX, float initY) {
        GameSpriteHelper.BodyConfig config = new GameSpriteHelper.BodyConfig();
        config.setInitX(initX).setInitY(initY)
                .setBodyType(BodyDef.BodyType.DynamicBody).setGravityScale(0).setWorld(world)
                .setShapeType(Shape.Type.Polygon).setDrawW(getDrawW()).setDrawH(getDrawH()).setCategory(category).setIsSensor(true)
                .setHost(this);

        return GameSpriteHelper.me.createBody(config);
    }

    @Override
    protected void initAnimation() {
        Texture texture = MiniGame.assetManager.get("members/mario1.png", Texture.class);
        animationMap.put(GameSpriteDirection.L, AnimationAssist.createAnimation(texture, Lists.newArrayList(
                new AnimationAssist.Bound(201, 407, 28, 16),
                new AnimationAssist.Bound(243, 407, 28, 16),
                new AnimationAssist.Bound(282, 407, 28, 16),
                new AnimationAssist.Bound(326, 407, 28, 16)
        ), MiniGameConfig.getScreenSettingFrameDuration(), 0, Animation.PlayMode.LOOP));
        animationMap.put(GameSpriteDirection.R, AnimationAssist.createAnimation(texture, Lists.newArrayList(
                new AnimationAssist.Bound(201, 407, 28, 16),
                new AnimationAssist.Bound(243, 407, 28, 16),
                new AnimationAssist.Bound(282, 407, 28, 16),
                new AnimationAssist.Bound(326, 407, 28, 16)
        ), MiniGameConfig.getScreenSettingFrameDuration(), 1, Animation.PlayMode.LOOP));
    }

    @Override
    protected void updateStatus() {
    }

    @Override
    protected void updateAnimation() {
        currentAnimation = animationMap.get(direction);
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
        return GameSpriteCategory.build(MemberFixtureAttribute.NINJA_FLAME, MemberFixtureAttribute._NINJA_FLAME, MemberName.NINJA_FLAME);
    }

    @Override
    public float getDrawX() {
        return getPosX() * MiniGameConfig.getScreenSettingViewRate() - getDrawW() / 2;
    }

    @Override
    public float getDrawY() {
        return getPosY() * MiniGameConfig.getScreenSettingViewRate() - getDrawH() / 2;
    }

    @Override
    public float getDrawW() {
        return 70;
    }

    @Override
    public float getDrawH() {
        return getDrawW() * 0.35f;
    }
}
