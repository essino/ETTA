package view;

import java.io.IOException;
import java.sql.Date;

import controller.WishlistController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import model.Item;
import model.Person;

/**
 * GUI class relating to the Wish section
 */
public class WishlistGUI {
	
	/**
	 * The menu view to which the alternative views in the Wish section are added
	 */
	@FXML
	BorderPane wishmainpane;
	
	WishlistController controller;
	
	/**
	 * The list view from where adding, editing and deleting can be started
	 */
	@FXML
	AnchorPane wishlistanchorpane;
	
	@FXML
	TableView<Item> wishlisttable;
	@FXML
	TableColumn<Item, String> item;
	@FXML
	TableColumn<Item, Person> person;
	@FXML
	TableColumn<Item, Double> price;
	@FXML
	TableColumn<Item, Date> date;
	@FXML
	TableColumn<Item, String> addinfo;
	@FXML
	TableColumn<Item, Boolean> bought;
	
	public WishlistGUI() {
		controller = new WishlistController(this);
	}
	
	/**
	 * Method showing the Wishlist view in the Wish section
	 * @param event ActionEvent that is handled
	 */
	@FXML
	public void showAll(ActionEvent event) {
		AnchorPane allView = null;
		FXMLLoader loaderWishlistAll  = new FXMLLoader(getClass().getResource("/view/WishlistView.fxml"));
		try {
			allView = loaderWishlistAll.load();
			} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			}
		wishmainpane.setCenter(allView);
	}
	
	/**
	 * Method showing the view of add to wishlist
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
	
	/*
	@FXML
	public void initialize() {
		item.setCellValueFactory(new PropertyValueFactory<Item, String>("description"));
		person.setCellValueFactory(new PropertyValueFactory<Item, Person>("person"));
		price.setCellValueFactory(new PropertyValueFactory<Item, Double>("price"));
		date.setCellValueFactory(new PropertyValueFactory<Item, Date>("dateNeeded"));
		addinfo.setCellValueFactory(new PropertyValueFactory<Item, String>("additionalInfo"));
		bought.setCellValueFactory(new PropertyValueFactory<Item, Boolean>("bought"));
		
		final ObservableList<Item> data = FXCollections.observableArrayList(controller.getItems());
		wishlisttable.setItems(data);
	}
	*/
}
