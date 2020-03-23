package controller;

import java.sql.Date;

import model.BorrowedThing;
import model.BorrowedThingDAO;
import model.Event;
import model.EventDAO;
import model.Item;
import model.ItemDAO;
import model.Person;
import model.PersonDAO;
import view.contacts.ContactsGUI;
import view.contacts.ContactsTableGUI;

/** 
 * Controller class for the contacts part.  
 * 
 */
public class ContactsController {
	
	/**
	 * Reference to the ContactsGUI
	 */
	private ContactsGUI conGUI;
	/**
	 * Reference to the ContactsTableGUI
	 */
	private ContactsTableGUI conTableGUI;
	/**
	 * PersonDAO used for accessing the database
	 */
	private PersonDAO perDAO = new PersonDAO();
	/**
	 * EventDAO used for accessing the database
	 */
	private EventDAO eventDAO = new EventDAO();
	/**
	 * BorrowedThingDAO used for accessing the database
	 */
	private BorrowedThingDAO borrowedDAO = new BorrowedThingDAO();
	/**
	 * ItemDAO used for accessing the database
	 */
	private ItemDAO wishlistDAO = new ItemDAO();
	

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
			int lastEvent = eventDAO.readEvents().length; 
			int lastEventId = eventDAO.readEvents()[lastEvent-1].getEvent_id();
			Event birthday = new Event();
			birthday.setEvent_id(lastEventId+1);
			birthday.setTitle(personName);
			birthday.setLocation(null);
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
	 * Method that gets the selected person from contactsTableGUI, 
	 * tells PersonDAO to delete the person from the database 
	 * and contactsTableGUI to delete it from the tableView.
	 */ 
	public void deletePerson() {
		perDAO.deletePerson(conTableGUI.personToDelete().getPerson_id());
		conTableGUI.removeFromTable(conTableGUI.personToDelete());
	}
	
	/** 
	 * Method that gets Persons from PersonDAO and returns a list containing persons's details 
	 * @return Person[] list of persons
	 */ 
	public Person[] getPeople() {
		return perDAO.readPeople();
	}

	/** 
	 * Constructor 
	 * @param ContactsGUI 
	 */
	public ContactsController(ContactsGUI conGUI) {
		this.conGUI = conGUI;
	}

	/** 
	 * Constructor 
	 */
	public ContactsController() {
	}

	/** 
	 * Constructor 
	 * @param ContactsTableGUI 
	 */
	public ContactsController(ContactsTableGUI contactsTableGUI) {
		this.conTableGUI = contactsTableGUI;
	}
	
	public boolean checkIfContactUsed(){
		if(borrowedDAO.readBorrowedThingsByPerson(conTableGUI.personToDelete().getPerson_id()).length!=0 ||
				wishlistDAO.readItemsByPerson(conTableGUI.personToDelete().getPerson_id()).length !=0) {
			return true;
		}
		else {
			return false;
		}
	}

	public void deletePersonAndEvents() {
		for(BorrowedThing bt : borrowedDAO.readBorrowedThingsByPerson(conTableGUI.personToDelete().getPerson_id())) {
			borrowedDAO.deleteBorrowedThing(bt.getThing_id());
		}
		for(Item i : wishlistDAO.readItemsByPerson(conTableGUI.personToDelete().getPerson_id())) {
			wishlistDAO.deleteItem(i.getItem_id());
		}
		deletePerson();
	}
	
}
