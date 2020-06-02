package com.mini.handler;

import java.lang.reflect.Method;
import java.util.*;

/**
 * @Author: zhaojn
 * @Date: 2020/5/31 14:47
 * <p>
 * 体积碰撞事件建造者
 */
public class MiniContactReactionBuilder {

    /**
     * 体积碰撞名称
     * <p>
     * 函数：public String getMiniContactName();
     *
     * @see MiniContactReactionCapable#getMiniContactName()
     */
    public static final String MethodGetMiniContactName = "getMiniContactName";

    /**
     * 体积碰撞函数集合
     * <p>
     * 函数：public Map<String, MiniContactReaction> getMiniContactReactionMap();
     *
     * @see MiniContactReactionCapable#getMiniContactReactionMap()
     */
    public static final String MethodGetMiniContactReactionMap = "getMiniContactReactionMap";

    /**
     * 待扫描的类集合，集合中的元素需实现上述两个函数
     *
     * @see MiniContactReactionCapable
     */
    private Set<Class<?>> miniContactReactionClasses = new LinkedHashSet<>();

    public void load(Class<?> c) {
        miniContactReactionClasses.add(c);
    }

    public void load(Collection<Class<?>> cs) {
        miniContactReactionClasses.addAll(cs);
    }

    public void load(Class<?>... cs) {
        miniContactReactionClasses.addAll(Arrays.asList(cs));
    }

    /**
     * 构建体积碰撞事件集合
     *
     * @return
     */
    public Map<String, Map<String, MiniContactReaction>> build() {
        Map<String, Map<String, MiniContactReaction>> miniContactReactions = new HashMap<>();

        for (Class<?> gameSpriteClass : miniContactReactionClasses) {
            String miniContactName = null;
            Map<String, MiniContactReaction> miniContactReactionMap = null;
            try {
                Method methodGetMiniContactName = gameSpriteClass.getMethod(MethodGetMiniContactName);
                miniContactName = (String) methodGetMiniContactName.invoke(null);

                Method methodGetMiniContactReactionMap = gameSpriteClass.getMethod(MethodGetMiniContactReactionMap);
                miniContactReactionMap = (Map<String, MiniContactReaction>) methodGetMiniContactReactionMap.invoke(null);
            } catch (Exception e) {
                System.err.println(MiniContactReactionBuilder.class.getName() + " : " + gameSpriteClass.getName());
                continue;
            }

            miniContactReactions.put(miniContactName, miniContactReactionMap);
        }

        return miniContactReactions;
    }
}