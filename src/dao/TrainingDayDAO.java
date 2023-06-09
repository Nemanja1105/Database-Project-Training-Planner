package dao;

import java.sql.SQLException;
import java.util.Date;

import models.TrainingDay;

public interface TrainingDayDAO {
	TrainingDay findByDate(Date date,int planId)throws SQLException;
	boolean insert(TrainingDay trainingDay)throws SQLException;
	boolean update(TrainingDay trainingDay)throws SQLException;
}
