/**
 * 
 */
package com.spaneos.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spaneos.dao.CartDao;
import com.spaneos.dao.CategoryDao;
import com.spaneos.dao.ProductDao;
import com.spaneos.dao.UserDao;
import com.spaneos.model.Cart;
import com.spaneos.model.CategoryBean;
import com.spaneos.model.Product;
import com.spaneos.model.User;

/**
 * @author balaji
 *
 */
@Service
public class AdminService {
	/**
	 * Getting logger for this class.
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(AdminService.class.getName());

	@Autowired
	private CategoryDao categoryDao;
	@Autowired
	private UserDao userDao;
	@Autowired
	private ProductDao productDao;
	@Autowired
	private CartDao cartDao;

	public AdminService() {
		LOGGER.error("Admin Service");
	}
	/**
	 * This method is used to retrieve the category details form the database and 
	 * forward the data to controller.
	 * @return
	 */
	public List<CategoryBean> fetchAllCategories() {
		return categoryDao.findAll();	 
	}
	/**
	 * This method is used to store the category details on the database.
	 * @param bean
	 * @return
	 */
	public String addCategory(CategoryBean bean) {
		categoryDao.save(bean);
		return "SUCCESS";
	}
	/**
	 * This method is used to retrieve the user details form the database and 
	 * forward the data to controller.
	 * @return
	 */
	public List<User> fetchUsers() {
		return userDao.findAll();	 
	}
	/**
	 * This method is used to store the product details on the database.
	 * @param product
	 * @return
	 */
	public String addProduct(Product product) {
		productDao.save(product);
		return "SUCCESS";
	}
	/**
	 * This method is used to retrieve the product details based on the category name 
	 * form the database and forward the data to controller.
	 * @param name
	 * @return
	 */
	public List<Product> fetchProductByCategoryName(String name) {
		return productDao.findAllByCategoryNameIgnoreCaseContaining(name); 
	}
	/**
	 * This method is used to retrieve the product details based on the category name and product name 
	 * form the database and forward the data to controller.
	 * @param productName
	 * @param categoryName
	 * @return
	 */
	public Product fetchSpecificProduct(String productName, String categoryName) {
		return productDao.findAllByProductNameAndCategoryName(productName, categoryName);
	}
	/**
	 * This method is used to retrieve the user details from the database.
	 * @param id
	 * @return
	 */
	public User fetchSpecificUser(long id) {
		return userDao.findById(id);
		 
	}
	/**
	 * This method is used to retrieve the category details.
	 * @param name
	 * @return
	 */
	public CategoryBean fetchSpecificCategory(String name) {
		return categoryDao.findAllByNameIgnoreCaseContaining(name);
	}
	/**
	 * This method is used to delete the user details from the database.
	 * @param id
	 * @return
	 */
	public String deleteUser(long id) {
		userDao.delete(id);
		return "SUCCESS";
	}
	/**
	 * This method is used to delete the product details from the database.
	 * @param id
	 * @return
	 */
	public String deleteProduct(long id) {
		productDao.delete(id);
		return "SUCCESS";
	}
	/**
	 * This method is used to delete the category and the products in the category fromthe database. 
	 * @param categoryName
	 * @return
	 */
	public String deleteCategory(String categoryName) {
		productDao.deleteByCategoryName(categoryName);
		categoryDao.deleteByName(categoryName);
		return "SUCCESS";
	}
	/**
	 * This method is used to update the user details.
	 * @param user
	 * @return
	 */
	public String editUser(User user) {
		userDao.setUserName(user.getMobile(), user.getAddress(), user.getGender(), user.getDob(), user.getEmail());
		return "SUCCESS";
	}
	/**
	 * This method is used to update the product details.
	 * @param product
	 * @return
	 */
	public String editProduct(Product product) {
		productDao.updateProduct(product.getCategoryName(), product.getDescription(), product.getPrice(), product.getStock(), product.getProductName());
		return "SUCCESS";
	}
	/**
	 * This method is used to update the category details.
	 * @param category
	 * @return
	 */
	public String editCategory(CategoryBean category) {
		categoryDao.updateCategory(category.getDescription(), category.getName());
		return "SUCCESS";
	}
	/**
	 * This method is used to get the shopping history details from Cart table.
	 * @param userEmail
	 * @return
	 */
	public List<Cart> getShoppingHistory(String userEmail){
		return cartDao.findByUserEmail(userEmail);
	}

}
