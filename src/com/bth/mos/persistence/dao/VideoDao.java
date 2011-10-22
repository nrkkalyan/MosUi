package com.bth.mos.persistence.dao;

import java.util.List;

import com.bth.mos.persistence.entity.Video;


public interface VideoDao  extends BaseEntityDao<Video> {
	List<Video> findVideosBySurveyId(int fk_survey_id) throws Exception;
}
