package model;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 * Data access object class for Languages. Used in accessing the table in the database.
 */
public class LanguageDAO {
	
	/**
	 * Transaction object to carry out database transactions
	 */
	private Transaction transaction = null;
	/**
	 * Boolean indicating whether the DAO should connect to the test database or not
	 * Default value false
	 */
	private boolean test = false;
	
	/**
	 * Construction without parameters
	 */
	public LanguageDAO() {
		
	}
	
	/**
	 * Constructor
	 * @param test boolean indicating whether the DAO is used for testing or not
	 */
	public LanguageDAO(boolean test) {
		if (test) {
			this.test = true;
		}
	}
	
	/**
	 * Method for reading one specific language in the database
	 * @param language_id int the id of the Language to be read
	 * @return language read from the database
	 */
	public Language readLanguage(int language_id) {
		Language language = new Language();
		try {
			Session session = HibernateUtil.getSessionFactory(test).openSession();
			transaction = session.beginTransaction();
			language = (Language)session.get(Language.class, language_id);		
			transaction.commit();
			System.out.println("reading one:" + language.getDescription());
		} catch(Exception e) {
			if (transaction!= null) transaction.rollback();
			throw e;
		}
		return language;
	}
	
	/**
	 * Method for reading all Languages in the database
	 * @return Language[] array with all the languages in the database
	 */
	public Language[] readLanguages() {
		ArrayList<Language> list = new ArrayList<>();
		try {
			Session session = HibernateUtil.getSessionFactory(test).openSession();
			transaction = session.beginTransaction();
			@SuppressWarnings("unchecked")
			List<Language> result = session.createQuery("from Language").getResultList();
			for(Language lang : result) {
				list.add(lang);
				System.out.println("reading all: " + lang.getDescription());
			}
			transaction.commit();
		} catch (Exception e) {
			if (transaction != null) transaction.rollback();
			throw e;
		}
		Language[] langs = new Language[list.size()];
		return (Language[])list.toArray(langs);
	}
	
	/**
	 * Method for retrieving a language from the database
	 * @return language Language from the database if transaction is successful
	 */
	public Language getSelectedLanguage() {
		Language language = new Language();
		try {
			Session session = HibernateUtil.getSessionFactory(test).openSession();
			transaction = session.beginTransaction();
			List<Language> result;
			result = session.createQuery( "from Language l where l.chosen=true").getResultList();	
			language = result.get(0);
			transaction.commit();
		} catch(Exception e) {
			if (transaction!= null) transaction.rollback();
			//throw e;
			return null;
		}
		return language;
	}

	/**
	 * Method for creating a new language in the database
	 * @param language Language that is to be created in the database
	 * @return success Boolean indicating the success or failure of the database transaction
	 */
	public boolean createLanguage(Language language) {
		boolean success = false;
		try (Session session = HibernateUtil.getSessionFactory(test).openSession()) {
			transaction = session.beginTransaction();
			session.saveOrUpdate(language);
			transaction.commit();
			success = true;
		} catch (Exception e) {
			if (transaction != null) transaction.rollback();
			throw e;
		}
		return success;
	}
	
	/**
	 * Method for updating a language in the database
	 * @param language Language to be updated
	 * @return success Boolean indicating the success or failure of the database transaction
	 */
	public boolean updateLanguage(Language language) {
		boolean success = false;
		try (Session session = HibernateUtil.getSessionFactory(test).openSession()) {
			transaction = session.beginTransaction();
			session.update(language);
			transaction.commit();
			success = true;
		} catch (Exception e) {
			if (transaction != null) transaction.rollback();
			throw e;
		}
		return success;
	}

	/**
	 * Method for updating a language in the database
	 * @param newLangName String naming the language
	 * @return language Language that is read from the database
	 */
	public Language readLanguage(String newLangName) {
		Language language = new Language();
		try {
			Session session = HibernateUtil.getSessionFactory(test).openSession();
			transaction = session.beginTransaction();
			List<Language> result;
			result = session.createQuery( "from Language l where l.description='" + newLangName +"'").getResultList();	
			language = result.get(0);
			transaction.commit();
		} catch(Exception e) {
			if (transaction!= null) transaction.rollback();
			throw e;
		}
		return language;
	}
	
	/**
	 * Method for deleting a language in the database
	 * @param language Language to be deleted
	 * @return success Boolean indicating the success or failure of the database transaction
	 */
	public boolean deleteLanguage(Language language) {
		boolean success = false;
		try (Session session = HibernateUtil.getSessionFactory(test).openSession()) {
			transaction = session.beginTransaction();
			session.delete(language);
			transaction.commit();
			success = true;
		} catch (Exception e) {
			if (transaction != null) transaction.rollback();
			throw e;
		}
		return success;
	}
}
