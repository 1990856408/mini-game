package com.mini.graph;

public abstract class TrackBase {

	protected MiniPosition positionA, positionB;

	protected long initTime;

	protected long currTime;

	protected long interval;

	public MiniPosition position;

	public TrackBase(MiniPosition positionA, MiniPosition positionB, long interval) {
		this.positionA = positionA;
		this.positionB = positionB;
		this.interval = interval;
	}

	public void update(long delta) {
		currTime += delta;
		computePosition();
	}

	public abstract void computePosition();
}
