package model;

import java.sql.Date;

import javax.persistence.*;

/**
 * Model class for Borrowed things. Used in the creation of the database table for Borrowed items through Hibernate.
 */
@Entity
@Table(name="BorrowedThing")
public class BorrowedThing {
	
	@Id
	@GeneratedValue
	@Column(name="thing_id") 
	private int thing_id;
	
	@Column(name="description", length=50)
	private String description;
	
	@Column(name="dateBorrowed")
	private Date dateBorrowed;
	
	@Column(name="returnDate", nullable=true)
	private Date returnDate;
	
	@ManyToOne
	@JoinColumn(name="person")
	private Person person;
	
	@Column(name="returned")
	private boolean returned = false;

	/**
	 * Constructor to create borrowed items. No parameters.
	 */
	public BorrowedThing() {
		
	}
	
	/**
	 * Constructor to create borrowed items.
	 *@param description String describing what the borrowed item is. That is, its name
	 *@param dateBorrowed Date on which the item is borrowed
	 *@param returnDate Date on which the item is returned
	 *@param person the person to whom the item is loaned. It refers to the right person in the Person table in the database.
	 */
	public BorrowedThing(String description, Date dateBorrowed, Date returnDate, Person person) {
		this.description = description;
		this.dateBorrowed = dateBorrowed;
		this.returnDate = returnDate;
		this.person = person;
	}
	
	/**
	 * Constructor to create borrowed items. Includes thing_id as parameter.
	 *@param thing_id identification number given to the borrowed item
	 *@param description String describing what the borrowed item is. That is, its name
	 *@param dateBorrowed Date on which the item is borrowed
	 *@param returnDate Date on which the item is returned
	 *@param person the person to whom the item is loaned. It refers to the right person in the Person table in the database.
	 */
	public BorrowedThing(int thing_id, String description, Date dateBorrowed, Date returnDate, Person person) {
		this.thing_id = thing_id;
		this.description = description;
		this.dateBorrowed = dateBorrowed;
		this.returnDate = returnDate;
		this.person = person;
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
	 * @return description the name of the borrowed item from the database
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
	 * @return dateBorrowed Date on which the item is borrowed
	 */
	public Date getDateBorrowed() {
		return dateBorrowed;
	}

	/**
	 * Function to register the date on which the item is borrowed in the database.
	 * @param dateBorrowed Date on which the item is borrowed
	 */
	public void setDateBorrowed(Date dateBorrowed) {
		this.dateBorrowed = dateBorrowed;
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
	 * @return person the person to whom the item is loaned
	 */
	public Person getPerson() {
		return person;
	}

	/**
	 * Function to register the id number of the person to whom the item is loaned in the database.
	 * @param person the person to whom the item is loaned
	 */
	public void setPerson(Person person) {
		this.person = person;
	}
	
	public boolean isReturned() {
		return returned;
	}

	public void setReturned(boolean returned) {
		this.returned = returned;
	}
	
}
