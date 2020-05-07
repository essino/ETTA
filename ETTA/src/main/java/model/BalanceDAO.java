package model;

import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *  Data access object class for Balance. Used in the creation of the database table for Balance through Hibernate.
 */

public class BalanceDAO {
	
	/**
	 * Transaction object to carry out database transaction
	 */
	private Transaction transaction = null;
	
	/**
	 * Boolean indicating whether the DAO should connect to the test database or not
	 * Default value false
	 */
	private boolean test = false;
	
	/**
	 * Construction without parameters
	 */
	public BalanceDAO() {
		
	}
	
	/**
	 * Constructor
	 * @param test boolean indicating whether the DAO is used for testing or not
	 */
	public BalanceDAO(boolean test) {
		if (test) {
			this.test = true;
		}
	}
	
	/** 
	 * Method that creates a balance row in the Balance table. Is is used only once, there should be only one row on the Balance table. 
	 * @param balance Balance object 
	 * @return success true if balance row was created successfully  
	 */
	public boolean createBalance(Balance balance) {
		boolean success = false;
		try {
			Session session = HibernateUtil.getSessionFactory(test).openSession();
			transaction = session.beginTransaction();
			session.saveOrUpdate(balance);
			transaction.commit();
			success = true;
			System.out.println("Creating balance: " + balance.getBalance() + " id " + balance.getId());
		} catch (Exception e) {
			if (transaction != null) transaction.rollback();
			throw e;
		}
		return success;
	}
	
	/** 
	 * Method that updates a balance row in the Balance table. 
	 * @param balance object 
	 * @return success true if balance row was updated successfully  
	 */
	public boolean updateBalance(Balance balance) {
		boolean success = false;
		try (Session session = HibernateUtil.getSessionFactory(test).openSession()) {
			transaction = session.beginTransaction();
			session.update(balance);
			transaction.commit();
			success = true;
			System.out.println("Updating balance, new balance " + balance.getBalance() + " id " + balance.getId());
		} catch (Exception e) {
			if (transaction != null) transaction.rollback();
			throw e;
		}
		return success;
	}
	/** 
	 * Method that reads a balance row in the Balance table. 
	 * @param balance_id 
	 * @return balance object 
	 */
	public Balance readBalance(int balance_id) {
		Balance balance = new Balance();
		try (Session session = HibernateUtil.getSessionFactory(test).openSession()) {
			transaction = session.beginTransaction();
			balance = (Balance)session.get(Balance.class, balance_id);		
			transaction.commit();
			System.out.println("reading balance:" + balance.getBalance() + " id " + balance.getId());
		}
		catch(NullPointerException eNull) {
			return null;
		}
		catch(Exception e){
			if (transaction!=null) transaction.rollback();
			throw e;
		}
		return balance;
	}
}
