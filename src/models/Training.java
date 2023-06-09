package models;

import java.util.Objects;

import models.enums.Difficulty;

public class Training {
	//"Id", "Name", "Description", "Duration", "Difficulty"
	private int id;
	private String name;
	private String description;
	private int duration;
	private Difficulty difficulty;
	private int creatorId;
	
	public Training() {
		super();
	}
	
	public Training(int id, String name, String description, int dudation, Difficulty difficulty,int creatorId) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
		this.duration = dudation;
		this.difficulty = difficulty;
		this.creatorId=creatorId;
	}

	public Training(String name, String description, int dudation, Difficulty difficulty,int creatorId) {
		super();
		this.name = name;
		this.description = description;
		this.duration = dudation;
		this.difficulty = difficulty;
		this.creatorId=creatorId;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(int dudation) {
		this.duration = dudation;
	}

	public Difficulty getDifficulty() {
		return difficulty;
	}

	public void setDifficulty(Difficulty difficulty) {
		this.difficulty = difficulty;
	}
	
	public int getCreatorId() {
		return this.creatorId;
	}
	
	public void setCreatorId(int creatorId) {
		this.creatorId=creatorId;
	}

	@Override
	public int hashCode() {
		return Objects.hash(duration);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Training other = (Training) obj;
		return duration == other.duration;
	}
	
	
	
	
	
	

}
