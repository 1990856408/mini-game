package com.mini.graph;

import java.util.ArrayList;
import java.util.List;

/**
 * 轨迹持有者
 */
public class TrackHolder {

    // 轨迹数组
    private List<TrackBase> tracks = new ArrayList<>();

    // 渲染数组
    private List<Long> intervals = new ArrayList<>();

    // 当前渲染下标
    private int index;

    // 初始渲染时间
    private long initTime;

    // 当前渲染时间
    private long currTime;

    private TrackHolderAction action;

    public TrackHolder putTrack(TrackBase track) {
        return putTrack(track, track.interval);
    }

    /**
     * @param track    轨迹
     * @param interval 轨迹要求的渲染时间
     */
    public TrackHolder putTrack(TrackBase track, long interval) {
        tracks.add(track);
        if (tracks.size() == 1) {
            intervals.add(interval);
        } else {
            intervals.add(intervals.get(intervals.size() - 1) + interval);
        }

        return this;
    }

    /**
     * 重置轨迹持有者，重新开始计算轨迹位置
     *
     * @return
     */
    public TrackHolder reset() {
        index = 0;
        initTime = System.currentTimeMillis();
        currTime = initTime;

        return this;
    }

    /**
     * 刷新
     *
     * @param delta
     */
    public void update(long delta) {
        if (index >= tracks.size()) {
            return;
        }

        currTime += delta;

        do {
            if ((currTime - initTime) <= intervals.get(index)) {
                break;
            }

            // 保持之前的轨迹的一致性
            TrackBase track = tracks.get(index);
            if (index == 0) {
                track.setInitTime(initTime);
                track.setCurrTime(initTime);
                track.update4Time(initTime + intervals.get(index));
            } else {
                track.setInitTime(initTime + intervals.get(index - 1));
                track.setCurrTime(initTime + intervals.get(index - 1));
                track.update4Time(initTime + intervals.get(index));
                track.getPosition().x += tracks.get(index - 1).getPosition().x;
                track.getPosition().y += tracks.get(index - 1).getPosition().y;
            }

            index++;
        } while (index < tracks.size());

        if (index >= tracks.size()) {
            if (action != null) {
                action.doFinishAct(tracks.get(tracks.size() - 1));
            }
            return;
        }

        TrackBase track = tracks.get(index);
        if (index == 0) {
            track.setInitTime(initTime);
            track.setCurrTime(initTime);
            track.update4Time(currTime);
        } else {
            track.setInitTime(initTime + intervals.get(index - 1));
            track.setCurrTime(initTime + intervals.get(index - 1));
            track.update4Time(currTime);
            track.getPosition().x += tracks.get(index - 1).getPosition().x;
            track.getPosition().y += tracks.get(index - 1).getPosition().y;
        }
    }

    public void update4Time() {
        update4Time(System.currentTimeMillis());
    }

    public void update4Time(long time) {
        update(time - currTime);
    }

    /**
     * 取当前位置
     * 若轨迹链运动完毕，则取轨迹链中的最后一个轨迹的位置
     *
     * @return 若轨迹链中无轨迹，则返回空
     */
    public MiniPosition getPosition() {
        if (index >= tracks.size()) {
            if (tracks.size() >= 1) {
                return tracks.get(tracks.size() - 1).getPosition();
            } else {
                return null;
            }
        }
        return tracks.get(index).getPosition();
    }
}