package dao;

import java.sql.SQLException;
import java.util.List;

import models.Training;

public interface TrainingDAO {
	List<Training> findAll()throws SQLException;
	Training insert(Training e)throws SQLException;
	boolean delete(Training e)throws SQLException;
	boolean update(Training e)throws SQLException;
}
