package model;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.joda.time.LocalDate;

/**
 * Data access object class for Events. Used in the communication with the database table for Events through Hibernate.
 */
public class EventDAO {
	/**
	 * SessionFactory object needed to open session with the database
	 */
	SessionFactory sessionFactory= null;
	Session session;
	/**
	 * Transaction object to carry out database transaction
	 */
	Transaction transaction = null;
	
	public EventDAO(){
		
		final StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure().build();
		
		sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
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
			session = sessionFactory.openSession();
			session.beginTransaction();
			result = session.createQuery( "from Event" ).list();
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
			session = sessionFactory.openSession();
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
			session = sessionFactory.openSession();
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
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();	
			session.update(event);
			transaction.commit();
			//System.out.println("changed" + event.getEvent_id());
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
	 * method for deleting one Category from the database
	 * @param id the id of the event
	 * @return deleted Boolean indicating the success or failure of the database transaction
	 */
	public boolean deleteEvent(int event_id) {
		boolean deleted = false;
		// Tiedon haku Session.get-metodilla + poisto jos löytyi
		try {
			session = sessionFactory.openSession();
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
	public Event[] readEventsFromOneCalendar(String calendar) {
		List<Event> result;
		Event[] returnArray;
		try {
			session = sessionFactory.openSession();
			session.beginTransaction();
			result = session.createQuery( "from Event where calendar="+ calendar).list();
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
	 * method for reading today's events from the database
	 * @return Event[]  list of  event objects read from the database
	 */
	public Event[] readTodaysEvents() {
		Transaction transaction = null;
		List<Event> result;
		Event[] returnArray;
		try {
			session = sessionFactory.openSession();
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
	
	/**
	 * method for closing the database session
	 */
	public void finalize() { // destruktori 
		try { 
			// oli sama yhteys koko sovelluksen ajan 
			if (sessionFactory != null) {// vapauttaa muutkin resurssit 
				sessionFactory.close(); 
				System.out.println("Tietokanta suljettu");
			}
		}catch (Exception e) { 
				e.printStackTrace();  
		} 
	}
}
