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
import model.Person;


public class BorrowedTableGUI {
	
	BorrowedController controller = new BorrowedController(this);
	
	@FXML
	AnchorPane borrowedviewanchorpane;
	
	@FXML
	private TableView<BorrowedThing> borrowedTable;
	
	@FXML
	private TableColumn<BorrowedThing, String> borrowedThingDescr;
	
	@FXML
	private TableColumn<BorrowedThing, Date> loanDate;
	
	@FXML
	private TableColumn<BorrowedThing, Date> returnDate;
	
	@FXML
	private TableColumn<Person, String> borrowedBy;
	//private TableColumn<BorrowedThing, String> borrowedBy;
	
	@FXML
	private TableColumn<BorrowedThing, Boolean> returned;
	
	public BorrowedTableGUI() {
		controller = new BorrowedController(this);
	}

	@FXML
	public void showBorrowedAdd(ActionEvent event) {
		AnchorPane showBorrowedAdd = null;
		FXMLLoader loaderBorrowedAdd = new FXMLLoader(getClass().getResource("/view/BorrowedAdd.fxml"));
		try {
			showBorrowedAdd = loaderBorrowedAdd.load();
			} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			}
		//shows the loaded fxml file
		borrowedviewanchorpane.getChildren().setAll(showBorrowedAdd);
	
	}
	
	@FXML
	public void initialize() {
		borrowedThingDescr.setCellValueFactory(new PropertyValueFactory<BorrowedThing, String>("description")); 
		/*
		borrowedBy.setCellValueFactory(new Callback<CellDataFeatures<BorrowedThing, String>, ObservableValue<String>>() {
			public ObservableValue<String> call(CellDataFeatures<BorrowedThing, String> borrowedThing) {
		         // item.getValue() returns the Data instance for a particular TableView row
		         return new ReadOnlyObjectWrapper(borrowedThing.getValue().getPerson().getName());
			}
		});
		*/
		borrowedBy.setCellValueFactory(new PropertyValueFactory<Person, String>("person"));
		loanDate.setCellValueFactory(new PropertyValueFactory<BorrowedThing, Date>("dateBorrowed"));
		returnDate.setCellValueFactory(new PropertyValueFactory<BorrowedThing, Date>("returnDate"));
		returned.setCellValueFactory(new PropertyValueFactory<BorrowedThing, Boolean>("returned"));
		
		ObservableList<BorrowedThing> data = FXCollections.observableArrayList(controller.getBorrowedThings());
		borrowedTable.setItems(data);
	}
	


} 


	
	
	
	
	

