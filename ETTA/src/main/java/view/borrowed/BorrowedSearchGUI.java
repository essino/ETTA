package view.borrowed;

import java.sql.Date;
import java.text.DateFormat;
import java.util.Locale;

import org.controlsfx.control.textfield.TextFields;

import controller.BorrowedController;
import controller.InputCheck;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.util.Callback;
import model.BorrowedThing;
import model.Person;
import res.MyBundle;

public class BorrowedSearchGUI {

	/**
	 * MyBundle object for setting the right resource bundle to localize the application
	 */
	MyBundle myBundle = new MyBundle();
	
	/**
	 * the controller for Borrowed things
	 */
	BorrowedController controller;
	
	/**
	 * The anchorpane for the overall view of returned things
	 */
	@FXML
	AnchorPane borrowedsearchanchorpane;
	
	/**
	 * The TableView for viewing all returned items
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
		
	
	/**
	 * A constructor for BorrowedTableGUI in which the controller object is created
	 */
	public BorrowedSearchGUI() {
		controller = new BorrowedController(this);
	}

	/**
	 * The reference of InputCheck class used for checking user's input
	 */
	InputCheck inputCheck = new InputCheck(); 
	
	Callback<TableColumn<BorrowedThing, Date>, TableCell<BorrowedThing, Date>> dateCellFactory = (TableColumn<BorrowedThing, Date> param) -> new DateEditingCell();

