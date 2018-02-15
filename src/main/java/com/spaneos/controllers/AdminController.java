package com.spaneos.controllers;

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
/**
 * 
 * @author balaji
 *
 */
@Controller
public class AdminController {
	/**
	 * Getting logger for this class.
	 */
	private static final Logger LOGGER= LoggerFactory.getLogger(AdminController.class.getName());

	@Autowired
	private AdminService service; 

	/**
	 * Controller to authenticate the admin
	 * @param model
	 * @param request
	 * @return String
	 * 
	 */
	@PostMapping("/authenticate_admin")
	public String authenticateAdmin(Model model, HttpServletRequest request) {
		HttpSession session = request.getSession();
		session.setAttribute("admin", "admin");
		List<CategoryBean> categories=service.fetchAllCategories();
		model.addAttribute("categories", categories);
		return "adminHome";
	}
	/**
	 * Controller for admin home page.
	 * @param model
	 * @param request
	 * @return
	 */
	@GetMapping("/admin_home")
	public String showAdminHome(Model model, HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		if(session==null || session.getAttribute("admin")==null) {
			model.addAttribute("message", "Your account has been logged out. Login first.");
			return "home";
		}
		else {
			List<CategoryBean> categories=service.fetchAllCategories();
			model.addAttribute("categories", categories);
			return "adminHome";
		}
	}

