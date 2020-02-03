package model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="Groups")

public class Groups {
	
	@Id
	@Column(name="group_id")
	private int group_id;
	
	@Column(name="description")
	private String description;


public int getGroup_id() {
		return group_id;
	}


	public void setGroup_id(int group_id) {
		this.group_id = group_id;
	}


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}


public Groups() {
	
}

}


