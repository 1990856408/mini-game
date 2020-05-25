package com.mini.member.monster;

import com.mini.member.AutomaticAct;
import com.mini.member.GameSprite;
import com.mini.member.rule.ReverseDirectionRule;

/**
 * 怪物精灵
 */
public abstract class MonsterSprite extends GameSprite implements AutomaticAct {

    protected ReverseDirectionRule ruleReverseDirection;

    @Override
    protected void init() {
        ruleReverseDirection = new ReverseDirectionRule();

        super.init();
    }

    @Override
    protected void update() {
        autoExecute();
        super.update();
    }

    @Override
    public void autoExecute() {

    }
}