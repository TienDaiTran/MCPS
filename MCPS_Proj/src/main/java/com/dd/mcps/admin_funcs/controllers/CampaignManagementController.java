package com.dd.mcps.admin_funcs.controllers;

import java.awt.image.BufferedImage;
import java.beans.PropertyEditorSupport;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;

import org.hibernate.SessionFactory;
import org.hibernate.annotations.Parameter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.dd.mcps.HomeController;
import com.dd.mcps.entities.McpsAccount;
import com.dd.mcps.entities.McpsCampaign;
import com.dd.mcps.entities.McpsGender;
import com.dd.mcps.entities.McpsPartneraccount;
import com.dd.mcps.entities.McpsRevieweraccount;
import com.dd.mcps.entities.McpsRole;
import com.dd.mcps.general_user_funcs.services.LoginService;
import com.dd.mcps.services.ManageAccountService;
import com.dd.mcps.services.ManageCampaignService;
import com.dd.mcps.storage.CampaignStorage;
import com.dd.mcps.util.HibernateUtil;
import com.dd.mcps.util.Util;
import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;

@Controller
public class CampaignManagementController {

	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	@Autowired
	private ManageCampaignService manageCampaignService;
	
	@Autowired
	private ManageAccountService manageAccountService;
	
	@RequestMapping(value = "/admin/campaign", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		logger.info("Redirect to admin dashboard page");
		
		model.addAttribute("content", "admin/partition/campaign-management");
		model.addAttribute("categories", manageCampaignService.getAllCategories());
		model.addAttribute("campaigns", manageCampaignService.getAllCampaigns());
		model.addAttribute("sidebarId", 2);
		
		return "admin/admin-home-page";
	}
	
	@RequestMapping(value = "/admin/campaign", method = RequestMethod.POST, 
			params = {"campaignID", "campaignName", "category", "createrName", "initDate"})
	public String search(@RequestParam(value = "campaignID") String campaignIDStr, 
			@RequestParam(value = "campaignName") String campaignName, 
			@RequestParam(value = "category") String categoryIDStr,
			@RequestParam(value = "createrName") String createrName,
			@RequestParam(value = "initDate") String initDate, Model model) {
		logger.info("Search account");
		
		List<McpsCampaign> campaigns = manageCampaignService.searchCampaign(campaignIDStr, 
				campaignName, categoryIDStr, createrName, initDate);
		model.addAttribute("campaigns", campaigns);
		return "admin/partition/campaign-management :: listCampaign";
	}
	
	@RequestMapping(value = "/admin/campaign/create", method = RequestMethod.GET)
	public String createCampaignPage(Model model) {
		

		model.addAttribute("content", "admin/partition/add-new-campaign");
		model.addAttribute("categories", manageCampaignService.getAllCategories());
		model.addAttribute("sidebarId", 2);
		
		return "admin/admin-home-page";
	}
	
	@RequestMapping(value = "/admin/campaign/create", headers=("content-type=multipart/*"), method = RequestMethod.POST)
	public @ResponseBody String createCampaign(@RequestParam(value="createrID") String createrID,
												@RequestParam(value="campaignName") String campaignName, 
												@RequestParam(value="category") String categoryID,
												@RequestParam(value="prototypeQuantity") String prototypeQuantity,
												@RequestParam(value="publishDate") String publishDate,
												@RequestParam(value="recuitDay") String recuitDay,
												@RequestParam(value="trialDay") String trialDay,
												@RequestParam(value="banner") MultipartFile bannerFile,
												@RequestParam(value="description") String description,
												HttpServletRequest request) {
		
		String success = "unsuccess";
		
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
				manageCampaignService.createCampaign("1", campaignName, categoryID, prototypeQuantity, publishDate, recuitDay, trialDay, bannerDestination, description);
				success = "success";
			}
		}
		
		return success;
	}
	
	@RequestMapping(value = "/admin/campaign/edit", method = RequestMethod.GET,
			params = {"id"})
	public String editAccountPage(@RequestParam(value = "id") String idString, Model model) {
		
		long id = 0;
		if (idString != "") {
			try {
				id = Long.parseLong(idString);
			} catch (NumberFormatException e) {
				// bo qua
			}
		}
		model.addAttribute("content", "admin/partition/edit-campaign");
		model.addAttribute("categories", manageCampaignService.getAllCategories());
		model.addAttribute("campaignInfo", manageCampaignService.getCampaign(id));
		model.addAttribute("sidebarId", 2);
		
		return "admin/admin-home-page";
	}
	
	@RequestMapping(value = "/admin/campaign/edit", headers=("content-type=multipart/*"), method = RequestMethod.POST)
	public @ResponseBody String editAccount(@RequestParam(value="id") String idStr,
			@RequestParam(value="createrID") String createrID,
			@RequestParam(value="campaignName") String campaignName, 
			@RequestParam(value="category") String categoryID,
			@RequestParam(value="prototypeQuantity") String prototypeQuantity,
			@RequestParam(value="publishDate") String publishDate,
			@RequestParam(value="recuitDay") String recuitDay,
			@RequestParam(value="trialDay") String trialDay,
			@RequestParam(value="banner") MultipartFile bannerFile,
			@RequestParam(value="description") String description,
			HttpServletRequest request) {
		
		String success = "unsuccess";
		
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
				manageCampaignService.updateCampaign(idStr, "1", campaignName, 
						categoryID, prototypeQuantity, publishDate, recuitDay, trialDay, bannerDestination, description);
				success = "success";
			} else {
				String bannerDestinationFolder = request.getSession().getServletContext().getRealPath("/resources/img/campaign/");
				String bannerFileName = bannerFile.getOriginalFilename();
				bannerDestination = "/resources/img/campaign/" + bannerFileName;
				boolean saveImgFlag = Util.saveImage(bannerFile, bannerDestinationFolder, bannerFileName);
				if (saveImgFlag) {
					//update campaign 
					manageCampaignService.updateCampaign(idStr, "1", campaignName, 
							categoryID, prototypeQuantity, publishDate, recuitDay, trialDay, bannerDestination, description);
					success = "success";
				}
			}

		}
		
		return success;
	}
	
	@RequestMapping(value = "/admin/campaign/delete", method = RequestMethod.POST, 
			params = {"id"})
	public @ResponseBody String editAccount(@RequestParam(value="id") String idStr, Model model) {
		
		String success = "unsuccess";
		try {
			Long id = Long.parseLong(idStr);
			manageCampaignService.delete(id);
			success = "success";
		} catch (NumberFormatException e) {
			success = "unsuccess";
		}
		
		return success;
	}
	
	@InitBinder
	public void binder(WebDataBinder binder) {
		binder.registerCustomEditor(Date.class,
	    new PropertyEditorSupport() {
	        public void setAsText(String value) {
	            try {
	                Date parsedDate = new SimpleDateFormat("dd/MM/yyyy").parse(value);
	                setValue(new Timestamp(parsedDate.getTime()));
	            } catch (ParseException e) {
	                setValue(null);
	            }
	        }
	    });
	}
}
