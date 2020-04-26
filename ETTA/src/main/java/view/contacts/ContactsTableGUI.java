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
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.AnchorPane;
import javafx.util.Callback;
import model.Person;
import res.MyBundle;

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
     
     CalendarController calendarController = new CalendarController();
 	/**
 	 * The reference of InputCheck class used for checking user's input
 	 */
 	InputCheck inputCheck = new InputCheck();
 	
 	MyBundle myBundle = MyBundle.getInstance();
  	
 	Callback<TableColumn<Person, Date>, TableCell<Person, Date>> dateCellFactory = (TableColumn<Person, Date> param) -> new ContactsDateEditingCell();
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
  		contactsTable.setEditable(true);
  		
  		contactsName.setCellFactory(TextFieldTableCell.<Person>forTableColumn());
  		contactsName.setOnEditCommit(
				new EventHandler<CellEditEvent<Person, String>>() {
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
				Person editedPerson = ((Person) t.getTableView().getItems().get(t.getTablePosition().getRow()));
				Date oldDate = editedPerson.getBirthday();
				editedPerson.setBirthday(t.getNewValue());
				controller.updatePerson(editedPerson);
				calendarController.updateBirthday(editedPerson.getName(), oldDate, editedPerson.getBirthday());
				contactsTable.refresh();
				}	
			);	
  		
  		contactsEmail.setCellFactory(TextFieldTableCell.<Person>forTableColumn());
  		contactsEmail.setOnEditCommit(
				new EventHandler<CellEditEvent<Person, String>>() {
					@Override
					public void handle(CellEditEvent<Person, String> t) {
						Person editedPerson = ((Person) t.getTableView().getItems().get(t.getTablePosition().getRow()));
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
		if(inputCheck.confirmDeleting()) {
			if(!controller.checkIfContactUsed()) {
				controller.deletePerson();
			}
			else {
				Alert alert = new Alert(AlertType.CONFIRMATION);
				alert.setTitle("Confirmation");
				alert.setHeaderText("This contact is used in other parts.");
				alert.setContentText("Are you sure you want to delete this data permanently? "
						+ "All the data where the contact is used will be also deleted.");
				Optional<ButtonType> result = alert.showAndWait();
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
