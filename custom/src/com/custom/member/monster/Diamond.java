package com.custom.member.monster;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.World;
import com.custom.member.constant.MemberFixtureAttribute;
import com.custom.member.constant.MemberName;
import com.google.common.collect.Lists;
import com.mini.assist.AnimationAssist;
import com.mini.constant.MiniGameScreenSetting;
import com.mini.game.MiniGame;
import com.mini.member.monster.MonsterSprite;
import com.mini.member.GameSpriteCategory;
import com.mini.member.helper.GameSpriteHelper;

public class Diamond extends MonsterSprite {

    @Override
    protected Body createBody(World world, float initX, float initY) {
        GameSpriteHelper.BodyConfig config = new GameSpriteHelper.BodyConfig();
        config.setInitX(initX).setInitY(initY)
                .setBodyType(BodyType.StaticBody).setWorld(world)
                .setShapeType(Shape.Type.Circle).setDrawR(getDrawR()).setCategory(category).setIsSensor(true)
                .setHost(this);

        return GameSpriteHelper.me.createBody(config);
    }

    @Override
    protected void initAnimation() {
        Texture texture = MiniGame.assetManager.get("members/mario2.png", Texture.class);
        currentAnimation = AnimationAssist.createAnimation(texture, Lists.newArrayList(
                new AnimationAssist.Bound(229, 111, 30, 32),
                new AnimationAssist.Bound(266, 111, 30, 32),
                new AnimationAssist.Bound(194, 144, 30, 32),
                new AnimationAssist.Bound(229, 144, 30, 32)
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
        return GameSpriteCategory.build(MemberFixtureAttribute.DIAMOND, MemberFixtureAttribute._DIAMOND, MemberName.DIAMOND);
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
        return 15;
    }
}
