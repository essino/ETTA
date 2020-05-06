package view.borrowed;

import controller.BorrowedController;
import controller.InputCheck;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import model.BorrowedThing;

public class BorrowedReturnedTableGUI extends AbstractBorrowedGUI { 
		
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
		private TableView<BorrowedThing> borrowedTable;
		
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
		 * Uses the Abstract class's initializeTable() method
		 * Also allows for in-table editing of the borrowed items on the list
		 */
		@FXML
		public void initialize() {
			super.initializeTable();
			//gets the data
			ObservableList<BorrowedThing> data = FXCollections.observableArrayList(controller.getBorrowedThings());
			//filters the data
			FilteredList<BorrowedThing> filteredData = new FilteredList<>(data,
		            s -> s.isReturned());
			//sets the data
			borrowedTable.setItems(filteredData);
		}
		
		/**
		 * Method for getting the selected item from the table
		 * uses the abstract class's method
		 * @return super.borrowedReturnedTable.getSelectionModel().getSelectedItem() the selected item
		 */
		@FXML
		public BorrowedThing getSelectedBorrowedThing() {
			return super.getSelectedBorrowedThing();
		}
		
		/**
		 * Method for deleting the selected borrowed thing from the database
		 */
		@FXML
		public void deleteSelectedReturnedThing() {
			//checking that item is selected is done in the abstract class
			if (super.checkItemIsSelected()) {
				//checking that the user want to delete item
				if (inputCheck.confirmDeleting()) {
					//item removed
					controller.removeReturnedThing();
					initialize();
				}
			} else {
				//if nothing selected from the table, the user is alerted
				inputCheck.alertNothingSelected();
			}
		}
		
		/** 
		 * Method that changes an item's status from returned to borrowed again
		 */
		@FXML
		public void makeReturnedBorrowed() {
			//checking that an item is selected from the table in the abstract class
			if (super.checkItemIsSelected()) {
				//making a returned item borrowed
				controller.changeReturnedToBorrowed();
				initialize();
			} else {
				//if nothing selected from the table, the user is alerted
				inputCheck.alertNothingSelected();
			}
		}
	
}
