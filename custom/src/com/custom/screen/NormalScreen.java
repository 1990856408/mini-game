package com.custom.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.custom.constant.CustomGameAttributeNames;
import com.custom.member.Protagonist;
import com.custom.member.constant.MemberFixtureAttribute;
import com.custom.member.constant.MemberName;
import com.custom.member.monster.Diamond;
import com.custom.member.monster.Duck;
import com.custom.member.status.ProtagonistStatus;
import com.custom.member.weapon.MarioBullet;
import com.custom.screen.stage.NormalScreenStage;
import com.mini.game.MiniGame;
import com.mini.game.MiniGameConfig;
import com.mini.handler.BaseContactHandler;
import com.mini.handler.MiniContactReactionBuilder;
import com.mini.member.GameSprite;
import com.mini.member.GameSpriteCategory;
import com.mini.member.MiniUserData;
import com.mini.member.status.GameSpriteDirection;
import com.mini.screen.BaseScreen;
import com.mini.screen.stage.BaseStage;

import java.util.Arrays;
import java.util.List;
import java.util.Vector;

public class NormalScreen extends BaseScreen {

    public int maxScore = MiniGame.getAttributeInt(CustomGameAttributeNames.CURRENT_LEVEL) * 64;
    public int curScore = 0;
    // 游戏主角
    public Protagonist prota;
    // TODO
    boolean debug = false;
    /*
     * 图形相机此时的左下角视距的位置与显示的范围,在updateCamera()中更新，
     * 这个用来决定某些音效何时播放，用来决定某个角色是否渲染也非常有效，
     * 可以很好的降低时间复杂度
     */
    private float cameraPosX, cameraPosY;
    // 物理世界
    private World world;
    // 物理世界渲染器
    private Box2DDebugRenderer box2DRender;
    // 物理世界相机
    private OrthographicCamera box2DCamera;
    // 自定义的物理世界监听器
    private BaseContactHandler contactHandler;
    // 地图
    private TiledMap tileMap;
    // 地图单元大小
    private float tileMapUnitSize;
    // 地图横纵图块个数
    private float mapCountW, mapCountH;
    // 地图渲染器，正交投影渲染器
    private OrthogonalTiledMapRenderer mapRender;
    // 钻石,怪鸭
    private Vector<GameSprite> diamonds, ducks;
    // 舞台
    private NormalScreenStage stage;

    public NormalScreen(MiniGame miniGame) {
        super(miniGame);

        world = new World(new Vector2(0f, -MiniGameConfig.getPhysicalSettingGravity()), false);
        box2DRender = new Box2DDebugRenderer();
        box2DCamera = new OrthographicCamera();
        box2DCamera.setToOrtho(false, MiniGameConfig.getPhysicalSettingViewW(), MiniGameConfig.getPhysicalSettingViewH());
        contactHandler = new BaseContactHandler() {
        };
        MiniContactReactionBuilder builder = new MiniContactReactionBuilder();
        builder.load(MarioBullet.class);
        contactHandler.configMiniContactReactions(builder.build());
        world.setContactListener(contactHandler);
    }


