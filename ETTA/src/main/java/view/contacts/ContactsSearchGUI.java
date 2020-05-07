package view.contacts;

import java.sql.Date;
import java.text.DateFormat;
import java.util.Locale;

import org.controlsfx.control.textfield.TextFields;

import controller.ContactsController;
import controller.InputCheck;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableCell;
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
	 * the controller for Contacts
	 */
	ContactsController controller;
	
	/**
	 * The anchorpane for the overall view of contacts list
	 */
	@FXML
	AnchorPane contactsearchanchorpane;
	
	/**
	 * The TableView for viewing all search contacts
	 */
	@FXML
	private TableView<Person> contactsSearchTable;
	
	/**
	 * The TableColumn that shows the person names
	 */
	@FXML
	private TableColumn<Person, String> personName;
	
	/**
	 * The TableColumn that shows e-mail addresses
	 */
	@FXML
	private TableColumn<Person, String> email;
	
	/**
	 * The TableColumn that shows persons birthday
	 */
	@FXML
	private TableColumn<Person, Date> birthday;
	
	
	/**
	 * The TextField where user can write searched contacts
	 */
	@FXML
	private TextField input;
	
	
	/**
	 * A constructor for ContactsSearchGUI in which the controller object is created
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
		//sets the placeholder in case table is empty
		contactsSearchTable.setPlaceholder(new Text(myBundle.getBundle().getString("wishlistEmpty")));
		//names set in the cells
		personName.setCellValueFactory(new PropertyValueFactory<Person, String>("name")); 
		//e-mail address set in the cells
		email.setCellValueFactory(new PropertyValueFactory<Person, String>("email"));
		//birthdays set in the cells
		birthday.setCellValueFactory(new PropertyValueFactory<Person, Date>("birthday"));
		birthday.setCellFactory(column -> {
	        TableCell<Person, Date> cell = new TableCell<Person, Date>() {
	            @Override
	            protected void updateItem(Date item, boolean empty) {
	            	Locale locale = Locale.getDefault();
		    	    DateFormat df = DateFormat.getDateInstance(DateFormat.SHORT, locale);
	                super.updateItem(item, empty);
	                if(item == null) {
	                    setText(null);
	                }
	                else {
	                	this.setText(df.format(item));
	                }
	            }
	        };
	        return cell;
	    });
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
	 * Method for getting the people item from the table
	 * @return the selected person/people
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
		ObservableList<Person> data = FXCollections.observableArrayList(controller.getPeople());
		FilteredList<Person> filteredData = new FilteredList<>(data,
	            s -> ((s.getName())).equals(value));
		contactsSearchTable.setItems(filteredData);
	}
	
}
	


