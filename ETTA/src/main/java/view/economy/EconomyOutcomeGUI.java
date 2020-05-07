package view.economy;

import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;

import controller.EconomyController;
import controller.InputCheck;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.AnchorPane;
import javafx.util.Callback;
import javafx.util.converter.FloatStringConverter;
import model.Category;
import model.Transfer;
import res.MyBundle;

/**
 * GUI class that is in charge of the expense table in the economy part. Data in
 * the table can be deleted or modified. There is also a search part where the
 * user can search for expenses depending on the dates. Adding a new expense is
 * called from this view.
 */
public class EconomyOutcomeGUI extends AbstractEconomyGUI implements ITransferGUI {

	/**
	 * Reference to the used EconomyController
	 */
	EconomyController controller = new EconomyController();

	/**
	 * MyBundle object for setting the right resource bundle to localize the
	 * application
	 */
	MyBundle myBundle = new MyBundle();

	/**
	 * The list view from where adding, editing and deleting can be started in
	 * expencies
	 */
	@FXML
	AnchorPane economyoutcomeaddanchorpane;

	/**
	 * The reference of TableView (expenses) will be injected by the FXML loader
	 */
	@FXML
	private TableView<Transfer> expenseTable;

	/**
	 * The reference of TableColumn (expense's description) will be injected by the
	 * FXML loader
	 */
	@FXML
	private TableColumn<Transfer, String> expenseDescription;

	/**
	 * The reference of TableColumn (expense's date) will be injected by the FXML
	 * loader
	 */
	@FXML
	private TableColumn<Transfer, Date> expenseDate;

	/**
	 * The reference of TableColumn (expense's amount) will be injected by the FXML
	 * loader
	 */
	@FXML
	private TableColumn<Transfer, Float> expenseAmount;

	/**
	 * The reference of TableColumn (expense's category) will be injected by the
	 * FXML loader
	 */
	@FXML
	private TableColumn<Category, String> expenseCategory;

	/**
	 * The reference of DatePicker for starting day to search expenses
	 */
	@FXML
	private DatePicker expenceStartDate;

	/**
	 * The reference of DatePicker for ending day to search expenses
	 */
	@FXML
	private DatePicker expenceEndDate;

	/**
	 * The reference of InputCheck class used for checking user's input
	 */
	InputCheck inputCheck = new InputCheck();

	/**
	 * The date cell factory for editing the dates in the table
	 */
	Callback<TableColumn<Transfer, Date>, TableCell<Transfer, Date>> dateCellFactory = (
			TableColumn<Transfer, Date> param) -> new DateEditingCell();

	/**
	 * Constructor with no parameters
	 */
	public EconomyOutcomeGUI() {

	}

	/**
	 * Method showing the view of the Add Expense in the Expenses items section
	 * @param event ActionEvent that is handled
	 */
	@FXML
	public void showAddOutcome(ActionEvent event) {
		AnchorPane showAddOutcomeView = null;
		FXMLLoader loaderAddOutcomeView = new FXMLLoader(getClass().getResource("/view/economy/EconomyAddOutcome.fxml"));
		//setting the localized text resources
		loaderAddOutcomeView.setResources(myBundle.getBundle());
		try {
			showAddOutcomeView = loaderAddOutcomeView.load();
		} catch (IOException e) {
			e.printStackTrace();
		}
		// shows the loaded fxml file
		economyoutcomeaddanchorpane.getChildren().setAll(showAddOutcomeView);
	}

	/**
	 * Method for searching expenses from a selected time span
	 * @param event ActionEvent that is handled
	 */
	@FXML
	public void searchExpence(ActionEvent event) {
		LocalDate startDate = expenceStartDate.getValue();
		LocalDate endDate = expenceEndDate.getValue();
		controller.getSelectedTransfers(this, java.sql.Date.valueOf(startDate), java.sql.Date.valueOf(endDate));
	}

