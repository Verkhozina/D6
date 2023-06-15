package com.example.springboot;

import java.util.Arrays;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import java.sql.*;

@SpringBootApplication
public class Application {

	static Connection connection = null;
	public static void main(String[] args) {
		String DB_URL = "jdbc:postgresql://localhost:1234/demo";
		String USER = "postgres";
		String PASS = "qwerty";


			System.out.println("Testing connection to PostgreSQL JDBC");
			try {
				Class.forName("org.postgresql.Driver");
			} catch (ClassNotFoundException e) {
				System.out.println("PostgreSQL JDBC Driver is not found. Include it in your library path ");
				e.printStackTrace();
				return;
			}

			System.out.println("PostgreSQL JDBC Driver successfully connected");

			try {
				connection = DriverManager
						.getConnection(DB_URL, USER, PASS);

			} catch (SQLException e) {
				System.out.println("Connection Failed");
				e.printStackTrace();
				return;
			}

			if (connection != null) {
				System.out.println("You successfully connected to database now");
			} else {
				System.out.println("Failed to make connection to database");
			}

		SpringApplication.run(Application.class, args);
	}

	static String getCities() throws SQLException {
		StringBuilder res = new StringBuilder();
		Statement statement = connection.createStatement();
		ResultSet rs = statement.executeQuery("SELECT airports.city from airports");
		while (rs.next()) {
			res.append("{\"Name\": \"").append(rs.getString("city")).append("\"}, ");
		}
		res.insert(0, "[");
		res.delete(res.length() - 2, res.length() - 1);
		res.append("]");
		return res.toString();
	}

	static String getAirports() throws SQLException {
		StringBuilder res = new StringBuilder();
		Statement statement = connection.createStatement();
		ResultSet rs = statement.executeQuery("SELECT airports.airport_code, airports.airport_name from airports");
		while (rs.next()) {
			res.append("{\"Code\": \"").append(rs.getString("airport_code"))
					.append("\", \"Name\": \"").append(rs.getString("airport_name")).append("\"}, ");
		}
		res.insert(0, "[");
		res.delete(res.length() - 2, res.length() - 1);
		res.append("]");
		return res.toString();
	}
	static String getAirportsByCity(String name) throws SQLException {
		StringBuilder res = new StringBuilder();
		Statement statement = connection.createStatement();
		ResultSet rs = statement.executeQuery("SELECT airports.airport_code, airports.airport_name from airports\n" +
				"WHERE city = '" + name + "';");
		while (rs.next()) {
			res.append("{\"Code\": \"").append(rs.getString("airport_code"))
					.append("\", \"Name\": \"").append(rs.getString("airport_name")).append("\"}, ");
		}
		res.insert(0, "[");
		res.delete(res.length() - 2, res.length() - 1);
		res.append("]");
		return res.toString();
	}

	@Bean
	public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
		return args -> {

			System.out.println("Let's inspect the beans provided by Spring Boot:");

			String[] beanNames = ctx.getBeanDefinitionNames();
			Arrays.sort(beanNames);
			for (String beanName : beanNames) {
				System.out.println(beanName);
			}

		};
	}

}
