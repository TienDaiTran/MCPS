package com.dd.mcps.admin_funcs.controllers;

import java.beans.PropertyEditorSupport;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.SessionFactory;
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

import com.dd.mcps.HomeController;
import com.dd.mcps.entities.McpsAccount;
import com.dd.mcps.entities.McpsGender;
import com.dd.mcps.entities.McpsPartneraccount;
import com.dd.mcps.entities.McpsRevieweraccount;
import com.dd.mcps.entities.McpsRole;
import com.dd.mcps.general_user_funcs.services.LoginService;
import com.dd.mcps.services.ManageAccountService;
import com.dd.mcps.util.HibernateUtil;
import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;

@Controller
public class CampaignManagementController {

	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	@Autowired
	private ManageAccountService manageAccountService;
	
	@RequestMapping(value = "/admin/campaign", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		logger.info("Redirect to admin dashboard page");
		
		model.addAttribute("content", "admin/partition/campaign-management");
		model.addAttribute("roles", manageAccountService.getAllRoles());
		model.addAttribute("accounts", manageAccountService.getAllAccounts());
		
		return "admin/admin-home-page";
	}
	
	@RequestMapping(value = "/admin/campaign", method = RequestMethod.POST, 
			params = {"id", "email", "role"})
	public String search(@RequestParam(value = "id") String id, 
			@RequestParam(value = "email") String email, 
			@RequestParam(value = "role") String roleID, Model model) {
		logger.info("Search account");
		
		McpsRole role = new McpsRole();
		if (roleID != "" && !"0".equals(roleID)) {
			try {
				role.setId(Byte.parseByte(roleID));
			} catch (NumberFormatException e) {
				// bo qua
			}
		}	
		
		McpsAccount criteria = new McpsAccount();
		criteria.setEmail(email);
		if (id != "") {
			try {
				criteria.setId(Long.parseLong(id));
			} catch (NumberFormatException e) {
				// bo qua
			}
		}
		criteria.setMcpsRole(role);
		List<McpsAccount> accounts = manageAccountService.searchAccount(criteria);
		
		model.addAttribute("content", "admin/partition/campaign-management");
		model.addAttribute("roles", manageAccountService.getAllRoles());
		model.addAttribute("accounts", accounts);
		return "admin/partition/campaign-management :: listCampaign";
		//return "admin/admin-home-page";
	}
	
	@RequestMapping(value = "/admin/campaign/create", method = RequestMethod.GET)
	public String createAccountPage(Model model) {
		
		McpsRole role = new McpsRole();
		McpsGender gender = new McpsGender();
		McpsPartneraccount partnerAccountInfo = new McpsPartneraccount();
		McpsRevieweraccount revieweraccountInfo = new McpsRevieweraccount();
		revieweraccountInfo.setMcpsGender(gender);
		McpsAccount newAccount = new McpsAccount();
		newAccount.setMcpsRole(role);
		newAccount.setMcpsPartneraccount(partnerAccountInfo);
		newAccount.setMcpsRevieweraccount(revieweraccountInfo);
		model.addAttribute("content", "admin/partition/add-new-campaign");
		model.addAttribute("roles", manageAccountService.getAllRoles());
		model.addAttribute("genders", manageAccountService.getGenders());
		model.addAttribute("occupations", manageAccountService.getOccupations());
		model.addAttribute("newAccount", newAccount);
		
		return "admin/admin-home-page";
	}
	
	@RequestMapping(value = "/admin/campaign/create", method = RequestMethod.POST)
	public @ResponseBody String createAccount(Model model, @ModelAttribute("newAccount") McpsAccount newAccount, BindingResult result) {
		
		String success = "unsuccess";
		newAccount.setState("active");
		if (!manageAccountService.isAccountExist(newAccount.getEmail())) {
			if (newAccount.getMcpsPartneraccount() != null) {
				newAccount.getMcpsPartneraccount().setMcpsAccount(newAccount);
			}
			if (newAccount.getMcpsRevieweraccount() != null) {
				newAccount.getMcpsRevieweraccount().setMcpsAccount(newAccount);
			}
			manageAccountService.createAccount(newAccount);
			success = "success";
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
				McpsAccount editAccount = manageAccountService.getAccount(id);
				model.addAttribute("content", "admin/partition/edit-account");
				model.addAttribute("roles", manageAccountService.getAllRoles());
				model.addAttribute("genders", manageAccountService.getGenders());
				model.addAttribute("occupations", manageAccountService.getOccupations());
				model.addAttribute("editAccount", editAccount);
			} catch (NumberFormatException e) {
				// bo qua
			}
		}
		
		return "admin/admin-home-page";
	}
	
	@RequestMapping(value = "/admin/campaign/edit", method = RequestMethod.POST)
	public @ResponseBody String editAccount(@ModelAttribute("editAccount") McpsAccount editAccount, Model model) {
		
		String success = "unsuccess";
		if (manageAccountService.updateAccountInfo(editAccount)) {	
			success = "success";
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