    @Override
    protected void initStages() {
        stage = new NormalScreenStage(this);
        stage.create();
        stage.setKeyAct(Input.Keys.S, new BaseStage.KeyAct() {
            @Override
            public void keyDown() {

            }

            @Override
            public void keyUp() {
                prota.setStatusPre(ProtagonistStatus.QUIET);
            }
        });

        // 使舞台获得输入焦点
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    protected void initMap() {
        tileMap = new TmxMapLoader().load("maps/game_screen" + MiniGame.getAttributeInt(CustomGameAttributeNames.CURRENT_LEVEL) + ".tmx");

        tileMapUnitSize = tileMap.getProperties().get("tilewidth", Integer.class);
        mapCountW = tileMap.getProperties().get("width", Integer.class);
        mapCountH = tileMap.getProperties().get("height", Integer.class);

        createMapFloor((TiledMapTileLayer) tileMap.getLayers().get("map"));
        createMapMargin();

        mapRender = new OrthogonalTiledMapRenderer(tileMap);
    }

    // 创建地图地板
    private void createMapFloor(TiledMapTileLayer layer) {
        GameSpriteCategory category = GameSpriteCategory.build(MemberFixtureAttribute.MAP_FLOOR, MemberFixtureAttribute._MAP_FLOOR, MemberName.MAP_FLOOR);

        for (int row = 0; row < mapCountH; row++) {
            for (int col = 0; col < mapCountW; col++) {
                Cell cell = layer.getCell(col, row);
                if (cell == null || cell.getTile() == null) {
                    continue;
                }
                BodyDef bodyDef = new BodyDef();
                bodyDef.position.set((col + 0.5f) * tileMapUnitSize * MiniGameConfig.getPhysicalSettingViewRate(), (row + 0.5f) * tileMapUnitSize * MiniGameConfig.getPhysicalSettingViewRate());
                bodyDef.type = BodyType.StaticBody;

                Body body = world.createBody(bodyDef);

                ChainShape shape = new ChainShape();
                Vector2[] v = new Vector2[3];
                v[0] = new Vector2(-tileMapUnitSize * MiniGameConfig.getPhysicalSettingMemberViewRate(), -tileMapUnitSize * MiniGameConfig.getPhysicalSettingMemberViewRate());
                v[1] = new Vector2(-tileMapUnitSize * MiniGameConfig.getPhysicalSettingMemberViewRate(), tileMapUnitSize * MiniGameConfig.getPhysicalSettingMemberViewRate());
                v[2] = new Vector2(tileMapUnitSize * MiniGameConfig.getPhysicalSettingMemberViewRate(), tileMapUnitSize * MiniGameConfig.getPhysicalSettingMemberViewRate());
                shape.createChain(v);

                FixtureDef fixTureDef = new FixtureDef();
                fixTureDef.shape = shape;
                fixTureDef.friction = 0.4f;
                fixTureDef.filter.categoryBits = category.bits;
                fixTureDef.filter.maskBits = category.maskBits;
                fixTureDef.isSensor = false;

                Fixture fixture = body.createFixture(fixTureDef);

                MiniUserData data = new MiniUserData();
                data.name = category.name;
                data.body = body;
                data.fixture = fixture;

                body.setUserData(data);
                fixture.setUserData(data);
            }
        }
    }

    // 创建地图边界
    private void createMapMargin() {
        createMapMargin(0, 0, 0, 0, mapCountW * tileMapUnitSize * MiniGameConfig.getPhysicalSettingViewRate(), 0);
        createMapMargin(mapCountW * tileMapUnitSize * MiniGameConfig.getPhysicalSettingViewRate(), 0, 0, 0, 0, mapCountH * tileMapUnitSize * MiniGameConfig.getPhysicalSettingViewRate());
        createMapMargin(mapCountW * tileMapUnitSize * MiniGameConfig.getPhysicalSettingViewRate(), mapCountH * tileMapUnitSize * MiniGameConfig.getPhysicalSettingViewRate(), 0, 0, -mapCountW * tileMapUnitSize * MiniGameConfig.getPhysicalSettingViewRate(), 0);
        createMapMargin(0, mapCountH * tileMapUnitSize * MiniGameConfig.getPhysicalSettingViewRate(), 0, 0, 0, -mapCountH * tileMapUnitSize * MiniGameConfig.getPhysicalSettingViewRate());
    }

    private void createMapMargin(float bodyX, float bodyY, float shapeX1, float shapeY1, float shapeX2, float shapeY2) {
        GameSpriteCategory category = GameSpriteCategory.build(MemberFixtureAttribute.MAP_MARGIN, MemberFixtureAttribute._MAP_MARGIN, MemberName.MAP_MARGIN);

        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(bodyX, bodyY);
        bodyDef.type = BodyType.StaticBody;

        Body body = world.createBody(bodyDef);

        EdgeShape shape = new EdgeShape();
        shape.set(shapeX1, shapeY1, shapeX2, shapeY2);

        FixtureDef fixTureDef = new FixtureDef();
        fixTureDef.shape = shape;
        fixTureDef.filter.categoryBits = category.bits;
        fixTureDef.filter.maskBits = category.maskBits;
        fixTureDef.isSensor = false;

        Fixture fixture = body.createFixture(fixTureDef);

        MiniUserData data = new MiniUserData();
        data.name = category.name;
        data.body = body;
        data.fixture = fixture;

        body.setUserData(data);
        fixture.setUserData(data);
    }

    @Override
    protected void initMembers() {
        createProta();
        createDiamonds();
        createDuck();
    }

    // 创建主角
    private void createProta() {
        // 获取主角的出生点
        MapLayer layer = tileMap.getLayers().get("mario");
        MapObject mario = layer.getObjects().get("mario");
        float initX = mario.getProperties().get("x", Float.class);
        float initY = mario.getProperties().get("y", Float.class);

        // 实例化主角
        prota = new Protagonist();
        prota.create(world, initX, initY);
    }

    // 创建钻石
    private void createDiamonds() {
        diamonds = new Vector<>();
        tileMap.getLayers().get("diamond").getObjects().forEach(o -> {
            float initX = o.getProperties().get("x", Float.class);
            float initY = o.getProperties().get("y", Float.class);
            Diamond diamond = new Diamond();
            diamond.create(world, initX, initY);
            diamonds.add(diamond);
        });
    }

    // 创建怪鸭
    private void createDuck() {
        ducks = new Vector<>();
        tileMap.getLayers().get("duck").getObjects().forEach(o -> {
            float initX = o.getProperties().get("x", Float.class);
            float initY = o.getProperties().get("y", Float.class);
            Duck duck = new Duck();
            duck.create(world, initX, initY);
            ducks.add(duck);
        });
    }

    @Override
    protected void initCustom() {

    }

    @Override
    protected void initKeyActs() {
        // 尝试发出春丽气功波
        setKeyActJust(Keys.W, () -> {
            prota.createBomb();
        });

        // 下蹲
        setKeyActPress(Keys.S, () -> {
            prota.setStatusPre(ProtagonistStatus.SQUAT);
        });

        // 向左移动
        setKeyActPress(Keys.A, () -> {
            prota.applyForceToCenter(-12f, 0);
            prota.setDirection(GameSpriteDirection.L);
        });

        // 向右移动
        setKeyActPress(Keys.D, () -> {
            prota.applyForceToCenter(12f, 0);
            prota.setDirection(GameSpriteDirection.R);
        });

        // 尝试攻击
        setKeyActJust(Keys.J, () -> {
            prota.createMarioBullet();
        });

        // 尝试跳跃
        setKeyActJust(Keys.K, () -> {
            prota.applyForceToCenter(0, 300f);
        });

        setKeyActJust(Keys.N, () -> {
            prota.createGrenade();
        });

        // 尝试变身春丽
        setKeyActJust(Keys.I, () -> {
            prota.cut();
//            stage.addActor(new ChunWidget().image_widget);
        });

        // 暴走
        setKeyActJust(Keys.O, () -> {
            debug = !debug;
        });
    }

    @Override
    protected void handleInputPre() {
    }

    @Override
    protected void updateMembers() {
        // 夹具处理
//        List<Vector<GameSprite>> gameSpritesArray = Arrays.asList(ducks, diamonds);
//        for (Vector<GameSprite> gameSprites : gameSpritesArray) {
//            Iterator<GameSprite> iterator = gameSprites.iterator();
//            while (iterator.hasNext()) {
//                GameSprite gameSprite = iterator.next();
//                if (gameSprite.isAlive) {
//                    continue;
//                }
//                if (((MiniUserData) (gameSprite.getBody().getUserData())).name.equals(ConstantNames.DIAMOND)) {
//                    if (gameSprite.getDrawY() < 0 && gameSprite.getPosY() < 0) {
//                        if (!world.isLocked() && !gameSprite.isRun) {
//                            world.destroyBody(gameSprite.getBody());
//                            iterator.remove();
//                        }
//                    }
//                }
//                if (((MiniUserData) (gameSprite.getBody().getUserData())).name.equals(ConstantNames.DUCK)) {
//                    if (!world.isLocked() && !gameSprite.isRun) {
//                        world.destroyBody(gameSprite.getBody());
//                        iterator.remove();
//                    }
//                }
//            }
//        }
    }

    @Override
    protected void updateCamera() {
        // 设置相机位置
        getCamera().position.set(prota.getPosX() * MiniGameConfig.getScreenSettingViewRate() + getViewW() / 4.0f, prota.getPosY() * MiniGameConfig.getScreenSettingViewRate() + getViewH() / 8.0f, 0);

        // 矫正相机位置
        if (getCamera().position.x < getCamera().viewportWidth / 2) {
            getCamera().position.x = getCamera().viewportWidth / 2;
        }
        if (getCamera().position.x > (mapCountW * tileMapUnitSize - getCamera().viewportWidth / 2)) {
            getCamera().position.x = (mapCountW * tileMapUnitSize - getCamera().viewportWidth / 2);
        }
        if (getCamera().position.y < getCamera().viewportHeight / 2) {
            getCamera().position.y = getCamera().viewportHeight / 2;
        }
        if (getCamera().position.y > (mapCountH * tileMapUnitSize - getCamera().viewportHeight / 2)) {
            getCamera().position.y = (mapCountH * tileMapUnitSize - getCamera().viewportHeight / 2);
        }
        getCamera().update();
        getBatch().setProjectionMatrix(getCamera().combined);

        // 更新相机左下角的位置
        cameraPosX = getCamera().position.x - getViewW() / 2.0f;
        cameraPosY = getCamera().position.y - getViewH() * 3 / 8.0f;

        // 更新物理相机
        updateBox2DCamera();
    }

    // 更新物理相机
    protected void updateBox2DCamera() {
        box2DCamera.position.set(getCamera().position.x * MiniGameConfig.getPhysicalSettingViewRate(), getCamera().position.y * MiniGameConfig.getPhysicalSettingViewRate(), 0);
        box2DCamera.update();
    }

    @Override
    protected void updateCustom() {
        // 若主角死亡，则重新进入游戏场景
        if (!prota.isAlive) {
            miniGame.resetScreen(new InitialScreen(miniGame), () -> {
                MiniGame.soundPlayer.stopMusic();
            });
        }

        if (curScore >= maxScore) {
            if (MiniGame.getAttributeInt(CustomGameAttributeNames.CURRENT_LEVEL) == 3) {
                miniGame.resetScreen(new InitialScreen(miniGame), () -> {
                    MiniGame.soundPlayer.stopMusic();
                });
            } else {
                miniGame.resetScreen(new TransformScreen(miniGame), () -> {
                    MiniGame.setAttribute(CustomGameAttributeNames.CURRENT_LEVEL, MiniGame.getAttributeInt(CustomGameAttributeNames.CURRENT_LEVEL) + 1);
                    MiniGame.soundPlayer.stopMusic();
                });
            }
        }
    }

    @Override
    protected void renderStages() {
        stage.act();
        stage.draw();
    }

    @Override
    protected void renderMap() {
        mapRender.setView(getCamera());
        mapRender.render();
    }

    @Override
    protected void renderMembers() {
        long now = System.currentTimeMillis();
        prota.render(getBatch(), getDelta());

        List<Vector<GameSprite>> gameSpritesArray = Arrays.asList(ducks, diamonds);
        for (Vector<GameSprite> gameSprites : gameSpritesArray) {
            gameSprites.forEach(gameSprite -> gameSprite.render(getBatch(), getDelta()));
        }

        if (debug) {
            System.out.println(String.format("render members's consume = %d ", System.currentTimeMillis() - now));
        }
    }

    @Override
    protected void renderCustom() {
        if (debug) {
            box2DRender.render(world, box2DCamera.combined);
            System.out.println(String.format("world count = %05d", world.getBodyCount()));
        }

        world.step(MiniGameConfig.getPhysicalSettingTimeStep(), 6, 2);
    }

    @Override
    public void dispose() {
        world.dispose();
        box2DRender.dispose();

        tileMap.dispose();
        mapRender.dispose();

        prota.dispose();
        List<Vector<GameSprite>> gameSpritesArray = Arrays.asList(ducks, diamonds);
        for (Vector<GameSprite> gameSprites : gameSpritesArray) {
            gameSprites.forEach(GameSprite::dispose);
        }

        stage.dispose();
    }
}
