package controller;

import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Person;
import model.PersonDAO;
import model.BorrowedThing;
import model.BorrowedThingDAO;
import model.Event;
import model.EventDAO;
import view.borrowed.BorrowedAddGUI;
import view.borrowed.BorrowedReturnedTableGUI;
import view.borrowed.BorrowedTableGUI;

/** 
 * Controller class for the Borrowed things part.   
 * 
 */
public class BorrowedController {
	
	/**
	 * PersonDAO used for accessing the database
	 */
	PersonDAO personDAO = new PersonDAO();
	
	/**
	 * EventDAO used for accessing the database
	 */
	private EventDAO eventDAO = new EventDAO();
	
	/** 
	 * BorrowedThingDAo used for accessing the database
	 */ 
	private BorrowedThingDAO borrowedThingDAO = new BorrowedThingDAO();
	
	/** 
	 * Reference to BorrowedTableGUI to introduce the GUI in charge of the list of borrowed things to the controller
	 */ 
	private BorrowedTableGUI tableGUI;
	
	/** 
	 * Reference to BorrowedAddGUI to introduce the GUI in charge of adding borrowed items
	 */ 
	private BorrowedAddGUI addGUI;
	
	/** 
	 * Reference to BorrowedReturnedTableGUI to introduce the GUI showing returned items
	 */ 
	private BorrowedReturnedTableGUI returnedGUI;
	
	/** 
	 * Calendar controller object
	 */ 
	private CalendarController calendarController = new CalendarController();
	
	/**
	 * Constructor to create controller for BorrowedThings
	 */
	public BorrowedController() {
	}
	
	/**
	 * Constructor to create controller for BorrowedThings
	 *@param tableGUI the gui for the BorrowedThing view
	 */
	public BorrowedController(BorrowedTableGUI tableGUI) {
		this.tableGUI = tableGUI;
	}
	
	/**
	 * Constructor to create controller for BorrowedThings
	 *@param addGUI the gui for adding borrowed things
	 */
	public BorrowedController(BorrowedAddGUI addGUI) {
		this.addGUI = addGUI;
	}
	
	/**
	 * Constructor to create controller for BorrowedThings
	 *@param returnedGUI the gui for showing returned items
	 */
	public BorrowedController(BorrowedReturnedTableGUI returnedGUI) {
		this.returnedGUI = returnedGUI;
	}
	
	//constructor used for tests
	/**
	 * Constructor to create controller for BorrowedThings for testing
	 *@param personDAO2 used for accessing the database
	 *@param borrowedThingDAO2 used for accessing the database
	 *@param eventDAO2 used for accessing the database
	 */
	public BorrowedController(PersonDAO personDAO2, BorrowedThingDAO borrowedThingDAO2, EventDAO eventDAO2) {
		this.personDAO = personDAO2;
		this.borrowedThingDAO = borrowedThingDAO2;
		this.eventDAO = eventDAO2;
	}

	/** 
	 * Method that gets Persons from PersonDAO and makes a list containing names only 
	 * @return names - list of persons' names
	 */ 
	public ObservableList<String> personsList() {
		Person[] people = personDAO.readPeople();
		ArrayList peopleNames = new ArrayList();
		for (Person person : people){
			peopleNames.add(person.getName());
		}
		ObservableList<String> names =  FXCollections.observableArrayList(peopleNames);
		return names;
	}
	
	/** 
	 * Method for creating a new borrowed thing and saving it to the database
	 * return borrowedThingDAO.readBorrowedThings() BorrowedThing[] array contains all borrowed things
	 */ 
	public BorrowedThing[] getBorrowedThings() {
		return borrowedThingDAO.readBorrowedThings();
	}
	
	/** 
	 * Method for creating a new borrowed thing and saving it to the database
	 */ 
	public void saveBorrowedThing() {
		BorrowedThing borrowedThing= new BorrowedThing();
		borrowedThing.setDescription(addGUI.getBorrowedDescription());
		Person person = personDAO.readPerson(addGUI.getBorrowedPerson());
		borrowedThing.setPerson(person);
		borrowedThing.setDateBorrowed(addGUI.getBorrowedLoanDate());
		borrowedThing.setReturnDate(addGUI.getBorrowedReturnDate());
		borrowedThing.setReturned(false);
		borrowedThingDAO.createBorrowedThing(borrowedThing);
		createBorrowedEvent(borrowedThing);
	}
	
	/** 
	 * Method for creating a event relating to a borrowed item
	 * @param borrowedThing the item that the event concerns
	 * @return eventDAO.createEvent(borrowed) boolean indicating whether or not the event has been successfully created
	 */ 
	public boolean createBorrowedEvent(BorrowedThing borrowedThing) {
		Event borrowed = new Event();
		borrowed.setTitle(borrowedThing.getPerson().getName() + " should return " +  borrowedThing.getDescription());
		borrowed.setLocation(null);
		borrowed.setStartDate(borrowedThing.getReturnDate());
		borrowed.setEndDate(borrowedThing.getReturnDate());
		borrowed.setFullday(true);
		borrowed.setRecurring(false);
		borrowed.setCalendar("borrowed");
		return eventDAO.createEvent(borrowed);
	}
	
	/** 
	 * Method for deleting a borrowed thing from the database
	 */ 
	public void removeBorrowedThing() {
		BorrowedThing thing = tableGUI.getSelectedBorrowedThing();
		String description = tableGUI.getSelectedBorrowedThing().getDescription();
		deleteBorrowedEvent(thing); //deletes the borrowed event 
		borrowedThingDAO.deleteBorrowedThing(tableGUI.getSelectedBorrowedThing().getThing_id());
	}
	
