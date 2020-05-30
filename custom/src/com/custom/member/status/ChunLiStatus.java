package com.custom.member.status;

import com.mini.tool.MiniReplacer;

public enum ChunLiStatus implements MiniReplacer {
    QUIET(1 << 0, 1 << 1 | 1 << 3 | 1 << 4 | 1 << 5 | 1 << 6),
    WALK(1 << 1, 1 << 0),
    JUMP(1 << 2, 1 << 0 | 1 << 1 | 1 << 3),
    LANDFALL(1 << 3, 1 << 2),
    SQUAT(1 << 4, 1 << 0 | 1 << 1),
    CRACKED_FEET(1 << 5, 1 << 0 | 1 << 1),
    QI_GONG(1 << 6, 1 << 0 | 1 << 1);

    // 自身的种类
    long category;

    // 可替换
    long categoryReplace;

    ChunLiStatus(long category, long categoryReplace) {
        this.category = category;
        this.categoryReplace = categoryReplace;
    }

    @Override
    public long getCategory() {
        return category;
    }

    @Override
    public long getCategoryReplace() {
        return categoryReplace;
    }
}