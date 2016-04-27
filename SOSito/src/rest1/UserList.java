package rest1;

import java.util.List;
import javax.xml.bind.annotation.XmlRootElement;

public class UserList {

	private List<User> l;
	
	public UserList(){
		
	}
	
	public UserList (List<User> l){
		this.l = l;
	}
	
	public List<User> getL(){
		return l;
	}
	
	public void setL (List<User> l){
		this.l = l;
	}
	
}
