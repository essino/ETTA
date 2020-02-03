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

public class BorrowedController {

	@FXML
	BorderPane borrowedrootborderpane;
	
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
