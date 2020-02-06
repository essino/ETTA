package model;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public class BalanceDAO {
	SessionFactory factory = null;
	Transaction transaction = null;
	
	public BalanceDAO() {
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
	
	public boolean createBalance(Balance balance) {
		boolean success = false;
		try {
			Session session = factory.openSession();
			transaction = session.beginTransaction();
			session.saveOrUpdate(balance);
			transaction.commit();
			success = true;
			System.out.println("Creating balance: " + balance.getBalance() + " id " + balance.getId());
		} catch (Exception e) {
			if (transaction != null) transaction.rollback();
			throw e;
		}
		return success;
	}
	
	public boolean updateBalance(Balance balance) {
		boolean success = false;
		try (Session session = factory.openSession()) {
			transaction = session.beginTransaction();
			session.update(balance);
			transaction.commit();
			success = true;
			System.out.println("Updating balance, new balance " + balance.getBalance() + " id " + balance.getId());
		} catch (Exception e) {
			if (transaction != null) transaction.rollback();
			throw e;
		}
		return success;
	}
	
	public float readBalance(int balance_id) {
		Balance balance = new Balance();
		try (Session session = factory.openSession()) {
			transaction = session.beginTransaction();
			balance = (Balance)session.get(Balance.class, balance_id);		
			transaction.commit();
			System.out.println("reading balance:" + balance.getBalance() + " id " + balance.getId());
		}
		catch(Exception e){
			if (transaction!=null) transaction.rollback();
			throw e;
		}
		return balance.getBalance();
	}
}
