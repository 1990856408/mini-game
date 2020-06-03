package com.mini.graph;

import com.alibaba.fastjson.JSON;

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

	public static void main(String[] args) {
		MiniPosition positionA = new MiniPosition(0, 0);
		MiniPosition positionB = new MiniPosition(100, 100);
		TrackCircle track = new TrackCircle(positionA, positionB, 100);
		track.update(25);
		System.out.println(JSON.toJSONString(track.getPosition()));
	}
}
