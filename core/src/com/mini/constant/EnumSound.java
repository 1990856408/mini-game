package com.mini.constant;

// TODO
public enum EnumSound {
    JUMP, SUFFER_HARM, FLAME, BULLET, SCORE, BURN, TRANSFORM;

    public static String path(EnumSound enumSound) {
        switch (enumSound) {
            case JUMP: // 跳跃
                return "sounds_small/jump.ogg";
            case SUFFER_HARM: // 受伤
                return "sounds_small/suffer_harm.ogg";
            case FLAME:  // 火焰
                return "sounds_small/flame.ogg";
            case BULLET: // 子弹
                return "sounds_small/bullet.ogg";
            case SCORE: // 吃金币
                return "sounds_small/score.ogg";
            case BURN: // 暴走
                return "sounds_small/burn.ogg";
            case TRANSFORM: // 场景切换
                return "sounds_small/transform.ogg";
            default:
                throw new NullPointerException();
        }
    }
}
