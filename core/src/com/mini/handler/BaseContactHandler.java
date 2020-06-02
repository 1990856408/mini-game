package com.mini.handler;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.mini.member.MiniUserData;

import java.util.HashMap;
import java.util.Map;

/**
 * 基础的体积碰撞处理器
 */
public abstract class BaseContactHandler implements ContactListener {

    private Map<String, Map<String, MiniContactReaction>> miniContactReactions = new HashMap<>();

    @Override
    public final void beginContact(Contact contact) {
        Object dataA = contact.getFixtureA().getUserData();
        Object dataB = contact.getFixtureB().getUserData();
        if (!(dataA instanceof MiniUserData) || !(dataB instanceof MiniUserData)) {
            return;
        }
        beginContact((MiniUserData) dataA, (MiniUserData) dataB);
    }

    private void beginContact(MiniUserData dataA, MiniUserData dataB) {
        beginContact(dataA, dataB.name);
        beginContact(dataB, dataA.name);
    }

    private void beginContact(MiniUserData data, String name) {
        Map<String, MiniContactReaction> miniContactReactionMap = miniContactReactions.get(data.name);
        if (miniContactReactionMap == null) {
            return;
        }
        MiniContactReaction miniContactReaction = miniContactReactionMap.get(name);
        if (miniContactReaction == null) {
            return;
        }
        miniContactReaction.reactBegin(data);
    }

    @Override
    public final void endContact(Contact contact) {
        Object dataA = contact.getFixtureA().getUserData();
        Object dataB = contact.getFixtureB().getUserData();
        if (!(dataA instanceof MiniUserData) || !(dataB instanceof MiniUserData)) {
            return;
        }
        endContact((MiniUserData) dataA, (MiniUserData) dataB);
    }

    private void endContact(MiniUserData dataA, MiniUserData dataB) {
        endContact(dataA, dataB.name);
        endContact(dataB, dataA.name);
    }

    private void endContact(MiniUserData data, String name) {
        Map<String, MiniContactReaction> miniContactReactionMap = miniContactReactions.get(data.name);
        if (miniContactReactionMap == null) {
            return;
        }
        MiniContactReaction miniContactReaction = miniContactReactionMap.get(name);
        if (miniContactReaction == null) {
            return;
        }
        miniContactReaction.reactEnd(data);
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }

    /**
     * 配置碰撞响应
     *
     * @param name
     * @param name2
     * @param reaction 响应事件
     */
    public void configMiniContactReaction(String name, String name2, MiniContactReaction reaction) {
        Map<String, MiniContactReaction> reactionMap = miniContactReactions.get(name);
        if (reactionMap == null) {
            reactionMap = new HashMap<>();
        }
        reactionMap.put(name2, reaction);

        miniContactReactions.put(name, reactionMap);
    }

    public void configMiniContactReactions(String name, Map<String, MiniContactReaction> reactionMap) {
        miniContactReactions.put(name, reactionMap);
    }

    public void configMiniContactReactions(Map<String, Map<String, MiniContactReaction>> reactions) {
        miniContactReactions.putAll(reactions);
    }
}