package model;

import java.sql.Date;

import javax.persistence.*;

import javafx.beans.property.BooleanProperty;

/**
 * Model class for Items. Used in the creation of the database table for Items through Hibernate.
 */
@Entity
@Table(name="Item")
@Access(AccessType.PROPERTY)
public class Item {
	
	private int item_id;
	
	private String description;
	
	private Person person;
	
	private double price;
	
	private BooleanProperty bought;
	
	private Date dateNeeded;
	
	private String additionalInfo;
	
	/**
	 * Constructor to create items without parameters.
	 */
	public Item() {
		
	}
	
	/**
	 * Constructor to create items
	 *@param description String describing what the item is
	 *@param person Person the person who the item is for
	 *@param price double the price of the item
	 *@param dateNeeded Date when the item is needed
	 *@param additionalInfo String additional information about the item
	 */
	public Item(String desc, Person person, double price, Date date, String info) {
		this.description = desc;
		this.person = person;
		this.price = price;
		this.dateNeeded = date;
		this.additionalInfo = info;
		this.bought.set(false);
	}

	/**
	 * Function to fetch the id number of the item from the database.
	 * @return item_id the id number of the item in the database
	 */
	@Id
	@GeneratedValue
	@Column
	public int getItem_id() {
		return item_id;
	}

	/**
	 * Function to register the id number of the item into the database
	 * @param item_id int the id number of the item in the database
	 */
	public void setItem_id(int item_id) {
		this.item_id = item_id;
	}

	/**
	 * Function to fetch the description of the item from the database.
	 * @return description the description of the item in the database
	 */
	@Column(length=50)
	public String getDescription() {
		return description;
	}

	/**
	 * Function to register the description of the item into the database
	 * @param description String the description of the item in the database
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * Function to fetch the person who the item is for from the database.
	 * @return person the person who the item is for in the database
	 */
	@ManyToOne
	@JoinColumn(nullable=true)
	public Person getPerson() {
		return person;
	}

	/**
	 * Function to register the person who the item is for into the database
	 * @param person Person the person who the item is for in the database
	 */
	public void setPerson(Person person) {
		this.person = person;
	}

	/**
	 * Function to fetch the price of the item from the database.
	 * @return price the price of the item in the database
	 */
	@Column(nullable = true)
	public double getPrice() {
		return price;
	}

	/**
	 * Function to register the price of the item into the database
	 * @param price double the price of the item in the database
	 */
	public void setPrice(double price) {
		this.price = price;
	}

	/**
	 * Function to check if the item has already been bought or not.
	 * @return bought if the item has been bought
	 */
	@Column(name="bought")
	public boolean getBought() {
		return bought.get();
	}

	/**
	 * Function to register whether the item has already been bought or not.
	 * @param bought boolean whether the item has been bought
	 */
	public void setBought(boolean bought) {
		this.bought.set(bought);
	}

	/**
	 * Function to fetch the date of the item from the database.
	 * @return dateNeeded the date when the item is needed
	 */
	@Column(nullable=true)
	public Date getDateNeeded() {
		return dateNeeded;
	}

	/**
	 * Function to register date of the item into the database
	 * @param dateNeeded Date the date when the item is needed
	 */
	public void setDateNeeded(Date dateNeeded) {
		this.dateNeeded = dateNeeded;
	}

	/**
	 * Function to fetch the additional information about the item from the database.
	 * @return additionalInfo additional information about the item in the database
	 */
	@Column(length=100, nullable=true)
	public String getAdditionalInfo() {
		return additionalInfo;
	}

	/**
	 * Function to register the additional information about the item into the database
	 * @param additionalInfo String additional information the item in the database
	 */
	public void setAdditionalInfo(String additionalInfo) {
		this.additionalInfo = additionalInfo;
	}
	
}
