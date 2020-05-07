package view.economy;

import java.io.IOException;
import java.sql.Date;

import controller.EconomyController;
import controller.InputCheck;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.cell.ProgressBarTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.AnchorPane;
import javafx.util.Callback;
import javafx.util.converter.FloatStringConverter;
import model.Saving;
import res.MyBundle;

/**
 * GUI class that is in charge of the savings table in the economy part. Data in
 * the table can be deleted or modified. Adding a new saving goal is called from
 * this view. There is also a form for adding money to a savings goal in this
 * view.
 * 
 * @author Lena
 */
public class EconomySavingsGUI {

	/**
	 * Reference to the used EconomyController that gets this EconomySavingsGUI as a
	 * parameter
	 */
	private EconomyController controller = new EconomyController(this);

	/**
	 * MyBundle object for setting the right resource bundle to localize the
	 * application
	 */
	private MyBundle myBundle = new MyBundle();

	/**
	 * The reference of InputCheck class used for checking user's input
	 */
	private InputCheck inputCheck = new InputCheck();

	/**
	 * The list view from where adding, editing and deleting can be started in
	 * savings
	 */
	@FXML
	private AnchorPane economysavingsanchorpane;

	/**
	 * The reference of TableView (savings) will be injected by the FXML loader
	 */
	@FXML
	private TableView<Saving> savingsTable;

	/**
	 * The reference of TableColumn (savings's description) will be injected by the
	 * FXML loader
	 */
	@FXML
	private TableColumn<Saving, String> savingsDescription;

	/**
	 * The reference of TableColumn (savings's goal amount) will be injected by the
	 * FXML loader
	 */
	@FXML
	private TableColumn<Saving, Float> savingsGoalAmount;

	/**
	 * The reference of TableColumn (savings's saved amount) will be injected by the
	 * FXML loader
	 */
	@FXML
	private TableColumn<Saving, Float> savingsSavedAmount;
	/**
	 * The reference of TableColumn (savings's progress) will be injected by the
	 * FXML loader
	 */
	@FXML
	private TableColumn<Saving, Double> savingsProgress;

	/**
	 * The reference of TableColumn (savings's goal date) will be injected by the
	 * FXML loader
	 */
	@FXML
	private TableColumn<Saving, Date> savingsGoalDate;

	/**
	 * The reference of ComboBox (savings' descriptions list) will be injected by
	 * the FXML loader
	 */
	@FXML
	private ComboBox<String> savingGoalList;

	/**
	 * The reference to Label where the goal amount is shown when starting to add
	 * money to that goal
	 */
	@FXML
	private Label savingGoalAmount;

	/**
	 * The reference to Label where the saved amount is shown when starting to add
	 * money to said amount
	 */
	@FXML
	private Label savingSavedAmount;

	/**
	 * Text field the amount added to a saving
	 */
	@FXML
	private TextField savingAddedAmount;

	/**
	 * The date cell factory for editing the dates in the table
	 */
	Callback<TableColumn<Saving, Date>, TableCell<Saving, Date>> dateCellFactory = (
			TableColumn<Saving, Date> param) -> new SavingDateEditingCell();

