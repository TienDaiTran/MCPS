package com.dd.mcps.storage;

import java.util.Date;
import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.dd.mcps.entities.McpsAccount;
import com.dd.mcps.entities.McpsCampaign;
import com.dd.mcps.entities.McpsInterest;
import com.dd.mcps.util.HibernateUtil;

public class CampaignStorage implements ICampaignStorage {

	@Override
	public void saveCampaign(McpsCampaign newCampaign) {
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		newCampaign.setState("New");
		session.save(newCampaign);
		tx.commit();
		session.close();
	}

	@Override
	public void updateCampaign(McpsCampaign updateCampaign) {
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		updateCampaign.setState("Editted");
		session.merge(updateCampaign);
		tx.commit();
		session.close();
	}

	@Override
	public McpsCampaign getCampaign(long campaignID) {
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		McpsCampaign found = (McpsCampaign)session.get(McpsCampaign.class, campaignID);
		Hibernate.initialize(found.getMcpsAccount());
		Hibernate.initialize(found.getMcpsInterests());
		Hibernate.initialize(found.getMcpsCampaignAccounts());
		tx.commit();
		session.close();
		return found;
	}

	@Override
	public void deleteCampaign(long campaignID) {
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		McpsCampaign deleteObj = (McpsCampaign) session.get(McpsCampaign.class, campaignID);
		session.delete(deleteObj);
		tx.commit();
		session.close();
	}

	@Override
	public List<McpsCampaign> getAll() {
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		Query query = session.createQuery("from McpsCampaign");
		List<McpsCampaign> campaigns = query.list();
		for (McpsCampaign camp : campaigns) {
			Hibernate.initialize(camp.getMcpsAccount());
			Hibernate.initialize(camp.getMcpsAccount().getMcpsPartneraccount());
			Hibernate.initialize(camp.getMcpsInterests());
			Hibernate.initialize(camp.getMcpsCampaignAccounts());
		}
		tx.commit();
		session.close();
		return campaigns;
	}

	@Override
	public List<McpsInterest> getAllCategories() {
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		Query query = session.createQuery("from McpsInterest");
		List<McpsInterest> interests = query.list();
		tx.commit();
		session.close();
		return interests;
	}

	@Override
	public List<McpsCampaign> search(Long campaignID, String campaignName,
			Short categoryID, String partnerName, Date createdDate) {
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		String queryString = "";
		if ("Admin".equals(partnerName)) {
			queryString = "select camp from McpsCampaign as camp left join camp.mcpsAccount as init inner join camp.mcpsInterests as inter "
					+ "where (:id is null or camp.id = :id) and "
					+ "(:campaignName = '%%' or camp.campaignName like :campaignName) and "
					+ "(:categoryID is null or inter.id = :categoryID) and "
					+ "(init.mcpsPartneraccount is not null) and "
					+ "(:createdDate is null or camp.initiateDate = :createdDate)";
		} else {
			queryString = "select camp from McpsCampaign as camp left join camp.mcpsAccount.mcpsPartneraccount as init inner join camp.mcpsInterests as inter "
				+ "where (:id is null or camp.id = :id) and "
				+ "(:campaignName = '%%' or camp.campaignName like :campaignName) and "
				+ "(:partnerName = '%%' or init.partnerName like :partnerName) and "
				+ "(:categoryID is null or inter.id = :categoryID) and "
				+ "(:createdDate is null or camp.initiateDate = :createdDate)";
		}
		Query query = session.createQuery(queryString);
		query.setParameter("id", campaignID);
		query.setParameter("campaignName", "%" + campaignName + "%");
		query.setParameter("categoryID", categoryID);
		if (!"Admin".equals(partnerName)) {
			query.setParameter("partnerName", "%" + partnerName + "%");
		}
		query.setParameter("createdDate", createdDate);
		List<McpsCampaign> campaigns = query.list();
		for (McpsCampaign camp : campaigns) {
			Hibernate.initialize(camp.getMcpsAccount());
			Hibernate.initialize(camp.getMcpsInterests());
			Hibernate.initialize(camp.getMcpsCampaignAccounts());
		}
		tx.commit();
		session.close();
		return campaigns;
	}

	@Override
	public void publish(Long id, boolean isPublish) {
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		McpsCampaign campaign = (McpsCampaign) session.get(McpsCampaign.class, id);
		if (isPublish) {
			campaign.setState("Published");
		} else {
			campaign.setState("Unpublished");
		}	
		session.update(campaign);
		tx.commit();
		session.close();
	}

	@Override
	public void waitForReview(Long id) {
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		McpsCampaign campaign = (McpsCampaign) session.get(McpsCampaign.class, id);
		campaign.setState("Wait for review");
		session.update(campaign);
		tx.commit();
		session.close();
	}

	@Override
	public void complete(Long id) {
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		McpsCampaign campaign = (McpsCampaign) session.get(McpsCampaign.class, id);
		campaign.setState("Completed");
		session.update(campaign);
		tx.commit();
		session.close();
	}

}
