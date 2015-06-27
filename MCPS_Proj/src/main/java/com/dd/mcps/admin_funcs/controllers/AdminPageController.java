package com.dd.mcps.admin_funcs.controllers;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.SessionFactory;
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

import com.dd.mcps.HomeController;
import com.dd.mcps.general_user_funcs.services.LoginService;
import com.dd.mcps.services.ManageAccountService;
import com.dd.mcps.util.HibernateUtil;

@Controller
public class AdminPageController {

	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	@RequestMapping(value = "/dashboard/fhjshfjkahfjhajk", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		logger.info("Redirect to admin login page");
		
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		
		return "admin/admin-home-page";
	}
}
