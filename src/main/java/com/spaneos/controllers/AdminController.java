package com.spaneos.controllers;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.spaneos.model.CategoryBean;
import com.spaneos.model.Product;
import com.spaneos.model.User;
import com.spaneos.service.AdminService;
@Controller
public class AdminController {
	private static final Logger LOGGER= LoggerFactory.getLogger(AdminController.class.getName());

	@Autowired
	private AdminService service; 


	@PostMapping("/authenticate_admin")
	public String authenticateAdmin(Model model) {
		List<CategoryBean> categories=service.fetchAllCategories();
		model.addAttribute("categories", categories);
		return "adminHome";
	}

	@GetMapping("/admin_home")
	public String showAdminHome(Model model) {
		List<CategoryBean> categories=service.fetchAllCategories();
		model.addAttribute("categories", categories);
		return "adminHome";
	}


	@RequestMapping("/add_category_form")
	public String showCategoryForm(Model model) {
		List<CategoryBean> categories=service.fetchAllCategories();
		model.addAttribute("categories", categories);
		return "categoryForm";
	}

	@RequestMapping("/add_category")
	public String addCategory(@ModelAttribute CategoryBean category, BindingResult result, Model model) {
		if(result.hasErrors()) {
			LOGGER.error("Error {}", result.getAllErrors());
			model.addAttribute("message", "Oops somethimng went wrong. Contact admin!!");
			return "home";  
		}
		else {
			LOGGER.error("Category {}", category);
			service.addCategory(category);
			List<CategoryBean> categories=service.fetchAllCategories();
			model.addAttribute("categories", categories);
			return "adminHome";
		}
	}

	@GetMapping("/category/{name}")
	public String showProducts(Model model, @PathVariable("name") String name ) {
		List<CategoryBean> categories=service.fetchAllCategories();
		model.addAttribute("categories", categories);
		List<Product> products=service.fetchProductByCategoryName(name);
		model.addAttribute("products", products);
		model.addAttribute("categoryName", name);
		return "adminCategories";
	} 
	
	@GetMapping("/edit_category/{name}")
	public String showEditCategoryForm(Model model, @PathVariable("name") String name ) {
		List<CategoryBean> categories=service.fetchAllCategories();
		model.addAttribute("categories", categories);
		CategoryBean category=service.fetchSpecificCategory(name);
		model.addAttribute("category", category);
		return "editCategoryForm";
	} 

	@RequestMapping("/user_list")
	public String showUserList(Model model) {
		List<CategoryBean> categories=service.fetchAllCategories();
		model.addAttribute("categories", categories);
		List<User> users=service.fetchUsers();
		model.addAttribute("users", users);
		return "userList";
	}
	@GetMapping("/product/{categoryName}/{productName}")
	public String editProductForm(Model model, @PathVariable("categoryName") String categoryName, 
			@PathVariable("productName") String productName) {
		List<CategoryBean> categories=service.fetchAllCategories();
		model.addAttribute("categories", categories);
		Product product=service.fetchSpecificProduct(productName, categoryName);
		model.addAttribute("product", product);
		return "editProductForm";
	}
	@RequestMapping("/user/{id}")
	public String editUserForm(Model model, @PathVariable("id") long id) {
		List<CategoryBean> categories=service.fetchAllCategories();
		model.addAttribute("categories", categories);
		User user=service.fetchSpecificUser(id);
		model.addAttribute("user", user);
		return "editUserForm";
	}

	@PostMapping("/add_product")
	public String addProduct(@ModelAttribute Product product, BindingResult result, Model model) {
		if(result.hasErrors()) {
			LOGGER.error("Error {}", result.getAllErrors());
			model.addAttribute("message", "Oops somethimng went wrong. Contact admin!!");
			return "home";
		}
		else {
			List<CategoryBean> categories=service.fetchAllCategories();
			model.addAttribute("categories", categories);
			String response=service.addProduct(product);
			LOGGER.error("Add Product {} ",response);
			List<Product> products=service.fetchProductByCategoryName(product.getCategoryName());
			model.addAttribute("products", products);
			model.addAttribute("categoryName", product.getCategoryName());
			return "adminCategories";
		}

	}

	@RequestMapping("/add_product_form")
	public String openAddProductForm(Model model) {
		List<CategoryBean> categories=service.fetchAllCategories();
		model.addAttribute("categories", categories);
		return "addProductForm";
	}
	@GetMapping("/delete_user/{id}")
	public String deleteUser(Model model, @PathVariable("id") long id) {
		List<CategoryBean> categories=service.fetchAllCategories();
		model.addAttribute("categories", categories);
		String response=service.deleteUser(id);
		List<User> users=service.fetchUsers();
		model.addAttribute("users", users);
		return "userList";
	}
	@GetMapping("/delete_product/{id}/{name}")
	public String deleteProduct(Model model, @PathVariable("id") long id, @PathVariable("name") String name) {
		List<CategoryBean> categories=service.fetchAllCategories();
		model.addAttribute("categories", categories);
		String response=service.deleteProduct(id);
		LOGGER.error("error response "+response);
		List<Product> products=service.fetchProductByCategoryName(name);
		model.addAttribute("products", products);
		model.addAttribute("categoryName", name);
		return "adminCategories";
	}
	
	@GetMapping("/delete_category/{name}")
	public String deleteCategory(Model model, @PathVariable("name") String name) {
		String response=service.deleteCategory(name); 
		LOGGER.error("error response in delete category "+response);
		List<CategoryBean> categories=service.fetchAllCategories();
		model.addAttribute("categories", categories);
		return "adminHome";
	}
	
	@PostMapping("/edit_user")
	public String editUser(Model model, @ModelAttribute User user, BindingResult result) {
		List<CategoryBean> categories=service.fetchAllCategories();
		model.addAttribute("categories", categories);
		service.editUser(user);
		List<User> users=service.fetchUsers();
		model.addAttribute("users", users);
		return "userList";
	}
	
	@PostMapping("/edit_category")
	public String editCategory(Model model, @ModelAttribute CategoryBean category, BindingResult result) {
		service.editCategory(category);
		List<CategoryBean> categories=service.fetchAllCategories();
		model.addAttribute("categories", categories);
		return "adminHome";
	}
	
	@PostMapping("/edit_product")
	public String editProduct(Model model, @ModelAttribute Product product, BindingResult result) {
		List<CategoryBean> categories=service.fetchAllCategories();
		model.addAttribute("categories", categories);
		String response=service.editProduct(product);
		LOGGER.error("edit product {} ", product);
		LOGGER.error("edit product response {} ", response);
		List<Product> products=service.fetchProductByCategoryName(product.getCategoryName());
		model.addAttribute("products", products);
		model.addAttribute("categoryName", product.getCategoryName());
		return "adminCategories";
	}

	@RequestMapping("/admin_logout")
	public String logout() {
		return "home";
	}
}
