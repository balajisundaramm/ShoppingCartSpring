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
import java.lang.String;

/**
 * @author balaji
 *
 */
@Transactional(readOnly = true)
public interface UserDao extends CrudRepository<User, Long> {
	/**
	 * This method is used to retrieve the all user details.
	 */
	List<User> findAll();
	/**
	 * This method is used to retrieve the user details base don the id.
	 * @param id
	 * @return
	 */
	User findById(long id);
	/**
	 * This method is used to retrieve the user details based on the email.
	 * @param email
	 * @return
	 */
	List<User> findByEmail(String email);
	/**
	 * Custom query to update the user in the user table.
	 * @param mobile
	 * @param address
	 * @param gender
	 * @param dob
	 * @param email
	 * @return
	 */
	@Modifying
	@Transactional(readOnly = false)
	@Query(value = "UPDATE User u SET u.mobile = :mobile, u.address = :address, u.gender = :gender, u.dob = :dob WHERE u.email = :email")
	Integer setUserName(@Param("mobile") String mobile, @Param("address") String address, @Param("gender") String gender, @Param("dob") String dob, @Param("email") String email);
	
}
