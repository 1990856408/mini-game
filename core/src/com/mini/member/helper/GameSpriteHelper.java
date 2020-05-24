package com.mini.member.helper;

import com.badlogic.gdx.physics.box2d.*;
import com.mini.assist.CustomUserData;
import com.mini.constant.MiniGamePhysicalSetting;
import com.mini.member.GameSprite;
import com.mini.member.GameSpriteCategory;

public class GameSpriteHelper {

    public static final GameSpriteHelper me = new GameSpriteHelper();

    private GameSpriteHelper() {
    }

    public static class BodyConfig {

        private float initX, initY;

        private BodyDef.BodyType bodyType;

        private float gravityScale = 1;

        private World world;

        private CircleShape.Type shapeType;

        private float drawR, drawW, drawH;

        private GameSpriteCategory category;

        private boolean isSensor;

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

    /**
     * 创建物理世界的刚体
     *
     * @param config 参数配置
     * @return 创建的刚体
     */
    public Body createBody(BodyConfig config) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(config.initX * MiniGamePhysicalSetting.VIEW_RATE, config.initY * MiniGamePhysicalSetting.VIEW_RATE);
        bodyDef.type = config.bodyType;

        Body body = config.world.createBody(bodyDef);
        body.setGravityScale(config.gravityScale);

        Shape shape;
        switch (config.shapeType) {
            case Circle:
                shape = new CircleShape();
                shape.setRadius(config.drawR * MiniGamePhysicalSetting.MEMBER_VIEW_RATE);
                break;
            case Polygon:
                shape = new PolygonShape();
                ((PolygonShape) shape).setAsBox(config.drawW * MiniGamePhysicalSetting.MEMBER_VIEW_RATE, config.drawH * MiniGamePhysicalSetting.MEMBER_VIEW_RATE);
                break;
            default:
                throw new IllegalArgumentException();
        }

        FixtureDef fixtureDef = createFixtureDef(shape, config.category.bits, config.category.maskBits, config.isSensor);
        Fixture fixture = body.createFixture(fixtureDef);

        CustomUserData data = new CustomUserData();
        data.name = config.category.name;
        data.body = body;
        data.fixture = fixture;
        data.mine = config.host;

        body.setUserData(data);
        fixture.setUserData(data);

        return body;
    }

    public FixtureDef createFixtureDef(Shape shape, short bits, short maskBits, boolean isSensor) {
        FixtureDef fixTureDef = new FixtureDef();
        fixTureDef.shape = shape;
        fixTureDef.filter.categoryBits = bits;
        fixTureDef.filter.maskBits = maskBits;
        fixTureDef.isSensor = isSensor;

        return fixTureDef;
    }
}
