package com.mini.member;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.mini.member.helper.GameSpriteHolder;
import com.mini.member.status.GameSpriteDirection;
import com.mini.member.status.GameSpriteStatus;

/**
 * 游戏精灵
 */
public abstract class GameSprite implements Runnable {

    // 成员是否存活（默认存活）
    public boolean isAlive = true;
    // 精灵属性
    protected GameSpriteCategory category;
    // 成员的世界
    protected World world;
    // 成员的刚体
    protected Body body;
    // 成员的初始位置
    protected float initPosX, initPosY;
    // 成员的朝向
    protected GameSpriteDirection direction;
    // 健康值
    protected GameSpriteHealth health;
    // 成员的动作
    protected long action;
    // 成员的增益
    protected long buff;
    // 当前动画
    protected Animation currentAnimation;

    public GameSprite() {
        category = getGameSpriteCategory();
    }

    /**
     * 构建物理世界相关
     *
     * @param world
     * @param initX 图形世界X坐标
     * @param initY 图形世界Y坐标
     */
    public final void create(World world, float initX, float initY) {
        this.world = world;
        body = createBody(world, initX, initY);
        initPosX = getPosX();
        initPosY = getPosY();

        init();
    }

    // 创建刚体
    protected abstract Body createBody(World world, float initX, float initY);

    /**
     * 销毁刚体
     * 渲染过程中创建的刚体建议在此函数中销毁
     *
     * @see GameSpriteHolder#destroyBody()
     */
    public final void destroyBody() {
        if (world != null && body != null) {
            world.destroyBody(body);
        }
    }

    // 销毁夹具
    public final void destroyFixture() {
        if (body != null && body.getFixtureList().size != 0) {
            body.destroyFixture(body.getFixtureList().first());
        }
    }

    // 初始化
    protected void init() {
        preInit();
        initStatus();
        initFixtures();
        initAnimations();
        initCustom();
    }

    // 初始化准备（补偿物理世界参数、图形世界参数等）
    protected void preInit() {
    }

    // 初始化夹具
    protected void initFixtures() {
    }

    // 初始化动画
    protected void initAnimations() {
    }

    // 初始化状态
    protected void initStatus() {
    }

    // 自定义初始化
    protected void initCustom() {
    }

    // 渲染
    public final void render(SpriteBatch batch, float delta) {
        update();
        renderCustom(batch, delta);
    }

    protected void update() {
        if (!needUpdate()) {
            return;
        }

        updateStatus();
        updateFixtures();
        updateAnimations();
        updateCustom();
    }

    // 是否需要刷新
    protected boolean needUpdate() {
        return isAlive;
    }

    // 刷新夹具
    protected void updateFixtures() {
    }

    // 刷新参数
    protected void updateStatus() {
    }

    // 刷新动画
    protected void updateAnimations() {
    }

    // 刷新自定义
    protected void updateCustom() {
    }

    // 渲染自定义
    protected void renderCustom(SpriteBatch batch, float delta) {

    }

    // 销毁
    public void dispose() {
    }

    @Override
    public void run() {
    }

    protected GameSpriteStatus getGameSpriteStatus() {
        return null;
    }

    // 向刚体中心施加一个力
    public void applyForceToCenter(float forceX, float forceY) {
        GameSpriteStatus gameSpriteStatus = getGameSpriteStatus();
        if (gameSpriteStatus == null || gameSpriteStatus.canApplyForceToCenter()) {
            body.applyForceToCenter(forceX, forceY, true);
        }
    }

    // 给刚体一个速度
    public void applyVelocity(float velocityX, float velocityY) {
        body.setLinearVelocity(velocityX, velocityY);
    }

    // 取成员属性
    public abstract GameSpriteCategory getGameSpriteCategory();

    // 取物理世界X坐标
    public float getPosX() {
        return body.getPosition().x;
    }

    // 取物理世界Y坐标
    public float getPosY() {
        return body.getPosition().y;
    }

    // 设置物理世界坐标
    public void setPosition(float x, float y) {
        body.setTransform(x, y, 0);
    }

    // 取物理世界X速度
    public float getVelocityX() {
        return body.getLinearVelocity().x;
    }

    // 取物理世界Y速度
    public float getVelocityY() {
        return body.getLinearVelocity().y;
    }

    // 取图形世界X绘制坐标
    public abstract float getDrawX();

    // 取图形世界Y绘制坐标
    public abstract float getDrawY();

    // 取图形世界绘制宽度
    public float getDrawW() {
        return getDrawR() * 2;
    }

    // 取图形世界绘制高度
    public float getDrawH() {
        return getDrawR() * 2;
    }

    // 取图形世界绘制半径
    public float getDrawR() {
        return 0;
    }

    // 取图形世界当前帧
    public TextureRegion getFrameCurrent(float delta) {
        return currentAnimation.getKeyFrame(delta);
    }


    /*
     * 下面都是变量的Get、Set函数
     */
    public GameSpriteDirection getDirection() {
        return direction;
    }

    public void setDirection(GameSpriteDirection direction) {
        GameSpriteStatus status = getGameSpriteStatus();
        if (status == null || status.canRedirect()) {
            this.direction = direction;
        }
    }

    public GameSpriteHealth getHealth() {
        return health;
    }

    public long getAction() {
        return action;
    }

    public void setAction(long action) {
        this.action = action;
    }
}