package model;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public class SavingDAO {
	
	SessionFactory factory = null;
	Transaction transaction = null;
	
	public SavingDAO() {
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
	
	public boolean createSaving(Saving saving) {
		boolean success = false;
		try {
			Session session = factory.openSession();
			transaction = session.beginTransaction();
			session.saveOrUpdate(saving);
			transaction.commit();
			success = true;
			System.out.println(saving.getSaving_id());
			System.out.println(saving.getDescription());
			
		} catch (Exception e) {
			if (transaction != null) transaction.rollback();
			throw e;
		}
		return success;
	}
	
	public Saving[] readSavings() {
		ArrayList<Saving> list = new ArrayList<>();
		
		try  {
			Session session = factory.openSession();
			transaction = session.beginTransaction();
			@SuppressWarnings("unchecked")
			List<Saving> result = session.createQuery("from Saving").getResultList();
			for(Saving saving : result) {
				list.add(saving);

			}
			transaction.commit();
		} catch (Exception e) {
			if (transaction != null) transaction.rollback();
			throw e;
		}
		Saving[] savings = new Saving[list.size()];
		return (Saving[])list.toArray(savings);
	}
	
	public Saving readSaving(int saving_id) {
		Saving saving = new Saving();
		try {
			Session session = factory.openSession();
			transaction = session.beginTransaction();
			saving = (Saving)session.get(Saving.class, saving_id);		
			transaction.commit();
			System.out.println("reading one:" + saving.getDescription());
		}
		catch(Exception e){
			if (transaction!= null) transaction.rollback();
			throw e;
		}
		return saving;
	}

	public boolean updateSaving(Saving saving) {
		boolean success = false;
		try (Session session = factory.openSession()) {
			transaction = session.beginTransaction();
			session.update(saving);
			transaction.commit();
			success = true;
		} catch (Exception e) {
			if (transaction != null) transaction.rollback();
			throw e;
		}
		return success;
	}

	public boolean deleteSaving(int saving_id) {
		boolean success = false;
		try (Session session = factory.openSession()) {
			transaction = session.beginTransaction();
			Saving saving = (Saving)session.get(Saving.class, saving_id);
			session.delete(saving);
			transaction.commit();
			success = true;
		} catch (Exception e) {
			if (transaction != null) transaction.rollback();
			throw e;
		}
		return success;
	}

}
