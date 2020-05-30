package com.mini.tool;

import java.util.LinkedList;
import java.util.Queue;

public class MiniNonBlockQueue<T> {

    private Queue<T> queue = new LinkedList<>();

    private boolean locked;

    private Object lockHolder;

    public synchronized boolean offer(T t) {
        if (locked) {
            return false;
        }

        queue.offer(t);
        return true;
    }

    public synchronized T poll() {
        return queue.poll();
    }

    public synchronized boolean lock(Object lockHolder) {
        if (locked) {
            return false;
        }

        this.lockHolder = lockHolder;
        locked = true;

        return true;
    }

    public synchronized boolean forceLock(Object lockHolder) {
        this.lockHolder = lockHolder;
        locked = true;

        return true;
    }

    public synchronized boolean release(Object lockHolder) {
        if (!locked) {
            return true;
        }

        if (this.lockHolder.equals(lockHolder)) {
            this.lockHolder = null;
            locked = false;

            return true;
        }

        return false;
    }

    public synchronized boolean forceRelease() {
        if (!locked) {
            return true;
        }

        this.lockHolder = null;
        locked = false;

        return true;
    }

    public synchronized boolean isLocked() {
        return locked;
    }

    public Queue<T> getQueue() {
        return new LinkedList<>(queue);
    }
}