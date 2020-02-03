package model;


import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="All_savings")

public class All_savings{
	
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

	public float getGoal_amount() {
		return goal_amount;
	}

	public void setGoal_amount(float goal_amount) {
		this.goal_amount = goal_amount;
	}

	public Date getGoal_date() {
		return goal_date;
	}

	public void setGoal_date(Date goal_date) {
		this.goal_date = goal_date;
	}

	public float getReached_goal() {
		return reached_goal;
	}

	public void setReached_goal(float reached_goal) {
		this.reached_goal = reached_goal;
	}

	@Id
	@Column(name="saving_id")
	private int saving_id;
	
	@Column(name="description")
	private String description;
	
	@Column(name="amount")
	private float amount;
	
	@Column(name="goal_amount")
	private float goal_amount;
	
	@Column(name="goal_date")
	private Date goal_date;
	
	@Column(name="reached_goal")
	private float reached_goal;
	
}
