package com.bth.mos.persistence.dao;

import com.bth.mos.persistence.entity.CustomScale;

public interface CustomScaleDao extends BaseEntityDao<CustomScale> {

	CustomScale findBySurveyId(int surveyId) throws Exception;
}
