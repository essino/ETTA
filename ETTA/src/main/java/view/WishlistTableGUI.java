package view;

import java.io.IOException;
import java.sql.Date;

import com.sun.xml.bind.v2.schemagen.episode.Bindings;

import controller.WishlistController;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.util.Callback;
import model.Item;
import model.Transfer;

/**
 * GUI class for the wishlist page
 */
public class WishlistTableGUI {
	
	/**
	 * Reference to the used WishlistController
	 */
	WishlistController controller;
	
	/**
	 * The anchor pane view from where adding, editing and deleting can be started
	 */
	@FXML
	AnchorPane wishlistanchorpane;
	
	/**
	 * Table view for showing the wishlist items
	 */
	@FXML
	TableView<Item> wishlisttable;
	
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
	TableColumn<Item, Boolean> bought;
	
	/**
	 * Constructor responsible for creating the wishlist controller
	 */
	public WishlistTableGUI() {
		controller = new WishlistController(this);
	}

	/**
	 * Initialize-method called when the class is created
	 * Fetches the wishlist items from the database and shows them in the table view
	 */
	@FXML
	public void initialize() {
		item.setCellValueFactory(new PropertyValueFactory<Item, String>("description"));
		
		person.setCellValueFactory(new Callback<CellDataFeatures<Item, String>, ObservableValue<String>>() {
			public ObservableValue<String> call(CellDataFeatures<Item, String> item) {
		         // item.getValue() returns the Data instance for a particular TableView row
				if (item.getValue().getPerson() != null) {
					return new ReadOnlyObjectWrapper(item.getValue().getPerson().getName());
				} else {
					ObservableValue<String> me = new ReadOnlyObjectWrapper<>("Me");
					return me;
				}
		         
			}
		});
		
		price.setCellValueFactory(new PropertyValueFactory<Item, Double>("price"));
		date.setCellValueFactory(new PropertyValueFactory<Item, Date>("dateNeeded"));
		addinfo.setCellValueFactory(new PropertyValueFactory<Item, String>("additionalInfo"));
		
		bought.setCellValueFactory(new PropertyValueFactory<Item, Boolean>("bought"));
		/*bought.setCellValueFactory (new Callback<CellDataFeatures<Item, Boolean>, ObservableValue<Boolean>>() {
			@Override public 
			ObservableValue<Boolean> call(CellDataFeatures<Item, Boolean> item) {
				//BooleanBinding bgth = Bindings.createBooleanBinding(() -> item.getValue().isBought(), bought);
				//return new ReadOnlyObjectWrapper(item.getValue().getBought()); 
				return item.getValue().getBought();
			}
		});
		*/
		/*bought.setCellFactory (new Callback<TableColumn<Item, Boolean>,TableCell<Item, Boolean>>(){
			@Override public
			TableCell<Item, Boolean> call(TableColumn<Item, Boolean> bought) {
				return new CheckBoxTableCell<>(); 
			}
		});	
		*/
		
		final ObservableList<Item> data = FXCollections.observableArrayList(controller.getItems());
		wishlisttable.setItems(data);
	}
	
	/**
	 * Method for showing the view of add to wishlist
	 * @param event ActionEvent that is handled
	 */
	@FXML
	public void showAddWish(ActionEvent event) {
		AnchorPane showAddWishView = null; 
		FXMLLoader loaderAddWishView  = new FXMLLoader(getClass().getResource("/view/WishlistAdd.fxml")); 
		try {
			showAddWishView = loaderAddWishView.load();
			} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			}
		wishlistanchorpane.getChildren().setAll(showAddWishView);
	}
	
	/**
	 * Method for getting the selected item from the table
	 * @return the selected item
	 */
	@FXML
	public Item getSelectedItem() {
		return wishlisttable.getSelectionModel().getSelectedItem();
	}
	
	/**
	 * Method for deleting the selected item from the database
	 */
	@FXML
	public void deleteItem() {
		controller.removeItem();
	}
	
	/** 
	 * Method that removes an item from the table
	 * @param item the item to be removed
	 */
	@FXML
	public void removeFromTable(Item item) {
		wishlisttable.getItems().remove(item);
	}
	
	@FXML
	public void markAsBought() {
		controller.setBought();
		wishlisttable.refresh();
	}
	
}
