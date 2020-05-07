package model;

import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

/**
 * Utility class for creating the SessionFactory object needed for connecting to the database
 */
public class HibernateUtil {

	/**
	 * SessionFactory object needed to open session with the database
	 */
	private static SessionFactory factory = null;
	private static SessionFactory testFactory = null;
	
	/**
	 * Method for creating (if it doesn't already exist) and getting the SessionFactory object
	 * @param test boolean indicating whether a session factory is used for tests or not
	 * @return SessionFactory 
	 */
	public static SessionFactory getSessionFactory(boolean test) {
		if (test) {
			if (testFactory == null) {
				StandardServiceRegistry registry = null;
				try {
					// Create registry
					registry = new StandardServiceRegistryBuilder().configure("hibernate-test.cfg.xml").build();
					// Create SessionFactory
					testFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
				} catch (Exception e) {
					System.out.println("Creation of test session factory failed");
					StandardServiceRegistryBuilder.destroy(registry);
					e.printStackTrace();
					System.exit(-1);
				}
			}
			return testFactory;
		} else {
			if (factory == null) {
				StandardServiceRegistry registry = null;
				try {
					// Create registry
					registry = new StandardServiceRegistryBuilder().configure().build();
					// Create SessionFactory
					factory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
				} catch (Exception e) {
					System.out.println("Creation of session factory failed");
					StandardServiceRegistryBuilder.destroy(registry);
					e.printStackTrace();
					System.exit(-1);
				}
			}
			return factory;	
		}
	}
        
}