package rest1;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="User")
public class JAXBUserModel {

	private Integer idUser;
	private String username;
	private Integer postNumber;
	private String name;
	private String lastname;
	private String gender;
	private String mail;
	private String phone;

	@XmlElement(name="idUser")
	public Integer getIdUser () {
		return 	idUser;
	}

	@XmlElement(name="username")
	public String getUsername () {
		return 	username;
	}

	@XmlElement(name="postNumber")
	public Integer getPostNumber () {
		return 	postNumber;
	}

	@XmlElement(name="name")
	public String getName () {
		return 	name;
	}

	@XmlElement(name="lastname")
	public String getLastname () {
		return 	lastname;
	}

	@XmlElement(name="gender")
	public String getGender () {
		return 	gender;
	}

	@XmlElement(name="mail")
	public String getMail () {
		return 	mail;
	}

	@XmlElement(name="phone")
	public String getPhone () {
		return 	phone;
	}

	public void setIdUser(Integer idUser) {
		this.idUser = idUser;
	}

	public void setUsername (String username){
		this.username = username;
	}

	public void setPostNumber(Integer postNumber) {
		this.postNumber = postNumber;
	}

	public void setName (String name) {
		this.name = name;
	}

	public void setLastname (String lastname) {
		this.lastname = lastname;
	}
	
	public void setGender (String gender) {
		this.gender = gender;
	}
	
	public void setMail (String mail) {
		this.mail = mail;
	}
	
	public void setPhone (String phone) {
		this.phone = phone;
	}

}
