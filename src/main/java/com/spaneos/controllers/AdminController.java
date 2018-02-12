package com.spaneos.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
@Controller
public class AdminController {
	
	@GetMapping("/admin_home")
	public String authenticateAdmin() {
		return "adminHome";
	}
	@RequestMapping("/add_category_form")
	public String showCategoryForm() {
		return "categoryForm";
	}
	
	@RequestMapping("/add_category")
	public String addCategory() {
		return "adminHome";
	}
	
	@RequestMapping("/product_list")
	public String showProducts() {
		return "adminCategories";
	}
	
	@RequestMapping("/user_list")
	public String showUserList() {
		return "userList";
	}
}
