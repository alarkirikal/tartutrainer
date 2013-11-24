package com.tartutrainer.models;

public class Exercise {
	
	public String id;
	public String name;
	public String description;
	public int level;
	public int modality;
	public String muscles;
	public String equipment;
	public String labels;
	public String owned;
	public int category;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
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
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}
	public int getModality() {
		return modality;
	}
	public void setModality(int modality) {
		this.modality = modality;
	}
	public String getMuscles() {
		return muscles;
	}
	public void setMuscles(String muscles) {
		this.muscles = muscles;
	}
	public String getEquipment() {
		return equipment;
	}
	public void setEquipment(String equipment) {
		this.equipment = equipment;
	}
	public String getLabels() {
		return labels;
	}
	public void setLabels(String labels) {
		this.labels = labels;
	}
	public String getOwned() {
		return owned;
	}
	public void setOwned(String owned) {
		this.owned = owned;
	}
	public int getCategory() {
		return category;
	}
	public void setCategory(int category) {
		this.category = category;
	}

}
