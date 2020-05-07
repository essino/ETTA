package model;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Model class for Savings. Used in the creation of the database table for Savings through Hibernate.
 */
@Entity
@Table(name="Saving")

public class Saving {
	/**
	 * a unique integer for the ID of the specificsaving
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="saving_id")
	private int saving_id;
	
	/**
	 * the description of the savings goal
	 */
	@Column(name="description", length=50)
	private String description;
	
	/**
	 * the saved amount of the savings goal
	 */
	@Column(name="amount")
	private float amount = 0;
	
	/**
	 * the goal amount of the savings goal
	 */
	@Column(name="goalAmount")
	private float goalAmount;
	
	/**
	 * the goal date of the savings goal
	 */
	@Column(name="goalDate", nullable=true)
	private Date goalDate;
	
	/**
	 * the progress of the savings goal (depends on the amount ang goal amount)
	 */
	@Column(name="progress")
	private double progress = 0;

	/**
	 * Constructor for creating a saving with parameters
	 * @param desc String describing the saving
	 * @param amountGoal float indicating how much money should be saved in total
	 * @param reachedGoal float indicating how much money has been saved until now
	 * @param date Date indicating when the goal should be reached
	 */
	public Saving(String desc, float amountGoal, float reachedGoal, Date date) {
		this.description = desc;
		this.goalAmount = amountGoal;
		this.amount = reachedGoal;
		this.goalDate = date;
	}

	/**
	 * Construction without parameters
	 */
	public Saving() {
		
	}

	/**
	 * Method for fetching the saving's id number
	 * @return saving_id int identifying a saving
	 */
	public int getSaving_id() {
		return saving_id;
	}

	/**
	 * Method for setting the saving's id number
	 * @param saving_id int identifying the saving
	 */
	public void setSaving_id(int saving_id) {
		this.saving_id = saving_id;
	}

	/**
	 * Method for fetching the saving's description
	 * @return description String naming the saving
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Method for setting the saving's description
	 * @param description String naming the saving
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * Method for fetching the saving's saved amount
	 * @return amount float indicating how much money has already been saved
	 */
	public float getAmount() {
		return amount;
	}

	/**
	 * Method for setting the saving's saved amount
	 * @param amount float indicating how much money has already been saved
	 */
	public void setAmount(float amount) {
		this.amount = amount;
	}

	/**
	 * Method for fetching the saving's goal amount
	 * @return amount float indicating how much money should be saved
	 */
	public float getGoalAmount() {
		return goalAmount;
	}

	/**
	 * Method for setting the saving's goal amount
	 * @param amount float indicating how much money should be saved
	 */
	public void setGoal_amount(float goalAmount) {
		this.goalAmount = goalAmount;
	}

	/**
	 * Method for fetching the date when the money should be saved
	 * @return goalDate Date when the money should be saved
	 */
	public Date getGoalDate() {
		return goalDate;
	}

	/**
	 * Method for setting the date when the money should be saved
	 * @param goalDate Date when the money should be saved
	 */
	public void setGoalDate(Date goalDate) {
		this.goalDate = goalDate;
	}

	/**
	 * Method for fetching the progress that has been made when saving for a goal
	 * @return progress double indicating how much progress has been made
	 */
	public double getProgress() {
		return progress;
	}

	/**
	 * Method for setting the progress that has been made when saving money for a goal
	 * @param progress double indicating how much progress has been made
	 */
	public void setProgress(double progress) {
		this.progress = progress;
	}
	
}
