package com.revpm.util;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBUtil {

	private static final String URL = "jdbc:oracle:thin:@localhost:1522:XE"; // Oracle 10g
																				
	private static final String USER = "revpm";
	private static final String PASSWORD = "revpm123";

	public static Connection getConnection() {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			return DriverManager.getConnection(URL, USER, PASSWORD);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
