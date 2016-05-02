package rest1;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="User")
public class JAXBUserModel {

	private Integer idUser;
	private String username;
	private String name;
	private String lastname;
	private String gender;
	private String mail;
	private String phone;

	@XmlElement(name="idUser")
	public Integer getIdUser () {
		return 	idUser;
	}
	
	public void setIdUser(Integer idUser) {
		this.idUser = idUser;
	}

	@XmlElement(name="username")
	public String getUsername () {
		return 	username;
	}
	
	public void setUsername (String username){
		this.username = username;
	}

	@XmlElement(name="name")
	public String getName () {
		return 	name;
	}
	
	public void setName (String name) {
		this.name = name;
	}

	@XmlElement(name="lastname")
	public String getLastname () {
		return 	lastname;
	}
	
	public void setLastname (String lastname) {
		this.lastname = lastname;
	}

	@XmlElement(name="gender")
	public String getGender () {
		return 	gender;
	}
	
	public void setGender (String gender) {
		this.gender = gender;
	}

	@XmlElement(name="mail")
	public String getMail () {
		return 	mail;
	}
	
	public void setMail (String mail) {
		this.mail = mail;
	}

	@XmlElement(name="phone")
	public String getPhone () {
		return 	phone;
	}
	
	public void setPhone (String phone) {
		this.phone = phone;
	}

}
