package com.dd.mcps.admin_funcs.services;

import java.util.List;

import com.dd.mcps.entities.McpsAccount;
import com.dd.mcps.storage.AccountStorage;

public class ManageAccountService {
	
	private AccountStorage accountStorage;
	
	public Boolean isAccountExist(String email, String password, String role) {
		List<McpsAccount> accounts = accountStorage.isAccountExist(email, password);
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
		return accountStorage.getAll();
	}
	
}
