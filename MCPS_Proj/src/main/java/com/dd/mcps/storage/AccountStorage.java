package com.dd.mcps.storage;

import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.dd.mcps.entities.McpsAccount;
import com.dd.mcps.entities.McpsRole;
import com.dd.mcps.util.HibernateUtil;

public class AccountStorage {
	
	/**
	 * save new account
	 * @param newAccount
	 */
	public void saveAccount(McpsAccount newAccount) {
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		session.save(newAccount);
		tx.commit();
		session.close();
	}
	
	/**
	 * update information of a existing account
	 * @param updatedAccount
	 */
	public void updateAccount(McpsAccount updatedAccount) {
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		session.save(updatedAccount);
		tx.commit();
		session.close();
	}
	
	/**
	 * get an account with a specific id
	 * @param accountID
	 * @return
	 */
	public McpsAccount getAccount(int accountID) {
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		McpsAccount found = (McpsAccount)session.get(McpsAccount.class, accountID);
		tx.commit();
		session.close();
		return found;
	}
	
	/**
	 * delete an account with a specific id
	 * @param accountID
	 */
	public void deleteAccount(long accountID) {
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		McpsAccount deleteObj = (McpsAccount) session.get(McpsAccount.class, accountID);
		session.delete(deleteObj);
		tx.commit();
		session.close();
	}
	
	/**
	 * retrieve all accounts in system
	 * @return all accounts
	 */
	public List<McpsAccount> getAll() {
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		Query query = session.createQuery("from McpsAccount");
		List<McpsAccount> accounts = query.list();
		for (McpsAccount acc : accounts) {
			Hibernate.initialize(acc.getMcpsRole());
		}
		tx.commit();
		session.close();
		return accounts;
	}
	
	/**
	 * retrieve account is exist with specific email and password
	 * @param email
	 * @param password
	 * @return list account
	 */
	public List<McpsAccount> accountExist(String email, String password) {
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		Query query = session.createQuery("from McpsAccount where email = :email and pass = :password");
		query.setParameter("email", email);
		query.setParameter("password", password);
		List<McpsAccount> accounts = query.list();
		for (McpsAccount acc : accounts) {
			Hibernate.initialize(acc.getMcpsRole());
		}
		tx.commit();
		session.close();
		return accounts;
	}
	
	/**
	 * retrieve all role in system
	 * @return list of roles
	 */
	public List<McpsRole> getAllRoles() {
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		Query query = session.createQuery("from McpsRole");
		List<McpsRole> roles = query.list();
		tx.commit();
		session.close();
		return roles;
	}
	
	/**
	 * search account with specific criteria
	 * @param criteria
	 * @return list account found
	 */
	public List<McpsAccount> search(McpsAccount criteria) {
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		String queryString = "select acc from McpsAccount as acc inner join acc.mcpsRole as ro "
				+ "where (:id is null or acc.id = :id) and "
				+ "(:email = '' or acc.email like :email) and "
				+ "(:roleId is null or ro.id = :roleId)";
		Query query = session.createQuery(queryString);
		Long id = criteria.getId();
		String email = criteria.getEmail();
		Byte roleId = criteria.getMcpsRole().getId(); 
		query.setParameter("id", criteria.getId());
		query.setParameter("email", "%" + criteria.getEmail() + "%");
		query.setParameter("roleId", criteria.getMcpsRole().getId());
		List<McpsAccount> accounts = query.list();
		for (McpsAccount acc : accounts) {
			Hibernate.initialize(acc.getMcpsRole());
		}
		tx.commit();
		session.close();
		return accounts;
	}
	
	public void block(Long id, boolean isBlock) {
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		McpsAccount account = (McpsAccount) session.get(McpsAccount.class, id);
		if (account != null) {
			if (isBlock) {
				account.setState("block");
			} else {
				account.setState("active");
			}
		}
		session.save(account);
		tx.commit();
		session.close();
	}
}
