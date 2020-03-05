package model;


import java.sql.Date;

import javax.persistence.*;

/**
 * Model class for Persons. Used in the creation of the database table for Persons through Hibernate.
 */
@Entity
@Table(name="Person")
public class Person {

	@Id
	@GeneratedValue
	@Column
	private int person_id;
	
	@Column(length=50)
	private String name;
	
	@Column(nullable=true)
	private Date birthday;
	
	@Column(length=50, nullable=true)
	private String email;
	
	/**
	 * Constructor to create people without parameters.
	 */
	public Person() {
		
	}
	
	/**
	 * Constructor to create people
	 *@param name String the person's name
	 *@param birthday Date the person's birthday
	 *@param email String the person's e-mail address
	 */
	public Person(String name, Date birthday, String email) {
		this.name = name;
		this.birthday = birthday;
		this.email = email;
	}

	/**
	 * Function to fetch the id number of the person from the database.
	 * @return person_id the id number of the person in the database
	 */
	public int getPerson_id() {
		return person_id;
	}

	/**
	 * Function to register the id number of the person into the database
	 * @param person_id int the id number of the person in the database
	 */
	public void setPerson_id(int person_id) {
		this.person_id = person_id;
	}

	/**
	 * Function to fetch the name of the person from the database.
	 * @return name the name of the person in the database
	 */
	public String getName() {
		return name;
	}

	/**
	 * Function to register the name of the person into the database
	 * @param name String the id number of the person in the database
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Function to fetch the birthday of the person from the database.
	 * @return birthday the birthday of the person in the database
	 */
	public Date getBirthday() {
		return birthday;
	}

	/**
	 * Function to register the birthday of the person into the database
	 * @param birthday Date the id number of the person in the database
	 */
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	/**
	 * Function to fetch the e-mail of the person from the database.
	 * @return email the e-mail address of the person in the database
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * Function to register the e-mail of the person into the database
	 * @param email String the e-mail address of the person in the database
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	
	@Override
	public String toString() {
		return this.name;
	}
	
}
