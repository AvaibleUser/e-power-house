package edu.epowerhouse.common.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.springframework.stereotype.Component;

@Component
public class DatabaseConnection {
	private static Connection connection = null;

	private static final String URL = "jdbc:postgresql://db:5432/e_power_house";
	private static final String USER = "epowerhousemanagemer";
	private static final String PASSWORD = "epowerhousemanagemerpass";

	public static Connection getConnection() {
		if (connection == null) {
			try {
				Class.forName("org.postgresql.Driver");
				connection = DriverManager.getConnection(URL, USER, PASSWORD);
			}
			catch (ClassNotFoundException | SQLException e) {
				e.printStackTrace();
			}
		}
		return connection;
	}
}
