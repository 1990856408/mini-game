package com.mini.member.strategy;

import com.mini.member.AutomaticActStrategy;
import com.mini.member.GameSprite;

/**
 * 速度策略
 */
public class ApplyVelocityStrategy implements AutomaticActStrategy {

    private GameSprite gameSprite;

    private float velocityX, velocityY;

    public ApplyVelocityStrategy(GameSprite gameSprite) {
        this.gameSprite = gameSprite;
    }

    @Override
    public void execute() {
        switch (gameSprite.getDirection()) {
            case U:
                gameSprite.applyVelocity(gameSprite.getVelocityX(), velocityY);
                break;
            case D:
                gameSprite.applyVelocity(gameSprite.getVelocityX(), -velocityY);
                break;
            case L:
                gameSprite.applyVelocity(-velocityX, gameSprite.getVelocityY());
                break;
            case R:
                gameSprite.applyVelocity(velocityX, gameSprite.getVelocityY());
                break;
        }
    }

    public void setVelocity(float velocityX, float velocityY) {
        this.velocityX = velocityX;
        this.velocityY = velocityY;
    }
}
