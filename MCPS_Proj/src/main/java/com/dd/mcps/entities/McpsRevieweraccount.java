package com.dd.mcps.entities;

// Generated Jul 1, 2015 2:40:35 PM by Hibernate Tools 3.4.0.CR1

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

/**
 * McpsRevieweraccount generated by hbm2java
 */
@Entity
@Table(name = "mcps_revieweraccount", catalog = "mcps")
public class McpsRevieweraccount implements java.io.Serializable {

	private long id;
	private McpsAccount mcpsAccount;
	private McpsOccupation mcpsOccupation;
	private McpsGender mcpsGender;
	private Long accountId;
	private String firstName;
	private String lastName;
	private Date birthday;
	private String address;
	private String tel;
	private Set<McpsInterest> mcpsInterests = new HashSet<McpsInterest>(0);
	private Set<McpsRevieweraccountSurvey> mcpsRevieweraccountSurveys = new HashSet<McpsRevieweraccountSurvey>(
			0);

	public McpsRevieweraccount() {
	}

	public McpsRevieweraccount(McpsAccount mcpsAccount, McpsGender mcpsGender,
			String firstName, String lastName, Date birthday, String address,
			String tel) {
		this.mcpsAccount = mcpsAccount;
		this.mcpsGender = mcpsGender;
		this.firstName = firstName;
		this.lastName = lastName;
		this.birthday = birthday;
		this.address = address;
		this.tel = tel;
	}

	public McpsRevieweraccount(McpsAccount mcpsAccount,
			McpsOccupation mcpsOccupation, McpsGender mcpsGender,
			Long accountId, String firstName, String lastName, Date birthday,
			String address, String tel, Set<McpsInterest> mcpsInterests,
			Set<McpsRevieweraccountSurvey> mcpsRevieweraccountSurveys) {
		this.mcpsAccount = mcpsAccount;
		this.mcpsOccupation = mcpsOccupation;
		this.mcpsGender = mcpsGender;
		this.accountId = accountId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.birthday = birthday;
		this.address = address;
		this.tel = tel;
		this.mcpsInterests = mcpsInterests;
		this.mcpsRevieweraccountSurveys = mcpsRevieweraccountSurveys;
	}

	@GenericGenerator(name = "generator", strategy = "foreign", parameters = @Parameter(name = "property", value = "mcpsAccount"))
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "ID", unique = true, nullable = false)
	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	@OneToOne(fetch = FetchType.LAZY)
	@PrimaryKeyJoinColumn
	public McpsAccount getMcpsAccount() {
		return this.mcpsAccount;
	}

	public void setMcpsAccount(McpsAccount mcpsAccount) {
		this.mcpsAccount = mcpsAccount;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "OccupationRef")
	public McpsOccupation getMcpsOccupation() {
		return this.mcpsOccupation;
	}

	public void setMcpsOccupation(McpsOccupation mcpsOccupation) {
		this.mcpsOccupation = mcpsOccupation;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "Gender", nullable = false)
	public McpsGender getMcpsGender() {
		return this.mcpsGender;
	}

	public void setMcpsGender(McpsGender mcpsGender) {
		this.mcpsGender = mcpsGender;
	}

	@Column(name = "AccountID")
	public Long getAccountId() {
		return this.accountId;
	}

	public void setAccountId(Long accountId) {
		this.accountId = accountId;
	}

	@Column(name = "FirstName", nullable = false)
	public String getFirstName() {
		return this.firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	@Column(name = "LastName", nullable = false)
	public String getLastName() {
		return this.lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "Birthday", nullable = false, length = 10)
	public Date getBirthday() {
		return this.birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	@Column(name = "Address", nullable = false)
	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@Column(name = "Tel", nullable = false, length = 12)
	public String getTel() {
		return this.tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "mcps_interest_reviewer", catalog = "mcps", joinColumns = { @JoinColumn(name = "ReviewerAccountID", nullable = false, updatable = false) }, inverseJoinColumns = { @JoinColumn(name = "InterestID", nullable = false, updatable = false) })
	public Set<McpsInterest> getMcpsInterests() {
		return this.mcpsInterests;
	}

	public void setMcpsInterests(Set<McpsInterest> mcpsInterests) {
		this.mcpsInterests = mcpsInterests;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "mcpsRevieweraccount")
	public Set<McpsRevieweraccountSurvey> getMcpsRevieweraccountSurveys() {
		return this.mcpsRevieweraccountSurveys;
	}

	public void setMcpsRevieweraccountSurveys(
			Set<McpsRevieweraccountSurvey> mcpsRevieweraccountSurveys) {
		this.mcpsRevieweraccountSurveys = mcpsRevieweraccountSurveys;
	}

}
