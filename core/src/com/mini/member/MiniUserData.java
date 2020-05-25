package com.mini.member;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Fixture;

/**
 * 用作刚体和夹具的属性
 */
public class MiniUserData {

    public String name;

    public Body body;

    public Fixture fixture;

    public GameSprite mine;
}
