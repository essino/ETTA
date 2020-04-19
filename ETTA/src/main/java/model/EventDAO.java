package model;

import java.sql.Date;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

/**
 * Data access object class for Events. Used in the communication with the database table for Events through Hibernate.
 */
public class EventDAO {

	Session session;
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
	public EventDAO() {
		
	}
	
	/**
	 * Constructor
	 * @param test boolean indicating whether the DAO is used for testing or not
	 */
	public EventDAO(boolean test) {
		if (test) {
			this.test = true;
		}
	}
	
	/**
	 * method for reading all events from the database
	 * @return Event[]  list of  event objects read from the database
	 */
	public Event[] readEvents() {
		Transaction transaction = null;
		List<Event> result;
		Event[] returnArray;
		try {
			session = HibernateUtil.getSessionFactory(test).openSession();
			session.beginTransaction();
			result = session.createQuery( "from Event order by event_id" ).list();
			for ( Event e : (List<Event>) result ) {
				//System.out.println( "Event (" + e.getTitle() + ") : " + e.getStartDate() + ", " + e.getStartTime());
			}
			session.getTransaction().commit();
			returnArray = new Event[result.size()];
		}
		catch(Exception e){
			if (transaction!=null) transaction.rollback();
				throw e;
			}	
		finally{
			session.close();
		}
		
		return result.toArray(returnArray);
	}
	/**
	 * method for reading one event from the database
	 * @param id the id of the event
	 * @return event object read from the database
	 */
	public Event readEvent(int event_id) {
		Transaction transaction = null;
		Event event = new Event();
		try {
			session = HibernateUtil.getSessionFactory(test).openSession();
			transaction = session.beginTransaction();
			event = (Event)session.get(Event.class, event_id);	
			
			transaction.commit();
			if(event == null) {
				return null;
			}
			//System.out.println("reading one:" + event.getTitle() + " startTime " + event.getStartTime());
		}
		catch(Exception e){
			if (transaction!=null) transaction.rollback();
			throw e;
		}
		finally{
			session.close();
		}
		return event;
	}
	
	/**
	 * method for making a new Event in the database
	 * @param event Object that represents an event
	 * @return created Boolean indicating the success or failure of the database transaction
	 */
	public boolean createEvent(Event event) {
		//System.out.println("Event creating " + event.getTitle() + " calendar " + event.getCalendar());
		
		boolean created = false;
		Transaction transaction = null;

		try{
			session = HibernateUtil.getSessionFactory(test).openSession();
			transaction = session.beginTransaction();
			session.saveOrUpdate(event);
			transaction.commit();
			created = true;
		}
		catch(Exception e){
			if (transaction!=null) transaction.rollback();
			throw e;
		}
		finally{
			session.close();
		}
		return created;
	}
	
	/**
	 * method for updating an event in the database
	 * @param event Object that represents an event
	 * @return updated Boolean indicating the success or failure of the database transaction
	 */
	public boolean updateEvent(Event event) {
		boolean updated =false;
		try {
			session = HibernateUtil.getSessionFactory(test).openSession();
			transaction = session.beginTransaction();	
			session.update(event);
			transaction.commit();
			updated = true;
		}
		catch(Exception e){
			if (transaction!=null) transaction.rollback();
			throw e;
		}
		finally{
			session.close();
		}
		return updated;
	}
	
	/**
	 * method for updating an event in the database
	 * @param description String the title of the event
	 * @return updated Boolean indicating the success or failure of the database transaction
	 */
	/*
	public boolean updateEvent(String description) {
		boolean updated =false;
		try {
			session = HibernateUtil.getSessionFactory(test).openSession();
			session.beginTransaction();
			List<Event> result;
			result = session.createQuery( "from Event  e where e.title='" + description).getResultList();
			System.out.println("result " + result.toString());
			Event e = result.get(0);
			System.out.println("event_id found " + e.getEvent_id());
			session.update(e);
			System.out.println(e.getEvent_id() + " updated.");
			session.getTransaction().commit();
			updated = true;
		}
		catch(Exception e){
			if (transaction!=null) transaction.rollback();
			throw e;
		}
		finally{
			session.close();
		}
		return updated;
	}*/
	
