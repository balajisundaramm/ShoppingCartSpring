/**
 * 
 */
package com.spaneos.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.spaneos.controllers.HomeController;

/**
 * @author spaneos
 *
 */
@Service
public class WelcomeService {
    private static final Logger LOGGER= LoggerFactory.getLogger(WelcomeService.class.getName());

	public WelcomeService() {
		LOGGER.error("Welcome Service");
	}
}
