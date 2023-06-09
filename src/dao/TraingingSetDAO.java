package dao;

import java.sql.SQLException;
import java.util.List;

import models.TrainingSet;

public interface TraingingSetDAO {
	List<TrainingSet> findAllByTraingId(int id)throws SQLException;
	TrainingSet insert(TrainingSet e,int trainingId)throws SQLException;
	void insert(List<TrainingSet>sets,int trainingId)throws SQLException;
	boolean delete(TrainingSet e)throws SQLException;
	void update(List<TrainingSet> sets,int trainingId)throws SQLException;

}
