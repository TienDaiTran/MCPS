package com.dd.mcps.general_user_funcs.controllers;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dd.mcps.general_user_funcs.services.LoginService;
import com.dd.mcps.services.ManageAccountService;

/**
 * Handles requests for the application home page.
 */
@Controller
public class LoginController {
	
	private static final Logger logger = LoggerFactory.getLogger(LoginController.class);
	
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public @ResponseBody String home(HttpServletRequest request,
			@RequestParam(value="email", required=false) String email,
			@RequestParam(value="password", required=false) String password) {
		//logger.info("Welcome home! The client locale is {}.", locale);
		ApplicationContext context = new ClassPathXmlApplicationContext("Spring-Module.xml");
		LoginService service = context.getBean("loginService", LoginService.class);
		Boolean accountExist = service.IsAccountExist(email, password);
		if (accountExist) {
			return "Exist";
		} else {
			return "Email or Password is not valid.";
		}
	}
	
}
