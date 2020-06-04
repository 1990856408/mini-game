package com.mini.graph;

import java.util.ArrayList;
import java.util.List;

public class TrackHolder {

    private List<TrackBase> tracks = new ArrayList<>();

    private List<Long> intervals = new ArrayList<>();

    private int index;

    protected long initTime;

    protected long currTime;

    public void putTrack(TrackBase track) {
        putTrack(track, track.interval);
    }

    public void putTrack(TrackBase track, long interval) {
        tracks.add(track);
        if (tracks.size() < 2) {
            intervals.add(interval);
        } else {
            intervals.add(intervals.get(intervals.size() - 2) + interval);
        }
    }

    public void reset() {
        index = 0;
        initTime = System.currentTimeMillis();
        currTime = initTime;
    }

    public void update(long delta) {
        if (index >= tracks.size()) {
            return;
        }

        currTime += delta;

        do {
            if ((currTime - initTime) <= intervals.get(index)) {
                break;
            }
            TrackBase track = tracks.get(index);
            if (index == 0) {
                track.setInitTime(initTime);
                track.update(intervals.get(index));
            } else {
                track.setInitTime(initTime + intervals.get(index - 1));
                track.update(intervals.get(index) - intervals.get(index - 1));
                track.getPosition().x = tracks.get(index - 1).getPosition().x + track.getPosition().x;
                track.getPosition().y = tracks.get(index - 1).getPosition().y + track.getPosition().y;
            }
            index++;
        } while (index < tracks.size());

        if (index >= tracks.size()) {
            // todo
            return;
        }

        TrackBase track = tracks.get(index);
        if (index == 0) {
            track.setInitTime(initTime);
            track.update(delta);
        } else {
            track.setInitTime(initTime + intervals.get(index - 1));
            track.update(currTime - intervals.get(index - 1));
            track.getPosition().x = tracks.get(index - 1).getPosition().x + track.getPosition().x;
            track.getPosition().y = tracks.get(index - 1).getPosition().y + track.getPosition().y;
        }
    }

    public void update4Time() {
        update4Time(System.currentTimeMillis());
    }

    public void update4Time(long time) {
        update(time - currTime);
    }
}