package controller;

import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Person;
import model.PersonDAO;

//essi trying out: starts
/*
import model.BorrowedThing;
import model.BorrowedThingDAO;
import model.Item;
import model.ItemDAO;
import view.BorrowedTableGUI;
*/
//essi trying out: ends
import view.WishlistTableGUI;

public class BorrowedController {
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
	
	//essi: all changes in comments, also in borrowedtablegui class
	//essi trying out: starts
	/*
	
	private BorrowedThingDAO borrowedThingDAO = new BorrowedThingDAO();
	
	private BorrowedTableGUI gui;
	
	public BorrowedController(BorrowedTableGUI gui) {
		this.gui = gui;
	}
	
	public BorrowedController() {
	}
	

	public BorrowedThing[] getBorrowedThings() {
		return borrowedThingDAO.readBorrowedThings();
	}
	*/
	//essi trying out: ends
	
}

