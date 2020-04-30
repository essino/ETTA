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
	MyBundle myBundle = new MyBundle();
	
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
	PersonDAO personDAO = new PersonDAO();
	
	/**
	 * EventDAO used for accessing the database
	 */
	private EventDAO eventDAO = new EventDAO();
	
	/**
	 * The input check class used for validating user input
	 */
	InputCheck inputCheck = new InputCheck();

	/**
	 * Constructor
	 * @param gui WishlistAddGUI
	 */
	public WishlistController(WishlistAddGUI gui) {
		this.addGui = gui;
	}
	
	/**
	 * Constructor
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
	 * @return ObservableList<String> names - list of persons' names
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
	
	public boolean createWishlistEvent(Item item) {
		Event wishlistEvent = new Event();
		wishlistEvent.setStartDate(item.getDateNeeded());
		wishlistEvent.setEndDate(item.getDateNeeded());
		wishlistEvent.setFullday(true);
		if (item.getPerson() != null) {
			wishlistEvent.setTitle("Buy " + item.getDescription() + " for " + item.getPerson().getName());
		} else {
			wishlistEvent.setTitle("Buy " + item.getDescription() + " for myself");
		}
		wishlistEvent.setLocation(null);
		wishlistEvent.setRecurring(false);
		wishlistEvent.setCalendar("wishlist");
		return eventDAO.createEvent(wishlistEvent); 
	}
	
	/** 
	 * Method for getting the item currently selected in a gui
	 * @param gui AbstractWishlistGUI the gui whose selected item is wanted
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
		Event event = findEvent(getSelectedItem(gui));
		if (event != null) {
			eventDAO.deleteEvent(event.getEvent_id());
		}
		itemDAO.deleteItem(getSelectedItem(gui).getItem_id());
	}
	
	/** 
	 * Method for marking an item as bought
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
		Event event = findEvent(editedItem);
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
	 */ 
	public Person findPerson(String name) {
		return personDAO.readPerson(name);
	}
	
	/** 
	 * Method for finding the event connected to the selected item from the database
	 * @param item the Item the event is connected to
	 */ 
	public Event findEvent(Item item) {
		String description = item.getDescription();
		//the person who has borrowed the item
		Person person = item.getPerson();
		String eventTitle = "Buy " + description + " for " + person;
		Event[] events = eventDAO.readEvents();
		for (int i = 0; events.length > i; i++) {
			if (events[i].getTitle().equals(eventTitle)) {
				Event event = events[i];
				return event;
			} 
		}
		return null;
	}
}
