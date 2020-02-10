package model;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public class TransferDAO {
	SessionFactory factory = null;
	Transaction transaction = null;
	
	public TransferDAO() {
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
	
	public boolean createTransfer(Transfer transfer) {
		boolean success = false;
		try (Session session = factory.openSession()) {
			transaction = session.beginTransaction();
			session.saveOrUpdate(transfer);
			transaction.commit();
			success = true;
			System.out.println("creating: " + transfer.getTransfer_id());
			System.out.println("creating: " + transfer.getDescription());
		} catch (Exception e) {
			if (transaction != null) transaction.rollback();
			throw e;
		}
		return success;
	}
	
	public Transfer readTransfer(int transfer_id) {
		Transfer transfer = new Transfer();
		try {
			Session session = factory.openSession();
			transaction = session.beginTransaction();
			transfer = (Transfer)session.get(Transfer.class, transfer_id);		
			transaction.commit();
			System.out.println("reading one:" + transfer.getDescription());
		}
		catch(Exception e){
			if (transaction!= null) transaction.rollback();
			throw e;
		}
		return transfer;
	}
	
	public Transfer[] readTransfers() {
		ArrayList<Transfer> list = new ArrayList<>();
		try {
			Session session = factory.openSession();
			transaction = session.beginTransaction();
			@SuppressWarnings("unchecked")
			List<Transfer> result = session.createQuery("from Transfer").getResultList();
			for(Transfer transfer : result) {
				list.add(transfer);
				System.out.println("reading all: " + transfer.getDescription());
			}
			transaction.commit();
		} catch (Exception e) {
			if (transaction != null) transaction.rollback();
			throw e;
		}
		Transfer[] transfers = new Transfer[list.size()];
		return (Transfer[])list.toArray(transfers);
	}

	public boolean updateTransfer(Transfer transfer) {
		boolean success = false;
		try {
			Session session = factory.openSession();
			transaction = session.beginTransaction();
			session.update(transfer);
			transaction.commit();
			success = true;
		} catch (Exception e) {
			if (transaction != null) transaction.rollback();
			throw e;
		}
		return success;
	}

	public boolean deleteTransfer(int transfer_id) {
		boolean success = false;
		try {
			Session session = factory.openSession();
			transaction = session.beginTransaction();
			Transfer transfer = (Transfer)session.get(Transfer.class, transfer_id);
			session.delete(transfer);
			transaction.commit();
			success = true;
		} catch (Exception e) {
			if (transaction != null) transaction.rollback();
			throw e;
		}
		return success;
	}
}