package com.bth.mos.manager;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

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
import com.bth.mos.persistence.dao.VideoDao;
import com.bth.mos.persistence.dao.VideoDaoBean;
import com.bth.mos.persistence.entity.BinaryScale;
import com.bth.mos.persistence.entity.CustomScale;
import com.bth.mos.persistence.entity.GlobalProperties;
import com.bth.mos.persistence.entity.Survey;
import com.bth.mos.persistence.entity.Timer;
import com.bth.mos.persistence.entity.Video;

public class AdminSettingsManager {

	private final GlobalPropertiesDao mGlobalPropertiesDao = new GlobalPropertiesDaoBean();
	private final SurveyDao mSurveyDao = new SurveyDaoBean();
	private final BinaryScaleDao mBinaryScaleDao = new BinaryScaleDaoBean();
	private final TimerDao mTimerDao = new TimerDaoBean();
	private final VideoDao mVideoDao = new VideoDaoBean();
	private final CustomScaleDao mCustomScaleDao = new CustomScaleDaoBean();
	private final SurveyManager mSurveyManager = new SurveyManager();

	public void saveSettings(Survey survey, Timer timer, BinaryScale binaryScale, CustomScale customScale) throws Exception {

		final File videoDir = new File(survey.getVideoLocation());
		if (!videoDir.exists()) {
			throw new IllegalArgumentException("Video location not found.");
		}
		validateIfOriginalVideoExists(videoDir);
		File[] listFiles = videoDir.listFiles();

		Survey surveyDetails = mSurveyDao.findSurveyByName(survey.getSurveyName());
		// Create survey in DB if there is no survey exists in DB by that
		// surveyname
		if (surveyDetails == null) {
			mTimerDao.createEntity(timer);
			survey.setFk_timer_id(timer.getId());
			// Create the Survey Entity
			mSurveyDao.createEntity(survey);
			// Saving the Binary scale
			if (binaryScale != null && Survey.ScaleType.BINARY_SCALE == survey.getScaleType()) {
				binaryScale.setFk_survey_id(survey.getId());
				mBinaryScaleDao.createEntity(binaryScale);
			}
			// Saving the Custom Scale
			if (customScale != null && Survey.ScaleType.CUSTOM_SCALE == survey.getScaleType()) {
				customScale.setFk_survey_id(survey.getId());
				mCustomScaleDao.createEntity(customScale);
			}

			/*
			 * For loop newly introduced from java 6
			 */
			for (File videoFile : listFiles) {
				traverseVideos(videoFile, survey);
			}

		} else {
			throw new IllegalArgumentException("Survey Name already exists. Please choose a new survey name.");
		}

		GlobalProperties globalProperty = mGlobalPropertiesDao.findByKey(MosConstants.CURRENT_SURVEY_NAME);
		if (globalProperty != null) {
			mGlobalPropertiesDao.deleteEntityById(globalProperty.getId());
		}
		globalProperty = new GlobalProperties();
		globalProperty.setKey(MosConstants.CURRENT_SURVEY_NAME);
		globalProperty.setValue(survey.getSurveyName());
		mGlobalPropertiesDao.createEntity(globalProperty);

	}

	public void validateIfOriginalVideoExists(File videoFile) {
		List<File> dirList = new ArrayList<File>();
		if (videoFile.isDirectory()) {
			File[] listFiles = videoFile.listFiles();
			for (File video : listFiles) {
				if (video.isDirectory()) {
					dirList.add(video);
					validateIfOriginalVideoExists(video);
				}
			}
		}

		for (File sf : dirList) {
			if (sf.isDirectory()) {
				File[] listFiles = sf.listFiles();
				if (listFiles != null && listFiles.length > 0) {
					boolean flag = false;
					for (File file : listFiles) {
						if (!file.isDirectory()) {
							String fileName = file.getName();
							String extension = fileName.substring(fileName.lastIndexOf("."));
							if (fileName.equalsIgnoreCase("original" + extension)) {
								flag = true;
								break;
							}
						}
					}
					if (!flag) {
						throw new IllegalArgumentException("No Original file found starting with " + "'original.' " + "in one of the folders " + sf.getPath());
					}
				}
			}
		}
	}

	private void traverseVideos(File videoFile, Survey survey) throws Exception {
		if (videoFile.isDirectory()) {
			// If not a directory then traverse
			for (File video : videoFile.listFiles()) {
				traverseVideos(video, survey);
			}
		} else if (!videoFile.isDirectory()) {
			// If not a directory then save
			saveVideo(survey, videoFile);
		}

	}

	private void saveVideo(Survey survey, File videoFile) throws Exception {
		if (videoFile.isDirectory()) {
			throw new IllegalArgumentException("Only files are allowed. Use traverse instead");
		}
		String fileName = videoFile.getName();
		int dotPos = fileName.lastIndexOf(".");
		if (dotPos >= 0) {
			String extension = fileName.substring(dotPos);
			if (CommonUtils.supportedVideoFormatsArray.contains(extension.toLowerCase())) {
				Video entity = new Video();
				entity.setFileName(fileName);
				entity.setUrl(videoFile.getAbsolutePath());
				entity.setFk_survey_id(survey.getId());
				mVideoDao.createEntity(entity);
			}
		}
	}

	public List<String> findAllSurveys() {
		List<String> surveyNames = new ArrayList<String>();
		try {
			List<Survey> surveyList = mSurveyDao.findAllEntries();
			if (surveyList.isEmpty()) {
				throw new RuntimeException("Could not find survey list");
			}
			for (Survey survey : surveyList) {
				surveyNames.add(survey.getSurveyName());
			}
		} catch (Exception e) {
			throw new RuntimeException("Could not find survey list");
		}

		return surveyNames;
	}

	public void setAsCurrentSurvey(final String surveyName) {

		GlobalProperties globalProperty;
		try {
			globalProperty = mGlobalPropertiesDao.findByKey(MosConstants.CURRENT_SURVEY_NAME);
			/*
			 * globalproperty!=null means if global property exist
			 */

			if (globalProperty != null) {
				mGlobalPropertiesDao.deleteEntityById(globalProperty.getId());
			}
			globalProperty = new GlobalProperties();
			globalProperty.setKey(MosConstants.CURRENT_SURVEY_NAME);
			globalProperty.setValue(surveyName);
			mGlobalPropertiesDao.createEntity(globalProperty);
		} catch (Exception e) {
			throw new RuntimeException("Could not set survey as current survey");
		}
	}

	public SurveyDetails findSurveyByName(String surveyName) {
		return mSurveyManager.findSurveyByName(surveyName);
	}

	public void deleteSurveyByName(String surveyName) throws Exception {
		Survey survey = mSurveyDao.findSurveyByName(surveyName);
		if (survey != null) {
			mSurveyDao.deleteEntityById(survey.getId());
		}
	}

}
