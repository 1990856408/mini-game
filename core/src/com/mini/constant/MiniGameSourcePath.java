package com.mini.constant;

import java.util.Arrays;
import java.util.List;

/**
 * 资源加载路径
 */
public interface MiniGameSourcePath {

    // 角色
    String PATH_MEMBERS = "members";
    // 菜单
    String PATH_MENUS = "menus";
    // 怪物
    String PATH_MONSTERS = "monsters";
    // 装饰
    String PATH_WIDGETS = "widgets";
    // 图片路径
    List<String> PICTURE_PATHS = Arrays.asList(PATH_MEMBERS, PATH_MENUS, PATH_MONSTERS, PATH_WIDGETS);

    // 音效
    String PATH_SOUNDS = "sounds";
    // 音效（小）
    String PATH_SOUNDS_SMALL = "sounds_small";
    // 音乐路径
    List<String> SOUND_PATHS = Arrays.asList(PATH_SOUNDS, PATH_SOUNDS_SMALL);
}
