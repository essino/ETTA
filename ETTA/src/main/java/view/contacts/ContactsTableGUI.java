package view.contacts;

import java.io.IOException;
import java.sql.Date;
import java.util.Optional;

import controller.CalendarController;
import controller.ContactsController;
import controller.InputCheck;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.util.Callback;
import model.Person;
import res.MyBundle;

public class ContactsTableGUI {
	
	/**
	 * The list view from where adding, editing and deleting can be started
	 */
	@FXML
	private AnchorPane contactsViewAnchorpane;
	
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
	 * Reference to the used ContactsController
	 */
    private ContactsController controller = new ContactsController(this); 
    
    /**
	 * Reference to the used CalendarController
	 */
    private CalendarController calendarController = new CalendarController();
    
 	/**
 	 * The reference of InputCheck class used for checking user's input
 	 */
 	private InputCheck inputCheck = new InputCheck();
 	
 	/**
	 * MyBundle object for setting the right resource bundle to localize the application
	 */
	private MyBundle myBundle = new MyBundle();
	
 	Callback<TableColumn<Person, Date>, TableCell<Person, Date>> dateCellFactory = (TableColumn<Person, Date> param) -> new ContactsDateEditingCell();
  	
 	/** 
  	 * Method that initializes the view and gets the contacts from the controller to display them on the page
  	 */
  	@FXML 
  	public void initialize() { 
  		//sets the placeholder in case table is empty
  		contactsTable.setPlaceholder(new Text(myBundle.getBundle().getString("wishlistEmpty")));
  		
  		contactsName.setCellValueFactory(
                  new PropertyValueFactory<Person, String>("name"));
  		contactsEmail.setCellValueFactory(
                  new PropertyValueFactory<Person, String>("email"));
  		contactsBirthday.setCellValueFactory(
                  new PropertyValueFactory<Person, Date>("birthday"));
  		ObservableList<Person> contacts =  FXCollections.observableArrayList(controller.getPeople());
  		contactsTable.setItems(contacts);
  		//makes in-table editing possible
  		contactsTable.setEditable(true);
  		
  		contactsName.setCellFactory(TextFieldTableCell.<Person>forTableColumn());
  		contactsName.setOnEditCommit(
				new EventHandler<CellEditEvent<Person, String>>() {
					//person's name edited in the table
					@Override
					public void handle(CellEditEvent<Person, String> t) {
						Person editedPerson = ((Person) t.getTableView().getItems().get(t.getTablePosition().getRow()));
						String oldName = editedPerson.getName();
						editedPerson.setName(t.getNewValue());
						controller.updatePerson(editedPerson);
						//updating birthday event
						if(editedPerson.getBirthday()!=null) {
							calendarController.updateBirthday(oldName, editedPerson.getName());
						}
						contactsTable.refresh();
					}});
  		
  		contactsBirthday.setCellFactory(dateCellFactory);
  		contactsBirthday.setOnEditCommit(
				(TableColumn.CellEditEvent<Person, Date> t) -> {
					//person's birthday edited in the table
					Person editedPerson = ((Person) t.getTableView().getItems().get(t.getTablePosition().getRow()));
					Date oldDate = editedPerson.getBirthday();
					editedPerson.setBirthday(t.getNewValue());
					controller.updatePerson(editedPerson);
					//updating birthday event
					calendarController.updateBirthday(editedPerson.getName(), oldDate, editedPerson.getBirthday());
					contactsTable.refresh();
					}	
  				);	
  		
  		contactsEmail.setCellFactory(TextFieldTableCell.<Person>forTableColumn());
  		contactsEmail.setOnEditCommit(
				new EventHandler<CellEditEvent<Person, String>>() {
					@Override
					public void handle(CellEditEvent<Person, String> t) {
						//person's email edited in the table
						Person editedPerson = ((Person) t.getTableView().getItems().get(t.getTablePosition().getRow()));
						//updating person's email
						editedPerson.setEmail(t.getNewValue());
						controller.updatePerson(editedPerson);
						contactsTable.refresh();
					}});
  	} 
  	
	/**
	 * Method showing the view of the Add contacts in the Contacts section
	 * @param event ActionEvent that is handled
	 */
	@FXML
	public void showAddContact(ActionEvent event) {
		AnchorPane showAddContactView = null; 
		FXMLLoader loaderAddContactView  = new FXMLLoader(getClass().getResource("/view/contacts/ContactsAdd.fxml")); 
		loaderAddContactView.setResources(myBundle.getBundle());
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
		//user confirmed deleting
		if(inputCheck.confirmDeleting()) {
			//Contacts controller checks if contact is used in other apllication´s parts
			if(!controller.checkIfContactUsed()) {
				//contact is not used otherwise, controller deletes the person
				controller.deletePerson();
			}
			else {
				//contact is used somewhere
				Alert alert = new Alert(AlertType.CONFIRMATION);
				alert.setTitle(myBundle.getBundle().getString("checkConfirmationTitle"));
				alert.setHeaderText(myBundle.getBundle().getString("personUsed"));
				alert.setContentText(myBundle.getBundle().getString("personUsedDeleteConfirmation"));
				ButtonType buttonOK = new ButtonType("OK", ButtonData.YES);
				ButtonType buttonTypeCancel = new ButtonType(myBundle.getBundle().getString("buttonCancel"), ButtonData.CANCEL_CLOSE);
				alert.getButtonTypes().setAll(buttonOK, buttonTypeCancel);
				Optional<ButtonType> result = alert.showAndWait();
				//user confirmed deleting person and all data for this contact, controller deletes person and all data
				if (result.isPresent() && result.get() == ButtonType.OK) {
					controller.deletePersonAndEvents();
				 }
			}
		}
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
