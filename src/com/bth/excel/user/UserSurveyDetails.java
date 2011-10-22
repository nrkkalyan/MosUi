package com.bth.excel.user;

import java.io.Serializable;
import java.util.LinkedList;

import com.bth.mos.manager.SurveyDetails;

public class UserSurveyDetails implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private SurveyDetails surveyDetails;
	private final LinkedList<UserVideo> userVideoList = new LinkedList<UserVideo>();

	public void setSurveyDetails(SurveyDetails surveyDetails) {
		this.surveyDetails = surveyDetails;
	}

	public SurveyDetails getSurveyDetails() {
		return surveyDetails;
	}

	public LinkedList<UserVideo> getUserVideoList() {
		return userVideoList;
	}

	public void addToUserVideoList(UserVideo userVideo) {
		userVideoList.add(userVideo);
	}

	public void addAllUserVideo(LinkedList<UserVideo> userVideos) {
		if (userVideos == null) {
			throw new UnsupportedOperationException("UserVideos is null");
		}
		userVideoList.clear();
		userVideoList.addAll(userVideos);
	}
}
