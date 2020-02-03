package model;

import java.sql.Date;

import javax.persistence.*;

@Entity
@Table(name="Wishes")
public class Wishlist {
	@Id
	@GeneratedValue
	@Column
	private int item_id;
	@Column
	private String description;
	@ManyToOne
	@JoinColumn
	private int person_id;
	@Column
	private double price;
	@Column
	private boolean bought;
	@Column
	private Date dateNeeded;
	@Column
	private String additionalInfo;
	
	public Wishlist() {
		
	}

	public int getItem_id() {
		return item_id;
	}

	public void setItem_id(int item_id) {
		this.item_id = item_id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getPerson_id() {
		return person_id;
	}

	public void setPerson_id(int person_id) {
		this.person_id = person_id;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public boolean isBought() {
		return bought;
	}

	public void setBought(boolean bought) {
		this.bought = bought;
	}

	public Date getDateNeeded() {
		return dateNeeded;
	}

	public void setDateNeeded(Date dateNeeded) {
		this.dateNeeded = dateNeeded;
	}

	public String getAdditionalInfo() {
		return additionalInfo;
	}

	public void setAdditionalInfo(String additionalInfo) {
		this.additionalInfo = additionalInfo;
	}
	
	
}
