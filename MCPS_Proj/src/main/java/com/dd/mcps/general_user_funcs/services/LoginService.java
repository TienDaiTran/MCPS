package com.dd.mcps.general_user_funcs.services;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.dd.mcps.entities.McpsAccount;
import com.dd.mcps.storage.AccountStorage;
import com.dd.mcps.util.HibernateUtil;

public class LoginService {

	private AccountStorage accountStorage;
	
	public AccountStorage getAccountStorage() {
		return accountStorage;
	}

	public void setAccountStorage(AccountStorage accountStorage) {
		this.accountStorage = accountStorage;
	}
	
	/**
	 * Check that account is exist or not
	 * @param email
	 * @param password
	 * @return true if exist and false for vice vesa
	 */
	public Boolean IsAccountExist(String email, String password) {
		List<McpsAccount> accounts = getAccountStorage().accountExist(email, password);
		return (accounts.size() == 1);
	}
	
}
