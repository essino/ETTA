package model;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 * Data access object class for Savings. Used in accessing the table in the database.
 */
public class SavingDAO {
	
	/**
	 * Transaction object to carry out database transactions
	 */
	Transaction transaction = null;
	
	/**
	 * Boolean indicating whether the DAO should connect to the test database or not
	 * Default value false
	 */
	boolean test = false;
	
	/**
	 * Construction without parameters
	 */
	public SavingDAO() {
		
	}
	
	/**
	 * Constructor
	 * @param test boolean indicating whether the DAO is used for testing or not
	 */
	public SavingDAO(boolean test) {
		if (test) {
			this.test = true;
		}
	}
	
	 /**
		 * Method for creating a new Saving in the database
		 * @param saving Saving the saving object to be added to the database
		 * @return success Boolean indicating the success or failure of the database transaction
		 */
	public boolean createSaving(Saving saving) {
		boolean success = false;
		try {
			Session session = HibernateUtil.getSessionFactory(test).openSession();
			transaction = session.beginTransaction();
			session.saveOrUpdate(saving);
			transaction.commit();
			success = true;
			System.out.println(saving.getSaving_id());
			System.out.println(saving.getDescription());
			
		} catch (Exception e) {
			if (transaction != null) transaction.rollback();
			throw e;
		}
		return success;
	}
	
	/**
	 * Method for reading all Savings in the database
	 * @return Saving[] array with all the people in the database
	 */
	public Saving[] readSavings() {
		ArrayList<Saving> list = new ArrayList<>();
		
		try  {
			Session session = HibernateUtil.getSessionFactory(test).openSession();
			transaction = session.beginTransaction();
			@SuppressWarnings("unchecked")
			List<Saving> result = session.createQuery("from Saving").getResultList();
			for(Saving saving : result) {
				saving.setProgress(saving.getAmount()/saving.getGoalAmount());
				list.add(saving);

			}
			transaction.commit();
		} catch (Exception e) {
			if (transaction != null) transaction.rollback();
			throw e;
		}
		Saving[] savings = new Saving[list.size()];
		return (Saving[])list.toArray(savings);
	}
	
	/**
	 * Method for reading one specific Saving in the database
	 * @param saving_id int the id of the Saving to be read
	 * @return success Boolean indicating the success or failure of the database transaction
	 */
	public Saving readSaving(int saving_id) {
		Saving saving = new Saving();
		try {
			Session session = HibernateUtil.getSessionFactory(test).openSession();
			transaction = session.beginTransaction();
			saving = (Saving)session.get(Saving.class, saving_id);		
			transaction.commit();
			System.out.println("reading one:" + saving.getDescription());
		}
		catch(Exception e){
			if (transaction!= null) transaction.rollback();
			throw e;
		}
		return saving;
	}

	/**
	 * Method for updating a Saving in the database
	 * @param saving Saving the updated person object
	 * @return success Boolean indicating the success or failure of the database transaction
	 */
	public boolean updateSaving(Saving saving) {
		saving.setProgress((saving.getAmount()/saving.getGoalAmount())*100);
		boolean success = false;
		try (Session session = HibernateUtil.getSessionFactory(test).openSession()) {
			transaction = session.beginTransaction();
			session.update(saving);
			transaction.commit();
			success = true;
		} catch (Exception e) {
			if (transaction != null) transaction.rollback();
			throw e;
		}
		return success;
	}

	/**
	 * Method for deleting a Saving from the database
	 * @param saving_id int the id of the Saving to be deleted
	 * @return success Boolean indicating the success or failure of the database transaction
	 */
	public boolean deleteSaving(int saving_id) {
		boolean success = false;
		try (Session session = HibernateUtil.getSessionFactory(test).openSession()) {
			transaction = session.beginTransaction();
			Saving saving = (Saving)session.get(Saving.class, saving_id);
			session.delete(saving);
			transaction.commit();
			success = true;
		} catch (Exception e) {
			if (transaction != null) transaction.rollback();
			throw e;
		}
		return success;
	}

	/**
	 * Method for getting a saving from the database
	 * @param description String describing the saving
	 * @return saving Saving to be retrieved
	 */
	public Saving getSaving(String description) {
		Saving saving = new Saving();
		try  {
			Session session = HibernateUtil.getSessionFactory(test).openSession();
			transaction = session.beginTransaction();
			@SuppressWarnings("unchecked")
			List<Saving> result = session.createQuery("from Saving where description='"+description + "'").getResultList();
			saving = result.get(0);
			transaction.commit();
		} catch (Exception e) {
			if (transaction != null) transaction.rollback();
			throw e;
		}
		return saving;
	}

}
