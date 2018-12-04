package com.example.brownj45.floatySmp.feed;

import java.io.Serializable;
import java.util.ArrayList;

public class VideoFeed implements Serializable {
	
	private static final long serialVersionUID = 8743747202130232552L;
	private String title;
    private String lastUpdated;
	private ArrayList<VideoFeedItem> videos = new ArrayList<VideoFeedItem>();
	
    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
	
	public void addVideoItem(VideoFeedItem item) {
		this.videos.add(item);
	}
	
	public ArrayList<VideoFeedItem> getVideos() {
		return this.videos;
	}

	public String getLastUpdated() {
		return lastUpdated;
	}

	public void setLastUpdated(String lastUpdated) {
		this.lastUpdated = lastUpdated;
	}

}