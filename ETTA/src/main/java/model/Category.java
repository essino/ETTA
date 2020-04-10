package model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Model class for category of income/expence. Used in the creating, reading and updating of the database table for Category through Hibernate.
 */
@Entity
@Table(name="Category")
public class Category {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="Category_id")
	private int Category_id;
	@Column(name="description", length=50)
	private String description;
	@Column(name="income")
	private boolean income;
	
	/**
	 * Function to fetch the type of the category (income/expense) from the database.
	 * @return true if it's income category
	 * @return false if it's expense category
	 */
	public boolean isCategory_type() {
		return income;
	}
	
	/**
	 * Function to register the type of the category (income/expense) into the database
	 * @param income the type of the category in the database
	 */
	public void setCategory_type(boolean income) {
		this.income = income;
	}
	/**
	 * Function to fetch the id number of the category from the database.
	 * @return id the id number of the category in the database
	 */
	public int getCategory_id() {
		return Category_id;
	}
	/**
	 * Function to register the id number of the category into the database
	 * @param id the id number of the category in the database
	 */
	public void setCategory_id(int Category_id) {
		this.Category_id = Category_id;
	}
	/**
	 * Function to fetch the name of the category from the database.
	 * @return description the name of the category from the database
	 */
	public String getDescription() {
		return description;
	}
	/**
	 * Function to register the name of the category in the database.
	 * @param desccription the name of the category in the database
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	/**
	 * Constructor to create balance. No parameters.
	 */
	public Category() {
	
	}	
	/**
	 * Constructor to create borrowed items.
	 *@param description String describing the category of income/expence
	 */
	public Category(String description, boolean income) {
		this.description = description;
		this.income = income;
	}	

	
	@Override
	public String toString() {
		return this.description;
	}
}

