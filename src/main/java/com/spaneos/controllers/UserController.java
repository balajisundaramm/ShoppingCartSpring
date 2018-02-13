/**
 * 
 */
package com.spaneos.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.spaneos.dao.UserDao;
import com.spaneos.model.User;
import com.spaneos.service.AdminService;
import com.spaneos.service.UserService;

/**
 * @author spaneos
 *
 */
@Controller
public class UserController {
    private static final Logger LOGGER= LoggerFactory.getLogger(UserController.class.getName());
    
    @Autowired
    private UserService userService;

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
	
	@PostMapping("/register")
	public String register(@ModelAttribute User user, BindingResult result, Model model ) {
		LOGGER.error("User Bean {}", user);
		if(result.hasErrors()) {
			LOGGER.error("Error {}", result.getAllErrors());
			model.addAttribute("message", "Oops somethimng went wrong. Contact admin!!");
			return "home";  
		}
		else {
			userService.register(user);
			LOGGER.error("User Registration Successfull!!!");
			return "userHome";
		}
	}
	
}
