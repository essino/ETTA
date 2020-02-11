package view;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

/**
 * GUI class relating to the Wish section
 */
public class WishlistGUI {
	
	/**
	 * The menu view to which the alternative views in the Wish section are added
	 */
	@FXML
	BorderPane wishmainpane;
	
	/**
	 * The list view from where adding, editing and deleting can be started
	 */
	@FXML
	AnchorPane wishlistanchorpane;
	
	
	/**
	 * Method showing the Wishlist view in the Wish section
	 * @param event ActionEvent that is handled
	 */
	@FXML
	public void showAll(ActionEvent event) {
		AnchorPane allView = null;
		FXMLLoader loaderWishlistAll  = new FXMLLoader(getClass().getResource("/view/WishlistView.fxml"));
		try {
			allView = loaderWishlistAll.load();
			} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			}
		wishmainpane.setCenter(allView);
	}
	
	/**
	 * Method showing the view of add to wishlist
	 * @param event ActionEvent that is handled
	 */
	@FXML
	public void showAddWish(ActionEvent event) {
		AnchorPane showAddWishView = null; 
		FXMLLoader loaderAddWishView  = new FXMLLoader(getClass().getResource("/view/WishlistAdd.fxml")); 
		try {
			showAddWishView = loaderAddWishView.load();
			} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			}
		wishlistanchorpane.getChildren().setAll(showAddWishView);
	}
}
