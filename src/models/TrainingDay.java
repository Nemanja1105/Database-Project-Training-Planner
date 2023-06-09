package models;

import java.util.Date;

public class TrainingDay {
	private int planId;
	private Date date;
	private int trainingId;
	
	public TrainingDay(int planId,Date date) {
		super();
		this.planId=planId;
		this.date=date;
	}

	public TrainingDay(int planId, Date date, int trainingId) {
		super();
		this.planId = planId;
		this.date = date;
		this.trainingId = trainingId;
	}

	public int getPlanId() {
		return planId;
	}

	public void setPlanId(int planId) {
		this.planId = planId;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public int getTrainingId() {
		return trainingId;
	}

	public void setTrainingId(int trainingId) {
		this.trainingId = trainingId;
	}
	
	

}
