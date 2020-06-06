package com.custom.member;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.custom.member.constant.MemberFixtureAttribute;
import com.custom.member.constant.MemberName;
import com.custom.member.status.ChunLiStatus;
import com.custom.member.status.ProtagonistStatus;
import com.custom.member.weapon.MarioBullet;
import com.custom.member.weapon.NinjaFireball;
import com.custom.member.weapon.NinjaFlame;
import com.mini.game.MiniGame;
import com.mini.game.MiniGameConfig;
import com.mini.graph.MiniPosition;
import com.mini.graph.TrackHolder;
import com.mini.graph.track.TrackLine;
import com.mini.member.GameSprite;
import com.mini.member.GameSpriteCategory;
import com.mini.member.MiniUserData;
import com.mini.member.helper.GameSpriteHolder;
import com.mini.member.status.GameSpriteDirection;

import java.util.HashMap;
import java.util.Map;

public class Protagonist extends GameSprite {

    private ProtagonistStatus status, statusPre;

    private float maximalVelocityX = 2.0f, maximalVelocityY = 0.1f;

    private Map<ProtagonistStatus, FixtureDef> fixtureDefMap = new HashMap<>();

    private Map<ProtagonistStatus, Map<GameSpriteDirection, Animation>> animationMap = new HashMap<>();

    private GameSpriteHolder marioBulletHolder;

    private Protagonist chunLi;

    private Protagonist proxy;

