package main;

import java.sql.SQLException;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import dao.impl.AuthDAOImpl;
import dao.impl.ExerciseDAOImpl;
import dao.impl.TraingingSetDAOImpl;
import gui.CalendarFrame;
import gui.Exercises;
import gui.ExercisesUpdateInsert;
import gui.HomePage;
import gui.Login;
import gui.TrainingPanel;
import gui.TrainingCreator;
import gui.TrainingPlanner;
import models.EquipmentDTO;
import models.Exercise;
import models.ExerciseCategory;
import models.enums.Difficulty;
import util.ConnectionPool;

public class Main {
	
	public static ExecutorService threadPool=Executors.newCachedThreadPool();
	public static int PLAN_ID=2;
	public static int CLIENT_ID=1;
	
	public static void main(String[]args) throws SQLException
	{
		//new ExerciseDAOImpl().insert(new Exercise("Mrtvo dizanje", "opis", Difficulty.HARD,new EquipmentDTO(3, null),new ExerciseCategory("Ledja")));
		//Exercises exercises=new Exercises();
		//ExercisesUpdateInsert exercises=new ExercisesUpdateInsert(true);
		//TrainingCreator exercises=new TrainingCreator();
		//CalendarFrame exercises=new CalendarFrame();
		//CalendarItem exercises=new CalendarItem();
		//TrainingPlanner exercises=new TrainingPlanner();
		Login exercises=new Login();
		//HomePage exercises=new HomePage();
		//Training exercises=new Training();
		exercises.setVisible(true);
		
		
		
	}
}
