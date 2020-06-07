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
import com.mini.graph.TrackHolder;
import com.mini.member.GameSprite;
import com.mini.member.GameSpriteCategory;
import com.mini.member.helper.GameSpriteHelper;
import com.mini.member.status.GameSpriteDirection;

import java.util.HashMap;
import java.util.Map;

public class NinjaFlame extends GameSprite {

    // 父精灵
    private GameSprite father;

    // 混合轨迹
    private TrackHolder trackHolder;

    // 轨迹之前的位置
    private MiniPosition positionPre;

    private Map<GameSpriteDirection, Animation> animationMap = new HashMap<>();

    public NinjaFlame(GameSprite father, TrackHolder trackHolder) {
        this.father = father;
        this.trackHolder = trackHolder;
    }

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
    protected void preInit() {
        trackHolder.update(0);
        positionPre = new MiniPosition(trackHolder.getPosition().x, trackHolder.getPosition().y);
    }

    @Override
    protected void initStatus() {
        direction = GameSpriteDirection.R;
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
        // 根据混合轨迹运动
        if ((action & 1 << 0) != 1 << 0) {
            trackHolder.update4Time();
            MiniPosition position = trackHolder.getPosition();
            // 更新方向
            if (!isPrepared()) {
                if (position.x > positionPre.x) {
                    direction = GameSpriteDirection.R;
                }
                if (position.x < positionPre.x) {
                    direction = GameSpriteDirection.L;
                }
            } else {
                direction = father.getDirection();
            }

            if (direction == GameSpriteDirection.R) {
                setPosition(position.x + father.getPosX() - ((getDrawW() + father.getDrawW()) / 2 * MiniGameConfig.getPhysicalSettingViewRate()), position.y + father.getPosY());
            }
            if (direction == GameSpriteDirection.L) {
                setPosition(position.x + father.getPosX() + ((getDrawW() + father.getDrawW()) / 2 * MiniGameConfig.getPhysicalSettingViewRate()), position.y + father.getPosY());
            }

            positionPre.x = position.x;
            positionPre.y = position.y;
        } else {
            if (isAlive && Math.abs(getPosX() - initPosX) >= 7.5) {
                isAlive = false;
            }
        }
    }

    @Override
    protected void updateAnimation() {
        currentAnimation = animationMap.get(direction);
    }

    @Override
    public void renderCustom(SpriteBatch batch, float delta) {
        batch.begin();
        batch.draw(getFrameCurrent(delta), getDrawX(), getDrawY(), getDrawW(), getDrawH());
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

    public boolean isPrepared() {
        return trackHolder.isFinished();
    }

    public void beginAttack() {
        initPosX = getPosX();
        initPosY = getPosY();
        action |= 1 << 0;
    }
}