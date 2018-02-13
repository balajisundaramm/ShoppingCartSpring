/**
 * 
 */
package com.spaneos.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spaneos.dao.CategoryDao;
import com.spaneos.dao.ProductDao;
import com.spaneos.dao.UserDao;
import com.spaneos.model.CategoryBean;
import com.spaneos.model.Product;
import com.spaneos.model.User;


/**
 * @author spaneos
 *
 */
@Service
public class AdminService {
    private static final Logger LOGGER= LoggerFactory.getLogger(AdminService.class.getName());
    
    @Autowired
	private CategoryDao categoryDao;
    @Autowired
    private UserDao userDao;
    @Autowired
    private ProductDao productDao; 

	public AdminService() {
		LOGGER.error("Admin Service");
	}
	
	public List<CategoryBean> fetchAllCategories(){
		List<CategoryBean> listOfCategories=categoryDao.findAll();
		return listOfCategories;
	}
	
	public String addCategory(CategoryBean bean) {
		categoryDao.save(bean);
		return "SUCCESS";
	}
	
	public List<User> fetchUsers(){
		List<User> users=userDao.findAll();
		return users;
	}
	
	public String addProduct(Product product) {
		productDao.save(product);
		return "SUCCESS";
	}
	public List<Product> fetchProductByCategoryName(String name){
		List<Product> products=productDao.findAllByCategoryNameIgnoreCaseContaining(name);
		return products;
	}
	
	public Product fetchSpecificProduct(String productName, String categoryName) {
		Product product=productDao.findAllByProductNameAndCategoryName(productName, categoryName);
		return product;
	}
	
	public User fetchSpecificUser(long id) {
		User user= userDao.findById(id);
		return user;
	}
	
	public CategoryBean fetchSpecificCategory(String name) {
		CategoryBean category= categoryDao.findAllByNameIgnoreCaseContaining(name);
		return category;
	}
	
	public String deleteUser(long id) {
		userDao.delete(id);
		return "SUCCESS";
	}
	
	public String deleteProduct(long id) {
		productDao.delete(id);
		return "SUCCESS";
	}
	
	public String deleteCategory(long id) {
		userDao.delete(id);
		return "SUCCESS";
	}
	
}
