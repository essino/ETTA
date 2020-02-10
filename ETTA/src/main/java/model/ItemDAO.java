package model;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public class ItemDAO {
	SessionFactory factory = null;
	Transaction transaction = null;
	
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
	
	protected void finalize() {
		factory.close();
	}
	
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
	
	public Item[] readItems() {
		ArrayList<Item> list = new ArrayList<>();
		try {
			Session session = factory.openSession();
			transaction = session.beginTransaction();
			@SuppressWarnings("unchecked")
			List<Item> result = session.createQuery("from item").getResultList();
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
