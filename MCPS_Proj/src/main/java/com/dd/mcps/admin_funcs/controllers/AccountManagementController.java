package com.dd.mcps.admin_funcs.controllers;

import java.beans.PropertyEditorSupport;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
import com.dd.mcps.entities.McpsInterest;
import com.dd.mcps.entities.McpsPartneraccount;
import com.dd.mcps.entities.McpsRevieweraccount;
import com.dd.mcps.entities.McpsRole;
import com.dd.mcps.general_user_funcs.services.LoginService;
import com.dd.mcps.services.ManageAccountService;
import com.dd.mcps.util.HibernateUtil;
import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;

@Controller
public class AccountManagementController {

	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	@Autowired
	private ManageAccountService manageAccountService;
	
	@RequestMapping(value = "/admin/account", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		logger.info("Redirect to admin dashboard page");
		
		model.addAttribute("content", "admin/partition/account-management");
		model.addAttribute("roles", manageAccountService.getAllRoles());
		model.addAttribute("accounts", manageAccountService.getAllAccounts());
		model.addAttribute("sidebarId", 1);
		
		return "admin/admin-home-page";
	}
	
	@RequestMapping(value = "/admin/account", method = RequestMethod.POST, 
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
		
		model.addAttribute("content", "admin/partition/account-management");
		model.addAttribute("roles", manageAccountService.getAllRoles());
		model.addAttribute("accounts", accounts);
		return "admin/partition/account-management :: listAccount";
	}
	
	@RequestMapping(value = "/admin/account", method = RequestMethod.POST, 
			params = {"id", "block"})
	public @ResponseBody String blockAccount(@RequestParam(value = "id") String idString, @RequestParam(value = "block") String block, Model model) {
		logger.info("Block account" + idString);
		
		boolean isBlock = false;
		if ("true".equals(block)) {
			isBlock = true;
		}
		String result = "";
		try {
			Long id = Long.parseLong(idString);
			manageAccountService.block(id, isBlock);
			result = "success";
		} catch (NumberFormatException e) {
			result = "unsuccess";
		}
		return result;
	}
	
	@RequestMapping(value = "/admin/account/delete", method = RequestMethod.POST, 
			params = {"id"})
	public @ResponseBody String deleteAccount(@RequestParam(value = "id") String idString, Model model) {
		logger.info("Block account" + idString);
		
		String result = "";
		try {
			Long id = Long.parseLong(idString);
			manageAccountService.delete(id);
			result = "success";
		} catch (NumberFormatException e) {
			result = "unsuccess";
		}
		return result;
	}
	
	@RequestMapping(value = "/admin/account/create", method = RequestMethod.GET)
	public String createAccountPage(Model model) {
		logger.info("Redirect to create account page");
		
		McpsRole role = new McpsRole();
		McpsGender gender = new McpsGender();
		McpsPartneraccount partnerAccountInfo = new McpsPartneraccount();
		McpsRevieweraccount revieweraccountInfo = new McpsRevieweraccount();
		revieweraccountInfo.setMcpsGender(gender);
		McpsAccount newAccount = new McpsAccount();
		newAccount.setMcpsRole(role);
		newAccount.setMcpsPartneraccount(partnerAccountInfo);
		newAccount.setMcpsRevieweraccount(revieweraccountInfo);
		model.addAttribute("content", "admin/partition/add-new-account");
		model.addAttribute("roles", manageAccountService.getAllRoles());
		model.addAttribute("genders", manageAccountService.getGenders());
		model.addAttribute("occupations", manageAccountService.getOccupations());
		model.addAttribute("interests", manageAccountService.getAllInterests());
		model.addAttribute("newAccount", newAccount);
		model.addAttribute("sidebarId", 1);
		
		return "admin/admin-home-page";
	}
	
	@RequestMapping(value = "/admin/account/create", method = RequestMethod.POST)
	public @ResponseBody String createAccount(Model model, @ModelAttribute("newAccount") McpsAccount newAccount, BindingResult result) {
		logger.info("Create new account");
		
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
	
	@RequestMapping(value = "/admin/account/edit", method = RequestMethod.GET,
			params = {"id"})
	public String editAccountPage(@RequestParam(value = "id") String idString, Model model) {
		logger.info("Redirect to editing account page");
		
		long id = 0;
		if (idString != "") {
			try {
				id = Long.parseLong(idString);
				McpsAccount editAccount = manageAccountService.getAccount(id);
				model.addAttribute("content", "admin/partition/edit-account");
				model.addAttribute("roles", manageAccountService.getAllRoles());
				model.addAttribute("genders", manageAccountService.getGenders());
				model.addAttribute("occupations", manageAccountService.getOccupations());
				model.addAttribute("interests", manageAccountService.getAllInterests());
				model.addAttribute("editAccount", editAccount);
				// make selected interest list
				List<Short> selectedInterests = new ArrayList<Short>();
				for (McpsInterest interest : editAccount.getMcpsRevieweraccount().getMcpsInterests()) {
					selectedInterests.add(interest.getId());
				}
				model.addAttribute("selectedInterests", selectedInterests);
			} catch (NumberFormatException e) {
				// bo qua
			}
		}
		model.addAttribute("sidebarId", 1);
		
		return "admin/admin-home-page";
	}
	
	@RequestMapping(value = "/admin/account/edit", method = RequestMethod.POST)
	public @ResponseBody String editAccount(@ModelAttribute("editAccount") McpsAccount editAccount, Model model) {
		logger.info("Edit account");
		
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
	
	@InitBinder
    protected void initBinder(final WebDataBinder binder) {
        binder.registerCustomEditor(McpsInterest.class, new InterestPropertyEditor ());
    }

    private static class InterestPropertyEditor extends PropertyEditorSupport {
        @Override
        public void setAsText(String interestId) {
            final McpsInterest interest = new McpsInterest();
            interest.setId(Short.parseShort(interestId));
            setValue(interest);
        }
    }
}
