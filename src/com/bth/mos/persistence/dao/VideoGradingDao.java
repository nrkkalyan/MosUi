package com.bth.mos.persistence.dao;

import java.util.List;

import com.bth.mos.persistence.entity.VideoGrading;

public interface VideoGradingDao extends BaseEntityDao<VideoGrading> {

	List<VideoGrading> findAllByUserId(int fk_userId) throws Exception;

	List<VideoGrading> findAllBySurveyId(int fk_surveyId) throws Exception;

	List<VideoGrading> findAllByVideoId(int fk_videoId) throws Exception;

}
