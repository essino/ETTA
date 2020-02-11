package model;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="Transfer")
public class Transfer {
	@Id
	@GeneratedValue
	@Column(name="transfer_id")
	private int transfer_id;
	
	@Column(name="description", length=50)
	private String description;
	
	@ManyToOne
	@JoinColumn(name="category", nullable=true)
	private Category category;
	
	@Column(name="income")
	private boolean income;
	
	@Column (name="date")
	private Date date;
	
	@Column(name="amount")
	private float amount;
	
	public Transfer() {
		
	}

	public Transfer(String description, Category category, boolean income, Date date, float amount) {
		this.description = description;
		this.category = category;
		this.income = income;
		this.date = date;
		this.amount = amount;
	}
	
	public int getTransfer_id() {
		return transfer_id;
	}

	public void setTransfer_id(int transfer_id) {
		this.transfer_id = transfer_id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public boolean isIncome() {
		return income;
	}

	public void setIncome(boolean income) {
		this.income = income;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public float getAmount() {
		return amount;
	}

	public void setAmount(float amount) {
		this.amount = amount;
	}
	
}