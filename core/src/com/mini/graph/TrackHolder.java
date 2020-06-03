package com.mini.graph;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public class TrackHolder {

	private List<TrackBase> tracks = new ArrayList<>();

	private List<Long> intervals = new ArrayList<>();

	private int index = 0;

	private long sumInterval = 0;

	public void putTrack(TrackBase track, long interval) {
		tracks.add(track);
		if (tracks.size() < 2) {
			intervals.add(interval);
		} else {
			intervals.add(intervals.get(intervals.size() - 2) + interval);
		}
	}

	public void putTracks(LinkedHashMap<TrackBase, Long> trackMap) {
	}
}
