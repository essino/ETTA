package view.contacts;

import java.io.IOException;
import java.sql.Date;

import controller.ContactsController;
import controller.InputCheck;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import res.MyBundle;

/**
 * GUI class relating to the Contacts items section
 */
public class ContactsGUI {
	
	/**
	 * Reference to the used ContactsController
	 */
	private ContactsController controller;
	
	/**
	 * The menu view to which the alternative views in the Contacts section are added
	 */
	@FXML 
	private BorderPane contactsrootborderpane;
	
	/**
	 * The add view from where adding, editing and deleting can be started
	 */
	@FXML
	private AnchorPane contactsaddanchorpane;
	/**
	 * Text field for the email of the person to be added
	 */
	@FXML
	private TextField personEmail;
	/**
	 * Text field for the name of the person to be added
	 */
	@FXML
	private TextField personName;
	/**
	 * DatePicker for the birthday of the person to be added
	 */
	@FXML
	private DatePicker personBirthday;
	
	/**
	 * MyBundle object for setting the right resource bundle to localize the application
	 */
	private MyBundle myBundle = new MyBundle();
	/**
	 * The reference of InputCheck class used for checking user's input
	 */
	private InputCheck inputCheck = new InputCheck();
	
	/** 
	 * Constructor  
	 */ 
	public ContactsGUI () {
		controller = new ContactsController(this);
	}
	
	/**
	 * Method showing the search view in the Contacts section
	 * @param event ActionEvent that is handled
	 */
	@FXML
	public void showSearchView(ActionEvent event) {
		AnchorPane contactsSearchView = null;
		FXMLLoader loaderContactsSearchView  = new FXMLLoader(getClass().getResource("/view/contacts/ContactsSearch1.fxml"));
		loaderContactsSearchView.setResources(myBundle.getBundle());
		try {
			contactsSearchView = loaderContactsSearchView.load();
			} catch (IOException e) {
			e.printStackTrace();
			}
		contactsrootborderpane.setCenter(contactsSearchView);
	}
	
	/**
	 * Method showing the main Contacts view in the Contacts section
	 * @param event ActionEvent that is handled
	 */
	@FXML
	public void showContactsView(ActionEvent event) {
		AnchorPane contactsView = null;
		FXMLLoader loaderContactsView  = new FXMLLoader(getClass().getResource("/view/contacts/ContactsView.fxml"));
		loaderContactsView.setResources(myBundle.getBundle());
		try {
			contactsView = loaderContactsView.load();
			} catch (IOException e) {
			e.printStackTrace();
			}
		contactsrootborderpane.setCenter(contactsView);
	}
	
	/**
	 * Method adding a new Person to database and showing the main Contacts view in the Contacts section
	 * @param event ActionEvent that is handled
	 */
	@FXML
	public void saveNewPerson(ActionEvent event) {
		//check if there a name given
		if(inputCheck.isInputEmpty(personName.getText())) {
			inputCheck.alertInputEmpty();
		}
		else {
			//check if a person with this name exists already, creating of people with the same name is not supported
			if(!controller.checkIfPersonexists(getPersonName())) {
				//ContactsController saves the new person
				controller.savePerson();
				AnchorPane contactsView = null; 
				FXMLLoader loaderContactsView  = new FXMLLoader(getClass().getResource("/view/contacts/ContactsView.fxml")); 
				loaderContactsView.setResources(myBundle.getBundle());
				try {
					contactsView = loaderContactsView.load();
				} catch (IOException e) {
					e.printStackTrace();
				}
				contactsaddanchorpane.getChildren().setAll(contactsView);
			}
			else {
				inputCheck.alertPersonExists();
			}
		}
	}
	
	/**
	 * Method for getting the value of the Person's email text field
	 * @return String the Person's email
	 */
	@FXML
	public String getPersonEmail(){
		return this.personEmail.getText();
		}
	
	/**
	 * Method for getting the value of the Person's name text field
	 * @return String the Person's name
	 */
	@FXML
	public String getPersonName(){
		return this.personName.getText();
		}
	
	/**
	 * Method for getting the value of the Person's birthday datepicker
	 * @return Date the Person's birthday
	 */
	@FXML
	public Date getPersonBirthday() {
		try {
		return Date.valueOf(this.personBirthday.getValue());
		}
		//no birthday given
		catch(NullPointerException e) {
			return null;
		}
	}
	
	/**
	 * Method for canceling the adding of a new person
	 * Exits the add view and returns to the main contacts view
	 */
	@FXML
	public void cancelAdding() {
		AnchorPane contactsView = null; 
		FXMLLoader loaderContactsView  = new FXMLLoader(getClass().getResource("/view/contacts/ContactsView.fxml")); 
		loaderContactsView.setResources(myBundle.getBundle());
		try {
			contactsView = loaderContactsView.load();
		} catch (IOException e) {
			e.printStackTrace();
		}
		contactsaddanchorpane.getChildren().setAll(contactsView);
	}
	
}
