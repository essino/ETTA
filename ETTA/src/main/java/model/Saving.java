package model;


import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="Saving")

public class Saving {
	@Id
	@GeneratedValue
	@Column(name="saving_id")
	private int saving_id;
	
	@Column(name="description", length=50)
	private String description;
	
	@Column(name="amount")
	private float amount = 0;
	
	@Column(name="goalAmount")
	private float goalAmount;
	
	@Column(name="goalDate", nullable=true)
	private Date goalDate;
	
	@Column(name="progress")
	private float progress = 0;
	
	public int getSaving_id() {
		return saving_id;
	}

	public void setSaving_id(int saving_id) {
		this.saving_id = saving_id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public float getAmount() {
		return amount;
	}

	public void setAmount(float amount) {
		this.amount = amount;
	}

	public float getGoalAmount() {
		return goalAmount;
	}

	public void setGoal_amount(float goalAmount) {
		this.goalAmount = goalAmount;
	}

	public Date getGoalDate() {
		return goalDate;
	}

	public void setGoalDate(Date goalDate) {
		this.goalDate = goalDate;
	}

	public float getProgress() {
		return progress;
	}

	public void setProgress(float progress) {
		this.progress = progress;
	}
	
}
