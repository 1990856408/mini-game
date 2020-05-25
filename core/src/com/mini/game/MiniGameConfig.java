package com.mini.game;

import com.alibaba.fastjson.JSONObject;
import com.mini.constant.MiniGamePhysicalSetting;
import com.mini.constant.MiniGameScreenSetting;

/**
 * 游戏配置 TODO
 */
public final class MiniGameConfig {

    public static String ScreenSetting = "screenSetting";
    public static String PhysicalSetting = "physicalSetting";

    public static String ViewRate = "viewRate";
    public static String ViewW = "viewW";
    public static String ViewH = "viewH";
    public static String FrameDuration = "frameDuration";
    public static String MemberViewRate = "memberViewRate";
    public static String Gravity = "gravity";
    public static String TimeStep = "timeStep";

    private static JSONObject jsonObject;

    private static JSONObject screenSetting;

    private static JSONObject physicalSetting;

    public static void load() {
        screenSetting = jsonObject.getJSONObject(ScreenSetting);
        physicalSetting = jsonObject.getJSONObject(PhysicalSetting);
    }

    /*
     * 图形世界
     */
    public static float getScreenSettingViewRate() {
        if (screenSetting == null || screenSetting.get(ViewRate) == null) {
            return MiniGameScreenSetting.VIEW_RATE;
        }
        return screenSetting.getFloat(ViewRate);
    }

    public static int getScreenSettingViewW() {
        if (screenSetting == null || screenSetting.get(ViewW) == null) {
            return MiniGameScreenSetting.VIEW_W;
        }
        return screenSetting.getInteger(ViewW);
    }

    public static int getScreenSettingViewH() {
        if (screenSetting == null || screenSetting.get(ViewH) == null) {
            return MiniGameScreenSetting.VIEW_H;
        }
        return screenSetting.getInteger(ViewH);
    }

    public static float getScreenSettingFrameDuration() {
        if (screenSetting == null || jsonObject.get(FrameDuration) == null) {
            return MiniGameScreenSetting.FRAME_DURATION;
        }
        return screenSetting.getFloat(FrameDuration);
    }

    /*
     * 物理世界
     */
    public static float getPhysicalSettingViewRate() {
        if (physicalSetting == null || physicalSetting.get(ViewRate) == null) {
            return MiniGamePhysicalSetting.VIEW_RATE;
        }
        return physicalSetting.getFloat(ViewRate);
    }

    public static float getPhysicalSettingViewW() {
        if (physicalSetting == null || physicalSetting.get(ViewW) == null) {
            return MiniGamePhysicalSetting.VIEW_W;
        }
        return physicalSetting.getFloat(ViewW);
    }

    public static float getPhysicalSettingViewH() {
        if (physicalSetting == null || physicalSetting.get(ViewH) == null) {
            return MiniGamePhysicalSetting.VIEW_H;
        }
        return physicalSetting.getFloat(ViewH);
    }

    public static float getPhysicalSettingMemberViewRate() {
        if (physicalSetting == null || physicalSetting.get(MemberViewRate) == null) {
            return MiniGamePhysicalSetting.MEMBER_VIEW_RATE;
        }
        return physicalSetting.getFloat(MemberViewRate);
    }

    public static float getPhysicalSettingGravity() {
        if (physicalSetting == null || physicalSetting.get(Gravity) == null) {
            return MiniGamePhysicalSetting.GRAVITY;
        }
        return physicalSetting.getFloat(Gravity);
    }

    public static float getPhysicalSettingTimeStep() {
        if (physicalSetting == null || physicalSetting.get(TimeStep) == null) {
            return MiniGamePhysicalSetting.TIME_STEP;
        }
        return physicalSetting.getFloat(TimeStep);
    }
}
