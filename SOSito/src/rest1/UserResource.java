package rest1;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriInfo;
import javax.xml.bind.JAXBElement;
import java.sql.*;

@Path("usuarios")

public class UserResource {

	@Context
	UriInfo uriInfo;
	@Context
	Request request;
	String id;
	
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

	@Path("{user}")
	@GET
	@Produces({MediaType.APPLICATION_XML})
	public Response getUser(@PathParam("user") String id){
		Response res;
		User user = null;
		/*if(UserDao.getInstance().getModel().containsKey(id)){
			user = UserDao.getInstance().getModel().get(id);
			res = Response.ok(user).build();
		}else{
			res = Response.status(Response.Status.NOT_FOUND).build();
		}*/
		Statement sentence = null;
		String query =	"SELECT * FROM USER u "
				+ "WHERE u.idUser=" + id;
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
				user.setUsername(username);
				user.setPostNumber(postNumber);
				user.setName(name);
				user.setLastname(lastname);
				user.setGender(gender);
				user.setMail(mail);
				user.setPhone(phone);
			}
			sentence.close();
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		res = Response.ok(user).build();
		return res;
	}

	@PUT
	@Path ("{user}")
	@Consumes(MediaType.APPLICATION_XML)
	public Response putUser(JAXBElement<User> user){
		User c = user.getValue();
		return putAndGetResponse(c);
	}
	
	private Response putAndGetResponse(User user){
		Response res;
		if(UserDao.getInstance().getModel().containsKey(user.getIdUser())){
			res = Response.noContent().build();
		}else{
			UserDao.getInstance().getModel().put(user.getIdUser(), user);
			res = Response.status(Status.CREATED).header("Location", uriInfo.getAbsolutePath().toString()).build();
		}
		return res;
	}
	
	
}
