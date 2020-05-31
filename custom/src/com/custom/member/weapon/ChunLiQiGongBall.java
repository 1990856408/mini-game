package com.custom.member.weapon;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.World;
import com.custom.member.constant.MemberFixtureAttribute;
import com.custom.member.constant.MemberName;
import com.mini.member.GameSpriteCategory;
import com.mini.member.helper.GameSpriteHelper;
import com.mini.member.special.EffectSprite;
import com.mini.member.special.EffectSpriteConfig;

public class ChunLiQiGongBall extends EffectSprite {

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
    public GameSpriteCategory getGameSpriteCategory() {
        if (category != null) {
            return category;
        }
        return GameSpriteCategory.build(MemberFixtureAttribute.CHUNLI_QI_GONG_BALL, MemberFixtureAttribute._CHUNLI_QI_GONG_BALL, MemberName.CHUNLI_QI_GONG_BALL);
    }

    @Override
    public float getDrawR() {
        return 97;
    }

    @Override
    protected EffectSpriteConfig getEffectSpriteConfig() {
        return new EffectSpriteConfig("specials/ball.p", "specials/");
    }
}
