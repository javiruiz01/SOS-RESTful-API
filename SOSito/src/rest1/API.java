package rest1;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.xml.bind.JAXBElement;
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

	public static Response getUsers (Connection conn) {
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
		return Response.status(Response.Status.OK).entity(result).build();
	}

	public static Response getPosts(Connection conn, Integer idUser, Integer limit, Integer offset, Date sdate, Date edate) {
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
		return Response.status(Response.Status.OK).entity(result).build();
	}

	public static Response getFriends (Connection conn, Integer idUser, Integer limit, Integer offset) {
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
		return Response.status(Response.Status.OK).entity(result).build();
	}

	public static Response getFindUser (Connection connection, String name) {
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
		return Response.status(Response.Status.OK).entity(result).build();
	}

	public static Response postCreateUser (Connection connection, Integer idUser, String username, Integer postNumber, String name,
			String lastname, String gender, String mail, String phone) {
		Statement sentence = null;
		String query = "INSERT INTO USER\n"
				+ "(idUser, username, postNumber, name, lastname, gender, mail, phone)\n"
				+ "VALUES\n"
				+ "('" + idUser + "', '" + username + "', '" + postNumber  
				+ "', '" + name + "', '" + lastname + "', '" + gender  
				+ "', '" + mail + "', '" + phone + "');"  ;		
		try {
			sentence = connection.createStatement();
			int rs = sentence.executeUpdate(query);
			if (rs != 0) {
				return Response.status(Response.Status.CREATED).build();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return Response.status(Response.Status.BAD_REQUEST).build();
	}
	
	public static Response postDeleteUser (Connection connection, Integer idUser) {
		Response result = null;
		return result;
	}

	@GET
	@Path("/users")
	@Produces(MediaType.TEXT_XML)
	public static Response test() {
		Response users;
		getConnection();
		try {		
			if (connection.isClosed() == true) {
				return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		users = getUsers(connection);
		if (users.getStatus() != 200) {
			users = Response.status(Response.Status.BAD_REQUEST).build();
		}
		return users;
	}

	@GET
	@Path("/users/{idUser}/posts")
	@Produces(MediaType.TEXT_XML)
	public static Response posts(@PathParam("idUser") Integer idUser,
			@QueryParam("limit") Integer limit,
			@QueryParam("offset") Integer offset,
			@QueryParam("sdate") Date sdate,
			@QueryParam("edate") Date edate) {
		Response result;
		getConnection();
		try {		
			if (connection.isClosed() == true) {
				return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		result = getPosts(connection, idUser, limit, offset, sdate, edate);
		if (result.getStatus() != 200) {
			result = Response.status(Response.Status.BAD_REQUEST).build();
		}
		return result;
	}

	@GET
	@Path("/users/{idUser}/friends")
	@Produces(MediaType.TEXT_XML)
	public static Response friends (@PathParam("idUser") Integer idUser,
			@QueryParam("limit") Integer limit,
			@QueryParam("offset") Integer offset) {
		Response result;
		getConnection();
		try {		
			if (connection.isClosed() == true) {
				return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		result = getFriends(connection, idUser, limit, offset);
		if (result.getStatus() != 200) {
			result = Response.status(Response.Status.BAD_REQUEST).build();
		}
		return result;
	}

	@GET
	@Path("/users/{name}")
	@Produces(MediaType.TEXT_XML)
	public static Response findUser (@PathParam("name") String name) {
		Response result;
		getConnection();
		try {		
			if (connection.isClosed() == true) {
				return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		result = getFindUser(connection, name);
		if (result.getStatus() != 200) {
			result = Response.status(Response.Status.BAD_REQUEST).build();
		}
		return result;
	}

	@GET
	@Path ("/hello")
	@Produces(MediaType.TEXT_PLAIN)
	public static String hello () {
		return "hello";
	}

	@POST
	@Path("/user/add")
	@Produces(MediaType.TEXT_XML)
	@Consumes(MediaType.TEXT_XML)
	public static Response createUser (JAXBUserModel user) {
		Response result;
		getConnection();
		try {		
			if (connection.isClosed() == true) {
				return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		Integer idUser = user.getIdUser();
		String username = user.getUsername();
		Integer postNumber = user.getPostNumber();
		String name = user.getName();
		String lastname = user.getLastname();
		String gender = user.getGender();
		String mail = user.getMail();
		String phone = user.getPhone();
		result = postCreateUser(connection, idUser, username, postNumber, name, lastname, gender, mail, phone);
		return result;
	}
	
	@POST
	@Path("/user/delete/{idUser}")
	@Produces(MediaType.TEXT_XML)
	@Consumes(MediaType.TEXT_XML)
	public static Response deleteUser (JAXBUserModel user, @PathParam("idUser") Integer idUser) {
		Response result = null;
		getConnection();
		try {		
			if (connection.isClosed() == true) {
				return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		result = postDeleteUser(connection, idUser);
		
		return result;
	}
}
