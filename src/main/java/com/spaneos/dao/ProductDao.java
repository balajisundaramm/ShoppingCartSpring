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
	/**
	 * This method is used to retrieve the all the products from the Product table.
	 */
	List<Product> findAll();
	/**
	 * This method is used to retrieve the product details based on the category name.
	 * @param name
	 * @return
	 */
	List<Product> findAllByCategoryNameIgnoreCaseContaining(String name);
	/**
	 * This method is used to retrieve the product details based on product name and category name.
	 * @param productName
	 * @param categoryName
	 * @return
	 */
	Product findAllByProductNameAndCategoryName(String productName,String categoryName);
	/**
	 * This method is used to delete the products based on the category name.
	 * @param name
	 * @return
	 */
	long deleteByCategoryName(String name); 
	/**
	 * This method is used to retrieve the product details based on the product name.
	 * @param productname
	 * @return
	 */
	List<Product> findByProductName(String productname);
	/**
	 * Custom query to update the product details.
	 * @param categoryName
	 * @param description
	 * @param price
	 * @param stock
	 * @param productName
	 * @return
	 */
	@Modifying
	@Transactional(readOnly = false) 
	@Query(value = "UPDATE Product p SET p.categoryName = :categoryName, p.description = :description, p.price = :price, p.stock = :stock WHERE p.productName = :productName")
	Integer updateProduct(@Param("categoryName") String categoryName, @Param("description") String description, @Param("price") int price, @Param("stock") int stock, @Param("productName") String productName);
	/**
	 * Custom query to update the stock details in the product table.
	 * This is used to manipulate the stock details based on the purchase status.
	 * @param stock
	 * @param productName
	 * @return
	 */
	@Modifying
	@Transactional(readOnly = false) 
	@Query(value = "UPDATE Product p SET p.stock = :stock WHERE p.productName = :productName")
	Integer updateStock(@Param("stock") int stock, @Param("productName") String productName);
	
} 
