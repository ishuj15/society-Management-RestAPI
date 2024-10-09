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
						connection = DriverManager.getConnection("jdbc:mysql://localhost:33066/test", "root", "root");
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
