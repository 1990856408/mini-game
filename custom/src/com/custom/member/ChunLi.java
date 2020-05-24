package com.custom.member;

public class ChunLi {
//    //春丽的宽高
//    public static final float DRAW_W = 47;
//    public static final float DRAW_H = DRAW_W * 1.7f;
//    //春丽的裂脚产生的力
//    public static final float force_Xleg = 135.0f;
//    public static final float force_Yleg = 270.0f;
//    //春丽的最大最小速度
//    private static final float max_velocity = 2.0f;
//    private static final float min_velocity = 0.1f;
//    //春丽释放完气功掌向后产生的冲力
//    private static final float rush_x_force = 120.0f;
//    private static final float rush_y_force = 120.0f;
//    //春丽的生命持续的时间
//    private static final int lifeTimes = 60000;
//    //春丽的裂脚攻击时间
//    private static final int attackTimes = 1500;
//    private static final float squat_flex = 0.7f;//下蹲夹具的变化率
//    private static final float attack_flex = 2.0f;//攻击夹具的变化率
//    private static final float attack_Yflex = 1.4f;//攻击夹具的Y轴变化率
//    public ChunLiState status;
//    //春丽是否在气功掌
//    public boolean isQigong;
//    public ChunLiQiGongBall qigong;
//    private Texture texture;
//    private Animation walk_left, walk_right;
//    private Animation idel_left, idel_right;
//    private Animation jump_up, jump_down_left, jump_down_right;
//    private Animation squat_left, squat_right;
//    private Animation attack_left, attack_right;
//    private float delta_qigong_times;//气功开始时到当前的渲染时间
//    private Animation qigong_left, qigong_right;
//    //春丽的裂脚攻击持续时间
//    private Delay attackDelay;
//    //两个夹具，普通夹具和下蹲夹具
//    private FixtureDef fixdef_normal, fixdef_squat;
//    //攻击时的夹具
//    private FixtureDef fixdef_attack;
//    //是否已经在攻击，是否已经在蹲下
//    private boolean hasSquated;
//    private boolean hasAttacked;
//    private Vector<ChunLiQiGongBall> qigongs;
//
//    public ChunLi(Body body, char direction) {
//        super(body);
//
//        this.draw_x = body.getPosition().x * MemberFixtureAttr.LENGTHRATE - DRAW_W / 2;
//        this.draw_y = body.getPosition().y * MemberFixtureAttr.LENGTHRATE - DRAW_H / 2;
//
//        this.width = DRAW_W;
//        this.height = DRAW_H;
//
//        this.direction = direction;
//
//        this.delay_run.setTimes(12);//模拟线程休眠
//        this.delay_run.setFlag(true);
//
//        this.delay_life.setTimes(lifeTimes);
//        this.delay_life.setFlag(false);//这一句用来保证自定义的模拟睡眠类在线程启动前标记为假，从而得到春丽活着
//        this.exec.execute(delay_life);
//
//        this.attackDelay = new Delay();
//        this.attackDelay.setTimes(attackTimes);
//        this.attackDelay.setFlag(true);//确保初始时可以释放百裂脚
//
//        this.isAttack = false;
//        this.delta_qigong_times = 0;
//
//        this.qigongs = new Vector<ChunLiQiGongBall>();
//        this.init();
//    }
//
//    public static Body getCreateBody(float init_x, float init_y, World world) {
//        BodyDef bodyDef = new BodyDef();
//        FixtureDef fixTureDef = new FixtureDef();
//        PolygonShape shape = new PolygonShape();
//        Body chunbody;
//        Fixture fixture;
//
//        bodyDef.position.set(init_x / MemberFixtureAttr.LENGTHRATE,
//                init_y / MemberFixtureAttr.LENGTHRATE);
//        bodyDef.type = BodyType.DynamicBody;
//
//        chunbody = world.createBody(bodyDef);
//
//        shape.setAsBox(ChunLi.DRAW_W / MemberFixtureAttr.LENGTHRATE / MemberFixtureAttr.SCALE,
//                ChunLi.DRAW_H / MemberFixtureAttr.LENGTHRATE / MemberFixtureAttr.SCALE);
//
//        fixTureDef.shape = shape;
//        fixTureDef.filter.categoryBits = MemberFixtureAttr.CHUNLI;
//        fixTureDef.filter.maskBits = MemberFixtureAttr._CHUNLI;
//        fixTureDef.isSensor = false;//设置为传感器
//        fixture = chunbody.createFixture(fixTureDef);
//
//        CustomUserData data = new CustomUserData();
//        data.name = MemberName.CHUNLI;
//        data.body = chunbody;
//        data.fixture = fixture;
//        chunbody.setUserData(data);
//        fixture.setUserData(data);
//
//        return chunbody;
//    }
//
//    public void attackLeg() {
//        //进入攻击状态
//        this.isAttack = true;
//        exec.execute(attackDelay);
//        if (this.direction == 'D') {
//            this.currentAnimation = attack_right;
//        } else {
//            this.currentAnimation = attack_left;
//        }
//    }
//
//    public void attackQiGong() {
//        //进入气功攻击状态
//        this.isQigong = true;
//        this.delta_qigong_times = 0;
//        if (this.direction == 'D') {
//            this.currentAnimation = qigong_right;
//        } else if (this.direction == 'A') {
//            this.currentAnimation = qigong_left;
//        }
//    }
//
//    @Override
//    public void init() {
//        this.initAnimation();
//        this.initState();
//        this.initFixture();
//    }
//
//    public void initFixture() {
//        fixdef_normal = new FixtureDef();
//        fixdef_squat = new FixtureDef();
//        fixdef_attack = new FixtureDef();
//
//        PolygonShape shape = new PolygonShape();
//        shape.setAsBox(ChunLi.DRAW_W / MemberFixtureAttr.LENGTHRATE / MemberFixtureAttr.SCALE,
//                ChunLi.DRAW_H / MemberFixtureAttr.LENGTHRATE / MemberFixtureAttr.SCALE);
//        fixdef_normal.shape = shape;
//        fixdef_normal.filter.categoryBits = MemberFixtureAttr.CHUNLI;
//        fixdef_normal.filter.maskBits = MemberFixtureAttr._CHUNLI;
//        fixdef_normal.isSensor = false;
//
//        PolygonShape shape2 = new PolygonShape();
//
//        shape2.setAsBox(ChunLi.DRAW_W / MemberFixtureAttr.LENGTHRATE / MemberFixtureAttr.SCALE,
//                ChunLi.DRAW_H / MemberFixtureAttr.LENGTHRATE / MemberFixtureAttr.SCALE * squat_flex);
//        fixdef_squat.shape = shape2;
//        fixdef_squat.filter.categoryBits = MemberFixtureAttr.CHUNLI;
//        fixdef_squat.filter.maskBits = MemberFixtureAttr._CHUNLI;
//        fixdef_squat.isSensor = false;
//
//        //初始是否已经蹲下，设置为假
//        this.hasSquated = false;
//
//        //攻击时的夹具
//        PolygonShape shape3 = new PolygonShape();
//
//        shape3.setAsBox(ChunLi.DRAW_W / MemberFixtureAttr.LENGTHRATE / MemberFixtureAttr.SCALE * attack_flex,
//                ChunLi.DRAW_H / MemberFixtureAttr.LENGTHRATE / MemberFixtureAttr.SCALE * attack_Yflex);
//        fixdef_attack.shape = shape3;
//        fixdef_attack.filter.categoryBits = MemberFixtureAttr.CHUNLI;
//        fixdef_attack.filter.maskBits = MemberFixtureAttr._CHUNLI;
//        fixdef_attack.isSensor = false;
//        this.hasAttacked = false;
//    }
//
//    private void initState() {
//        status = ChunLiState.QUIET;
//    }
//
//    private void initAnimation() {
//        //裂脚攻击的动画
//        texture = MiniGame.assetManager.get("members/chunli4.png", Texture.class);
//        TextureRegion[][] region_attacks = TextureRegion.split(texture, texture.getWidth() / 2, texture.getHeight() / 4);
//        TextureRegion region_attack_right[] = new TextureRegion[8];
//        TextureRegion region_attack_left[] = new TextureRegion[8];
//        int m = 0;
//        for (int i = 0; i < 4; i++)
//            for (int j = 0; j < 2; j++) {
//                region_attack_right[m] = region_attacks[i][j];
//                region_attack_left[m] = new TextureRegion(region_attack_right[m]);
//                region_attack_left[m].flip(true, false);
//                m++;
//            }
//        attack_right = new Animation(MemberFixtureAttr.DRAWDUR, region_attack_right);
//        attack_right.setPlayMode(Animation.PlayMode.LOOP);
//
//        attack_left = new Animation(MemberFixtureAttr.DRAWDUR, region_attack_left);
//        attack_left.setPlayMode(Animation.PlayMode.LOOP);
//
//
//        //站立的动画
//        Texture texture8 = MiniGame.assetManager.get("members/chunli8.png", Texture.class);
//        TextureRegion[][] region_idels = TextureRegion.split(texture8, texture8.getWidth() / 4, texture8.getHeight() / 3);
//        TextureRegion[] region_idel_right = new TextureRegion[10];
//        TextureRegion[] region_idel_left = new TextureRegion[10];
//        m = 0;
//        for (int i = 0; i < 3; i++)
//            for (int j = 0; j < 4; j++) {
//                if (m >= 10)
//                    break;
//                region_idel_right[m] = region_idels[i][j];
//                region_idel_left[m] = new TextureRegion(region_idel_right[m]);
//                region_idel_left[m].flip(true, false);
//                m++;
//            }
//        idel_right = new Animation(MemberFixtureAttr.DRAWDUR, region_idel_right);
//        idel_right.setPlayMode(Animation.PlayMode.LOOP);
//
//        idel_left = new Animation(MemberFixtureAttr.DRAWDUR, region_idel_left);
//        idel_left.setPlayMode(Animation.PlayMode.LOOP);
//
//        //走动的动画
//        Texture texture7 = MiniGame.assetManager.get("members/chunli7.png", Texture.class);
//        TextureRegion[][] region_walks = TextureRegion.split(texture7, texture7.getWidth() / 4, texture7.getHeight() / 3);
//        TextureRegion[] region_walk_right = new TextureRegion[10];
//        TextureRegion[] region_walk_left = new TextureRegion[10];
//        m = 0;
//        for (int i = 0; i < 3; i++)
//            for (int j = 0; j < 3; j++) {
//                region_walk_right[m] = region_walks[i][j];
//                region_walk_left[m] = new TextureRegion(region_walk_right[m]);
//                region_walk_left[m].flip(true, false);
//                m++;
//
//            }
//        region_walk_right[m] = region_walks[2][3];
//        region_walk_left[m] = new TextureRegion(region_walk_right[m]);
//        region_walk_left[m].flip(true, false);
//        walk_right = new Animation(MemberFixtureAttr.DRAWDUR, region_walk_right);
//        walk_right.setPlayMode(Animation.PlayMode.LOOP);
//        walk_left = new Animation(MemberFixtureAttr.DRAWDUR, region_walk_left);
//        walk_left.setPlayMode(Animation.PlayMode.LOOP);
//        //跳跃的动画
//        Texture texture2 = MiniGame.assetManager.get("members/chunli1.png", Texture.class);
//        TextureRegion[][] region_jumps = TextureRegion.split(texture2, texture2.getWidth() / 4, texture2.getHeight() / 2);
//        TextureRegion[] region_jump_up = new TextureRegion[8];
//        m = 0;
//        for (int i = 0; i < 2; i++)
//            for (int j = 0; j < 4; j++) {
//                region_jump_up[m] = region_jumps[i][j];
//                m++;
//            }
//        jump_up = new Animation(MemberFixtureAttr.DRAWDUR, region_jump_up);
//        jump_up.setPlayMode(Animation.PlayMode.LOOP);
//
//        //下降的动画
//        Texture texture9 = MiniGame.assetManager.get("members/chunli9.png", Texture.class);
//        TextureRegion[][] region_downs = TextureRegion.split(texture9, texture9.getWidth() / 3, texture9.getHeight() / 3);
//        TextureRegion[] region_down_left = new TextureRegion[8];
//        TextureRegion[] region_down_right = new TextureRegion[8];
//        m = 0;
//        for (int i = 0; i < 3; i++)
//            for (int j = 0; j < 3; j++) {
//                if (m >= 8)
//                    break;
//                region_down_left[m] = region_downs[i][j];
//                region_down_right[m] = new TextureRegion(region_down_left[m]);
//                region_down_right[m].flip(true, false);
//                m++;
//            }
//        jump_down_left = new Animation(MemberFixtureAttr.DRAWDUR, region_down_left);
//        jump_down_left.setPlayMode(Animation.PlayMode.LOOP);
//        jump_down_right = new Animation(MemberFixtureAttr.DRAWDUR, region_down_right);
//        jump_down_right.setPlayMode(Animation.PlayMode.LOOP);
//        //下蹲的动画
//        TextureRegion region_squat_left = new TextureRegion(texture8, 230, 250, 124, 104);
//        squat_left = new Animation(MemberFixtureAttr.DRAWDUR, region_squat_left);
//
//        TextureRegion region_squat_right = new TextureRegion(region_squat_left);
//        region_squat_right.flip(true, false);
//        squat_right = new Animation(MemberFixtureAttr.DRAWDUR, region_squat_right);
//
//        //气功波，真实费劲儿
//        Texture texture5 = MiniGame.assetManager.get("members/chunli5.png", Texture.class);
//        Texture texture6 = MiniGame.assetManager.get("members/chunli6.png", Texture.class);
//        TextureRegion[][] chunli5 = TextureRegion.split(texture5, texture5.getWidth() / 3, texture5.getHeight() / 5);
//        TextureRegion[][] chunli6 = TextureRegion.split(texture6, texture6.getWidth() / 3, texture6.getHeight() / 6);
//        TextureRegion[] region_qigong_right = new TextureRegion[28];
//        TextureRegion[] region_qigong_left = new TextureRegion[28];
//        int n = 0;
//        for (int i = 0; i < 4; i++)
//            for (int j = 0; j < 3; j++) {
//                region_qigong_right[n] = chunli6[i][j];
//                region_qigong_left[n] = new TextureRegion(region_qigong_right[n]);
//                region_qigong_left[n].flip(true, false);
//                n++;
//            }
//        region_qigong_right[n] = chunli6[4][0];
//        region_qigong_left[n] = new TextureRegion(region_qigong_right[n]);
//        region_qigong_left[n].flip(true, false);
//        n++;
//        for (int i = 0; i < 5; i++)
//            for (int j = 0; j < 3; j++) {
//                region_qigong_right[n] = chunli5[i][j];
//                region_qigong_left[n] = new TextureRegion(region_qigong_right[n]);
//                region_qigong_left[n].flip(true, false);
//                n++;
//            }
//        qigong_right = new Animation(MemberFixtureAttr.DRAWDUR, region_qigong_right);
////		qigong_right.setPlayMode(Animation.PlayMode.LOOP);
//        qigong_left = new Animation(MemberFixtureAttr.DRAWDUR, region_qigong_left);
////		qigong_left.setPlayMode(Animation.PlayMode.LOOP);
//
//
//    }
//
//    @Override
//    public void update(float delta) {
//        this.delta_qigong_times += Gdx.graphics.getDeltaTime();
//        this.updatePosition();
//        this.updateChunLiState();
//        this.updateAnimation();
//        this.updateLife();
//        this.updateQiGongLife();
//        //如果在裂脚状态，则更新裂脚的夹具，如果不在裂脚状态，则更新气功掌的夹具
//        if (!attackDelay.isFlag())
//            this.updateAttackFixture(isAttack);
//        else
//            this.updateAttackFixture(isQigong);
//    }
//
//    private void updateQiGongLife() {
//        for (ChunLiQiGongBall qg : qigongs) {
//            if (!qg.isAlive) {
//                Body bo = qg.getBody();
//                if (bo.getFixtureList().size > 0) {
//                    bo.destroyFixture(bo.getFixtureList().first());
//                }
//            }
//        }
//        for (int i = 0; i < qigongs.size(); i++) {
//            ChunLiQiGongBall qg = qigongs.get(i);
//            Body bo = qg.getBody();
//            if (bo.getFixtureList().size == 0) {
//                if (!world.isLocked() && !qg.isRun) {
//                    world.destroyBody(bo);
//                    qigongs.remove(i);
//                }
//            }
//        }
//    }
//
//    private void updateLife() {
//        if (isAlive == false)
//            return;
//        if (this.delay_life.isFlag())//生命时间到了，则令其死亡
//        {
//            this.isAlive = false;
//        }
//    }
//
//    private void updatePosition() {
//        this.pos_x = body.getPosition().x * MemberFixtureAttr.LENGTHRATE;
//        this.pos_y = body.getPosition().y * MemberFixtureAttr.LENGTHRATE;
//        this.draw_x = body.getPosition().x * MemberFixtureAttr.LENGTHRATE - DRAW_W / 2;
//        this.draw_y = body.getPosition().y * MemberFixtureAttr.LENGTHRATE - DRAW_H / 2;
//    }
//
//    public void updateChunLiState() {
//
//        if (this.attackDelay.isFlag()) {
//            //攻击完毕，退出裂脚攻击状态
//            this.isAttack = false;
//        }
//
//        float x = body.getLinearVelocity().x;
//        float y = body.getLinearVelocity().y;
//
//        //先矫正一下速度
//        this.adjustSpeed(x, y);
//
//        //如果是下蹲状态，则直接判断完成退出
//        if (y == 0 && status == ChunLiState.SQUAT) {
//            return;
//        }
//
//        if (x == 0 && y == 0) {
//            status = ChunLiState.QUIET;
//        } else if (x != 0 && y == 0) {
//            status = ChunLiState.WALK;
//        } else if (x == 0 && y != 0) {
//            status = ChunLiState.JUMP;
//        } else if (x != 0 && y != 0) {
//            status = ChunLiState.JUMP;
//        }
//
//    }
//
//    public void updateAnimation() {
//        //攻击状态下不需要更新动画，保持攻击动画即可
//        if (this.isAttack || this.isQigong)
//            return;
//        switch (status) {
//            case QUIET:
//                switch (direction) {
//                    case 'A':
//                        currentAnimation = idel_left;
//                        break;
//                    case 'D':
//                        currentAnimation = idel_right;
//                        break;
//                }
//                break;
//            case WALK:
//                switch (direction) {
//                    case 'A':
//                        currentAnimation = walk_left;
//                        break;
//                    case 'D':
//                        currentAnimation = walk_right;
//                        break;
//                }
//                break;
//            case JUMP:
//                if (body.getLinearVelocity().y > 0) {
//                    currentAnimation = jump_up;
//                } else {
//                    switch (direction) {
//                        case 'A':
//                            currentAnimation = jump_down_left;
//                            break;
//                        case 'D':
//                            currentAnimation = jump_down_right;
//                            break;
//                    }
//                }
//                break;
//            case SQUAT:
//                switch (direction) {
//                    case 'A':
//                        currentAnimation = squat_left;
//                        break;
//                    case 'D':
//                        currentAnimation = squat_right;
//                        break;
//                }
//                break;
//        }
//    }
//
//    public void adjustSpeed(float x, float y) {
//        if (x > max_velocity) {
//            body.setLinearVelocity(max_velocity, y);
//            return;
//        }
//        if (x < -max_velocity) {
//            body.setLinearVelocity(-max_velocity, y);
//            return;
//        }
//        if (x > 0 && x < min_velocity) {
//            body.setLinearVelocity(0, y);
//            return;
//        }
//        if (x < 0 && x > -min_velocity) {
//            body.setLinearVelocity(0, y);
//            return;
//        }
//
//    }
//
//    //下蹲夹具切换
//    public void updateFixture(boolean isSquat) {
//        //如果在裂脚攻击或者气功掌状态，则直接退出夹具更新
//        if (this.isAttack || this.isQigong)
//            return;
//        //先获取当前的夹具
//        Fixture current_fixture = body.getFixtureList().first();
//        //如果要求蹲下，并且没有蹲下才蹲下，否则保持状态即可
//
//        CustomUserData data = new CustomUserData();
//        data.name = MemberName.CHUNLI;
//        data.body = body;
//
//        if (isSquat) {
//            if (!hasSquated) {
//                body.destroyFixture(current_fixture);
//                Fixture fixture = body.createFixture(fixdef_squat);
//                data.fixture = fixture;
//                fixture.setUserData(data);
//                hasSquated = true;
//            }
//        } else {
//            if (hasSquated) {
//                body.destroyFixture(current_fixture);
//                Fixture fixture = body.createFixture(fixdef_normal);
//                data.fixture = fixture;
//                fixture.setUserData(data);
//                hasSquated = false;
//            }
//        }
//    }
//
//    //攻击夹具切换
//    public void updateAttackFixture(boolean attacking) {
//        //先获取当前的夹具
//        Fixture current_fixture = body.getFixtureList().first();
//        //如果要求攻击，并且没有在攻击夹具状态下才更换，否则保持状态即可
//        CustomUserData data = new CustomUserData();
//        data.name = MemberName.CHUNLI;
//        data.body = body;
//        if (attacking) {
//            if (!hasAttacked) {
//                body.destroyFixture(current_fixture);
//                Fixture fixture = body.createFixture(fixdef_attack);
//                data.fixture = fixture;
//                fixture.setUserData(data);
//                hasAttacked = true;
//            }
//        } else {
//            if (hasAttacked) {
//                body.destroyFixture(current_fixture);
//                Fixture fixture = body.createFixture(fixdef_normal);
//                data.fixture = fixture;
//                fixture.setUserData(data);
//                hasAttacked = false;
//            }
//        }
//    }
//
//    @Override
//    public void render(SpriteBatch batch, float delta) {
//        this.update(delta);
//        batch.begin();
//
//        /*
//         * 如果正在释放气功掌，如果动画没有播放完，则获取当前动画的关键帧
//         */
//        if (this.isQigong) {
//            if (!this.currentAnimation.isAnimationFinished(delta_qigong_times)) {
//                this.currentFrame = this.currentAnimation.getKeyFrame(delta_qigong_times);
//            } else {
//                this.createBall();
//                this.isQigong = false;
//                if (this.direction == 'D')
//                    body.applyForceToCenter(-rush_x_force, rush_y_force, true);
//                else
//                    body.applyForceToCenter(rush_x_force, rush_y_force, true);
//            }
//        } else//如果不在气功掌状态，则直接获取当前的关键帧
//        {
//            this.currentFrame = this.currentAnimation.getKeyFrame(delta);
//        }
//
//        //根据不同的状态，夹具也会发生变化，随之绘制位置也微调使图像处于夹具中
//        if (this.isAttack || this.isQigong) {
//            this.draw_x = body.getPosition().x * MemberFixtureAttr.LENGTHRATE - DRAW_W * attack_flex / 2;
//            this.draw_y = body.getPosition().y * MemberFixtureAttr.LENGTHRATE - DRAW_H * attack_Yflex / 2;
//            batch.draw(currentFrame, draw_x, draw_y, DRAW_W * attack_flex, DRAW_H * attack_Yflex);
//        } else if (this.hasSquated) {
//            this.draw_y = draw_y + DRAW_H * squat_flex / 4;
//            batch.draw(currentFrame, draw_x, draw_y, DRAW_W, DRAW_H * squat_flex);
//        } else if (this.status == ChunLiState.QUIET) {
//            this.draw_y = this.draw_y - 2;
//            batch.draw(currentFrame, draw_x, draw_y, DRAW_W, DRAW_H);
//        } else {
//            batch.draw(currentFrame, draw_x, draw_y, DRAW_W, DRAW_H);
//        }
//        batch.end();
//
//        for (ChunLiQiGongBall q : qigongs) {
//            q.render(batch, delta);
//        }
//    }
//
//    private void createBall() {
//        if (world.isLocked())
//            return;
//
//        float ball_x = this.pos_x;
//        float ball_y = this.pos_y + DRAW_H * 0.2f;
//
//        this.qigong = new ChunLiQiGongBall(ChunLiQiGongBall.getCreateBody(ball_x, ball_y, world), this.direction == 'D' ? 'D' : 'A');
//        this.qigongs.add(qigong);
//    }
//
//    @Override
//    public void dispose() {
//        if (qigongs != null)
//            for (ChunLiQiGongBall qg : qigongs)
//                qg.dispose();
//    }
//
//    @Override
//    public void xrun() {
//        if (!delay_run.isFlag() || !isAlive)
//            return;
//        exec.execute(delay_run);
//
//        isRun = true;
//
//        isRun = false;
//    }
}
