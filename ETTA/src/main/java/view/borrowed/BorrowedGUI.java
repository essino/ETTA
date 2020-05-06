package view.borrowed;

import java.io.IOException;
import controller.BorrowedController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import res.MyBundle;


/**
 * GUI class relating to the Borrowed items section
 */
public class BorrowedGUI {

	/**
	 * MyBundle object for setting the right resource bundle to localize the application
	 */
	MyBundle myBundle = new MyBundle();
	
	/**
	 * The menu view to which the alternative views in the Borrowed items section are added
	 */
	@FXML
	BorderPane borrowedRootBorderPane;
	
	/**
	 * The list view from where adding, editing and deleting can be started
	 */
	@FXML 
	AnchorPane borrowedviewanchorpane;
	
	/**
	 * the controller for borrowed things
	 */
	BorrowedController controller;
	
	/**
	 * an empty constructor for BorrowedGUI
	 */
	public BorrowedGUI() {
	}
	
	/**
	 * Method showing the search view in the Borrowed items section
	 * @param event ActionEvent that is handled
	 */
	@FXML
	public void showBorrowedSearch(ActionEvent event) {
		AnchorPane borrowedSearch = null;
		FXMLLoader loaderBorrowedSearch  = new FXMLLoader(getClass().getResource("/view/borrowed/BorrowedSearch.fxml"));
		loaderBorrowedSearch.setResources(myBundle.getBundle());
		try {
			borrowedSearch = loaderBorrowedSearch.load();
			} catch (IOException e) {
			e.printStackTrace();
			}
		//shows the loaded fxml file
		borrowedRootBorderPane.setCenter(borrowedSearch);
	}
	
	/**
	 * Method showing the main view in the Borrowed items section
	 * @param event ActionEvent that is handled
	 */
	@FXML
	public void showBorrowedView(ActionEvent event) {
		AnchorPane borrowedView = null;
		FXMLLoader loaderBorrowedView = new FXMLLoader(getClass().getResource("/view/borrowed/BorrowedView.fxml"));
		loaderBorrowedView.setResources(myBundle.getBundle());
		try {
			borrowedView = loaderBorrowedView.load();
			} catch (IOException e) {
			e.printStackTrace();
			}
		//shows the loaded fxml file
		borrowedRootBorderPane.setCenter(borrowedView);
	}
	
	/**
	 * Method showing the view of the list of borrowed items in the Borrowed items section
	 * @param event ActionEvent that is handled
	 */
	@FXML
	public void showBorrowedAdd(ActionEvent event) {
		AnchorPane borrowedAdd = null;
		FXMLLoader loaderBorrowedAdd = new FXMLLoader(getClass().getResource("/view/borrowed/BorrowedAdd.fxml"));
		loaderBorrowedAdd.setResources(myBundle.getBundle());
		try {
				borrowedAdd = loaderBorrowedAdd.load();
			} catch (IOException e) { 
				e.printStackTrace();
			}
		//shows the loaded fxml file
		borrowedviewanchorpane.getChildren().setAll(borrowedAdd);
	}

	/**
	 * Method showing the view of returned items
	 * @param event ActionEvent that is handled
	 */
	@FXML
	public void showReturned(ActionEvent event) {
		AnchorPane borrowedReturned = null;
		FXMLLoader loaderBorrowedReturned = new FXMLLoader(getClass().getResource("/view/borrowed/BorrowedReturnedView.fxml"));
		loaderBorrowedReturned.setResources(myBundle.getBundle());
		try {
			borrowedReturned = loaderBorrowedReturned.load();
			} catch (IOException e) {
			e.printStackTrace();
			}
		//shows the loaded fxml file
		borrowedRootBorderPane.setCenter(borrowedReturned);
	}
}

