package model;


import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="All_savings")

public class All_savings{
	
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
