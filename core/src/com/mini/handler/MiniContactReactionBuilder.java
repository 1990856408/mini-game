package com.mini.handler;

import java.lang.reflect.Method;
import java.util.*;

/**
 * @Author: zhaojn
 * @Date: 2020/5/31 14:47
 */
public class MiniContactReactionBuilder {

    public static final String MethodGetMiniContactName = "getMiniContactName";

    public static final String MethodGetMiniContactReactionMap = "getMiniContactReactionMap";

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