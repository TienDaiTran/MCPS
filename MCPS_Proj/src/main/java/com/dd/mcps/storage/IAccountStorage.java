package com.dd.mcps.storage;

import java.util.List;

import com.dd.mcps.entities.McpsAccount;
import com.dd.mcps.entities.McpsGender;
import com.dd.mcps.entities.McpsInterest;
import com.dd.mcps.entities.McpsOccupation;
import com.dd.mcps.entities.McpsRole;

public interface IAccountStorage {
	/**
	 * save new account
	 * @param newAccount
	 */
	public void saveAccount(McpsAccount newAccount);
	
	/**
	 * update information of a existing account
	 * @param updatedAccount
	 */
	public void updateAccount(McpsAccount updatedAccount);
	
	/**
	 * get an account with a specific id
	 * @param accountID
	 * @return
	 */
	public McpsAccount getAccount(long accountID);
	
	/**
	 * get an account with a specific id
	 * @param accountID
	 * @return null if email is not exist
	 */
	public McpsAccount getAccount(String email);
	
	/**
	 * delete an account with a specific id
	 * @param accountID
	 */
	public void deleteAccount(long accountID);
	
	/**
	 * retrieve all accounts in system
	 * @return all accounts
	 */
	public List<McpsAccount> getAll();
	
	/**
	 * retrieve account is exist with specific email
	 * @param email
	 * @param password
	 * @return list account
	 */
	public List<McpsAccount> accountExist(String email);
	
	/**
	 * retrieve account is exist with specific email and password
	 * @param email
	 * @param password
	 * @return list account
	 */
	public List<McpsAccount> accountExist(String email, String password);
	
	/**
	 * retrieve all role in system
	 * @return list of roles
	 */
	public List<McpsRole> getAllRoles();
	
	/**
	 * retrieve all gender type in system
	 * @return list of roles
	 */
	public List<McpsGender> getAllGenders();
	
	/**
	 * retrieve all occupation type in system
	 * @return list of occupations
	 */
	public List<McpsOccupation> getAllOccupation();
	
	/**
	 * retrieve all interests in db
	 * @return list of interests
	 */
	public List<McpsInterest> getAllInterests();
	
	/**
	 * search account with specific criteria
	 * @param criteria with account id, email and roleid
	 * @return list account found
	 */
	public List<McpsAccount> search(McpsAccount criteria);
	
	/**
	 * Block or unblock account
	 * @param id
	 * @param isBlock
	 */
	public void block(Long id, boolean isBlock);
}