	/** 
	 * Method for deleting a returned thing from the database
	 */ 
	public void removeReturnedThing() {
		try {
			String description = returnedGUI.getSelectedBorrowedThing().getDescription();
			borrowedThingDAO.deleteBorrowedThing(returnedGUI.getSelectedBorrowedThing().getThing_id());
		} catch(NullPointerException e) {
			System.out.println("No returned item selected.");
		}
	}
	
	/** 
	 * Method for making the borrowed item returned
	 */ 
	public void markReturned() {
		try {
			String description = tableGUI.getSelectedBorrowedThing().getDescription();
			deleteBorrowedEvent(tableGUI.getSelectedBorrowedThing()); //deletes the borrowed event
			BorrowedThing borrowedThing = borrowedThingDAO.readBorrowedThing(tableGUI.getSelectedBorrowedThing().getThing_id());
			borrowedThing.setReturned(true);
			borrowedThingDAO.updateBorrowedThing(borrowedThing);
			
		} catch (NullPointerException e) {
			System.out.println("No item selected.");
		}
	}
	
	public void changeReturnedToBorrowed() {
		try {
			BorrowedThing returnedThing = borrowedThingDAO.readBorrowedThing(returnedGUI.getSelectedBorrowedThing().getThing_id());
			returnedThing.setReturned(false);
			borrowedThingDAO.updateBorrowedThing(returnedThing);
			createBorrowedEvent(returnedThing);
		} catch (NullPointerException e) {
			System.out.println("No item selected.");
		}
	}
	
	//deletes borrowed event
	/** 
	 * Method for deleting the "should return" event from events
	 * @return deleted whether or not the event has been successfully deleted
	 */
	public boolean deleteBorrowedEvent(BorrowedThing borrowedThing) {
		boolean deleted = false;
		Event event = findRightEvent(borrowedThing);
		try {
			int eventID = event.getEvent_id();
			deleted = eventDAO.deleteEvent(eventID);
		//if the borrowed thing has been returned, the event relating to it has already been deleted
		} catch(NullPointerException e) {
			System.out.println("No borrowing event to delete");
		}
		return deleted;
	}
	
	//updates the return date in the borrowed event
	/** 
	 * Method for updating the return date in an event concerning the borrowed item
	 * @param borrowedThing the borrowed thing, the event of which is changed
	 */
	public void updateReturnDate(BorrowedThing borrowedThing) {
		calendarController.updateBorrowedDate(borrowedThing);
	}
	
	//updates the title of the borrowed event 
	/** 
	 * Method for updating the title of the borrowed event 
	 * @param String oldDescription the description of the borrowed item that is being changed
	 * @param BorrowedThing editedBorrowedThing  the borrowed thing that is being updated
	 */
	public void updateBorrowedEventTitle(String oldDescription, BorrowedThing editedBorrowedThing) {
		calendarController.updateBorrowedTitle(oldDescription, editedBorrowedThing);
	}
	
	/** 
	 * Method for updating the borrowed item
	 * @param borrowedThing the borrowed thing that is being updated
	 */
	public void updateBorrowedThing(BorrowedThing borrowedThing) {
		borrowedThingDAO.updateBorrowedThing(borrowedThing);
	}
	
	//used for updating or deleting the right borrowed event - HARD CODED!
	/** 
	 * Method for finding the borrowed event based on the description of the borrowed item
	 * @param BorrowedThing  borrowed item, the event of which is being searched for
	 * @return event the right event is returned
	 */
	public Event findRightEvent(BorrowedThing thing) {
		//the person who has borrowed the item
		Person loanPerson = thing.getPerson();
		String eventTitle = loanPerson + " should return " + thing.getDescription();
		Event[] loanEvent = eventDAO.readEvents();
		for (int i = 0; loanEvent.length > i; i++) {
			if (loanEvent[i].getTitle().equals(eventTitle)) {
				Event event = loanEvent[i];
				return event;
			} 
		}
		return null;
	}
	
	/** 
	 * Method for finding the right person
	 * @param name the name matching the person that is being searched for
	 */
	public Person findPerson(String name) {
		return personDAO.readPerson(name);
	}

	//updating borrowed event if person changes
	/** 
	 * Method for updating the event is the person relating to it is changed
	 * @param oldPerson the person who is changed
	 * @param editedBorrowedThing the borrowed thing the event and borrower of which are being changed
	 * @return updated whether or not the event has been successfully updated
	 */
	public boolean updateBorrowedEventPerson(Person oldPerson, BorrowedThing editedBorrowedThing) {
		boolean updated = false;
		String oldEvent = oldPerson.getName() + " should return " + editedBorrowedThing.getDescription();
		Event event = findBorrowedEvent(oldEvent);
		if (event!=null) {
			event.setTitle(editedBorrowedThing.getPerson().getName() + " should return " + editedBorrowedThing.getDescription());
			updated = eventDAO.updateEvent(event);
		}
		return updated;
	}
	
	/** 
	 * Method for finding the borrowed event based on the name of the event
	 * @param description the name of the event, the event of which is being searched for
	 * @return loanEvent the event that has to do with the borrowing event
	 */
	public Event findBorrowedEvent(String description) {
		Event loanEvent = eventDAO.readBorrowed(description);
		return loanEvent;
	}
	
}

