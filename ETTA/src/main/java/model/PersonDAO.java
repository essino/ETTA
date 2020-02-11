package model;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public class PersonDAO {
	SessionFactory factory = null;
	Transaction transaction = null;
	
	public PersonDAO() {
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
	
	public boolean createPerson(Person person) {
		boolean success = false;
		try (Session session = factory.openSession()) {
			transaction = session.beginTransaction();
			session.saveOrUpdate(person);
			transaction.commit();
			success = true;
			System.out.println("creating: " + person.getPerson_id());
			System.out.println("creating: " + person.getName());
		} catch (Exception e) {
			if (transaction != null) transaction.rollback();
			throw e;
		}
		return success;
	}
	
	public Person readPerson(int person_id) {
		Person person = new Person();
		try {
			Session session = factory.openSession();
			transaction = session.beginTransaction();
			person = (Person)session.get(Person.class, person_id);		
			transaction.commit();
			System.out.println("reading one:" + person.getName());
		}
		catch(Exception e){
			if (transaction!= null) transaction.rollback();
			throw e;
		}
		return person;
	}
	
	public Person[] readPeople() {
		ArrayList<Person> list = new ArrayList<>();
		try (Session session = factory.openSession()) {
			transaction = session.beginTransaction();
			@SuppressWarnings("unchecked")
			List<Person> result = session.createQuery("from Person").getResultList();
			for(Person person : result) {
				list.add(person);
				System.out.println("reading all: " + person.getName());
			}
			transaction.commit();
		} catch (Exception e) {
			if (transaction != null) transaction.rollback();
			throw e;
		}
		Person[] people = new Person[list.size()];
		return (Person[])list.toArray(people);
	}

	public boolean updatePerson(Person person) {
		boolean success = false;
		try (Session session = factory.openSession()) {
			transaction = session.beginTransaction();
			session.update(person);
			transaction.commit();
			success = true;
		} catch (Exception e) {
			if (transaction != null) transaction.rollback();
			throw e;
		}
		return success;
	}

	public boolean deletePerson(int person_id) {
		boolean success = false;
		try {
			Session session = factory.openSession();
			transaction = session.beginTransaction();
			Person person = (Person)session.get(Person.class, person_id);
			session.delete(person);
			transaction.commit();
			success = true;
		} catch (Exception e) {
			if (transaction != null) transaction.rollback();
			throw e;
		}
		return success;
	}
}



