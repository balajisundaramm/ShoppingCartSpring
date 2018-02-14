/**
 * 
 */
package com.spaneos.controllers;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

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

import com.spaneos.model.Cart;
import com.spaneos.model.CategoryBean;
import com.spaneos.model.Product;
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
    
    @Autowired
    private AdminService adminService;

	public UserController() {
		LOGGER.error("User Controller");
	}
	
	@RequestMapping("/user_signup")
	public String showSignUpForm() {
		return "userSignUpForm";
	}
	
	@RequestMapping("/user_login")
	public String showLoginForm(Model model) {
		return "userLoginForm";
	}
	
	@GetMapping("/user_home")
	public String showUserHome(Model model) {
		List<CategoryBean> categories=adminService.fetchAllCategories();
		model.addAttribute("categories", categories);
		return "userHome";
	}
	
	@PostMapping("/authenticate_user")
	public String authenticateUser(Model model) {
		List<CategoryBean> categories=adminService.fetchAllCategories();
		model.addAttribute("categories", categories);
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
	
	@GetMapping("/user_category/{name}")
	public String showProducts(Model model, @PathVariable("name") String name ) {
		List<CategoryBean> categories=adminService.fetchAllCategories();
		model.addAttribute("categories", categories);
		List<Product> products=adminService.fetchProductByCategoryName(name);
		model.addAttribute("products", products);
		model.addAttribute("categoryName", name);
		return "userCategories";
	}
	
	@RequestMapping("/product_list")
	public String showAllProducts(Model model) {
		List<CategoryBean> categories=adminService.fetchAllCategories();
		model.addAttribute("categories", categories);
		List<Product> products=userService.fetchAllProducts();
		model.addAttribute("products", products);
		return "allProducts";
	} 
	
	@RequestMapping("/view_cart")
	public String showCart(Model model) {
		List<CategoryBean> categories=adminService.fetchAllCategories();
		model.addAttribute("categories", categories);
		List<Cart> products=userService.fetchCartProducts();
		model.addAttribute("products", products);
		return "cartList";
	}
	
	@RequestMapping("/delete_product/{name}")
	public String deleteFromCart(@PathVariable("name") String productName, Model model) {
		List<CategoryBean> categories=adminService.fetchAllCategories();
		model.addAttribute("categories", categories);
		userService.deleteCartProduct(productName);
		List<Cart> products=userService.fetchCartProducts();
		model.addAttribute("products", products);
		return "cartList";
	}
	
	@PostMapping("/add_checkout")
	public String addToCheckout(Model model) {
		List<CategoryBean> categories=adminService.fetchAllCategories();
		model.addAttribute("categories", categories);
		List<Cart> products=userService.fetchCartProducts();
		model.addAttribute("products", products);
		return "checkoutList";
	}
	
	@PostMapping("/add_cart")
	public String addToCart(HttpServletRequest request, Model model) {
		Enumeration<String> names=request.getParameterNames();
		List<String> params=new ArrayList<String>();
		Cart bean1=null, bean2=null, bean3=null;
		while (names.hasMoreElements()) {
			String param = names.nextElement();
			params.add(param);
			LOGGER.info("Param "+param);
		}
		for (String string : params) {
			LOGGER.info("ALl parameters "+string);
		}
		LOGGER.info("Length is "+params.size());

		if(params.size()>=2) {
			String productName=request.getParameter(params.get(0));
			int quantity=Integer.parseInt(request.getParameter(params.get(1)));
			int price=userService.getProductPrice(productName);
			bean1=new Cart(productName, quantity,(price*quantity));

		}
		if(params.size()>=4) {
			String productName=request.getParameter(params.get(2));
			int quantity=Integer.parseInt(request.getParameter(params.get(3)));
			int price=userService.getProductPrice(productName);
			bean2=new Cart(productName, quantity,(price*quantity));

		}
		if(params.size()>=6) {
			String productName=request.getParameter(params.get(4));
			int quantity=Integer.parseInt(request.getParameter(params.get(5)));
			int price=userService.getProductPrice(productName);
			bean3=new Cart(productName, quantity,(price*quantity));
		}
		
		List<Cart> beans=new ArrayList<Cart>();
		if(bean1!=null) {
			beans.add(bean1);

		}
		if(bean2!=null) {
			beans.add(bean2);

		}
		if(bean3!=null) {
			beans.add(bean3);
		}
		userService.addToCart(beans);
		List<CategoryBean> categories=adminService.fetchAllCategories();
		model.addAttribute("categories", categories);
		List<Cart> products=userService.fetchCartProducts();
		model.addAttribute("products", products);
		return "cartList";
	}
	
	
}
