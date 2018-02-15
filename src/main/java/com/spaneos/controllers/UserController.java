/**
 * 
 */
package com.spaneos.controllers;

import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

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
 * @author balaji
 *
 */
@Controller
public class UserController {
	/**
	 * Getting logger for this class.
	 */
	private static final Logger LOGGER= LoggerFactory.getLogger(UserController.class.getName());

	@Autowired
	private UserService userService;

	@Autowired
	private AdminService adminService;
	/**
	 * Static variable to store the products information for checkout process.
	 */
	private static List<Cart> cartProducts=new ArrayList<>();

	/**
	 * Controller to open user sign up form
	 * @return
	 */
	@RequestMapping("/user_signup")
	public String showSignUpForm() {
		return "userSignUpForm";
	}
	/**
	 * Controller to open user login form
	 * @param model
	 * @return
	 */
	@RequestMapping("/user_login")
	public String showLoginForm(Model model) {
		return "userLoginForm";
	}
	/**
	 * Controller to open user home page after successful login
	 * @param model
	 * @param request
	 * @return
	 */
	@GetMapping("/user_home")
	public String showUserHome(Model model, HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		if(session==null || session.getAttribute("user")==null) {
			model.addAttribute("message", "Your account has been logged out. Login first.");
			return "home";

		}
		else {
			List<CategoryBean> categories=adminService.fetchAllCategories();
			model.addAttribute("categories", categories);
			return "userHome";
		}
	}
	/**
	 * Controller to authenticate the user.
	 * @param model
	 * @param email
	 * @param password
	 * @param request
	 * @return
	 */
	@PostMapping("/authenticate_user")
	public String authenticateUser(Model model,@ModelAttribute("email") String email, 
			@ModelAttribute("password") String password, HttpServletRequest request) {
		LOGGER.error("Email {} ",email);
		LOGGER.error("pass {} ",password);
		for (User user : userService.authenticateUser(email)) {
			if(user.getEmail().equals(email) && user.getPassword().equals(password)) {
				HttpSession session = request.getSession();
				session.setAttribute("user", email);
				List<CategoryBean> categories=adminService.fetchAllCategories();
				model.addAttribute("categories", categories);
				return "userHome";
			}
		}
		model.addAttribute("message", "Invalid Login credentials.");
		return "userLoginForm";

	}
	/**
	 * Controller to register the user details in the database.
	 * @param user
	 * @param result
	 * @param model
	 * @return
	 */
	@PostMapping("/register")
	public String register(@ModelAttribute User user, BindingResult result, Model model) {

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
	/**
	 * Controller to view the list of products based on the selected category.
	 * @param model
	 * @param name
	 * @param request
	 * @return
	 */
	@GetMapping("/user_category/{name}")
	public String showProducts(Model model, @PathVariable("name") String name, HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		if(session==null || session.getAttribute("user")==null) {
			model.addAttribute("message", "Your account has been logged out. Login first.");
			return "home";
		}
		else {
			List<CategoryBean> categories=adminService.fetchAllCategories();
			model.addAttribute("categories", categories);
			List<Product> products=adminService.fetchProductByCategoryName(name);
			model.addAttribute("products", products);
			model.addAttribute("categoryName", name);
			return "userCategories";
		}
	}
	/**
	 * Controller to view all products.
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping("/product_list")
	public String showAllProducts(Model model, HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		if(session==null || session.getAttribute("user")==null) {
			model.addAttribute("message", "Your account has been logged out. Login first.");
			return "home";
		}
		else {
			List<CategoryBean> categories=adminService.fetchAllCategories();
			model.addAttribute("categories", categories);
			List<Product> products=userService.fetchAllProducts();
			model.addAttribute("products", products);
			return "allProducts";
		}
	} 
	/**
	 * Controller to view the products in the cart based on the user email.
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping("/view_cart")
	public String showCart(Model model, HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		if(session==null || session.getAttribute("user")==null) {
			model.addAttribute("message", "Your account has been logged out. Login first.");
			return "home";
		}
		else {
			String email=(String)session.getAttribute("user");
			List<CategoryBean> categories=adminService.fetchAllCategories();
			model.addAttribute("categories", categories);
			List<Cart> products=userService.fetchCartProductsByStatus(email);
			model.addAttribute("products", products);
			return "cartList";
		}
	}
	/**
	 * Controller to delete the product from cart.
	 * @param productName
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping("/delete_product/{name}")
	public String deleteFromCart(@PathVariable("name") String productName, Model model, HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		if(session==null || session.getAttribute("user")==null) {
			model.addAttribute("message", "Your account has been logged out. Login first.");
			return "home";
		}
		else {
			String email=(String) session.getAttribute("user");
			List<CategoryBean> categories=adminService.fetchAllCategories();
			model.addAttribute("categories", categories);
			userService.deleteCartProduct(productName);
			List<Cart> products=userService.fetchCartProductsByStatus(email);
			model.addAttribute("products", products);
			return "cartList";
		}
	}
	/**
	 * Controller to add the products form cart to checkout.
	 * @param request
	 * @param model
	 * @return
	 */
	@PostMapping("/add_checkout")
	public String addToCheckout(HttpServletRequest request, Model model) {
		HttpSession session = request.getSession(false);
		if(session==null || session.getAttribute("user")==null) {
			model.addAttribute("message", "Your account has been logged out. Login first.");
			return "home";
		}
		else {
			Enumeration<String> names=request.getParameterNames();
			cartProducts=new ArrayList<Cart>();
			while (names.hasMoreElements()) {
				String param = names.nextElement();
				LOGGER.error("Param names {} ", param);
				List<Cart> carts=userService.fetchCartProductByName(request.getParameter(param));
				for (Cart cart : carts) {
					cartProducts.add(cart);
				}
			}
			List<CategoryBean> categories=adminService.fetchAllCategories();
			model.addAttribute("categories", categories);
			LOGGER.error("Cart Products {} ", cartProducts);
			model.addAttribute("products", cartProducts);
			return "checkoutList";
		}
	}
	/**
	 * Controller to delete the products from check out.
	 * @param model
	 * @param productName
	 * @param request
	 * @return
	 */
	@RequestMapping("/delete_checkout/{name}")
	public String deleteCheckout(Model model, @PathVariable("name") String productName, HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		if(session==null || session.getAttribute("user")==null) {
			model.addAttribute("message", "Your account has been logged out. Login first.");
			return "home";
		}
		else {
			List<CategoryBean> categories=adminService.fetchAllCategories();
			model.addAttribute("categories", categories);
			List<Cart> carts=userService.fetchCartProductByName(productName);
			cartProducts.removeAll(carts);
			model.addAttribute("products", cartProducts);
			return "checkoutList";
		}
	}
	/**
	 * Controller to proceed to pay the bill.
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping("/payment")
	public String billPay(Model model, HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		if(session==null || session.getAttribute("user")==null) {
			model.addAttribute("message", "Your account has been logged out. Login first.");
			return "home";
		}
		else {
			List<CategoryBean> categories=adminService.fetchAllCategories();
			model.addAttribute("categories", categories);
			for (Cart cart : cartProducts) {
				userService.payBill(cart.getProductName());
			}
			cartProducts=new ArrayList<Cart>();
			return "userHome";
		}
	}
	/**
	 * Controller to add the product to cart.
	 * @param request
	 * @param model
	 * @return
	 */
	@PostMapping("/add_cart")
	public String addToCart(HttpServletRequest request, Model model) {
		HttpSession session = request.getSession(false);
		if(session==null || session.getAttribute("user")==null) {
			model.addAttribute("message", "Your account has been logged out. Login first.");
			return "home";
		}
		else {
			String email=(String) session.getAttribute("user");
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
				bean1.setDate(new Date().toString());
				bean1.setStatus("In cart");
				bean1.setUserEmail(email);

			}
			if(params.size()>=4) {
				String productName=request.getParameter(params.get(2));
				int quantity=Integer.parseInt(request.getParameter(params.get(3)));
				int price=userService.getProductPrice(productName);
				bean2=new Cart(productName, quantity,(price*quantity));
				bean2.setDate(new Date().toString());
				bean2.setStatus("In cart");
				bean2.setUserEmail(email);


			}
			if(params.size()>=6) {
				String productName=request.getParameter(params.get(4));
				int quantity=Integer.parseInt(request.getParameter(params.get(5)));
				int price=userService.getProductPrice(productName);
				bean3=new Cart(productName, quantity,(price*quantity));
				bean3.setDate(new Date().toString());
				bean3.setStatus("In cart");
				bean3.setUserEmail(email);

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
			String userEmail=(String) session.getAttribute("user");
			userService.addToCart(beans);
			List<CategoryBean> categories=adminService.fetchAllCategories();
			model.addAttribute("categories", categories);
			List<Cart> products=userService.fetchCartProductsByStatus(userEmail);
			model.addAttribute("products", products);
			return "cartList";
		}
	}
	/**
	 * Controller to logout.
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("/logout")
	public String logout(HttpServletRequest request, Model model ) {
		HttpSession session = request.getSession(false);
		if(session!=null)
		{
			session.removeAttribute("admin");
			session.invalidate();
		}
		model.addAttribute("message","You have logged out successfully!!!");
		return "home";
	}


}
