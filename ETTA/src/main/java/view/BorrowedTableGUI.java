package view;

import java.io.IOException;
import java.sql.Date;

import controller.BorrowedController;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.util.Callback;
import model.BorrowedThing;

public class BorrowedTableGUI {
	
	BorrowedController controller;
	
	@FXML
	AnchorPane borrowedviewanchorpane;
	
	@FXML
	TableView<BorrowedThing> borrowedTable;
	
	@FXML
	TableColumn<BorrowedThing, String> borrowedThing;
	
	@FXML
	TableColumn<BorrowedThing, Date> loanDate;
	
	@FXML
	TableColumn<BorrowedThing, Date> returnDate;
	
	@FXML
	TableColumn<BorrowedThing, String> borrowedBy;
	
	public BorrowedTableGUI() {
		controller = new BorrowedController(this);
	}


	
	@FXML
	public void initialize() {
		borrowedThing.setCellValueFactory(new PropertyValueFactory<BorrowedThing, String>("description")); 
		/*borrowedBy.setCellValueFactory(new Callback<CellDataFeatures<BorrowedThing, String>, ObservableValue<String>>() {
			public ObservableValue<String> call(CellDataFeatures<BorrowedThing, String> borrowedThing) {
		         // item.getValue() returns the Data instance for a particular TableView row
		         return new ReadOnlyObjectWrapper(borrowedThing.getValue().getPerson().getName());
			}
		});*/
		loanDate.setCellValueFactory(new PropertyValueFactory<BorrowedThing, Date>("dateBorrowed"));
		returnDate.setCellValueFactory(new PropertyValueFactory<BorrowedThing, Date>("returnDate"));
		//returned.setCellValueFactory(new PropertyValueFactory<BorrowedThing, Boolean>("returned"));
		
		ObservableList<BorrowedThing> data = FXCollections.observableArrayList(controller.getBorrowedThings());
		borrowedTable.setItems(data);
	}
	


} 

//EconomyOutcomeGUI as an example
/*
 * package view;


public class EconomyOutcomeGUI {
	
	@FXML	
	AnchorPane economyoutcomeaddanchorpane;

	@FXML
    private TableView<Transfer> expenseTable;
	
    @FXML
    private TableColumn<Transfer, String> expenseDescription;

    @FXML
    private TableColumn<Transfer, Date> expenseDate;
    
    @FXML
    private TableColumn<Transfer, Float> expenseAmount;
	
	@FXML
	public void showAddOutcome(ActionEvent event) {
		AnchorPane showAddOutcomeView = null;
		FXMLLoader loaderAddOutcomeView = new FXMLLoader(getClass().getResource("/view/EconomyAddOutcome.fxml"));
		try {
			showAddOutcomeView = loaderAddOutcomeView.load();
			} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			}
		//shows the loaded fxml file
		economyoutcomeaddanchorpane.getChildren().setAll(showAddOutcomeView);
	
	}
	
	@FXML 
	public void initialize() { 
		expenseDescription.setCellValueFactory(
                new PropertyValueFactory<Transfer, String>("description"));
		expenseDate.setCellValueFactory(
                new PropertyValueFactory<Transfer, Date>("date"));
		expenseAmount.setCellValueFactory(
                new PropertyValueFactory<Transfer, Float>("amount"));
		ObservableList<Transfer> expencies =  FXCollections.observableArrayList(controller.getExpenses());
		expenseTable.setItems(expencies);
	
	}
}

 */
	
	
	
	
	

