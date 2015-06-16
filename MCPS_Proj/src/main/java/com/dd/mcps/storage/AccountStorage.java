package com.dd.mcps.storage;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.dd.mcps.entities.McpsAccount;
import com.dd.mcps.util.HibernateUtil;

public class AccountStorage {
	
	public void saveAccount(McpsAccount newAccount) {
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		session.save(newAccount);
		tx.commit();
		session.close();
	}
	
	public void updateAccount(McpsAccount updatedAccount) {
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		session.save(updatedAccount);
		tx.commit();
		session.close();
	}
	
	public McpsAccount getAccount(int accountID) {
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		McpsAccount found = (McpsAccount)session.get(McpsAccount.class, accountID);
		tx.commit();
		session.close();
		return found;
	}
	
	public void deleteAccount(long accountID) {
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		McpsAccount deleteObj = new McpsAccount();
		deleteObj.setId(accountID);
		session.delete(deleteObj);
		tx.commit();
		session.close();
	}
	
	public List<McpsAccount> getAll() {
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		Query query = session.createQuery("from McpsAccount");
		List<McpsAccount> accounts = query.list();
		tx.commit();
		session.close();
		return accounts;
	}
	
	public List<McpsAccount> isAccountExist(String email, String password) {
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		Query query = session.createQuery("from McpsAccount where email = :email and pass = :password");
		query.setParameter("email", email);
		query.setParameter("password", password);
		List<McpsAccount> accounts = query.list();
		tx.commit();
		session.close();
		return accounts;
	}
}
