package com.society.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbConnection {
	private static Connection connection;
	private static final Object lock = new Object();

	public static Connection getConnection() throws ClassNotFoundException, SQLException {
		if (connection == null || connection.isClosed()) {
			synchronized (lock) {
				if (connection == null || connection.isClosed()) {
					try {
						Class.forName("com.mysql.cj.jdbc.Driver");
						//connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/test", "root", "root");
						
						//local host with rds as a database
						connection = DriverManager.getConnection("jdbc:mysql://app-society-database.czqqok6w4am2.us-east-1.rds.amazonaws.com/test", "admin", "Admin123#");

					} catch (SQLException e) {
						// Log the exception with a meaningful message
						System.err.println("Failed to create the database connection.");
						e.printStackTrace();
						// Rethrow the exception to notify the caller of the failure
						throw e;
					}
				}
			}
		}
		return connection;
	}
}
