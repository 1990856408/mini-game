package com.mini.handler;

import com.mini.member.MiniUserData;

/**
 * 体积碰撞处理函数
 */
public interface MiniContactReaction {

    /**
     * 碰撞时回调该函数
     *
     * @param data
     */
    default void reactBegin(MiniUserData data) {
    }

    /**
     * 分离时回调该函数
     *
     * @param data
     */
    default void reactEnd(MiniUserData data) {
    }
}
