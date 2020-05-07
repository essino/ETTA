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
	private PersonDAO personDAO = new PersonDAO();
	
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
	 * Constructor to create controller for BorrowedThings without parameters
	 */
	public BorrowedController() {
	}
	
	/** 
	 * Constructor to create controller for BorrowedThings
	 *@param tableGUI BorrowedTableGUI for the BorrowedThing view 
	 */
	public BorrowedController(BorrowedTableGUI tableGUI) {
		this.tableGUI = tableGUI; 
	}
	
	/**
	 * Constructor to create controller for BorrowedThings
	 *@param addGUI BorrowedAddGUI for adding borrowed things
	 */
	public BorrowedController(BorrowedAddGUI addGUI) {
		this.addGUI = addGUI;
	}
	
	/**
	 * Constructor to create controller for BorrowedThings
	 *@param returnedGUI BorrowedReturnedGUI for showing returned items
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
	 * @return names - ObervableList of persons' names
	 */ 
	public ObservableList<String> personsList() {
		Person[] people = personDAO.readPeople();
		ArrayList<String> peopleNames = new ArrayList<String>();
		for (Person person : people){
			peopleNames.add(person.getName());
		}
		ObservableList<String> names =  FXCollections.observableArrayList(peopleNames);
		return names;
	}
	
	/** 
	 * Method for creating a new borrowed thing and saving it to the database
	 * @return BorrowedThing[] array containing all borrowed things
	 */ 
	public BorrowedThing[] getBorrowedThings() {
		return borrowedThingDAO.readBorrowedThings();
	}
	
	/** 
	 * Method for creating a new borrowed thing and saving it to the database
	 */ 
	public void saveBorrowedThing() {
		//creating a new borrowed thing
		BorrowedThing borrowedThing= new BorrowedThing();
		//getting the user's input and setting it in the new borrowed thing
		borrowedThing.setDescription(addGUI.getBorrowedDescription());
		Person person = personDAO.readPerson(addGUI.getBorrowedPerson());
		borrowedThing.setPerson(person);
		borrowedThing.setDateBorrowed(addGUI.getBorrowedLoanDate());
		borrowedThing.setReturnDate(addGUI.getBorrowedReturnDate());
		borrowedThing.setReturned(false);
		//Borrowed thing created in the database with DAO
		borrowedThingDAO.createBorrowedThing(borrowedThing);
		//the related event created
		createBorrowedEvent(borrowedThing);
	}
	
	/** 
	 * Method for creating an event relating to a borrowed item
	 * @param borrowedThing BorrowedThing that the event concerns
	 * @return boolean indicating whether or not the event has been successfully created
	 */ 
	public boolean createBorrowedEvent(BorrowedThing borrowedThing) {
		return calendarController.createBorrowedEvent(borrowedThing);
	}
	
	/** 
	 * Method for deleting a borrowed thing from the database
	 */ 
	public void removeBorrowedThing() {
		BorrowedThing thing = tableGUI.getSelectedBorrowedThing();
		deleteBorrowedEvent(thing); //deletes the borrowed event 
		borrowedThingDAO.deleteBorrowedThing(tableGUI.getSelectedBorrowedThing().getThing_id());
	}
	
	/** 
	 * Method for deleting a returned thing from the database
	 */ 
	public void removeReturnedThing() {
		try {
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
			deleteBorrowedEvent(tableGUI.getSelectedBorrowedThing()); //deletes the borrowed event
			BorrowedThing borrowedThing = borrowedThingDAO.readBorrowedThing(tableGUI.getSelectedBorrowedThing().getThing_id());
			borrowedThing.setReturned(true);
			borrowedThingDAO.updateBorrowedThing(borrowedThing);
		} catch (NullPointerException e) {
			System.out.println("No item selected.");
		}
	}
	
	/** 
	 * Method for making the returned item borrowed
	 */ 
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
	 * @param borrowedThing  BorrowedThing that the event concerns
	 * @return true if the event has been successfully deleted, false if the event hasn't been deleted
	 */
	public boolean deleteBorrowedEvent(BorrowedThing borrowedThing) {
		return calendarController.deleteBorrowedEvent(borrowedThing);
	}
	
	//updates the return date in the borrowed event
	/** 
	 * Method for updating the return date in an event concerning the borrowed item
	 * @param borrowedThing BorrowedThing, the event of which is changed
	 */
	public void updateReturnDate(BorrowedThing borrowedThing) {
		calendarController.updateBorrowedDate(borrowedThing);
	}
	
	//updates the title of the borrowed event 
	/** 
	 * Method for updating the title of the borrowed event 
	 * @param oldDescription String describing the borrowed item that is being changed
	 * @param editedBorrowedThing BorrowedThing the borrowed thing that is being updated
	 */
	public void updateBorrowedEventTitle(String oldDescription, BorrowedThing editedBorrowedThing) {
		calendarController.updateBorrowedTitle(oldDescription, editedBorrowedThing);
	}
	
	/** 
	 * Method for updating the borrowed item
	 * @param borrowedThing BorrowedThing that is being updated
	 */
	public void updateBorrowedThing(BorrowedThing borrowedThing) {
		borrowedThingDAO.updateBorrowedThing(borrowedThing);
	}
	
	//used for updating or deleting the right borrowed event
	/** 
	 * Method for finding the borrowed event based on the description of the borrowed item
	 * @param thing  BorrowedThing, the event of which is being searched for
	 * @return event that calendarController found
	 */
	public Event findRightEvent(BorrowedThing thing) {
		return calendarController.findBorrowedEvent(thing);
	}
	
	/** 
	 * Method for finding the right person
	 * @param name String matching the person that is being searched for
	 * @return the found person
	 */
	public Person findPerson(String name) {
		return personDAO.readPerson(name);
	}

	//updating borrowed event if person changes
	/** 
	 * Method for updating the event is the person relating to it is changed
	 * @param oldPerson Person who is changed
	 * @param editedBorrowedThing BorrowedThing the event and borrower of which are being changed
	 * @return true if the event has been successfully updated, false if the event hasn't been successfully updated
	 */
	public boolean updateBorrowedEventPerson(Person oldPerson, BorrowedThing editedBorrowedThing) {
		return calendarController.updateBorrowedEventPerson(oldPerson, editedBorrowedThing);
	}
	
}

