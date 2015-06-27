package com.dd.mcps.storage;

import java.util.Date;
import java.util.List;

import com.dd.mcps.entities.McpsAccount;
import com.dd.mcps.entities.McpsCampaign;
import com.dd.mcps.entities.McpsGender;
import com.dd.mcps.entities.McpsInterest;
import com.dd.mcps.entities.McpsOccupation;
import com.dd.mcps.entities.McpsRole;

public interface ICampaignStorage {
	/**
	 * save new campaign
	 * @param newCampaign
	 */
	public void saveCampaign(McpsCampaign newCampaign);
	
	/**
	 * update information of a existing campaign
	 * @param updateCampaign
	 */
	public void updateCampaign(McpsCampaign updateCampaign);
	
	/**
	 * get a campaign with a specific id
	 * @param campaignID
	 * @return
	 */
	public McpsCampaign getCampaign(long campaignID);
	
	/**
	 * delete a campaign with a specific id
	 * @param campaignID
	 */
	public void deleteCampaign(long campaignID);
	
	/**
	 * retrieve all campaigns in system
	 * @return all campaigns
	 */
	public List<McpsCampaign> getAll();
	
	/**
	 * retrieve all categories in system
	 * @return list of categories
	 */
	public List<McpsInterest> getAllCategories();
	
	/**
	 * search campaigns with specific criteria
	 * @param criteria with campaignID, campaign name, categoryID, partner name and created date
	 * @return list campaign found
	 */
	public List<McpsCampaign> search(Long campaignID, String campaignName, Short categoryID, String partnerName, Date createdDate);
	
	/**
	 * publish/unpublish campaign
	 * @param id
	 * @param isPublish
	 */
	public void publish(Long id, boolean isPublish);
	
	/**
	 * Change to wait for review
	 */
	public void waitForReview();
	
	/**
	 * Complete campaign
	 */
	public void complete();
}
