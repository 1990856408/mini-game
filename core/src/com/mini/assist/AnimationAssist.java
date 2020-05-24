package com.mini.assist;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.util.List;

/**
 * 动画辅助器
 */
public class AnimationAssist {

    public static Animation createAnimation(Texture texture, List<Bound> bounds, float frameDuration) {
        return createAnimation(texture, bounds, frameDuration, 0, null);
    }

    public static Animation createAnimation(Texture texture, List<Bound> bounds, float frameDuration, int flip, Animation.PlayMode mode) {
        TextureRegion[] regions = new TextureRegion[bounds.size()];
        for (int i = 0; i < regions.length; i++) {
            regions[i] = new TextureRegion(texture, bounds.get(i).x, bounds.get(i).y, bounds.get(i).w, bounds.get(i).h);
            regions[i].flip((flip & 1) == 1, (flip & 2) == 2);
        }
        Animation animation = new Animation(frameDuration, regions);
        if (mode != null) {
            animation.setPlayMode(mode);
        }
        return animation;
    }

    /**
     * 裁剪边界
     */
    public static class Bound {

        private int x, y, w, h;

        public Bound(int x, int y, int w, int h) {
            this.x = x;
            this.y = y;
            this.w = w;
            this.h = h;
        }
    }
}
