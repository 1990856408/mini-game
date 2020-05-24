package com.custom.member.constant;

public interface MemberFixtureAttribute {
    short MAP_FLOOR = 1 << 0;
    short MAP_MARGIN = 1 << 1;
    short PROTA = 1 << 2;
    short MARIO_BULLET = PROTA;
    short CHUNLI = PROTA;
    short CHUNLI_QI_GONG_BALL = CHUNLI;
    short DIAMOND = 1 << 3;
    short DUCK = 1 << 4;

    short _MAP_FLOOR = PROTA | DUCK;
    short _MAP_MARGIN = PROTA;
    short _PROTA = MAP_FLOOR | MAP_MARGIN | DIAMOND | DUCK;
    short _MARIO_BULLET = _PROTA - DIAMOND - DUCK;
    short _CHUNLI = _PROTA;
    short _CHUNLI_QI_GONG_BALL = _MARIO_BULLET;
    short _DIAMOND = PROTA;
    short _DUCK = MAP_FLOOR | PROTA;
}
