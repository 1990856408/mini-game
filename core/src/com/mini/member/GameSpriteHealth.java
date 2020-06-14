package com.mini.member;

import java.io.Serializable;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @Author: zhaojn
 * @Date: 2020/5/31 20:21
 * <p>
 * 游戏精灵健康值
 */
public class GameSpriteHealth implements Serializable {

    private static final long serialVersionUID = 3631382286100881839L;

    // 最大生命值、最大魔法值
    public AtomicLong MaxHP, MaxMP;

    // 生命值、魔法值
    public AtomicLong hp, mp;

    public GameSpriteHealth() {
        hp = new AtomicLong();
        mp = new AtomicLong();
        MaxHP = new AtomicLong(hp.get());
        MaxMP = new AtomicLong(mp.get());
    }

    public GameSpriteHealth(long hp, long mp) {
        this.hp = new AtomicLong(hp);
        this.mp = new AtomicLong(mp);
        MaxHP = new AtomicLong(hp);
        MaxMP = new AtomicLong(mp);
    }

    public long incHP() {
        return hp.incrementAndGet();
    }

    public long incMP() {
        return mp.incrementAndGet();
    }

    public long incHP(long delta) {
        return hp.addAndGet(delta);
    }

    public long incMP(long delta) {
        return mp.addAndGet(delta);
    }

    public long getMaxHP() {
        return MaxHP.get();
    }

    public long getMaxMP() {
        return MaxMP.get();
    }

    public long getHp() {
        return hp.get();
    }

    public long getMp() {
        return mp.get();
    }
}