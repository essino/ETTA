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

public class BorrowedSearchGUI {

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
	private TableView<BorrowedThing> borrowedSearchTable;
	
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
	
	//returned changed from boolean to String
	/**
	 * The TableColumn that shows if the item has been returned
	 */
	@FXML
	private TableColumn<BorrowedThing, String> returned;
	
	//returned changed from boolean to String
	/**
	 * The TableColumn that shows if the item has been returned
	 */
	@FXML
	private TextField input;
	
	Callback<TableColumn<BorrowedThing, Date>, TableCell<BorrowedThing, Date>> dateCellFactory = (TableColumn<BorrowedThing, Date> param) -> new DateEditingCell();

	/**
	 * Initialize-method called when the class is created
	 * Fetches the list of loaned items in the database
	 */
	@FXML
	public void initialize() {
		borrowedSearchTable.setPlaceholder(new Text(myBundle.getBundle().getString("wishlistEmpty")));
		borrowedThingDescr.setCellValueFactory(new PropertyValueFactory<BorrowedThing, String>("description")); 
		borrowedBy.setCellValueFactory(new PropertyValueFactory<BorrowedThing, String>("person"));
		loanDate.setCellValueFactory(new PropertyValueFactory<BorrowedThing, Date>("dateBorrowed"));
		returnDate.setCellValueFactory(new PropertyValueFactory<BorrowedThing, Date>("returnDate"));
		returned.setCellValueFactory(new Callback<CellDataFeatures<BorrowedThing, String>, ObservableValue<String>>(){
			public ObservableValue<String> call(CellDataFeatures<BorrowedThing, String> borrowedThingDescr) {
				if (borrowedThingDescr.getValue().isReturned() == true) {
					return new ReadOnlyObjectWrapper<>(myBundle.getBundle().getString("yesYes"));
				} else {
					return new ReadOnlyObjectWrapper<>(myBundle.getBundle().getString("noNo"));
				}
			}});
		
		BorrowedThing[] borrowedThings = controller.getBorrowedThings();
		String[] possibleWords = new String[borrowedThings.length];
		for (int i = 0; i < borrowedThings.length; i++) {
			possibleWords[i] = borrowedThings[i].getDescription();
		}
		TextFields.bindAutoCompletion(input, possibleWords);
		
		ObservableList<BorrowedThing> data = FXCollections.observableArrayList(borrowedThings);
		borrowedSearchTable.setItems(data);
	}
	
	/**
	 * Method for getting the selected item from the table
	 * @return the selected item
	 */
	@FXML
	public BorrowedThing getSelectedBorrowedThing() {
		return borrowedSearchTable.getSelectionModel().getSelectedItem();
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
		borrowedSearchTable.setItems(filteredData);
	}
	
}
	

