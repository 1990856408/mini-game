package com.mini.member.status;

public interface GameSpriteStatus {

    // 持有此状态时可否重定向
    boolean canRedirect();

    // 持有此状态时可否施加力
    boolean canApplyForceToCenter();
}