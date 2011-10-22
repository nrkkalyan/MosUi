package com.bth.mos.manager;

import java.io.File;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import com.bth.mos.persistence.dao.UserDao;
import com.bth.mos.persistence.dao.UserDaoBean;
import com.bth.mos.persistence.dao.VideoDao;
import com.bth.mos.persistence.dao.VideoDaoBean;
import com.bth.mos.persistence.dao.VideoGradingDao;
import com.bth.mos.persistence.dao.VideoGradingDaoBean;
import com.bth.mos.persistence.entity.User;
import com.bth.mos.persistence.entity.Video;
import com.bth.mos.persistence.entity.VideoGrading;

public class UserDetailsManager {

	private final UserDao mUserDao = new UserDaoBean();
	private final SurveyManager mSurveyManager = new SurveyManager();
	private final VideoDao mVideoDao = new VideoDaoBean();
	private final VideoGradingDao mVideoGradingDao = new VideoGradingDaoBean();

	public void saveUserDetails(User entity) throws Exception {
		entity.setName(mUserDao.getNextUserName());
		mUserDao.createEntity(entity);
	}

	public SurveyDetails getCurrentSurveyDetails() throws Exception {
		String currentSurveyName = mSurveyManager.getCurrentSurveyName();
		return mSurveyManager.findSurveyByName(currentSurveyName);
	}

	public Map<String, List<Video>> getVideoFiles(int surveyId) throws Exception {
		List<Video> videoList = mVideoDao.findVideosBySurveyId(surveyId);
		if (videoList.isEmpty()) {
			throw new IllegalArgumentException("No Videos are available for survey. Please contact Administrator.");
		}

		Map<String, List<Video>> videoMaps = new HashMap<String, List<Video>>();

		Set<String> fileDir = new HashSet<String>();
		for (Video video : videoList) {
			File file = new File(video.getUrl());
			String dir = file.getParent();
			fileDir.add(dir);
		}

		for (String dirPath : fileDir) {
			List<Video> videos = new LinkedList<Video>();
			for (Video video : videoList) {
				File file = new File(video.getUrl());
				String dir = file.getParent();
				if (dir.equals(dirPath)) {
					videos.add(video);
				}
			}

			int i = -1;
			for (Video v : videos) {
				i++;
				String fileName = v.getFileName();
				String extension = fileName.substring(fileName.lastIndexOf("."));
				if (fileName.equalsIgnoreCase("original" + extension)) {
					break;
				}
			}

			// Shuffle the videos except the original video.
			Video originaVideo = videos.remove(i);
			Collections.shuffle(videos, new Random());
			videos.add(0, originaVideo);
			videoMaps.put(dirPath, videos);
		}
		System.out.println("*************************");
		for (String key : videoMaps.keySet()) {
			List<Video> list = videoMaps.get(key);
			for (Video v : list) {
				System.out.println(v.getUrl());
			}
			System.out.println("--------------------------");
		}
		return videoMaps;
	}

	public void gradeVideo(Video currentVideo, SurveyDetails currentSurveyDetails, User user, int grade) throws Exception {
		VideoGrading entity = new VideoGrading();
		entity.setFk_survey_id(currentSurveyDetails.getSurvey().getId());
		entity.setFk_user_id(user.getId());
		entity.setFk_video_id(currentVideo.getId());
		entity.setGrade(grade);

		System.out.println("******************************");
		System.out.println("Saving Survey Grading");
		System.out.println("Video " + currentVideo.getFileName());
		System.out.println("User " + user.getId());
		mVideoGradingDao.createEntity(entity);
		System.out.println("VideoGrading Done :" + entity);
		System.out.println("******************************");

	}

}
