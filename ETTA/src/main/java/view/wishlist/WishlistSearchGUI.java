package view.wishlist;

import java.sql.Date;
import java.text.DateFormat;
import java.util.Locale;

import org.controlsfx.control.textfield.TextFields;

import controller.WishlistController;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableCell;
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

/**
 * GUI class relating to the wishlist search section
 */
public class WishlistSearchGUI {
	
	/**
	 * MyBundle object for setting the right resource bundle to localize the application
	 */
	private MyBundle myBundle = new MyBundle();
	
	/**
	 * Reference to the used WishlistController
	 */
	private WishlistController controller = new WishlistController();
	
	/**
	 * The anchor pane view for wishlist search view
	 */
	@FXML
	private AnchorPane wishlistsearchanchorpane;
	
	/**
	 * Table view for showing the wishlist items
	 */
	@FXML
	private TableView<Item> wishlistSearchTable;
	
	/**
	 * Table view column for item name
	 */
	@FXML
	private TableColumn<Item, String> item;

	/**
	 * Table view column for person name
	 */
	@FXML
	private TableColumn<Item, String> person;

	/**
	 * Table view column for item price
	 */
	@FXML
	private TableColumn<Item, Double> price;

	/**
	 * Table view column for item date
	 */
	@FXML
	private TableColumn<Item, Date> date;

	/**
	 * Table view column for item additional information
	 */
	@FXML
	private TableColumn<Item, String> addinfo;

	/**
	 * Table view column for bought boolean
	 */
	@FXML
	private TableColumn<Item, String> bought;
	
	/**
	 * TextField for searching
	 */
	@FXML
	private TextField input;

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
		date.setCellFactory(column -> {
	        TableCell<Item, Date> cell = new TableCell<Item, Date>() {
	            @Override
	            protected void updateItem(Date item, boolean empty) {
	            	Locale locale = Locale.getDefault();
		    	    DateFormat df = DateFormat.getDateInstance(DateFormat.SHORT, locale);
	                super.updateItem(item, empty);
	                if(item == null) {
	                    setText(null);
	                }
	                else {
	                	this.setText(df.format(item));
	                }
	            }
	        };
	        return cell;
	    });
		addinfo.setCellValueFactory(new PropertyValueFactory<Item, String>("additionalInfo"));
		
		//get the list of people names from the database
		Person[] people = controller.getPeople();
		String[] names = new String[people.length];
		for (int i = 0; i < people.length; i++) {
			names[i] = people[i].getName();
		}
		//bind the list of people names to the text field autocomplete
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
		//populates the filtered list with only the items whose person's name matches the user input in the text field
		FilteredList<Item> filteredData = new FilteredList<>(data,
	           s -> (s.getPerson() != null && s.getPerson().getName().equals(value)));
		wishlistSearchTable.setItems(filteredData);
	}
	
	
}
