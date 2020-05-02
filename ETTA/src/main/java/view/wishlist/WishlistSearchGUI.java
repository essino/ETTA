package view.wishlist;

import java.sql.Date;

import org.controlsfx.control.textfield.TextFields;

import controller.InputCheck;
import controller.WishlistController;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.util.Callback;
import model.Item;
import model.Person;
import res.MyBundle;

public class WishlistSearchGUI {
	
	/**
	 * MyBundle object for setting the right resource bundle to localize the application
	 */
	MyBundle myBundle = new MyBundle();
	/**
	 * Reference to the used WishlistController
	 */
	WishlistController controller = new WishlistController();
	
	/**
	 * The anchor pane view for wishlist search view
	 */
	@FXML
	AnchorPane wishlistsearchanchorpane;
	
	/**
	 * Table view for showing the wishlist items
	 */
	@FXML
	TableView<Item> wishlistSearchTable;
	
	/**
	 * Table view column for item name
	 */
	@FXML
	TableColumn<Item, String> item;

	/**
	 * Table view column for person name
	 */
	@FXML
	TableColumn<Item, String> person;

	/**
	 * Table view column for item price
	 */
	@FXML
	TableColumn<Item, Double> price;

	/**
	 * Table view column for item date
	 */
	@FXML
	TableColumn<Item, Date> date;

	/**
	 * Table view column for item additional information
	 */
	@FXML
	TableColumn<Item, String> addinfo;

	/**
	 * Table view column for bought boolean
	 */
	@FXML
	TableColumn<Item, String> bought;
	
	/**
	 * TextField for searching
	 */
	@FXML
	private TextField input;
	
	/**
	 * The input check class used for validating user input and handling alerts
	 */
	InputCheck inputCheck = new InputCheck();

	/**
	 * Initialize-method called when the class is created
	 * Builds the table view
	 * Fetches the needed wishlist items from the database and displays them in the table view
	 */
	@FXML
	public void initialize() {
		wishlistSearchTable.setPlaceholder(new Text(myBundle.getBundle().getString("wishlistEmpty")));
		item.setCellValueFactory(new PropertyValueFactory<Item, String>("description"));
		person.setCellValueFactory(new PropertyValueFactory<Item, String>("person"));
		bought.setCellValueFactory(new Callback<CellDataFeatures<Item, String>, ObservableValue<String>>() {
			public ObservableValue<String> call(CellDataFeatures<Item, String> item) {
				if (item.getValue().isBought() == true) {
					return new ReadOnlyObjectWrapper<>(myBundle.getBundle().getString("yesYes"));
				} else {
					return new ReadOnlyObjectWrapper<>(myBundle.getBundle().getString("noNo"));
				}

			}
		});
		price.setCellValueFactory(new PropertyValueFactory<Item, Double>("price"));
		date.setCellValueFactory(new PropertyValueFactory<Item, Date>("dateNeeded"));
		addinfo.setCellValueFactory(new PropertyValueFactory<Item, String>("additionalInfo"));
		
		Person[] people = controller.getPeople();
		String[] names = new String[people.length];
		for (int i = 0; i < people.length; i++) {
			names[i] = people[i].getName();
		}
		TextFields.bindAutoCompletion(input, people);
		ObservableList<Item> data = FXCollections.observableArrayList(controller.getItems());
		wishlistSearchTable.setItems(data);
	}
	
	/**
	 * Filters the list of items by the name of a person inputted by the user
	 * @param event the ActionEvent that is handled
	 */
	public void searchItemByPerson(ActionEvent event) {
		String value = input.getText();
		ObservableList<Item> data = FXCollections.observableArrayList(controller.getItems());
		FilteredList<Item> filteredData = new FilteredList<>(data,
	            s -> ((s.getPerson().getName())).equals(value));
		wishlistSearchTable.setItems(filteredData);
	}
}
