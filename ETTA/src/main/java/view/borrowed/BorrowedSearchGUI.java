package view.borrowed;

import java.sql.Date;

import org.controlsfx.control.textfield.TextFields;

import controller.BorrowedController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import model.BorrowedThing;
import res.MyBundle;

public class BorrowedSearchGUI extends AbstractBorrowedGUI { 

	/**
	 * MyBundle object for setting the right resource bundle to localize the application
	 */
	MyBundle myBundle = new MyBundle();
	
	/**
	 * the controller for Borrowed things
	 */
	BorrowedController controller = new BorrowedController();
	
	/**
	 * The anchor pane for the search view of returned things
	 */
	@FXML
	AnchorPane borrowedsearchanchorpane;
	
	/**
	 * The TableView for viewing all loaned items
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
	private TableColumn<BorrowedThing, String> borrowedBy;
	
	/**
	 * The TableColumn that shows if the item has been returned
	 */
	@FXML
	private TableColumn<BorrowedThing, String> returned;
	
	/**
	 * The TableColumn that shows if the item has been returned
	 */
	@FXML
	private TextField input;

	/**
	 * Initialize method called when the class is created
	 * Fetches the list of loaned items in the database
	 */
	@FXML
	public void initialize() {
		//uses abstract class's method in initializing
		super.initializeTable();
		//creates the autocomplete list for the search text field
		BorrowedThing[] borrowedThings = controller.getBorrowedThings();
		String[] possibleWords = new String[borrowedThings.length];
		for (int i = 0; i < borrowedThings.length; i++) {
			possibleWords[i] = borrowedThings[i].getDescription();
		}
		//creates the auto complete functionality in the input text field
		TextFields.bindAutoCompletion(input, possibleWords);
		//sets the items in the table
		ObservableList<BorrowedThing> data = FXCollections.observableArrayList(borrowedThings);
		borrowedTable.setItems(data);
	}
	
	/**
	 * Method for getting the selected item from the table
	 * @return super.borrowedSearchTable.getSelectionModel().getSelectedItem() the selected item
	 */
	@FXML
	public BorrowedThing getSelectedBorrowedThing() {
		return super.getSelectedBorrowedThing();
	}
	
	/**
	 * Filters the list of borrowed things by the description input by the user
	 * @param event the ActionEvent that is handled
	 */
	public void searchBorrowedThing(ActionEvent event) {
		//getting the text from the textfield
		String value = input.getText();
		//finding the items that match that text
		ObservableList<BorrowedThing> data = FXCollections.observableArrayList(controller.getBorrowedThings());
		FilteredList<BorrowedThing> filteredData = new FilteredList<>(data,
	            s -> ((s.getDescription())).equals(value));
		//setting the items in the table
		borrowedTable.setItems(filteredData);
	}
	
}
	

