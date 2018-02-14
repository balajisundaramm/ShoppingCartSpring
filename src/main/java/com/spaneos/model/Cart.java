/**
 * 
 */
package com.spaneos.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

/**
 * @author spaneos
 *
 */
@Entity
public class Cart {
	@Id
	@GeneratedValue
	private long id;
	@NotNull
	private String productName;
	@NotNull
	private int quantity;
	@NotNull
	private int price;
	
	
	

	public Cart() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Cart(String productName, int quantity, int price) {
		super();
		this.productName = productName;
		this.quantity = quantity;
		this.price = price;
	}

	/**
	 * @return the id
	 */
	public long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * @return the productName
	 */
	public String getProductName() {
		return productName;
	}

	/**
	 * @param productName the productName to set
	 */
	public void setProductName(String productName) {
		this.productName = productName;
	}

	/**
	 * @return the quantity
	 */
	public int getQuantity() {
		return quantity;
	}

	/**
	 * @param quantity the quantity to set
	 */
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	/**
	 * @return the price
	 */
	public int getPrice() {
		return price;
	}

	/**
	 * @param price the price to set
	 */
	public void setPrice(int price) {
		this.price = price;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Cart [id=" + id + ", productName=" + productName + ", quantity=" + quantity + ", price=" + price + "]";
	}

	
	
	
	
}
