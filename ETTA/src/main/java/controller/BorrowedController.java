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
	
	private BorrowedThingDAO borrowedThingDAO = new BorrowedThingDAO();
	
	private BorrowedGUI gui;
	
	private BorrowedTableGUI tableGUI;
	
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
		borrowedThingDAO.deleteBorrowedThing(tableGUI.getSelectedBorrowedThing().getThing_id());
		tableGUI.removeFromBorrowedTable(tableGUI.getSelectedBorrowedThing());
	}
}

