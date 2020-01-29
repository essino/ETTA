package model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="Calendar")
public class Calendar {

	@Column(name="name")
	private String name;


	public Calendar() {

	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}