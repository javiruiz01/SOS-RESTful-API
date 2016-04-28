package rest1;

import javax.ws.rs.core.Response;
import java.sql.*;
import java.util.Date;

public class DatabaseQuery {
	
	public static Response getUsers (Connection connection) {
		Statement sentence = null;
		String query =	"SELECT * FROM USER";
		String result = "<?xml version=\"1.0\"?>\n<Users>\n";

		try {
			sentence = connection.createStatement();
			ResultSet rs = sentence.executeQuery(query);
			while (rs.next()) {
				String username = rs.getString("username");
				String name = rs.getString("name");
				String lastname = rs.getString("lastname");
				String gender = rs.getString("gender");
				String mail = rs.getString("mail");
				String phone = rs.getString("phone");
				result += "<User>\n"
						+ "<Username>" + username + "</Username>\n"
						+ "<Name>" + name + "</Name>\n" 
						+ "<LastName>" + lastname + "</LastName>\n"
						+ "<Gender>" + gender + "</Gender>\n"
						+ "<Mail>" + mail + "</Mail>\n"
						+ "<Phone>" + phone + "</Phone>\n"
						+ "</User>\n";
			}
			sentence.close();
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		result += "</Users>";
		return Response.status(Response.Status.OK).entity(result).build();
	}

	public static Response getPosts(Connection connection, Integer idUser, Integer limit, Integer offset, String sdate, String edate) {
		PreparedStatement sentence = null;
		String result = "<?xml version=\"1.0\"?>\n<Posts>\n";
		String query = "SELECT * \n" 
				+ "FROM POST p, USER u \n"
				+ "WHERE u.idUser = ? \n"
				+ "AND p.user = u.idUser\n";
		if (sdate != null && edate != null) {
			query += "AND p.creationDate BETWEEN '" + sdate + "' AND '" + edate + "' \n";
		} else {
			if (sdate != null) {
				query += "AND p.creationDate >= '" + sdate + "' \n";
			}
			if (edate != null) {
				query += "AND p.creationDate <= '" + edate + "' \n";
			}
		}
		if (limit != null) {
			query += "LIMIT " + limit + " \n";
			if (offset != null) {
				query += "OFFSET " + offset + " \n";
			}
		}
		try {
			sentence = connection.prepareStatement(query);
			sentence.setInt(1, idUser);
			ResultSet rs = sentence.executeQuery();
			while(rs.next()) {
				String postBody = rs.getString("postBody");
				Date date = rs.getDate("creationDate");
				result += "<Post>" + postBody + "</Post>\n"
						+ "<CreationDate>" + date + "</CreationDate>\n";
			}
			sentence.close();
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		result += "</Posts>";
		return Response.status(Response.Status.OK).entity(result).build();
	}
	
	public static Response getPostNumber (Connection connection, Integer idUser, String sdate, String edate) {
		String number = "<?xml version=\"1.0\"?>\n<POSTS>\n";
		Statement sentence = null;
		String query = "SELECT COUNT(user) count\n"
				+ "FROM POST p\n"
				+ "WHERE p.user = " + idUser + "\n";
		if (sdate != null && edate != null) {
			query += "AND p.creationDate BETWEEN '" + sdate + "' AND '" + edate + "' \n";
		} else {
			if (sdate != null) {
				query += "AND p.creationDate >= '" + sdate + "' \n";
			}
			if (edate != null) {
				query += "AND p.creationDate <= '" + edate + "' \n";
			}
		}
		try {
			sentence = connection.createStatement();
			ResultSet rs = sentence.executeQuery(query);
			rs.next();
			int count = rs.getInt("count");
			number += "<Number>" + count + "</Number>\n";
		} catch (SQLException e) {
			e.printStackTrace();
		}	
		number += "</POSTS>";
		return Response.status(Response.Status.OK).entity(number).build();
	}
	
	public static Response getFriends (Connection connection, Integer idUser, Integer limit, Integer offset) {
		PreparedStatement sentence = null;
		PreparedStatement sentenceFriend = null;
		String result = "<?xml version=\"1.0\"?>\n<Friends>\n";
		String query = "SELECT * \n" 
				+ "FROM ISFRIEND f, USER u \n"
				+ "WHERE f.user1 = ? \n"
				+ "AND f.user1 = u.idUser \n";
		if (limit != null) {
			query += "LIMIT " + limit + " \n";
			if (offset != null) {
				query += "OFFSET " + offset + " \n";
			}
		}

		String queryFriend = "SELECT * \n"
				+ "FROM USER u\n"
				+ "WHERE u.idUser = ?";
		try {
			sentence = connection.prepareStatement(query);
			sentence.setInt(1, idUser);
			ResultSet rs = sentence.executeQuery();
			while (rs.next()) {
				Integer friend = rs.getInt("user2");
				sentenceFriend = connection.prepareStatement(queryFriend);
				sentenceFriend.setInt(1, friend);
				ResultSet rsFriend = sentenceFriend.executeQuery();
				while (rsFriend.next()) {
					String name = rsFriend.getString("name");
					String lastname = rsFriend.getString("lastname");
					result += "<Friend>\n" 
							+ "<Name>" + name + "</Name>"
							+ "<LastName>" + lastname + "</LastName>"
							+ "</Friend>";
				}
				sentenceFriend.close();
				rsFriend.close();
			}
			sentence.close();
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		result += "</Friends>";
		return Response.status(Response.Status.OK).entity(result).build();
	}
	
	public static Response getFindUser (Connection connection, String name) {
		String result = "<?xml version=\"1.0\"?>\n<Users>\n";
		PreparedStatement sentence = null;
		String query = "SELECT * \n"
				+ "FROM USER u\n"
				+ "WHERE u.name = ? \n";

		try {
			sentence = connection.prepareStatement(query);
			sentence.setString(1, name);
			ResultSet rs = sentence.executeQuery();
			while (rs.next()) {
				String nameUser = rs.getString("name");
				String lastname = rs.getString("lastname");
				result += "<User>\n"
						+ "<Name>" + nameUser + "</Name>"
						+ "<Lastname>" + lastname + "</Lastname>\n"
						+ "</User>";
			}
			sentence.close();
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		result += "</Users>";
		return Response.status(Response.Status.OK).entity(result).build();
	}
	
	public static Response postCreateUser (Connection connection, Integer idUser, String username, String name,
			String lastname, String gender, String mail, String phone) {
		Statement sentence = null;
		String query = "INSERT INTO USER\n"
				+ "(idUser, username, name, lastname, gender, mail, phone)\n"
				+ "VALUES\n"
				+ "('" + idUser + "', '" + username   
				+ "', '" + name + "', '" + lastname + "', '" + gender  
				+ "', '" + mail + "', '" + phone + "');"  ;		
		try {
			sentence = connection.createStatement();
			int rs = sentence.executeUpdate(query);
			if (rs == 0) {
				return Response.status(Response.Status.BAD_REQUEST).build();
			}
			sentence.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return Response.status(Response.Status.CREATED).build();
	}
	
	public static Response postDeleteUser (Connection connection, Integer idUser) {
		Statement sentence = null;
		String query = "DELETE FROM USER\n"
				+ "WHERE idUser='" + idUser + "';";
		try {
			sentence = connection.createStatement();
			int rs = sentence.executeUpdate(query);
			if (rs != 0) {
				return Response.status(Response.Status.OK).build();
			}
			sentence.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return Response.status(Response.Status.BAD_REQUEST).build();
	}
	
	public static Response postAddPost (Connection connection, Integer idPost, String postBody, String creationDate, Integer user) {
		Statement sentence = null;
		String query = "INSERT INTO POST\n"
				+ "(idPost, postBody, creationDate, user)\n"
				+ "VALUES\n"
				+ "('" + idPost + "', '" + postBody + "', '" + creationDate + "', '" + user + "');";

		try {
			sentence = connection.createStatement();
			int rs = sentence.executeUpdate(query);
			if (rs != 0) {
				return Response.status(Response.Status.CREATED).build();
			}
			sentence.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return Response.status(Response.Status.BAD_REQUEST).build();
	}
	
	public static Response postDeletePost(Connection connection, Integer idPost) {
		Statement sentence = null;
		String query = "DELETE FROM POST\n"
				+ "WHERE idPost = " + idPost + ";";
		try {
			sentence = connection.createStatement();
			int rs = sentence.executeUpdate(query);
			if (rs!=0) {
				return Response.status(Response.Status.OK).build();
			}
			sentence.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return Response.status(Response.Status.BAD_REQUEST).build();
	}
	
	public static Response postAddFriend(Connection connection, Integer idUser, Integer idUserFriend) {
		Statement sentence1 = null;
		Statement sentence2 = null;
		Boolean allIsGood = false;
		String query1 = "INSERT INTO ISFRIEND\n"
				+ "(user1, user2)\n"
				+ "VALUES\n"
				+ "('" + idUser + "', '" + idUserFriend + "');\n";
		try {
			sentence1 = connection.createStatement();
			int rs = sentence1.executeUpdate(query1);
			if (rs != 0) {
				allIsGood = true;
			}
			sentence1.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		String query2 = "INSERT INTO ISFRIEND\n"
				+ "(user1, user2)\n"
				+ "VALUES\n"
				+ "('" + idUserFriend + "', '" + idUser + "');\n";
		try {
			sentence2 = connection.createStatement();
			int rs = sentence2.executeUpdate(query2);
			if (rs != 0 && allIsGood) {
				return Response.status(Response.Status.CREATED).build();
			}
			sentence2.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return Response.status(Response.Status.BAD_REQUEST).build();
	}
	
	public static Boolean postModifyUsername (Connection connection, Integer idUser, String username) {
		String query = "UPDATE USER u\n"
				+ "SET u.username = '" + username + "'\n"
				+ "WHERE u.idUser = " + idUser;
		Statement sentence = null;
		Boolean result = false;
		try {
			sentence = connection.createStatement();
			int rs = sentence.executeUpdate(query);
			if (rs != 0) {
				result = true;
			}
			sentence.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	public static Boolean postModifyName (Connection connection, Integer idUser, String name) {
		String query = "UPDATE USER u\n"
				+ "SET u.name = '" + name + "'\n"
				+ "WHERE u.idUser = " + idUser;
		Boolean result = false;
		Statement sentence = null;
		try {
			sentence = connection.createStatement();
			int rs = sentence.executeUpdate(query);
			if (rs != 0) {
				result = true;
			}
			sentence.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	public static Boolean postModifyLastname (Connection connection, Integer idUser, String lastname) {
		String query = "UPDATE USER u\n"
				+ "SET u.lastname = '" + lastname + "'\n"
				+ "WHERE u.idUser = " + idUser;
		Boolean result = false;
		Statement sentence = null;
		try {
			sentence = connection.createStatement();
			int rs = sentence.executeUpdate(query);
			if (rs != 0) {
				result = true;
			}
			sentence.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	public static Boolean postModifyGender (Connection connection, Integer idUser, String gender) {
		String query = "UPDATE USER u\n"
				+ "SET u.gender = '" + gender + "'\n"
				+ "WHERE u.idUser = " + idUser;
		Boolean result = false;
		Statement sentence = null;
		try {
			sentence = connection.createStatement();
			int rs = sentence.executeUpdate(query);
			if (rs != 0) {
				result = true;
			}
			sentence.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	public static Boolean postModifyMail (Connection connection, Integer idUser, String mail) {
		String query = "UPDATE USER u\n"
				+ "SET u.mail = '" + mail + "'\n"
				+ "WHERE u.idUser = " + idUser;
		Boolean result = false;
		Statement sentence = null;
		try {
			sentence = connection.createStatement();
			int rs = sentence.executeUpdate(query);
			if (rs != 0) {
				result = true;
			}
			sentence.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	public static Boolean postModifyPhone (Connection connection, Integer idUser, String phone) {
		String query = "UPDATE USER u\n"
				+ "SET u.phone = '" + phone + "'\n"
				+ "WHERE u.idUser = " + idUser;
		Boolean result = false;
		Statement sentence = null;
		try {
			sentence = connection.createStatement();
			int rs = sentence.executeUpdate(query);
			if (rs != 0) {
				result = true;
			}
			sentence.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	public static Response putModifyPost (Connection connection, Integer idPost, String postBody) {
		String query = "UPDATE POST p\n"
				+ "SET p.postBody = '" + postBody + "'\n"
				+ "WHERE p.idPost = " + idPost; 
		Statement sentence = null;
		try {
			sentence = connection.createStatement();
			int rs = sentence.executeUpdate(query);
			if (rs == 0) {
				return Response.status(Response.Status.BAD_REQUEST).build();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return Response.status(Response.Status.OK).build();
	}
	
	public static Response postDeleteFriend (Connection connection, Integer idUser, Integer idFriend) {
		String query1 = "DELETE FROM ISFRIEND \n"
				+ "WHERE user1 = " + idUser + "\n"
				+ "AND user2 = " + idFriend;
		String query2 = "DELETE FROM ISFRIEND\n"
				+ "WHERE user1 = " + idFriend + "\n"
				+ "AND user2 = " + idUser;
		Statement sentence1 = null;
		Statement sentence2 = null;
		try {
			sentence1 = connection.createStatement();
			int rs = sentence1.executeUpdate(query1);
			if (rs == 0) {
				return Response.status(Response.Status.BAD_REQUEST).build();
			}
			sentence1.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			sentence2 = connection.createStatement();
			int rs = sentence2.executeUpdate(query2);
			if (rs == 0) {
				return Response.status(Response.Status.BAD_REQUEST).build();
			}
			sentence2.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return Response.status(Response.Status.OK).build();
	}
	
	public static Response getFriendPosts (Connection connection, Integer idUser, String postBody, 
			Integer limit, Integer offset, String sdate, String edate){
		Statement sentenceFriend = null;
		String queryFriend = "SELECT user2\n"
				+ "FROM ISFRIEND f\n"
				+ "WHERE f.user1 = " + idUser;
		String queryPost = "SELECT *\n"
				+ "FROM POST p, USER u\n"
				+ "WHERE p.user = ?\n"
				+ "AND u.idUser = ?\n";
		if (sdate != null && edate != null) {
			queryPost += "AND p.creationDate BETWEEN '" + sdate + "' AND '" + edate + "' \n";
		} else {
			if (sdate != null) {
				queryPost += "AND p.creationDate >= '" + sdate + "' \n";
			}
			if (edate != null) {
				queryPost += "AND p.creationDate <= '" + edate + "' \n";
			}
		}
		if (postBody != null) {
			queryPost += "AND p.postBody LIKE '%" + postBody + "%'";
		}
		if (limit != null) {
			queryPost += "LIMIT " + limit + " \n";
			if (offset != null) {
				queryPost += "OFFSET " + offset + " \n";
			}
		}
		String subString = "";
		int counter = 0;
		PreparedStatement sentencePost = null;
		String xmlResult = "<?xml version=\"1.0\"?>\n<Posts>\n";
		try {
			sentenceFriend = connection.createStatement();
			ResultSet rsFriend = sentenceFriend.executeQuery(queryFriend);
			while (rsFriend.next()) {
				Integer myFriend = rsFriend.getInt("user2");				
				sentencePost = connection.prepareStatement(queryPost);
				sentencePost.setInt(1, myFriend);
				sentencePost.setInt(2, myFriend);
				ResultSet rsPost = sentencePost.executeQuery();
				subString += "<Friend>\n";
				while (rsPost.next()) {
					String body = rsPost.getString("postBody");
					String friendName = rsPost.getString("username");
					subString += "<Username>" + friendName + "</Username>\n"
							+ "<PostBody>" + body + "</PostBody>\n";
					counter += 1;
				}
				if (counter != 0) {
					subString += "</Friend>\n";
					xmlResult += subString;
				}
				subString = "";
				counter = 0;
				sentencePost.close();
				rsPost.close();
			}
			sentenceFriend.close();
			rsFriend.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		xmlResult += "</Posts>";
		return Response.status(Response.Status.OK).entity(xmlResult).build();
	}

}
