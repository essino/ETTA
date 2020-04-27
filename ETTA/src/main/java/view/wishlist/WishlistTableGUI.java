package view.wishlist;

import controller.CalendarController;
import controller.InputCheck;
import controller.WishlistController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import model.Item;
import res.MyBundle;

public class WishlistTableGUI extends AbstractWishlistGUI {
	
	/**
	 * MyBundle object for setting the right resource bundle to localize the application
	 */
	MyBundle myBundle = new MyBundle();
	/**
	 * Reference to the used WishlistController
	 */
	WishlistController controller = new WishlistController();
	
	/**
	 * The anchor pane view from where adding, editing and deleting can be started
	 */
	@FXML
	AnchorPane wishlistanchorpane;
	
	/**
	 * Table view for showing the wishlist items
	 */
	@FXML
	TableView<Item> wishlisttable;
	
	/**
	 * The input check class used for validating user input and handling alerts
	 */
	InputCheck inputCheck = new InputCheck();

	/**
	 * Initialize-method called when the class is created
	 * Calls the super class method to initialize the table view
	 * Fetches the needed wishlist items from the database and displays them in the table view
	 */
	@FXML
	public void initialize() {
		super.initializeTable();
		ObservableList<Item> data = FXCollections.observableArrayList(controller.getItems());
		wishlisttable.setItems(data);
	}
	
	/**
	 * Method for showing the view of add to wishlist
	 * @param event ActionEvent that is handled
	 */
	@FXML
	public void showAddWish(ActionEvent event) {
		AnchorPane showAddWishView = super.loadAddWish();
		if (showAddWishView != null) {
			wishlistanchorpane.getChildren().setAll(showAddWishView);
		} else {
			System.out.println("Error loading WishlistAdd.fxml");
		}
	}
	
	/**
	 * Method for getting the selected item from the table
	 * @return the selected item
	 */
	@FXML
	public Item getSelectedItem() {
		return super.getSelectedItem();
	}
	
	/**
	 * Method for deleting the selected item from the database
	 */
	@FXML
	public void deleteItem() {
		if (super.checkItemIsSelected()) {
			controller.removeItem(this);
			initialize();
		} else {
			inputCheck.alertNothingSelected();
		}
	}
	
	/** 
	 * Method for marking an item as bought
	 */
	@FXML
	public void markAsBought() {
		if (super.checkItemIsSelected()) {
			controller.setBought(this);
			initialize();
		} else {
			inputCheck.alertNothingSelected();
		}
	}
	
}