	/**
	 * Controller to open add category form.
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping("/add_category_form")
	public String showCategoryForm(Model model, HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		if(session==null || session.getAttribute("admin")==null) {
			model.addAttribute("message", "Your account has been logged out. Login first.");
			return "home";
		}
		else {
			List<CategoryBean> categories=service.fetchAllCategories();
			model.addAttribute("categories", categories);
			return "categoryForm";
		}
	}
	/**
	 * Controller to add the category successfully to the database. 
	 * @param category
	 * @param result
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping("/add_category")
	public String addCategory(@ModelAttribute CategoryBean category, BindingResult result, Model model, 
			HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		if(session==null || session.getAttribute("admin")==null) {
			model.addAttribute("message", "Your account has been logged out. Login first.");
			return "home";
		}
		else {
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
	}
	/**
	 * Controller to open the products in the selected category
	 * @param model
	 * @param name
	 * @param request
	 * @return
	 */
	@GetMapping("/category/{name}")
	public String showProducts(Model model, @PathVariable("name") String name, HttpServletRequest request ) {
		HttpSession session = request.getSession(false);
		if(session==null || session.getAttribute("admin")==null) {
			model.addAttribute("message", "Your account has been logged out. Login first.");
			return "home";
		}
		else {
			List<CategoryBean> categories=service.fetchAllCategories();
			model.addAttribute("categories", categories);
			List<Product> products=service.fetchProductByCategoryName(name);
			model.addAttribute("products", products);
			model.addAttribute("categoryName", name);
			return "adminCategories";
		}
	} 
	/**
	 * Controller to open edit category form
	 * @param model
	 * @param name
	 * @param request
	 * @return
	 */
	@GetMapping("/edit_category/{name}")
	public String showEditCategoryForm(Model model, @PathVariable("name") String name, HttpServletRequest request ) {
		HttpSession session = request.getSession(false);
		if(session==null || session.getAttribute("admin")==null) {
			model.addAttribute("message", "Your account has been logged out. Login first.");
			return "home";
		}
		else {
			List<CategoryBean> categories=service.fetchAllCategories();
			model.addAttribute("categories", categories);
			CategoryBean category=service.fetchSpecificCategory(name);
			model.addAttribute("category", category);
			return "editCategoryForm";
		}
	} 
	/**
	 * Controller to view list of users.
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping("/user_list")
	public String showUserList(Model model, HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		if(session==null || session.getAttribute("admin")==null) {
			model.addAttribute("message", "Your account has been logged out. Login first.");
			return "home";
		}
		else {
			List<CategoryBean> categories=service.fetchAllCategories();
			model.addAttribute("categories", categories);
			List<User> users=service.fetchUsers();
			model.addAttribute("users", users);
			return "userList";
		}
	}
	/**
	 * Controller to open the form to edit the product.
	 * @param model
	 * @param categoryName
	 * @param productName
	 * @param request
	 * @return
	 */
	@GetMapping("/product/{categoryName}/{productName}")
	public String editProductForm(Model model, @PathVariable("categoryName") String categoryName, 
			@PathVariable("productName") String productName, HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		if(session==null || session.getAttribute("admin")==null) {
			model.addAttribute("message", "Your account has been logged out. Login first.");
			return "home";
		}
		else {
			List<CategoryBean> categories=service.fetchAllCategories();
			model.addAttribute("categories", categories);
			Product product=service.fetchSpecificProduct(productName, categoryName);
			model.addAttribute("product", product);
			return "editProductForm";
		}
	}
	/**
	 * Controller to open the form to edit user details.
	 * @param model
	 * @param id
	 * @param request
	 * @return
	 */
	@RequestMapping("/user/{id}")
	public String editUserForm(Model model, @PathVariable("id") long id, HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		if(session==null || session.getAttribute("admin")==null) {
			model.addAttribute("message", "Your account has been logged out. Login first.");
			return "home";
		}
		else {
			List<CategoryBean> categories=service.fetchAllCategories();
			model.addAttribute("categories", categories);
			User user=service.fetchSpecificUser(id);
			model.addAttribute("user", user);
			return "editUserForm";
		}
	}
	/**
	 * Controller to add products successfully to the database.
	 * @param product
	 * @param result
	 * @param model
	 * @param request
	 * @return
	 */
	@PostMapping("/add_product")
	public String addProduct(@ModelAttribute Product product, BindingResult result, Model model,
			HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		if(session==null || session.getAttribute("admin")==null) {
			model.addAttribute("message", "Your account has been logged out. Login first.");
			return "home";
		}
		else {
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

	}
	/**
	 * Controller to open the form to add products. 
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping("/add_product_form")
	public String openAddProductForm(Model model, HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		if(session==null || session.getAttribute("admin")==null) {
			model.addAttribute("message", "Your account has been logged out. Login first.");
			return "home";
		}
		else {
			List<CategoryBean> categories=service.fetchAllCategories();
			model.addAttribute("categories", categories);
			return "addProductForm";
		}
	}
	/**
	 * Controller to delete the user.
	 * @param model
	 * @param id
	 * @param request
	 * @return
	 */
	@GetMapping("/delete_user/{id}")
	public String deleteUser(Model model, @PathVariable("id") long id, HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		if(session==null || session.getAttribute("admin")==null) {
			model.addAttribute("message", "Your account has been logged out. Login first.");
			return "home";
		}
		else {
			List<CategoryBean> categories=service.fetchAllCategories();
			model.addAttribute("categories", categories);
			service.deleteUser(id);
			List<User> users=service.fetchUsers();
			model.addAttribute("users", users);
			return "userList";
		}
	}
	/**
	 * Controller to delete the product.
	 * @param model
	 * @param id
	 * @param name
	 * @param request
	 * @return
	 */
	@GetMapping("/delete_product/{id}/{name}")
	public String deleteProduct(Model model, @PathVariable("id") long id, @PathVariable("name") String name,
			HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		if(session==null || session.getAttribute("admin")==null) {
			model.addAttribute("message", "Your account has been logged out. Login first.");
			return "home";
		}
		else {
			List<CategoryBean> categories=service.fetchAllCategories();
			model.addAttribute("categories", categories);
			String response=service.deleteProduct(id);
			LOGGER.error("error response "+response);
			List<Product> products=service.fetchProductByCategoryName(name);
			model.addAttribute("products", products);
			model.addAttribute("categoryName", name);
			return "adminCategories";
		}
	}
	/**
	 * Controller to delete the category.
	 * @param model
	 * @param name
	 * @param request
	 * @return
	 */
	@GetMapping("/delete_category/{name}")
	public String deleteCategory(Model model, @PathVariable("name") String name, HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		if(session==null || session.getAttribute("admin")==null) {
			model.addAttribute("message", "Your account has been logged out. Login first.");
			return "home";
		}
		else {
			String response=service.deleteCategory(name); 
			LOGGER.error("error response in delete category "+response);
			List<CategoryBean> categories=service.fetchAllCategories();
			model.addAttribute("categories", categories);
			return "adminHome";
		}
	}
	/**
	 * Controller to update the user details in the database.
	 * @param model
	 * @param user
	 * @param result
	 * @param request
	 * @return
	 */
	@PostMapping("/edit_user")
	public String editUser(Model model, @ModelAttribute User user, BindingResult result, HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		if(session==null || session.getAttribute("admin")==null) {
			model.addAttribute("message", "Your account has been logged out. Login first.");
			return "home";
		}
		else {
			List<CategoryBean> categories=service.fetchAllCategories();
			model.addAttribute("categories", categories);
			service.editUser(user);
			List<User> users=service.fetchUsers();
			model.addAttribute("users", users);
			return "userList";
		}
	}
	/**
	 * Controller to update the category details in the database.
	 * @param model
	 * @param category
	 * @param result
	 * @param request
	 * @return
	 */
	@PostMapping("/edit_category")
	public String editCategory(Model model, @ModelAttribute CategoryBean category, BindingResult result, HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		if(session==null || session.getAttribute("admin")==null) {
			model.addAttribute("message", "Your account has been logged out. Login first.");
			return "home";
		}
		else {
			service.editCategory(category);
			List<CategoryBean> categories=service.fetchAllCategories();
			model.addAttribute("categories", categories);
			return "adminHome";
		}
	}
	/**
	 * Controller to update the product details.
	 * @param model
	 * @param product
	 * @param result
	 * @param request
	 * @return
	 */
	@PostMapping("/edit_product")
	public String editProduct(Model model, @ModelAttribute Product product, BindingResult result, HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		if(session==null || session.getAttribute("admin")==null) {
			model.addAttribute("message", "Your account has been logged out. Login first.");
			return "home";
		}
		else {
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
	}
	/**
	 * Controller to view the shopping history for users.
	 * @param model
	 * @param email
	 * @param request
	 * @return
	 */
	@GetMapping("/history/{email:.+}")
	public String shoppingHistory(Model model, @PathVariable("email") String email, HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		if(session==null || session.getAttribute("admin")==null) {
			model.addAttribute("message", "Your account has been logged out. Login first.");
			return "home";
		}
		else {
			LOGGER.error("Email {} ", email); 
			List<CategoryBean> categories=service.fetchAllCategories();
			model.addAttribute("categories", categories);
			List<Cart> carts = service.getShoppingHistory(email);
			LOGGER.error("Shopping History {} ",carts);
			model.addAttribute("history", carts);
			return "shoppingHistory";
		}
	}
	/**
	 * Controller to logout.
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping("/admin_logout")
	public String logout(Model model, HttpServletRequest request) {
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
