package model;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;


/**
 * Data access object class for Transfers. Used in accessing the table in the database.
 */
public class TransferDAO {
	
	/**
	 * Transaction object to carry out database transactions
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
	public TransferDAO() {
		
	}
	
	/**
	 * Constructor
	 * @param test boolean indicating whether the DAO is used for testing or not
	 */
	public TransferDAO(boolean test) {
		if (test) {
			this.test = true;
		}
	}
	
	/**
	 * Method for creating a new Transfer in the database
	 * @param Transfer the transfer object to be added to the database
	 * @return Boolean indicating the success or failure of the database transaction
	 */
	public boolean createTransfer(Transfer transfer) {
		boolean success = false;
		try (Session session = HibernateUtil.getSessionFactory(test).openSession()) {
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
	 * @param transfer_id - int the id of the Transfer to be read
	 * @return transfer - the found Transfer from the database
	 */
	public Transfer readTransfer(int transfer_id) {
		Transfer transfer = new Transfer();
		try {
			Session session = HibernateUtil.getSessionFactory(test).openSession();
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
	 * Method for reading selected Transfers in the database, transfers are filtered by the dates
	 * @param dateStart - the earliest date of the transfers
	 * @param dateEnd - the latest date of the transfers
	 * @return Transfer[] array with all the transfers in the database
	 */
	public Transfer[] readSeletedTransfers(Date dateStart, Date dateEnd) {
		ArrayList<Transfer> list = new ArrayList<>();
		try {
			Session session = HibernateUtil.getSessionFactory(test).openSession();
			transaction = session.beginTransaction();
			@SuppressWarnings("unchecked")
			List<Transfer> result = session.createQuery("from Transfer where date between '" +dateStart+ "' and '"+dateEnd + "'").getResultList();
			for(Transfer transfer : result) {
				list.add(transfer);
				System.out.println("reading selected: " + transfer.getDescription());
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
	 * Method for reading all Transfers in the database
	 * @return Transfer[] array with all the transfers in the database
	 */
	public Transfer[] readTransfers() {
		ArrayList<Transfer> list = new ArrayList<>();
		try {
			Session session = HibernateUtil.getSessionFactory(test).openSession();
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
			Session session = HibernateUtil.getSessionFactory(test).openSession();
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
			Session session = HibernateUtil.getSessionFactory(test).openSession();
			transaction = session.beginTransaction();
			@SuppressWarnings("unchecked")
			List<Transfer> result = session.createQuery("from Transfer where income=true order by date desc").getResultList();
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
			Session session = HibernateUtil.getSessionFactory(test).openSession();
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
			Session session = HibernateUtil.getSessionFactory(test).openSession();
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