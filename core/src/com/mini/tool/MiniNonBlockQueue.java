package com.mini.tool;

import java.util.LinkedList;
import java.util.Queue;

/**
 * 线程安全的迷你非阻塞队列
 *
 * @param <T>
 */
public class MiniNonBlockQueue<T> {

    private Queue<T> queue = new LinkedList<>();

    // 上锁状态
    private boolean locked;

    // 锁持有者
    private Object lockHolder;

    /**
     * 追加元素
     *
     * @param t
     * @return
     */
    public synchronized boolean offer(T t) {
        if (locked) {
            return false;
        }

        queue.offer(t);

        return true;
    }

    /**
     * 当前锁持有者追加元素
     *
     * @param t
     * @param lockHolder
     * @return
     */
    public synchronized boolean offer(T t, Object lockHolder) {
        if (locked) {
            if (lockHolder == null) {
                throw new NullPointerException();
            }
            if (!this.lockHolder.equals(lockHolder)) {
                return false;
            }
        }

        queue.offer(t);

        return true;
    }

    /**
     * 追加元素且上锁
     *
     * @param t
     * @param lockHolder
     * @return
     */
    public synchronized boolean offerAndLock(T t, Object lockHolder) {
        if (locked) {
            if (lockHolder == null) {
                throw new NullPointerException();
            }
            if (!this.lockHolder.equals(lockHolder)) {
                return false;
            }
        } else {
            if (lockHolder == null) {
                throw new NullPointerException();
            }
            locked = true;
            this.lockHolder = lockHolder;
        }

        queue.offer(t);

        return true;
    }

    public synchronized T poll() {
        return queue.poll();
    }

    /**
     * 尝试上锁
     *
     * @param lockHolder
     * @return
     */
    public synchronized boolean lock(Object lockHolder) {
        if (lockHolder == null) {
            throw new NullPointerException();
        }
        if (locked) {
            return false;
        }

        this.lockHolder = lockHolder;
        locked = true;

        return true;
    }

    /**
     * 强制上锁
     *
     * @param lockHolder
     * @return
     */
    public synchronized boolean forceLock(Object lockHolder) {
        if (lockHolder == null) {
            throw new NullPointerException();
        }

        this.lockHolder = lockHolder;
        locked = true;

        return true;
    }

    /**
     * 释放锁
     *
     * @param lockHolder
     * @return
     */
    public synchronized boolean release(Object lockHolder) {
        if (!locked) {
            return true;
        }
        if (lockHolder == null) {
            throw new NullPointerException();
        }
        if (!this.lockHolder.equals(lockHolder)) {
            return false;
        }

        this.lockHolder = null;
        locked = false;

        return true;
    }

    /**
     * 强制释放锁
     *
     * @return
     */
    public synchronized boolean forceRelease() {
        if (!locked) {
            return true;
        }

        this.lockHolder = null;
        locked = false;

        return true;
    }

    /**
     * 当前锁状态
     *
     * @return
     */
    public synchronized boolean isLocked() {
        return locked;
    }

    /**
     * 当前队列内容
     *
     * @return
     */
    public Queue<T> getQueue() {
        return new LinkedList<>(queue);
    }
}