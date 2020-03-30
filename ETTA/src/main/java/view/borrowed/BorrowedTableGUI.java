package view.borrowed;

import java.io.IOException;
import java.sql.Date;


import controller.BorrowedController;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import javafx.scene.paint.Color;
import javafx.util.Callback;
import model.BorrowedThing;
import model.Item;
import model.Person;
import controller.InputCheck;
//import view.borrowed.DateEditingCell;



public class BorrowedTableGUI {
	
	//NB! this is different in Tiina's WishlistTableGUI - could cause problems
	/**
	 * the controller for Borrowed things
	 */
	BorrowedController controller;// = new BorrowedController(this);
	
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
	
	//returned changed from boolean to String
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
	
	//DateEditingCell cell = new DateEditingCell();
	Callback<TableColumn<BorrowedThing, Date>, TableCell<BorrowedThing, Date>> dateCellFactory = (TableColumn<BorrowedThing, Date> param) -> new DateEditingCell();

	/**
	 * Method that shows all the borrowed items
	 * @param event either Borrowed Things tab or Borrowed Items button is pushed
	 */
	@FXML
	public void showBorrowedAdd(ActionEvent event) {
		AnchorPane showBorrowedAdd = null;
		FXMLLoader loaderBorrowedAdd = new FXMLLoader(getClass().getResource("/view/borrowed/BorrowedAdd.fxml"));
		try {
			showBorrowedAdd = loaderBorrowedAdd.load();
			} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			}
		//shows the loaded fxml file
		borrowedviewanchorpane.getChildren().setAll(showBorrowedAdd);
	}

	/**
	 * Initialize-method called when the class is created
	 * Fetches the list of borrowed items in the database 
	 */
	@FXML
	public void initialize() {
		borrowedTable.setEditable(true);
		borrowedThingDescr.setCellValueFactory(new PropertyValueFactory<BorrowedThing, String>("description")); 
		//lisäys
		borrowedThingDescr.setCellFactory(TextFieldTableCell.<BorrowedThing>forTableColumn());
		borrowedThingDescr.setOnEditCommit(
			new EventHandler<CellEditEvent<BorrowedThing, String>>(){
				@Override
				public void handle(CellEditEvent<BorrowedThing, String> t) {
					BorrowedThing editedBorrowedThing = ((BorrowedThing) t.getTableView().getItems().get(t.getTablePosition().getRow()));
					String oldDescription = t.getOldValue();
					//System.out.println("Tididii dii tididii " + oldDescription);
					editedBorrowedThing.setDescription(t.getNewValue());
					
					
					controller.updateBorrowedThing(editedBorrowedThing);
					controller.updateBorrowedEventTitle(oldDescription);
					borrowedTable.refresh();
				}
			}
		);
		borrowedBy.setCellValueFactory(new PropertyValueFactory<BorrowedThing, String>("person"));
		borrowedBy.setCellFactory(ComboBoxTableCell.<BorrowedThing, String>forTableColumn(controller.personsList()));
		borrowedBy.setOnEditCommit(
				new EventHandler<CellEditEvent<BorrowedThing, String>>() {
					@Override
					public void handle(CellEditEvent<BorrowedThing, String> t) {
						
						BorrowedThing editedBorrowedThing = ((BorrowedThing) t.getTableView().getItems().get(t.getTablePosition().getRow()));
						String newName = t.getNewValue();
						Person newPerson = controller.findPerson(newName);
						editedBorrowedThing.setPerson(newPerson);
						controller.updateBorrowedThing(editedBorrowedThing);
						borrowedTable.refresh();
					}});
		loanDate.setCellValueFactory(new PropertyValueFactory<BorrowedThing, Date>("dateBorrowed"));
		loanDate.setCellFactory(dateCellFactory);
		
		loanDate.setOnEditCommit(
                (TableColumn.CellEditEvent<BorrowedThing, Date> t) -> {
                    BorrowedThing editedBorrowedThing = ((BorrowedThing) t.getTableView().getItems()
                    .get(t.getTablePosition().getRow()));
                    //essin lisäys
               
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
                }
			);
		
		
		returnDate.setCellValueFactory(new PropertyValueFactory<BorrowedThing, Date>("returnDate"));
		//returned.setCellValueFactory(new PropertyValueFactory<BorrowedThing, Boolean>("returned"));
		returnDate.setCellFactory(dateCellFactory);
		returnDate.setOnEditCommit(
			(TableColumn.CellEditEvent<BorrowedThing, Date> t) -> {
				BorrowedThing editedBorrowedThing = ((BorrowedThing) t.getTableView().getItems()
						.get(t.getTablePosition().getRow()));
				java.sql.Date tempReturnDate = t.getNewValue();
				//essin lisäys
				java.sql.Date tempLDate = editedBorrowedThing.getDateBorrowed();
				System.out.println("This is the rhythm of the loan date " + tempLDate);
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
			}
			//essin lisäys loppuu
		);
		returned.setCellValueFactory(new Callback<CellDataFeatures<BorrowedThing, String>, ObservableValue<String>>(){
			public ObservableValue<String> call(CellDataFeatures<BorrowedThing, String> borrowedThingDescr) {
				if (borrowedThingDescr.getValue().isReturned() == true) {
					return new ReadOnlyObjectWrapper<>("Yes");
				} else {
					return new ReadOnlyObjectWrapper<>("No");
				}
			}
		});
		ObservableList<BorrowedThing> data = FXCollections.observableArrayList(controller.getBorrowedThings());
		borrowedTable.setItems(data);
	}
	
	/**
	 * Method for getting the selected item from the table
	 * @return the selected item
	 */
	@FXML
	public BorrowedThing getSelectedBorrowedThing() {
		return borrowedTable.getSelectionModel().getSelectedItem();
	}
	
	/**
	 * Method for deleting the selected borrowed thing from the database
	 */
	@FXML
	public void deleteSelectedBorrowedThing() {
		if (inputCheck.confirmDeleting()) {
			controller.removeBorrowedThing();
			initialize();
		}
	}
	
	/** 
	 * Method that removes an item from the table
	 * @param borrowedThing the borrowed item to be removed
	 */
	public void removeFromBorrowedTable(BorrowedThing borrowedThing) {
		borrowedTable.getItems().remove(borrowedThing);
	}
	

	@FXML
	public void markAsReturned() {
		controller.markReturned();
		initialize();
	}
	
} 


	
	
	
	
	

