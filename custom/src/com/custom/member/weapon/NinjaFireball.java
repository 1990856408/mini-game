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
import com.mini.graph.MiniPosition;
import com.mini.graph.track.TrackCircle;
import com.mini.member.GameSprite;
import com.mini.member.GameSpriteCategory;
import com.mini.member.helper.GameSpriteHelper;

public class NinjaFireball extends GameSprite {

    // 父精灵
    private GameSprite father;

    // 圆形轨迹
    private TrackCircle trackCircle;

    public NinjaFireball(GameSprite father, MiniPosition miniPositionA, MiniPosition miniPositionB, long interval) {
        this.father = father;
        this.trackCircle = new TrackCircle(miniPositionA, miniPositionB, interval);
    }

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
        Texture texture = MiniGame.assetManager.get("members/mario1.png", Texture.class);
        currentAnimation = AnimationAssist.createAnimation(texture, Lists.newArrayList(
                new AnimationAssist.Bound(409, 319, 16, 16),
                new AnimationAssist.Bound(409, 348, 16, 16),
                new AnimationAssist.Bound(409, 376, 16, 16)
        ), MiniGameConfig.getScreenSettingFrameDuration(), 0, Animation.PlayMode.LOOP);
    }

    @Override
    protected void updateStatus() {
        trackCircle.update4Time();
        setPosition(
                father.getPosX() + trackCircle.getPosition().x,
                father.getPosY() + trackCircle.getPosition().y);
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
        return GameSpriteCategory.build(MemberFixtureAttribute.NINJA_FIREBALL, MemberFixtureAttribute._NINJA_FIREBALL, MemberName.NINJA_FIREBALL);
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
        return 42;
    }
}
