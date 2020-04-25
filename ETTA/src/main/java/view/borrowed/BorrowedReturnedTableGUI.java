package view.borrowed;

import java.io.IOException;
import java.sql.Date;
import java.text.DateFormat;
import java.util.Locale;
import java.util.Observable;
import java.util.Observer;
import java.util.ResourceBundle;
import java.util.function.Predicate;

import controller.BorrowedController;
import controller.InputCheck;
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
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.util.Callback;
import model.BorrowedThing;
import model.Person;
import res.MyBundle;

public class BorrowedReturnedTableGUI implements Observer{
	public static final BorrowedReturnedTableGUI single = new BorrowedReturnedTableGUI();
	MyBundle myBundleInst = MyBundle.getInstance();
	MyBundle myBundle;
	ResourceBundle bundle;
		
		/**
		 * the controller for Borrowed things
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
		
		//returned changed from boolean to String
		/**
		 * The TableColumn that shows if the item has been returned
		 */
		@FXML
		private TableColumn<BorrowedThing, String> returned;
		
		/**
		 * A constructor for BorrowedTableGUI in which the controller object is created
		 */
		private BorrowedReturnedTableGUI() {
			controller = new BorrowedController();
			this.myBundle = myBundleInst;
			this.bundle=myBundle.getBundle();
			this.myBundle.addObserver(this);
			System.out.println("observers in gui " + myBundle.countObservers());
		}
		
		public static BorrowedReturnedTableGUI getInstance() {
			return single;
		}
		
		@Override
		public void update(Observable o, Object arg) {
			System.out.println("observer informed");
			if(o instanceof MyBundle) {
				this.bundle=myBundle.getBundle();
			}
			
		}

		/**
		 * The reference of InputCheck class used for checking user's input
		 */
		InputCheck inputCheck = InputCheck.getInstance();
		
		//not needed because returned items are not editable
		//Callback<TableColumn<BorrowedThing, Date>, TableCell<BorrowedThing, Date>> dateCellFactory = (TableColumn<BorrowedThing, Date> param) -> new DateEditingCell();

		/**
		 * Initialize-method called when the class is created
		 * Fetches the list of returned items in the database 
		 * Also allows for inline editing of the borrowed items on the list
		 */
		@FXML
		public void initialize() {
	
			//for setting the right formatting for dates in table cells
			Locale locale = Locale.getDefault();
    	    DateFormat df = DateFormat.getDateInstance(DateFormat.SHORT, locale);
    	    borrowedReturnedTable.setPlaceholder(new Text(bundle.getString("wishlistEmpty")));
			borrowedThingDescr.setCellValueFactory(new PropertyValueFactory<BorrowedThing, String>("description")); 
			borrowedThingDescr.setCellFactory(TextFieldTableCell.<BorrowedThing>forTableColumn());
			borrowedBy.setCellValueFactory(new PropertyValueFactory<BorrowedThing, String>("person"));
			loanDate.setCellValueFactory(new PropertyValueFactory<BorrowedThing, Date>("dateBorrowed"));
			//for setting the right formatting for dates in table cells
			loanDate.setCellFactory(column -> {
		        TableCell<BorrowedThing, Date> cell = new TableCell<BorrowedThing, Date>() {
		           // private SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");
		        	
		            @Override
		            protected void updateItem(Date item, boolean empty) {
		                super.updateItem(item, empty);
		                if(empty) {
		                    setText(null);
		                }
		                else {
		                    //this.setText(format.format(item));
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
						return new ReadOnlyObjectWrapper<>(bundle.getString("yesYes")); 
						//return new ReadOnlyObjectWrapper<>("Yes");
					} else {
						return new ReadOnlyObjectWrapper<>(bundle.getString("noNo"));
						//return new ReadOnlyObjectWrapper<>("No");
					}
				}});
			ObservableList<BorrowedThing> data = FXCollections.observableArrayList(controller.getBorrowedThings());
			FilteredList<BorrowedThing> filteredData = new FilteredList<>(data,
		            s -> s.isReturned());
			borrowedReturnedTable.setItems(filteredData);
		}
		
		/**
		 * Method for getting the selected item from the table
		 * @return the selected item
		 */
		@FXML
		public BorrowedThing getSelectedBorrowedThing() {
			return borrowedReturnedTable.getSelectionModel().getSelectedItem();
		}
		
		/**
		 * Method for deleting the selected borrowed thing from the database
		 */
		@FXML
		public void deleteSelectedReturnedThing() {
			if (inputCheck.confirmDeleting()) {
				controller.removeReturnedThing();
				initialize();
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
		 * Method that marks an event as returned
		 */
		@FXML
		public void markAsReturned() {
			controller.markReturned();
			initialize();
		}
		
		/** 
		 * Method that changes an item's status from returned to borrowed again
		 */
		@FXML
		public void makeReturnedBorrowed() {
			controller.changeReturnedToBorrowed();
			initialize();
		}
		
}
