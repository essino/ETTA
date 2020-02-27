package view;

import java.io.IOException;

import controller.BorrowedController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.AnchorPane;

public class BorrowedAddGUI {
	
	@FXML
	ComboBox<String> bbc;
	
	@FXML
	Button buttonAdd;
	
	BorrowedController controller = new BorrowedController();
	
	@FXML
	public void addBorrowed(ActionEvent event) {
		System.out.println("found"); 
	}
	
	
	@FXML
	public AnchorPane borrowedAdd() {
		AnchorPane borrowedAdd = null;
		FXMLLoader loaderBorrowedAdd = new FXMLLoader(getClass().getResource("/view/BorrowedAdd.fxml"));
		try {
				borrowedAdd = loaderBorrowedAdd.load();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
       return borrowedAdd;
       
	}

	@FXML
	public void initialize() {
			bbc.getItems().addAll(controller.personsList());
	}

}
