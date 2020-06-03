package com.mini.graph;

public abstract class TrackBase {

    protected MiniPosition positionA, positionB;

    // 起始时间
    protected long initTime;

    // 当前时间
    protected long currTime;

    // 渲染周期（毫秒）
    protected long interval;

    // 渲染进度
    protected float rate;

    /**
     * 渲染模式
     * 1.普通 -> -> ->
     * 2.反向 -> <-
     * 3.循环 ->> ->>
     */
    protected int mode = 3;

    protected MiniPosition position;

    public TrackBase(MiniPosition positionA, MiniPosition positionB, long interval) {
        this.positionA = positionA;
        this.positionB = positionB;
        this.initTime = System.currentTimeMillis();
        this.currTime = initTime;
        this.interval = interval;

        position = new MiniPosition();
        position.x = positionA.x;
        position.y = positionA.y;

        init();
    }

    protected abstract void init();

    public void update(long delta) {
        currTime += delta;

        switch (mode) {
            case 1:
                rate = 1.0f * (currTime - initTime) / interval;
                break;
            case 2:
                long x = (currTime - initTime) % (2 * interval);
                rate = (x > interval) ? (1 - 1.0f * (x % interval) / interval) : (1.0f * x / interval);
                break;
            case 3:
                rate = 1.0f * ((currTime - initTime) % interval) / interval;
                break;
        }

        computePosition();
    }

    public void update4Time() {
        update4Time(System.currentTimeMillis());
    }

    public void update4Time(long time) {
        update(time - currTime);
    }

    protected abstract void computePosition();

    public MiniPosition getPosition() {
        return position;
    }
}
