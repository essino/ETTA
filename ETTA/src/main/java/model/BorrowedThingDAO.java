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
	SessionFactory factory = null;
	Transaction transaction = null;
	
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
	
	protected void finalize() {
		factory.close();
	}
	
	public boolean createBorrowedThing(BorrowedThing borrowedThing) {
		boolean success = false;
		try {
			Session session = factory.openSession();
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
	
	public BorrowedThing[] readBorrowedThings() {
		ArrayList<BorrowedThing> list = new ArrayList<>();
		try (Session session = factory.openSession()) {
			transaction = session.beginTransaction();
			@SuppressWarnings("unchecked")
			List<BorrowedThing> result = session.createQuery("from BorrowedThing").getResultList();
			for(BorrowedThing borrowedThing : result) {
				list.add(borrowedThing);
				System.out.println(borrowedThing.getDescription());
			}
			transaction.commit();
		} catch (Exception e) {
			if (transaction != null) transaction.rollback();
			throw e;
		}
		BorrowedThing[] borrowedThings = new BorrowedThing[list.size()];
		return (BorrowedThing[])list.toArray(borrowedThings);
	}

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

	public boolean deleteBorrowedThing(String description) {
		boolean success = false;
		try (Session session = factory.openSession()) {
			transaction = session.beginTransaction();
			BorrowedThing borrowedThing = (BorrowedThing)session.get(BorrowedThing.class, description);
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
