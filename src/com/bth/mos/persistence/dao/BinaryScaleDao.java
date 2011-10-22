package com.bth.mos.persistence.dao;

import com.bth.mos.persistence.entity.BinaryScale;

public interface BinaryScaleDao extends BaseEntityDao<BinaryScale> {
	
	BinaryScale findBySurveyId(int surveyId) throws Exception;

}
