package model;

import java.sql.Date;

import javax.persistence.*;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

/**
 * Model class for Items. Used in the creation of the database table for Items through Hibernate.
 */
@Entity
@Table(name="Item")
public class Item {
	
	/**
	 * Unique int for identifying the wishlist item
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column
	private int item_id;
	
	/**
	 * String describing the item
	 */
	@Column(length=50)
	private String description;
	
	/**
	 * Person who the item is for
	 */
	@ManyToOne
	@JoinColumn(nullable=true)
	private Person person;
	
	/**
	 * Price of the item
	 */
	@Column(nullable = true)
	private Double price;
	
	/**
	 * Boolean indicating whether or not the item has been purchased
	 */
	@Column(name="bought")
	private boolean bought;
	
	/**
	 * Date on which the item will be needed
	 */
	@Column(nullable=true)
	private Date dateNeeded;
	
	/**
	 * It's possible to add additional information about the item
	 */
	@Column(length=100, nullable=true)
	private String additionalInfo;
	
	/**
	 * Constructor to create items without parameters.
	 */
	public Item() {
		
	}
	
	/**
	 * Constructor to create items
	 *@param desc String describing what the item is
	 *@param person Person the person who the item is for
	 *@param price double the price of the item
	 *@param date Date when the item is needed
	 *@param info String additional information about the item
	 */
	public Item(String desc, Person person, Double price, Date date, String info) {
		this.description = desc;
		this.person = person;
		this.price = price;
		this.dateNeeded = date;
		this.additionalInfo = info;
		this.bought = false;
	}
	
	/**
	 * Constructor to create items
	 *@param desc String describing what the item is
	 *@param person Person the person who the item is for
	 *@param price double the price of the item
	 *@param date Date when the item is needed
	 *@param info String additional information about the item
	 *@param bought boolean whether the item has been bought or not
	 */
	public Item(String desc, Person person, Double price, Date date, String info, boolean bought) {
		this.description = desc;
		this.person = person;
		this.price = price;
		this.dateNeeded = date;
		this.additionalInfo = info;
		this.bought = bought;
	}

	/**
	 * Function to fetch the id number of the item
	 * @return item_id the id number of the item
	 */
	public int getItem_id() {
		return item_id;
	}

	/**
	 * Function to register the id number of the item
	 * @param item_id int the id number of the item
	 */
	public void setItem_id(int item_id) {
		this.item_id = item_id;
	}

	/**
	 * Function to fetch the description of the item
	 * @return description the description of the item
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Function to register the description of the item
	 * @param description String the description of the item
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * Function to fetch the person who the item is for
	 * @return person the person who the item is for
	 */
	public Person getPerson() {
		return person;
	}

	/**
	 * Function to register the person who the item is for
	 * @param person Person the person who the item is for
	 */
	public void setPerson(Person person) {
		this.person = person;
	}

	/**
	 * Function to fetch the price of the item
	 * @return price the price of the item
	 */
	public Double getPrice() {
		return price;
	}

	/**
	 * Function to register the price of the item
	 * @param price double the price of the item
	 */
	public void setPrice(Double price) {
		this.price = price;
	}

	/**
	 * Function to check if the item has already been bought or not.
	 * @return bought if the item has been bought
	 */
	public boolean isBought() {
		return bought;
	}

	/**
	 * Function to register whether the item has already been bought or not.
	 * @param bought boolean whether the item has been bought
	 */
	public void setBought(boolean bought) {
		this.bought = bought;
	}

	/**
	 * Function to fetch the date of the item
	 * @return dateNeeded the date when the item is needed
	 */
	public Date getDateNeeded() {
		return dateNeeded;
	}

	/**
	 * Function to register date of the item
	 * @param dateNeeded Date the date when the item is needed
	 */
	public void setDateNeeded(Date dateNeeded) {
		this.dateNeeded = dateNeeded;
	}

	/**
	 * Function to fetch the additional information about the item
	 * @return additionalInfo additional information about the item
	 */
	public String getAdditionalInfo() {
		return additionalInfo;
	}

	/**
	 * Function to register the additional information about the item
	 * @param additionalInfo String additional information the item
	 */
	public void setAdditionalInfo(String additionalInfo) {
		this.additionalInfo = additionalInfo;
	}
	
}
