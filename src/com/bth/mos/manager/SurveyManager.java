package com.bth.mos.manager;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import com.bth.excel.user.UserSurveyDetails;
import com.bth.excel.user.UserVideo;
import com.bth.excel.user.VideoDetails;
import com.bth.excel.video.UserGradeDetails;
import com.bth.excel.video.VideoSurveyDetails;
import com.bth.excel.video.VideosPerUser;
import com.bth.mos.MosConstants;
import com.bth.mos.persistence.dao.BinaryScaleDao;
import com.bth.mos.persistence.dao.BinaryScaleDaoBean;
import com.bth.mos.persistence.dao.CustomScaleDao;
import com.bth.mos.persistence.dao.CustomScaleDaoBean;
import com.bth.mos.persistence.dao.GlobalPropertiesDao;
import com.bth.mos.persistence.dao.GlobalPropertiesDaoBean;
import com.bth.mos.persistence.dao.SurveyDao;
import com.bth.mos.persistence.dao.SurveyDaoBean;
import com.bth.mos.persistence.dao.TimerDao;
import com.bth.mos.persistence.dao.TimerDaoBean;
import com.bth.mos.persistence.dao.UserDao;
import com.bth.mos.persistence.dao.UserDaoBean;
import com.bth.mos.persistence.dao.VideoDao;
import com.bth.mos.persistence.dao.VideoDaoBean;
import com.bth.mos.persistence.dao.VideoGradingDao;
import com.bth.mos.persistence.dao.VideoGradingDaoBean;
import com.bth.mos.persistence.entity.BinaryScale;
import com.bth.mos.persistence.entity.CustomScale;
import com.bth.mos.persistence.entity.GlobalProperties;
import com.bth.mos.persistence.entity.Survey;
import com.bth.mos.persistence.entity.Survey.ScaleType;
import com.bth.mos.persistence.entity.Timer;
import com.bth.mos.persistence.entity.User;
import com.bth.mos.persistence.entity.Video;
import com.bth.mos.persistence.entity.VideoGrading;

public class SurveyManager {

	private final SurveyDao mSurveyDao = new SurveyDaoBean();
	private final BinaryScaleDao mBinaryScaleDao = new BinaryScaleDaoBean();
	private final TimerDao mTimerDao = new TimerDaoBean();
	private final CustomScaleDao mCustomScaleDao = new CustomScaleDaoBean();
	private final VideoGradingDao mVideoGradingDao = new VideoGradingDaoBean();
	private final GlobalPropertiesDao mGlobalPropertiesDao = new GlobalPropertiesDaoBean();
	private final UserDao mUserDao = new UserDaoBean();
	private final VideoDao mVideoDao = new VideoDaoBean();

	public SurveyDetails findSurveyByName(String surveyName) {
		SurveyDetails surveyDetails = new SurveyDetails();
		try {

			Survey survey = mSurveyDao.findSurveyByName(surveyName);
			if (survey == null) {
				throw new RuntimeException("No Survey found. Please contact Administrator");
			}
			surveyDetails.setSurvey(survey);
			//
			Timer timer = mTimerDao.findById(survey.getFk_timer_id());
			surveyDetails.setTimer(timer);
			//
			if (survey.getScaleType() == ScaleType.BINARY_SCALE) {
				BinaryScale binaryScale = mBinaryScaleDao.findBySurveyId(survey.getId());
				surveyDetails.setBinaryScale(binaryScale);
			}
			//
			if (survey.getScaleType() == ScaleType.CUSTOM_SCALE) {
				CustomScale customScale = mCustomScaleDao.findBySurveyId(survey.getId());
				surveyDetails.setCustomScale(customScale);
			}

		} catch (Exception e) {
			throw new RuntimeException("Could not find survey by that name : " + surveyName);
		}

		return surveyDetails;
	}

