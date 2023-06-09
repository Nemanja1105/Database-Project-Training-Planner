package dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import dao.AuthDAO;
import util.ConnectionPool;
import util.DBUtil;

public class AuthDAOImpl implements AuthDAO {

	@Override
	public boolean login(String username, String password) throws SQLException {
		String query = "select check_login(?,?)";
		boolean status=false;

		var connectionPool = ConnectionPool.getInstance();
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try {
			connection=connectionPool.checkOut();
			statement = connection.prepareStatement(query);
			statement.setString(1, username);
			statement.setString(2, password);
			resultSet = statement.executeQuery();
			if(resultSet.next())
				status=resultSet.getBoolean(1);
		} 
		finally {
			connectionPool.checkIn(connection);
			DBUtil.close(statement);
		}
		return status;
	}

}
