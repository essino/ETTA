package model;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

/**
 * Data access object class for Savings. Used in accessing the table in the database.
 */
public class SavingDAO {
	
	/**
	 * SessionFactory object needed to open session with the database
	 */
	SessionFactory factory = null;
	/**
	 * Transaction object to carry out database transactions
	 */
	Transaction transaction = null;
	
	/**
	 * Constructor for SavingDAO
	 */
	public SavingDAO() {
		final StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure().build();
		try {
			factory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
		} catch (Exception e) {
			System.out.println("Creation of session factory failed");
			StandardServiceRegistryBuilder.destroy(registry);
			e.printStackTrace();
			System.exit(-1);}
	}
	
	/**
	 * Method for closing the database connection
	 */
	protected void finalize() {
		factory.close();
	}
	
	 /**
		 * Method for creating a new Saving in the database
		 * @param saving Saving the saving object to be added to the database
		 * @return success Boolean indicating the success or failure of the database transaction
		 */
	public boolean createSaving(Saving saving) {
		boolean success = false;
		try {
			Session session = factory.openSession();
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
			Session session = factory.openSession();
			transaction = session.beginTransaction();
			@SuppressWarnings("unchecked")
			List<Saving> result = session.createQuery("from Saving").getResultList();
			for(Saving saving : result) {
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
			Session session = factory.openSession();
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
		boolean success = false;
		try (Session session = factory.openSession()) {
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
		try (Session session = factory.openSession()) {
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

}
