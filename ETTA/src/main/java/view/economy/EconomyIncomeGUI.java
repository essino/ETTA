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
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.AnchorPane;
import javafx.util.Callback;
import javafx.util.converter.FloatStringConverter;
import model.Category;
import model.Transfer;
import res.MyBundle;

/**
 * GUI class that is in charge of the income table in the economy part. Data in
 * the table can be deleted or modified. There is also a search part where the
 * user can search for incomes depending on the dates. Adding a new income is
 * called from this view.
 */
public class EconomyIncomeGUI extends AbstractEconomyGUI implements ITransferGUI {

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
	 * incomes
	 */
	@FXML
	AnchorPane economyincomeanchorpane;

	/**
	 * The reference of TableView (incomes) will be injected by the FXML loader
	 */
	@FXML
	private TableView<Transfer> incomeTable;

	/**
	 * The reference of TableColumn (income's description) will be injected by the
	 * FXML loader
	 */
	@FXML
	private TableColumn<Transfer, String> incomeDescription;

	/**
	 * The reference of TableColumn (income's date) will be injected by the FXML
	 * loader
	 */
	@FXML
	private TableColumn<Transfer, Date> incomeDate;

	/**
	 * The reference of TableColumn (income's amount) will be injected by the FXML
	 * loader
	 */
	@FXML
	private TableColumn<Transfer, Float> incomeAmount;

	/**
	 * The reference of TableColumn (income's category) will be injected by the FXML
	 * loader
	 */
	@FXML
	private TableColumn<Category, String> incomeCategory;

	/**
	 * The reference of DatePicker for starting day to search incomes
	 */
	@FXML
	private DatePicker incomeStartDate;

	/**
	 * The reference of DatePicker for ending day to search incomes
	 */
	@FXML
	private DatePicker incomeEndDate;

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
	public EconomyIncomeGUI() {

	}

	/**
	 * Method showing the view of the Add Income in the Incomes items section
	 * @param event ActionEvent that is handled
	 */
	@FXML
	public void showAddIncome(ActionEvent event) {
		AnchorPane showAddIncomeView = null;
		FXMLLoader loaderAddIncomeView = new FXMLLoader(getClass().getResource("/view/economy/EconomyAddIncome2.fxml"));
		//setting the localized text resources
		loaderAddIncomeView.setResources(myBundle.getBundle());
		try {
			showAddIncomeView = loaderAddIncomeView.load();
		} catch (IOException e) {
			e.printStackTrace();
		}
		economyincomeanchorpane.getChildren().setAll(showAddIncomeView);
	}

	/**
	 * Method for searching incomes from a selected time span
	 * @param event ActionEvent that is handled
	 */
	@FXML
	public void searchIncomes(ActionEvent event) {
		LocalDate startDate = incomeStartDate.getValue();
		LocalDate endDate = incomeEndDate.getValue();
		controller.getSelectedTransfers(this, java.sql.Date.valueOf(startDate), java.sql.Date.valueOf(endDate));
	}

	/**
	 * Method that initializes the view and gets the incomes from the controller to display them on the page 
	 * Fetches the incomes items from the database,
	 * and displays them in the table view, 
	 * and enables in-table editing
	 */
	@FXML
	public void initialize() {
		incomeDescription.setCellValueFactory(new PropertyValueFactory<Transfer, String>("description"));
		incomeDate.setCellValueFactory(new PropertyValueFactory<Transfer, Date>("date"));
		incomeAmount.setCellValueFactory(new PropertyValueFactory<Transfer, Float>("amount"));
		incomeCategory.setCellValueFactory(new PropertyValueFactory<Category, String>("category"));

		ObservableList<Transfer> incomes = FXCollections.observableArrayList(controller.getIncomes());
		incomeTable.setItems(incomes);

		incomeTable.setEditable(true);
		incomeDescription.setCellValueFactory(new PropertyValueFactory<Transfer, String>("description"));
		incomeAmount.setCellValueFactory(new PropertyValueFactory<Transfer, Float>("amount"));
		incomeDate.setCellValueFactory(new PropertyValueFactory<Transfer, Date>("date"));

		// enable editing of the description and updating the data
		incomeDescription.setCellFactory(TextFieldTableCell.<Transfer>forTableColumn());
		incomeDescription.setOnEditCommit(new EventHandler<CellEditEvent<Transfer, String>>() {
			@Override
			public void handle(CellEditEvent<Transfer, String> t) {
				Transfer editedIncomeDesc = ((Transfer) t.getTableView().getItems().get(t.getTablePosition().getRow()));
				editedIncomeDesc.setDescription(t.getNewValue());
				controller.updateTransfer(editedIncomeDesc);
				incomeTable.refresh();
			}
		});

		// enable editing of the amount and updating the data
		incomeAmount.setCellFactory(TextFieldTableCell.forTableColumn(new FloatStringConverter()));
		incomeAmount.setOnEditCommit(new EventHandler<CellEditEvent<Transfer, Float>>() {
			@Override
			public void handle(CellEditEvent<Transfer, Float> t) {
				Transfer editedIncomeAmount = ((Transfer) t.getTableView().getItems()
						.get(t.getTablePosition().getRow()));
				editedIncomeAmount.setAmount(t.getNewValue());
				controller.updateTransferAmount(editedIncomeAmount);
				incomeTable.refresh();
			}
		});

		// enable editing of the date of income and updating the data
		incomeDate.setCellFactory(dateCellFactory);
		incomeDate.setOnEditCommit(new EventHandler<CellEditEvent<Transfer, Date>>() {
			@Override
			public void handle(CellEditEvent<Transfer, Date> t) {
				Transfer editedIncomeDate = ((Transfer) t.getTableView().getItems().get(t.getTablePosition().getRow()));
				editedIncomeDate.setDate(t.getNewValue());
				controller.updateTransfer(editedIncomeDate);
				incomeTable.refresh();
			}
		});
	}

	/**
	 * Method for deleting an income
	 */
	@FXML
	public void deleteIncome() {
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
	 * Method for getting the selected income
	 * @return Transfer that is selected
	 */
	public Transfer transferToDelete() {
		return incomeTable.getSelectionModel().getSelectedItem();
	}

	/**
	 * Method that removes an income from the tableView
	 * @param Transfer income to be removed
	 */
	@FXML
	public void removeFromTable(Transfer transfer) {
		incomeTable.getItems().remove(transfer);
	}

	/**
	 * Method for getting the selected item from the table
	 * @return Transfer that is selected
	 */
	@FXML
	public Transfer getSelectedItem() {
		return incomeTable.getSelectionModel().getSelectedItem();
	}

	/**
	 * Method that sets data of incomes to the table. 
	 * Gets both incomes and expenses but displays only incomes in the table.
	 * @param Transfer [] list of the transfers
	 */
	@FXML
	public void setData(Transfer[] transfers) {
		ArrayList<Transfer> incomes = new ArrayList<Transfer>();
		//get only the income transfers
		for (Transfer t : transfers) {
			if (t.isIncome() == true) {
				incomes.add(t);
			}
		}
		Transfer[] incomesArr = new Transfer[incomes.size()];
		incomes.toArray(incomesArr);
		incomeTable.setItems(FXCollections.observableArrayList(incomesArr));
	}

}