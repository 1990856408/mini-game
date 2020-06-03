package com.mini.graph;

/**
 * 逆时针旋转
 */
public class TrackCircle extends TrackLine {

    protected float initAngle;

    public TrackCircle(MiniPosition positionA, MiniPosition positionB, long interval) {
        super(positionA, positionB, interval);
    }

    @Override
    protected void init() {
        super.init();
        initAngle = (float) Math.atan2(positionB.y - positionA.y, positionB.x - positionA.x);
    }

    @Override
    public void computePosition() {
        float transferAngle = initAngle + (float) Math.toRadians(360 * rate);
        position.x = (float) (positionA.x + distance * Math.cos(transferAngle));
        position.y = (float) (positionA.y + distance * Math.sin(transferAngle));
    }
}
