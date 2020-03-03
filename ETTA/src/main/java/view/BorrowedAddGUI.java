package view;


import java.io.IOException;

import controller.BorrowedController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.TextInputDialog;
import javafx.scene.input.MouseEvent;
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
			bbc.getItems().add("");
			bbc.setCellFactory(lv -> {
	            ListCell<String> cell = new ListCell<String>() {
	                @Override
	                protected void updateItem(String item, boolean empty) {
	                    super.updateItem(item, empty);
	                    if (empty) {
	                        setText(null);
	                    } else {
	                        if (item.isEmpty()) {
	                            setText("Add item...");
	                        } else {
	                            setText(item);
	                        }
	                    }
	                }
	            };

	            cell.addEventFilter(MouseEvent.MOUSE_PRESSED, evt -> {
	                if (cell.getItem().isEmpty() && ! cell.isEmpty()) {
	                    TextInputDialog dialog = new TextInputDialog();
	                    dialog.setContentText("Enter name");
	                    dialog.showAndWait().ifPresent(text -> {
	                        int index = bbc.getItems().size()-1;
	                        bbc.getItems().add(index, text);
	                        bbc.getSelectionModel().select(index);
	                    });
	                    evt.consume();
	                }
	            });

	            return cell ;
	        });
	}

}