	/**
	 * Method that initializes the view and gets the expenses from the controller to
	 * display them on the page Fetches the expenses items from the database,
	 * displays them in the table view, and enables in-table editing
	 */
	@FXML
	public void initialize() {
		expenseDescription.setCellValueFactory(new PropertyValueFactory<Transfer, String>("description"));
		expenseDate.setCellValueFactory(new PropertyValueFactory<Transfer, Date>("date"));
		expenseAmount.setCellValueFactory(new PropertyValueFactory<Transfer, Float>("amount"));
		expenseCategory.setCellValueFactory(new PropertyValueFactory<Category, String>("category"));
		ObservableList<Transfer> expenses = FXCollections.observableArrayList(controller.getExpenses());
		expenseTable.setItems(expenses);

		expenseTable.setEditable(true);
		expenseDescription.setCellValueFactory(new PropertyValueFactory<Transfer, String>("description"));

		expenseAmount.setCellValueFactory(new PropertyValueFactory<Transfer, Float>("amount"));

		expenseDate.setCellValueFactory(new PropertyValueFactory<Transfer, Date>("date"));

		// enable editing of the description and updating the data
		expenseDescription.setCellFactory(TextFieldTableCell.<Transfer>forTableColumn());
		expenseDescription.setOnEditCommit(new EventHandler<CellEditEvent<Transfer, String>>() {
			@Override
			public void handle(CellEditEvent<Transfer, String> t) {
				Transfer editedOutcomeDesc = ((Transfer) t.getTableView().getItems()
						.get(t.getTablePosition().getRow()));
				editedOutcomeDesc.setDescription(t.getNewValue());
				controller.updateTransfer(editedOutcomeDesc);
				expenseTable.refresh();
			}
		});

		// enable editing of the amount and updating the data
		expenseAmount.setCellFactory(TextFieldTableCell.forTableColumn(new FloatStringConverter()));
		expenseAmount.setOnEditCommit(new EventHandler<CellEditEvent<Transfer, Float>>() {
			@Override
			public void handle(CellEditEvent<Transfer, Float> t) {
				Transfer editedOutcomeAmount = ((Transfer) t.getTableView().getItems()
						.get(t.getTablePosition().getRow()));
				editedOutcomeAmount.setAmount(0 - Math.abs(t.getNewValue()));
				controller.updateTransferAmount(editedOutcomeAmount);
				expenseTable.refresh();
			}
		});

		// enable editing of the expences date and updating the data
		expenseDate.setCellFactory(dateCellFactory);
		expenseDate.setOnEditCommit(new EventHandler<CellEditEvent<Transfer, Date>>() {
			@Override
			public void handle(CellEditEvent<Transfer, Date> t) {
				Transfer editedOutcomeDate = ((Transfer) t.getTableView().getItems()
						.get(t.getTablePosition().getRow()));
				editedOutcomeDate.setDate(t.getNewValue());
				controller.updateTransfer(editedOutcomeDate);
				expenseTable.refresh();
			}
		});
	}

	/**
	 * Method for deleting an expense
	 */
	@FXML
	public void deleteOutcome() {
		//if something is selected
		if (getSelectedItem() != null) {
			if (inputCheck.confirmDeleting()) {
				controller.removeTransfer(this);
				//update the table
				initialize();
			}
		} else {
			inputCheck.alertNothingSelected();
		}
	}

	/**
	 * Method for getting the selected expense
	 * @return Transfer that is selected
	 */
	public Transfer transferToDelete() {
		return expenseTable.getSelectionModel().getSelectedItem();
	}

	/**
	 * Method that removes an expense from the tableView
	 * @param Transfer expense to be removed
	 */
	public void removeFromTable(Transfer transfer) {
		expenseTable.getItems().remove(transfer);
	}

	/**
	 * Method for getting the selected income
	 * @return Transfer that is selected
	 */
	@FXML
	public Transfer getSelectedItem() {
		return expenseTable.getSelectionModel().getSelectedItem();
	}

	/**
	 * Method that sets data of expenses to the table. 
	 * Gets both incomes and expenses but displays only expenses in the table.
	 * @param Transfer [] list of the transfers
	 * 
	 */
	public void setData(Transfer[] readSeletedTransfers) {
		ArrayList<Transfer> expences = new ArrayList<Transfer>();
		//get only the expense transfers
		for (Transfer t : readSeletedTransfers) {
			if (t.isIncome() != true) {
				expences.add(t);
			}
		}
		Transfer[] expencesArr = new Transfer[expences.size()];
		expences.toArray(expencesArr);
		expenseTable.setItems(FXCollections.observableArrayList(expencesArr));
	}
}
