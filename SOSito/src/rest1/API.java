package rest1;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
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
		String result = "<?xml version=\"1.0\"?>\n<Users>\n";

		try {
			sentence = connection.createStatement();
			ResultSet rs = sentence.executeQuery(query);
			while (rs.next()) {
				String username = rs.getString("username");
				int postNumber = rs.getInt("postNumber");
				String name = rs.getString("name");
				String lastname = rs.getString("lastname");
				String gender = rs.getString("gender");
				String mail = rs.getString("mail");
				String phone = rs.getString("phone");

				result += "<User>\n"
						+ "<Username>" + username + "</Username>\n"
						+ "<PostNumber>" + postNumber + "</PostNumber>"
						+ "<Name>" + name + "</Name>\n" 
						+ "<LastName>" + lastname + "</LastName>\n"
						+ "<Gender>" + gender + "</Gender>\n"
						+ "<Mail>" + mail + "</Mail>\n"
						+ "<Phone>" + phone + "</Phone>\n"
						+ "</User>\n";
			}
			sentence.close();
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		result += "</Users>";
		return result;
	}

	public static String getPosts(Connection conn, Integer idUser, Integer limit, Integer offset, Date sdate, Date edate) {
		PreparedStatement sentence = null;
		String result = "<?xml version=\"1.0\"?>\n<Posts>\n";
		String query = "SELECT * \n" 
				+ "FROM POST p, USER u \n"
				+ "WHERE u.idUser = ? \n"
				+ "AND p.user = u.idUser\n";
		if (sdate != null && edate != null) {
			query += "AND p.creationDate BETWEEN '" + sdate + "' AND '" + edate + "' \n";
		} else {
			if (sdate != null) {
				query += "AND p.creationDate >= '" + sdate + "' \n";
			}
			if (edate != null) {
				query += "AND p.creationDate <= '" + edate + "' \n";
			}
		}
		if (limit != null) {
			query += "LIMIT " + limit + " \n";
			if (offset != null) {
				query += "OFFSET " + offset + " \n";
			}
		}
		try {
			sentence = connection.prepareStatement(query);
			sentence.setInt(1, idUser);
			ResultSet rs = sentence.executeQuery();
			while(rs.next()) {
				String postBody = rs.getString("postBody");
				Date date = rs.getDate("creationDate");
				result += "<Post>" + postBody + "</Post>\n"
						+ "<CreationDate>" + date + "</CreationDate>\n";
			}
			sentence.close();
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		result += "</Posts>";
		return result;
	}

	public static String getFriends (Connection conn, Integer idUser, Integer limit, Integer offset) {
		PreparedStatement sentence = null;
		PreparedStatement sentenceFriend = null;
		String result = "<?xml version=\"1.0\"?>\n<Friends>\n";
		String query = "SELECT * \n" 
				+ "FROM ISFRIEND f, USER u \n"
				+ "WHERE f.user1 = ? \n"
				+ "AND f.user1 = u.idUser \n";
		if (limit != null) {
			query += "LIMIT " + limit + " \n";
			if (offset != null) {
				query += "OFFSET " + offset + " \n";
			}
		}

		String queryFriend = "SELECT * \n"
				+ "FROM USER u\n"
				+ "WHERE u.idUser = ?";
		try {
			sentence = connection.prepareStatement(query);
			sentence.setInt(1, idUser);
			ResultSet rs = sentence.executeQuery();
			while (rs.next()) {
				Integer friend = rs.getInt("user2");
				sentenceFriend = connection.prepareStatement(queryFriend);
				sentenceFriend.setInt(1, friend);
				ResultSet rsFriend = sentenceFriend.executeQuery();
				while (rsFriend.next()) {
					String name = rsFriend.getString("name");
					String lastname = rsFriend.getString("lastname");
					result += "<Friend>\n" 
							+ "<Name>" + name + "</Name>"
							+ "<LastName>" + lastname + "</LastName>"
							+ "</Friend>";
				}
				sentenceFriend.close();
				rsFriend.close();
			}
			sentence.close();
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		result += "</Friends>";
		return result;
	}

	public static String getFindUser (Connection connection, String name) {
		String result = "<?xml version=\"1.0\"?>\n<Users>\n";
		PreparedStatement sentence = null;
		String query = "SELECT * \n"
				+ "FROM USER u\n"
				+ "WHERE u.name = ? \n";

		try {
			sentence = connection.prepareStatement(query);
			sentence.setString(1, name);
			ResultSet rs = sentence.executeQuery();
			while (rs.next()) {
				String nameUser = rs.getString("name");
				String lastname = rs.getString("lastname");
				result += "<User>\n"
						+ "<Name>" + nameUser + "</Name>"
						+ "<Lastname>" + lastname + "</Lastname>\n"
						+ "</User>";
			}
			sentence.close();
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		result += "</Users>";
		return result;
	}

	@GET
	@Path("/users")
	@Produces(MediaType.TEXT_XML)
	public static String test() {
		String users;
		getConnection();
		users = getUsers(connection);
		return users;
	}

	@GET
	@Path("/users/{idUser}/posts")
	@Produces(MediaType.TEXT_XML)
	public static String posts(@PathParam("idUser") Integer idUser,
			@QueryParam("limit") Integer limit,
			@QueryParam("offset") Integer offset,
			@QueryParam("sdate") Date sdate,
			@QueryParam("edate") Date edate) {
		String result;
		getConnection();
		result = getPosts(connection, idUser, limit, offset, sdate, edate);
		return result;
	}

	@GET
	@Path("/users/{idUser}/friends")
	@Produces(MediaType.TEXT_XML)
	public static String friends (@PathParam("idUser") Integer idUser,
			@QueryParam("limit") Integer limit,
			@QueryParam("offset") Integer offset) {
		String result;
		getConnection();
		result = getFriends(connection, idUser, limit, offset);
		return result;
	}

	@GET
	@Path("/users/{name}")
	@Produces(MediaType.TEXT_XML)
	public static String findUser (@PathParam("name") String name) {
		String result;
		getConnection();
		result = getFindUser(connection, name);
		return result;
	}

	@GET
	@Path ("/hello")
	@Produces(MediaType.TEXT_PLAIN)
	public static String hello () {
		return "hello";
	}
}
