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
 * Data access object class for Borrowed things. Used in the creation of the database table for Borrowed items through Hibernate.
 */
public class BorrowedThingDAO {
	
	/**
	 * SessionFactory object needed to open session with the database
	 */
	SessionFactory factory = null;
	
	/**
	 * Transaction object to carry out database transaction
	 */
	Transaction transaction = null;
	
	/**
	 * Constructor for BorrowedThingDAO
	 */
	public BorrowedThingDAO() {
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
	 * method for closing the database session
	 */
	protected void finalize() {
		factory.close();
	}
	
	/**
	 * method for making a new BorrowedThing item in the database
	 * @param borrowedThing Object that represents an item borrowed to someone
	 * @return success Boolean indicating the success or failure of the database transaction
	 */
	public boolean createBorrowedThing(BorrowedThing borrowedThing) {
		boolean success = false;
		try (Session session = factory.openSession()) {
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
	 * method for seeing one specific Borrowed Thing in the database
	 * @return success Boolean indicating the success or failure of the database transaction
	 */
	public BorrowedThing readBorrowedThing(int thing_id) {
		BorrowedThing borrowedThing = new BorrowedThing();
		try {
			Session session = factory.openSession();
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
	 * method for seeing all Borrowed Things in the database
	 * @return success Boolean indicating the success or failure of the database transaction
	 */
	public BorrowedThing[] readBorrowedThings() {
		ArrayList<BorrowedThing> list = new ArrayList<>();
		try (Session session = factory.openSession()) {
			transaction = session.beginTransaction();
			@SuppressWarnings("unchecked")
			List<BorrowedThing> result = session.createQuery("from BorrowedThing").getResultList();
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
	 * method for updating Borrowed items in the database
	 * @param borrowedThing Object that represents an item borrowed to someone
	 * @return success Boolean indicating the success or failure of the database transaction
	 */
	public boolean updateBorrowedThing(BorrowedThing borrowedThing) {
		boolean success = false;
		try (Session session = factory.openSession()) {
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
	 * method for deleting Borrowed items in the database
	 * @param description String that names the borrowed item
	 * @return success Boolean indicating the success or failure of the database transaction
	 */
	public boolean deleteBorrowedThing(int thing_id) {
		boolean success = false;
		try (Session session = factory.openSession()) {
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
}
