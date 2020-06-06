package com.mini.graph.track;

import com.mini.graph.MiniPosition;
import com.mini.graph.TrackBase;
import com.mini.graph.TrackMode;

/**
 * 线性轨迹
 */
public class TrackLine extends TrackBase {

    protected MiniPosition positionInit;

    public TrackLine(long interval) {
        /**
         * @see TrackLine#TrackLine(MiniPosition, long)
         */
        this(new MiniPosition(), interval);
    }

    public TrackLine(MiniPosition positionB, long interval) {
        /**
         * @see TrackLine#TrackLine(MiniPosition, MiniPosition, long)
         */
        this(new MiniPosition(), positionB, interval);
    }

    public TrackLine(MiniPosition positionA, MiniPosition positionB, long interval) {
        /**
         * @see TrackLine#TrackLine(MiniPosition, MiniPosition, long, MiniPosition)
         */
        this(positionA, positionB, interval, new MiniPosition());
    }

    public TrackLine(MiniPosition positionA, MiniPosition positionB, long interval, MiniPosition positionInit) {
        super(positionA, positionB, interval);
        this.positionInit = positionInit;
    }

    public TrackLine(MiniPosition positionB, long interval, MiniPosition positionInit) {
        /**
         * @see TrackLine#TrackLine(MiniPosition, MiniPosition, long, MiniPosition)
         */
        this(new MiniPosition(), positionB, interval, positionInit);
    }

    @Override
    protected TrackMode getTrackMode() {
        return TrackMode.NORMAL;
    }

    @Override
    public void computePosition() {
        position.x = positionInit.x + (positionB.x - positionA.x) * rate;
        position.y = positionInit.y + (positionB.y - positionA.y) * rate;
    }
}
