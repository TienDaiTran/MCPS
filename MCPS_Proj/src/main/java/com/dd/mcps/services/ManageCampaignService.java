package com.dd.mcps.services;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeParseException;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.dd.mcps.entities.McpsAccount;
import com.dd.mcps.entities.McpsCampaign;
import com.dd.mcps.entities.McpsGender;
import com.dd.mcps.entities.McpsInterest;
import com.dd.mcps.entities.McpsOccupation;
import com.dd.mcps.entities.McpsRole;
import com.dd.mcps.storage.IAccountStorage;
import com.dd.mcps.storage.ICampaignStorage;

public class ManageCampaignService {
	
	private ICampaignStorage campaignStorage;
	
	public ICampaignStorage getCampaignStorage() {
		return campaignStorage;
	}

	public void setCampaignStorage(ICampaignStorage campaignStorage) {
		this.campaignStorage = campaignStorage;
	}

	public McpsCampaign getCampaign(long campaignID) {
		return campaignStorage.getCampaign(campaignID);
	}
	
	/**
	 * Get all campaigns in system
	 * @return
	 */
	public List<McpsCampaign> getAllCampaigns() {
		return campaignStorage.getAll();
	}
	
	/**
	 * Get campaigns by creator id
	 * @return
	 */
	public List<McpsCampaign> getCampaignsByCreator(Long creatorID) {
		return campaignStorage.getCampaignByCreatorID(creatorID);
	}
	
	/**
	 * Get all category of campaigns in system
	 * @return
	 */
	public List<McpsInterest> getAllCategories() {
		return campaignStorage.getAllCategories();
	}
	
