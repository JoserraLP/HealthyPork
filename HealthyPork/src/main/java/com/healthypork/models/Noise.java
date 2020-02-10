package com.healthypork.models;

import java.sql.Date;

/**
 * Represents the noise in the environment
 * @author Jose Ramon Lozano, Daniel Garcia
 */
public class Noise {

	private double amount;
	private Date date;
	
	/**
	 * Parameterized constructor 
	 * @param amount current value of the noise
	 * @param date date when the value was obtained
	 */
	public Noise(double amount, Date date) {
		super();
		this.amount = amount;
		this.date = date;
	}
	
	/**
	 * Getter of amount
	 * @return the value of the noise
	 */
	public double getAmount() {
		return amount;
	}
	
	/**
	 * Setter of amount
	 * @param amount the value of the noise
	 */
	public void setAmount(double amount) {
		this.amount = amount;
	}
	
	/**
	 * Getter of date
	 * @return the date when the data was obtained
	 */
	public Date getDate() {
		return date;
	}
	
	/**
	 * Setter of date
	 * @param date the date when the data was obtained
	 */
	public void setDate(Date date) {
		this.date = date;
	}

}
