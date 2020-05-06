package view.borrowed;


import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import controller.InputCheck;
import controller.BorrowedController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.TextInputDialog;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import model.Person;
import model.PersonDAO;
import res.MyBundle;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

/**
 * GUI class in charge of the view used for adding borrowed items
 */
public class BorrowedAddGUI {
	
	/**
	 * MyBundle object for setting the right resource bundle to localize the application
	 */
	MyBundle myBundle = new MyBundle();

	
	/**
	 * PersonDAO used for accessing the database
	 */
	PersonDAO personDAO = new PersonDAO();
	
	/**
	 * Text field for the name of the borrowed thing to be added
	 */
	@FXML
	TextField borrowedThing;
	
	/**
	 * Date picker for the date when the item is loaned
	 */
	@FXML
	DatePicker loanDate;
	
	/**
	 * Date picker for the date when the item should be returned
	 */
	@FXML
	DatePicker returnDate;
	
	/**
	 * ComboBox for selecting the person who has borrowed the item
	 */
	@FXML
	ComboBox<String> bbc;
	
	/**
	 * The anchor pane for the add view
	 */
	@FXML
	AnchorPane borrowedaddanchorpane;
	
	/**
	 * Borrowed Controller created
	 */
	BorrowedController controller = new BorrowedController(this);
	
	/**
	 * InputCheck object created to check the user's input
	 */
	InputCheck inputCheck = new InputCheck();
	
	/**
	 * Initialize-method called when the class is created
	 * Fetches the list of people in the database to whom items can be loaned 
	 */
	@FXML
	public void initialize() {
			//setting up the combobox including all contacts
			bbc.getItems().addAll(controller.personsList());
			bbc.getItems().add("");
			bbc.setCellFactory(lv -> {
				//custom list cell for choosing a contact
	            ListCell<String> cell = new ListCell<String>() {
	                @Override
	                protected void updateItem(String item, boolean empty) {
	                    super.updateItem(item, empty);
	                    if (empty) {
	                        setText(null);
	                    } else {
	                        if (item.isEmpty()) {
	                            setText(myBundle.getBundle().getString("personAdd"));
	                        } else {
	                            setText(item);
	                        }
	                    }
	                }
	            };
	            //creating a new contact if the right one isn't on the list
	            cell.addEventFilter(MouseEvent.MOUSE_PRESSED, evt -> {
	                if (cell.getItem().isEmpty() && ! cell.isEmpty()) {
	                    TextInputDialog dialog = new TextInputDialog();
	                    dialog.setContentText(myBundle.getBundle().getString("personAddName"));
	                    dialog.showAndWait().ifPresent(text -> {
	                    	personDAO.createPerson(new Person(text));
	                        int index = bbc.getItems().size()-1;
	                        bbc.getItems().add(index, text);
	                        bbc.getSelectionModel().select(index);
	                    });
	                    evt.consume();
	                }
	            });
	            return cell ;
	        });
	}

	/**
	 * Method for getting the value of the borrowedThing description text field
	 * @return this.borrowedThing.getText() the description of the borrowedThing
	 */
	@FXML
	public String getBorrowedDescription() {
		return this.borrowedThing.getText();
	}
	
	/**
	 * Method for getting the borrower from the combo box
	 * @return this.bbc.getValue() the borrower
	 */
	@FXML
	public String getBorrowedPerson() {
		return this.bbc.getValue();
	}
	
	/**
	 * Method for getting the date when the item has been borrowed
	 * @return Date.valueOf(this.loanDate.getValue()) the date when the item has been borrowed
	 */
	@FXML
	public Date getBorrowedLoanDate() {
		if (this.loanDate.getValue() == null) {
			//if no loan date chosen, the current date is the loan date
			return Date.valueOf(LocalDate.now());
		} else {
			return Date.valueOf(this.loanDate.getValue());
		}
	}
	
	/**
	 * Method for getting the date when the item will be returned
	 * @return Date.valueOf(this.returnDate.getValue()) the date when the item will be returned
	 */
	@FXML
	public Date getBorrowedReturnDate() {
		try {
			return Date.valueOf(this.returnDate.getValue());
		} catch(NullPointerException e) {
			return null;
		}
	}
	
	/**
	 * Method for adding a new borrowed thing to the database
	 * If the user inputs are not valid, displays an error message to the user
	 * After the new item is created, returns to the main borrowed items view
	 */
	@FXML
	public void addBorrowed() {
		//checking that neither the description nor the borrower nor the return date are empty
		//also checking if loan date is before return date
		if (!inputCheck.isInputEmpty(borrowedThing.getText()) && (!inputCheck.isInputEmpty(this.bbc.getValue())) && (!inputCheck.isDateEmpty(this.returnDate.getValue()))) {
			if(inputCheck.dateCheck(getBorrowedLoanDate(), getBorrowedReturnDate())) {
				//saving the new borrowed thing
				controller.saveBorrowedThing();
				//going back to the view of all borrowed items as adding is completed
				AnchorPane borrowedviewanchorpane = null; 
				FXMLLoader loaderBorrowedView  = new FXMLLoader(getClass().getResource("/view/borrowed/BorrowedView.fxml")); 
				loaderBorrowedView.setResources(myBundle.getBundle());
				try {
					borrowedviewanchorpane = loaderBorrowedView.load();
				} catch (IOException e) {
					e.printStackTrace();
				}
				borrowedaddanchorpane.getChildren().setAll(borrowedviewanchorpane);
			} else {
				inputCheck.alertDatesWrong();
			} 
		} else {
			inputCheck.alertInputEmpty();
		}
	}
	
	/**
	 * Method for canceling the adding of a new borrowed thing
	 * Exits the add view and returns to the main view
	 */
	@FXML
	public void cancelAddBorrowed() {
		AnchorPane borrowedviewanchorpane = null; 
		FXMLLoader loaderBorrowedView  = new FXMLLoader(getClass().getResource("/view/borrowed/BorrowedView.fxml")); 
		loaderBorrowedView.setResources(myBundle.getBundle());
		try {
			borrowedviewanchorpane = loaderBorrowedView.load();
		} catch (IOException e) {
			e.printStackTrace();
		}
		borrowedaddanchorpane.getChildren().setAll(borrowedviewanchorpane);
	}
	
}
