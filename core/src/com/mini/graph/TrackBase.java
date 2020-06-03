package com.mini.graph;

public abstract class TrackBase {

	protected MiniPosition positionA, positionB;

	// 起始时间
	protected long initTime;

	// 当前时间
	protected long currTime;

	// 运动时长
	protected long interval;

	// 当前比例
	protected float rate;

	// 是否循环
	protected boolean isLoop = true;

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

		if (isLoop) {
			rate = 1.0f * (currTime - initTime) % interval;
		} else {
			rate = 1.0f * (currTime - initTime) / interval;
		}

		computePosition();
	}

	public void update4Time() {
		update4Time(System.currentTimeMillis());
	}

	public void update4Time(long time) {
		update(time - initTime);
	}

	protected abstract void computePosition();

	public void setLoop(boolean loop) {
		isLoop = loop;
	}

	public MiniPosition getPosition() {
		return position;
	}
}
