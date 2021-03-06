package com.dd.mcps.entities;

// Generated Jul 3, 2015 7:49:41 PM by Hibernate Tools 3.4.0.CR1

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * McpsCampaign generated by hbm2java
 */
@Entity
@Table(name = "mcps_campaign", catalog = "mcps")
public class McpsCampaign implements java.io.Serializable {

	private Long id;
	private McpsAccount mcpsAccount;
	private String campaignName;
	private String bannerImage;
	private String video;
	private String shortDescription;
	private String description;
	private long prototypeQuantity;
	private short recuitReviewerTime;
	private short reviewTime;
	private String state;
	private Date initiateDate;
	private Date publishDate;
	private Set<McpsCampaignAccount> mcpsCampaignAccounts = new HashSet<McpsCampaignAccount>(
			0);
	private Set<McpsInterest> mcpsInterests = new HashSet<McpsInterest>(0);

	public McpsCampaign() {
	}

	public McpsCampaign(McpsAccount mcpsAccount, String campaignName,
			String bannerImage, long prototypeQuantity,
			short recuitReviewerTime, short reviewTime, String state,
			Date initiateDate, Date publishDate) {
		this.mcpsAccount = mcpsAccount;
		this.campaignName = campaignName;
		this.bannerImage = bannerImage;
		this.prototypeQuantity = prototypeQuantity;
		this.recuitReviewerTime = recuitReviewerTime;
		this.reviewTime = reviewTime;
		this.state = state;
		this.initiateDate = initiateDate;
		this.publishDate = publishDate;
	}

	public McpsCampaign(McpsAccount mcpsAccount, String campaignName,
			String bannerImage, String video, String shortDescription,
			String description, long prototypeQuantity,
			short recuitReviewerTime, short reviewTime, String state,
			Date initiateDate, Date publishDate,
			Set<McpsCampaignAccount> mcpsCampaignAccounts,
			Set<McpsInterest> mcpsInterests) {
		this.mcpsAccount = mcpsAccount;
		this.campaignName = campaignName;
		this.bannerImage = bannerImage;
		this.video = video;
		this.shortDescription = shortDescription;
		this.description = description;
		this.prototypeQuantity = prototypeQuantity;
		this.recuitReviewerTime = recuitReviewerTime;
		this.reviewTime = reviewTime;
		this.state = state;
		this.initiateDate = initiateDate;
		this.publishDate = publishDate;
		this.mcpsCampaignAccounts = mcpsCampaignAccounts;
		this.mcpsInterests = mcpsInterests;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "ID", unique = true, nullable = false)
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CreaterID", nullable = false)
	public McpsAccount getMcpsAccount() {
		return this.mcpsAccount;
	}

	public void setMcpsAccount(McpsAccount mcpsAccount) {
		this.mcpsAccount = mcpsAccount;
	}

	@Column(name = "CampaignName", nullable = false)
	public String getCampaignName() {
		return this.campaignName;
	}

	public void setCampaignName(String campaignName) {
		this.campaignName = campaignName;
	}

	@Column(name = "BannerImage", nullable = false)
	public String getBannerImage() {
		return this.bannerImage;
	}

	public void setBannerImage(String bannerImage) {
		this.bannerImage = bannerImage;
	}

	@Column(name = "Video")
	public String getVideo() {
		return this.video;
	}

	public void setVideo(String video) {
		this.video = video;
	}

	@Column(name = "ShortDescription", length = 500)
	public String getShortDescription() {
		return this.shortDescription;
	}

	public void setShortDescription(String shortDescription) {
		this.shortDescription = shortDescription;
	}

	@Column(name = "Description", length = 5000)
	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Column(name = "PrototypeQuantity", nullable = false)
	public long getPrototypeQuantity() {
		return this.prototypeQuantity;
	}

	public void setPrototypeQuantity(long prototypeQuantity) {
		this.prototypeQuantity = prototypeQuantity;
	}

	@Column(name = "RecuitReviewerTime", nullable = false)
	public short getRecuitReviewerTime() {
		return this.recuitReviewerTime;
	}

	public void setRecuitReviewerTime(short recuitReviewerTime) {
		this.recuitReviewerTime = recuitReviewerTime;
	}

	@Column(name = "ReviewTime", nullable = false)
	public short getReviewTime() {
		return this.reviewTime;
	}

	public void setReviewTime(short reviewTime) {
		this.reviewTime = reviewTime;
	}

	@Column(name = "State", nullable = false)
	public String getState() {
		return this.state;
	}

	public void setState(String state) {
		this.state = state;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "InitiateDate", nullable = false, length = 10)
	public Date getInitiateDate() {
		return this.initiateDate;
	}

	public void setInitiateDate(Date initiateDate) {
		this.initiateDate = initiateDate;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "PublishDate", nullable = false, length = 10)
	public Date getPublishDate() {
		return this.publishDate;
	}

	public void setPublishDate(Date publishDate) {
		this.publishDate = publishDate;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "mcpsCampaign")
	public Set<McpsCampaignAccount> getMcpsCampaignAccounts() {
		return this.mcpsCampaignAccounts;
	}

	public void setMcpsCampaignAccounts(
			Set<McpsCampaignAccount> mcpsCampaignAccounts) {
		this.mcpsCampaignAccounts = mcpsCampaignAccounts;
	}

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "mcps_campaign_interest", catalog = "mcps", joinColumns = { @JoinColumn(name = "CampaignID", nullable = false, updatable = false) }, inverseJoinColumns = { @JoinColumn(name = "InterestID", nullable = false, updatable = false) })
	public Set<McpsInterest> getMcpsInterests() {
		return this.mcpsInterests;
	}

	public void setMcpsInterests(Set<McpsInterest> mcpsInterests) {
		this.mcpsInterests = mcpsInterests;
	}

}
