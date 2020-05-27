package com.custom.member;

import com.alibaba.fastjson.JSONObject;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.*;
import com.custom.member.constant.MemberFixtureAttribute;
import com.custom.member.constant.MemberName;
import com.custom.member.status.ChunLiStatus;
import com.custom.member.weapon.ChunLiQiGongBall;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.mini.animation.MiniAnimation;
import com.mini.animation.MiniAnimationHelper;
import com.mini.animation.MiniAnimationHolder;
import com.mini.assist.AnimationAssist;
import com.mini.assist.MiniAnimationHolderAssist;
import com.mini.game.MiniGame;
import com.mini.game.MiniGameConfig;
import com.mini.member.GameSprite;
import com.mini.member.GameSpriteCategory;
import com.mini.member.MiniUserData;
import com.mini.member.helper.GameSpriteHelper;
import com.mini.member.helper.GameSpriteHolder;
import com.mini.member.status.GameSpriteDirection;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class ChunLi extends Protagonist {

    protected ChunLiStatus status, statusPre;

    protected float maximalVelocityX = 2.0f, maximalVelocityY = 0.1f;

    private Map<ChunLiStatus, FixtureDef> fixtureDefMap = new HashMap<>();

    private Set<ChunLiStatus> variableFixtureDefs = Sets.newHashSet(ChunLiStatus.SQUAT, ChunLiStatus.QI_GONG, ChunLiStatus.QI_GONG_BALL);

//    private Map<ChunLiStatus, Map<GameSpriteDirection, Animation>> animationMap = new HashMap<>();

    private Map<ChunLiStatus, Map<GameSpriteDirection, MiniAnimationHolder>> miniAnimationHolderMap = new HashMap<>();

    private MiniAnimationHolder currentMiniAnimationHolder;

    private MiniAnimationHelper miniAnimationHelper;

    private JSONObject animationTimes = new JSONObject();

    private GameSpriteHolder chunLiQiGongBallHolder;

    @Override
    protected Body createBody(World world, float initX, float initY) {
        GameSpriteHelper.BodyConfig config = new GameSpriteHelper.BodyConfig();
        config.setInitX(initX).setInitY(initY)
                .setBodyType(BodyDef.BodyType.DynamicBody).setWorld(world)
                .setShapeType(Shape.Type.Polygon).setDrawW(getDrawW()).setDrawH(getDrawH()).setCategory(category).setIsSensor(false)
                .setHost(this);
        return GameSpriteHelper.me.createBody(config);
    }

    @Override
    protected void preInit() {
        miniAnimationHelper = new MiniAnimationHelper();
        chunLiQiGongBallHolder = new GameSpriteHolder(world);
    }

    @Override
    protected void initFixtures() {
        initFixtureBox(ChunLiStatus.QUIET, getDrawW() * MiniGameConfig.getPhysicalSettingMemberViewRate(), getDrawH() * MiniGameConfig.getPhysicalSettingMemberViewRate());
        initFixtureBox(ChunLiStatus.WALK, getDrawW() * MiniGameConfig.getPhysicalSettingMemberViewRate(), getDrawH() * MiniGameConfig.getPhysicalSettingMemberViewRate());
        initFixtureBox(ChunLiStatus.JUMP, getDrawW() * MiniGameConfig.getPhysicalSettingMemberViewRate(), getDrawH() * MiniGameConfig.getPhysicalSettingMemberViewRate());
        initFixtureBox(ChunLiStatus.SQUAT, getDrawW() * MiniGameConfig.getPhysicalSettingMemberViewRate(), getDrawH() * MiniGameConfig.getPhysicalSettingMemberViewRate() * 0.85f);
        initFixtureBox(ChunLiStatus.CRACKED_FEET, getDrawW() * MiniGameConfig.getPhysicalSettingMemberViewRate(), getDrawH() * MiniGameConfig.getPhysicalSettingMemberViewRate());
        initFixtureBox(ChunLiStatus.QI_GONG, getDrawW() * MiniGameConfig.getPhysicalSettingMemberViewRate() * 1.4f, getDrawH() * MiniGameConfig.getPhysicalSettingMemberViewRate());
        initFixtureBox(ChunLiStatus.QI_GONG_BALL, getDrawW() * MiniGameConfig.getPhysicalSettingMemberViewRate() * 1.4f, getDrawH() * MiniGameConfig.getPhysicalSettingMemberViewRate());
    }

    protected void initFixtureBox(ChunLiStatus st, float boxW, float boxH) {
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(boxW, boxH);
        FixtureDef fixtureDef = GameSpriteHelper.me.createFixtureDef(shape, category.bits, category.maskBits, false);
        fixtureDefMap.put(st, fixtureDef);
    }

    @Deprecated
    @Override
    protected void initStatus() {
        direction = GameSpriteDirection.R;

        status = ChunLiStatus.QUIET;
        statusPre = status;
    }

    @Override
    protected void initAnimation() {
        initAnimationQuiet();
        initAnimationWalk();
        initAnimationJump();
        initAnimationLandfall();
        initAnimationSquat();
        initAnimationCrackedFeet();
        initAnimationQiGong();
        initAnimationQiGongBall();
    }

    private void initAnimationQuiet() {
        Texture texture = MiniGame.assetManager.get("members/chunli8.png");
        insertAnimation(ChunLiStatus.QUIET, GameSpriteDirection.R,
                AnimationAssist.createAnimation(texture, texture.getWidth() / 4, texture.getHeight() / 3, 10, MiniGameConfig.getScreenSettingFrameDuration(), 0, Animation.PlayMode.LOOP));
        insertAnimation(ChunLiStatus.QUIET, GameSpriteDirection.L,
                AnimationAssist.createAnimation(texture, texture.getWidth() / 4, texture.getHeight() / 3, 10, MiniGameConfig.getScreenSettingFrameDuration(), 1, Animation.PlayMode.LOOP));
    }

    private void initAnimationWalk() {
        Texture texture = MiniGame.assetManager.get("members/chunli7.png");
        int perW = texture.getWidth() / 4;
        int perH = texture.getHeight() / 3;
        insertAnimation(ChunLiStatus.WALK, GameSpriteDirection.R, AnimationAssist.createAnimation(texture, Lists.newArrayList(
                new AnimationAssist.Bound(perW * 0, perH * 0, perW, perH),
                new AnimationAssist.Bound(perW * 1, perH * 0, perW, perH),
                new AnimationAssist.Bound(perW * 2, perH * 0, perW, perH),
                new AnimationAssist.Bound(perW * 0, perH * 1, perW, perH),
                new AnimationAssist.Bound(perW * 1, perH * 1, perW, perH),
                new AnimationAssist.Bound(perW * 2, perH * 1, perW, perH),
                new AnimationAssist.Bound(perW * 0, perH * 2, perW, perH),
                new AnimationAssist.Bound(perW * 1, perH * 2, perW, perH),
                new AnimationAssist.Bound(perW * 2, perH * 2, perW, perH),
                new AnimationAssist.Bound(perW * 3, perH * 2, perW, perH)
        ), MiniGameConfig.getScreenSettingFrameDuration(), 0, Animation.PlayMode.LOOP));
        insertAnimation(ChunLiStatus.WALK, GameSpriteDirection.L, AnimationAssist.createAnimation(texture, Lists.newArrayList(
                new AnimationAssist.Bound(perW * 0, perH * 0, perW, perH),
                new AnimationAssist.Bound(perW * 1, perH * 0, perW, perH),
                new AnimationAssist.Bound(perW * 2, perH * 0, perW, perH),
                new AnimationAssist.Bound(perW * 0, perH * 1, perW, perH),
                new AnimationAssist.Bound(perW * 1, perH * 1, perW, perH),
                new AnimationAssist.Bound(perW * 2, perH * 1, perW, perH),
                new AnimationAssist.Bound(perW * 0, perH * 2, perW, perH),
                new AnimationAssist.Bound(perW * 1, perH * 2, perW, perH),
                new AnimationAssist.Bound(perW * 2, perH * 2, perW, perH),
                new AnimationAssist.Bound(perW * 3, perH * 2, perW, perH)
        ), MiniGameConfig.getScreenSettingFrameDuration(), 1, Animation.PlayMode.LOOP));
//        insertAnimation(ChunLiStatus.WALK, GameSpriteDirection.R,
//                AnimationAssist.createAnimation(texture, texture.getWidth() / 4, texture.getHeight() / 3, 10, MiniGameConfig.getScreenSettingFrameDuration(), 0, Animation.PlayMode.LOOP));
//        insertAnimation(ChunLiStatus.WALK, GameSpriteDirection.L,
//                AnimationAssist.createAnimation(texture, texture.getWidth() / 4, texture.getHeight() / 3, 10, MiniGameConfig.getScreenSettingFrameDuration(), 1, Animation.PlayMode.LOOP));
    }

    private void initAnimationJump() {
        Texture texture = MiniGame.assetManager.get("members/chunli1.png");
        insertAnimation(ChunLiStatus.JUMP, GameSpriteDirection.R,
                AnimationAssist.createAnimation(texture, texture.getWidth() / 4, texture.getHeight() / 2, 8, MiniGameConfig.getScreenSettingFrameDuration(), 0, Animation.PlayMode.LOOP));
        insertAnimation(ChunLiStatus.JUMP, GameSpriteDirection.L,
                AnimationAssist.createAnimation(texture, texture.getWidth() / 4, texture.getHeight() / 2, 8, MiniGameConfig.getScreenSettingFrameDuration(), 0, Animation.PlayMode.LOOP));
    }

    private void initAnimationLandfall() {
        Texture texture = MiniGame.assetManager.get("members/chunli9.png");
        insertAnimation(ChunLiStatus.LANDFALL, GameSpriteDirection.R,
                AnimationAssist.createAnimation(texture, texture.getWidth() / 3, texture.getHeight() / 3, 8, MiniGameConfig.getScreenSettingFrameDuration(), 1, Animation.PlayMode.LOOP));
        insertAnimation(ChunLiStatus.LANDFALL, GameSpriteDirection.L,
                AnimationAssist.createAnimation(texture, texture.getWidth() / 3, texture.getHeight() / 3, 8, MiniGameConfig.getScreenSettingFrameDuration(), 0, Animation.PlayMode.LOOP));
    }

    private void initAnimationSquat() {
        Texture texture = MiniGame.assetManager.get("members/chunli8.png");
        insertAnimation(ChunLiStatus.SQUAT, GameSpriteDirection.R,
                AnimationAssist.createAnimation(texture, Lists.newArrayList(new AnimationAssist.Bound(230, 250, 124, 104)), MiniGameConfig.getScreenSettingFrameDuration(), 1, Animation.PlayMode.LOOP));
        insertAnimation(ChunLiStatus.SQUAT, GameSpriteDirection.L,
                AnimationAssist.createAnimation(texture, Lists.newArrayList(new AnimationAssist.Bound(230, 250, 124, 104)), MiniGameConfig.getScreenSettingFrameDuration(), 0, Animation.PlayMode.LOOP));
    }

    private void initAnimationCrackedFeet() {
        Texture texture = MiniGame.assetManager.get("members/chunli4.png");
        insertAnimation(ChunLiStatus.CRACKED_FEET, GameSpriteDirection.R,
                AnimationAssist.createAnimation(texture, texture.getWidth() / 2, texture.getHeight() / 4, 8, MiniGameConfig.getScreenSettingFrameDuration(), 0, null));
        insertAnimation(ChunLiStatus.CRACKED_FEET, GameSpriteDirection.L,
                AnimationAssist.createAnimation(texture, texture.getWidth() / 2, texture.getHeight() / 4, 8, MiniGameConfig.getScreenSettingFrameDuration(), 1, null));
    }

    private void initAnimationQiGong() {
        Texture texture = MiniGame.assetManager.get("members/chunli6.png");
        insertAnimation(ChunLiStatus.QI_GONG, GameSpriteDirection.R,
                AnimationAssist.createAnimation(texture, texture.getWidth() / 3, texture.getHeight() / 6, 13, MiniGameConfig.getScreenSettingFrameDuration(), 0, null));
        insertAnimation(ChunLiStatus.QI_GONG, GameSpriteDirection.L,
                AnimationAssist.createAnimation(texture, texture.getWidth() / 3, texture.getHeight() / 6, 13, MiniGameConfig.getScreenSettingFrameDuration(), 1, null));
    }

    private void initAnimationQiGongBall() {
        Texture texture = MiniGame.assetManager.get("members/chunli5.png");
        insertAnimation(ChunLiStatus.QI_GONG_BALL, GameSpriteDirection.R,
                AnimationAssist.createAnimation(texture, texture.getWidth() / 3, texture.getHeight() / 5, 15, MiniGameConfig.getScreenSettingFrameDuration(), 0, null));
        insertAnimation(ChunLiStatus.QI_GONG_BALL, GameSpriteDirection.L,
                AnimationAssist.createAnimation(texture, texture.getWidth() / 3, texture.getHeight() / 5, 15, MiniGameConfig.getScreenSettingFrameDuration(), 1, null));
    }

    private void insertAnimation(ChunLiStatus chunLiStatus, GameSpriteDirection gameSpriteDirection, Animation animation) {
//        Map<GameSpriteDirection, Animation> directionMap = animationMap.get(chunLiStatus);
//        if (directionMap == null) {
//            directionMap = new HashMap<>();
//        }
//        directionMap.put(gameSpriteDirection, animation);
//
//        animationMap.put(chunLiStatus, directionMap);

        insertMiniAnimationHolder(chunLiStatus, gameSpriteDirection, MiniAnimationHolderAssist.createMiniAnimationHolder(new MiniAnimation(animation, getDrawW(), getDrawH())));
    }

    private void insertMiniAnimationHolder(ChunLiStatus chunLiStatus, GameSpriteDirection gameSpriteDirection, MiniAnimationHolder miniAnimationHolder) {
        Map<GameSpriteDirection, MiniAnimationHolder> directionMap = miniAnimationHolderMap.get(chunLiStatus);
        if (directionMap == null) {
            directionMap = new HashMap<>();
        }
        directionMap.put(gameSpriteDirection, miniAnimationHolder);

        miniAnimationHolderMap.put(chunLiStatus, directionMap);
    }

    @Override
    protected void initCustom() {

    }

    @Override
    protected void update() {
        if (!needUpdate()) {
            return;
        }

        updateFixtures();
        updateStatus();
        updateAnimation();
        updateCustom();
    }

    @Override
    public void updateFixtures() {
        if (status != statusPre) {
            if (variableFixtureDefs.contains(status) || variableFixtureDefs.contains(statusPre)) {
                Fixture fixture = body.getFixtureList().first();
                body.destroyFixture(fixture);
                fixture = body.createFixture(fixtureDefMap.get(statusPre));

                MiniUserData data = new MiniUserData();
                data.name = category.name;
                data.body = body;
                data.fixture = fixture;
                data.mine = this;

                fixture.setUserData(data);
            }
        }
    }

    @Override
    protected void updateStatus() {
        // 生命值更新
        if (isAlive && body.getFixtureList().size == 0) {
            isAlive = false;
        }

        // 矫正速度
        float velocityX = getVelocityX();
        float velocityY = getVelocityY();
        if (velocityX > maximalVelocityX) {
            body.setLinearVelocity(maximalVelocityX, velocityY);
            velocityX = getVelocityX();
        }
        if (velocityX < -maximalVelocityX) {
            body.setLinearVelocity(-maximalVelocityX, velocityY);
            velocityX = getVelocityX();
        }

        if (status == ChunLiStatus.CRACKED_FEET || status == ChunLiStatus.QI_GONG || status == ChunLiStatus.QI_GONG_BALL) {
            Float animationTime = animationTimes.getFloat(status.name());
            animationTime = animationTime == null ? Gdx.graphics.getDeltaTime() : Gdx.graphics.getDeltaTime() + animationTime;
//            if (currentAnimation.isAnimationFinished(animationTime)) {
            if (currentMiniAnimationHolder.getMiniAnimation().getAnimation().isAnimationFinished(animationTime)) {
                if (status == ChunLiStatus.CRACKED_FEET) {
                    statusPre = ChunLiStatus.QUIET;
                    action = 0;
                }
                if (status == ChunLiStatus.QI_GONG) {
                    statusPre = ChunLiStatus.QI_GONG_BALL;
                }
                if (status == ChunLiStatus.QI_GONG_BALL) {
                    statusPre = ChunLiStatus.QUIET;
                    action = 0;
                    chunLiQiGongBallHolder.pushCreateTask(new GameSpriteHolder.CreateTask() {

                        private GameSprite gameSprite;

                        @Override
                        public GameSprite getGameSprite() {
                            gameSprite = new ChunLiQiGongBall();
                            return gameSprite;
                        }

                        @Override
                        public float getInitX() {
                            if (direction == GameSpriteDirection.L) {
                                return getDrawX() - gameSprite.getDrawR();
                            }
                            return getDrawX() + getDrawW() + gameSprite.getDrawR();
                        }

                        @Override
                        public float getInitY() {
                            return getDrawY() + gameSprite.getDrawR() * 0.5f;
                        }

                        @Override
                        public GameSpriteHolder.CreateAction getCreateAction() {
                            return (gameSprite -> {
                                gameSprite.applyForceToCenter(360 * (direction == GameSpriteDirection.L ? -1 : 1), 0);
                            });
                        }
                    });
                }
                animationTime = 0f;
            }
            animationTimes.put(status.name(), animationTime);
        }

        status = statusPre;

        // 纠正状态
        if (action == 0 && status != ChunLiStatus.SQUAT) {
            if (velocityX == 0 && velocityY == 0) {
                status = ChunLiStatus.QUIET;
            } else if (velocityY == 0) {
                status = ChunLiStatus.WALK;
            } else {
                if (velocityY > 0) {
                    status = ChunLiStatus.JUMP;
                }
                if (velocityY < 0) {
                    status = ChunLiStatus.LANDFALL;
                }
            }
        }
    }

    @Override
    protected void updateAnimation() {
//        currentAnimation = animationMap.get(status).get(direction);
        currentMiniAnimationHolder = miniAnimationHolderMap.get(status).get(direction);
    }

    @Override
    protected void renderCustom(SpriteBatch batch, float delta) {
//        batch.begin();
        Float animationTime = animationTimes.getFloat(status.name());
        if (animationTime == null || animationTime == 0) {
            animationTime = delta;
        }
        miniAnimationHelper.draw(batch, delta, currentMiniAnimationHolder, getDrawX(), getDrawY());
//        batch.draw(getFrameCurrent(animationTime), getDrawX(), getDrawY(), getDrawW(), getDrawH());
//        batch.end();

//        System.out.println("render");
        chunLiQiGongBallHolder.render(batch, delta);
    }

    @Override
    public void createBomb() {
        statusPre = ChunLiStatus.QI_GONG;
        action = 1L << 0;
    }

    @Override
    public void createMarioBullet() {
        statusPre = ChunLiStatus.CRACKED_FEET;
        action = 1L << 0;
    }

    @Override
    public float getPosX() {
        return body.getPosition().x;
    }

    @Override
    public float getPosY() {
        return body.getPosition().y;
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
        return 47;
    }

    @Override
    public float getDrawH() {
        return getDrawW() * 1.7f;
    }

    @Override
    public void applyForceToCenter(float forceX, float forceY) {
        body.applyForceToCenter(forceX, forceY, true);
    }

    @Override
    public GameSpriteCategory getGameSpriteCategory() {
        if (category != null) {
            return category;
        }
        return GameSpriteCategory.build(MemberFixtureAttribute.CHUNLI, MemberFixtureAttribute._CHUNLI, MemberName.CHUNLI);
    }

    public void setStatusPre(ChunLiStatus statusPre) {
        this.statusPre = statusPre;
    }
}
