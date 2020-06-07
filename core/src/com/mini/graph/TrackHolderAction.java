package com.mini.graph;

public interface TrackHolderAction {

    /**
     * 轨迹运动完成时的执行函数
     *
     * @param track 最后一个轨迹
     */
    void doFinishAct(TrackBase track);
}
