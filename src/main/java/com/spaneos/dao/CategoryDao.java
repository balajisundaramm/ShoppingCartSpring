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

import com.spaneos.model.CategoryBean;

/**
 * @author balaji
 *
 */
@Transactional(readOnly = true)
public interface CategoryDao extends CrudRepository<CategoryBean, Long>{
	/**
	 * This method is used to retrieve the Category details.
	 */
	List<CategoryBean> findAll();
	/**
	 * This method is used to retrieve the Category details based on the category name.
	 * 
	 * @param name
	 * @return
	 */
	CategoryBean findAllByNameIgnoreCaseContaining(String name);
	/**
	 * This method is used to delete the category from database based on category name
	 * @param name
	 * @return
	 */
	@Transactional
	long deleteByName(String name);
	/**
	 * Custom query to update the category details in the database.
	 * @param description
	 * @param name
	 * @return
	 */
	@Modifying
	@Transactional(readOnly = false) 
	@Query(value = "UPDATE CategoryBean c SET c.description = :description WHERE c.name = :name")
	Integer updateCategory(@Param("description") String description, @Param("name") String name);
	
}
