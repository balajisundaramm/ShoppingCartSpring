/**
 * 
 */
package com.spaneos.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.spaneos.service.WelcomeService;

/**
 * @author spaneos
 *
 */
@Controller
public class HomeController {
    private static final Logger LOGGER= LoggerFactory.getLogger(HomeController.class.getName());

	@Autowired
	private WelcomeService service;
	public HomeController() {
		LOGGER.error("Controller");
	}
	
	@RequestMapping("/")
	public String showHome() {
		return "home";
	}
	
	@RequestMapping("/adminLogin")
	public String adminLoginForm() {
		return "adminLoginForm";
	}
	
	@RequestMapping("/user_home")
	public String showUserHome() {
		return "userLoginView";
	}
	
	@PostMapping("/authenticate_admin")
	public String authenticateAdmin() {
		return "adminHome";
	}
}
