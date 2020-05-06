package view.borrowed;

import java.io.IOException;
import java.sql.Date;
import controller.BorrowedController;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.util.Callback;
import model.BorrowedThing;
import model.Person;
import res.MyBundle;
import controller.InputCheck;

/**
 * GUI class in charge of the view showing the list of borrowed items
 */
public class BorrowedTableGUI extends AbstractBorrowedGUI {
	
	/**
	 * MyBundle object for setting the right resource bundle to localize the application
	 */
	MyBundle myBundle = new MyBundle();
	
	/**
	 * the controller for Borrowed things
	 */
	BorrowedController controller;
	
	/**
	 * The anchorpane for the overall view of borrowed things
	 */
	@FXML
	AnchorPane borrowedviewanchorpane;
	
	/**
	 * The TableView for viewing all borrowed things
	 */
	@FXML
	private TableView<BorrowedThing> borrowedTable;
	
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
	
	/**
	 * The TableColumn that shows if the item has been returned
	 */
	@FXML
	private TableColumn<BorrowedThing, String> returned;
	
	/**
	 * A constructor for BorrowedTableGUI in which the controller object is created
	 */
	public BorrowedTableGUI() {
		controller = new BorrowedController(this);
	}

	/**
	 * The reference of InputCheck class used for checking user's input
	 */
	InputCheck inputCheck = new InputCheck(); 
	
	/**
	 * Cell factory for in-table editing of cells containing dates
	 */
	Callback<TableColumn<BorrowedThing, Date>, TableCell<BorrowedThing, Date>> dateCellFactory = (TableColumn<BorrowedThing, Date> param) -> new DateEditingCell();
	
	/**
	 * Method that shows the view of all the borrowed items
	 * @param event either Borrowed Things tab or Borrowed Items button is pushed
	 */
	@FXML
	public void showBorrowedAdd(ActionEvent event) {
		AnchorPane showBorrowedAdd = null;
		FXMLLoader loaderBorrowedAdd = new FXMLLoader(getClass().getResource("/view/borrowed/BorrowedAdd.fxml"));
		loaderBorrowedAdd.setResources(myBundle.getBundle());
		try {
			showBorrowedAdd = loaderBorrowedAdd.load();
			} catch (IOException e) {
			e.printStackTrace();
			}
		//shows the loaded fxml file
		borrowedviewanchorpane.getChildren().setAll(showBorrowedAdd);
	}

