package model;


import java.sql.Date;

import javax.persistence.*;

/**
 * Model class for Persons. Used in the creation of the database table for Persons through Hibernate.
 */
@Entity
@Table(name="Person")
public class Person {

	/**
	 * Unique identifying id number for each person
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column
	private int person_id;
	
	/**
	 * Person's name
	 */
	@Column(length=50)
	private String name;
	
	/**
	 * Person's birthday
	 */
	@Column(nullable=true)
	private Date birthday;
	
	/**
	 * Person's email
	 */
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
	 * Constructor to create people
	 *@param name the person's name
	 */
	public Person(String name) {
		this.name = name;
	}

	/**
	 * Function to fetch the id number of the person
	 * @return person_id the id number of the person
	 */
	public int getPerson_id() {
		return person_id;
	}

	/**
	 * Function to register the id number of the person
	 * @param person_id the id number of the person
	 */
	public void setPerson_id(int person_id) {
		this.person_id = person_id;
	}

	/**
	 * Function to fetch the name of the person
	 * @return name the name of the person
	 */
	public String getName() {
		return name;
	}

	/**
	 * Function to register the name of the person
	 * @param name the name of the person
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Function to fetch the birthday of the person
	 * @return birthday the birthday of the person
	 */
	public Date getBirthday() {
		return birthday;
	}

	/**
	 * Function to register the birthday of the person
	 * @param birthday the birthday of the person
	 */
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	/**
	 * Function to fetch the e-mail of the person
	 * @return email the e-mail address of the person
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * Function to register the e-mail of the person
	 * @param email the e-mail address of the person
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	
	/**
	 * Function returning person's name
	 *@return String naming the person
	 */
	@Override
	public String toString() {
		return this.name;
	}
	
}
