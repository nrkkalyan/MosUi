package com.bth.excel.user;

import java.io.Serializable;
import java.util.LinkedList;

import com.bth.mos.persistence.entity.User;

public class UserVideo implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private User user;
	private final LinkedList<VideoDetails> videoList = new LinkedList<VideoDetails>();

	public void setUser(User user) {
		this.user = user;
	}

	public User getUser() {
		return user;
	}

	public LinkedList<VideoDetails> getVideoList() {
		return videoList;
	}

	public void addVideo(VideoDetails video) {
		videoList.add(video);
	}

	public void addOnlyOneVideo(VideoDetails video) {
		videoList.clear();
		videoList.add(video);
	}

	public void addAllVideo(LinkedList<VideoDetails> videoList2) {
		if (videoList2 == null) {
			throw new IllegalArgumentException("Video List is null");
		}
		this.videoList.clear();
		this.videoList.addAll(videoList2);
	}

}
