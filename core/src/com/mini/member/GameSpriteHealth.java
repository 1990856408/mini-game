package com.mini.member;

import java.io.Serializable;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @Author: zhaojn
 * @Date: 2020/5/31 20:21
 */
public class GameSpriteHealth implements Serializable {

    private static final long serialVersionUID = 3631382286100881839L;

    public AtomicLong hp, mp;

    public GameSpriteHealth() {
        hp = new AtomicLong();
        mp = new AtomicLong();
    }

    public GameSpriteHealth(long hp, long mp) {
        this.hp = new AtomicLong(hp);
        this.mp = new AtomicLong(mp);
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
}