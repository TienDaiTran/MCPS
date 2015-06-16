package com.dd.mcps.entities;

// Generated Jun 16, 2015 2:25:50 PM by Hibernate Tools 3.4.0.CR1

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
 * McpsRole generated by hbm2java
 */
@Entity
@Table(name = "mcps_role", catalog = "mcps")
public class McpsRole implements java.io.Serializable {

	private Byte id;
	private String roleName;
	private Set<McpsAccount> mcpsAccounts = new HashSet<McpsAccount>(0);

	public McpsRole() {
	}

	public McpsRole(String roleName) {
		this.roleName = roleName;
	}

	public McpsRole(String roleName, Set<McpsAccount> mcpsAccounts) {
		this.roleName = roleName;
		this.mcpsAccounts = mcpsAccounts;
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

	@Column(name = "RoleName", nullable = false)
	public String getRoleName() {
		return this.roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "mcpsRole")
	public Set<McpsAccount> getMcpsAccounts() {
		return this.mcpsAccounts;
	}

	public void setMcpsAccounts(Set<McpsAccount> mcpsAccounts) {
		this.mcpsAccounts = mcpsAccounts;
	}

}