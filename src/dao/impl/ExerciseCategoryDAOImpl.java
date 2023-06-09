package dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dao.ExerciseCategoryDAO;
import models.EquipmentDTO;
import models.Exercise;
import models.ExerciseCategory;
import models.enums.Difficulty;
import util.ConnectionPool;
import util.DBUtil;

public class ExerciseCategoryDAOImpl implements ExerciseCategoryDAO {

	@Override
	public List<ExerciseCategory> findAll()throws SQLException {
		String query="select * from kategorijavjezbe";
		
		var connectionPool = ConnectionPool.getInstance();
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		List<ExerciseCategory> results = new ArrayList<>();
		try {
			connection = connectionPool.checkOut();
			statement = connection.prepareStatement(query);
			resultSet = statement.executeQuery();
			while (resultSet.next())
				results.add(new ExerciseCategory(resultSet.getString(1)));
		} finally {
			connectionPool.checkIn(connection);
			DBUtil.close(statement, resultSet);
		}

		return results;
		
	}

}
