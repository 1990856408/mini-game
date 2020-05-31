package com.custom.member.status;

import com.mini.member.status.GameSpriteStatus;
import com.mini.tool.MiniReplacer;

public enum ChunLiStatus implements MiniReplacer, GameSpriteStatus {
    QUIET(1 << 0, 1 << 1 | 1 << 3 | 1 << 4 | 1 << 5 | 1 << 6, true, true),
    WALK(1 << 1, 1 << 0 | 1 << 3 | 1 << 4 | 1 << 5 | 1 << 6, true, true),
    JUMP(1 << 2, 1 << 0 | 1 << 1 | 1 << 3 | 1 << 5, true, true),
    LANDFALL(1 << 3, 1 << 1 | 1 << 2 | 1 << 5, true, true),
    SQUAT(1 << 4, 1 << 0 | 1 << 1, true, true),
    CRACKED_FEET(1 << 5, 1 << 0 | 1 << 1 | 1 << 2 | 1 << 3, false, true),
    QI_GONG(1 << 6, 1 << 0 | 1 << 1, false, false);

    // 自身的种类
    long category;

    // 可替换
    long categoryReplace;

    // 可否重定向
    boolean canRedirect;

    // 可否施加力
    boolean canApplyForceToCenter;

    ChunLiStatus(long category, long categoryReplace, boolean canRedirect, boolean canApplyForceToCenter) {
        this.category = category;
        this.categoryReplace = categoryReplace;
        this.canRedirect = canRedirect;
        this.canApplyForceToCenter = canApplyForceToCenter;
    }

    @Override
    public long getCategory() {
        return category;
    }

    @Override
    public long getCategoryReplace() {
        return categoryReplace;
    }

    @Override
    public boolean canRedirect() {
        return canRedirect;
    }

    @Override
    public boolean canApplyForceToCenter() {
        return canApplyForceToCenter;
    }
}