package edu.epowerhouse.common.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.springframework.stereotype.Component;

@Component
public class DatabaseConnection {
	private static Connection connection = null;

	private static final String URL = "jdbc:mysql:// localhost:3306/org";
	private static final String USER = "postgres";
	private static final String PASSWORD = "password";

	public static Connection getConnection() {
		if (connection == null) {
			try {
				Class.forName("com.mysql.jdbc.Driver");
				connection = DriverManager.getConnection(URL, USER, PASSWORD);
			}
			catch (ClassNotFoundException | SQLException e) {
				e.printStackTrace();
			}
		}
		return connection;
	}
}
