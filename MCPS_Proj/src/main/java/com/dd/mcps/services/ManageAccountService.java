package com.dd.mcps.services;

import java.util.List;

import com.dd.mcps.entities.McpsAccount;
import com.dd.mcps.entities.McpsGender;
import com.dd.mcps.entities.McpsOccupation;
import com.dd.mcps.entities.McpsRole;
import com.dd.mcps.storage.IAccountStorage;

public class ManageAccountService {
	
	private IAccountStorage accountStorage;
	
	public IAccountStorage getAccountStorage() {
		return accountStorage;
	}

	public void setAccountStorage(IAccountStorage accountStorage) {
		this.accountStorage = accountStorage;
	}
	
	public McpsAccount getAccount(long accountID) {
		return accountStorage.getAccount(accountID);
	}
	
	/**
	 * check account exist or not
	 * @param email
	 * @return true or false
	 */
	public Boolean isAccountExist(String email) {
		List<McpsAccount> accounts = getAccountStorage().accountExist(email);
		if (accounts.size() > 0) {
			return true;
		}
		return false;
	}
	
	/**
	 * check account exist or not
	 * @param email
	 * @param password
	 * @param role
	 * @return true or false
	 */
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
	
	/**
	 * Get all accounts in system
	 * @return
	 */
	public List<McpsAccount> getAllAccounts() {
		return getAccountStorage().getAll();
	}
	
	/**
	 * Get all roles of account in system
	 * @return
	 */
	public List<McpsRole> getAllRoles() {
		return getAccountStorage().getAllRoles();
	}
	
	/**
	 * Get all kind of genders in system
	 * @return
	 */
	public List<McpsGender> getGenders() {
		return getAccountStorage().getAllGenders();
	}
	
	/**
	 * Get all kind of jobs in system
	 * @return
	 */
	public List<McpsOccupation> getOccupations() {
		return getAccountStorage().getAllOccupation();
	}
	
	/**
	 * Search an account by criteria (AccountID, email and role)
	 * @param criteria
	 * @return
	 */
	public List<McpsAccount> searchAccount(McpsAccount criteria) {
		return getAccountStorage().search(criteria);
	}
	
	/**
	 * Create new account
	 * @param newAccount
	 */
	public void createAccount(McpsAccount newAccount) {
		getAccountStorage().saveAccount(newAccount);
	}
	
	/**
	 * Block or unblock account
	 * @param id
	 * @param isBlock true if block and vice versa
	 */
	public void block(Long id, boolean isBlock) {
		getAccountStorage().block(id, isBlock);
	}
	
	/**
	 * Delete an account by id
	 * @param id
	 */
	public void delete(Long id) {
		getAccountStorage().deleteAccount(id);
	}
	
	/**
	 * Update account info
	 * @param newInfo
	 * @return true or fasle
	 */
	public boolean updateAccountInfo(McpsAccount newInfo) {
		McpsAccount oldInfo = getAccount(newInfo.getId());
		boolean completed = false;
		if (oldInfo != null) {
			// update password
			if (!"".equals(newInfo.getPass())) {
				oldInfo.setPass(newInfo.getPass());
			}
			// update partner info
			if (newInfo.getMcpsPartneraccount()!=null) {
				newInfo.getMcpsPartneraccount().setId(oldInfo.getId());
				newInfo.getMcpsPartneraccount().setMcpsAccount(oldInfo);
				oldInfo.setMcpsPartneraccount(newInfo.getMcpsPartneraccount());
			}
			
			// update reviewer info
			if (newInfo.getMcpsRevieweraccount()!=null) {
				newInfo.getMcpsRevieweraccount().setId(oldInfo.getId());
				newInfo.getMcpsRevieweraccount().setMcpsAccount(oldInfo);
				oldInfo.setMcpsRevieweraccount(newInfo.getMcpsRevieweraccount());
			}
			// update info
			getAccountStorage().updateAccount(oldInfo);
			completed = true;
		}
		return completed;
	}
}
