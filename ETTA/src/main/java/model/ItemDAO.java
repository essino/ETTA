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
 * Data access object class for Items. Used in accessing the table in the database.
 */
public class ItemDAO {
	
	/**
	 * SessionFactory object needed to open session with the database
	 */
	SessionFactory factory = null;
	
	/**
	 * Transaction object to carry out database transactions
	 */
	Transaction transaction = null;
	
	/**
	 * Constructor for ItemDAO
	 */
	public ItemDAO() {
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
	 * Method for creating a new Item in the database
	 * @param item Item the item object to be added to the database
	 * @return success Boolean indicating the success or failure of the database transaction
	 */
	public boolean createItem(Item item) {
		boolean success = false;
		try (Session session = factory.openSession()) {
			transaction = session.beginTransaction();
			session.saveOrUpdate(item);
			transaction.commit();
			success = true;
			System.out.println("creating: " + item.getItem_id());
			System.out.println("creating: " + item.getDescription());
		} catch (Exception e) {
			if (transaction != null) transaction.rollback();
			throw e;
		}
		return success;
	}
	
	/**
	 * Method for reading one specific Item in the database
	 * @param item_id int the id of the Item to be read
	 * @return success Boolean indicating the success or failure of the database transaction
	 */
	public Item readItem(int item_id) {
		Item item = new Item();
		try {
			Session session = factory.openSession();
			transaction = session.beginTransaction();
			item = (Item)session.get(Item.class, item_id);		
			transaction.commit();
			System.out.println("reading one:" + item.getDescription());
		}
		catch(Exception e){
			if (transaction!= null) transaction.rollback();
			throw e;
		}
		return item;
	}
	
	/**
	 * Method for reading all Items in the database
	 * @return Item[] array with all the items in the database
	 */
	public Item[] readItems() {
		ArrayList<Item> list = new ArrayList<>();
		try {
			Session session = factory.openSession();
			transaction = session.beginTransaction();
			@SuppressWarnings("unchecked")
			List<Item> result = session.createQuery("from Item").getResultList();
			for(Item item : result) {
				list.add(item);
				System.out.println("reading all: " + item.getDescription());
			}
			transaction.commit();
		} catch (Exception e) {
			if (transaction != null) transaction.rollback();
			throw e;
		}
		Item[] items = new Item[list.size()];
		return (Item[])list.toArray(items);
	}

	/**
	 * Method for updating an Item in the database
	 * @param item Item the updated Item object
	 * @return success Boolean indicating the success or failure of the database transaction
	 */
	public boolean updateItem(Item item) {
		boolean success = false;
		try (Session session = factory.openSession()) {
			transaction = session.beginTransaction();
			session.update(item);
			transaction.commit();
			success = true;
		} catch (Exception e) {
			if (transaction != null) transaction.rollback();
			throw e;
		}
		return success;
	}

	/**
	 * Method for deleting an Item from the database
	 * @param item_id int the id of the Item to be deleted
	 * @return success Boolean indicating the success or failure of the database transaction
	 */
	public boolean deleteItem(int item_id) {
		boolean success = false;
		try {
			Session session = factory.openSession();
			transaction = session.beginTransaction();
			Item item = (Item)session.get(Item.class, item_id);
			session.delete(item);
			transaction.commit();
			success = true;
		} catch (Exception e) {
			if (transaction != null) transaction.rollback();
			throw e;
		}
		return success;
	}
}
