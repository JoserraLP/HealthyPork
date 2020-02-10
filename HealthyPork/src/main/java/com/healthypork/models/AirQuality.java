package com.healthypork.models;

import java.sql.Date;

/**
 * Represents the air quality
 * @author Jose Ramon Lozano, Daniel Garcia
 */
public class AirQuality {

	private double amount;
	private Date date;
	
	/**
	 * Parameterized constructor 
	 * @param amount current value of the air quality
	 * @param date date when the value was obtained
	 */
	public AirQuality(double amount, Date date) {
		super();
		this.amount = amount;
		this.date = date;
	}
	
	/**
	 * Getter of amount
	 * @return the value of the air quality
	 */
	public double getAmount() {
		return amount;
	}
	
	/**
	 * Setter of amount
	 * @param amount the value of the air quality
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
