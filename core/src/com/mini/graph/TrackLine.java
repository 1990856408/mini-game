package com.mini.graph;

public class TrackLine extends TrackBase {

	protected float distance;

	public TrackLine(MiniPosition positionA, MiniPosition positionB, long interval) {
		super(positionA, positionB, interval);
	}

	@Override
	protected void init() {
		distance = (float) Math.sqrt(Math.pow(positionB.x - positionA.x, 2) + Math.pow(positionB.y - positionA.y, 2));
	}

	@Override
	public void computePosition() {
		position.x = (positionB.x - positionA.x) * rate;
		position.y = (positionB.y - positionA.y) * rate;
	}
}
