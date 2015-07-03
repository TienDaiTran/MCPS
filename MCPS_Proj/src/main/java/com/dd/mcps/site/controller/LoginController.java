package com.dd.mcps.site.controller;

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
import org.springframework.web.bind.annotation.ResponseBody;

import com.dd.mcps.entities.McpsAccount;
import com.dd.mcps.services.ManageAccountService;
import com.dd.mcps.util.HibernateUtil;

/**
 * Handles requests for the application home page.
 */
@Controller
public class LoginController {
	
	private static final Logger logger = LoggerFactory.getLogger(LoginController.class);
	
	@Autowired
	private ManageAccountService manageAccountService;
	
	/**
	 * Login controller
	 */
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String login(HttpSession session, HttpServletRequest request, final McpsAccount account, Model model) {
		logger.info("Login user");
		
		//logger.info("Welcome home! The client locale is {}.", locale);
		String success = "success";
		Boolean accountExist = manageAccountService.isAccountExist(account.getEmail(), account.getPass(), null);
		if (accountExist) {
			McpsAccount user = manageAccountService.getAccount(account.getEmail());
			session.invalidate();
			session = request.getSession();
			session.setAttribute("userid", user.getId());
			session.setAttribute("displayname", user.getDisplayName());
			session.setAttribute("email", user.getEmail());
			session.setAttribute("role", user.getMcpsRole().getId());
		} else {
			success = "unsuccess";
		}
		
		return "redirect:/";
	}
	
	/**
	 * Logout controller
	 */
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String logout(HttpSession session, HttpServletRequest request, final McpsAccount account, Model model) {
		logger.info("Login user");
		
		// delete session
		session.invalidate();
		
		return "redirect:/";
	}
	
}
