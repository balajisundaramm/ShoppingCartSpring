/**
 * 
 */
package com.spaneos.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spaneos.dao.UserDao;
import com.spaneos.model.User;

/**
 * @author spaneos
 *
 */
@Service
public class UserService {
    private static final Logger LOGGER= LoggerFactory.getLogger(UserService.class.getName());
    
    @Autowired
    private UserDao userDao;
    
    public UserService() {
    	LOGGER.error("User Service");
	}
    
    public String register(User user) {
    	userDao.save(user);
    	return "SUCCESS";
    }

}
