package model;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Model class for Transfers. Used in the creation of the database table for Transfers through Hibernate.
 */
@Entity
@Table(name="Transfer")
public class Transfer {
	/**
	 * a unique integer for the ID of the specific transfer
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="transfer_id")
	private int transfer_id;
	
	/**
	 * the description of the transfer
	 */
	@Column(name="description", length=50)
	private String description;
	
	/**
	 * the category of the transfer
	 */
	@ManyToOne
	@JoinColumn(name="category", nullable=true)
	private Category category;
	
	/**
	 * boolean showing if the transfer is income (true) or expense (false)
	 */
	@Column(name="income")
	private boolean income;
	
	/**
	 * the date of the transfer
	 */
	@Column (name="date")
	private Date date;
	
	/**
	 * the amount of the transfer
	 */
	@Column(name="amount")
	private float amount;
	
	/**
	 * Constructor to create transfers without parameters.
	 */
	public Transfer() {
		
	}

	/**
	 * Constructor to create transfers
	 *@param description String describing what the transfer is
	 *@param category Category to which the transfer belongs
	 *@param income boolean whether the transfer is income or not
	 *@param date Date on which the transfer was made
	 *@param amount float the amount of money transferred
	 */
	public Transfer(String description, Category category, boolean income, Date date, float amount) {
		this.description = description;
		this.category = category;
		this.income = income;
		this.date = date;
		this.amount = amount;
	}
	
	/**
	 * Function to fetch the id number of the transfer from the database.
	 * @return transfer_id the id number of the transfer in the database
	 */
	public int getTransfer_id() {
		return transfer_id;
	}

	/**
	 * Function to register the id number of the transfer into the database
	 * @param transfer_id int the id number of the transfer in the database
	 */
	public void setTransfer_id(int transfer_id) {
		this.transfer_id = transfer_id;
	}

	/**
	 * Function to fetch the description of the transfer from the database.
	 * @return description the description of the transfer in the database
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Function to register the description of the transfer into the database
	 * @param description String the description of the transfer in the database
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * Function to fetch the category of the transfer from the database.
	 * @return category the category to which the transfer belongs in the database
	 */
	public Category getCategory() {
		return category;
	}

	/**
	 * Function to register the category of the transfer into the database
	 * @param category Category the category to which the transfer belongs in the database
	 */
	public void setCategory(Category category) {
		this.category = category;
	}

	/**
	 * Function to check if the transfer is income or not.
	 * @return true if the transfer is income
	 * @return false if the transfer is an expense
	 */
	public boolean isIncome() {
		return income;
	}

	/**
	 * Function to register whether the transfer is income or not
	 * @param income boolean whether the transfer is income or not
	 */
	public void setIncome(boolean income) {
		this.income = income;
	}

	/**
	 * Function to fetch the date of the transfer from the database.
	 * @return date the date of the transfer in the database
	 */
	public Date getDate() {
		return date;
	}

	/**
	 * Function to register the date of the transfer into the database
	 * @param date Date the date of transfer in the database
	 */
	public void setDate(Date date) {
		this.date = date;
	}
	
	/**
	 * Function to fetch the amount of the transfer from the database.
	 * @return amount the amount of money transferred
	 */
	public float getAmount() {
		return amount;
	}

	/**
	 * Function to register the amount of the transfer into the database
	 * @param amount float the amount of money transferred
	 */
	public void setAmount(float amount) {
		this.amount = amount;
	}
	
}