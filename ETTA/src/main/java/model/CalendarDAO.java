package model;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public class CalendarDAO {
	
	/**
	 * Transaction object to carry out database transaction
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
	public CalendarDAO() {
		
	}
	
	/**
	 * Constructor
	 * @param test boolean indicating whether the DAO is used for testing or not
	 */
	public CalendarDAO(boolean test) {
		if (test) {
			this.test = true;
		}
	}
	
	/**
	 * method for making a new Calendar in the database
	 * @param calendar Object that represents a calendar
	 * @return success Boolean indicating the success or failure of the database transaction
	 */
	public boolean createCalendar(Calendar calendar) {
		boolean success = false;
		try {
			Session session = HibernateUtil.getSessionFactory(test).openSession();
			transaction = session.beginTransaction();
			session.saveOrUpdate(calendar);
			transaction.commit();
			success = true;
		} catch (Exception e) {
			if (transaction != null) transaction.rollback();
			throw e;
		}
		return success;
	}
	
	/**
	 * method for reading one specific calendar from the database
	 * @param id the id of the calendar
	 * @return calendar object read from the database
	 */
	public Calendar readCalendar(int id) {
		Calendar calendar = new Calendar();
		try {
			Session session = HibernateUtil.getSessionFactory(test).openSession();
			transaction = session.beginTransaction();
			calendar = (Calendar)session.get(Calendar.class, id);		
			transaction.commit();
			System.out.println("reading one:" + calendar.getName());
		}
		catch(Exception e){
			if (transaction!= null) transaction.rollback();
			throw e;
		}
		return calendar;
	}
	
	/**
	 * method for reading all calendars from the database
	 * @return Calendar[]  list of  calendar objects read from the database
	 */
	public Calendar[] readCalendars() {
		ArrayList<Calendar> list = new ArrayList<>();
		try (Session session = HibernateUtil.getSessionFactory(test).openSession()) {
			transaction = session.beginTransaction();
			
			List<Calendar> result = session.createQuery("from Calandar").getResultList();
			for(Calendar calendar : result) {
				list.add(calendar);
				System.out.println(calendar.getName());
			}
			transaction.commit();
		} catch (Exception e) {
			if (transaction != null) transaction.rollback();
			throw e;
		}
		Calendar[] calendars = new Calendar[list.size()];
		return (Calendar[])list.toArray(calendars);
	}
	
	/**
	 * method for updating one Calendar in the database
	 * @param calendar Object that represents a calendar
	 * @return success Boolean indicating the success or failure of the database transaction
	 */
	public boolean updateCategory(Calendar calendar) {
		boolean success = false;
		try (Session session = HibernateUtil.getSessionFactory(test).openSession()) {
			transaction = session.beginTransaction();
			session.update(calendar);
			transaction.commit();
			success = true;
		} catch (Exception e) {
			if (transaction != null) transaction.rollback();
			throw e;
		}
		return success;
	}
	
	/**
	 * method for deleting one Calendar from the database
	 * @param name 
	 * @return success Boolean indicating the success or failure of the database transaction
	 */
	public boolean deleteCalendar(String name) {
		boolean success = false;
		try (Session session = HibernateUtil.getSessionFactory(test).openSession()) {
			transaction = session.beginTransaction();
			Calendar calendar = (Calendar)session.get(Calendar.class, name);
			session.delete(calendar);
			transaction.commit();
			success = true;
		} catch (Exception e) {
			if (transaction != null) transaction.rollback();
			throw e;
		}
		return success;
	}
}
