package model;

import java.sql.Date;

import javax.persistence.*;

@Entity
@Table(name="Item")
public class Item {
	@Id
	@GeneratedValue
	@Column
	private int item_id;
	
	@Column(length=50)
	private String description;
	
	@ManyToOne
	@JoinColumn(nullable=true)
	private Person person;
	
	@Column(nullable = true)
	private double price;
	
	@Column
	private boolean bought = false;
	
	@Column(nullable=true)
	private Date dateNeeded;
	
	@Column(length=100, nullable=true)
	private String additionalInfo;
	
	public Item() {
		
	}
	
	public Item(String desc, Person person, double price, Date date, String info) {
		this.description = desc;
		this.person = person;
		this.price = price;
		this.dateNeeded = date;
		this.additionalInfo = info;
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

	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
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
