package dao.impl;

import java.lang.invoke.VarHandle;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;


import dao.ExerciseDAO;
import models.Equipment;
import models.Exercise;
import models.ExerciseCategory;
import models.enums.Difficulty;
import util.ConnectionPool;
import util.DBUtil;

public class ExerciseDAOImpl implements ExerciseDAO {

	@Override
	public List<Exercise> findAll() throws SQLException {
		String query = "select * from vjezba_view";

		var connectionPool = ConnectionPool.getInstance();
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		List<Exercise> results = new ArrayList<>();
		try {
			connection = connectionPool.checkOut();
			statement = connection.prepareStatement(query);
			resultSet = statement.executeQuery();
			while (resultSet.next())
				results.add(new Exercise(resultSet.getLong(1), resultSet.getString(2), resultSet.getString(3),
						Difficulty.values()[resultSet.getByte(4)], new Equipment(resultSet.getString(6)),
						new ExerciseCategory(resultSet.getString(5))));
		} finally {
			connectionPool.checkIn(connection);
			DBUtil.close(statement, resultSet);
		}

		return results;
	}

	@Override
	public Exercise insert(Exercise e)throws SQLException {

		String query="{call vjezba_insert(?,?,?,?,?,?)}";

		var connectionPool = ConnectionPool.getInstance();
		Connection connection = null;
		CallableStatement statement=null;
		try {
			connection=connectionPool.checkOut();
			statement=connection.prepareCall(query);
			statement.setString(1,e.getName());
			statement.setString(2, e.getDescription());
			statement.setByte(3, (byte)e.getDifficulty().ordinal());
			statement.setString(4, e.getCategory().getName());
			if(e.getEquipment()!=null)
				statement.setInt(5,(int) e.getEquipment().getId());
			else 
				statement.setNull(5,Types.INTEGER);
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
	public boolean update(Exercise e) throws SQLException {
		String query="{call vjezba_update(?,?,?,?,?,?)}";
		boolean status=false;
		var connectionPool = ConnectionPool.getInstance();
		Connection connection = null;
		CallableStatement statement=null;
		try {
			connection=connectionPool.checkOut();
			statement=connection.prepareCall(query);
			statement.setInt(1, (int)e.getId());
			statement.setString(2,e.getName());
			statement.setString(3, e.getDescription());
			statement.setByte(4, (byte)e.getDifficulty().ordinal());
			statement.setString(5, e.getCategory().getName());
			if(e.getEquipment()!=null)
				statement.setInt(6,(int) e.getEquipment().getId());
			else 
				statement.setNull(6,Types.INTEGER);
			status=statement.executeUpdate()==1;	
		} 
		finally {
			connectionPool.checkIn(connection);
			DBUtil.close(statement);
		}
		return status;
	}

	@Override
	public boolean delete(Exercise e) throws SQLException {
		String query = "delete from vjezba where IdVjezbe=?";
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
	public List<Exercise> findByCategory(ExerciseCategory category) throws SQLException {
		return null;
	}

}
