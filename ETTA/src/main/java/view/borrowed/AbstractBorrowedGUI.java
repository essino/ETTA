package view.borrowed;

import java.sql.Date;
import java.text.DateFormat;
import java.util.Locale;
import controller.BorrowedController;
import controller.InputCheck;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import javafx.util.Callback;
import model.BorrowedThing;
import res.MyBundle; 

/**
 * Abstract class for BorrowedGUIs
 */
public abstract class AbstractBorrowedGUI {

	/**
	 * MyBundle object for setting the right resource bundle to localize the application
	 */
	MyBundle myBundle = new MyBundle();
	
	/**
	 * the controller for Borrowed things
	 */
	BorrowedController controller = new BorrowedController();
	
	/**
	 * The TableView for viewing all borrowed things
	 */
	@FXML
	TableView<BorrowedThing> borrowedTable;
	
	/**
	 * The TableColumn that shows the items' names
	 */
	@FXML
	TableColumn<BorrowedThing, String> borrowedThingDescr;
	
	/**
	 * The TableColumn that shows when the items have been loaned
	 */
	@FXML
	TableColumn<BorrowedThing, Date> loanDate;
	
	/**
	 * The TableColumn that shows when the items are supposed to be returned
	 */
	@FXML
	TableColumn<BorrowedThing, Date> returnDate;
	
	/**
	 * The TableColumn that shows who have borrowed the items
	 */
	@FXML
	TableColumn<BorrowedThing, String> borrowedBy;
	
	/**
	 * The TableColumn that shows if the item has been returned
	 */
	@FXML
	TableColumn<BorrowedThing, String> returned;

	/**
	 * The reference of InputCheck class used for checking user's input
	 */
	InputCheck inputCheck = new InputCheck(); 
	
	/**
	 * Initializes the table view for subclasses
	 */
	public void initializeTable() {
		MyBundle myBundle = new MyBundle();
		//for setting the right formatting for dates in table cells
		Locale locale = Locale.getDefault();
	    DateFormat df = DateFormat.getDateInstance(DateFormat.SHORT, locale);
	    //in case table is empty, a placeholder text is shown
	    borrowedTable.setPlaceholder(new Text(myBundle.getBundle().getString("wishlistEmpty")));
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
		//for setting the right value for returned or not
		returned.setCellValueFactory(new Callback<CellDataFeatures<BorrowedThing, String>, ObservableValue<String>>(){
			public ObservableValue<String> call(CellDataFeatures<BorrowedThing, String> borrowedThingDescr) {
				if (borrowedThingDescr.getValue().isReturned() == true) {
					return new ReadOnlyObjectWrapper<>(myBundle.getBundle().getString("yesYes")); 
				} else {
					return new ReadOnlyObjectWrapper<>(myBundle.getBundle().getString("noNo"));
				}
			}});
	}
	
	/**
	 * Method for getting the selected item from the table
	 * @return borrowedTable.getSelectionModel().getSelectedItem() the selected item
	 */
	@FXML
	public BorrowedThing getSelectedBorrowedThing() {
		return borrowedTable.getSelectionModel().getSelectedItem();
	}
	
	/**
	 * Method for checking that an item is currently selected in the table
	 * @return thing != null boolean indicating that the item is not null, and thus something is selected
	 */
	public boolean checkItemIsSelected() {
		BorrowedThing thing = getSelectedBorrowedThing();
		return thing != null;
	}
	
} 
	

