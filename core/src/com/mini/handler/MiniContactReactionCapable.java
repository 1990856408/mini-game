package com.mini.handler;

import java.util.Map;

/**
 * @Author: zhaojn
 * @Date: 2020/5/31 14:32
 */
public interface MiniContactReactionCapable {

    String getMiniContactName();

    Map<String, MiniContactReaction> getMiniContactReactionMap();
}