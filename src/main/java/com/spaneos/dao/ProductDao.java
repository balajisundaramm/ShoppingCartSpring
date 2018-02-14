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

import com.spaneos.model.Product;
import java.lang.String;

/**
 * @author spaneos
 *
 */
@Transactional(readOnly = true)
public interface ProductDao extends CrudRepository<Product, Long> {
	List<Product> findAll();
	List<Product> findAllByCategoryNameIgnoreCaseContaining(String name);
	Product findAllByProductNameAndCategoryName(String productName,String categoryName);
	long deleteByCategoryName(String name); 
	List<Product> findByProductName(String productname);
	@Modifying
	@Transactional(readOnly = false) 
	@Query(value = "UPDATE Product p SET p.categoryName = :categoryName, p.description = :description, p.price = :price, p.stock = :stock WHERE p.productName = :productName")
	Integer updateProduct(@Param("categoryName") String categoryName, @Param("description") String description, @Param("price") int price, @Param("stock") int stock, @Param("productName") String productName);
	@Modifying
	@Transactional(readOnly = false) 
	@Query(value = "UPDATE Product p SET p.stock = :stock WHERE p.productName = :productName")
	Integer updateStock(@Param("stock") int stock, @Param("productName") String productName);
	
} 
