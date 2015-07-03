package com.dd.mcps.site.controller;

import java.beans.PropertyEditorSupport;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
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
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dd.mcps.entities.McpsAccount;
import com.dd.mcps.entities.McpsGender;
import com.dd.mcps.entities.McpsInterest;
import com.dd.mcps.entities.McpsPartneraccount;
import com.dd.mcps.entities.McpsRevieweraccount;
import com.dd.mcps.entities.McpsRole;
import com.dd.mcps.services.ManageAccountService;
import com.dd.mcps.util.HibernateUtil;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	@Autowired
	private ManageAccountService manageAccountService;
	
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		logger.info("Redirect to home page");
		
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		model.addAttribute("page", 1);
		model.addAttribute("content", "site/partition/home-body");
		
		return "site/homepage";
	}
	
	/**
	 * Sign in controller
	 */
	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public String register(Model model) {	
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
		model.addAttribute("content", "site/partition/register-body");
		model.addAttribute("roles", manageAccountService.getRolesButAdmin());
		model.addAttribute("genders", manageAccountService.getGenders());
		model.addAttribute("occupations", manageAccountService.getOccupations());
		model.addAttribute("interests", manageAccountService.getAllInterests());
		model.addAttribute("newAccount", newAccount);
		model.addAttribute("sidebarId", 1);
		
		return "site/homepage";
	}
	
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public @ResponseBody String createNewAccount(Model model, @ModelAttribute("newAccount") McpsAccount newAccount, BindingResult result) {
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
	
	/**
	 * Edit user info controller
	 */
	@RequestMapping(value = "/userinfo", method = RequestMethod.GET)
	public String userInfo(Model model, HttpSession session) {
		
		Object userIdObj = session.getAttribute("userid");
		if(userIdObj != null){
			Long userId = (Long)userIdObj;
			McpsAccount account = manageAccountService.getAccount(userId);
				McpsAccount editAccount = manageAccountService.getAccount(userId);
				model.addAttribute("content", "site/partition/edit-user-info");
				model.addAttribute("roles", manageAccountService.getAllRoles());
				model.addAttribute("editAccount", editAccount);
				// attributes only for reviewer
				if (editAccount.getMcpsRole().getId() == 3) {
					model.addAttribute("genders", manageAccountService.getGenders());
					model.addAttribute("occupations", manageAccountService.getOccupations());
					model.addAttribute("interests", manageAccountService.getAllInterests());
					
					// make selected interest list
					List<Short> selectedInterests = new ArrayList<Short>();
					for (McpsInterest interest : editAccount.getMcpsRevieweraccount().getMcpsInterests()) {
						selectedInterests.add(interest.getId());
					}
					model.addAttribute("selectedInterests", selectedInterests);
				}
		}
		
		return "site/homepage";
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