	/**
	 * Initialize-method called when the class is created
	 * Fetches the list of borrowed items in the database 
	 * Also allows for in-table editing of the borrowed items on the list
	 */
	@FXML
	public void initialize() {
		//makes in-table editing possible
		borrowedTable.setEditable(true);
		//sets the placeholder in case table is empty
		borrowedTable.setPlaceholder(new Text(myBundle.getBundle().getString("wishlistEmpty")));
		//descriptions set in the cells
		borrowedThingDescr.setCellValueFactory(new PropertyValueFactory<BorrowedThing, String>("description")); 
		//description cells turn to textfield cells when in-table editing starts in them
		borrowedThingDescr.setCellFactory(TextFieldTableCell.<BorrowedThing>forTableColumn());
		//edited content saved
		borrowedThingDescr.setOnEditCommit(
			new EventHandler<CellEditEvent<BorrowedThing, String>>(){
				@Override
				public void handle(CellEditEvent<BorrowedThing, String> t) {
					//edited borrowed thing
					BorrowedThing editedBorrowedThing = ((BorrowedThing) t.getTableView().getItems().get(t.getTablePosition().getRow()));
					//getting the old description
					String oldDescription = t.getOldValue();
					//getting and setting the new description
					editedBorrowedThing.setDescription(t.getNewValue());
					//controller updates the borrowed thing
					controller.updateBorrowedThing(editedBorrowedThing);
					//controller updates the borrowed event
					controller.updateBorrowedEventTitle(oldDescription, editedBorrowedThing);
					//refreshes the table so the new edited data can be seen
					borrowedTable.refresh();
				}});
		//sets the contacts in the table
		borrowedBy.setCellValueFactory(new PropertyValueFactory<BorrowedThing, String>("person"));
		//makes the cell a combo box cell when editing starts
		borrowedBy.setCellFactory(ComboBoxTableCell.<BorrowedThing, String>forTableColumn(controller.personsList()));
		//edited content saved
		borrowedBy.setOnEditCommit(
				new EventHandler<CellEditEvent<BorrowedThing, String>>() {
					@Override
					public void handle(CellEditEvent<BorrowedThing, String> t) {
						BorrowedThing editedBorrowedThing = ((BorrowedThing) t.getTableView().getItems().get(t.getTablePosition().getRow()));
						//getting the old contact
						Person oldPerson = editedBorrowedThing.getPerson();
						//getting the new name of contact
						String newName = t.getNewValue();
						//finding the person matching that name
						Person newPerson = controller.findPerson(newName);
						//a new person is the borrower
						editedBorrowedThing.setPerson(newPerson);
						//updating the borrowed thing
						controller.updateBorrowedThing(editedBorrowedThing);
						//updating the borrowed event
						controller.updateBorrowedEventPerson(oldPerson, editedBorrowedThing);
						borrowedTable.refresh();
					}});
		loanDate.setCellValueFactory(new PropertyValueFactory<BorrowedThing, Date>("dateBorrowed"));
		loanDate.setCellFactory(dateCellFactory);
		loanDate.setOnEditCommit(
                (TableColumn.CellEditEvent<BorrowedThing, Date> t) -> {
                    BorrowedThing editedBorrowedThing = ((BorrowedThing) t.getTableView().getItems()
                    .get(t.getTablePosition().getRow()));
                    //getting date values for making sure the return date is not before loan date even when loan date is edited
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
                    borrowedTable.refresh();
                });
		returnDate.setCellValueFactory(new PropertyValueFactory<BorrowedThing, Date>("returnDate"));
		returnDate.setCellFactory(dateCellFactory);
		returnDate.setOnEditCommit(
			(TableColumn.CellEditEvent<BorrowedThing, Date> t) -> {
				BorrowedThing editedBorrowedThing = ((BorrowedThing) t.getTableView().getItems()
						.get(t.getTablePosition().getRow()));
				//getting date values for making sure the return date is not before loan date even when return date is edited
				java.sql.Date tempReturnDate = t.getNewValue();
				java.sql.Date tempLDate = editedBorrowedThing.getDateBorrowed();
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
				borrowedTable.refresh();
			});
		//showing whether the item is borrowed or returned; this cell not editable, but there is a different button for changing this
		returned.setCellValueFactory(new Callback<CellDataFeatures<BorrowedThing, String>, ObservableValue<String>>(){
			public ObservableValue<String> call(CellDataFeatures<BorrowedThing, String> borrowedThingDescr) {
				if (borrowedThingDescr.getValue().isReturned() == true) {
					return new ReadOnlyObjectWrapper<>(myBundle.getBundle().getString("yesYes"));
				} else {
					return new ReadOnlyObjectWrapper<>(myBundle.getBundle().getString("noNo"));
				}
			}});
		//gets the data
		ObservableList<BorrowedThing> data = FXCollections.observableArrayList(controller.getBorrowedThings());
		//filters the data
		FilteredList<BorrowedThing> filteredData = new FilteredList<>(data,
	            s -> !s.isReturned());
		//sets the data
		borrowedTable.setItems(filteredData);
	}
	
	/**
	 * Method for getting the selected item from the table
	 * @return super.borrowedTable.getSelectionModel().getSelectedItem() the selected item
	 */
	@FXML
	public BorrowedThing getSelectedBorrowedThing() {
		return super.getSelectedBorrowedThing();
	}
	
	/**
	 * Method for deleting the selected borrowed thing from the database
	 */
	@FXML
	public void deleteSelectedBorrowedThing() {
		//checks that an item is selected from the table and that the user is sure about deleting it
		if (super.checkItemIsSelected()) {
			if (inputCheck.confirmDeleting()) {
				//controller passes on the deletion of the item
				controller.removeBorrowedThing();
				initialize();
			}
		} else {
			//alerts that nothing is selected from the table
			inputCheck.alertNothingSelected();
		}
	}
	
	/**
	 * Method that marks an event as returned
	 */
	@FXML
	public void markAsReturned() {
		//checks that an item is selected from the table and that the user is sure about making the item returned
		if (super.checkItemIsSelected()) {
			if (inputCheck.confirmReturn()) {
				//controller passes on the returned status
				controller.markReturned();
				initialize();
			}
		} else {
			//alerts that nothing is selected from the table
			inputCheck.alertNothingSelected();
		}
	}
	
} 


	
	
	
	
	

