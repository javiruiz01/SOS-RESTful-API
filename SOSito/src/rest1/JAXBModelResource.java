package rest1;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/jaxb")
public class JAXBModelResource {
	/*@GET
	@Path("/hello")
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public JAXBModel getXML () {
		JAXBModel serializableXML = new JAXBModel();
		serializableXML.setName("Javier");
		serializableXML.setLastname("Ruiz");
		return serializableXML;
	}
	*/
}
