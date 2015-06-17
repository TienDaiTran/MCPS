package com.dd.mcps.admin_funcs.controllers;

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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dd.mcps.HomeController;
import com.dd.mcps.admin_funcs.services.ManageAccountService;
import com.dd.mcps.entities.McpsAccount;
import com.dd.mcps.entities.McpsRole;
import com.dd.mcps.general_user_funcs.services.LoginService;
import com.dd.mcps.util.HibernateUtil;

@Controller
public class AccountManagementController {

	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	@Autowired
	private ManageAccountService manageAccountService;
	
	@RequestMapping(value = "/dashboard", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		logger.info("Redirect to admin dashboard page");
		
		model.addAttribute("content", "admin/partition/account-management");
		model.addAttribute("roles", manageAccountService.getAllRoles());
		model.addAttribute("accounts", manageAccountService.getAllAccounts());
		
		return "admin/admin-home-page";
	}
	
	@RequestMapping(value = "/dashboard/account", method = RequestMethod.POST, 
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
		//return "admin/admin-home-page";
	}
	
	@RequestMapping(value = "/dashboard/account", method = RequestMethod.POST, 
			params = {"id", "block"})
	public @ResponseBody String blockAccount(@RequestParam(value = "id") String idString, @RequestParam(value = "block") String block, Model model) {
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
	
	@RequestMapping(value = "/dashboard/account/delete", method = RequestMethod.POST, 
			params = {"id"})
	public @ResponseBody String deleteAccount(@RequestParam(value = "id") String idString, Model model) {
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
}
