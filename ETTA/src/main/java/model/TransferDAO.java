package model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

/**
 * Data access object class for Transfers. Used in accessing the table in the database.
 */
public class TransferDAO {
	
	/**
	 * SessionFactory object needed to open session with the database
	 */
	SessionFactory factory = null;
	/**
	 * Transaction object to carry out database transactions
	 */
	Transaction transaction = null;
	
	/**
	 * Constructor for TransferDAO
	 */
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
	
	/**
	 * Method for closing the database connection
	 */
	protected void finalize() {
		factory.close();
	}
	
	/**
	 * Method for creating a new Transfer in the database
	 * @param transfer Transfer the transfer object to be added to the database
	 * @return success Boolean indicating the success or failure of the database transaction
	 */
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
	
	/**
	 * Method for reading one specific Transfer in the database
	 * @param transfer_id int the id of the Transfer to be read
	 * @return success Boolean indicating the success or failure of the database transaction
	 */
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
	
	/**
	 * Method for reading all Transfers in the database
	 * @return Transfer[] array with all the transfers in the database
	 */
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

	/**
	 * Method for reading all expenses in the database
	 * @return Transfer[] array with all the expense  transfers in the database
	 */
	public Transfer[] readExpenses() {
		ArrayList<Transfer> list = new ArrayList<>();
		try {
			Session session = factory.openSession();
			transaction = session.beginTransaction();
			@SuppressWarnings("unchecked")
			List<Transfer> result = session.createQuery("from Transfer where income=false order by date desc").getResultList();
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
	
	/**
	 * Method for reading all incomes in the database
	 * @return Transfer[] array with all the income  transfers in the database
	 */
	public Transfer[] readIncome() {
		ArrayList<Transfer> list = new ArrayList<>();
		try {
			Session session = factory.openSession();
			transaction = session.beginTransaction();
			@SuppressWarnings("unchecked")
			List<Transfer> result = session.createQuery("from Transfer where income=false order by date desc").getResultList();
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
	
	/**
	 * Method for updating a Transfer in the database
	 * @param transfer Transfer the updated Transfer object
	 * @return success Boolean indicating the success or failure of the database transaction
	 */
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

	/**
	 * Method for deleting a Transfer from the database
	 * @param transfer_id int the id of the Transfer to be deleted
	 * @return success Boolean indicating the success or failure of the database transaction
	 */
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