	/**
	 * method for deleting one Event from the database
	 * @param id the id of the event
	 * @return deleted Boolean indicating the success or failure of the database transaction
	 */
	public boolean deleteEvent(int event_id) {
		boolean deleted = false;
		// Tiedon haku Session.get-metodilla + poisto jos löytyi
		try {
			session = HibernateUtil.getSessionFactory(test).openSession();
			session.beginTransaction();

			Event e = (Event)session.get(Event.class, event_id);
			if (e!= null) {
				session.delete(e);
				//System.out.println(event_id + " deleted.");
				deleted = true;
			}
			else {
				System.out.println("Ei löydy listalta.");
			}
			session.getTransaction().commit();
		}
		catch(Exception e){
			if (transaction!=null) transaction.rollback();
			throw e;
		}
		finally{
			session.close();
		}
		return deleted;
	}
	
	/**
	 * method for reading events only belonging to the calendar recieved as parameter from the database
	 * @return Event[]  list of  event objects read from the database
	 */
	public Event[] readEventsFromOneCalendar(String calendar, boolean test) {
		List<Event> result;
		Event[] returnArray;
		try {
			session = HibernateUtil.getSessionFactory(test).openSession();
			session.beginTransaction();
			if (test) {
				result = session.createQuery( "from Event where calendar='"+ calendar + "'").list();
			} else {
				result = session.createQuery( "from Event where calendar="+ calendar).list();
			}
			
			/*for ( Event e : (List<Event>) result ) {
				//System.out.println( "Event (" + e.getTitle() + ") : " + e.getStartDate() + ", " + e.getStartTime());
			}*/
			session.getTransaction().commit();
			returnArray = new Event[result.size()];
		}
		catch(Exception e){
			if (transaction!=null) transaction.rollback();
				throw e;
			}	
		finally{
			session.close();
		}
		
		return result.toArray(returnArray);
	}
	
	/**
	 * method for reading today's events from the database
	 * @return Event[]  list of  event objects read from the database
	 */
	public Event[] readTodaysEvents() {
		Transaction transaction = null;
		List<Event> result;
		Event[] returnArray;
		try {
			session = HibernateUtil.getSessionFactory(test).openSession();
			session.beginTransaction();
			result = session.createQuery( "from Event where startDate=current_date()").list();
			session.getTransaction().commit();
			returnArray = new Event[result.size()];
		}
		catch(Exception e){
			if (transaction!=null) transaction.rollback();
				throw e;
			}	
		finally{
			session.close();
		}	
		return result.toArray(returnArray);
	}

	public boolean deleteBirthday(String name) {
		boolean deleted = false;
		try {
			session = HibernateUtil.getSessionFactory(test).openSession();
			session.beginTransaction();
			List<Event> result;
			result = session.createQuery( "from Event  e where e.title='" + name + "' and calendar='birthdays'").getResultList();
			System.out.println("result " + result.toString());
			try  {
				Event e = result.get(0);
				session.delete(e);
				System.out.println(e.getEvent_id() + " deleted.");
				deleted = true;
			}
			catch(IndexOutOfBoundsException e) {
				System.out.println("Ei löydy listalta.");
			}
			session.getTransaction().commit();
			
		}
		catch(Exception e){
			if (transaction!=null) transaction.rollback();
			throw e;
		}
		finally {
			session.close();
		}
		return deleted;
	}
	
	public Event readBirthday(String name) {
		Event event =null;
		try {
			session = HibernateUtil.getSessionFactory(test).openSession();
			session.beginTransaction();
			List<Event> result;
			result = session.createQuery( "from Event  e where e.title='" + name + "' and calendar='birthdays'").getResultList();
			System.out.println("result " + result.toString());
			try  {
				event = result.get(0);
			}
			catch(IndexOutOfBoundsException e) {
				System.out.println("Ei löydy listalta.");
			}
			session.getTransaction().commit();
			
		}
		catch(Exception e){
			if (transaction!=null) transaction.rollback();
			throw e;
		}
		finally {
			session.close();
		}
		return event;
	}
	
	public Event readBorrowed(String description) {
		Event event =null;
		try {
			session = HibernateUtil.getSessionFactory(test).openSession();
			session.beginTransaction();
			List<Event> result;
			result = session.createQuery( "from Event  e where e.title='" + description + "' and calendar='borrowed'").getResultList();
			System.out.println("result " + result.toString());
			try  {
				event = result.get(0);
			}
			catch(IndexOutOfBoundsException e) {
				System.out.println("Ei löydy listalta.");
			}
			session.getTransaction().commit();
			
		}
		catch(Exception e){
			if (transaction!=null) transaction.rollback();
			throw e;
		}
		finally {
			session.close();
		}
		return event;
	}
	
}
