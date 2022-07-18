package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class UserDao {
	private static final String SELECT_USER_BY_USERNAME_PASSWORD = "select * from users where username = ? and password = ?";
	
	public static String login(String username, String password) throws NamingException, SQLException  {
		String result = null;
		Context ctx = new InitialContext();
		DataSource ds = (DataSource) ctx.lookup("java:/comp/env/jdbc/MyDB");
		Connection con = ds.getConnection();
		PreparedStatement pst = con.prepareStatement(SELECT_USER_BY_USERNAME_PASSWORD);
		pst.setString(1, username);
		pst.setString(2, password);
		ResultSet rs = pst.executeQuery();
		if (rs.next()) {
			result = rs.getString("name") + " " + rs.getString("surname");
		}
		rs.close();
		con.close();
		ctx.close();
		return result;
	}
}
