package model;

import javax.persistence.*;

/**
 * Model class for balance. Used in the reading and updating of the database table for Balance through Hibernate.
 */

@Entity
@Table(name="Balance")
public class Balance {
	
	/**
	 * a unique integer for identifying the balance
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column
	private int id;
	
	/**
	 * amount of balance
	 */
	@Column
	private float balance = 0;
	
	/**
	 * Constructor to create balance. No parameters.
	 */
	public Balance() {
		
	}

	/**
	 * Function to fetch the id number of the balance from the database.
	 * @return id the id number of the balance in the database
	 */
	public int getId() {
		return id;
	}
	/**
	 * Function to register the id number of the balance into the database
	 * @param id the id number of the balance in the database
	 */
	public void setId(int id) {
		this.id = id;
	}
	/**
	 * Function to fetch the amount of the balance from the database.
	 * @return balance float amount of the balance
	 */
	public float getBalance() {
		return balance;
	}
	/**
	 * Function to register the amount of the balance into the database
	 * @param balance float amount of the balance
	 */
	public void setBalance(float balance) {
		this.balance = balance;
	}

	/**
	 * Constructor to create balance.
	 *@param balance float amount of the balance
	 */
	public Balance(float balance) {
		this.balance = balance;
	}
	
	/**
	 * Constructor to create balance.
	 *@param id the id number of the balance in the database
	 *@param balance float amount of the balance
	 */
	public Balance(int id, float balance) {
		this.id = id;
		this.balance = balance;
	}
	
}
