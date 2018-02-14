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
import com.spaneos.dao.ProductDao;
import com.spaneos.dao.UserDao;
import com.spaneos.model.Cart;
import com.spaneos.model.Product;
import com.spaneos.model.User;

/**
 * @author spaneos
 *
 */
@Service
public class UserService {
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

	public String register(User user) {
		userDao.save(user);
		return "SUCCESS";
	}

	public int getProductPrice(String productName) {
		int price=0;
		for (Product product : productDao.findByProductName(productName)) {
			price=product.getPrice();
		}
		return price;
	}
	
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
	
	public List<Product> fetchAllProducts() {
		return productDao.findAll();
	}
	
	public String deleteCartProduct(String productName) {
		cartDao.deleteByProductName(productName);
		int quantity=0;
		List<Cart> carts=cartDao.findByProductName(productName);
		for (Cart cart : carts) {
			LOGGER.error("Carts {} ", cart);
		}
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
		return "SUCCESS";
	}
	
	public List<Cart> fetchCartProducts(){
		return cartDao.findAll();
	}

}
