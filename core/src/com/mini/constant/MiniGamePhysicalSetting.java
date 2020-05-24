package com.mini.constant;

/**
 * 物理配置
 */
public interface MiniGamePhysicalSetting {

    /**
     * @see MiniGameScreenSetting#VIEW_RATE
     */
    float VIEW_RATE = 0.01f;
    // 视距宽高
    float VIEW_W = MiniGameScreenSetting.VIEW_W * VIEW_RATE, VIEW_H = MiniGameScreenSetting.VIEW_H * VIEW_RATE;
    // 成员视图比例
    float MEMBER_VIEW_RATE = VIEW_RATE * 0.5f;

    // 重力
    float GRAVITY = 9.8f;
    // 地板摩擦力
    float FLOOR_FRICTION = 0.4f;

    // 时间步
    float TIME_STEP = 1.0f / 60.0f;
}
