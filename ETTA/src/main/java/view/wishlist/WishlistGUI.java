package view.wishlist;

import java.io.IOException;

import controller.WishlistController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import res.MyBundle;

/**
 * GUI class relating to the Wish section
 */
public class WishlistGUI {
	
	/**
	 * MyBundle object for setting the right resource bundle to localize the application
	 */
	MyBundle myBundle = new MyBundle();
	/**
	 * The menu view to which the alternative views in the Wish section are added
	 */
	@FXML
	BorderPane wishmainpane;
	
	WishlistController controller = new WishlistController();
	
	/**
	 * Method showing the Wishlist view in the Wish section
	 * @param event ActionEvent that is handled
	 */
	@FXML
	public void showAll(ActionEvent event) {
		AnchorPane allView = null;
		FXMLLoader loaderWishlistAll  = new FXMLLoader(getClass().getResource("/view/wishlist/WishlistView.fxml"));
		loaderWishlistAll.setResources(myBundle.getBundle());
		try {
			allView = loaderWishlistAll.load();
			} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			}
		wishmainpane.setCenter(allView);
	}
	
	@FXML
	public void showBought(ActionEvent event) {
		AnchorPane boughtView = null;
		FXMLLoader loaderWishlistBought  = new FXMLLoader(getClass().getResource("/view/wishlist/WishlistBought.fxml"));
		loaderWishlistBought.setResources(myBundle.getBundle());
		try {
			boughtView = loaderWishlistBought.load();
			} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			}
		wishmainpane.setCenter(boughtView);
	}
	
	@FXML
	public void showGifts(ActionEvent event) {
		AnchorPane giftView = null;
		FXMLLoader loaderWishlistGifts  = new FXMLLoader(getClass().getResource("/view/wishlist/WishlistGifts.fxml"));
		loaderWishlistGifts.setResources(myBundle.getBundle());
		try {
			giftView = loaderWishlistGifts.load();
			} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			}
		wishmainpane.setCenter(giftView);
	}
	
	@FXML
	public void showOwn(ActionEvent event) {
		AnchorPane ownView = null;
		FXMLLoader loaderWishlistOwn = new FXMLLoader(getClass().getResource("/view/wishlist/WishlistOwn.fxml"));
		loaderWishlistOwn.setResources(myBundle.getBundle());
		try {
			ownView = loaderWishlistOwn.load();
			} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			}
		wishmainpane.setCenter(ownView);
	}
	
}