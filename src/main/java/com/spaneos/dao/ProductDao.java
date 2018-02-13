/**
 * 
 */
package com.spaneos.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.spaneos.model.Product;

/**
 * @author spaneos
 *
 */

public interface ProductDao extends CrudRepository<Product, Long> {
	List<Product> findAll();
	List<Product> findAllByCategoryNameIgnoreCaseContaining(String name);
	Product findAllByProductNameAndCategoryName(String productName,String categoryName);
} 
