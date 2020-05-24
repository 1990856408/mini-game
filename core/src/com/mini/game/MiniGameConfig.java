package com.mini.game;

import com.alibaba.fastjson.JSONObject;
import com.mini.constant.MiniGameScreenSetting;

/**
 * 全局游戏配置 TODO
 */
public class MiniGameConfig {

    public static String ScreenSettingViewW = "screenSettingViewW";
    public static String ScreenSettingViewH = "screenSettingViewH";

    private static JSONObject jsonObject;

    public static void load() {
    }

    public static int getScreenSettingViewW() {
        if (jsonObject == null || jsonObject.get(ScreenSettingViewW) == null) {
            return MiniGameScreenSetting.VIEW_W;
        }
        return jsonObject.getInteger(ScreenSettingViewW);
    }

    public static int getScreenSettingViewH() {
        if (jsonObject == null || jsonObject.get(ScreenSettingViewH) == null) {
            return MiniGameScreenSetting.VIEW_W;
        }
        return jsonObject.getInteger(ScreenSettingViewH);
    }
}
