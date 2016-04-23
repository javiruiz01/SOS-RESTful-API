package rest1;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.PathParam;
import java.sql.*;

@Path ("/api/v1")
public class API {

	static Connection connection = null; // Conexión generica para todos los métodos que creemos

	public static void getConnection() {

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
			String url = "jdbc:mysql://" + host + "/" + database; // URL para la conexión

			try {
				connection = DriverManager.getConnection(url,user,passwd); // Conectamos con estos credenciales
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public static String getUsers (Connection conn) {
		Statement sentence = null;
		String query =	"SELECT * FROM USER";
		String result = "|++++++++++++++++++++++|\n";

		try {
			sentence = connection.createStatement();
			ResultSet rs = sentence.executeQuery(query);
			while (rs.next()) {
				String username = rs.getString("name");
				String surname = rs.getString("lastname");
				result = result + "|Name: " + username + " | " + "Surname: " + surname + "|\n";
			}
			sentence.close();
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		result = result + "|++++++++++++++++++++++|";
		return result;
	}

	public static String getPosts(Connection conn, int idUser) {
		PreparedStatement sentence = null;
		String result = "++++++++++++++++++++++++++\n";
		String query = "SELECT postBody " 
				+ "FROM POST p, USER u " 
				+ "WHERE u.idUser = ? "
				+ "AND p.user = u.idUser";
		try {
			sentence = connection.prepareStatement(query);
			sentence.setInt(1, idUser);
			ResultSet rs = sentence.executeQuery();
			while(rs.next()) {
				result = result + rs.getString(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	@GET
	@Path("/users")
	@Produces(MediaType.TEXT_PLAIN) // Hacerlo tambien para html
	public static String test() {
		String users;
		getConnection();
		users = getUsers(connection);
		return users;
	}

	@GET
	@Path("/users/{idUser}/posts")
	@Produces(MediaType.TEXT_PLAIN)
	public static String posts(@PathParam("idUser") int idUser) {
		String result;
		getConnection();
		result = getPosts(connection, idUser);
		return result;
	}


	@GET
	@Path ("/hello")
	@Produces(MediaType.TEXT_PLAIN)
	public static String hello () {
		return "hello";
	}
}
