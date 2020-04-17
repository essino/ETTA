package view.wishlist;

import java.io.IOException;
import java.sql.Date;

import controller.InputCheck;
import controller.WishlistController;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import javafx.util.Callback;
import javafx.util.converter.DoubleStringConverter;
import model.Item;
import model.Person;
import res.MyBundle;


public class WishlistTableGUI {
	
	MyBundle myBundle = new MyBundle();
	
	/**
	 * Reference to the used WishlistController
	 */
	WishlistController controller = new WishlistController(this);
	
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
	TableColumn<Item, String> bought;
	
	/**
	 * The input check class used for validating user input
	 */
	InputCheck inputCheck = new InputCheck();
	
	/**
	 * Reference to the list of data displayed in the table view
	 */
	ObservableList<Item> data = FXCollections.observableArrayList();
	
	Callback<TableColumn<Item, Date>, TableCell<Item, Date>> dateCellFactory = (TableColumn<Item, Date> param) -> new WishlistDateEditingCell();
	
	/**
	 * Constructor
	 */
	public WishlistTableGUI() {
		
	}

	/**
	 * Initialize-method called when the class is created
	 * Fetches the wishlist items from the database, displays them in the table view, and enables in-table editing
	 */
	@FXML
	public void initialize() {
		wishlisttable.setEditable(true);
		item.setCellValueFactory(new PropertyValueFactory<Item, String>("description"));
		item.setCellFactory(TextFieldTableCell.<Item>forTableColumn());
		item.setOnEditCommit(
				new EventHandler<CellEditEvent<Item, String>>() {
					@Override
					public void handle(CellEditEvent<Item, String> t) {
						Item editedItem = ((Item) t.getTableView().getItems().get(t.getTablePosition().getRow()));
						editedItem.setDescription(t.getNewValue());
						controller.updateItem(editedItem);
						wishlisttable.refresh();
					}});

		person.setCellValueFactory(new PropertyValueFactory<Item, String>("person"));
		person.setCellFactory(ComboBoxTableCell.<Item, String>forTableColumn(controller.personsList()));
		person.setOnEditCommit(
				new EventHandler<CellEditEvent<Item, String>>() {
					@Override
					public void handle(CellEditEvent<Item, String> t) {
						
						Item editedItem = ((Item) t.getTableView().getItems().get(t.getTablePosition().getRow()));
						String newName = t.getNewValue();
						Person newPerson = controller.findPerson(newName);
						editedItem.setPerson(newPerson);
						controller.updateItem(editedItem);
						wishlisttable.refresh();
					}});
					
		bought.setCellValueFactory(new Callback<CellDataFeatures<Item, String>, ObservableValue<String>>() {
			public ObservableValue<String> call(CellDataFeatures<Item, String> item) {
				if (item.getValue().isBought() == true) {
					return new ReadOnlyObjectWrapper<>("Yes");
				} else {
					return new ReadOnlyObjectWrapper<>("No");
				}
					
			}
		});
		
		price.setCellValueFactory(new PropertyValueFactory<Item, Double>("price"));
		price.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
		price.setOnEditCommit(
				new EventHandler<CellEditEvent<Item, Double>>() {
					@Override
					public void handle(CellEditEvent<Item, Double> t) {
						Item editedItem = ((Item) t.getTableView().getItems().get(t.getTablePosition().getRow()));
						editedItem.setPrice(t.getNewValue());
						controller.updateItem(editedItem);
						wishlisttable.refresh();
					}});
		
		date.setCellValueFactory(new PropertyValueFactory<Item, Date>("dateNeeded"));
		date.setCellFactory(dateCellFactory);
		date.setOnEditCommit(
				(TableColumn.CellEditEvent<Item, Date> t) -> {
				Item editedItem = ((Item) t.getTableView().getItems()
	            .get(t.getTablePosition().getRow()));
				editedItem.setDateNeeded(t.getNewValue());
				controller.updateItem(editedItem);
				wishlisttable.refresh();
				}	
			);	
		
		addinfo.setCellValueFactory(new PropertyValueFactory<Item, String>("additionalInfo"));
		addinfo.setCellFactory(TextFieldTableCell.<Item>forTableColumn());
		addinfo.setOnEditCommit(
				new EventHandler<CellEditEvent<Item, String>>() {
					@Override
					public void handle(CellEditEvent<Item, String> t) {
						Item editedItem = ((Item) t.getTableView().getItems().get(t.getTablePosition().getRow()));
						editedItem.setAdditionalInfo(t.getNewValue());
						controller.updateItem(editedItem);
						wishlisttable.refresh();
					}});
		
		data = FXCollections.observableArrayList(controller.getItems());
		wishlisttable.setItems(data);
		
	}
	
	/**
	 * Method for showing the view of add to wishlist
	 * @param event ActionEvent that is handled
	 */
	@FXML
	public void showAddWish(ActionEvent event) {
		AnchorPane showAddWishView = null; 
		FXMLLoader loaderAddWishView  = new FXMLLoader(getClass().getResource("/view/wishlist/WishlistAdd.fxml")); 
		loaderAddWishView.setResources(myBundle.getBundle());
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
		System.out.println("selected in tableGUI " + wishlisttable.getSelectionModel().getSelectedItem());
		return wishlisttable.getSelectionModel().getSelectedItem();
	}
	
	/**
	 * Method for deleting the selected item from the database
	 */
	@FXML
	public void deleteItem() {
		if (inputCheck.confirmDeleting()) {
			controller.removeItem();
			initialize();
		}
	}
	
	/** 
	 * Method for marking an item as bought
	 */
	@FXML
	public void markAsBought() {
		controller.setBought();
		initialize();
	}
	
}
