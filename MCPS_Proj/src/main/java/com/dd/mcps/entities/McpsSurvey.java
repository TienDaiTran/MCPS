package com.dd.mcps.entities;

// Generated Jul 3, 2015 7:49:41 PM by Hibernate Tools 3.4.0.CR1

import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * McpsSurvey generated by hbm2java
 */
@Entity
@Table(name = "mcps_survey", catalog = "mcps")
public class McpsSurvey implements java.io.Serializable {

	private long id;
	private String surveyName;
	private String description;
	private Set<McpsRevieweraccountSurvey> mcpsRevieweraccountSurveys = new HashSet<McpsRevieweraccountSurvey>(
			0);
	private Set<McpsInterest> mcpsInterests = new HashSet<McpsInterest>(0);
	private Set<McpsSurveyquestion> mcpsSurveyquestions = new HashSet<McpsSurveyquestion>(
			0);

	public McpsSurvey() {
	}

	public McpsSurvey(long id, String surveyName) {
		this.id = id;
		this.surveyName = surveyName;
	}

	public McpsSurvey(long id, String surveyName, String description,
			Set<McpsRevieweraccountSurvey> mcpsRevieweraccountSurveys,
			Set<McpsInterest> mcpsInterests,
			Set<McpsSurveyquestion> mcpsSurveyquestions) {
		this.id = id;
		this.surveyName = surveyName;
		this.description = description;
		this.mcpsRevieweraccountSurveys = mcpsRevieweraccountSurveys;
		this.mcpsInterests = mcpsInterests;
		this.mcpsSurveyquestions = mcpsSurveyquestions;
	}

	@Id
	@Column(name = "ID", unique = true, nullable = false)
	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	@Column(name = "SurveyName", nullable = false)
	public String getSurveyName() {
		return this.surveyName;
	}

	public void setSurveyName(String surveyName) {
		this.surveyName = surveyName;
	}

	@Column(name = "Description", length = 2000)
	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "mcpsSurvey")
	public Set<McpsRevieweraccountSurvey> getMcpsRevieweraccountSurveys() {
		return this.mcpsRevieweraccountSurveys;
	}

	public void setMcpsRevieweraccountSurveys(
			Set<McpsRevieweraccountSurvey> mcpsRevieweraccountSurveys) {
		this.mcpsRevieweraccountSurveys = mcpsRevieweraccountSurveys;
	}

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "mcps_survey_interest", catalog = "mcps", joinColumns = { @JoinColumn(name = "SurveyID", nullable = false, updatable = false) }, inverseJoinColumns = { @JoinColumn(name = "InterestID", nullable = false, updatable = false) })
	public Set<McpsInterest> getMcpsInterests() {
		return this.mcpsInterests;
	}

	public void setMcpsInterests(Set<McpsInterest> mcpsInterests) {
		this.mcpsInterests = mcpsInterests;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "mcpsSurvey")
	public Set<McpsSurveyquestion> getMcpsSurveyquestions() {
		return this.mcpsSurveyquestions;
	}

	public void setMcpsSurveyquestions(
			Set<McpsSurveyquestion> mcpsSurveyquestions) {
		this.mcpsSurveyquestions = mcpsSurveyquestions;
	}

}
