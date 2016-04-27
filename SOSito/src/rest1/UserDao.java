package rest1;

import java.util.HashMap;
import java.util.Map;

public class UserDao {

	private Map<String, User> contentProvider = new HashMap<>();
	
	private static UserDao instance = null;
	
	private UserDao(){
		User user = new User("7", "ipinuela");
		user.setPostNumber(30);
		user.setName("irene");
		user.setLastname("pinuela");
		user.setGender("female");
		user.setMail("mail");
		user.setPhone("phone");
		
		
		contentProvider.put("1", user);
		user = new User("8", "bsanchez");
		user.setPostNumber(30);
		user.setName("beatriz");
		user.setLastname("sanchez");
		user.setGender("female");
		user.setMail("mail");
		user.setPhone("phone");
	}
	
	public Map<String, User> getModel(){
		return contentProvider;
		
	}
	
	public static UserDao getInstance(){
		if (instance == null){
			instance = new UserDao();
		}
		return instance;
	}
}
