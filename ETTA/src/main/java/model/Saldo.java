package model;

import javax.persistence.*;

@Entity
@Table(name="Balance")
public class Saldo {
	@Id
	@GeneratedValue
	@Column
	private int id;
	@Column
	private float balance = 0;
	
	public Saldo() {
		
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public float getBalance() {
		return balance;
	}

	public void setBalance(float balance) {
		this.balance = balance;
	}
	
}
