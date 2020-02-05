package controller;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;


/**
 * Controller class relating to the Borrowed items section
 */
public class BorrowedController {

	/**
	 * The menu view to which the alternative views in the Borrowed items section are added
	 */
	@FXML
	BorderPane borrowedrootborderpane;
	
	/**
	 * Method showing the search view in the Borrowed items section
	 * @param event ActionEvent that is handled
	 */
	@FXML
	public void showBorrowedSearch(ActionEvent event) {
		AnchorPane borrowedSearch = null;
		FXMLLoader loaderBorrowedSearch  = new FXMLLoader(getClass().getResource("/view/BorrowedSearch.fxml"));
		try {
			borrowedSearch = loaderBorrowedSearch.load();
			} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			}
		borrowedrootborderpane.setCenter(borrowedSearch);
	}
	
	/**
	 * Method showing the main view in the Borrowed items section
	 * @param event ActionEvent that is handled
	 */
	@FXML
	public void showBorrowedView(ActionEvent event) {
		AnchorPane borrowedView = null;
		FXMLLoader loaderBorrowedView = new FXMLLoader(getClass().getResource("/view/BorrowedView.fxml"));
		try {
			borrowedView = loaderBorrowedView.load();
			} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			}
		borrowedrootborderpane.setCenter(borrowedView);
	}
	
}
