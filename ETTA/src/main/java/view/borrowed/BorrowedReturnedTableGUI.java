package view.borrowed;

import java.sql.Date;
import java.text.DateFormat;
import java.util.Locale;
import controller.BorrowedController;
import controller.InputCheck;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.util.Callback;
import model.BorrowedThing;
import res.MyBundle;

public class BorrowedReturnedTableGUI {
		
		/**
		 * Reference to the controller for Borrowed things
		 */
		BorrowedController controller;
		
		/**
		 * The anchorpane for the overall view of returned things
		 */
		@FXML
		AnchorPane borrowedReturnedViewAnchorpane;
		
		/**
		 * The TableView for viewing all returned items
		 */
		@FXML
		private TableView<BorrowedThing> borrowedReturnedTable;
		
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
		public BorrowedReturnedTableGUI() {
			controller = new BorrowedController(this);
		}

		/**
		 * InputCheck object used for checking user's input
		 */
		InputCheck inputCheck = new InputCheck(); 
		
		/**
		 * Initialize method called when the class is created
		 * Fetches the list of returned items in the database 
		 * Also allows for in-table editing of the borrowed items on the list
		 */
		@FXML
		public void initialize() {
			MyBundle myBundle = new MyBundle();
			//for setting the right formatting for dates in table cells
			Locale locale = Locale.getDefault();
    	    DateFormat df = DateFormat.getDateInstance(DateFormat.SHORT, locale);
    	    //in case table is empty, a placeholder text is shown
    	    borrowedReturnedTable.setPlaceholder(new Text(myBundle.getBundle().getString("wishlistEmpty")));
			borrowedThingDescr.setCellValueFactory(new PropertyValueFactory<BorrowedThing, String>("description")); 
			borrowedBy.setCellValueFactory(new PropertyValueFactory<BorrowedThing, String>("person"));
			loanDate.setCellValueFactory(new PropertyValueFactory<BorrowedThing, Date>("dateBorrowed"));
			//for setting the right formatting for dates in table cells
			loanDate.setCellFactory(column -> {
		        TableCell<BorrowedThing, Date> cell = new TableCell<BorrowedThing, Date>() {
		            @Override
		            protected void updateItem(Date item, boolean empty) {
		                super.updateItem(item, empty);
		                if(empty) {
		                    setText(null);
		                }
		                else {
		                	setText(df.format(item));
		                }
		            }
		        };
		        return cell;
		    });
			returnDate.setCellValueFactory(new PropertyValueFactory<BorrowedThing, Date>("returnDate"));
			//for setting the right formatting for dates in table cells
			returnDate.setCellFactory(column -> {
		        TableCell<BorrowedThing, Date> cell = new TableCell<BorrowedThing, Date>() {
		            @Override
		            protected void updateItem(Date item, boolean empty) {
		                super.updateItem(item, empty);
		                if(empty) {
		                    setText(null);
		                }
		                else {
		                	setText(df.format(item));
		                }
		            }
		        };
		        return cell;
		    });
			returned.setCellValueFactory(new Callback<CellDataFeatures<BorrowedThing, String>, ObservableValue<String>>(){
				public ObservableValue<String> call(CellDataFeatures<BorrowedThing, String> borrowedThingDescr) {
					if (borrowedThingDescr.getValue().isReturned() == true) {
						return new ReadOnlyObjectWrapper<>(myBundle.getBundle().getString("yesYes")); 
					} else {
						return new ReadOnlyObjectWrapper<>(myBundle.getBundle().getString("noNo"));
					}
				}});
			//gets all the borrowed things from the database through the controller
			ObservableList<BorrowedThing> data = FXCollections.observableArrayList(controller.getBorrowedThings());
			//filters the returned items that are to be shown
			FilteredList<BorrowedThing> filteredData = new FilteredList<>(data,
		            s -> s.isReturned());
			//sets the needed items in the table
			borrowedReturnedTable.setItems(filteredData);
		}
		
		/**
		 * Method for getting the selected item from the table
		 * @return borrowedReturnedTable.getSelectionModel().getSelectedItem() the selected item
		 */
		@FXML
		public BorrowedThing getSelectedBorrowedThing() {
			return borrowedReturnedTable.getSelectionModel().getSelectedItem();
		}
		
		/**
		 * Method for checking if an item has been selected from the table
		 * @return thing != null boolean indicating that the selected item is not null
		 */
		public boolean checkItemIsSelected() {
			BorrowedThing thing = getSelectedBorrowedThing();
			return thing != null;
		}
		
		/**
		 * Method for deleting the selected borrowed thing from the database
		 */
		@FXML
		public void deleteSelectedReturnedThing() {
			if (checkItemIsSelected()) {
				if (inputCheck.confirmDeleting()) {
					controller.removeReturnedThing();
					initialize();
				}
			} else {
				inputCheck.alertNothingSelected();
			}
		}
		
		/** 
		 * Method that removes an item from the table
		 * @param borrowedThing the borrowed item to be removed
		 */
		public void removeFromBorrowedTable(BorrowedThing borrowedThing) {
			borrowedReturnedTable.getItems().remove(borrowedThing);
		}
		
		/** 
		 * Method that changes an item's status from returned to borrowed again
		 */
		@FXML
		public void makeReturnedBorrowed() {
			if (checkItemIsSelected()) {
				controller.changeReturnedToBorrowed();
				initialize();
				
			} else {
				inputCheck.alertNothingSelected();
			}
		}
	
}
