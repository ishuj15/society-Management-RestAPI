package com.society.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.society.util.str;

public class DbConnection {
	private static Connection connection;
	private static final Object lock = new Object();

	public static Connection getConnection() throws ClassNotFoundException, SQLException {
		if (connection == null || connection.isClosed()) {
			synchronized (lock) {
				if (connection == null || connection.isClosed()) {
					try {
						Class.forName(str.mysqlConnectorJdbc);
						connection = DriverManager.getConnection(str.rdsURL, str.rdsUsername, str.rdsPassword);

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
