/**
 * 
 */
package com.spaneos.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.spaneos.model.CategoryBean;

/**
 * @author spaneos
 *
 */

public interface CategoryDao extends CrudRepository<CategoryBean, Long>{
	List<CategoryBean> findAll();
	CategoryBean findAllByNameIgnoreCaseContaining(String name);
}
