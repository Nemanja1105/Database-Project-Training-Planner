package dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dao.EquipmentDAO;
import models.Equipment;
import models.EquipmentDTO;
import models.Exercise;
import models.ExerciseCategory;
import models.enums.Difficulty;
import util.ConnectionPool;
import util.DBUtil;

public class EquipmentDAOImpl implements EquipmentDAO {

	@Override
	public List<Equipment> findAll()throws SQLException {
		String query="select * from rekvizit";

		var connectionPool = ConnectionPool.getInstance();
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		List<Equipment> results = new ArrayList<>();
		try {
			connection = connectionPool.checkOut();
			statement = connection.prepareStatement(query);
			resultSet = statement.executeQuery();
			while (resultSet.next())
				results.add(new Equipment(resultSet.getLong(1),resultSet.getString(2),resultSet.getString(3)));
		} finally {
			connectionPool.checkIn(connection);
			DBUtil.close(statement, resultSet);
		}

		return results;
	}

}
