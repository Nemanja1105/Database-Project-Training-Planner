package dao;

import java.sql.SQLException;
import java.util.List;

import models.ExerciseCategory;

public interface ExerciseCategoryDAO {
	List<ExerciseCategory> findAll()throws SQLException;

}
