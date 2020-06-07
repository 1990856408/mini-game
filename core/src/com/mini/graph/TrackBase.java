package com.mini.graph;

/**
 * 轨迹
 */
public abstract class TrackBase {

    // 两个相对位置确定间距、角度
    protected MiniPosition positionA, positionB;

    // 起始时间
    protected long initTime = System.currentTimeMillis();

    // 当前时间
    protected long currTime = initTime;

    // 渲染周期（毫秒）
    protected long interval;

    // 渲染进度
    protected float rate = 0;

    // 当前位置
    protected MiniPosition position = new MiniPosition();

    public TrackBase(long interval) {
        /**
         * @see TrackBase#TrackBase(MiniPosition, long)
         */
        this(new MiniPosition(), interval);
    }

    public TrackBase(MiniPosition positionB, long interval) {
        /**
         * @see TrackBase#TrackBase(MiniPosition, MiniPosition, long)
         */
        this(new MiniPosition(), positionB, interval);
    }

    public TrackBase(MiniPosition positionA, MiniPosition positionB, long interval) {
        this.positionA = positionA;
        this.positionB = positionB;
        this.interval = interval;

        init();
    }

    // 定义初始化操作
    protected void init() {
    }

    // 刷新
    public void update(long delta) {
        currTime += delta;

        computeRate();
        computePosition();
    }

    // 根据当前时间戳刷新
    public void update4Time() {
        update4Time(System.currentTimeMillis());
    }

    // 根据指定时间戳刷新
    public void update4Time(long time) {
        update(time - currTime);
    }

    // 计算比率
    protected void computeRate() {
        switch (getTrackMode()) {
            case NORMAL:
                computeRate4Normal();
                break;
            case REVERSE:
                computeRate4Reverse();
                break;
            case LOOP:
                computeRate4Loop();
                break;
        }
    }

    protected void computeRate4Normal() {
        rate = 1.0f * (currTime - initTime) / interval;
    }

    protected void computeRate4Reverse() {
        computeRate4Loop();
        if ((currTime - initTime) / interval % 2 != 0) {
            rate = 1.0f - rate;
        }
    }

    protected void computeRate4Loop() {
        rate = 1.0f * ((currTime - initTime) % interval) / interval;
    }

    // 取轨迹模式
    protected abstract TrackMode getTrackMode();

    // 计算位置
    protected abstract void computePosition();

    public void setInitTime(long initTime) {
        this.initTime = initTime;
    }

    public void setCurrTime(long currTime) {
        this.currTime = currTime;
    }

    // 取当前位置
    public MiniPosition getPosition() {
        return position;
    }
}