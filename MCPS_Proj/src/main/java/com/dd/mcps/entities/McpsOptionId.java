package com.dd.mcps.entities;

// Generated Jun 15, 2015 5:48:45 PM by Hibernate Tools 3.4.0.CR1

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * McpsOptionId generated by hbm2java
 */
@Embeddable
public class McpsOptionId implements java.io.Serializable {

	private long optionId;
	private long surveyQuestionId;

	public McpsOptionId() {
	}

	public McpsOptionId(long optionId, long surveyQuestionId) {
		this.optionId = optionId;
		this.surveyQuestionId = surveyQuestionId;
	}

	@Column(name = "OptionID", nullable = false)
	public long getOptionId() {
		return this.optionId;
	}

	public void setOptionId(long optionId) {
		this.optionId = optionId;
	}

	@Column(name = "SurveyQuestionID", nullable = false)
	public long getSurveyQuestionId() {
		return this.surveyQuestionId;
	}

	public void setSurveyQuestionId(long surveyQuestionId) {
		this.surveyQuestionId = surveyQuestionId;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof McpsOptionId))
			return false;
		McpsOptionId castOther = (McpsOptionId) other;

		return (this.getOptionId() == castOther.getOptionId())
				&& (this.getSurveyQuestionId() == castOther
						.getSurveyQuestionId());
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + (int) this.getOptionId();
		result = 37 * result + (int) this.getSurveyQuestionId();
		return result;
	}

}