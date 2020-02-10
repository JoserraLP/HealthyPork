package com.healthypork.db;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.healthypork.models.AirQuality;
import com.healthypork.models.Humidity;
import com.healthypork.models.Luminosity;
import com.healthypork.models.Noise;
import com.healthypork.models.Temperature;
import com.healthypork.models.Weather;

public class DBConn {

	private Connection conn;

	/**
	 * Method to this.connect to the MySQL database
	 * @return True if the this.connection is successful or False if not
	 */
	public boolean dbConnect() {

		try {
			String driver = DBConstants.DRIVER;
			String serverAddress = DBConstants.SERVER_ADDRESS;
			String port = DBConstants.PORT;
			String user = DBConstants.USER;
			String password = DBConstants.PWD;
			String database = DBConstants.DATABASE;

			Class.forName(driver);

			this.conn = DriverManager.getConnection("jdbc:mysql://" + serverAddress + ":" + port + 
					"/" + database + "?characterEncoding=latin1", user, password);

			if (!this.conn.isClosed()) 
				return true;
			else 
				return false;
		} catch (Exception e) {
			System.out.println(e);
			return false;
		}

	}

	/**
	 * Method to disthis.connect to the MySQL database
	 * @return True if the disthis.connection is successful or False if not
	 */
	public boolean dbDisconnect() {
		try {
			this.conn.close();
			if (this.conn.isClosed())
				return true;
			else
				return false;
		} catch (Exception e) {
			System.out.println(e);
			return false;
		}
	}

	/**
	 * Method to get the info from the airQuality table
	 */
	public ArrayList<AirQuality> getAirQuality() {
		try {
			ArrayList<AirQuality> arrAirQuality = new ArrayList<AirQuality>(); 
			Statement st;
			st = this.conn.createStatement();
			String sql = "SELECT * FROM airquality"; // TODO Get those who has the date 5 minutes less than actual for example
			ResultSet rset = st.executeQuery(sql);
			while(rset.next()) {
				AirQuality a = new AirQuality(rset.getDouble(2), rset.getDate(3));
				arrAirQuality.add(a);
			}
			return arrAirQuality;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * Method to get the info from the humidity table
	 */
	public ArrayList<Humidity> getHumidity() {
		try {
			ArrayList<Humidity> arrHumidity = new ArrayList<Humidity>(); 
			Statement st;
			st = this.conn.createStatement();
			String sql = "SELECT * FROM humidity"; // TODO Get those who has the date 5 minutes less than actual for example
			ResultSet rset = st.executeQuery(sql);
			while(rset.next()) {
				Humidity h = new Humidity(rset.getDouble(2), rset.getDate(3));
				arrHumidity.add(h);
			}
			return arrHumidity;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * Method to get the info from the luminosity table
	 */
	public ArrayList<Luminosity> getLuminosity() {
		try {
			ArrayList<Luminosity> arrLuminosity = new ArrayList<Luminosity>(); 
			Statement st;
			st = this.conn.createStatement();
			String sql = "SELECT * FROM luminosity"; // TODO Get those who has the date 5 minutes less than actual for example
			ResultSet rset = st.executeQuery(sql);
			while(rset.next()) {
				Luminosity l = new Luminosity(rset.getDouble(2), rset.getDate(3));
				arrLuminosity.add(l);
			}
			return arrLuminosity;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * Method to get the info from the noise table
	 */
	public ArrayList<Noise> getNoise() {
		try {
			ArrayList<Noise> arrNoise = new ArrayList<Noise>(); 
			Statement st;
			st = this.conn.createStatement();
			String sql = "SELECT * FROM noise"; // TODO Get those who has the date 5 minutes less than actual for example
			ResultSet rset = st.executeQuery(sql);
			while(rset.next()) {
				Noise n = new Noise(rset.getDouble(2), rset.getDate(3));
				arrNoise.add(n);
			}
			return arrNoise;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * Method to get the info from the temperature table
	 */
	public ArrayList<Temperature> getTemperature() {
		try {
			ArrayList<Temperature> arrTemperature = new ArrayList<Temperature>(); 
			Statement st;
			st = this.conn.createStatement();
			String sql = "SELECT * FROM temperature"; // TODO Get those who has the date 5 minutes less than actual for example
			ResultSet rset = st.executeQuery(sql);
			while(rset.next()) {
				Temperature t = new Temperature(rset.getDouble(2), rset.getDate(3));
				arrTemperature.add(t);
			}
			return arrTemperature;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * Method to get the info from the weather table
	 */
	public ArrayList<Weather> getWeather() {
		try {
			ArrayList<Weather> arrWeather = new ArrayList<Weather>(); 
			Statement st;
			st = this.conn.createStatement();
			String sql = "SELECT * FROM weather"; // TODO Get those who has the date 5 minutes less than actual for example
			ResultSet rset = st.executeQuery(sql);
			while(rset.next()) {
				Weather w = new Weather(rset.getString(2), rset.getDouble(3), rset.getDouble(4), rset.getDouble(5), rset.getDouble(6), rset.getInt(7), rset.getInt(8), rset.getDouble(9), rset.getDate(10), rset.getString(11));
				arrWeather.add(w);
			}
			return arrWeather;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
}
