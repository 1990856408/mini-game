package com.mini.handler;

import com.mini.member.MiniUserData;

/**
 * 体积碰撞处理函数
 */
public interface MiniContactReaction {
    default void reactBegin(MiniUserData data) {
    }

    default void reactEnd(MiniUserData data) {
    }
}
