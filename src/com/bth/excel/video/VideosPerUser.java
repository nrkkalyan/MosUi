package com.bth.excel.video;

import java.io.Serializable;
import java.util.LinkedList;

import com.bth.mos.persistence.entity.Video;

public class VideosPerUser implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final LinkedList<UserGradeDetails> userGradeDetailsList = new LinkedList<UserGradeDetails>();
	private Video video;

	public void setVideo(Video video) {
		this.video = video;
	}

	public Video getVideo() {
		return video;
	}

	public LinkedList<UserGradeDetails> getUserGradeDetails() {
		return userGradeDetailsList;
	}

	public void addToUserVideoList(UserGradeDetails userVideo) {
		userGradeDetailsList.add(userVideo);
	}

	public void addAllUserVideoList(LinkedList<UserGradeDetails> userVideo) {
		userGradeDetailsList.addAll(userVideo);
	}

}
