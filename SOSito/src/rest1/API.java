package rest1;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
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
		users = DatabaseQuery.getUsers(connection);
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
			@QueryParam("sdate") String sdate,
			@QueryParam("edate") String edate) {
		Response result;
		getConnection();
		try {		
			if (connection.isClosed() == true) {
				return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		result = DatabaseQuery.getPosts(connection, idUser, limit, offset, sdate, edate); 
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
		result = DatabaseQuery.getFriends(connection, idUser, limit, offset);
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
		result = DatabaseQuery.getFindUser(connection, name);
		if (result.getStatus() != 200) {
			result = Response.status(Response.Status.BAD_REQUEST).build();
		}
		return result;
	}

	@POST
	@Path("/users/add")
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
		String name = user.getName();
		String lastname = user.getLastname();
		String gender = user.getGender();
		String mail = user.getMail();
		String phone = user.getPhone();
		result = DatabaseQuery.postCreateUser(connection, idUser, username, name, lastname, gender, mail, phone);
		return result;
	}

	@POST
	@Path("/users/{idUser}/delete")
	public static Response deleteUser (JAXBUserModel user, @PathParam("idUser") Integer idUser) { 
		// Este seguro que es un POST?
		// Debería ser un DELETE
		Response result = null;
		getConnection();
		try {		
			if (connection.isClosed() == true) {
				return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		result = DatabaseQuery.postDeleteUser(connection, idUser);
		if (result.getStatus() != 200) {
			result = Response.status(Response.Status.BAD_REQUEST).build();
		}
		return result;
	}

	@POST
	@Path("/posts/add")
	@Produces(MediaType.TEXT_XML)
	@Consumes(MediaType.TEXT_XML)
	public static Response addPost (JAXBPostModel post) {
		Response result = null;
		getConnection();
		try {		
			if (connection.isClosed() == true) {
				return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		Integer idPost = post.getIdPost();
		String postBody = post.getPostBody();
		String creationDate = post.getCreationDate();
		Integer user = post.getUser();
		result = DatabaseQuery.postAddPost (connection, idPost, postBody, creationDate, user);
		return result;
	}

	@POST
	@Path("/posts/{idPost}/delete")
	@Produces(MediaType.TEXT_XML)
	public static Response deletePost (@PathParam("idPost") Integer idPost) {
		Response result = null;
		getConnection();
		try {		
			if (connection.isClosed() == true) {
				return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		result = DatabaseQuery.postDeletePost(connection, idPost);
		return result;
	}

	@POST
	@Path("/users/{idUser}/friends/add")
	@Produces(MediaType.TEXT_XML)
	@Consumes(MediaType.TEXT_XML)
	public static Response addFriend(@PathParam("idUser") Integer idUser, JAXBFriendModel friend) {
		Response result = null;
		getConnection();
		try {		
			if (connection.isClosed() == true) {
				return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		Integer idUserFriend = friend.getIdFriend();
		result = DatabaseQuery.postAddFriend(connection, idUser, idUserFriend);
		return result;
	}

	@POST
	@Path("/users/{idUser}")
	@Produces(MediaType.TEXT_XML)
	@Consumes(MediaType.TEXT_XML)
	public static Response modifyProfile (@PathParam("idUser") Integer idUser, JAXBUserModel user) {
		String username = user.getUsername();
		String name = user.getName();
		String lastname = user.getLastname();
		String gender = user.getGender();
		String mail = user.getMail();
		String phone = user.getPhone();
		getConnection();
		try {		
			if (connection.isClosed() == true) {
				return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if (username != null) {
			Boolean queryUsername = false;
			queryUsername = DatabaseQuery.postModifyUsername(connection, idUser, username);
			if (queryUsername == false) {
				return Response.status(Response.Status.BAD_REQUEST).build();
			}
		}
		if (name != null) {
			Boolean queryName = false;
			queryName = DatabaseQuery.postModifyName(connection, idUser, name);
			if (queryName == false){
				return Response.status(Response.Status.BAD_REQUEST).build();
			}
		}
		if (lastname != null) {
			Boolean queryLastname = false;
			queryLastname = DatabaseQuery.postModifyLastname (connection, idUser, lastname);
			if (queryLastname == false){
				return Response.status(Response.Status.BAD_REQUEST).build();
			}
		}
		if (gender != null) {
			Boolean queryGender = false;
			queryGender = DatabaseQuery.postModifyGender(connection, idUser, gender);
			if (queryGender == false){
				return Response.status(Response.Status.BAD_REQUEST).build();
			}
		}
		if (mail != null) {
			Boolean queryMail = false;
			queryMail = DatabaseQuery.postModifyMail (connection, idUser, mail);
			if (queryMail == false){
				return Response.status(Response.Status.BAD_REQUEST).build();
			}
		}
		if (phone != null) {
			Boolean queryPhone = false;
			queryPhone = DatabaseQuery.postModifyPhone(connection, idUser, phone);
			if (queryPhone == false){
				return Response.status(Response.Status.BAD_REQUEST).build();
			}
		}
		return Response.status(Response.Status.OK).build();
	}

	@PUT
	@Path("/posts/{idPost}")
	@Produces(MediaType.TEXT_XML)
	@Consumes(MediaType.TEXT_XML)
	public static Response modifyPost (@PathParam("idPost") Integer idPost, JAXBPostModel post) {
		Response result = null;
		String postBody = post.getPostBody();
		getConnection();
		try {		
			if (connection.isClosed() == true) {
				return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		result = DatabaseQuery.putModifyPost(connection, idPost, postBody);
		if (result.getStatus() != 200) {
			return Response.status(Response.Status.BAD_REQUEST).build();
		}
		return Response.status(Response.Status.OK).build();
	}

	@POST
	@Path("/users/{idUser}/friends/delete")
	@Produces(MediaType.TEXT_XML)
	@Consumes(MediaType.TEXT_XML)
	public static Response deleteFriend (@PathParam("idUser") Integer idUser, JAXBFriendModel friend) {
		Response result = null;
		Integer idFriend = friend.getIdFriend();
		getConnection();
		try {		
			if (connection.isClosed() == true) {
				return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		result = DatabaseQuery.postDeleteFriend(connection, idUser, idFriend);
		if (result.getStatus() != 200) {
			return Response.status(Response.Status.BAD_REQUEST).build();
		}
		return Response.status(Response.Status.OK).build();
	}
	
	@GET
	@Path("/users/{idUser}/nposts") 
	@Produces(MediaType.TEXT_XML)
	public static Response postNumber (@PathParam("idUser") Integer idUser,
			@QueryParam("sdate") String sdate,
			@QueryParam("edate") String edate) {
		Response result = null;
		getConnection();
		try {		
			if (connection.isClosed() == true) {
				return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		result = DatabaseQuery.getPostNumber(connection, idUser, sdate, edate);		
		return result;
	}
	
	@GET
	@Path("/users/{idUser}/friends/posts")
	@Produces(MediaType.TEXT_XML)
	@Consumes(MediaType.TEXT_XML)
	public static Response friendPosts (@PathParam("idUser") Integer idUser, @QueryParam("postBody") String postBody,
			@QueryParam("limit") Integer limit,
			@QueryParam("offset") Integer offset,
			@QueryParam("sdate") String sdate,
			@QueryParam("edate") String edate) {
		Response result = null;
		getConnection();
		try {		
			if (connection.isClosed() == true) {
				return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		result = DatabaseQuery.getFriendPosts(connection, idUser, postBody, limit, offset, sdate, edate);
		if (result.getStatus() != 200) {
			result = Response.status(Response.Status.BAD_REQUEST).build();
		}
		return result;
	}
}
