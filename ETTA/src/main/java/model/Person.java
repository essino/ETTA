package model;


import java.sql.Date;

import javax.persistence.*;

@Entity
@Table(name="Person")
public class Person {
	@Id
	@GeneratedValue
	@Column
	private int person_id;
	@Column(length=0)
	private String name;
	@Column(nullable=true)
	private Date birthday;
	@Column(length=50, nullable=true)
	private String email;
	
	public Person() {
		
	}
	
	public Person(String name, Date birthday, String email) {
		this.name = name;
		this.birthday = birthday;
		this.email = email;
	}

	public int getPerson_id() {
		return person_id;
	}

	public void setPerson_id(int person_id) {
		this.person_id = person_id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
}
