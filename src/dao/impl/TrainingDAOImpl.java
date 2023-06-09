package dao.impl;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import dao.TrainingDAO;
import models.Equipment;
import models.Exercise;
import models.ExerciseCategory;
import models.Training;
import models.enums.Difficulty;
import util.ConnectionPool;
import util.DBUtil;

public class TrainingDAOImpl implements TrainingDAO {

	@Override
	public List<Training> findAll() throws SQLException {
		String query = "select * from trening_view";

		var connectionPool = ConnectionPool.getInstance();
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		List<Training> results = new ArrayList<>();
		try {
			connection = connectionPool.checkOut();
			statement = connection.prepareStatement(query);
			resultSet = statement.executeQuery();
			while (resultSet.next())
				results.add(new Training(resultSet.getInt(1),resultSet.getString(2),resultSet.getString(3),resultSet.getInt(4),
						Difficulty.values()[resultSet.getByte(5)],resultSet.getInt(6)));
		} finally {
			connectionPool.checkIn(connection);
			DBUtil.close(statement, resultSet);
		}
		return results;
	}

	@Override
	public Training insert(Training e) throws SQLException {
		String query="{call trening_insert(?,?,?,?,?,?)}";

		var connectionPool = ConnectionPool.getInstance();
		Connection connection = null;
		CallableStatement statement=null;
		try {
			connection=connectionPool.checkOut();
			statement=connection.prepareCall(query);
			statement.setString(1,e.getName());
			statement.setString(2, e.getDescription());
			statement.setInt(3, e.getDuration());
			statement.setByte(4, (byte)e.getDifficulty().ordinal());
			statement.setInt(5, e.getCreatorId());
			statement.registerOutParameter(6, Types.INTEGER);
			statement.executeUpdate();
			var id=statement.getInt(6);
			e.setId(id);	
		} 
		finally {
			connectionPool.checkIn(connection);
			DBUtil.close(statement);
		}
		return e;
	}

	@Override
	public boolean delete(Training e) throws SQLException {
		String query = "delete from trening where IdTreninga=?";
		boolean status=false;
		
		var connectionPool = ConnectionPool.getInstance();
		Connection connection = null;
		PreparedStatement statement = null;
		try {
			connection=connectionPool.checkOut();
			statement=connection.prepareStatement(query);
			statement.setInt(1,(int) e.getId());
			status=statement.executeUpdate()==1;
		}
		finally {
			connectionPool.checkIn(connection);
			DBUtil.close(statement);
		}
		return status;
	}

	@Override
	public boolean update(Training e) throws SQLException {
		String query="{call trening_update(?,?,?,?,?)}";
		boolean status=false;
		var connectionPool = ConnectionPool.getInstance();
		Connection connection = null;
		CallableStatement statement=null;
		try {
			connection=connectionPool.checkOut();
			statement=connection.prepareCall(query);
			statement.setInt(1, e.getId());
			statement.setString(2, e.getName());
			statement.setString(3, e.getDescription());
			statement.setInt(4, e.getDuration());
			statement.setByte(5,(byte) e.getDifficulty().ordinal());
			status=statement.executeUpdate()==1;	
		} 
		finally {
			connectionPool.checkIn(connection);
			DBUtil.close(statement);
		}
		return status;
	}

}
