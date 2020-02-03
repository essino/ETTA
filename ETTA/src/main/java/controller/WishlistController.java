package controller;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

public class WishlistController {
	@FXML
	BorderPane wishmainpane;
	
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
	

}