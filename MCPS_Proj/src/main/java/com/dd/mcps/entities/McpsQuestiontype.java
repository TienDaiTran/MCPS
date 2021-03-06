package com.dd.mcps.entities;

// Generated Jul 3, 2015 7:49:41 PM by Hibernate Tools 3.4.0.CR1

import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * McpsQuestiontype generated by hbm2java
 */
@Entity
@Table(name = "mcps_questiontype", catalog = "mcps")
public class McpsQuestiontype implements java.io.Serializable {

	private byte id;
	private Long typeName;
	private Set<McpsSurveyquestion> mcpsSurveyquestions = new HashSet<McpsSurveyquestion>(
			0);

	public McpsQuestiontype() {
	}

	public McpsQuestiontype(byte id) {
		this.id = id;
	}

	public McpsQuestiontype(byte id, Long typeName,
			Set<McpsSurveyquestion> mcpsSurveyquestions) {
		this.id = id;
		this.typeName = typeName;
		this.mcpsSurveyquestions = mcpsSurveyquestions;
	}

	@Id
	@Column(name = "ID", unique = true, nullable = false)
	public byte getId() {
		return this.id;
	}

	public void setId(byte id) {
		this.id = id;
	}

	@Column(name = "TypeName")
	public Long getTypeName() {
		return this.typeName;
	}

	public void setTypeName(Long typeName) {
		this.typeName = typeName;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "mcpsQuestiontype")
	public Set<McpsSurveyquestion> getMcpsSurveyquestions() {
		return this.mcpsSurveyquestions;
	}

	public void setMcpsSurveyquestions(
			Set<McpsSurveyquestion> mcpsSurveyquestions) {
		this.mcpsSurveyquestions = mcpsSurveyquestions;
	}

}
