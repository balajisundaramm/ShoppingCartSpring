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
 * @author spaneos
 *
 */
@Transactional(readOnly = true)
public interface CategoryDao extends CrudRepository<CategoryBean, Long>{
	List<CategoryBean> findAll();
	CategoryBean findAllByNameIgnoreCaseContaining(String name);
	@Transactional
	long deleteByName(String name);
	
	@Modifying
	@Transactional(readOnly = false) 
	@Query(value = "UPDATE CategoryBean c SET c.description = :description WHERE c.name = :name")
	Integer updateCategory(@Param("description") String description, @Param("name") String name);
	
}
