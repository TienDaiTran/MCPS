package com.dd.mcps.entities;

// Generated Jul 1, 2015 2:40:35 PM by Hibernate Tools 3.4.0.CR1

import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * McpsOccupation generated by hbm2java
 */
@Entity
@Table(name = "mcps_occupation", catalog = "mcps")
public class McpsOccupation implements java.io.Serializable {

	private Byte id;
	private String occupationName;
	private Set<McpsRevieweraccount> mcpsRevieweraccounts = new HashSet<McpsRevieweraccount>(
			0);

	public McpsOccupation() {
	}

	public McpsOccupation(String occupationName,
			Set<McpsRevieweraccount> mcpsRevieweraccounts) {
		this.occupationName = occupationName;
		this.mcpsRevieweraccounts = mcpsRevieweraccounts;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "ID", unique = true, nullable = false)
	public Byte getId() {
		return this.id;
	}

	public void setId(Byte id) {
		this.id = id;
	}

	@Column(name = "OccupationName")
	public String getOccupationName() {
		return this.occupationName;
	}

	public void setOccupationName(String occupationName) {
		this.occupationName = occupationName;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "mcpsOccupation")
	public Set<McpsRevieweraccount> getMcpsRevieweraccounts() {
		return this.mcpsRevieweraccounts;
	}

	public void setMcpsRevieweraccounts(
			Set<McpsRevieweraccount> mcpsRevieweraccounts) {
		this.mcpsRevieweraccounts = mcpsRevieweraccounts;
	}

}
