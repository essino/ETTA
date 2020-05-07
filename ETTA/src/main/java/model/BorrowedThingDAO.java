package model;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 * Data access object class for Borrowed things. Used for accessing data concerning Borrowed items.
 */
public class BorrowedThingDAO {
	
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
	public BorrowedThingDAO() {
		
	}
	
	/**
	 * Constructor
	 * @param test boolean indicating whether the DAO is used for testing or not
	 */
	public BorrowedThingDAO(boolean test) {
		if (test) {
			this.test = true;
		}
	}
	
	/**
	 * Method for creating a new BorrowedThing item in the database
	 * @param borrowedThing Object that represents an item borrowed to someone
	 * @return success Boolean indicating the success or failure of the database transaction
	 */
	public boolean createBorrowedThing(BorrowedThing borrowedThing) {
		boolean success = false;
		try (Session session = HibernateUtil.getSessionFactory(test).openSession()) {
			transaction = session.beginTransaction();
			session.saveOrUpdate(borrowedThing);
			transaction.commit();
			success = true;
			System.out.println(borrowedThing.getThing_id());
			System.out.println(borrowedThing.getDescription());
		} catch (Exception e) {
			if (transaction != null) transaction.rollback();
			throw e;
		}
		return success;
	}
	
	/**
	 * Method for reading one specific Borrowed Thing in the database
	 * @param thing_id int referring to the Borrowed item which is to be read
	 * @return borrowedThing Object that represents an item borrowed to someone
	 */
	public BorrowedThing readBorrowedThing(int thing_id) {
		BorrowedThing borrowedThing = new BorrowedThing();
		try {
			Session session = HibernateUtil.getSessionFactory(test).openSession();
			transaction = session.beginTransaction();
			borrowedThing = (BorrowedThing)session.get(BorrowedThing.class, thing_id);		
			transaction.commit();
			System.out.println("reading one:" + borrowedThing.getDescription());
		}
		catch(Exception e){
			if (transaction!= null) transaction.rollback();
			throw e;
		}
		return borrowedThing;
	}
	
	/**
	 * Method for reading all Borrowed Things in the database
	 * @return borrowedThings array containing all Borrowed items in the database
	 */
	public BorrowedThing[] readBorrowedThings() {
		ArrayList<BorrowedThing> list = new ArrayList<>();
		try (Session session = HibernateUtil.getSessionFactory(test).openSession()) {
			transaction = session.beginTransaction();
			@SuppressWarnings("unchecked")
			List<BorrowedThing> result = session.createQuery("from BorrowedThing").getResultList();
			//System.out.println("reading all, length: " + result.size());
			for(BorrowedThing borrowedThing : result) {
				list.add(borrowedThing);
				System.out.println("reading all: " + borrowedThing.getDescription());
			}
			transaction.commit();
		} catch (Exception e) {
			if (transaction != null) transaction.rollback();
			throw e;
		}
		BorrowedThing[] borrowedThings = new BorrowedThing[list.size()];
		return (BorrowedThing[])list.toArray(borrowedThings);
	}

	/**
	 * Method for updating Borrowed items in the database
	 * @param borrowedThing Object that represents an item borrowed to someone
	 * @return success Boolean indicating the success or failure of the database transaction
	 */
	public boolean updateBorrowedThing(BorrowedThing borrowedThing) {
	
		boolean success = false;
		try (Session session = HibernateUtil.getSessionFactory(test).openSession()) {
			transaction = session.beginTransaction();
			session.update(borrowedThing);
			transaction.commit();
			success = true;
		} catch (Exception e) {
			if (transaction != null) transaction.rollback();
			throw e;
		}
		return success;
	}
	

	/**
	 * Method for deleting Borrowed items in the database
	 * @param thing_id int referring to the Borrowed item that is to be deleted
	 * @return success Boolean indicating the success or failure of the database transaction
	 */
	public boolean deleteBorrowedThing(int thing_id) {
		boolean success = false;
		try (Session session = HibernateUtil.getSessionFactory(test).openSession()) {
			transaction = session.beginTransaction();
			BorrowedThing borrowedThing = (BorrowedThing)session.get(BorrowedThing.class, thing_id);
			session.delete(borrowedThing);
			transaction.commit();
			success = true;
		} catch (Exception e) {
			if (transaction != null) transaction.rollback();
			throw e;
		}
		return success;
	}
	
	/**
	 * Method for reading Borrowed Things in the database that use a concrete Person
	 * @param person_id int referring to the Person that might be used for Borrowed Thing
	 * @return BorrowedThing[] Array containing all Borrowed items connected with this person in the database
	 */
	public BorrowedThing [] readBorrowedThingsByPerson(int person_id) {
		ArrayList<BorrowedThing> list = new ArrayList<>();
		try (Session session = HibernateUtil.getSessionFactory(test).openSession()) {
			transaction = session.beginTransaction();
			@SuppressWarnings("unchecked")
			List<BorrowedThing> result = session.createQuery("from BorrowedThing where person=" + person_id).getResultList();
			//System.out.println("reading all, length: " + result.size());
			for(BorrowedThing borrowedThing : result) {
				list.add(borrowedThing);
				System.out.println("reading all for this person: " + borrowedThing.getDescription());
			}
			transaction.commit();
		} catch (Exception e) {
			if (transaction != null) transaction.rollback();
			throw e;
		}
		BorrowedThing[] borrowedThings = new BorrowedThing[list.size()];
		return (BorrowedThing[])list.toArray(borrowedThings);
	}
}