	/**
	 * Initialize-method called when the class is created
	 * Fetches the list of returned items in the database 
	 * Also allows for inline editing of the borrowed items on the list
	 */
	@FXML
	public void initialize() {
		System.out.println("We don't need no education!");
		borrowedSearchTable.setPlaceholder(new Text(myBundle.getBundle().getString("wishlistEmpty")));
		//borrowedSearchTable.setEditable(true);
		borrowedThingDescr.setCellValueFactory(new PropertyValueFactory<BorrowedThing, String>("description")); 
		/*borrowedThingDescr.setCellFactory(TextFieldTableCell.<BorrowedThing>forTableColumn());
		borrowedThingDescr.setOnEditCommit(
			new EventHandler<CellEditEvent<BorrowedThing, String>>(){
				@Override
				public void handle(CellEditEvent<BorrowedThing, String> t) {
					BorrowedThing editedBorrowedThing = ((BorrowedThing) t.getTableView().getItems().get(t.getTablePosition().getRow()));
					String oldDescription = t.getOldValue();
					//System.out.println("Old description " + oldDescription);
					editedBorrowedThing.setDescription(t.getNewValue());
					controller.updateBorrowedThing(editedBorrowedThing);
					controller.updateBorrowedEventTitle(oldDescription);
					borrowedSearchTable.refresh();
				}});*/
		borrowedBy.setCellValueFactory(new PropertyValueFactory<BorrowedThing, String>("person"));
		/*borrowedBy.setCellFactory(ComboBoxTableCell.<BorrowedThing, String>forTableColumn(controller.personsList()));
		borrowedBy.setOnEditCommit(
				new EventHandler<CellEditEvent<BorrowedThing, String>>() {
					@Override
					public void handle(CellEditEvent<BorrowedThing, String> t) {
						BorrowedThing editedBorrowedThing = ((BorrowedThing) t.getTableView().getItems().get(t.getTablePosition().getRow()));
						String newName = t.getNewValue();
						Person newPerson = controller.findPerson(newName);
						editedBorrowedThing.setPerson(newPerson);
						controller.updateBorrowedThing(editedBorrowedThing);
						borrowedSearchTable.refresh();
					}});*/
		loanDate.setCellValueFactory(new PropertyValueFactory<BorrowedThing, Date>("dateBorrowed"));
		/*loanDate.setCellFactory(dateCellFactory);
		loanDate.setOnEditCommit(
                (TableColumn.CellEditEvent<BorrowedThing, Date> t) -> {
                    BorrowedThing editedBorrowedThing = ((BorrowedThing) t.getTableView().getItems()
                    .get(t.getTablePosition().getRow()));
                    java.sql.Date tempLoanDate = t.getNewValue();
                    java.sql.Date tempRDate = editedBorrowedThing.getReturnDate();
                    if(inputCheck.dateCheck(tempLoanDate, tempRDate)) {
                    	editedBorrowedThing.setDateBorrowed(tempLoanDate);
                    	controller.updateBorrowedThing(editedBorrowedThing);
                    } else {
                    	inputCheck.alertDatesWrong();
    					//sets the loan date as return date
    					editedBorrowedThing.setDateBorrowed(tempRDate);
    					controller.updateBorrowedThing(editedBorrowedThing);
                    }
                    borrowedSearchTable.refresh();
                });*/
		returnDate.setCellValueFactory(new PropertyValueFactory<BorrowedThing, Date>("returnDate"));
		/*returnDate.setCellFactory(dateCellFactory);
		returnDate.setOnEditCommit(
			(TableColumn.CellEditEvent<BorrowedThing, Date> t) -> {
				BorrowedThing editedBorrowedThing = ((BorrowedThing) t.getTableView().getItems()
						.get(t.getTablePosition().getRow()));
				java.sql.Date tempReturnDate = t.getNewValue();
				java.sql.Date tempLDate = editedBorrowedThing.getDateBorrowed();
				//System.out.println("This is the rhythm of the loan date " + tempLDate);
				if(inputCheck.dateCheck(tempLDate, tempReturnDate)) {
					editedBorrowedThing.setReturnDate(tempReturnDate);
					controller.updateBorrowedThing(editedBorrowedThing);
					controller.updateReturnDate(editedBorrowedThing);
				} else {
					inputCheck.alertDatesWrong();
					//sets the loan date as return date
					editedBorrowedThing.setReturnDate(tempLDate);
					controller.updateBorrowedThing(editedBorrowedThing);
					controller.updateReturnDate(editedBorrowedThing);
				}
				borrowedSearchTable.refresh();
			});*/
		returned.setCellValueFactory(new Callback<CellDataFeatures<BorrowedThing, String>, ObservableValue<String>>(){
			public ObservableValue<String> call(CellDataFeatures<BorrowedThing, String> borrowedThingDescr) {
				if (borrowedThingDescr.getValue().isReturned() == true) {
					return new ReadOnlyObjectWrapper<>(myBundle.getBundle().getString("yesYes"));
					//return new ReadOnlyObjectWrapper<>("Yes");
				} else {
					return new ReadOnlyObjectWrapper<>(myBundle.getBundle().getString("noNo"));
					//return new ReadOnlyObjectWrapper<>("No");
				}
			}});
		
		BorrowedThing[] borrowedThings = controller.getBorrowedThings();
		String[] possibleWords = new String[borrowedThings.length];
		for (int i = 0; i < borrowedThings.length; i++) {
			possibleWords[i] = borrowedThings[i].getDescription();
		}
		TextFields.bindAutoCompletion(input, possibleWords);
		
		ObservableList<BorrowedThing> data = FXCollections.observableArrayList(borrowedThings);
		/*FilteredList<BorrowedThing> filteredData = new FilteredList<>(data,
	            s -> !s.isReturned());*/
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
	 * Method for deleting the selected borrowed thing from the database
	 */
	/*@FXML
	public void deleteSelectedReturnedThing() {
		if (inputCheck.confirmDeleting()) {
			controller.removeReturnedThing();
			initialize();
		}
	}*/
	
	/** 
	 * Method that removes an item from the table
	 * @param borrowedThing the borrowed item to be removed
	 */
	/*public void removeFromBorrowedTable(BorrowedThing borrowedThing) {
		borrowedReturnedTable.getItems().remove(borrowedThing);
	}*/
	
	/** 
	 * Method that marks an event as returned
	 */
	/*@FXML
	public void markAsReturned() {
		controller.markReturned();
		initialize();
	}*/
	
	/** 
	 * Method that changes an item's status from returned to borrowed again
	 */
	/*@FXML
	public void makeReturnedBorrowed() {
		controller.changeReturnedToBorrowed();
		initialize();
	}*/
	
	public void searchBorrowedThing(ActionEvent event) {
		String value = input.getText();
		System.out.println("Input " + value);
		ObservableList<BorrowedThing> data = FXCollections.observableArrayList(controller.getBorrowedThings());
		FilteredList<BorrowedThing> filteredData = new FilteredList<>(data,
	            s -> ((s.getDescription())).equals(value));
		borrowedSearchTable.setItems(filteredData);
	}
	
}
	
