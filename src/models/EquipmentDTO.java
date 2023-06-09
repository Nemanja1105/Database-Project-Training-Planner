package models;

import java.util.Objects;

public class EquipmentDTO {
	private long id;
	private String name;
	
	public EquipmentDTO() {
		super();
	}

	public EquipmentDTO(String name) {
		super();
		this.name = name;
	}
	
	

	public EquipmentDTO(long id, String name) {
		super();
		this.id = id;
		this.name = name;
	}

	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name=name;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
	
	

	
}
