package com.dd.mcps.admin_funcs.services;

import java.util.List;

import com.dd.mcps.entities.McpsAccount;
import com.dd.mcps.entities.McpsGender;
import com.dd.mcps.entities.McpsOccupation;
import com.dd.mcps.entities.McpsRole;
import com.dd.mcps.storage.AccountStorage;
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
	 * kiểm tra tài khoản có tồn tại hay không
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
	 * kiểm tra tài khoản có tồn tại hay không
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
	
	public List<McpsAccount> getAllAccounts() {
		return getAccountStorage().getAll();
	}
	
	public List<McpsRole> getAllRoles() {
		return getAccountStorage().getAllRoles();
	}
	
	public List<McpsGender> getGenders() {
		return getAccountStorage().getAllGenders();
	}
	
	public List<McpsOccupation> getOccupations() {
		return getAccountStorage().getAllOccupation();
	}
	
	public List<McpsAccount> searchAccount(McpsAccount criteria) {
		return getAccountStorage().search(criteria);
	}
	
	public void createAccount(McpsAccount newAccount) {
		getAccountStorage().saveAccount(newAccount);
	}
	
	public void block(Long id, boolean isBlock) {
		getAccountStorage().block(id, isBlock);
	}
	
	public void delete(Long id) {
		getAccountStorage().deleteAccount(id);
	}
	
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
