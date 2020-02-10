package model;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public class CategoryDAO {
	SessionFactory factory = null;
	Transaction transaction = null;
	
	public CategoryDAO() {
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
	
	public boolean createCategory(Category category) {
		boolean success = false;
		try {
			Session session = factory.openSession();
			transaction = session.beginTransaction();
			session.saveOrUpdate(category);
			transaction.commit();
			success = true;
		} catch (Exception e) {
			if (transaction != null) transaction.rollback();
			throw e;
		}
		return success;
	}
	
	public Category readCategory(int id) {
		Category category = new Category();
		try {
			Session session = factory.openSession();
			transaction = session.beginTransaction();
			category = (Category)session.get(Category.class, id);		
			transaction.commit();
			System.out.println("reading one:" + category.getDescription());
		}
		catch(Exception e){
			if (transaction!= null) transaction.rollback();
			throw e;
		}
		return category;
	}
	
	public Category[] readCategories() {
		ArrayList<Category> list = new ArrayList<>();
		try (Session session = factory.openSession()) {
			transaction = session.beginTransaction();
			
			List<Category> result = session.createQuery("from Category").getResultList();
			for(Category category : result) {
				list.add(category);
				System.out.println(category.getDescription());
			}
			transaction.commit();
		} catch (Exception e) {
			if (transaction != null) transaction.rollback();
			throw e;
		}
		Category[] categories = new Category[list.size()];
		return (Category[])list.toArray(categories);
	}

	public boolean updateCategory(Category category) {
		boolean success = false;
		try (Session session = factory.openSession()) {
			transaction = session.beginTransaction();
			session.update(category);
			transaction.commit();
			success = true;
		} catch (Exception e) {
			if (transaction != null) transaction.rollback();
			throw e;
		}
		return success;
	}

	public boolean deleteCategory(String description) {
		boolean success = false;
		try (Session session = factory.openSession()) {
			transaction = session.beginTransaction();
			Category category = (Category)session.get(Category.class, description);
			session.delete(category);
			transaction.commit();
			success = true;
		} catch (Exception e) {
			if (transaction != null) transaction.rollback();
			throw e;
		}
		return success;
	}
}
