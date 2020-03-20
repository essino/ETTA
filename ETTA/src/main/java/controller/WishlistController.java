package controller;

import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Event;
import model.EventDAO;
import model.Item;
import model.ItemDAO;
import model.Person;
import model.PersonDAO;
import view.wishlist.WishlistAddGUI;
import view.wishlist.WishlistEditGUI;
import view.wishlist.WishlistTableGUI;


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
	 * Reference to the WishlistAddGUI
	 */
	private WishlistAddGUI addGui;
	
	/**
	 * Reference to the WishlistEditGUI
	 */
	private WishlistEditGUI editGui;
	
	/**
	 * PersonDAO used for accessing the database
	 */
	PersonDAO personDAO = new PersonDAO();
	
	/**
	 * EventDAO used for accessing the database
	 */
	private EventDAO eventDAO = new EventDAO();
	
	/**
	 * The input check class used for validating user input
	 */
	InputCheck inputCheck = new InputCheck();

	public Item selectedItem = new Item();

	/**
	 * Constructor
	 * @param gui WishlistTableGUI
	 */
	public WishlistController(WishlistTableGUI gui) {
		this.gui = gui;
	}
	
	/**
	 * Constructor
	 * @param gui WishlistAddGUI
	 */
	public WishlistController(WishlistAddGUI gui) {
		this.addGui = gui;
	}
	
	/**
	 * Constructor
	 * @param gui WishlistEditGUI
	 */
	public WishlistController(WishlistEditGUI gui) {
		this.editGui = gui;
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
	
	/** 
	 * Method for creating a new item and saving it to the database
	 */ 
	public void saveItem() {
		Item item = new Item();
		item.setDescription(addGui.getItemDesc());
		Person person = personDAO.readPerson(addGui.getItemPerson());
		item.setPerson(person);
		if (inputCheck.isInputEmpty(addGui.getItemPrice())) {
			item.setPrice(null);
		} else {
			item.setPrice(Double.parseDouble(addGui.getItemPrice()));
		}
		item.setDateNeeded(addGui.getItemDate());
		item.setBought(false);
		item.setAdditionalInfo(addGui.getItemAdditional());
		itemDAO.createItem(item);
		if(addGui.getItemDate() != null) {
			Event wishlistEvent = new Event();
			wishlistEvent.setStartDate(addGui.getItemDate());
			wishlistEvent.setEndDate(addGui.getItemDate());
			wishlistEvent.setFullday(true);
			wishlistEvent.setTitle("Buy " + addGui.getItemDesc() + " for " + addGui.getItemPerson());
			wishlistEvent.setLocation(null);
			wishlistEvent.setRecurring(false);
			wishlistEvent.setCalendar("default");
			eventDAO.createEvent(wishlistEvent); 
		}
	}
	
	/** 
	 * Method for deleting an item from the database
	 */ 
	public void removeItem() {
		itemDAO.deleteItem(gui.getSelectedItem().getItem_id());
	}
	
	/** 
	 * Method for marking an item as bought
	 */ 
	public void setBought() {
		Item item = itemDAO.readItem(gui.getSelectedItem().getDescription());
		item.setBought(true);
		itemDAO.updateItem(item);
	}
	
	public Item getItem() {
		System.out.println("selected wishlist item " + gui.getSelectedItem().getDescription());
		return itemDAO.readItem(gui.getSelectedItem().getDescription());
	}

	public void updateItem(Item editedItem) {
		itemDAO.updateItem(editedItem);		
	}
}
