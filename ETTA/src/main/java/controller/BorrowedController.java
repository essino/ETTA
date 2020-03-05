package controller;

import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Person;
import model.PersonDAO;

//essi trying out: starts

import model.BorrowedThing;
import model.BorrowedThingDAO;
import model.Item;
import model.ItemDAO;
import view.BorrowedTableGUI;
import view.BorrowedAddGUI;
import view.BorrowedGUI;

//essi trying out: ends


public class BorrowedController {
	
	/**
	 * PersonDAO used for accessing the database
	 */
	PersonDAO personDAO = new PersonDAO();
	
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
	}
}

