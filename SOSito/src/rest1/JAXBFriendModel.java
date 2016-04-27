package rest1;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="Friend")
public class JAXBFriendModel {
	private Integer idFriend;
	
	@XmlElement(name="idFriend")
	public Integer getIdFriend() {
		return idFriend;
	}
	
	public void setIdFriend (Integer idFriend) {
		this.idFriend = idFriend;
	}
}
