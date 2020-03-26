package view.wishlist;

import java.io.IOException;
import java.sql.Date;
import java.time.Instant;
import java.time.ZoneId;

import controller.InputCheck;
import controller.WishlistController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListCell;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import model.Item;
import model.Person;
import model.PersonDAO;

public class WishlistEditGUI {
	
	/**
	 * Text field for the name of the item to be added
	 */
	@FXML
	TextField item;
	
	/**
	 * ComboBox for selecting the person who the item to be added is for
	 */
	@FXML
	ComboBox<String> toWhom;
	
	/**
	 * Text field for the price of the item to be added
	 */
	@FXML
	TextField price;
	
	/**
	 * Date picker for the date the item to be added is needed
	 */
	@FXML
	DatePicker date;
	
	/**
	 * Text field for additional information about the item to be added
	 */
	@FXML
	TextField additional;
	
	/**
	 * The anchor pane for the add view
	 */
	@FXML
	AnchorPane wishlisteditpane;
	
	/**
	 * Reference to the used WishlistController
	 */
	WishlistController controller = new WishlistController(this);
	
	/**
	 * The input check class used for validating user input
	 */
	InputCheck inputCheck = new InputCheck();
	
	/**
	 * PersonDAO used for accessing the database
	 */
	PersonDAO personDAO = new PersonDAO();
	
	public WishlistEditGUI() {
		
	}
	
	/**
	 * Method for getting the value of the item text field
	 * @return the description of the item
	 */
	@FXML
	public String getItemDesc() {
		return this.item.getText();
	}
	
	/**
	 * Method for getting the person who the item is for from the combo box
	 * @return the person who the item is for
	 */
	@FXML
	public String getItemPerson() {
		return this.toWhom.getValue();
	}
	
	/**
	 * Method for getting the value of the price text field
	 * @return the price of the item
	 */
	@FXML
	public String getItemPrice() {
		return this.price.getText();
	}
	
	/**
	 * Method for getting the date when the item is needed from the date picker
	 * @return the date when the item is needed
	 */
	@FXML
	public Date getItemDate() {
		try {
			return Date.valueOf(this.date.getValue());
		} catch (NullPointerException e) {
			return null;
		}
	}
	
	/**
	 * Method for getting the value of the addition info text field
	 * @return additional information about the item
	 */
	@FXML
	public String getItemAdditional() {
		return this.additional.getText();
	}

	/**
	 * Initialize-method called when the class is created
	 * Fetches the list of people in the database to whom items can be given
	 * Also possible to add a new Person in the drop down list in ComboBox - PersonDAO creates a new Person with the given name
	 */
	@FXML
	public void initialize() {
		System.out.println("tultiin edit init 132");
		toWhom.getItems().addAll(controller.personsList());
		toWhom.getItems().add("");
		toWhom.setCellFactory(lv -> {
			ListCell<String> cell = new ListCell<String>() {
				@Override
	            protected void updateItem(String item, boolean empty) {
					super.updateItem(item, empty);
					if (empty) {
	                	setText(null);
	                } else {
	                	if (item.isEmpty()) {
	                		setText("Add person...");
	                	} else {
	                		setText(item);
	                	}
	                }
	            }
	       };
	       cell.addEventFilter(MouseEvent.MOUSE_PRESSED, evt -> {
	    	   if (cell.getItem().isEmpty() && ! cell.isEmpty()) {
	    		   TextInputDialog dialog = new TextInputDialog();
	    		   dialog.setContentText("Enter name");
	    		   dialog.showAndWait().ifPresent(text -> {
	                    personDAO.createPerson(new Person(text));
	                    int index = toWhom.getItems().size()-1;
	                    toWhom.getItems().add(index, text);
	                    toWhom.getSelectionModel().select(index);
	    		   });
	    		   evt.consume(); 
	           }
	       });
	       return cell ;
		});
		System.out.println("tultiin edit init 166");
		Item itemToBeUpdated = controller.getItem();
		item.setText(itemToBeUpdated.getDescription());
		toWhom.getSelectionModel().select(1);
		price.setText(Double.toString(itemToBeUpdated.getPrice()));
		date.setValue(itemToBeUpdated.getDateNeeded().toLocalDate());
		additional.setText(itemToBeUpdated.getAdditionalInfo());
	}
	
	/**
	 * Method for canceling the editing of the item
	 * Exits the edit view and returns to the main view
	 */
	@FXML
	public void cancelEdit() {
		AnchorPane wishlistView = null; 
		FXMLLoader loaderWishlistView  = new FXMLLoader(getClass().getResource("/view/wishlist/WishlistView.fxml")); 
		try {
			wishlistView = loaderWishlistView.load();
		} catch (IOException e) {
			e.printStackTrace();
		}
		wishlisteditpane.getChildren().setAll(wishlistView);
	}
	
}