	/**
	 * Search an campaign by criteria passed through
	 * @param criteria
	 * @return
	 */
	public List<McpsCampaign> searchCampaign(String campaignIDStr, String campaignName, String categoryIDStr, String partnerName, String createdDateStr) {
		// convert string data to property type
		Long campaignID = null;
		Short categoryID = null;
		Date createdDate = null;
		
		try {
			campaignID = Long.parseLong(campaignIDStr);
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			categoryID = Short.parseShort(categoryIDStr);
			if (categoryID == 0) categoryID = null;
		} catch (NumberFormatException e) {
			
		}

		try {
			createdDate = new SimpleDateFormat("dd/MM/yyyy").parse(createdDateStr);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return campaignStorage.search(campaignID, campaignName, categoryID, partnerName, createdDate);
	}
	
	/**
	 * Create new campaign
	 * @param newCampaign
	 */
	public void createCampaign(String createrIDStr, String campaignName, String categoryIDStr, String prototypeQuantityStr,
			String publishDateStr, String recuitDayStr, String trialDayStr, String bannerDes, String shortDescription, String description) {
		Long createrID = Long.parseLong(createrIDStr);
		Short categoryID = Short.parseShort(categoryIDStr);
		Long prototypeQuantity = Long.parseLong(prototypeQuantityStr);
		Date publishDate = null;
		try {
			publishDate = new SimpleDateFormat("dd/MM/yyyy").parse(publishDateStr);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Short recuitDay = Short.parseShort(recuitDayStr);
		Short trialDay = Short.parseShort(trialDayStr);
		// create new campaign
		McpsAccount creater = new McpsAccount();
		creater.setId(createrID);
		McpsCampaign newCampaign = new McpsCampaign(creater, campaignName, bannerDes, prototypeQuantity, recuitDay, trialDay, "new", new Date(), publishDate);
		newCampaign.setDescription(description);
		newCampaign.setShortDescription(shortDescription);
		HashSet interestSet = new HashSet<McpsInterest>();
		McpsInterest category = new McpsInterest();
		category.setId(categoryID);
		interestSet.add(category);
		newCampaign.setMcpsInterests(interestSet);
		campaignStorage.saveCampaign(newCampaign);
	}
	
	/**
	 * Create new campaign
	 * @param newCampaign
	 */
	public void updateCampaign(String idStr, String createrIDStr, String campaignName, String categoryIDStr, String prototypeQuantityStr,
			String publishDateStr, String recuitDayStr, String trialDayStr, String bannerDes, String shortDescription, String description) {
		Long id = Long.parseLong(idStr);
		Long createrID = Long.parseLong(createrIDStr);
		Short categoryID = Short.parseShort(categoryIDStr);
		Long prototypeQuantity = Long.parseLong(prototypeQuantityStr);
		Date publishDate = null;
		try {
			publishDate = new SimpleDateFormat("dd/MM/yyyy").parse(publishDateStr);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Short recuitDay = Short.parseShort(recuitDayStr);
		Short trialDay = Short.parseShort(trialDayStr);
		// create new campaign
		McpsCampaign oldCampaign = campaignStorage.getCampaign(id);
		Date createdDate = oldCampaign.getInitiateDate();
		McpsAccount creater = new McpsAccount();
		creater.setId(createrID);
		McpsCampaign updateCampaign = new McpsCampaign(creater, campaignName, bannerDes, prototypeQuantity, recuitDay, trialDay, "new", createdDate, publishDate);
		updateCampaign.setId(id);
		updateCampaign.setDescription(description);
		updateCampaign.setShortDescription(shortDescription);
		HashSet interestSet = new HashSet<McpsInterest>();
		McpsInterest category = new McpsInterest();
		category.setId(categoryID);
		interestSet.add(category);
		updateCampaign.setMcpsInterests(interestSet);
		campaignStorage.updateCampaign(updateCampaign);
	}
	
	/**
	 * Delete an campaign by id
	 * @param id
	 */
	public void delete(Long id) {
		campaignStorage.deleteCampaign(id);
	}
	
	/**
	 * Publish or unpublish a campaign
	 * @param campaignID
	 * @param isPublish true or false
	 */
	public void publish(Long campaignID, boolean isPublish) {
		if (isPublish) {
			campaignStorage.publish(campaignID, true);
		} else {
			campaignStorage.publish(campaignID, false);
		}
	}
	
	public boolean checkCampaignInfoForEditing(String id, String createrId,
			String campaignName, String categoryID, String prototypeQuantity,
			String publishDate, String recuitDay, String trialDay,
			String description) {
		boolean successful = true;

		// check campaign name
		if ("".equals(campaignName)) {
			successful = false;
			return successful;
		}

		// check number format
		try {
			Long.parseLong(id);
			Long.parseLong(createrId);
			Long.parseLong(categoryID);
			Short.parseShort(categoryID);
			Long.parseLong(prototypeQuantity);
			Short.parseShort(recuitDay);
			Short.parseShort(trialDay);
		} catch (NumberFormatException e) {
			successful = false;
			return successful;
		}

		// check datetime
		try {
			new SimpleDateFormat("dd/MM/yyyy").parse(publishDate);
		} catch (ParseException e) {
			successful = false;
			return successful;
		}
		return successful;
	}
	
	public boolean checkCampaignInfoForCreating(String createrid, String campaignName, String categoryID, String prototypeQuantity,
												String publishDate, String recuitDay, String trialDay, String description, MultipartFile bannerFile) {
		boolean successful = true;
		
		// check campaign name
		if ("".equals(campaignName)) {
			successful = false;
			return successful;
		}
		
		// check number format
		try {
			Long.parseLong(createrid);
			Long.parseLong(categoryID);
			Short.parseShort(categoryID);
			Long.parseLong(prototypeQuantity);
			Short.parseShort(recuitDay);
			Short.parseShort(trialDay);
		} catch (NumberFormatException e) {
			successful = false;
			return successful;
		}
		
		// check datetime
		try {
			new SimpleDateFormat("dd/MM/yyyy").parse(publishDate);
		} catch (ParseException e) {
			successful = false;
			return successful;
		}
		// check banner file
		if (bannerFile.isEmpty()) {
			successful = false;
			return successful;
		}
		return successful;
	}
}
