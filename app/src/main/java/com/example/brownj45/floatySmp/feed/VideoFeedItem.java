package com.example.brownj45.floatySmp.feed;

import java.io.Serializable;

public class VideoFeedItem implements Serializable{

	private static final long serialVersionUID = 1L;
	private String id = "";
	private String section = "";
	private String title = "";
	private String description = "";
	private String indexImage = "";
	private String playlistUrl = "";


	public VideoFeedItem() {
	}

	public VideoFeedItem(String title, String playlist) {
		this.title = title;
		this.playlistUrl = playlist;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getSection() {
		return section;
	}
	public void setSection(String section) {
		this.section = section;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getIndexImage() {
		return indexImage;
	}
	public void setIndexImage(String indexImage) {
		this.indexImage = indexImage;
	}
	public String getPlaylistUrl() {
		return playlistUrl;
	}
	public void setPlaylistUrl(String playlistUrl) {
		this.playlistUrl = playlistUrl;
	}
	
    @Override
    public String toString() {
        return title;
    }
	

}
