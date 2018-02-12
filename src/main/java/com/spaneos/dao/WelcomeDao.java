/**
 * 
 */
package com.spaneos.dao;

import org.springframework.data.repository.CrudRepository;

import com.spaneos.model.Admin;

/**
 * @author spaneos
 *
 */
public interface WelcomeDao extends CrudRepository<Admin, Integer> {

}
