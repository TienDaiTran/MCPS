package com.dd.mcps.entities;

// Generated Jul 3, 2015 7:49:41 PM by Hibernate Tools 3.4.0.CR1

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * McpsCampaignAccountId generated by hbm2java
 */
@Embeddable
public class McpsCampaignAccountId implements java.io.Serializable {

	private long campaignId;
	private long accountId;

	public McpsCampaignAccountId() {
	}

	public McpsCampaignAccountId(long campaignId, long accountId) {
		this.campaignId = campaignId;
		this.accountId = accountId;
	}

	@Column(name = "CampaignID", nullable = false)
	public long getCampaignId() {
		return this.campaignId;
	}

	public void setCampaignId(long campaignId) {
		this.campaignId = campaignId;
	}

	@Column(name = "AccountID", nullable = false)
	public long getAccountId() {
		return this.accountId;
	}

	public void setAccountId(long accountId) {
		this.accountId = accountId;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof McpsCampaignAccountId))
			return false;
		McpsCampaignAccountId castOther = (McpsCampaignAccountId) other;

		return (this.getCampaignId() == castOther.getCampaignId())
				&& (this.getAccountId() == castOther.getAccountId());
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + (int) this.getCampaignId();
		result = 37 * result + (int) this.getAccountId();
		return result;
	}

}
