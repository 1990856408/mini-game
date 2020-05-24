package com.mini.assist;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.mini.member.GameSprite;

/**
 * 用作刚体和夹具的属性
 */
public class CustomUserData {

    public String name;

    public Body body;

    public Fixture fixture;

    public GameSprite mine;
}
