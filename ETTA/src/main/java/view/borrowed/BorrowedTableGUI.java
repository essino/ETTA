package view.borrowed;

import java.io.IOException;
import java.sql.Date;

import com.sun.xml.bind.v2.schemagen.episode.Bindings;

import controller.BorrowedController;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.util.Callback;
import model.BorrowedThing;
import model.Item;
import model.Person;


public class BorrowedTableGUI {
	
	//NB! this is different in Tiina's WishlistTableGUI - could cause problems
	BorrowedController controller;// = new BorrowedController(this);
	
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
	
	@FXML
	private TableColumn<BorrowedThing, Boolean> returned;
	
	public BorrowedTableGUI() {
		controller = new BorrowedController(this);
	}

	@FXML
	public void showBorrowedAdd(ActionEvent event) {
		AnchorPane showBorrowedAdd = null;
		FXMLLoader loaderBorrowedAdd = new FXMLLoader(getClass().getResource("/view/borrowed/BorrowedAdd.fxml"));
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
		borrowedBy.setCellValueFactory(new PropertyValueFactory<Person, String>("person"));
		loanDate.setCellValueFactory(new PropertyValueFactory<BorrowedThing, Date>("dateBorrowed"));
		returnDate.setCellValueFactory(new PropertyValueFactory<BorrowedThing, Date>("returnDate"));
		returned.setCellValueFactory(new PropertyValueFactory<BorrowedThing, Boolean>("returned"));
		
		ObservableList<BorrowedThing> data = FXCollections.observableArrayList(controller.getBorrowedThings());
		borrowedTable.setItems(data);
	}
	
	/**
	 * Method for getting the selected item from the table
	 * @return the selected item
	 */
	@FXML
	public BorrowedThing getSelectedBorrowedThing() {
		return borrowedTable.getSelectionModel().getSelectedItem();
	}
	
	/**
	 * Method for deleting the selected borrowed thing from the database
	 */
	@FXML
	public void deleteSelectedBorrowedThing() {
		controller.removeBorrowedThing();
	}
	
	/** 
	 * Method that removes an item from the table
	 * @param item the item to be removed
	 */
	public void removeFromBorrowedTable(BorrowedThing borrowedThing) {
		borrowedTable.getItems().remove(borrowedThing);
	}
} 


	
	
	
	
	

