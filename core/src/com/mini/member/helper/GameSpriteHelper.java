package com.mini.member.helper;

import com.badlogic.gdx.physics.box2d.*;
import com.mini.game.MiniGameConfig;
import com.mini.member.GameSprite;
import com.mini.member.GameSpriteCategory;
import com.mini.member.MiniUserData;

/**
 * 游戏精灵辅助器
 */
public class GameSpriteHelper {

    public static final GameSpriteHelper me = new GameSpriteHelper();

    private GameSpriteHelper() {
    }

    /**
     * 创建物理世界的刚体
     *
     * @param config 参数配置
     * @return 创建的刚体
     */
    public Body createBody(BodyConfig config) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(config.initX * MiniGameConfig.getPhysicalSettingViewRate(), config.initY * MiniGameConfig.getPhysicalSettingViewRate());
        bodyDef.type = config.bodyType;

        Body body = config.world.createBody(bodyDef);
        body.setGravityScale(config.gravityScale);

        Shape shape;
        switch (config.shapeType) {
            case Circle:
                shape = new CircleShape();
                shape.setRadius(config.drawR * MiniGameConfig.getPhysicalSettingMemberViewRate());
                break;
            case Polygon:
                shape = new PolygonShape();
                ((PolygonShape) shape).setAsBox(config.drawW * MiniGameConfig.getPhysicalSettingMemberViewRate(), config.drawH * MiniGameConfig.getPhysicalSettingMemberViewRate());
                break;
            default:
                throw new IllegalArgumentException();
        }

        FixtureDef fixtureDef = createFixtureDef(shape, config.category.bits, config.category.maskBits, config.isSensor);
        Fixture fixture = body.createFixture(fixtureDef);

        MiniUserData data = new MiniUserData();
        data.name = config.category.name;
        data.body = body;
        data.fixture = fixture;
        data.mine = config.host;

        body.setUserData(data);
        fixture.setUserData(data);

        return body;
    }

    /**
     * 创建夹具
     *
     * @param shape    夹具图形
     * @param bits     定义码
     * @param maskBits 碰撞码
     * @param isSensor 是否为传感器
     * @return
     */
    public FixtureDef createFixtureDef(Shape shape, short bits, short maskBits, boolean isSensor) {
        FixtureDef fixTureDef = new FixtureDef();
        fixTureDef.shape = shape;
        fixTureDef.filter.categoryBits = bits;
        fixTureDef.filter.maskBits = maskBits;
        fixTureDef.isSensor = isSensor;

        return fixTureDef;
    }

    /**
     * 刚体配置
     */
    public static class BodyConfig {

        // 图形世界的(x,y)坐标
        private float initX, initY;

        // 刚体类型
        private BodyDef.BodyType bodyType;

        // 重力比率
        private float gravityScale = 1.0f;

        // 刚体所在的世界
        private World world;

        // 夹具的图形
        private CircleShape.Type shapeType;

        // 绘制的半径、长，宽
        private float drawR, drawW, drawH;

        // 游戏精灵的属性
        private GameSpriteCategory category;

        // 夹具是否为传感器
        private boolean isSensor;

        // 刚体的宿主
        private GameSprite host;

        public BodyConfig setInitX(float initX) {
            this.initX = initX;
            return this;
        }

        public BodyConfig setInitY(float initY) {
            this.initY = initY;
            return this;
        }

        public BodyConfig setBodyType(BodyDef.BodyType bodyType) {
            this.bodyType = bodyType;
            return this;
        }

        public BodyConfig setGravityScale(float gravityScale) {
            this.gravityScale = gravityScale;
            return this;
        }

        public BodyConfig setWorld(World world) {
            this.world = world;
            return this;
        }

        public BodyConfig setShapeType(CircleShape.Type shapeType) {
            this.shapeType = shapeType;
            return this;
        }

        public BodyConfig setDrawR(float drawR) {
            this.drawR = drawR;
            return this;
        }

        public BodyConfig setDrawW(float drawW) {
            this.drawW = drawW;
            return this;
        }

        public BodyConfig setDrawH(float drawH) {
            this.drawH = drawH;
            return this;
        }

        public BodyConfig setCategory(GameSpriteCategory category) {
            this.category = category;
            return this;
        }

        public BodyConfig setIsSensor(boolean isSensor) {
            this.isSensor = isSensor;
            return this;
        }

        public BodyConfig setHost(GameSprite host) {
            this.host = host;
            return this;
        }
    }
}
