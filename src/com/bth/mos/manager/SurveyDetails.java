package com.bth.mos.manager;

import java.io.Serializable;

import com.bth.mos.persistence.entity.BinaryScale;
import com.bth.mos.persistence.entity.CustomScale;
import com.bth.mos.persistence.entity.Survey;
import com.bth.mos.persistence.entity.Timer;

public class SurveyDetails implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Survey survey;
	private Timer timer;
	private BinaryScale binaryScale;
	private CustomScale customScale;

	public void setSurvey(Survey survey) {
		this.survey = survey;
	}

	public Survey getSurvey() {
		return survey;
	}

	public void setTimer(Timer timer) {
		this.timer = timer;
	}

	public Timer getTimer() {
		return timer;
	}

	public void setBinaryScale(BinaryScale binaryScale) {
		this.binaryScale = binaryScale;
	}

	public BinaryScale getBinaryScale() {
		return binaryScale;
	}

	public void setCustomScale(CustomScale customScale) {
		this.customScale = customScale;
	}

	public CustomScale getCustomScale() {
		return customScale;
	}

	@Override
	public String toString() {
		return "SurveyDetails [survey=" + survey + ", timer=" + timer + ", binaryScale=" + binaryScale + ", customScale=" + customScale + "]";
	}

}
