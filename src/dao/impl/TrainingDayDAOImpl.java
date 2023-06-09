package dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import dao.TrainingDayDAO;
import models.TrainingDay;
import util.ConnectionPool;
import util.DBUtil;

public class TrainingDayDAOImpl implements TrainingDayDAO {

	@Override
	public TrainingDay findByDate(Date date, int planId) throws SQLException {
		String query = "select Trening_IdTreninga from dantreninga where PlanTreninga_IdPlanTreninga=? and Datum=?";
		TrainingDay result = null;

		var connectionPool = ConnectionPool.getInstance();
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try {
			connection = connectionPool.checkOut();
			statement = connection.prepareStatement(query);
			statement.setInt(1, planId);
			statement.setDate(2, new java.sql.Date(date.getTime()));
			resultSet = statement.executeQuery();
			if (resultSet.next())
				result = new TrainingDay(planId, date, resultSet.getInt(1));
		} finally {
			connectionPool.checkIn(connection);
			DBUtil.close(statement);
		}
		return result;
	}

	@Override
	public boolean insert(TrainingDay trainingDay) throws SQLException {
		String query = "insert into dantreninga values(?,?,?)";
		boolean status = false;

		var connectionPool = ConnectionPool.getInstance();
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try {
			connection = connectionPool.checkOut();
			statement = connection.prepareStatement(query);
			statement.setInt(1, trainingDay.getPlanId());
			statement.setDate(2, new java.sql.Date(trainingDay.getDate().getTime()));
			statement.setInt(3, trainingDay.getTrainingId());
			status = statement.executeUpdate() == 1;
		} finally {
			connectionPool.checkIn(connection);
			DBUtil.close(statement);
		}
		return status;
	}

	@Override
	public boolean update(TrainingDay trainingDay) throws SQLException {
		String query = "update dantreninga set Trening_IdTreninga=? where PlanTreninga_IdPlanTreninga=? and Datum=?";
		boolean status = false;

		var connectionPool = ConnectionPool.getInstance();
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try {
			connection = connectionPool.checkOut();
			statement = connection.prepareStatement(query);
			statement.setInt(1, trainingDay.getTrainingId());
			statement.setInt(2, trainingDay.getPlanId());
			statement.setDate(3, new java.sql.Date(trainingDay.getDate().getTime()));
			status = statement.executeUpdate() == 1;
		} finally {
			connectionPool.checkIn(connection);
			DBUtil.close(statement);
		}
		return status;
	}

}
