package com.mini.screen.stage;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.mini.member.GameSpriteHealth;
import org.apache.commons.lang3.StringUtils;

/**
 * @Author: zhaojn
 * @Date: 2020/6/14 23:01
 * 健康值画刷
 */
public class BrushHealth extends Label {

    public static int MAX_REPEAT = 20;

    private char element = '|';

    private GameSpriteHealth health;

    private int type;

    public BrushHealth(GameSpriteHealth health, int type) {
        super("", new LabelStyle(new BitmapFont(), type == 1 ? Color.RED : Color.BLUE));
        if (health == null) {
            throw new IllegalArgumentException();
        }
        if (type != 1 && type != 2) {
            throw new IllegalArgumentException();
        }

        this.health = health;
        this.type = type;
        setHeight(35);
        setFontScaleX(2.5f);
        setFontScaleY(1.5f);
        update();
    }

    public String getContent() {
        if (type == 1) {
            return StringUtils.repeat(element, (int) (1.0f * health.getHp() / health.getMaxHP() * MAX_REPEAT));
        }

        if (type == 2) {
            return StringUtils.repeat(element, (int) (1.0f * health.getMp() / health.getMaxMP() * MAX_REPEAT));
        }

        throw new IllegalArgumentException();
    }

    public void update() {
        setText(getContent());
    }
}