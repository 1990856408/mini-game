package com.mini.graph.track;

import com.mini.graph.MiniPosition;
import com.mini.graph.TrackBase;
import com.mini.graph.TrackMode;

/**
 * 环形轨迹，逆时针旋转
 */
public class TrackCircle extends TrackBase {

    protected float radius;

    protected float initAngle;

    public TrackCircle(MiniPosition positionA, MiniPosition positionB, long interval) {
        super(positionA, positionB, interval);
    }

    @Override
    protected void init() {
        radius = (float) Math.sqrt(Math.pow(positionB.x - positionA.x, 2) + Math.pow(positionB.y - positionA.y, 2));
        initAngle = (float) Math.atan2(positionB.y - positionA.y, positionB.x - positionA.x);
    }

    @Override
    protected TrackMode getTrackMode() {
        return TrackMode.LOOP;
    }

    @Override
    public void computePosition() {
        float transferAngle = initAngle + (float) Math.toRadians(360 * rate);
        position.x = (float) (positionA.x + radius * Math.cos(transferAngle));
        position.y = (float) (positionA.y + radius * Math.sin(transferAngle));
    }
}