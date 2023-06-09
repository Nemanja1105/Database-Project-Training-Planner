package models;

import java.util.Objects;

public class TrainingSet
{
	//"Id", "Exercises","Repetition", "Series", "RestDuration", "Note"
	private int id;
	private int exerciseId;
	private String exerciseName;
	private int repetition;
	private int series;
	private int restDuration;
	private String note;
	
	public TrainingSet() {
		super();
	}

	public TrainingSet(int setId, int exerciseId, String exerciseName, int repetition, int series, int restDuration,
			String note) {
		super();
		this.id = setId;
		this.exerciseId = exerciseId;
		this.exerciseName = exerciseName;
		this.repetition = repetition;
		this.series = series;
		this.restDuration = restDuration;
		this.note = note;
	}

	public TrainingSet(String exerciseName, int repetition, int series, int restDuration, String note) {
		super();
		this.exerciseName = exerciseName;
		this.repetition = repetition;
		this.series = series;
		this.restDuration = restDuration;
		this.note = note;
	}

	public int getId() {
		return id;
	}

	public void setId(int setId) {
		this.id = setId;
	}

	public int getExerciseId() {
		return exerciseId;
	}

	public void setExerciseId(int exerciseId) {
		this.exerciseId = exerciseId;
	}

	public String getExerciseName() {
		return exerciseName;
	}

	public void setExerciseName(String exerciseName) {
		this.exerciseName = exerciseName;
	}

	public int getRepetition() {
		return repetition;
	}

	public void setRepetition(int repetition) {
		this.repetition = repetition;
	}

	public int getSeries() {
		return series;
	}

	public void setSeries(int series) {
		this.series = series;
	}

	public int getRestDuration() {
		return restDuration;
	}

	public void setRestDuration(int restDuration) {
		this.restDuration = restDuration;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TrainingSet other = (TrainingSet) obj;
		return id == other.id;
	}
	
	

	
	
	
	

}
