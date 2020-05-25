package com.mini.member.rule;

import com.mini.member.AutomaticActRule;
import com.mini.member.GameSprite;
import com.mini.member.status.GameSpriteDirection;

public class ReverseDirectionRule implements AutomaticActRule {

    private Float minScopeX, maxScopeX, minScopeY, maxScopeY;

    private Float minVelocityX, maxVelocityX, minVelocityY, maxVelocityY;

    @Override
    public void react(GameSprite gameSprite) {
        if (minScopeX != null) {
            if (gameSprite.getPosX() <= minScopeX) {
                gameSprite.setDirection(GameSpriteDirection.R);
            }
        }
        if (maxScopeX != null) {
            if (gameSprite.getPosX() >= maxScopeX) {
                gameSprite.setDirection(GameSpriteDirection.L);
            }
        }
    }

    public void setMinScopeX(Float minScopeX) {
        this.minScopeX = minScopeX;
    }

    public void setMaxScopeX(Float maxScopeX) {
        this.maxScopeX = maxScopeX;
    }
}
