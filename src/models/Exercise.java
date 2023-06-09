package models;

import java.util.List;
import java.util.Objects;

import models.enums.Difficulty;

public class Exercise {
	private long id;
	private String name;
	private String description;
	private Difficulty difficulty;
	private Equipment equipment;
	private ExerciseCategory category;
	
	public Exercise() {
		super();
	}

	public Exercise(long id, String name, String description, Difficulty difficulty, Equipment equipment,
			ExerciseCategory category) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
		this.difficulty = difficulty;
		this.equipment = equipment;
		this.category = category;
	}

	public Exercise(String name, String description, Difficulty difficulty, Equipment equipment,
			ExerciseCategory category) {
		super();
		this.name = name;
		this.description = description;
		this.difficulty = difficulty;
		this.equipment = equipment;
		this.category = category;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
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

	public Difficulty getDifficulty() {
		return difficulty;
	}

	public void setDifficulty(Difficulty difficulty) {
		this.difficulty = difficulty;
	}

	public Equipment getEquipment() {
		return equipment;
	}

	public void setEquipment(Equipment equipment) {
		this.equipment = equipment;
	}

	public ExerciseCategory getCategory() {
		return category;
	}

	public void setCategory(ExerciseCategory category) {
		this.category = category;
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
		Exercise other = (Exercise) obj;
		return id == other.id;
	}

	@Override
	public String toString() {
		return "Exercise [id=" + id + ", name=" + name + ", description=" + description + ", difficulty=" + difficulty
				+ ", equipment=" + equipment.getName() + ", category=" + category.getName() + "]";
	}
	
	
}
