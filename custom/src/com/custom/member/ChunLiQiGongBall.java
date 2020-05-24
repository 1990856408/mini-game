package com.custom.member;

public class ChunLiQiGongBall {
//    public static final float xForce = 360.0f;
//    public static final float yForce = 360.0f;
//
//    public static final float Radius = 97;
//    private static final float speed = 7.0f;
//    private static final float pos_x_range = MiniGameScreenSetting.VIEW_W;
//    private Ball ball;
//
//    public ChunLiQiGongBall(Body body, char direction) {
//        super(body);
//
//        this.direction = direction;
//
//        this.draw_x = body.getPosition().x * MemberFixtureAttr.LENGTHRATE;
//        this.draw_y = body.getPosition().y * MemberFixtureAttr.LENGTHRATE;
//
//        this.width = Radius;
//        this.height = Radius;
//
//        this.delay_run.setTimes(12);
//
//        this.ball = new Ball(pos_x, pos_y);
//        this.init();
//    }
//
//    public static Body getCreateBody(float init_x, float init_y, World world) {
//        //刚体定义，夹具定义，图形创建，夹具创建
//        BodyDef bodyDef = new BodyDef();
//        FixtureDef fixTureDef = new FixtureDef();
//        CircleShape shape = new CircleShape();
//        Body body;
//        Fixture fixture;
//
//        bodyDef.position.set(init_x / MemberFixtureAttr.LENGTHRATE,
//                init_y / MemberFixtureAttr.LENGTHRATE);
//        bodyDef.type = BodyType.DynamicBody;
//
//        body = world.createBody(bodyDef);
//
//        shape.setRadius(Radius / MemberFixtureAttr.LENGTHRATE);
//
//        fixTureDef.shape = shape;
//        fixTureDef.filter.categoryBits = MemberFixtureAttr.BALL;
//        fixTureDef.filter.maskBits = MemberFixtureAttr._BALL;
//        fixTureDef.isSensor = true;//设置为传感器
//        fixture = body.createFixture(fixTureDef);
//
//        CustomUserData data = new CustomUserData();
//        data.name = MemberName.BALL;
//        data.body = body;
//        data.fixture = fixture;
//        body.setUserData(data);
//        fixture.setUserData(data);
//
//        return body;
//    }
//
//    @Override
//    public void init() {
//        if (this.direction == 'D')
//            body.setLinearVelocity(speed, 0);
//        else if (this.direction == 'A')
//            body.setLinearVelocity(-speed, 0);
//    }
//
//    @Override
//    public void update(float delta) {
//        if (this.isAlive)
//            this.body.applyForceToCenter(0, NormalScreen.WORLD_GRAVITY, true);
//        else
//            return;
//
//        this.updatePosition();
//        this.updateLife();
//        this.runZ();
//    }
//
//    private void runZ() {
//        if (!delay_run.isFlag() || !isAlive)
//            return;
//        exec.execute(delay_run);
//
//        isRun = true;
//
//        isRun = false;
//
//    }
//
//    private void updatePosition() {
//        if (!isAlive)
//            return;
//        this.pos_x = body.getPosition().x * MemberFixtureAttr.LENGTHRATE;
//        this.pos_y = body.getPosition().y * MemberFixtureAttr.LENGTHRATE;
//        this.draw_x = body.getPosition().x * MemberFixtureAttr.LENGTHRATE;
//        this.draw_y = body.getPosition().y * MemberFixtureAttr.LENGTHRATE;
//        this.ball.setPosition(pos_x, pos_y);
//    }
//
//    private void updateLife() {
//        if (!isAlive)
//            return;
//
//        if (this.body.getFixtureList().size == 0) {
//            this.isAlive = false;
//            return;
//        }
//
//        if (direction == 'D') {
//            if (pos_x >= (init_pos_x + pos_x_range)) {
//                this.isAlive = false;
//            }
//        } else if (direction == 'A') {
//            if (pos_x <= (init_pos_x - pos_x_range)) {
//                this.isAlive = false;
//            }
//        }
//    }
//
//    @Override
//    public void render(SpriteBatch batch, float delta) {
//        this.update(delta);
//        batch.begin();
//
//        if (this.isAlive)
//            ball.draw(batch);
//
//        batch.end();
//    }
//
//    @Override
//    public void dispose() {
//        ball.dispose();
//    }
//
//    @Override
//    public void xrun() {
//
//    }

}
