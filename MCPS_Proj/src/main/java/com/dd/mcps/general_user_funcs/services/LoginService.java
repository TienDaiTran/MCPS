package com.dd.mcps.general_user_funcs.services;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.dd.mcps.entities.McpsAccount;
import com.dd.mcps.util.HibernateUtil;

public class LoginService {

	/**
	 * Check that account is exist or not
	 * @param email
	 * @param password
	 * @return true if exist and false for vice vesa
	 */
	public Boolean IsAccountExist(String email, String password) {
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		// query for search account
		Query query = session.createQuery("from McpsAccount ac where ac.email = :email and ac.pass = :password");
		query.setParameter("email", email);
		query.setParameter("password", password);
		List<McpsAccount> accounts = query.list();
		session.close();
		// return result
		if (accounts == null || accounts.size() == 0) {
			return false;
		} else {
			return true;
		}
	}
	
}
