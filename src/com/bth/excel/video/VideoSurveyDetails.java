package com.bth.excel.video;

import java.io.Serializable;
import java.util.LinkedList;

import com.bth.mos.manager.SurveyDetails;

public class VideoSurveyDetails implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private SurveyDetails surveyDetails;
	private final LinkedList<VideosPerUser> VideosPerUserList = new LinkedList<VideosPerUser>();

	public void setSurveyDetails(SurveyDetails surveyDetails) {
		this.surveyDetails = surveyDetails;
	}

	public SurveyDetails getSurveyDetails() {
		return surveyDetails;
	}

	public LinkedList<VideosPerUser> getVideosPerUserList() {
		return VideosPerUserList;
	}

	public void addToVideosPerUserList(VideosPerUser videosPerUser) {
		VideosPerUserList.add(videosPerUser);
	}

}
