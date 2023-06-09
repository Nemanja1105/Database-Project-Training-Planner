package dao;

import java.sql.SQLException;
import java.util.List;

import models.Exercise;
import models.ExerciseCategory;

public interface ExerciseDAO {
	
	List<Exercise> findAll()throws SQLException;
	Exercise insert(Exercise e)throws SQLException;
	boolean update(Exercise e)throws SQLException;
	boolean delete(Exercise e)throws SQLException;
	List<Exercise> findByCategory(ExerciseCategory category)throws SQLException;

}