	public UserSurveyDetails getCurrentSurveyResults() throws Exception {
		UserSurveyDetails userSurveyDetails = new UserSurveyDetails();
		String currentSurveyName = getCurrentSurveyName();
		SurveyDetails surveyDetails = findSurveyByName(currentSurveyName);
		userSurveyDetails.setSurveyDetails(surveyDetails);
		//
		Survey survey = surveyDetails.getSurvey();
		List<VideoGrading> videoGradings = mVideoGradingDao.findAllBySurveyId(survey.getId());

		Set<Integer> userIdSet = new LinkedHashSet<Integer>();
		// Finding list of unique Users participated in the survey
		for (VideoGrading vg : videoGradings) {
			userIdSet.add(vg.getFk_user_id());
		}

		for (int id : userIdSet) {
			User user = mUserDao.findById(id);
			UserVideo userVideo = new UserVideo();
			userVideo.setUser(user);
			userSurveyDetails.addToUserVideoList(userVideo);
			for (VideoGrading vg : videoGradings) {
				if (vg.getFk_user_id() == id) {
					VideoDetails videoDetails = new VideoDetails();
					Video video = mVideoDao.findById(vg.getFk_video_id());
					videoDetails.setVideo(video);
					videoDetails.setGrade(vg.getGrade());
					userVideo.addVideo(videoDetails);
				}
			}
		}

		//

		//

		return userSurveyDetails;

	}

	public UserSurveyDetails getCurrentSurveyResultsByUserId(int userId) throws Exception {
		UserSurveyDetails userSurveyDetails = new UserSurveyDetails();
		String currentSurveyName = getCurrentSurveyName();
		SurveyDetails surveyDetails = findSurveyByName(currentSurveyName);
		userSurveyDetails.setSurveyDetails(surveyDetails);
		//
		Survey survey = surveyDetails.getSurvey();
		List<VideoGrading> videoGradings = mVideoGradingDao.findAllBySurveyId(survey.getId());

		// Set<Integer> userIdSet = new HashSet<Integer>();
		// // Finding list of unique Users participated in the survey
		// for (VideoGrading vg : videoGradings) {
		// if (vg.getFk_user_id() == userId) {
		// userIdSet.add(vg.getFk_user_id());
		// }
		// }

		// for (int id : userIdSet) {
		User user = mUserDao.findById(userId);
		UserVideo userVideo = new UserVideo();
		userVideo.setUser(user);
		userSurveyDetails.addToUserVideoList(userVideo);
		for (VideoGrading vg : videoGradings) {
			if (vg.getFk_user_id() == userId) {
				VideoDetails videoDetails = new VideoDetails();
				Video video = mVideoDao.findById(vg.getFk_video_id());
				videoDetails.setVideo(video);
				videoDetails.setGrade(vg.getGrade());
				userVideo.addVideo(videoDetails);
			}
		}

		//

		//

		return userSurveyDetails;

	}

	public VideoSurveyDetails getSurveyResultsPerVideo() throws Exception {
		VideoSurveyDetails userSurveyDetails = new VideoSurveyDetails();
		String currentSurveyName = getCurrentSurveyName();
		SurveyDetails surveyDetails = findSurveyByName(currentSurveyName);
		userSurveyDetails.setSurveyDetails(surveyDetails);
		//
		Survey survey = surveyDetails.getSurvey();
		List<VideoGrading> videoGradings = mVideoGradingDao.findAllBySurveyId(survey.getId());

		Set<Integer> videoIdSet = new HashSet<Integer>();
		// Finding list of unique Users participated in the survey
		for (VideoGrading vg : videoGradings) {
			videoIdSet.add(vg.getFk_video_id());
		}

		for (int id : videoIdSet) {
			Video video = mVideoDao.findById(id);
			VideosPerUser videosPerUser = new VideosPerUser();
			videosPerUser.setVideo(video);
			userSurveyDetails.addToVideosPerUserList(videosPerUser);
			for (VideoGrading vg : videoGradings) {
				if (vg.getFk_video_id() == id) {
					UserGradeDetails userGradeDetails = new UserGradeDetails();
					User user = mUserDao.findById(vg.getFk_user_id());
					userGradeDetails.setUser(user);
					userGradeDetails.setGrade(vg.getGrade());
					videosPerUser.addToUserVideoList(userGradeDetails);
				}
			}
		}

		return userSurveyDetails;

	}

	public String getCurrentSurveyName() throws Exception {
		GlobalProperties globalProperties = mGlobalPropertiesDao.findByKey(MosConstants.CURRENT_SURVEY_NAME);
		if (globalProperties == null || globalProperties.getValue() == null || globalProperties.getValue().isEmpty()) {
			throw new UnsupportedOperationException("Survey not set. Please contact your administrator");
		}
		//
		String currentSurveyName = globalProperties.getValue();
		return currentSurveyName;
	}

}
