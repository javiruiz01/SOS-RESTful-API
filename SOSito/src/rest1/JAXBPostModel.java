package rest1;

import java.util.Date;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="Post")
public class JAXBPostModel {
	private Integer idPost;
	private String postBody;
	private String creationDate;
	private Integer user;
	
	
	@XmlElement(name="idPost")
	public Integer getIdPost() {
		return idPost;
	}
	
	@XmlElement(name="postBody")
	public String getPostBody() {
		return postBody;
	}
	
	@XmlElement(name="creationDate")
	public String getCreationDate() {
		return creationDate;
	}
	
	@XmlElement(name="user")
	public Integer getUser() {
		return user;
	}
	
	public void setIdPost(Integer idPost) {
		this.idPost = idPost;
	}
	
	public void setPostBody(String postBody) {
		this.postBody = postBody;
	}
	
	public void setCreationDate(String creationDate) {
		this.creationDate = creationDate;
	}
	
	public void setUser (Integer user) {
		this.user = user;
	}
}
