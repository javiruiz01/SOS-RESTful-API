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

public class clientSOSito {

	public static void main (String[] args) {

		ClientConfig config = new ClientConfig();
		Client client = ClientBuilder.newClient(config);
		WebTarget target = client.target(getBaseURI());

		System.out.println("This will be the path: " + target.path("users"));
		
		JAXBUserModel user = new JAXBUserModel();
		user.setIdUser(7);
		user.setUsername("ipinuela");
		user.setPostNumber(30);
		user.setName("Irene");
		user.setLastname("Pinuela");
		user.setGender("Female");
		user.setMail("gmail");
		user.setPhone("3523");
		System.out.println(target.path("user").path("add").request().accept(MediaType.TEXT_XML).post(Entity.entity(user, MediaType.TEXT_XML)));
	}

	private static URI getBaseURI() {
		return UriBuilder.fromUri("http://localhost:8080/SOSito/api/v1").build();
	}
}
