/**
 * 
 */
package com.spaneos.service;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spaneos.dao.CartDao;
import com.spaneos.dao.ProductDao;
import com.spaneos.dao.UserDao;
import com.spaneos.model.Cart;
import com.spaneos.model.Product;
import com.spaneos.model.User;

/**
 * @author balaji
 *
 */
@Service
public class UserService {
	/**
	 * Getting logger for this class.
	 */
	private static final Logger LOGGER= LoggerFactory.getLogger(UserService.class.getName());

	@Autowired
	private UserDao userDao; 

	@Autowired
	private ProductDao productDao;
	
	@Autowired
	private CartDao cartDao;

	public UserService() {
		LOGGER.error("User Service");
	}
	/**
	 * This method is used to store the user details on the database.
	 * @param user
	 * @return
	 */
	public String register(User user) {
		userDao.save(user);
		return "SUCCESS";
	}
	/**
	 * This method is used to authenticate the user.
	 * @param email
	 * @return
	 */
	public List<User> authenticateUser(String email) {
		return userDao.findByEmail(email);
	}
	/**
	 * This method is used to get the product price to calculate the total price based on the quantity.
	 * @param productName
	 * @return
	 */
	public int getProductPrice(String productName) {
		int price=0;
		for (Product product : productDao.findByProductName(productName)) {
			price=product.getPrice();
		}
		return price;
	}
	/**
	 * This method is used to store the product details on the Cart table.
	 * @param carts
	 * @return
	 */
	public String addToCart(List<Cart> carts) {
		for (Cart cart : carts) {
			cartDao.save(cart);
			int stock=0;
			for (Product product:productDao.findByProductName(cart.getProductName())){
				stock=product.getStock();
			}
			productDao.updateStock(stock-(cart.getQuantity()), cart.getProductName());
		}
		return "SUCCESS";
	}
	/**
	 * This method is used to get the product details in the cart.
	 * @param name
	 * @return
	 */
	public List<Cart> fetchCartProductByName(String name){
		return cartDao.findByProductName(name);
	}
	/**
	 * This method is used to retrieve all the products in the product table. 
	 * @return
	 */
	public List<Product> fetchAllProducts() {
		return productDao.findAll();
	}
	/**
	 * This method is used to delete the products in the cart. 
	 * and increase the stock.
	 * @param productName
	 * @return
	 */
	public String deleteCartProduct(String productName) {
		LOGGER.error("Product name in delete cart {} ", productName);
		int quantity=0;
		for (Cart cart : cartDao.findByProductName(productName)) {
			LOGGER.error("Cart product {} ",cart);
			quantity=cart.getQuantity();
			LOGGER.error("Quantity in delete cart first {} ", quantity);
		}
		int stock=0;
		for (Product product:productDao.findByProductName(productName)){
			stock=product.getStock();
		}
		productDao.updateStock((stock+quantity), productName);
		LOGGER.error("Quantity in delete cart last {} ", quantity);
		cartDao.deleteByProductName(productName);
		return "SUCCESS";
	}
	/**
	 * This method is used to get the product details in the cart.
	 * To proceed to check out.
	 * @param email
	 * @return
	 */
	public List<Cart> fetchCartProductsByStatus(String email){
		return cartDao.findByStatusAndUserEmail("In cart", email);
	}
	/**
	 * This method is to update the status and date of the purchase of the product in the cart.
	 * @param productName
	 * @return
	 */
	public String payBill(String productName) {
		cartDao.updateStatus("Purchased", new Date().toString(), productName);
		return "SUCCESS";
	}

}
