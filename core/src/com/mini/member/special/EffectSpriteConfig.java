package com.mini.member.special;

/**
 * @Author: zhaojn
 * @Date: 2020/5/31 23:35
 * <p>
 * 特效配置
 */
public class EffectSpriteConfig {

    // 效果路径
    public String effectPath;

    // 效果相关的图片路径
    public String imagesPath;

    public EffectSpriteConfig() {
    }

    public EffectSpriteConfig(String effectPath, String imagesPath) {
        this.effectPath = effectPath;
        this.imagesPath = imagesPath;
    }
}