/**
 * 
 */
package com.spaneos.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author balaji
 *
 */
@Controller
public class HomeController {
	/**
	 * Getting logger for this class.
	 */
    private static final Logger LOGGER= LoggerFactory.getLogger(HomeController.class.getName());
    	
	/**
	 * Controller to open the welcome page of the application.
	 * @return
	 */
	@RequestMapping("/")
	public String showHome() {
		return "home";
	}
	/**
	 * Controller to open admin login form
	 * @return
	 */
	@RequestMapping("/adminLogin")
	public String adminLoginForm() {
		return "adminLoginForm";
	}
	/**
	 * Controller to open user pre-login view.
	 * @return
	 */
	@RequestMapping("/user_login_view")
	public String showUserHome() {
		return "userLoginView";
	}

}
