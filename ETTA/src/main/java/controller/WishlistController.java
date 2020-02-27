package controller;

import model.Item;
import model.ItemDAO;
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
	 * Constructor
	 * @param gui WishlistTableGUI
	 */
	public WishlistController(WishlistTableGUI gui) {
		this.gui = gui;
	}
	
	/**
	 * Method for fetching the wishlist items from the database
	 * @return Item[] array containing all the wishlist items from the database
	 */
	public Item[] getItems() {
		return itemDAO.readItems();
	}

}
