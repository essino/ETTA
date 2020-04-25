package view.borrowed;

import java.sql.Date;
import java.text.DateFormat;
import java.util.Locale;
import java.util.Observable;
import java.util.Observer;
import java.util.ResourceBundle;

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

public class BorrowedSearchGUI implements Observer{
	public static final BorrowedSearchGUI single = new BorrowedSearchGUI();
	/**
	 * MyBundle object for setting the right resource bundle to localize the application
	 */
	MyBundle myBundleInst = MyBundle.getInstance();
	MyBundle myBundle;
	
	ResourceBundle bundle;
	
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
	private BorrowedSearchGUI() {
		controller = new BorrowedController();
		this.myBundle = myBundleInst;
		this.bundle=myBundle.getBundle();
		this.myBundle.addObserver(this);
	}
	
	public static BorrowedSearchGUI getInstance() {
		return single;
	}

	@Override
	public void update(Observable o, Object arg) {
		System.out.println("observer informed");
		if(o instanceof MyBundle) {
			this.bundle=myBundle.getBundle();
		}
		
	}
	/**
	 * The reference of InputCheck class used for checking user's input
	 */
	InputCheck inputCheck = InputCheck.getInstance();
	
	Callback<TableColumn<BorrowedThing, Date>, TableCell<BorrowedThing, Date>> dateCellFactory = (TableColumn<BorrowedThing, Date> param) -> new DateEditingCell();

	/**
	 * Initialize-method called when the class is created
	 * Fetches the list of returned items in the database 
	 * Also allows for inline editing of the borrowed items on the list
	 */
	@FXML
	public void initialize() {
		System.out.println("We don't need no education!");
		borrowedSearchTable.setPlaceholder(new Text(bundle.getString("wishlistEmpty")));
		borrowedThingDescr.setCellValueFactory(new PropertyValueFactory<BorrowedThing, String>("description")); 
		borrowedBy.setCellValueFactory(new PropertyValueFactory<BorrowedThing, String>("person"));
		loanDate.setCellValueFactory(new PropertyValueFactory<BorrowedThing, Date>("dateBorrowed"));
		returnDate.setCellValueFactory(new PropertyValueFactory<BorrowedThing, Date>("returnDate"));
		returned.setCellValueFactory(new Callback<CellDataFeatures<BorrowedThing, String>, ObservableValue<String>>(){
			public ObservableValue<String> call(CellDataFeatures<BorrowedThing, String> borrowedThingDescr) {
				if (borrowedThingDescr.getValue().isReturned() == true) {
					return new ReadOnlyObjectWrapper<>(bundle.getString("yesYes"));
					//return new ReadOnlyObjectWrapper<>("Yes");
				} else {
					return new ReadOnlyObjectWrapper<>(bundle.getString("noNo"));
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
	
	public void searchBorrowedThing(ActionEvent event) {
		String value = input.getText();
		System.out.println("Input " + value);
		ObservableList<BorrowedThing> data = FXCollections.observableArrayList(controller.getBorrowedThings());
		FilteredList<BorrowedThing> filteredData = new FilteredList<>(data,
	            s -> ((s.getDescription())).equals(value));
		borrowedSearchTable.setItems(filteredData);
	}
	
}
	

