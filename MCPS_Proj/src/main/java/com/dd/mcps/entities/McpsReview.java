package com.dd.mcps.entities;

// Generated Jun 29, 2015 11:54:07 PM by Hibernate Tools 3.4.0.CR1

import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * McpsReview generated by hbm2java
 */
@Entity
@Table(name = "mcps_review", catalog = "mcps")
public class McpsReview implements java.io.Serializable {

	private long id;
	private String content;
	private Set<McpsCampaignAccount> mcpsCampaignAccounts = new HashSet<McpsCampaignAccount>(
			0);

	public McpsReview() {
	}

	public McpsReview(long id, String content) {
		this.id = id;
		this.content = content;
	}

	public McpsReview(long id, String content,
			Set<McpsCampaignAccount> mcpsCampaignAccounts) {
		this.id = id;
		this.content = content;
		this.mcpsCampaignAccounts = mcpsCampaignAccounts;
	}

	@Id
	@Column(name = "ID", unique = true, nullable = false)
	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	@Column(name = "Content", nullable = false)
	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "mcpsReview")
	public Set<McpsCampaignAccount> getMcpsCampaignAccounts() {
		return this.mcpsCampaignAccounts;
	}

	public void setMcpsCampaignAccounts(
			Set<McpsCampaignAccount> mcpsCampaignAccounts) {
		this.mcpsCampaignAccounts = mcpsCampaignAccounts;
	}

}
