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
	/**
	 * the controller for Borrowed things
	 */
	BorrowedController controller;// = new BorrowedController(this);
	
	/**
	 * The anchorpane for the overall view of borrowed things
	 */
	@FXML
	AnchorPane borrowedviewanchorpane;
	
	/**
	 * The TableView for viewing all borrowed things
	 */
	@FXML
	private TableView<BorrowedThing> borrowedTable;
	
	/**
	 * The TableColumn that shows the items' names
	 */
	@FXML
	private TableColumn<BorrowedThing, String> borrowedThingDescr;
	
	/**
	 * The TableColumn that shows when the items have been loaned
	 */
	@FXML
	private TableColumn<BorrowedThing, Date> loanDate;
	
	/**
	 * The TableColumn that shows when the items are supposed to be returned
	 */
	@FXML
	private TableColumn<BorrowedThing, Date> returnDate;
	
	/**
	 * The TableColumn that shows who have borrowed the items
	 */
	@FXML
	private TableColumn<Person, String> borrowedBy;
	
	/**
	 * The TableColumn that shows if the item has been returned
	 */
	@FXML
	private TableColumn<BorrowedThing, Boolean> returned;
	
	/**
	 * A constructor for BorrowedTableGUI in which the controller object is created
	 */
	public BorrowedTableGUI() {
		controller = new BorrowedController(this);
	}

	/** 
	 * Method that shows all the borrowed items
	 * @param event either Borrowed Things tab or Borrowed Items button is pushed
	 */
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
	
	/** 
	 * Method that adds the information of Borrowed items into the table
	 */
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
	 * @param borrowedThing the borrowed item to be removed
	 */
	public void removeFromBorrowedTable(BorrowedThing borrowedThing) {
		borrowedTable.getItems().remove(borrowedThing);
	}
} 


	
	
	
	
	

