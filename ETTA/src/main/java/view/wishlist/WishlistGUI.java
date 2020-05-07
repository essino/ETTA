package view.wishlist;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import res.MyBundle;

/**
 * GUI class relating to the Wishlist buttons
 */
public class WishlistGUI {
	
	/**
	 * MyBundle object for setting the right resource bundle to localize the application
	 */
	private MyBundle myBundle = new MyBundle();
	
	/**
	 * The menu view to which the alternative views in the Wish section are added
	 */
	@FXML
	private BorderPane wishmainpane;
	
	/**
	 * Method showing the Wishlist view in the Wish section
	 * @param event ActionEvent that is handled
	 */
	@FXML
	public void showAll(ActionEvent event) {
		AnchorPane allView = null;
		FXMLLoader loaderWishlistAll  = new FXMLLoader(getClass().getResource("/view/wishlist/WishlistView.fxml"));
		//setting the text resources
		loaderWishlistAll.setResources(myBundle.getBundle());
		try {
			allView = loaderWishlistAll.load();
			} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			}
		wishmainpane.setCenter(allView);
	}
	
	/**
	 * Method showing the Wishlist bought view in the Wish section
	 * @param event ActionEvent that is handled
	 */
	@FXML
	public void showBought(ActionEvent event) {
		AnchorPane boughtView = null;
		FXMLLoader loaderWishlistBought  = new FXMLLoader(getClass().getResource("/view/wishlist/WishlistBought.fxml"));
		//setting the text resources
		loaderWishlistBought.setResources(myBundle.getBundle());
		try {
			boughtView = loaderWishlistBought.load();
			} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			}
		wishmainpane.setCenter(boughtView);
	}
	
	/**
	 * Method showing the Wishlist gift view in the Wish section
	 * @param event ActionEvent that is handled
	 */
	@FXML
	public void showGifts(ActionEvent event) {
		AnchorPane giftView = null;
		FXMLLoader loaderWishlistGifts  = new FXMLLoader(getClass().getResource("/view/wishlist/WishlistGifts.fxml"));
		//setting the text resources
		loaderWishlistGifts.setResources(myBundle.getBundle());
		try {
			giftView = loaderWishlistGifts.load();
			} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			}
		wishmainpane.setCenter(giftView);
	}
	
	/**
	 * Method showing the Wishlist own view in the Wish section
	 * @param event ActionEvent that is handled
	 */
	@FXML
	public void showOwn(ActionEvent event) {
		AnchorPane ownView = null;
		FXMLLoader loaderWishlistOwn = new FXMLLoader(getClass().getResource("/view/wishlist/WishlistOwn.fxml"));
		//setting the text resources
		loaderWishlistOwn.setResources(myBundle.getBundle());
		try {
			ownView = loaderWishlistOwn.load();
			} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			}
		wishmainpane.setCenter(ownView);
	}
	
	/**
	 * Method showing the Wishlist search view in the Wish section
	 * @param event ActionEvent that is handled
	 */
	@FXML
	public void showSearch(ActionEvent event) {
		AnchorPane searchView = null;
		FXMLLoader loaderWishlistSearch = new FXMLLoader(getClass().getResource("/view/wishlist/WishlistSearch.fxml"));
		//setting the text resources
		loaderWishlistSearch.setResources(myBundle.getBundle());
		try {
			searchView = loaderWishlistSearch.load();
			} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			}
		wishmainpane.setCenter(searchView);
	}
	
}