	/**
	 * Method that initializes the view and gets the savings from the controller to
	 * display them on the page.
	 */
	@FXML
	public void initialize() {
		// getting the savings' data to put into the table
		savingsDescription.setCellValueFactory(new PropertyValueFactory<Saving, String>("description"));
		savingsGoalDate.setCellValueFactory(new PropertyValueFactory<Saving, Date>("goalDate"));
		savingsGoalAmount.setCellValueFactory(new PropertyValueFactory<Saving, Float>("goalAmount"));
		savingsSavedAmount.setCellValueFactory(new PropertyValueFactory<Saving, Float>("amount"));
		savingsProgress.setCellValueFactory(new PropertyValueFactory<Saving, Double>("progress"));
		savingsProgress.setCellFactory(ProgressBarTableCell.<Saving>forTableColumn());
		ObservableList<Saving> savings = FXCollections.observableArrayList(controller.getSavingss());
		savingsTable.setItems(savings);

		// making the savings table editable
		savingsTable.setEditable(true);

		// enable editing of the description and updating the data
		savingsDescription.setCellValueFactory(new PropertyValueFactory<Saving, String>("description"));
		savingsDescription.setCellFactory(TextFieldTableCell.<Saving>forTableColumn());
		savingsDescription.setOnEditCommit(new EventHandler<CellEditEvent<Saving, String>>() {
			@Override
			public void handle(CellEditEvent<Saving, String> t) {
				Saving editedSavingDesc = ((Saving) t.getTableView().getItems().get(t.getTablePosition().getRow()));
				// if the new description is given, update the data
				if (!inputCheck.isInputEmpty(t.getNewValue())) {
					editedSavingDesc.setDescription(t.getNewValue());
					controller.updateSaving(editedSavingDesc);
					initialize();
				} else {
					inputCheck.alertInputEmpty();
				}
			}
		});

		// enable editing of the amount and updating the data
		savingsSavedAmount.setCellValueFactory(new PropertyValueFactory<Saving, Float>("amount"));
		savingsSavedAmount.setCellFactory(TextFieldTableCell.forTableColumn(new FloatStringConverter()));
		savingsSavedAmount.setOnEditCommit(new EventHandler<CellEditEvent<Saving, Float>>() {
			@Override
			public void handle(CellEditEvent<Saving, Float> t) {
				Saving editedSaving = ((Saving) t.getTableView().getItems().get(t.getTablePosition().getRow()));
				Float oldAmount = editedSaving.getAmount();
				Float newAmount = t.getNewValue();
				Float difference = oldAmount - newAmount;
				// check if there's enough money to make this change
				if (controller.enoughBalance(difference)) {
					controller.updateBalanceAmount(difference);
					editedSaving.setAmount(newAmount);
					controller.updateSaving(editedSaving);
				} else {
					inputCheck.alertNotEnoughBalance();
				}
				initialize();
			}
		});

		// enable editing of the goal amount and updating the data
		savingsGoalAmount.setCellValueFactory(new PropertyValueFactory<Saving, Float>("goalAmount"));
		savingsGoalAmount.setCellFactory(TextFieldTableCell.forTableColumn(new FloatStringConverter()));
		savingsGoalAmount.setOnEditCommit(new EventHandler<CellEditEvent<Saving, Float>>() {
			@Override
			public void handle(CellEditEvent<Saving, Float> t) {
				Saving editedSavingGoalAmount = ((Saving) t.getTableView().getItems()
						.get(t.getTablePosition().getRow()));
				editedSavingGoalAmount.setGoal_amount(t.getNewValue());
				controller.updateSaving(editedSavingGoalAmount);
				initialize();
			}
		});

		// enable editing of the goal date and updating the data
		savingsGoalDate.setCellFactory(dateCellFactory);
		savingsGoalDate.setOnEditCommit((TableColumn.CellEditEvent<Saving, Date> t) -> {
			Saving editedSaving = ((Saving) t.getTableView().getItems().get(t.getTablePosition().getRow()));
			java.sql.Date newDate = t.getNewValue();
			editedSaving.setGoalDate(newDate);
			controller.updateSaving(editedSaving);
			savingsTable.refresh();
		});
		// clears the savingGoalList so that the list items aren't duplicated
		savingGoalList.getItems().clear();
		savingGoalList.getItems().addAll(controller.getSavingsList());
		savingGoalList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
			public void changed(ObservableValue<? extends String> ov, final String oldvalue, final String newvalue) {
				// something is selected
				if (newvalue != null) {
					Saving saving = controller.getSaving(savingGoalList.getValue());
					savingGoalAmount.setText(String.valueOf(saving.getGoalAmount()));
					savingSavedAmount.setText(String.valueOf(saving.getAmount()));
				}
			}
		});
	}

	/**
	 * Method showing the view of the Add Savings in the Savings items section
	 * 
	 * @param event ActionEvent that is handled
	 */
	@FXML
	public void showAddSavings(ActionEvent event) {
		AnchorPane showAddSavingsView = null;
		FXMLLoader loaderAddSavingsView = new FXMLLoader(
				getClass().getResource("/view/economy/EconomyAddSavings.fxml"));
		// setting the localized text resources
		loaderAddSavingsView.setResources(myBundle.getBundle());
		try {
			showAddSavingsView = loaderAddSavingsView.load();
		} catch (IOException e) {
			e.printStackTrace();
		}
		economysavingsanchorpane.getChildren().setAll(showAddSavingsView);
	}

	/**
	 * Method for deleting a saving
	 */
	@FXML
	public void deleteSaving() {
		// if something is selected
		if (savingToDelete() != null) {
			if (inputCheck.confirmDeleting()) {
				controller.removeSaving();
				// update the table
				initialize();
			}
		} else {
			inputCheck.alertNothingSelected();
		}
	}

	/**
	 * Method for moving a saving to expenses
	 */
	@FXML
	public void moveSavingToExspence() {
		//if something is selected
		if (savingToDelete() != null) {
			if (inputCheck.confirmSavingAchieved()) {
				Saving achievedSaving = savingToDelete();
				controller.moveSavingToExpense(achievedSaving);
			}
		} else {
			inputCheck.alertNothingSelected();
		}
	}

	/**
	 * Method for getting the selected saving
	 * @return Saving that is selected
	 */
	public Saving savingToDelete() {
		return savingsTable.getSelectionModel().getSelectedItem();
	}

	/**
	 * Method that removes a saving goal from the tableView
	 * @param Saving to be removed
	 */
	public void removeFromTable(Saving savingToDelete) {
		savingsTable.getItems().remove(savingToDelete);
	}

	/**
	 * Method for adding money to a saving goal
	 */
	@FXML
	public void updateSavingAmount() {
		// a saving goal is selected
		if (savingGoalList.getSelectionModel().getSelectedItem() != null) {
			// user input is not empty
			if (!inputCheck.isInputEmpty(savingAddedAmount.getText())) {
				// user input is a number
				if (inputCheck.isInputFloat(savingAddedAmount.getText())) {
					Saving editedSaving = controller.getSaving(savingGoalList.getValue());
					Float oldAmount = editedSaving.getAmount();
					// only positive amount can be added
					Float difference = Math.abs(Float.parseFloat(savingAddedAmount.getText()));
					// there is enough money on the balance
					if (controller.updateBalanceAmount(0 - difference)) {
						editedSaving.setAmount(oldAmount + difference);
						controller.updateSaving(editedSaving);
					} else {
						inputCheck.alertNotEnoughBalance();
					}
					// clear the form
					savingGoalList.getSelectionModel().clearSelection();
					savingGoalAmount.setText("");
					savingSavedAmount.setText("");
					savingAddedAmount.clear();
					initialize();
				} else {
					inputCheck.alertInputNotFloat();
				}
			} else {
				inputCheck.alertInputEmpty();
			}
		} else {
			inputCheck.alertNothingComboBox();
		}
	}

}
