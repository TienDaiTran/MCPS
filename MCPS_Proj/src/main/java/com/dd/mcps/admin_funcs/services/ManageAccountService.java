package com.dd.mcps.admin_funcs.services;

import java.util.List;

import org.hibernate.Hibernate;

import com.dd.mcps.entities.McpsAccount;
import com.dd.mcps.entities.McpsRole;
import com.dd.mcps.storage.AccountStorage;

public class ManageAccountService {
	
	private AccountStorage accountStorage;
	
	public AccountStorage getAccountStorage() {
		return accountStorage;
	}

	public void setAccountStorage(AccountStorage accountStorage) {
		this.accountStorage = accountStorage;
	}
	
	public Boolean isAccountExist(String email, String password, String role) {
		List<McpsAccount> accounts = getAccountStorage().accountExist(email, password);
		if (accounts.size() > 0) {
			for (McpsAccount acc : accounts) {
				if (role.equals(acc.getMcpsRole().getRoleName())) {
					return true;
				}
			}
		}
		return false;
	}
	
	public List<McpsAccount> getAllAccounts() {
		return getAccountStorage().getAll();
	}
	
	public List<McpsRole> getAllRoles() {
		return getAccountStorage().getAllRoles();
	}
	
	public List<McpsAccount> searchAccount(McpsAccount criteria) {
		return getAccountStorage().search(criteria);
	}
	
	public void block(Long id, boolean isBlock) {
		getAccountStorage().block(id, isBlock);
	}
	
	public void delete(Long id) {
		getAccountStorage().deleteAccount(id);
	}
}
