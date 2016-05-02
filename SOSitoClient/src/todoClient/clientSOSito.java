package todoClient;

import java.net.URI;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import org.glassfish.jersey.client.ClientConfig;

import rest1.JAXBUserModel;
import rest1.JAXBPostModel;
import rest1.JAXBFriendModel;

public class clientSOSito {

	public static void main (String[] args) {

		ClientConfig config = new ClientConfig();
		Client client = ClientBuilder.newClient(config);
		WebTarget target = client.target(getBaseURI());


		// Operaciones a realizar:
		// 1) Crear un usuario nuevo:

		JAXBUserModel newUser = new JAXBUserModel();
		newUser.setIdUser(10);
		newUser.setUsername("newUser");
		newUser.setName("New");
		newUser.setLastname("User");
		newUser.setGender("female");
		newUser.setMail("gmail");
		newUser.setPhone("number");
		Response response1 = target.path("users")
				.path("add").request()
				.accept(MediaType.TEXT_XML)
				.post(Entity.entity(newUser, MediaType.TEXT_XML));
		System.out.println(response1.getStatus());

		// 2) Publicar un Post nuevo:

		JAXBPostModel newPost = new JAXBPostModel();
		newPost.setIdPost(10);
		newPost.setPostBody("Post from JAVA client");
		newPost.setCreationDate("2016-04-27");
		newPost.setUser(5);
		Response response2 = target.path("posts").
				path("add").request().
				post(Entity.entity(newPost, MediaType.TEXT_XML));
		System.out.println(response2.getStatus());

		// 3) Obtener mis posts usando los filtros disponibles
		// 		Suponemos que somos el usuario con idUser = 1

		Response response3 = target.path("users").
				path("1").path("posts")
				.queryParam("sdate", "2016-04-01")
				.queryParam("edate",	"2016-04-09")
				.request().accept(MediaType.TEXT_XML).get();
		System.out.println(response3.readEntity(String.class));

		// 4) Modificar un post:

		JAXBPostModel modifyPost = new JAXBPostModel();
		modifyPost.setPostBody("Modifying this post");
		Response response4 = target.path("posts")
				.path("11").request()
				.put(Entity.entity(modifyPost, MediaType.TEXT_XML));
		System.out.println(response4);

		// 5) Borrar un post:

		JAXBPostModel postDelete = new JAXBPostModel();
		postDelete.setIdPost(11);
		Response response5 = target.path("posts")
				.path("11").path("delete")
				.request().accept(MediaType.TEXT_XML)
				.post(Entity.entity(postDelete, MediaType.TEXT_XML));
		System.out.println(response5);

		// 6) Buscar posibles amigos entre los usuarios:
		// 		Suponemos que buscamos al usuario por el nombre: Javier

		Response response6 = target.path("users").path("javier").request().get();
		System.out.println(response6.readEntity(String.class));

		// 7) Agregar un amigo:
		// 		Suponemos que hacemos que el usuario idUser = 7 sea amigo de idUser = 2

		JAXBFriendModel addfriend = new JAXBFriendModel();
		addfriend.setIdFriend(7);
		Response response7 = target.path("users")
				.path("2").path("friends")
				.path("add").request()
				.post(Entity.entity(addfriend, MediaType.TEXT_XML));
		System.out.println(response7);

		// 8) Eliminar un amigo:
		// 		Suponemos que borramos el amigo que hemos agregado en el apartado anterior

		JAXBFriendModel deleteFriend = new JAXBFriendModel();
		deleteFriend.setIdFriend(7);
		Response response8 = target.path("users")
				.path("2").path("friends")
				.path("delete").request()
				.post(Entity.entity(deleteFriend, MediaType.TEXT_XML));
		System.out.println(response8);

		// 9) Obtener la lista de amigos usando los filtros disponibles:
		// 		Suponemos que queremos los amigos del usuario idUser = 1

		Response response9 = target.path("users").
				path("1").path("friends").
				queryParam("limit", 2).queryParam("offset", 1).
				request().accept(MediaType.TEXT_XML).get();
		System.out.println(response9.readEntity(String.class));

		// 10) Consultar número de posts publicados por mi en un periodo:

		Response response10 = target.path("users")
				.path("1").path("nposts")
				.queryParam("sdate", "2016-04-01")
				.queryParam("edate", "2016-04-09")
				.request().accept(MediaType.TEXT_XML).get();
		System.out.println(response10.readEntity(String.class));

		// 11) Obtener la lista de usuarios:

		Response response11 = target.path("users")
				.request().accept(MediaType.TEXT_XML).get();
		System.out.println(response11.readEntity(String.class));

		// 12) Modificar los datos de nuestro perfil:
		// 		Suponemos que solo modificamos el nombre de usuario, el correo electronico y el telefono

		JAXBUserModel modifyUser = new JAXBUserModel();
		modifyUser.setUsername("jruiz");
		modifyUser.setMail("j.rcalle@alumnos.upm.es");
		modifyUser.setPhone("608911616");
		Response response12 = target.path("users")
				.path("1").request()
				.post(Entity.entity(modifyUser, MediaType.TEXT_XML));
		System.out.println(response12);

		// 13) Darse de baja de la red social:
		// 		Suponemos que damos de baja al usuario idUser = 6

		JAXBUserModel userDelete = new JAXBUserModel();
		userDelete.setIdUser(6);
		Response response13 = target.path("users")
				.path("6").path("delete").request()
				.post(Entity.entity(userDelete, MediaType.TEXT_XML));
		System.out.println(response13);

		// 14) Obtener la lista de posts publicados por un amigo que contenga un determinado texto:

		Response response14 = target.path("users")
				.path("1").path("friends")
				.path("posts").queryParam("limit", 5)
				.queryParam("postBody", "This is")
				.queryParam("offset", 1)
				.queryParam("sdate", "2016-04-01")
				.queryParam("edate", "2016-04-15")
				.request().accept(MediaType.TEXT_XML).get();
		System.out.println(response14.readEntity(String.class));		
	}

	private static URI getBaseURI() {
		return UriBuilder.fromUri("http://localhost:8080/SOSito/api/v1").build();
	}
}
