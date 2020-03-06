package view;

import java.io.IOException;
import java.sql.Date;

import controller.ContactsController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import model.Person;

public class ContactsTableGUI {
	
	/**
	 * The list view from where adding, editing and deleting can be started
	 */
	@FXML
	AnchorPane contactsViewAnchorpane;
	/**
	 * The reference of TableView (contacts) will be injected by the FXML loader
	 */
	@FXML
    private TableView<Person> contactsTable;
	
	/**
	 * The reference of TableColumn (person's) will be injected by the FXML loader
	 */
    @FXML
    private TableColumn<Person, String> contactsName;
    
    /**
	 * The reference of TableColumn (person's email) will be injected by the FXML loader
	 */
    @FXML
    private TableColumn<Person, String> contactsEmail;
    
    /**
	 * The reference of TableColumn (person's birthday) will be injected by the FXML loader
	 */
    @FXML
    private TableColumn<Person, Date> contactsBirthday;
    
    /**
  	 * The reference of TableColumn (person's address) will be injected by the FXML loader
  	 */
      @FXML
      private TableColumn<Person, String> contactsAddress;
      
     ContactsController controller = new ContactsController(this); 
  	
  	/** 
  	 * Method that initializes the view and gets the contacts  from the controller to display them on the page
  	 */
  	@FXML 
  	public void initialize() { 
  		contactsName.setCellValueFactory(
                  new PropertyValueFactory<Person, String>("name"));
  		contactsEmail.setCellValueFactory(
                  new PropertyValueFactory<Person, String>("email"));
  		contactsBirthday.setCellValueFactory(
                  new PropertyValueFactory<Person, Date>("birthday"));
  		ObservableList<Person> contacts =  FXCollections.observableArrayList(controller.getPeople());
  		contactsTable.setItems(contacts);
  	} 
	/**
	 * Method showing the view of the Add contacts in the Contacts section
	 * @param event ActionEvent that is handled
	 */
	@FXML
	public void showAddContact(ActionEvent event) {
		AnchorPane showAddContactView = null; 
		FXMLLoader loaderAddContactView  = new FXMLLoader(getClass().getResource("/view/ContactsAdd.fxml")); 
		try {
			showAddContactView = loaderAddContactView.load();
			} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			}
		contactsViewAnchorpane.getChildren().setAll(showAddContactView);
	}
	
	/**
	 * Method that tell ContactsController to remove a person from the database
	 */
	@FXML
	public void deleteContact() {
		controller.deletePerson();
	}
	
	/** 
	 * Method that returns the selected contact
	 * @return person that is selected
	 */
	public Person personToDelete() {
		return contactsTable.getSelectionModel().getSelectedItem();
	}
	
	/** 
	 * Method that removes a person from the tableView
	 * @param Person to be removed
	 */
	@FXML
	public void removeFromTable(Person person) {
		contactsTable.getItems().remove(person);
	}
	
}
