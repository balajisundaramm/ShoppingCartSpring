/**
 * 
 */
package com.spaneos.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author spaneos
 *
 */
@Controller
public class UserController {
    private static final Logger LOGGER= LoggerFactory.getLogger(UserController.class.getName());

	public UserController() {
		LOGGER.error("User Controller");
	}
	
	@RequestMapping("/user_signup")
	public String showSignUpForm() {
		return "userSignUpForm";
	}
	
	@RequestMapping("/user_login")
	public String showLoginForm() {
		return "userLoginForm";
	}
	
	@PostMapping("/authenticate_user")
	public String showUserHome() {
		return "userHome";
	}
	
	
	
	
}
