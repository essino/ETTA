package view.wishlist;

import java.io.IOException;
import java.sql.Date;

import controller.CalendarController;
import controller.WishlistController;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ObservableValue;
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

/**
 * Abstract super class for all the different wishlist table GUIs
 * @author Tiina
 *
 */
public abstract class AbstractWishlistGUI {
	
	/**
	 * The used resource bundle
	 */
	//MyBundle myBundle = new MyBundle();
		MyBundle myBundle =MyBundle.getInstance();
	
	/**
	 * Reference to the used WishlistController
	 */
	WishlistController controller = new WishlistController();
	
	/**
	 * Reference to the used CalendarController
	 */
	CalendarController calendarController = new CalendarController();
	
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
	 * The dateCellFactory for editing dates in the table
	 */
	Callback<TableColumn<Item, Date>, TableCell<Item, Date>> dateCellFactory = (TableColumn<Item, Date> param) -> new WishlistDateEditingCell();
	
	/**
	 * Builds the wishlist table and makes it editable
	 */
	public void initializeTable() {
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
						controller.updateItem(editedItem);
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
						controller.updateItem(editedItem);
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
						controller.updateItem(editedItem);
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
				controller.updateItem(editedItem);
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
						controller.updateItem(editedItem);
						wishlisttable.refresh();
					}});
	}
	
	/**
	 * Method for loading the view of add to wishlist
	 * @param event ActionEvent that is handled
	 */
	public AnchorPane loadAddWish() {
		AnchorPane showAddWishView = null; 
		FXMLLoader loaderAddWishView  = new FXMLLoader(getClass().getResource("/view/wishlist/WishlistAdd.fxml")); 
		loaderAddWishView.setResources(myBundle.getBundle());
		try {
			showAddWishView = loaderAddWishView.load();
		} catch (IOException e) {
			return null;
		}
		return showAddWishView;
	}
	
	/**
	 * Method for getting the selected item from the table
	 * @return the selected item
	 */
	public Item getSelectedItem() {
		return wishlisttable.getSelectionModel().getSelectedItem();
	}
	
	/**
	 * Method for checking that an item is currently selected in the table
	 */
	public boolean checkItemIsSelected() {
		Item item = getSelectedItem();
		return item != null;
	}
	
	/**
	 * Abstract method for deleting an item from the table
	 */
	public abstract void deleteItem();

}
