package view;


import java.io.IOException;
import java.sql.Date;

import controller.InputCheck;
import controller.WishlistController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

/**
 * GUI class relating to the wishlist add section
 */
public class WishlistAddGUI {
	
	@FXML
	TextField item;
	@FXML
	ComboBox<String> toWhom;
	@FXML
	TextField price;
	@FXML
	DatePicker date;
	@FXML
	TextField additional;
	
	@FXML
	AnchorPane wishlistaddpane;
	
	WishlistController controller = new WishlistController();
	
	InputCheck inputCheck = new InputCheck();
	
	@FXML
	public void initialize() {
			toWhom.getItems().addAll(controller.personsList());
	}
	
	@FXML
	public String getItemDesc() {
		return this.item.getText();
	}
	
	@FXML
	public String getItemPerson() {
		return this.toWhom.getValue();
	}
	
	@FXML
	public String getItemPrice() {
		return this.price.getText();
	}
	
	@FXML
	public Date getItemDate() {
		try {
			return Date.valueOf(this.date.getValue());
		} catch(NullPointerException e) {
			return null;
		}
	}
	
	@FXML
	public String getItemAdditional() {
		return this.additional.getText();
	}
	
	@FXML
	public void addNewItem() {
		if (inputCheck.isInputFloat(price.getText())) {
			controller.saveItem();
			AnchorPane wishlistView = null; 
			FXMLLoader loaderWishlistView  = new FXMLLoader(getClass().getResource("/view/WishlistView.fxml")); 
			try {
				wishlistView = loaderWishlistView.load();
			} catch (IOException e) {
				e.printStackTrace();
			}
			wishlistaddpane.getChildren().setAll(wishlistView);
			
		} else {
			inputCheck.alertInputNotFloat();
		}
	}
	
	@FXML
	public void cancelAdd() {
		AnchorPane wishlistView = null; 
		FXMLLoader loaderWishlistView  = new FXMLLoader(getClass().getResource("/view/WishlistView.fxml")); 
		try {
			wishlistView = loaderWishlistView.load();
		} catch (IOException e) {
			e.printStackTrace();
		}
		wishlistaddpane.getChildren().setAll(wishlistView);
	}
}
