package view.wishlist;

import controller.InputCheck;
import controller.WishlistController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import model.Item;

/**
 * GUI class relating to the wishlist gifts section
 */
public class WishlistGiftGUI extends AbstractWishlistGUI {

	/**
	 * Reference to the used WishlistController
	 */
	private WishlistController controller = new WishlistController();
	
	/**
	 * The anchor pane view from where adding, editing and deleting can be started
	 */
	@FXML
	private AnchorPane wishlistGiftAnchorpane;
	
	/**
	 * Table view for showing the wishlist items
	 */
	@FXML
	private TableView<Item> wishlisttable;
	
	/**
	 * The input check class used for validating user input and handling alerts
	 */
	private InputCheck inputCheck = new InputCheck();

	/**
	 * Initialize-method called when the class is created
	 * Calls the super class method to initialize the table view
	 * Fetches the needed wishlist items from the database and displays them in the table view
	 */
	@FXML
	public void initialize() {
		super.initializeTable();
		ObservableList<Item> data = FXCollections.observableArrayList(controller.getItemsForOthers());
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
			wishlistGiftAnchorpane.getChildren().setAll(showAddWishView);
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
			if (inputCheck.confrimMarkBought()) {
				controller.setBought(this);
				initialize();
			}
		} else {
			inputCheck.alertNothingSelected();
		}
	}
}
