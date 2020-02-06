package model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="Category")

public class Category {
	@Id
	@GeneratedValue
	@Column(name="Category_id")
	private int Category_id;
	@Column(name="description", length=50)
	private String description;

	public int getCategory_id() {
		return Category_id;
	}

	public void setCategory_id(int Category_id) {
		this.Category_id = Category_id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Category() {
	
	}	
	
	public Category(String description) {
		this.description = description;
	}	

}

