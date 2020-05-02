package view.contacts;

import java.sql.Date;

import org.controlsfx.control.textfield.TextFields;

import controller.ContactsController;
import controller.InputCheck;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import res.MyBundle;
import model.Person;

public class ContactsSearchGUI {

	/**
	 * MyBundle object for setting the right resource bundle to localize the application
	 */
	MyBundle myBundle = new MyBundle();
	/**
	 * the controller for Borrowed things
	 */
	ContactsController controller;
	
	/**
	 * The anchorpane for the overall view of returned things
	 */
	@FXML
	AnchorPane contactsearchanchorpane;
	
	/**
	 * The TableView for viewing all returned items
	 */
	@FXML
	private TableView<Person> contactsSearchTable;
	
	/**
	 * The TableColumn that shows the items' names
	 */
	@FXML
	private TableColumn<Person, String> personName;
	
	/**
	 * The TableColumn that shows when the items have been loaned
	 */
	@FXML
	private TableColumn<Person, String> email;
	
	/**
	 * The TableColumn that shows when the items are supposed to be returned
	 */
	@FXML
	private TableColumn<Person, Date> birthday;
	
	//returned changed from boolean to String
	/**
	 * The TableColumn that shows if the item has been returned
	 */
	@FXML
	private TextField input;
	
	/**
	 * A constructor for BorrowedTableGUI in which the controller object is created
	 */
	public ContactsSearchGUI() {
		controller = new ContactsController(this);
	}

	/**
	 * The reference of InputCheck class used for checking user's input
	 */
	InputCheck inputCheck = new InputCheck(); 
	
	/**
	 * Initialize-method called when the class is created
	 * Fetches the list of people in the database
	 */
	@FXML
	public void initialize() {
		contactsSearchTable.setPlaceholder(new Text(myBundle.getBundle().getString("wishlistEmpty")));
		personName.setCellValueFactory(new PropertyValueFactory<Person, String>("name")); 
		email.setCellValueFactory(new PropertyValueFactory<Person, String>("email"));
		birthday.setCellValueFactory(new PropertyValueFactory<Person, Date>("birthday"));
		Person[] people = controller.getPeople();
		String[] possibleWords = new String[people.length];
		for (int i = 0; i < people.length; i++) {
			possibleWords[i] = people[i].getName();
		}
		TextFields.bindAutoCompletion(input, possibleWords);
		
		ObservableList<Person> data = FXCollections.observableArrayList(people);
		contactsSearchTable.setItems(data);
	}
	
	/**
	 * Method for getting the selected item from the table
	 * @return the selected item
	 */
	@FXML
	public Person getSelectedPerson() {
		return contactsSearchTable.getSelectionModel().getSelectedItem();
	}
	
	/**
	 * Filters the list of people by the name inputted by the user
	 * @param event the ActionEvent that is handled
	 */
	public void searchContact(ActionEvent event) {
		String value = input.getText();
		System.out.println("Input " + value);
		ObservableList<Person> data = FXCollections.observableArrayList(controller.getPeople());
		FilteredList<Person> filteredData = new FilteredList<>(data,
	            s -> ((s.getName())).equals(value));
		contactsSearchTable.setItems(filteredData);
	}
	
}
	


