package dao.impl;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import dao.TraingingSetDAO;
import models.TrainingSet;
import util.ConnectionPool;
import util.DBUtil;

public class TraingingSetDAOImpl implements TraingingSetDAO {

	@Override
	public List<TrainingSet> findAllByTraingId(int id) throws SQLException {

		String query = "{call dobaviSetoveZaTrening(?)}";

		var connectionPool = ConnectionPool.getInstance();
		Connection connection = null;
		CallableStatement statement = null;
		ResultSet resultSet = null;
		List<TrainingSet> result = new ArrayList<>();
		try {
			connection = connectionPool.checkOut();
			statement = connection.prepareCall(query);
			statement.setInt(1, id);
			resultSet = statement.executeQuery();
			while (resultSet.next()) {
				result.add(new TrainingSet(resultSet.getInt(1), resultSet.getInt(2), resultSet.getString(3),
						resultSet.getInt(4), resultSet.getInt(5), resultSet.getInt(6), resultSet.getString(7)));
			}
		} finally {
			connectionPool.checkIn(connection);
			DBUtil.close(statement, resultSet);
		}

		return result;
	}

	@Override
	public TrainingSet insert(TrainingSet e, int trainingId) throws SQLException {
		String query = "{call insertSet(?,?,?,?,?,?,?)}";

		var connectionPool = ConnectionPool.getInstance();
		Connection connection = null;
		CallableStatement statement = null;
		try {
			connection = connectionPool.checkOut();
			statement = connection.prepareCall(query);
			statement.setInt(1, e.getExerciseId());
			statement.setInt(2, e.getRepetition());
			statement.setInt(3, e.getSeries());
			statement.setInt(4, e.getRestDuration());
			statement.setString(5, e.getNote());
			statement.setInt(6, trainingId);
			statement.registerOutParameter(7, Types.INTEGER);
			statement.executeUpdate();
			var id = statement.getInt(7);
			e.setId(id);
		} finally {
			connectionPool.checkIn(connection);
			DBUtil.close(statement);
		}
		return e;
	}

	@Override
	public void insert(List<TrainingSet> sets, int trainingId) throws SQLException {
		String query = "{call insertSet(?,?,?,?,?,?,?)}";

		var connectionPool = ConnectionPool.getInstance();
		Connection connection = null;
		CallableStatement statement = null;
		try {
			connection = connectionPool.checkOut();
			connection.setAutoCommit(false);
			statement = connection.prepareCall(query);
			for (var e : sets) {
				statement.setInt(1, e.getExerciseId());
				statement.setInt(2, e.getRepetition());
				statement.setInt(3, e.getSeries());
				statement.setInt(4, e.getRestDuration());
				statement.setString(5, e.getNote());
				statement.setInt(6, trainingId);
				statement.registerOutParameter(7, Types.INTEGER);
				statement.executeUpdate();
				var id = statement.getInt(7);
				e.setId(id);
			}
			connection.commit();
		} catch (SQLException e) {
			if (connection != null)
				connection.rollback();
			throw e;
		} finally {
			connectionPool.checkIn(connection);
			DBUtil.close(statement);
		}
	}

	@Override
	public boolean delete(TrainingSet e) throws SQLException {
		String query = "delete from mydb.set where IdSeta=?";
		boolean status = false;

		var connectionPool = ConnectionPool.getInstance();
		Connection connection = null;
		PreparedStatement statement = null;
		try {
			connection = connectionPool.checkOut();
			statement = connection.prepareStatement(query);
			statement.setInt(1, (int) e.getId());
			status = statement.executeUpdate() == 1;
		} finally {
			connectionPool.checkIn(connection);
			DBUtil.close(statement);
		}
		return status;
	}

	@Override
	public void update(List<TrainingSet> sets,int trainingId) throws SQLException {
		String query1 = "{call insertSet(?,?,?,?,?,?,?)}";
		String query2 = "{call set_update(?,?,?,?,?,?)}";

		var connectionPool = ConnectionPool.getInstance();
		Connection connection = null;
		CallableStatement statement = null;
		try {
			connection = connectionPool.checkOut();
			connection.setAutoCommit(false);
			for (var e : sets) {
				if (e.getId() == 0) {
					statement = connection.prepareCall(query1);
					statement.setInt(1, e.getExerciseId());
					statement.setInt(2, e.getRepetition());
					statement.setInt(3, e.getSeries());
					statement.setInt(4, e.getRestDuration());
					statement.setString(5, e.getNote());
					statement.setInt(6, trainingId);
					statement.registerOutParameter(7, Types.INTEGER);
					statement.executeUpdate();
					var id = statement.getInt(7);
					e.setId(id);
				}
				else {
					statement = connection.prepareCall(query2);
					statement.setInt(1, e.getId());
					statement.setInt(2, e.getExerciseId());
					statement.setInt(3, e.getRepetition());
					statement.setInt(4, e.getSeries());
					statement.setInt(5, e.getRestDuration());
					statement.setString(6, e.getNote());
					statement.executeUpdate();
				}
			}
			connection.commit();
		} catch (SQLException e) {
			if (connection != null)
				connection.rollback();
			throw e;
		} finally {
			connectionPool.checkIn(connection);
			DBUtil.close(statement);
		}

	}

}
