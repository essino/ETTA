package controller;

import java.sql.Date;

import model.Event;
import model.EventDAO;
import model.Person;
import model.PersonDAO;
import view.ContactsGUI;

public class ContactsController {
	
	/**
	 * Reference to the ContactsGUI
	 */
	private ContactsGUI conGUI;
	/**
	 * PersonDAO used for accessing the database
	 */
	private PersonDAO perDAO = new PersonDAO();
	/**
	 * EventDAO used for accessing the database
	 */
	private EventDAO eventDAO = new EventDAO();
	

	/**
	 * Method make person witch have information of name, address, e-mail and birthday
	 * 
	 */

	/** 
	 * Method that gets person details from ContactsGUI, 
	 * creates a new Person, and gives it forward to PersonDAO to add it to the database.
	 * If there's a birthday added to person details, creates an yearly event 
	 * and gives it forward to EventDAO to add to the database
	 */ 

	public void savePerson() {
		String personName = conGUI.getPersonName();
		String personAddress = conGUI.getPersonAddress();
		String personEmail = conGUI.getPersonEmail();
		Date personBirthday = conGUI.getPersonBirthday();
		Person person = new Person (personName, personBirthday, personEmail);
		Boolean personCreate = perDAO.createPerson(person);
		//if there's a birthday added to person details, create an yearly event 
		if(personBirthday != null) {
			Event birthday = new Event();
			birthday.setTitle(personName);
			birthday.setStartDate(personBirthday);
			birthday.setEndDate(personBirthday);
			birthday.setFullday(true);
			birthday.setRecurring(true);
			birthday.setRrule("RRULE:FREQ=YEARLY;");
			birthday.setCalendar("birthdays");
			eventDAO.createEvent(birthday);
		}
	}
	

	/** 
	 * Constructor 
	 * @param ContactsGUI 
	 */

	public ContactsController(ContactsGUI conGUI) {
		this.conGUI = conGUI;
	}
	
}
