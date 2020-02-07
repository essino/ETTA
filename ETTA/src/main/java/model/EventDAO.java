package model;

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
	SessionFactory sessionFactory= null;
	Session session;
	Transaction transaction = null;
	
	public EventDAO(){
		
		final StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure().build();
		
		sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
	}
	
	public Event[] readEvents() {
		List<Event> result;
		Event[] returnArray;
		try {
			session = sessionFactory.openSession();
			session.beginTransaction();
			result = session.createQuery( "from Event" ).list();
			for ( Event e : (List<Event>) result ) {
				System.out.println( "Event (" + e.getTitle() + ") : " + e.getStartDate() + ", " + e.getStartTime());
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
	
	public Event readEvent(int event_id) {
		
		Event event = new Event();
		try {
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			event = (Event)session.get(Event.class, event_id);		
			transaction.commit();
			System.out.println("reeding one:" + event.getTitle() + " startTime " + event.getStartTime());
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
	
	public boolean createEvent(Event event) {
		System.out.println("Event creating " + event.getTitle());
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
	
	public boolean updateEvent(Event event) {
		boolean updated =false;
		try {
			session = sessionFactory.openSession();
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
	
	public boolean deleteEvent(int event_id) {
		boolean deleted = false;
		// Tiedon haku Session.get-metodilla + poisto jos löytyi
		try {
			session = sessionFactory.openSession();
			session.beginTransaction();

			Event e = (Event)session.get(Event.class, event_id);
			if (e!= null) {
				session.delete(e);
				System.out.println(event_id + " deleted.");
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
