package com.dd.mcps.entities;

// Generated Jun 18, 2015 6:31:16 PM by Hibernate Tools 3.4.0.CR1

import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

/**
 * McpsInterest generated by hbm2java
 */
@Entity
@Table(name = "mcps_interest", catalog = "mcps")
public class McpsInterest implements java.io.Serializable {

	private short id;
	private String interestName;
	private Set<McpsRevieweraccount> mcpsRevieweraccounts = new HashSet<McpsRevieweraccount>(
			0);
	private Set<McpsCampaign> mcpsCampaigns = new HashSet<McpsCampaign>(0);
	private Set<McpsSurvey> mcpsSurveys = new HashSet<McpsSurvey>(0);

	public McpsInterest() {
	}

	public McpsInterest(short id) {
		this.id = id;
	}

	public McpsInterest(short id, String interestName,
			Set<McpsRevieweraccount> mcpsRevieweraccounts,
			Set<McpsCampaign> mcpsCampaigns, Set<McpsSurvey> mcpsSurveys) {
		this.id = id;
		this.interestName = interestName;
		this.mcpsRevieweraccounts = mcpsRevieweraccounts;
		this.mcpsCampaigns = mcpsCampaigns;
		this.mcpsSurveys = mcpsSurveys;
	}

	@Id
	@Column(name = "ID", unique = true, nullable = false)
	public short getId() {
		return this.id;
	}

	public void setId(short id) {
		this.id = id;
	}

	@Column(name = "InterestName")
	public String getInterestName() {
		return this.interestName;
	}

	public void setInterestName(String interestName) {
		this.interestName = interestName;
	}

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "mcps_interest_reviewer", catalog = "mcps", joinColumns = { @JoinColumn(name = "InterestID", nullable = false, updatable = false) }, inverseJoinColumns = { @JoinColumn(name = "ReviewerAccountID", nullable = false, updatable = false) })
	public Set<McpsRevieweraccount> getMcpsRevieweraccounts() {
		return this.mcpsRevieweraccounts;
	}

	public void setMcpsRevieweraccounts(
			Set<McpsRevieweraccount> mcpsRevieweraccounts) {
		this.mcpsRevieweraccounts = mcpsRevieweraccounts;
	}

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "mcps_campaign_interest", catalog = "mcps", joinColumns = { @JoinColumn(name = "InterestID", nullable = false, updatable = false) }, inverseJoinColumns = { @JoinColumn(name = "CampaignID", nullable = false, updatable = false) })
	public Set<McpsCampaign> getMcpsCampaigns() {
		return this.mcpsCampaigns;
	}

	public void setMcpsCampaigns(Set<McpsCampaign> mcpsCampaigns) {
		this.mcpsCampaigns = mcpsCampaigns;
	}

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "mcps_survey_interest", catalog = "mcps", joinColumns = { @JoinColumn(name = "InterestID", nullable = false, updatable = false) }, inverseJoinColumns = { @JoinColumn(name = "SurveyID", nullable = false, updatable = false) })
	public Set<McpsSurvey> getMcpsSurveys() {
		return this.mcpsSurveys;
	}

	public void setMcpsSurveys(Set<McpsSurvey> mcpsSurveys) {
		this.mcpsSurveys = mcpsSurveys;
	}

}
