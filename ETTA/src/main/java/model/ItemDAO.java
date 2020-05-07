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
	 * Transaction object to carry out database transactions
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
	public ItemDAO() {
		
	}
	
	/**
	 * Constructor
	 * @param test boolean indicating whether the DAO is used for testing or not
	 */
	public ItemDAO(boolean test) {
		if (test) {
			this.test = true;
		}
	}
	
	/**
	 * Method for creating a new Item in the database
	 * @param item Item to be added to the database
	 * @return success Boolean indicating the success or failure of the database transaction
	 */
	public boolean createItem(Item item) {
		boolean success = false;
		try (Session session = HibernateUtil.getSessionFactory(test).openSession()) {
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
	 * @return item the Item retrieved from the database
	 */
	public Item readItem(int item_id) {
		Item item = new Item();
		try {
			Session session = HibernateUtil.getSessionFactory(test).openSession();
			transaction = session.beginTransaction();
			item = (Item)session.get(Item.class, item_id);		
			transaction.commit();
			System.out.println("reading one:" + item.getDescription());
		} catch(Exception e) {
			if (transaction!= null) transaction.rollback();
			throw e;
		}
		return item;
	}
	
	/**
	 * Method for reading one specific Item in the database
	 * @param desc String describing the Item
	 * @return item the Item retrieved from the database
	 */
	public Item readItem(String desc) {
		Item item = new Item();
		try {
			Session session = HibernateUtil.getSessionFactory(test).openSession();
			transaction = session.beginTransaction();
			List<Item>  result = session.createQuery( "from Item where description='" + desc + "'" ).list();
			if (result.size() != 0) {
				item = result.get(0);		
				transaction.commit();
				System.out.println("result " + result);
				System.out.println("reading one:" + item.getDescription());
			} else {
				item = null;
			}
		} catch (Exception e) {
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
			Session session = HibernateUtil.getSessionFactory(test).openSession();
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
		try (Session session = HibernateUtil.getSessionFactory(test).openSession()) {
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
			Session session = HibernateUtil.getSessionFactory(test).openSession();
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

	/**
	 * Method for reading Wishlist Items in the database that use a concrete Person
	 * @param person_id int referring to the Person that might be used for Wishlist Item
	 * @return Item[] Array containing all Items connected with this person in the database
	 */
	public Item[] readItemsByPerson(int person_id) {
		ArrayList<Item> list = new ArrayList<>();
		try {
			Session session = HibernateUtil.getSessionFactory(test).openSession();
			transaction = session.beginTransaction();
			@SuppressWarnings("unchecked")
			List<Item> result = session.createQuery("from Item where person=" + person_id).getResultList();
			for(Item item : result) {
				list.add(item);
				System.out.println("reading for one person: " + item.getDescription());
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
	 * Method for reading Wishlist Items in the database that are either bought or not bought
	 * @param boolean bought whether the item has been bought
	 * @return Item[] Array containing all Items that are bought/not bought
	 */
	public Item[] readItemsByBought(boolean bought) {
		ArrayList<Item> list = new ArrayList<>();
		try {
			Session session = HibernateUtil.getSessionFactory(test).openSession();
			transaction = session.beginTransaction();
			@SuppressWarnings("unchecked")
			List<Item> result = session.createQuery("from Item where bought=" + bought).getResultList();
			for(Item item : result) {
				list.add(item);
				System.out.println("reading for one bought: " + item.getDescription());
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
	 * Method for reading Wishlist Items in the database whose connected person is null
	 * @return Item[] Array containing all Items with a null person in the database
	 */
	public Item[] readOwnItems() {
		ArrayList<Item> list = new ArrayList<>();
		try {
			Session session = HibernateUtil.getSessionFactory(test).openSession();
			transaction = session.beginTransaction();
			@SuppressWarnings("unchecked")
			List<Item> result = session.createQuery("from Item where person=null").getResultList();
			for(Item item : result) {
				list.add(item);
				System.out.println("reading for one own item: " + item.getDescription());
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
	 * Method for reading Wishlist Items in the database whose connected person is not null
	 * @return Item[] Array containing all Items with a not null person in the database
	 */
	public Item[] readItemsForOthers() {
		ArrayList<Item> list = new ArrayList<>();
		try {
			Session session = HibernateUtil.getSessionFactory(test).openSession();
			transaction = session.beginTransaction();
			@SuppressWarnings("unchecked")
			List<Item> result = session.createQuery("from Item where person is not null").getResultList();
			for(Item item : result) {
				list.add(item);
				System.out.println("reading for one gift item: " + item.getDescription());
			}
			transaction.commit();
		} catch (Exception e) {
			if (transaction != null) transaction.rollback();
			throw e;
		}
		Item[] items = new Item[list.size()];
		return (Item[])list.toArray(items);
	}
}
