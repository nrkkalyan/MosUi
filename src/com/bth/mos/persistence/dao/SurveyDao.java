package com.bth.mos.persistence.dao;

import com.bth.mos.persistence.entity.Survey;

public interface SurveyDao extends BaseEntityDao<Survey> {

	Survey findSurveyByName(String surveyName) throws Exception;

}
