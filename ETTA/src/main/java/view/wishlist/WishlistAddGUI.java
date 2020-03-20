package view.wishlist;


import java.io.IOException;
import java.sql.Date;

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
import model.Person;
import model.PersonDAO;

/**
 * GUI class relating to the wishlist add section
 */
public class WishlistAddGUI {
	
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
	AnchorPane wishlistaddpane;
	
	/**
	 * Reference to the used WishlistController
	 */
	WishlistController controller; //= new WishlistController(this);
	
	/**
	 * The input check class used for validating user input
	 */
	InputCheck inputCheck = new InputCheck();
	/**
	 * PersonDAO used for accessing the database
	 */
	PersonDAO personDAO = new PersonDAO();
	
	public WishlistAddGUI(WishlistController controller) {
		this.controller = controller;
	}
	
	/**
	 * Initialize-method called when the class is created
	 * Fetches the list of people in the database to whom items can be given
	 * Also possible to add a new Person in the drop down list in ComboBox - PersonDAO creates a new Person with the given name
	 */
	@FXML
	public void initialize() {
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
	 * Method for adding a new item to the database
	 * Checks if the user inputs are valid, then calls the controller to create the new item
	 * If the user inputs are not valid, displays an error message to the user
	 * After the new item is created, returns to the main wishlist view
	 */
	@FXML
	public void addNewItem() {
		if (inputCheck.isInputFloat(price.getText()) || inputCheck.isInputEmpty(price.getText())) {
			if (!inputCheck.isInputEmpty(item.getText())) {
				System.out.println("Desc " + item.getText());
				controller.saveItem();
				AnchorPane wishlistView = null; 
				FXMLLoader loaderWishlistView  = new FXMLLoader(getClass().getResource("/view/wishlist/WishlistView.fxml")); 
				try {
					wishlistView = loaderWishlistView.load();
				} catch (IOException e) {
					e.printStackTrace();
				}
				wishlistaddpane.getChildren().setAll(wishlistView);
			} else {
				inputCheck.alertInputEmpty();
			}	
		} else {
			inputCheck.alertInputNotFloat();
		}
	}
	
	/**
	 * Method for canceling the adding of a new item
	 * Exits the add view and returns to the main view
	 */
	@FXML
	public void cancelAdd() {
		AnchorPane wishlistView = null; 
		FXMLLoader loaderWishlistView  = new FXMLLoader(getClass().getResource("/view/wishlist/WishlistView.fxml")); 
		try {
			wishlistView = loaderWishlistView.load();
		} catch (IOException e) {
			e.printStackTrace();
		}
		wishlistaddpane.getChildren().setAll(wishlistView);
	}
}
