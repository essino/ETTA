package model;

import java.sql.Date;

import javax.persistence.*;

/**
 * Model class for Borrowed things. Used in the creation of the database table for Borrowed items through Hibernate.
 */
@Entity
@Table(name="borrowed_things")
public class BorrowedThing {
	
	@Id
	@GeneratedValue
	@Column(name="thing_id") 
	private int thing_id;
	
	@Column(name="description")
	private String description;
	
	@Column(name="dateBorrow")
	private Date dateBorrow;
	
	@Column(name="returnDate")
	private Date returnDate;
	
	@ManyToOne
	@JoinColumn(name="person_id")
	private int person_id;
	
	/**
	 * Constructor to create borrowed items. No parameters.
	 */
	public BorrowedThing() {
	}
	
	/**
	 * Constructor to create borrowed items.
	 *@param thing_id the id number of the borrowed item in the database
	 *@param description String describing what the borrowed item is. That is, its name
	 *@param dateBorrow Date on which the item is borrowed
	 *@param returnDate Date on which the item is returned
	 *@param person_id id number of the person to whom the item is loaned. It refers to the right person in the Person table in the database.
	 */
	public BorrowedThing(int thing_id, String description, Date dateBorrow, Date returnDate, int person_id) {
		this.thing_id = thing_id;
		this.description = description;
		this.dateBorrow = dateBorrow;
		this.returnDate = returnDate;
		this.person_id = person_id;
	}

	/**
	 * Function to fetch the id number of the borrowed item from the database.
	 * @return thing_id the id number of the borrowed item in the database
	 */
	public int getThing_id() {
		return thing_id;
	}

	/**
	 * Function to register the id number of the borrowed item into the database
	 * @param thing_id the id number of the borrowed item in the database
	 */
	public void setThing_id(int thing_id) {
		this.thing_id = thing_id;
	}

	/**
	 * Function to fetch the name of the borrowed item from the database.
	 * @return desccription the name of the borrowed item from the database
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Function to register the name of the borrowed item in the database.
	 * @param desccription the name of the borrowed item in the database
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * Function to fetch the date on which the item is borrowed from the database.
	 * @return dateBorrow Date on which the item is borrowed
	 */
	public Date getDateBorrow() {
		return dateBorrow;
	}

	/**
	 * Function to register the date on which the item is borrowed in the database.
	 * @param dateBorrow Date on which the item is borrowed
	 */
	public void setDateBorrow(Date dateBorrow) {
		this.dateBorrow = dateBorrow;
	}

	/**
	 * Function to fetch the date on which the item is returned from the database.
	 * @return returnDate Date on which the item is returned
	 */
	public Date getReturnDate() {
		return returnDate;
	}

	/**
	 * Function to register the date on which the item is returned in the database.
	 * @param returnDate Date on which the item is returned
	 */
	public void setReturnDate(Date returnDate) {
		this.returnDate = returnDate;
	}

	/**
	 * Function to fetch the id number of the person to whom the item is loaned from the database.
	 * @return person_id id number of the person to whom the item is loaned, the number refers to a person in the Person table
	 */
	public int getPerson_id() {
		return person_id;
	}

	/**
	 * Function to register the id number of the person to whom the item is loaned in the database.
	 * @param person_id id number of the person to whom the item is loaned, the number refers to a person in the Person table
	 */
	public void setPerson_id(int person_id) {
		this.person_id = person_id;
	}
	
	
}
