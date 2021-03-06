package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;


public class FriendTable {
	// Initialisation
	static 
	{
		try {
			
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	private Connection getConnection() throws SQLException
	{
		String un = "root";
		String pw = "";
		String url = "jdbc:mysql://localhost:3306/talkwithme";
		return DriverManager.getConnection(url, un, pw);
	}
	
	public List<User> selectFriends(String username){
		try {
			Connection connection = getConnection();
			String sql = 
			"SELECT `username`, `fullname`, `status`, `port`, `ip` " +
			"FROM `talkwithme`.`friendrelation` join `talkwithme`.`user` on `user`.`username` = `friendrelation`.`UserName2` " +
			"WHERE userName1 = ?";
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, username);
			ResultSet resultSet = preparedStatement.executeQuery();
			// Return
			List<User> ret = new LinkedList<User>();
			while(resultSet.next()){
				String friendUsername = resultSet.getString("Username");
				String fullname = resultSet.getString("FullName");
				boolean status = (resultSet.getInt("Status") == 0) ? true : false;
				String ip = resultSet.getString("IP");
				int port = resultSet.getInt("Port");
				User user = new User(friendUsername, fullname, ip, port, status);
				ret.add(user);
			}
			return ret;
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
