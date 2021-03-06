package com.dd.mcps.entities;

// Generated Jul 3, 2015 7:49:41 PM by Hibernate Tools 3.4.0.CR1

import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * McpsSurveyquestion generated by hbm2java
 */
@Entity
@Table(name = "mcps_surveyquestion", catalog = "mcps")
public class McpsSurveyquestion implements java.io.Serializable {

	private long id;
	private McpsQuestiontype mcpsQuestiontype;
	private McpsSurvey mcpsSurvey;
	private String content;
	private Set<McpsOption> mcpsOptions = new HashSet<McpsOption>(0);
	private Set<McpsAnswer> mcpsAnswers = new HashSet<McpsAnswer>(0);

	public McpsSurveyquestion() {
	}

	public McpsSurveyquestion(long id, McpsQuestiontype mcpsQuestiontype,
			McpsSurvey mcpsSurvey, String content) {
		this.id = id;
		this.mcpsQuestiontype = mcpsQuestiontype;
		this.mcpsSurvey = mcpsSurvey;
		this.content = content;
	}

	public McpsSurveyquestion(long id, McpsQuestiontype mcpsQuestiontype,
			McpsSurvey mcpsSurvey, String content, Set<McpsOption> mcpsOptions,
			Set<McpsAnswer> mcpsAnswers) {
		this.id = id;
		this.mcpsQuestiontype = mcpsQuestiontype;
		this.mcpsSurvey = mcpsSurvey;
		this.content = content;
		this.mcpsOptions = mcpsOptions;
		this.mcpsAnswers = mcpsAnswers;
	}

	@Id
	@Column(name = "ID", unique = true, nullable = false)
	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "QuestionTypeID", nullable = false)
	public McpsQuestiontype getMcpsQuestiontype() {
		return this.mcpsQuestiontype;
	}

	public void setMcpsQuestiontype(McpsQuestiontype mcpsQuestiontype) {
		this.mcpsQuestiontype = mcpsQuestiontype;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "SurveyID", nullable = false)
	public McpsSurvey getMcpsSurvey() {
		return this.mcpsSurvey;
	}

	public void setMcpsSurvey(McpsSurvey mcpsSurvey) {
		this.mcpsSurvey = mcpsSurvey;
	}

	@Column(name = "Content", nullable = false, length = 2000)
	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "mcpsSurveyquestion")
	public Set<McpsOption> getMcpsOptions() {
		return this.mcpsOptions;
	}

	public void setMcpsOptions(Set<McpsOption> mcpsOptions) {
		this.mcpsOptions = mcpsOptions;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "mcpsSurveyquestion")
	public Set<McpsAnswer> getMcpsAnswers() {
		return this.mcpsAnswers;
	}

	public void setMcpsAnswers(Set<McpsAnswer> mcpsAnswers) {
		this.mcpsAnswers = mcpsAnswers;
	}

}
