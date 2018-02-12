/**
 * 
 */
package com.spaneos.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * @author spaneos
 *
 */
@Entity
public class Admin {
	@Id
    @GeneratedValue
    private Integer id;
	private String userName;
	private String password;
}
