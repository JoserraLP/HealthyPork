package com.healthypork.models;

import java.sql.Date;

/**
 * Represents the temperature in the environment
 * @author Jose Ramon Lozano, Daniel Garcia
 */
public class Weather {

	private String state;
	private double temp;
	private double tempFeel;
	private double tempMin;
	private double tempMax;
	private int pressure;
	private int humidity;
	private double wind;
	private Date date;
	private String season;
	
	/**
	 * Parameterized constructor
	 * @param state current state of the weather
	 * @param temp current temperature 
	 * @param tempFeel current feel temperature
	 * @param tempMin minimum temperature
	 * @param tempMax maximum temperature
	 * @param pressure current pressure
	 * @param humidity current humidity 
	 * @param wind current wind
	 * @param date date when the data was obtained
	 * @param season season when the data was obtained
	 */
	public Weather(String state, double temp, double tempFeel, double tempMin, double tempMax, int pressure,
			int humidity, double wind, Date date, String season) {
		super();
		this.state = state;
		this.temp = temp;
		this.tempFeel = tempFeel;
		this.tempMin = tempMin;
		this.tempMax = tempMax;
		this.pressure = pressure;
		this.humidity = humidity;
		this.wind = wind;
		this.date = date;
		this.season = season;
	}
	
	/**
	 * Getter of state
	 * @return the state
	 */
	public String getState() {
		return state;
	}
	
	/**
	 * Setter of state
	 * @param state the state to set
	 */
	public void setState(String state) {
		this.state = state;
	}
	
	/**
	 * Getter of temp
	 * @return the temp
	 */
	public double getTemp() {
		return temp;
	}
	
	/**
	 * Setter of temp
	 * @param temp the temp to set
	 */
	public void setTemp(double temp) {
		this.temp = temp;
	}
	
	/** 
	 * Getter of tempFeel
	 * @return the tempFeel
	 */
	public double getTempFeel() {
		return tempFeel;
	}
	
	/**
	 * Setter of tempFeel
	 * @param tempFeel the tempFeel to set
	 */
	public void setTempFeel(double tempFeel) {
		this.tempFeel = tempFeel;
	}
	
	/**
	 * Getter of tempMin
	 * @return the tempMin
	 */
	public double getTempMin() {
		return tempMin;
	}
	
	/**
	 * Setter of tempMin
	 * @param tempMin the tempMin to set
	 */
	public void setTempMin(double tempMin) {
		this.tempMin = tempMin;
	}
	
	/**
	 * Getter of tempMax
	 * @return the tempMax
	 */
	public double getTempMax() {
		return tempMax;
	}
	
	/**
	 * Setter of tempMax
	 * @param tempMax the tempMax to set
	 */
	public void setTempMax(double tempMax) {
		this.tempMax = tempMax;
	}
	
	/**
	 * Getter of pressure
	 * @return the pressure
	 */
	public int getPressure() {
		return pressure;
	}
	
	/**
	 * Setter of pressure
	 * @param pressure the pressure to set
	 */
	public void setPressure(int pressure) {
		this.pressure = pressure;
	}
	
	/**
	 * Getter of humidity
	 * @return the humidity
	 */
	public int getHumidity() {
		return humidity;
	}
	
	/**
	 * Setter of humidity
	 * @param humidity the humidity to set
	 */
	public void setHumidity(int humidity) {
		this.humidity = humidity;
	}
	
	/**
	 * Getter of wind
	 * @return the wind
	 */
	public double getWind() {
		return wind;
	}
	
	/**
	 * Setter of wind
	 * @param wind the wind to set
	 */
	public void setWind(double wind) {
		this.wind = wind;
	}
	
	/**
	 * Getter of date
	 * @return the date
	 */
	public Date getDate() {
		return date;
	}
	
	/**
	 * Setter of date
	 * @param date the date to set
	 */
	public void setDate(Date date) {
		this.date = date;
	}
	
	/**
	 * Getter of season 
	 * @return the season
	 */
	public String getSeason() {
		return season;
	}
	
	/**
	 * Setter of season
	 * @param season the season to set
	 */
	public void setSeason(String season) {
		this.season = season;
	}
	
	
	
	
}
