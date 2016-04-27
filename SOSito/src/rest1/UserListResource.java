package rest1;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.UriInfo;

@Path("/users")
public class UserListResource {
	@Context
	UriInfo uriInfo;
	@Context
	Request request;
	
	@GET
	@Produces(MediaType.TEXT_XML)
	public UserList getUsersBrowser(){
		List<User> users = new ArrayList<User>();
		users.addAll(UserDao.getInstance().getModel().values());
		return new UserList(users);
	}
	
	@POST
	@Produces(MediaType.TEXT_HTML)
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public void newUser(@FormParam("idUser") String id, 
			@FormParam("username") String username,
			@FormParam("postNumber") Integer postNumber,
			@FormParam("name") String name,
			@FormParam("lastname") String lastname,
			@FormParam("gender") String gender,
			@FormParam("mail") String mail,
			@FormParam("phone") String phone,
			@Context HttpServletResponse servletResponse) throws IOException{
		User user = new User (id,username);
		if(postNumber != null){
			user.setPostNumber(postNumber);
		}
		if(name != null){
			user.setName(name);
		}
		if(lastname != null){
			user.setLastname(lastname);
		}
		if(gender != null){
			user.setGender(gender);
		}
		if(mail != null){
			user.setMail(mail);
		}
		if(phone != null){
			user.setPhone(phone);
		}
		
		UserDao.getInstance().getModel().put(id, user);
		
		servletResponse.sendRedirect("../create_todo.html");
	}
	
	

}
