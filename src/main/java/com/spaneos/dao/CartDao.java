/**
 * 
 */
package com.spaneos.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.spaneos.model.Cart;
import java.lang.String;

/**
 * @author balaji
 *
 */
@Transactional(readOnly = true)
public interface CartDao extends CrudRepository<Cart, Long> {
	/**
	 * This method is used to retrieve all the product details in the Cart table.
	 */
	List<Cart> findAll();
	/**
	 * This method is used to retrieve the product details based on the product name. 
	 * @param productName
	 * @return
	 */
	List<Cart> findByProductName(String productName);
	/**
	 * This method is used to retrieve the product details based on the status and user email.
	 * To hide the purchased products in the cart.
	 * @param status
	 * @param email
	 * @return
	 */
	List<Cart> findByStatusAndUserEmail(String status, String email);
	/**
	 * This method is used to retrieve the product details based on user email.
	 * To get the shopping history of the user.
	 * @param userEmail
	 * @return
	 */
	List<Cart> findByUserEmail(String userEmail);
	/**
	 * This method is used to delete the product from Cart table bsed on the product name.
	 * @param productName
	 * @return
	 */
	@Transactional
	long deleteByProductName(String productName);
	/**
	 * Custom query to update the status and date of the products in the Cart once it is purchased.
	 * @param stock
	 * @param date
	 * @param productName
	 * @return
	 */
	@Modifying
	@Transactional(readOnly = false) 
	@Query(value = "UPDATE Cart c SET c.status = :status, c.date = :date WHERE c.productName = :productName")
	Integer updateStatus(@Param("status") String stock, @Param("date") String date, @Param("productName") String productName);
	
}
