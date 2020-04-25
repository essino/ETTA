package view.wishlist;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import model.Item;
import res.MyBundle;

public abstract class AbstractWishlistGUI {
	
	MyBundle myBundle = new MyBundle();
	
	@FXML
	TableView<Item> wishlisttable;
	
	public AnchorPane loadAddWish() {
		AnchorPane showAddWishView = null; 
		FXMLLoader loaderAddWishView  = new FXMLLoader(getClass().getResource("/view/wishlist/WishlistAdd.fxml")); 
		loaderAddWishView.setResources(myBundle.getBundle());
		try {
			showAddWishView = loaderAddWishView.load();
		} catch (IOException e) {
			return null;
		}
		return showAddWishView;
	}
	
	public Item getSelectedItem() {
		return wishlisttable.getSelectionModel().getSelectedItem();
	}
	
	public abstract void deleteItem();

}
