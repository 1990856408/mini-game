package com.mini.constant;

/**
 * 图形世界配置
 */
public interface MiniGameScreenSetting {

    /**
     * @see MiniGamePhysicalSetting#VIEW_RATE
     */
    float VIEW_RATE = 100;

    // 视距宽高
    int VIEW_W = 720, VIEW_H = 540;

    // 渲染帧间隔
    float FRAME_DURATION = 1.0f / 12f;
}
