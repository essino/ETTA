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
 * 
 * @author Tiina
 *
 */
public abstract class AbstractWishlistGUI {

	/**
	 * MyBundle object for setting the right resource bundle to localize the application
	 */
	private MyBundle myBundle = new MyBundle();
	
	/**
	 * Reference to the used WishlistController
	 */
	private WishlistController controller = new WishlistController();

	/**
	 * Reference to the used CalendarController
	 */
	private CalendarController calendarController = new CalendarController();

	/**
	 * Table view for showing the wishlist items
	 */
	@FXML
	private TableView<Item> wishlisttable;

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
	 * The dateCellFactory for editing dates in the table
	 */
	Callback<TableColumn<Item, Date>, TableCell<Item, Date>> dateCellFactory = (
			TableColumn<Item, Date> param) -> new WishlistDateEditingCell();

	/**
	 * Method for building the wishlist table and making it editable
	 */
	public void initializeTable() {
		wishlisttable.setEditable(true);
		//nothing in the table
		wishlisttable.setPlaceholder(new Text(myBundle.getBundle().getString("wishlistEmpty")));
		
		//displaying and editing the description of the items
		item.setCellValueFactory(new PropertyValueFactory<Item, String>("description"));
		item.setCellFactory(TextFieldTableCell.<Item>forTableColumn());
		item.setOnEditCommit(new EventHandler<CellEditEvent<Item, String>>() {
			@Override
			public void handle(CellEditEvent<Item, String> t) {
				Item editedItem = ((Item) t.getTableView().getItems().get(t.getTablePosition().getRow()));
				String oldDescription = editedItem.getDescription();
				editedItem.setDescription(t.getNewValue());
				controller.updateItem(editedItem);
				//updating the event connected to the item
				calendarController.updateWishlistDescription(oldDescription, editedItem);
				wishlisttable.refresh();
			}
		});

		//displaying and editing the person name of the items
		person.setCellValueFactory(new PropertyValueFactory<Item, String>("person"));
		person.setCellFactory(ComboBoxTableCell.<Item, String>forTableColumn(controller.personsList()));
		person.setOnEditCommit(new EventHandler<CellEditEvent<Item, String>>() {
			@Override
			public void handle(CellEditEvent<Item, String> t) {
				Item editedItem = ((Item) t.getTableView().getItems().get(t.getTablePosition().getRow()));
				String newName = t.getNewValue();
				//if the item is for the person himself, person is null in the database
				if (newName == "Me" || newName == "Minä") {
					newName = null;
				}
				Person oldPerson = editedItem.getPerson();
				String oldName = null;
				if (oldPerson != null) {
					oldName = oldPerson.getName();
				}
				Person newPerson = controller.findPerson(newName);
				editedItem.setPerson(newPerson);
				controller.updateItem(editedItem);
				//updating the event connected to the item
				calendarController.updateWishlistPerson(oldName, editedItem);
				wishlisttable.refresh();
			}
		});

		//displaying and editing the state of  the items - are they bought or not
		bought.setCellValueFactory(new Callback<CellDataFeatures<Item, String>, ObservableValue<String>>() {
			public ObservableValue<String> call(CellDataFeatures<Item, String> item) {
				if (item.getValue().isBought() == true) {
					return new ReadOnlyObjectWrapper<>(myBundle.getBundle().getString("yesYes"));
				} else {
					return new ReadOnlyObjectWrapper<>(myBundle.getBundle().getString("noNo"));
				}

			}
		});

		//displaying and editing the price of the items
		price.setCellValueFactory(new PropertyValueFactory<Item, Double>("price"));
		price.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
		price.setOnEditCommit(new EventHandler<CellEditEvent<Item, Double>>() {
			@Override
			public void handle(CellEditEvent<Item, Double> t) {
				Item editedItem = ((Item) t.getTableView().getItems().get(t.getTablePosition().getRow()));
				editedItem.setPrice(t.getNewValue());
				controller.updateItem(editedItem);
				wishlisttable.refresh();
			}
		});

		//displaying and editing the date when the items are needed
		date.setCellValueFactory(new PropertyValueFactory<Item, Date>("dateNeeded"));
		date.setCellFactory(dateCellFactory);
		date.setOnEditCommit((TableColumn.CellEditEvent<Item, Date> t) -> {
			Item editedItem = ((Item) t.getTableView().getItems().get(t.getTablePosition().getRow()));
			Date oldDate = editedItem.getDateNeeded();
			editedItem.setDateNeeded(t.getNewValue());
			controller.updateItem(editedItem);
			//updating the event connected to the item
			calendarController.updateWishlistDate(oldDate, editedItem);
			wishlisttable.refresh();
		});

		//displaying and editing the additional info about the items
		addinfo.setCellValueFactory(new PropertyValueFactory<Item, String>("additionalInfo"));
		addinfo.setCellFactory(TextFieldTableCell.<Item>forTableColumn());
		addinfo.setOnEditCommit(new EventHandler<CellEditEvent<Item, String>>() {
			@Override
			public void handle(CellEditEvent<Item, String> t) {
				Item editedItem = ((Item) t.getTableView().getItems().get(t.getTablePosition().getRow()));
				editedItem.setAdditionalInfo(t.getNewValue());
				controller.updateItem(editedItem);
				wishlisttable.refresh();
			}
		});
	}

	/**
	 * Method for loading the view of add to wishlist
	 */
	public AnchorPane loadAddWish() {
		AnchorPane showAddWishView = null;
		FXMLLoader loaderAddWishView = new FXMLLoader(getClass().getResource("/view/wishlist/WishlistAdd.fxml"));
		//setting the text resources
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
	 * 
	 * @return the selected item
	 */
	public Item getSelectedItem() {
		return wishlisttable.getSelectionModel().getSelectedItem();
	}

	/**
	 * Method for checking that an item is currently selected in the table
	 * @return true if some item is selected
	 * @return false if nothing is selected
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
