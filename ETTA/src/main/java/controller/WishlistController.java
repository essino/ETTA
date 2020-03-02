package controller;

import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Item;
import model.ItemDAO;
import model.Person;
import model.PersonDAO;
import view.WishlistTableGUI;

/**
 * Controller class for the wishlist page
 */
public class WishlistController {

	/**
	 * ItemDAO used for accessing the database
	 */
	private ItemDAO itemDAO = new ItemDAO();
	
	/**
	 * Reference to the WishlistTableGUI
	 */
	private WishlistTableGUI gui;
	
	/**
	 * PersonDAO used for accessing the database
	 */
	PersonDAO personDAO = new PersonDAO();
	
	/**
	 * Constructor
	 * @param gui WishlistTableGUI
	 */
	public WishlistController(WishlistTableGUI gui) {
		this.gui = gui;
	}
	
	/**
	 * Constructor
	 */
	public WishlistController() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * Method for fetching the wishlist items from the database
	 * @return Item[] array containing all the wishlist items from the database
	 */
	public Item[] getItems() {
		return itemDAO.readItems();
	}
	
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

}
