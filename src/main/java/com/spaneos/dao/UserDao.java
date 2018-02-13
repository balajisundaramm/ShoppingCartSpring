/**
 * 
 */
package com.spaneos.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.spaneos.model.User;

/**
 * @author spaneos
 *
 */
public interface UserDao extends CrudRepository<User, Long>{
	List<User> findAll();
	User findById(long id);
}
