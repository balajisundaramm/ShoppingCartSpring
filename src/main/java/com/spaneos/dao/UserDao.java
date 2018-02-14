/**
 * 
 */
package com.spaneos.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.spaneos.model.User;

/**
 * @author spaneos
 *
 */
@Transactional(readOnly = true)
public interface UserDao extends CrudRepository<User, Long> {
	List<User> findAll();

	User findById(long id);

	@Modifying
	@Transactional(readOnly = false)
	@Query(value = "UPDATE User u SET u.mobile = :mobile, u.address = :address, u.gender = :gender, u.dob = :dob WHERE u.email = :email")
	Integer setUserName(@Param("mobile") String mobile, @Param("address") String address, @Param("gender") String gender, @Param("dob") String dob, @Param("email") String email);
	
}
