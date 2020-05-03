package view.borrowed;

import java.sql.Date;

import org.controlsfx.control.textfield.TextFields;

import controller.BorrowedController;
import controller.InputCheck;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.util.Callback;
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
	 * The anchorpane for the search view of returned things
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
		super.initializeTable();
		//creates the autocomplete list for the search text field
		BorrowedThing[] borrowedThings = controller.getBorrowedThings();
		String[] possibleWords = new String[borrowedThings.length];
		for (int i = 0; i < borrowedThings.length; i++) {
			possibleWords[i] = borrowedThings[i].getDescription();
		}
		TextFields.bindAutoCompletion(input, possibleWords);
		//sets the items in the table
		ObservableList<BorrowedThing> data = FXCollections.observableArrayList(borrowedThings);
		borrowedTable.setItems(data);
	}
	
	/**
	 * Method for getting the selected item from the table
	 * @return borrowedSearchTable.getSelectionModel().getSelectedItem() the selected item
	 */
	@FXML
	public BorrowedThing getSelectedBorrowedThing() {
		return super.getSelectedBorrowedThing();
	}
	
	/**
	 * Filters the list of borrowed things by the description inputted by the user
	 * @param event the ActionEvent that is handled
	 */
	public void searchBorrowedThing(ActionEvent event) {
		String value = input.getText();
		ObservableList<BorrowedThing> data = FXCollections.observableArrayList(controller.getBorrowedThings());
		FilteredList<BorrowedThing> filteredData = new FilteredList<>(data,
	            s -> ((s.getDescription())).equals(value));
		borrowedTable.setItems(filteredData);
	}
	
}
	

