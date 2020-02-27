package view;

import java.io.IOException;
import java.sql.Date;

import controller.ContactsController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

/**
 * GUI class relating to the Contacts items section
 */
public class ContactsGUI {
	
	ContactsController controller;
	
	/**
	 * The menu view to which the alternative views in the Contacts section are added
	 */
	@FXML 
	BorderPane contactsrootborderpane;
	
	/**
	 * The list view from where adding, editing and deleting can be started
	 */
	@FXML
	AnchorPane contactsviewanchorpane;
	
	/**
	 * The add view from where adding, editing and deleting can be started
	 */
	@FXML
	AnchorPane contactsaddanchorpane;
	
	
	
	@FXML
	TextField personAddress;
	
	@FXML
	TextField personEmail;
	
	@FXML
	TextField personName;
	
	@FXML
	DatePicker personBirthday;
	
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
		FXMLLoader loaderContactsSearchView  = new FXMLLoader(getClass().getResource("/view/ContactsSearch.fxml"));
		try {
			contactsSearchView = loaderContactsSearchView.load();
			} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			}
		contactsrootborderpane.setCenter(contactsSearchView);
	}
	
	/**
	 * Method showing the Contacts view in the Contacts section
	 * @param event ActionEvent that is handled
	 */
	@FXML
	public void showContactsView(ActionEvent event) {
		AnchorPane contactsView = null;
		FXMLLoader loaderContactsView  = new FXMLLoader(getClass().getResource("/view/ContactsView.fxml"));
		try {
			contactsView = loaderContactsView.load();
			} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			}
		contactsrootborderpane.setCenter(contactsView);
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
		contactsviewanchorpane.getChildren().setAll(showAddContactView);
	}
	
	/**
	 * Method showing the Contacts view in the Contacts section and add Person to database
	 * @param event ActionEvent that is handled
	 */
	@FXML
	public void saveNewPerson(ActionEvent event) {
		
		controller.savePerson();
		AnchorPane contactsView = null; 
		FXMLLoader loaderContactsView  = new FXMLLoader(getClass().getResource("/view/ContactsView.fxml")); 
		try {
			contactsView = loaderContactsView.load();
			} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			}
		contactsaddanchorpane.getChildren().setAll(contactsView);
	}
	
	
	@FXML
	public String getPersonAddress(){
		return this.personAddress.getText();
		}
	
	@FXML
	public String getPersonEmail(){
		return this.personEmail.getText();
		}
	
	@FXML
	public String getPersonName(){
		return this.personName.getText();
		}
	
	@FXML
	public Date getPersonBirthday() {
		return Date.valueOf(this.personBirthday.getValue());
	}
	
	
}
