package com.dd.mcps.site.controller;

import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.dd.mcps.entities.McpsCampaign;
import com.dd.mcps.services.ManageAccountService;
import com.dd.mcps.services.ManageCampaignService;
import com.dd.mcps.util.HibernateUtil;
import com.dd.mcps.util.Util;

/**
 * Handles requests for the application home page.
 */
@Controller
public class PartnerManagementController {
	
	private static final Logger logger = LoggerFactory.getLogger(PartnerManagementController.class);
	
	@Autowired
	private ManageCampaignService manageCampaignService;
	
	@Autowired
	private ManageAccountService manageAccountService;
	
	/**
	 * Simply selects the home view to render by returning its name.
	 */
//	@RequestMapping(value = "/management/campaign", method = RequestMethod.GET)
//	public String partnerCampaignManagementPage(Model model, HttpSession session) {
//		logger.info("Redirect to partner management page");
//		
//		Long userID = (Long)session.getAttribute("userid");
//		model.addAttribute("page", 2);
//		model.addAttribute("content", "site/partition/partner-campaign-body");
//		model.addAttribute("campaigns", manageCampaignService.getCampaignsByCreator(userID));
//		
//		return "site/homepage";
//	}
	
	@RequestMapping(value = "/management/campaign", method = RequestMethod.GET)
	public String partnerCampaignManagementPageWithParam(@RequestParam(value="q", required = false) String request, 
			@RequestParam(value="id", required = false) Long campaignId,
			Model model, HttpSession session) {
		logger.info("Redirect to partner management page");
		if (request == null) {
			Long userID = (Long)session.getAttribute("userid");
			model.addAttribute("page", 2);
			model.addAttribute("content", "site/partition/partner-campaign-body");
			model.addAttribute("campaigns", manageCampaignService.getCampaignsByCreator(userID));
		}
		if ("create".equals(request)) {
		model.addAttribute("page", 2);
		model.addAttribute("content", "site/partition/partner-create-campaign-body");	
		model.addAttribute("categories", manageCampaignService.getAllCategories());
		} else if ("edit".equals(request)) {
			model.addAttribute("page", 2);
			model.addAttribute("content", "site/partition/partner-edit-campaign-body");
			model.addAttribute("categories", manageCampaignService.getAllCategories());
			model.addAttribute("campaignInfo", manageCampaignService.getCampaign(campaignId));
		}
		
		return "site/homepage";
	}

	@RequestMapping(value = "/management/campaign/create", headers=("content-type=multipart/*"), method = RequestMethod.POST)
	public @ResponseBody String createCampaign(@RequestParam(value="createrID") String createrID,
												@RequestParam(value="campaignName") String campaignName, 
												@RequestParam(value="category") String categoryID,
												@RequestParam(value="prototypeQuantity") String prototypeQuantity,
												@RequestParam(value="publishDate") String publishDate,
												@RequestParam(value="recuitDay") String recuitDay,
												@RequestParam(value="trialDay") String trialDay,
												@RequestParam(value="banner") MultipartFile bannerFile,
												@RequestParam(value="shortDescription") String shortDescription,
												@RequestParam(value="description") String description,
												HttpServletRequest request, HttpSession session) {
		
		String success = "unsuccess";
		
		Long userID = (Long)session.getAttribute("userid");
		
		try {
			campaignName = Util.convertToUtf8(campaignName);
		} catch (UnsupportedEncodingException e) {
		}
		try {
			description = Util.convertToUtf8(description);
		} catch (UnsupportedEncodingException e) {
		}
		
		if (manageCampaignService.checkCampaignInfoForCreating("1", campaignName, categoryID, 
				prototypeQuantity, publishDate, recuitDay, trialDay, description, bannerFile)) {
			String bannerDestinationFolder = request.getSession().getServletContext().getRealPath("/resources/img/campaign/");
			String bannerFileName = bannerFile.getOriginalFilename();
			String bannerDestination = "/resources/img/campaign/" + bannerFileName;
			boolean saveImgFlag = Util.saveImage(bannerFile, bannerDestinationFolder, bannerFileName);
			if (saveImgFlag) {
				//Save the id you have used to create the file name in the DB. You can retrieve the image in future with the ID.
				manageCampaignService.createCampaign("" + userID, campaignName, categoryID, 
						prototypeQuantity, publishDate, recuitDay, trialDay, bannerDestination, 
						shortDescription, description);
				success = "success";
			}
		}
		
		return success;
	}
	
