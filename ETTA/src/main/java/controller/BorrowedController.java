package controller;

import java.sql.Date;
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
import view.borrowed.BorrowedGUI;
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
	 * Method that gets Persons from PersonDAO and makes a list containing names only 
	 * @return ObservableList<String> names - list of persons' names
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
	 * BorrowedThingDAo used for accessing the database
	 */ 
	private BorrowedThingDAO borrowedThingDAO = new BorrowedThingDAO();
	
	/** 
	 * Reference to BorrowedGUI to introduce the overall GUI to the controller
	 */ 
	private BorrowedGUI gui;
	
	/** 
	 * Reference to BorrowedTableGUI to introduce the GUI in charge of the list of borrowed things to the controller
	 */ 
	private BorrowedTableGUI tableGUI;
	
	/** 
	 * Reference to BorrowedAddGUI to introduce the GUI in charge of adding borrowed items
	 */ 
	private BorrowedAddGUI addGUI;
	
	
	/**
	 * Constructor to create controller for BorrowedThings
	 *@param gui the general gui for BorrowedThing
	 */
	public BorrowedController(BorrowedGUI gui) {
		this.gui = gui;
	}
	
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
	 * Method for creating a new borrowed thing and saving it to the database
	 * return BorrowedThing[] array contains all borrowed things
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
		System.out.println("person name " + person.getName());
		System.out.println("person id " + person.getPerson_id());
		borrowedThing.setPerson(person);
		borrowedThing.setDateBorrowed(addGUI.getBorrowedLoanDate());
		borrowedThing.setReturnDate(addGUI.getBorrowedReturnDate());
		borrowedThing.setReturned(false);
		borrowedThingDAO.createBorrowedThing(borrowedThing);
		//create a calendar event
		int lastEvent = eventDAO.readEvents().length; 
		int lastEventId = eventDAO.readEvents()[lastEvent-1].getEvent_id();
		Event borrowed = new Event();
		borrowed.setEvent_id(lastEventId+1);
		borrowed.setTitle(addGUI.getBorrowedPerson() + " should return " +  addGUI.getBorrowedDescription());
		borrowed.setLocation(null);
		borrowed.setStartDate(addGUI.getBorrowedReturnDate());
		borrowed.setEndDate(addGUI.getBorrowedReturnDate());
		borrowed.setFullday(true);
		borrowed.setRecurring(false);
		borrowed.setCalendar("borrowed");
		eventDAO.createEvent(borrowed);
	}
	
	/** 
	 * Method for deleting a borrowed thing from the database
	 */ 
	public void removeBorrowedThing() {
		String description = tableGUI.getSelectedBorrowedThing().getDescription();
		deleteBorrowedEvent(description); //deletes the borrowed event
		borrowedThingDAO.deleteBorrowedThing(tableGUI.getSelectedBorrowedThing().getThing_id());
		tableGUI.removeFromBorrowedTable(tableGUI.getSelectedBorrowedThing());
	}
	
	/** 
	 * Method for making the borrowed item returned
	 */ 
	public void markReturned() {
		String description = tableGUI.getSelectedBorrowedThing().getDescription();
		deleteBorrowedEvent(description); //deletes the borrowed event
		BorrowedThing borrowedThing = borrowedThingDAO.readBorrowedThing(tableGUI.getSelectedBorrowedThing().getThing_id());
		borrowedThing.setReturned(true);
		borrowedThingDAO.updateBorrowedThing(borrowedThing);
	}
	
	//deletes borrowed event
	/** 
	 * Method for deleting the "should return" event from events
	 */
	public void deleteBorrowedEvent(String description) {
		Event event = findRightEvent(description);
		try {
			int eventID = event.getEvent_id();
			eventDAO.deleteEvent(eventID);
		//if the borrowed thing has been returned, the event relating to it has been already deleted
		} catch(NullPointerException e) {
			System.out.println("No borrowing event to delete");
		}
		
	}
	
	//updates the return date in the borrowed event
	/** 
	 * Method for updating the return date in an event concerning the borrowed item
	 * @param borrowedThing the borrowed thing, the event of which is changed
	 */
	public void updateReturnDate(BorrowedThing borrowedThing) {
		
		Date returnDate = tableGUI.getSelectedBorrowedThing().getReturnDate();
		String description = borrowedThing.getDescription();
		System.out.println("Description of the borrowed thing" + description);
		
		Event updatingEvent = findRightEvent(description);
		try {
			updatingEvent.setStartDate(returnDate);
			updatingEvent.setEndDate(returnDate);
			eventDAO.updateEvent(updatingEvent);
		//if the borrowed thing has been returned, the event relating to it has been already deleted
		} catch(NullPointerException e) {
			System.out.println("No borrowing event to delete");
		}
	}
	
	//updates the title of the borrowed event - HARD CODED!
	/** 
	 * Method for updating the title of the borrowed event 
	 * @param oldDescription the description of the borrowed item that is being changed
	 */
	public void updateBorrowedEventTitle(String oldDescription) {
		String thingDescription = tableGUI.getSelectedBorrowedThing().getDescription();
		Event updatingEvent = findRightEvent(oldDescription);
		String newTitle = "";	
		try {
			String oldTitle = updatingEvent.getTitle();
			String[] titleParts = oldTitle.split(" ");
			int i = 0;
			while (!titleParts[i].equals("return")) {
				System.out.println("Titlepart " + i + " is " + titleParts[i]);
				i++;
			}
			for (int j = 0; j <= i; j++) {
				newTitle = newTitle + " " + titleParts[j];
			}
			newTitle = newTitle + " " + thingDescription;
			newTitle = newTitle.replaceFirst("^ *", "");
			
			updatingEvent.setTitle(newTitle);
			eventDAO.updateEvent(updatingEvent);
		//if the borrowed thing has been returned, the event relating to it has been already deleted
		} catch(NullPointerException e) {
			System.out.println("No borrowing event to delete");
		}
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
	 * @param description the description of the borrowed item, the event of which is being searched for
	 */
	public Event findRightEvent(String description) {
		//String loanDescription = tableGUI.getSelectedBorrowedThing().getDescription();
		//the person who has borrowed the item
		Person loanPerson = tableGUI.getSelectedBorrowedThing().getPerson();
		String eventTitle = loanPerson + " should return " + description;
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
}

