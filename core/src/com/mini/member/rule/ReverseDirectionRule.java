package com.mini.member.rule;

import com.mini.member.AutomaticActRule;
import com.mini.member.GameSprite;
import com.mini.member.status.GameSpriteDirection;

/**
 * 反向规则
 */
public class ReverseDirectionRule implements AutomaticActRule {

    private Float minScopeX, maxScopeX, minScopeY, maxScopeY;

    private Float minVelocityX, maxVelocityX, minVelocityY, maxVelocityY;

    @Override
    public void react(GameSprite gameSprite) {
        // 范围矫正
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
        if (minScopeY != null) {
            if (gameSprite.getPosY() <= minScopeY) {
                gameSprite.setDirection(GameSpriteDirection.U);
            }
        }
        if (maxScopeY != null) {
            if (gameSprite.getPosY() >= maxScopeY) {
                gameSprite.setDirection(GameSpriteDirection.D);
            }
        }

        // 速度矫正
        if (minVelocityX != null) {
            if (gameSprite.getVelocityX() <= minVelocityX) {
                gameSprite.applyVelocity(minVelocityX, gameSprite.getVelocityY());
            }
        }
        if (maxVelocityX != null) {
            if (gameSprite.getVelocityX() >= maxVelocityX) {
                gameSprite.applyVelocity(maxVelocityX, gameSprite.getVelocityY());
            }
        }
        if (minVelocityY != null) {
            if (gameSprite.getVelocityY() <= minVelocityY) {
                gameSprite.applyVelocity(gameSprite.getVelocityX(), minVelocityY);
            }
        }
        if (maxVelocityY != null) {
            if (gameSprite.getVelocityY() >= maxVelocityY) {
                gameSprite.applyVelocity(gameSprite.getVelocityX(), maxVelocityY);
            }
        }
    }

    public void setMinScopeX(Float minScopeX) {
        this.minScopeX = minScopeX;
    }

    public void setMaxScopeX(Float maxScopeX) {
        this.maxScopeX = maxScopeX;
    }

    public void setMinScopeY(Float minScopeY) {
        this.minScopeY = minScopeY;
    }

    public void setMaxScopeY(Float maxScopeY) {
        this.maxScopeY = maxScopeY;
    }

    public void setMinVelocityX(Float minVelocityX) {
        this.minVelocityX = minVelocityX;
    }

    public void setMaxVelocityX(Float maxVelocityX) {
        this.maxVelocityX = maxVelocityX;
    }

    public void setMinVelocityY(Float minVelocityY) {
        this.minVelocityY = minVelocityY;
    }

    public void setMaxVelocityY(Float maxVelocityY) {
        this.maxVelocityY = maxVelocityY;
    }
}
