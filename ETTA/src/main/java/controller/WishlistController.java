package controller;

import model.Item;
import model.ItemDAO;
import view.WishlistGUI;

public class WishlistController {

	private ItemDAO itemDAO = new ItemDAO();
	private WishlistGUI gui;
	private Item[] items = itemDAO.readItems();
	
	public WishlistController(WishlistGUI wishlistGUI) {
		this.gui = wishlistGUI;
	}
	
	public String[] getPeopleNames() {
		String[] names = new String[items.length];
		for(int i=0;i<items.length;i++) {
			names[i] = items[i].getPerson().getName();
		}
		return names;
	}
	
	public Item[] getItems() {
		return items;
	}

}
