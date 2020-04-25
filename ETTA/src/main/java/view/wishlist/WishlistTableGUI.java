package view.wishlist;

import java.io.IOException;
import java.sql.Date;

import controller.CalendarController;
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
import javafx.scene.text.Text;
import javafx.util.Callback;
import javafx.util.converter.DoubleStringConverter;
import model.Item;
import model.Person;
import res.MyBundle;


public class WishlistTableGUI extends AbstractWishlistGUI {
	
	MyBundle myBundle = new MyBundle();
	
	/**
	 * Reference to the used WishlistController
	 */
	WishlistController controller = new WishlistController(this);
	
	CalendarController calendarController = new CalendarController();
	
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
	 * Initialize-method called when the class is created
	 * Fetches the wishlist items from the database, displays them in the table view, and enables in-table editing
	 */
	@FXML
	public void initialize() {
		wishlisttable.setEditable(true);
		wishlisttable.setPlaceholder(new Text(myBundle.getBundle().getString("wishlistEmpty")));
		item.setCellValueFactory(new PropertyValueFactory<Item, String>("description"));
		item.setCellFactory(TextFieldTableCell.<Item>forTableColumn());
		item.setOnEditCommit(
				new EventHandler<CellEditEvent<Item, String>>() {
					@Override
					public void handle(CellEditEvent<Item, String> t) {
						Item editedItem = ((Item) t.getTableView().getItems().get(t.getTablePosition().getRow()));
						String oldDescription = editedItem.getDescription();
						editedItem.setDescription(t.getNewValue());
						controller.updateItem(editedItem, WishlistTableGUI.this);
						calendarController.updateWishlistDescription(oldDescription, editedItem);
						wishlisttable.refresh();
					}});

		person.setCellValueFactory(new PropertyValueFactory<Item, String>("person"));
		person.setCellFactory(ComboBoxTableCell.<Item, String>forTableColumn(controller.personsList()));
		person.setOnEditCommit(
				new EventHandler<CellEditEvent<Item, String>>() {
					@Override
					public void handle(CellEditEvent<Item, String> t) {
						Item editedItem = ((Item) t.getTableView().getItems().get(t.getTablePosition().getRow()));
						String oldName = editedItem.getPerson().getName();
						String newName = t.getNewValue();
						Person newPerson = controller.findPerson(newName);
						editedItem.setPerson(newPerson);
						controller.updateItem(editedItem, WishlistTableGUI.this);
						calendarController.updateWishlistPerson(oldName, editedItem);
						wishlisttable.refresh();
					}});
					
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
		price.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
		price.setOnEditCommit(
				new EventHandler<CellEditEvent<Item, Double>>() {
					@Override
					public void handle(CellEditEvent<Item, Double> t) {
						Item editedItem = ((Item) t.getTableView().getItems().get(t.getTablePosition().getRow()));
						editedItem.setPrice(t.getNewValue());
						controller.updateItem(editedItem, WishlistTableGUI.this);
						wishlisttable.refresh();
					}});
		
		date.setCellValueFactory(new PropertyValueFactory<Item, Date>("dateNeeded"));
		date.setCellFactory(dateCellFactory);
		date.setOnEditCommit(
				(TableColumn.CellEditEvent<Item, Date> t) -> {
				Item editedItem = ((Item) t.getTableView().getItems()
	            .get(t.getTablePosition().getRow()));
				Date oldDate = editedItem.getDateNeeded();
				editedItem.setDateNeeded(t.getNewValue());
				controller.updateItem(editedItem, WishlistTableGUI.this);
				calendarController.updateWishlistDate(oldDate, editedItem);
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
						controller.updateItem(editedItem, WishlistTableGUI.this);
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
		AnchorPane showAddWishView = super.loadAddWish();
		if (showAddWishView != null) {
			wishlistanchorpane.getChildren().setAll(showAddWishView);
		} else {
			System.out.println("Error loading WishlistAdd.fxml");
		}
	}
	
	/**
	 * Method for getting the selected item from the table
	 * @return the selected item
	 */
	@FXML
	public Item getSelectedItem() {
		return super.getSelectedItem();
	}
	
	/**
	 * Method for deleting the selected item from the database
	 */
	@FXML
	public void deleteItem() {
		if (inputCheck.confirmDeleting()) {
			controller.removeItem(this);
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
