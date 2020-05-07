package model;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.query.Query;
/**
 * Data access object class for Category. Used in the creation of the database table for Category through Hibernate.
 */
public class CategoryDAO {

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
	public CategoryDAO() {
		
	}
	
	/**
	 * Constructor
	 * @param test boolean indicating whether the DAO is used for testing or not
	 */
	public CategoryDAO(boolean test) {
		if (test) {
			this.test = true;
		}
	}
	
	/**
	 * method for making a new Category in the database
	 * @param category Object that represents a category of income/expense
	 * @return success Boolean indicating the success or failure of the database transaction
	 */
	public boolean createCategory(Category category) {
		boolean success = false;
		try {
			Session session = HibernateUtil.getSessionFactory(test).openSession();
			transaction = session.beginTransaction();
			session.saveOrUpdate(category);
			System.out.println("id " + category.getCategory_id());
			transaction.commit();
			success = true;
		} catch (Exception e) {
			if (transaction != null) transaction.rollback();
			throw e;
		}
		return success;
	}
	
	/**
	 * method for reading one specific category from the database
	 * @param id the id of the category
	 * @return category object read from the database
	 */
	public Category readCategory(int id) {
		System.out.println("id in reading one " + id);
		Category category = new Category();
		try {
			Session session = HibernateUtil.getSessionFactory(test).openSession();
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
	
	/**
	 * method for reading one specific category from the database
	 * @param description of the category
	 * @return category object read from the database
	 */
	public Category readCategory(String description) {
		//System.out.println("id in reading one " + id);
		Category category = new Category();
		try {
			Session session = HibernateUtil.getSessionFactory(test).openSession();
			transaction = session.beginTransaction();
			List<Category>  result = session.createQuery( "from Category where description='" + description + "'" ).list();
			if(result.size() !=0) {
				category = result.get(0);
				transaction.commit();
				System.out.println("result " + result);
				System.out.println("reading one:" + category.getDescription());
			}
			else {
				category = null;
			}
					
			
		}
		catch(Exception e){
			if (transaction!= null) transaction.rollback();
			throw e;
		}
		return category;
	}
	
	/**
	 * method for reading all expense categories from the database
	 * @return Category[]  list of  category objects read from the database
	 */
	public Category[] readExpenseCategories() {
		ArrayList<Category> list = new ArrayList<>();
		try (Session session = HibernateUtil.getSessionFactory(test).openSession()) {
			transaction = session.beginTransaction();
			
			List<Category> result = session.createQuery("from Category where income=false").getResultList();
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
	
	/**
	 * method for reading all income categories from the database
	 * @return Category[]  list of  category objects read from the database
	 */
	public Category[] readIncomeCategories() {
		ArrayList<Category> list = new ArrayList<>();
		try (Session session = HibernateUtil.getSessionFactory(test).openSession()) {
			transaction = session.beginTransaction();
			
			List<Category> result = session.createQuery("from Category where income=true").getResultList();
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
	
	/**
	 * method for updating one Category in the database
	 * @param category Object that represents a category of income/expense
	 * @return success Boolean indicating the success or failure of the database transaction
	 */
	public boolean updateCategory(Category category) {
		boolean success = false;
		try (Session session = HibernateUtil.getSessionFactory(test).openSession()) {
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
	
	/**
	 * method for deleting one Category from the database
	 * @param id 
	 * @return success Boolean indicating the success or failure of the database transaction
	 */
	public boolean deleteCategory(int id) {
		boolean success = false;
		try (Session session = HibernateUtil.getSessionFactory(test).openSession()) {
			transaction = session.beginTransaction();
			Category category = (Category)session.get(Category.class, id);
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
