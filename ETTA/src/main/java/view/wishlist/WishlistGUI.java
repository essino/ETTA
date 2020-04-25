package view.wishlist;

import java.io.IOException;
import java.util.Observable;
import java.util.Observer;
import java.util.ResourceBundle;

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
public class WishlistGUI implements Observer{
	
	MyBundle myBundleInst = MyBundle.getInstance();
	MyBundle myBundle;
	
	ResourceBundle bundle;
	
	/**
	 * The menu view to which the alternative views in the Wish section are added
	 */
	@FXML
	BorderPane wishmainpane;
	
	WishlistController controller = new WishlistController();
	
	public WishlistGUI() {
		this.myBundle = myBundleInst;
		this.bundle=myBundle.getBundle();
		this.myBundle.addObserver(this);
	}
	
	@Override
	public void update(Observable o, Object arg) {
		System.out.println("observer informed");
		if(o instanceof MyBundle) {
			this.bundle=myBundle.getBundle();
		}
		
	}
	
	/**
	 * Method showing the Wishlist view in the Wish section
	 * @param event ActionEvent that is handled
	 */
	@FXML
	public void showAll(ActionEvent event) {
		AnchorPane allView = null;
		FXMLLoader loaderWishlistAll  = new FXMLLoader(getClass().getResource("/view/wishlist/WishlistView.fxml"));
		loaderWishlistAll.setResources(bundle);
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
		loaderWishlistBought.setResources(bundle);
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
		loaderWishlistGifts.setResources(bundle);
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
		loaderWishlistOwn.setResources(bundle);
		try {
			ownView = loaderWishlistOwn.load();
			} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			}
		wishmainpane.setCenter(ownView);
	}
	
}