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
import res.MyBundle;
import view.wishlist.AbstractWishlistGUI;
import view.wishlist.WishlistAddGUI;

public class WishlistController {

	/**
	 * MyBundle object for setting the right resource bundle to localize the application
	 */
	private MyBundle myBundle = new MyBundle();
	
	/**
	 * ItemDAO used for accessing the database
	 */
	private ItemDAO itemDAO = new ItemDAO();
	
	/**
	 * Reference to the WishlistAddGUI
	 */
	private WishlistAddGUI addGui;
	
	/**
	 * PersonDAO used for accessing the database
	 */
	private PersonDAO personDAO = new PersonDAO();
	
	/**
	 * EventDAO used for accessing the database
	 */
	private EventDAO eventDAO = new EventDAO();
	
	/**
	 * The input check class used for validating user input
	 */
	private InputCheck inputCheck = new InputCheck();

	/** 
	 * Calendar controller object
	 */ 
	private CalendarController calendarController = new CalendarController();
	
	/**
	 * Constructor
	 * @param gui WishlistAddGUI
	 */
	public WishlistController(WishlistAddGUI gui) {
		this.addGui = gui;
	}
	
	/**
	 * Constructor without parameters
	 */
	public WishlistController() {
		
	}

	//constructor used for tests
	public WishlistController(ItemDAO itemDAO2, PersonDAO personDAO2, EventDAO eventDAO2) {
		this.itemDAO = itemDAO2;
		this.personDAO = personDAO2;
		this.eventDAO = eventDAO2;
	}

	/**
	 * Method for fetching all the wishlist items from the database
	 * @return Item[] array containing all the wishlist items from the database
	 */
	public Item[] getItems() {
		return itemDAO.readItems();
	} 
	
	/**
	 * Method for fetching all own items from the database
	 * @return Item[] array containing the wishlist items from the database
	 */
	public Item[] getOwnItems() {
		return itemDAO.readOwnItems(); 
	}
	
	/**
	 * Method for fetching the wishlist items meant as gifts for other people from the database
	 * @return Item[] array containing the wishlist items from the database
	 */
	public Item[] getItemsForOthers() {
		return itemDAO.readItemsForOthers();
	}
	
	/**
	 * Method for fetching all the bought/not bought wishlist items from the database
	 * @param bought boolean indicating whether to get the bought or not bought items from the database
	 * @return Item[] array containing all bought/not bought wishlist items from the database
	 */
	public Item[] getBoughtItems(boolean bought) {
		return itemDAO.readItemsByBought(bought);
	}
	
	/** 
	 * Method that gets Persons from PersonDAO and makes a list containing names only 
	 * @return names -ObservableList<String> list of persons' names
	 */ 
	public ObservableList<String> personsList() {
		Person[] people = personDAO.readPeople();
		ArrayList<String> peopleNames = new ArrayList<String>();
		peopleNames.add(myBundle.getBundle().getString("Me"));
		for (Person person : people){
			peopleNames.add(person.getName());
		}
		ObservableList<String> names =  FXCollections.observableArrayList(peopleNames);
		return names;
	}
	
	/** 
	 * Method for reading all people in the database
	 * @return Person[] the people in the database
	 */ 
	public Person[] getPeople() {
		return personDAO.readPeople();
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
			createWishlistEvent(item);
		}
	}
	
	/** 
	 * Method for creating a new wishlist event for an item
	 * @return true if creation succeeded, false if creation failed
	 */ 
	public boolean createWishlistEvent(Item item) {
		return calendarController.createWishlistEvent(item); 
	}
	
	/** 
	 * Method for getting the item currently selected in a gui
	 * @param gui AbstractWishlistGUI the gui whose selected item is wanted
	 * @return Item selected item
	 */ 
	public Item getSelectedItem(AbstractWishlistGUI gui) {
		return gui.getSelectedItem();
	}
	
	/** 
	 * Method for deleting an item from the database
	 * Also removes the event connected to the item if there is one
	 * @param gui AbstractWishlistGUI the gui calling this method
	 */ 
	public void removeItem(AbstractWishlistGUI gui) {
		Event event = calendarController.findWishlistEvent(getSelectedItem(gui));
		if (event != null) {
			eventDAO.deleteEvent(event.getEvent_id());
		}
		itemDAO.deleteItem(getSelectedItem(gui).getItem_id());
	}
	
	/** 
	 * Method for marking an item as bought
	 * @param gui AbstractWishlistGUI abstract gui implementation
	 */ 
	public void setBought(AbstractWishlistGUI gui) {
		Item item = itemDAO.readItem(getSelectedItem(gui).getDescription());
		item.setBought(true);
		itemDAO.updateItem(item);
	}

	/** 
	 * Method for updating an item
	 * Also updates the event connected to the item if there is one
	 * @param editedItem the item with the updated information
	 */ 
	public void updateItem(Item editedItem) {
		Event event = calendarController.findWishlistEvent(editedItem);
		System.out.println(event);
		if (event != null) {
			event.setStartDate(editedItem.getDateNeeded());
			event.setEndDate(editedItem.getDateNeeded());
			event.setTitle("Buy " + editedItem.getDescription() + " for " + editedItem.getPerson().getName());
			eventDAO.updateEvent(event);
		}
		itemDAO.updateItem(editedItem);		
	}
	
	/** 
	 * Method for finding a specific person from the database
	 * @param name - the name of the Person to look for 
	 * @return Person the person being searched for
	 */ 
	public Person findPerson(String name) {
		return personDAO.readPerson(name);
	}
}