	@RequestMapping(value = "/management/campaign/edit", headers=("content-type=multipart/*"), method = RequestMethod.POST)
	public @ResponseBody String editCampaign(@RequestParam(value="id") String idStr,
			@RequestParam(value="createrID") String createrID,
			@RequestParam(value="campaignName") String campaignName, 
			@RequestParam(value="category") String categoryID,
			@RequestParam(value="prototypeQuantity") String prototypeQuantity,
			@RequestParam(value="publishDate") String publishDate,
			@RequestParam(value="recuitDay") String recuitDay,
			@RequestParam(value="trialDay") String trialDay,
			@RequestParam(value="banner") MultipartFile bannerFile,
			@RequestParam(value="shortDescription") String shortDescription,
			@RequestParam(value="description") String description,
			HttpServletRequest request, HttpSession session) {
		
		String success = "unsuccess";
		
		Long userID = (Long)session.getAttribute("userid");
		
		try {
			campaignName = Util.convertToUtf8(campaignName);
		} catch (UnsupportedEncodingException e) {
		}
		try {
			description = Util.convertToUtf8(description);
		} catch (UnsupportedEncodingException e) {
		}
		
		if (manageCampaignService.checkCampaignInfoForEditing(idStr, "1", campaignName, categoryID, 
				prototypeQuantity, publishDate, recuitDay, trialDay, description)) {
			String bannerDestination = "";
			if (bannerFile.isEmpty()) {
				Long id = Long.parseLong(idStr);
				McpsCampaign oldCampaign = manageCampaignService.getCampaign(id);
				bannerDestination = oldCampaign.getBannerImage();
				//update campaign 
				manageCampaignService.updateCampaign(idStr, "" + userID, campaignName, 
						categoryID, prototypeQuantity, publishDate, recuitDay, trialDay, 
						bannerDestination, shortDescription, description);
				success = "success";
			} else {
				String bannerDestinationFolder = request.getSession().getServletContext().getRealPath("/resources/img/campaign/");
				String bannerFileName = bannerFile.getOriginalFilename();
				bannerDestination = "/resources/img/campaign/" + bannerFileName;
				boolean saveImgFlag = Util.saveImage(bannerFile, bannerDestinationFolder, bannerFileName);
				if (saveImgFlag) {
					//update campaign 
					manageCampaignService.updateCampaign(idStr, "" + userID, campaignName, 
							categoryID, prototypeQuantity, publishDate, recuitDay, trialDay, 
							bannerDestination, shortDescription, description);
					success = "success";
				}
			}

		}
		
		return success;
	}
	
	@RequestMapping(value = "/management/campaign/publish", method = RequestMethod.POST)
	public @ResponseBody String publishCampaign(@RequestParam(value="id") Long campaignID) {
		
		String success = "success";

		manageCampaignService.publish(campaignID, true);
		
		return success;
	}
	
	@RequestMapping(value = "/management/campaign/delete", method = RequestMethod.POST, 
			params = {"id"})
	public @ResponseBody String deleteCampaign(@RequestParam(value="id") Long campaignID, HttpSession session) {
		
		String success = "unsuccess";
		
		Object useridObj = session.getAttribute("userid");
		if (useridObj != null) {
			Long creatorId = manageCampaignService.getCampaign(campaignID).getMcpsAccount().getId();
			Long userId = (Long) useridObj;
			if (creatorId.equals(userId)) {
				manageCampaignService.delete(campaignID);
				success = "success";
			}
		}
		
		return success;
	}
}
