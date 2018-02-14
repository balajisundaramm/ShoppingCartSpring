/**
 * 
 */
package com.spaneos.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import com.spaneos.model.Cart;
import java.lang.String;

/**
 * @author spaneos
 *
 */
public interface CartDao extends CrudRepository<Cart, Long> {
	List<Cart> findAll();
	List<Cart> findByProductName(String productName);
	@Transactional
	long deleteByProductName(String productName);
}