    @Override
    protected Body createBody(World world, float initX, float initY) {
        // 定义刚体
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(initX * MiniGameConfig.getPhysicalSettingViewRate(), initY * MiniGameConfig.getPhysicalSettingViewRate());
        bodyDef.type = BodyType.DynamicBody;

        // 创建刚体
        Body body = world.createBody(bodyDef);

        // 定义图形
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(getDrawW() * MiniGameConfig.getPhysicalSettingMemberViewRate(), getDrawH() * MiniGameConfig.getPhysicalSettingMemberViewRate());

        // 定义夹具
        FixtureDef fixTureDef = new FixtureDef();
        fixTureDef.shape = shape;
        fixTureDef.filter.categoryBits = category.bits;
        fixTureDef.filter.maskBits = category.maskBits;
        fixTureDef.isSensor = false;
        // 初始化夹具集合
        fixtureDefMap.put(ProtagonistStatus.QUIET, fixTureDef);
        fixtureDefMap.put(ProtagonistStatus.WALK, fixTureDef);
        fixtureDefMap.put(ProtagonistStatus.JUMP, fixTureDef);

        // 创建夹具
        Fixture fixture = body.createFixture(fixTureDef);

        // 配置属性
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
    protected void preInit() {
        marioBulletHolder = new GameSpriteHolder(world);
        marioBulletHolder.setUpdateExecutor(gameSprite -> {
            if (!gameSprite.isAlive) {
                gameSprite.destroyFixture();
                marioBulletHolder.pushDestroyBodyTask(() -> gameSprite);
                marioBulletHolder.removeGameSprite(gameSprite);
            }
        });
    }

    @Override
    protected void initStatus() {
        direction = GameSpriteDirection.R;

        status = ProtagonistStatus.QUIET;
        setStatusPre(status);
    }

    @Override
    protected void initFixtures() {
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(getDrawW() * MiniGameConfig.getPhysicalSettingMemberViewRate(), getDrawH() * MiniGameConfig.getPhysicalSettingMemberViewRate() * 0.7f);

        FixtureDef fixTureDef = new FixtureDef();
        fixTureDef.shape = shape;
        fixTureDef.filter.categoryBits = category.bits;
        fixTureDef.filter.maskBits = category.maskBits;
        fixTureDef.isSensor = false;
        // 初始化夹具集合
        fixtureDefMap.put(ProtagonistStatus.SQUAT, fixTureDef);
    }

    @Override
    protected void initAnimation() {
        Texture texture = MiniGame.assetManager.get("members/mario1.png", Texture.class);

        // 初始化右走动画
        TextureRegion[] regions = new TextureRegion[3];
        regions[0] = new TextureRegion(texture, 8, 80, 15, 30);
        regions[1] = new TextureRegion(texture, 48, 80, 15, 30);
        regions[2] = new TextureRegion(texture, 88, 80, 15, 30);
        Animation animation = new Animation(MiniGameConfig.getScreenSettingFrameDuration(), regions);
        animation.setPlayMode(Animation.PlayMode.LOOP);
        insertAnimation(ProtagonistStatus.WALK, GameSpriteDirection.R, animation);
        // 初始化左走动画
        TextureRegion[] regions2 = new TextureRegion[3];
        for (int i = 0; i < 3; i++) {
            regions2[i] = new TextureRegion();
            regions2[i].setRegion(regions[i]);
            regions2[i].flip(true, false);
        }
        animation = new Animation(MiniGameConfig.getScreenSettingFrameDuration(), regions2);
        animation.setPlayMode(Animation.PlayMode.LOOP);
        insertAnimation(ProtagonistStatus.WALK, GameSpriteDirection.L, animation);

        // 右站立
        animation = new Animation(MiniGameConfig.getScreenSettingFrameDuration(), regions[0]);
        insertAnimation(ProtagonistStatus.QUIET, GameSpriteDirection.R, animation);
        // 左站立
        animation = new Animation(MiniGameConfig.getScreenSettingFrameDuration(), regions2[0]);
        insertAnimation(ProtagonistStatus.QUIET, GameSpriteDirection.L, animation);

        // 右跳，左跳
        TextureRegion region = new TextureRegion(texture, 130, 80, 15, 30);
        animation = new Animation(MiniGameConfig.getScreenSettingFrameDuration(), region);
        insertAnimation(ProtagonistStatus.JUMP, GameSpriteDirection.R, animation);
        region = new TextureRegion(region);
        region.flip(true, false);
        animation = new Animation(MiniGameConfig.getScreenSettingFrameDuration(), region);
        insertAnimation(ProtagonistStatus.JUMP, GameSpriteDirection.L, animation);

        // 右蹲，左蹲
        region = new TextureRegion(texture, 130, 120, 15, 30);
        animation = new Animation(MiniGameConfig.getScreenSettingFrameDuration(), region);
        insertAnimation(ProtagonistStatus.SQUAT, GameSpriteDirection.R, animation);
        region = new TextureRegion(region);
        region.flip(true, false);
        animation = new Animation(MiniGameConfig.getScreenSettingFrameDuration(), region);
        insertAnimation(ProtagonistStatus.SQUAT, GameSpriteDirection.L, animation);

        // 打坐
        region = new TextureRegion(texture, 88, 120, 15, 30);
        animation = new Animation(MiniGameConfig.getScreenSettingFrameDuration(), region);
        insertAnimation(ProtagonistStatus.SIT, GameSpriteDirection.F, animation);
    }

    private void insertAnimation(ProtagonistStatus protagonistStatus, GameSpriteDirection gameSpriteDirection, Animation animation) {
        Map<GameSpriteDirection, Animation> directionMap = animationMap.get(protagonistStatus);
        if (directionMap == null) {
            directionMap = new HashMap<>();
        }
        directionMap.put(gameSpriteDirection, animation);

        animationMap.put(protagonistStatus, directionMap);
    }

    @Override
    protected void initCustom() {
        chunLi = new ChunLi();
        chunLi.create(world, initPosX * MiniGameConfig.getScreenSettingViewRate(), initPosY * MiniGameConfig.getScreenSettingViewRate());
    }

    @Override
    protected void update() {
        if (proxy != null) {
            proxy.update();
            return;
        }
        super.update();
    }

    @Override
    protected void updateStatus() {
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

        // 下蹲不做判断
        if (statusPre != ProtagonistStatus.SQUAT) {
            if (velocityX == 0 && velocityY == 0) {
                setStatusPre(ProtagonistStatus.QUIET);
            } else if (velocityX != 0 && velocityY == 0) {
                setStatusPre(ProtagonistStatus.WALK);
            } else if (velocityX == 0 && velocityY != 0) {
                setStatusPre(ProtagonistStatus.JUMP);
            } else if (velocityX != 0 && velocityY != 0) {
                setStatusPre(ProtagonistStatus.JUMP);
            }
        }
    }

    @Override
    public void updateFixtures() {
        if (status != statusPre) {
            if (status == ProtagonistStatus.SQUAT || statusPre == ProtagonistStatus.SQUAT) {
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
    protected void updateAnimation() {
        status = statusPre;
        currentAnimation = animationMap.get(status).get(direction);
    }

    @Override
    protected void renderCustom(SpriteBatch batch, float delta) {
        if (proxy != null) {
            proxy.renderCustom(batch, delta);
            return;
        }

        batch.begin();
        batch.draw(getFrameCurrent(delta), getDrawX(), getDrawY(), getDrawW(), getDrawH());
        batch.end();

        marioBulletHolder.render(batch, delta);
    }

    public void createBomb() {
        if (proxy != null) {
            proxy.createBomb();
            return;
        }

        marioBulletHolder.pushCreateTask(new GameSpriteHolder.CreateTask() {
            @Override
            public GameSprite getGameSprite() {
                return new NinjaFireball(Protagonist.this, new MiniPosition(0, 0), new MiniPosition(1, 0), 1200);
            }

            @Override
            public float getInitX(GameSprite gameSprite) {
                return getPosX() * MiniGameConfig.getScreenSettingViewRate();
            }

            @Override
            public float getInitY(GameSprite gameSprite) {
                return getPosY() * MiniGameConfig.getScreenSettingViewRate();
            }
        });

        marioBulletHolder.pushCreateTask(new GameSpriteHolder.CreateTask() {
            @Override
            public GameSprite getGameSprite() {
                return new NinjaFireball(Protagonist.this, new MiniPosition(0, 0), new MiniPosition(0, 1), 1200);
            }

            @Override
            public float getInitX(GameSprite gameSprite) {
                return getPosX() * MiniGameConfig.getScreenSettingViewRate();
            }

            @Override
            public float getInitY(GameSprite gameSprite) {
                return getPosY() * MiniGameConfig.getScreenSettingViewRate();
            }
        });

        marioBulletHolder.pushCreateTask(new GameSpriteHolder.CreateTask() {
            @Override
            public GameSprite getGameSprite() {
                return new NinjaFireball(Protagonist.this, new MiniPosition(0, 0), new MiniPosition(-1, 0), 1200);
            }

            @Override
            public float getInitX(GameSprite gameSprite) {
                return getPosX() * MiniGameConfig.getScreenSettingViewRate();
            }

            @Override
            public float getInitY(GameSprite gameSprite) {
                return getPosY() * MiniGameConfig.getScreenSettingViewRate();
            }
        });

        marioBulletHolder.pushCreateTask(new GameSpriteHolder.CreateTask() {
            @Override
            public GameSprite getGameSprite() {
                return new NinjaFireball(Protagonist.this, new MiniPosition(0, 0), new MiniPosition(0, -1), 1200);
            }

            @Override
            public float getInitX(GameSprite gameSprite) {
                return getPosX() * MiniGameConfig.getScreenSettingViewRate();
            }

            @Override
            public float getInitY(GameSprite gameSprite) {
                return getPosY() * MiniGameConfig.getScreenSettingViewRate();
            }
        });
    }

    public void createMarioBullet() {
        if (proxy != null) {
            proxy.createMarioBullet();
            return;
        }

        marioBulletHolder.pushCreateTask(new GameSpriteHolder.CreateTask() {
            @Override
            public GameSprite getGameSprite() {
                return new MarioBullet();
            }

            @Override
            public float getInitX(GameSprite gameSprite) {
                return getPosX() * MiniGameConfig.getScreenSettingViewRate();
            }

            @Override
            public float getInitY(GameSprite gameSprite) {
                return getDrawY() + getDrawH() * 0.75f;
            }

            @Override
            public GameSpriteHolder.CreateAction getCreateAction(GameSprite gameSprite) {
                return (gs) -> {
                    gs.applyForceToCenter(640 * (direction == GameSpriteDirection.L ? -1 : 1), -320);
                };
            }
        });
    }

    public void createGrenade() {
        if (proxy != null) {
            return;
        }

        marioBulletHolder.pushCreateTask(new GameSpriteHolder.CreateTask() {
            @Override
            public GameSprite getGameSprite() {
                return new NinjaFlame(Protagonist.this, new TrackHolder()
                        .putTrack(new TrackLine(new MiniPosition(1, 0), 1000, new MiniPosition(-2, 3)))
                        .putTrack(new TrackLine(new MiniPosition(1, -3), 500))
                        .reset());
            }

            @Override
            public float getInitX(GameSprite gameSprite) {
                return getPosX() * MiniGameConfig.getScreenSettingViewRate();
            }

            @Override
            public float getInitY(GameSprite gameSprite) {
                return getPosY() * MiniGameConfig.getScreenSettingViewRate();
            }
        });
        marioBulletHolder.pushCreateTask(new GameSpriteHolder.CreateTask() {
            @Override
            public GameSprite getGameSprite() {
                return new NinjaFlame(Protagonist.this, new TrackHolder()
                        .putTrack(new TrackLine(new MiniPosition(1, 0), 1000, new MiniPosition(-2, 2)))
                        .putTrack(new TrackLine(new MiniPosition(1, -2), 500))
                        .reset());
            }

            @Override
            public float getInitX(GameSprite gameSprite) {
                return getPosX() * MiniGameConfig.getScreenSettingViewRate();
            }

            @Override
            public float getInitY(GameSprite gameSprite) {
                return getPosY() * MiniGameConfig.getScreenSettingViewRate();
            }
        });
        marioBulletHolder.pushCreateTask(new GameSpriteHolder.CreateTask() {
            @Override
            public GameSprite getGameSprite() {
                return new NinjaFlame(Protagonist.this, new TrackHolder()
                        .putTrack(new TrackLine(new MiniPosition(1, 0), 1000, new MiniPosition(-2, 1)))
                        .putTrack(new TrackLine(new MiniPosition(1, -1), 500))
                        .reset());
            }

            @Override
            public float getInitX(GameSprite gameSprite) {
                return getPosX() * MiniGameConfig.getScreenSettingViewRate();
            }

            @Override
            public float getInitY(GameSprite gameSprite) {
                return getPosY() * MiniGameConfig.getScreenSettingViewRate();
            }
        });
        marioBulletHolder.pushCreateTask(new GameSpriteHolder.CreateTask() {
            @Override
            public GameSprite getGameSprite() {
                return new NinjaFlame(Protagonist.this, new TrackHolder()
                        .putTrack(new TrackLine(new MiniPosition(1, 0), 1000, new MiniPosition(-2, -1)))
                        .putTrack(new TrackLine(new MiniPosition(1, 1), 500))
                        .reset());
            }

            @Override
            public float getInitX(GameSprite gameSprite) {
                return getPosX() * MiniGameConfig.getScreenSettingViewRate();
            }

            @Override
            public float getInitY(GameSprite gameSprite) {
                return getPosY() * MiniGameConfig.getScreenSettingViewRate();
            }
        });
        marioBulletHolder.pushCreateTask(new GameSpriteHolder.CreateTask() {
            @Override
            public GameSprite getGameSprite() {
                return new NinjaFlame(Protagonist.this, new TrackHolder()
                        .putTrack(new TrackLine(new MiniPosition(1, 0), 1000, new MiniPosition(-2, -2)))
                        .putTrack(new TrackLine(new MiniPosition(1, 2), 500))
                        .reset());
            }

            @Override
            public float getInitX(GameSprite gameSprite) {
                return getPosX() * MiniGameConfig.getScreenSettingViewRate();
            }

            @Override
            public float getInitY(GameSprite gameSprite) {
                return getPosY() * MiniGameConfig.getScreenSettingViewRate();
            }
        });
        marioBulletHolder.pushCreateTask(new GameSpriteHolder.CreateTask() {
            @Override
            public GameSprite getGameSprite() {
                return new NinjaFlame(Protagonist.this, new TrackHolder()
                        .putTrack(new TrackLine(new MiniPosition(1, 0), 1000, new MiniPosition(-2, -3)))
                        .putTrack(new TrackLine(new MiniPosition(1, 3), 500))
                        .reset());
            }

            @Override
            public float getInitX(GameSprite gameSprite) {
                return getPosX() * MiniGameConfig.getScreenSettingViewRate();
            }

            @Override
            public float getInitY(GameSprite gameSprite) {
                return getPosY() * MiniGameConfig.getScreenSettingViewRate();
            }
        });
    }

    @Override
    public float getPosX() {
        if (proxy != null) {
            return proxy.getPosX();
        }
        return super.getPosX();
    }

    @Override
    public float getPosY() {
        if (proxy != null) {
            return proxy.getPosY();
        }
        return super.getPosY();
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
        return 30;
    }

    @Override
    public float getDrawH() {
        return getDrawW() * 2;
    }

    @Override
    public void applyForceToCenter(float forceX, float forceY) {
        if (proxy != null) {
            proxy.applyForceToCenter(forceX, forceY);
            return;
        }
        super.applyForceToCenter(forceX, forceY);
    }

    @Override
    public GameSpriteCategory getGameSpriteCategory() {
        if (category != null) {
            return category;
        }
        return GameSpriteCategory.build(MemberFixtureAttribute.PROTA, MemberFixtureAttribute._PROTA, MemberName.PROTA);
    }

    @Override
    public GameSpriteDirection getDirection() {
        if (proxy != null) {
            return proxy.getDirection();
        }
        return super.getDirection();
    }

    @Override
    public void setDirection(GameSpriteDirection direction) {
        // 同代理一起变更
        super.setDirection(direction);

        if (proxy != null) {
            proxy.setDirection(direction);
        }
    }

    public void setStatusPre(ProtagonistStatus statusPre) {
        // 同代理一起变更
        this.statusPre = statusPre;

        if (proxy != null) {
            switch (statusPre) {
                case QUIET:
                    if (proxy instanceof ChunLi) {
                        ((ChunLi) proxy).setStatusPre(ChunLiStatus.QUIET);
                    }
                    break;
                case SQUAT:
                    if (proxy instanceof ChunLi) {
                        ((ChunLi) proxy).setStatusPre(ChunLiStatus.SQUAT);
                    }
                    break;
            }
            // 同步当前状态
            this.status = this.statusPre;
        }
    }

    // 切换
    public void cut() {
        if (proxy == null) {
            mintMark(this, chunLi);
            proxy = chunLi;
        } else {
            mintMark(chunLi, this);
            proxy = null;
        }
    }

    // 属性刻印
    public void mintMark(GameSprite source, GameSprite origin) {
        origin.setPosition(source.getPosX(), source.getPosY());
        origin.setDirection(source.getDirection());
    }
}