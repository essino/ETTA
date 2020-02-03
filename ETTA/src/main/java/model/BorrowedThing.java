package model;

import java.sql.Date;

import javax.persistence.*;

@Entity
@Table(name="borrowed_things")
public class BorrowedThing {
	
	@Id
	@GeneratedValue
	@Column(name="thing_id") 
	private int thing_id;
	
	@Column(name="description")
	private String description;
	
	@Column(name="dateBorrow")
	private Date dateBorrow;
	
	@Column(name="returnDate")
	private Date returnDate;
	
	@ManyToOne
	@JoinColumn(name="person_id")
	private int person_id;
	
	public BorrowedThing() {
	}
	
	public BorrowedThing(int thing_id, String description, Date dateBorrow, Date returnDate, int person_id) {
		this.thing_id = thing_id;
		this.description = description;
		this.dateBorrow = dateBorrow;
		this.returnDate = returnDate;
		this.person_id = person_id;
	}

	public int getThing_id() {
		return thing_id;
	}

	public void setThing_id(int thing_id) {
		this.thing_id = thing_id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getDateBorrow() {
		return dateBorrow;
	}

	public void setDateBorrow(Date dateBorrow) {
		this.dateBorrow = dateBorrow;
	}

	public Date getReturnDate() {
		return returnDate;
	}

	public void setReturnDate(Date returnDate) {
		this.returnDate = returnDate;
	}

	public int getPerson_id() {
		return person_id;
	}

	public void setPerson_id(int person_id) {
		this.person_id = person_id;
	}
	
	
}
