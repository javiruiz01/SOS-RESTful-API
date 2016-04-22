package todo;

import java.sql.*;
import java.util.*;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/api/v1")
public class iClass {

	static Connection connection = null; // Conexión generica para todos los métodos que creemos

	public static Connection getConnection() {
		if (connection == null) {
			try {
				Class.forName("com.mysql.jdbc.Driver"); // Busca que está instanciado el driver
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			String host = "localhost:3306";
			String user = "root";
			String passwd = "sandsand";
			String database = "APIRESTful";
			String url = "jdbc:mysql://" + host + "/" + database;

			try {
				connection = DriverManager.getConnection(url,user,passwd);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return connection;
	}

	public static String getUsers (Connection conn) {
		Statement sentence = null;
		String query =	"SELECT * FROM USERS";
		String result = "|++++++++++++++++++++++|\n";

		try {
			sentence = connection.createStatement();
			ResultSet rs = sentence.executeQuery(query);
			while (rs.next()) {
				String username = rs.getString("name");
				String surname = rs.getString("surname");
				result = result + "|Name: " + username + " | " + "Surname: " + surname + "|\n";
			}
			sentence.close();
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		result = result + "\n|++++++++++++++++++++++|";
		return result;
	}
	
	@GET
	@Path("/users")
	@Produces(MediaType.TEXT_PLAIN)
	public static String test() {
		String users;
		connection = getConnection();
		users = getUsers(connection);
		try {
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return users;
	}
}
