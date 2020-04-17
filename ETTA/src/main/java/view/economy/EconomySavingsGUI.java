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

public class EconomySavingsGUI {
	EconomyController controller = new EconomyController(this);
	
	MyBundle myBundle = new MyBundle();
	
	/**
	 * The reference of InputCheck class used for checking user's input
	 */
	InputCheck inputCheck = new InputCheck();
	
	/**
	 * The list view from where adding, editing and deleting can be started in savings
	 */
	@FXML
	AnchorPane economysavingsanchorpane;
	/**
	 * The reference of TableView (savings) will be injected by the FXML loader
	 */
	@FXML
    private TableView<Saving> savingsTable;
	/**
	 * The reference of TableColumn (savings's description) will be injected by the FXML loader
	 */
    @FXML
    private TableColumn<Saving, String> savingsDescription;
	/**
	 * The reference of TableColumn (savings's goal amount) will be injected by the FXML loader
	 */
    @FXML
    private TableColumn<Saving, Float> savingsGoalAmount;
    
	/**
	 * The reference of TableColumn (savings's saved amount) will be injected by the FXML loader
	 */
    @FXML
    private TableColumn<Saving, Float> savingsSavedAmount;
    /**
	 * The reference of TableColumn (savings's progress) will be injected by the FXML loader
	 */
    @FXML
    private TableColumn<Saving, Double> savingsProgress;
    
	/**
	 * The reference of TableColumn (savings's goal date) will be injected by the FXML loader
	 */
    @FXML
    private TableColumn<Saving, Date> savingsGoalDate;
    
    
    @FXML
    private ComboBox<String> savingGoalList;
    
    @FXML
    private Label savingGoalAmount;
    
    @FXML
    private Label savingSavedAmount;

    @FXML
    private TextField savingAddedAmount;
    
    Callback<TableColumn<Saving, Date>, TableCell<Saving, Date>> dateCellFactory = (TableColumn<Saving, Date> param) -> new SavingDateEditingCell();
    
	/** 
	 * Method that initializes the view and gets the savings  from the controller to display them on the page
	 */
	@FXML 
	public void initialize() { 
		savingsDescription.setCellValueFactory(
                new PropertyValueFactory<Saving, String>("description"));
		savingsGoalDate.setCellValueFactory(
                new PropertyValueFactory<Saving, Date>("goalDate"));
		savingsGoalAmount.setCellValueFactory(
                new PropertyValueFactory<Saving, Float>("goalAmount"));
		savingsSavedAmount.setCellValueFactory(
                new PropertyValueFactory<Saving, Float>("amount"));
		savingsProgress.setCellValueFactory( 
				new PropertyValueFactory<Saving, Double>("progress"));
		savingsProgress.setCellFactory(ProgressBarTableCell.<Saving>forTableColumn());
		ObservableList<Saving> savings =  FXCollections.observableArrayList(controller.getSavingss());
		savingsTable.setItems(savings);
		
		savingsTable.setEditable(true);
		
		savingsDescription.setCellValueFactory(new PropertyValueFactory<Saving, String>("description")); 
		savingsDescription.setCellFactory(TextFieldTableCell.<Saving>forTableColumn());
		savingsDescription.setOnEditCommit(
			new EventHandler<CellEditEvent<Saving, String>>(){
				@Override
				public void handle(CellEditEvent<Saving, String> t) {
					Saving editedSavingDesc = ((Saving) t.getTableView().getItems().get(t.getTablePosition().getRow()));
					editedSavingDesc.setDescription(t.getNewValue());
					controller.updateSaving(editedSavingDesc);
					savingsTable.refresh();
					}});
		
		savingsSavedAmount.setCellValueFactory(new PropertyValueFactory<Saving, Float>("amount"));
		savingsSavedAmount.setCellFactory(TextFieldTableCell.forTableColumn(new FloatStringConverter()));
		savingsSavedAmount.setOnEditCommit(
			new EventHandler<CellEditEvent<Saving, Float>>(){
				@Override
				public void handle(CellEditEvent<Saving, Float> t) {			
					Saving editedSaving = ((Saving) t.getTableView().getItems().get(t.getTablePosition().getRow()));
					Float oldAmount = editedSaving.getAmount();
					Float newAmount = t.getNewValue();
					Float difference = oldAmount-newAmount;
					if(controller.updateBalanceAmount(difference)) {
						editedSaving.setAmount(newAmount);
						controller.updateSaving(editedSaving);
					}
					else {
						inputCheck.alertNotEnoughBalance();
					}
					initialize();
					}});
		
		savingsGoalAmount.setCellValueFactory(new PropertyValueFactory<Saving, Float>("goalAmount"));
		savingsGoalAmount.setCellFactory(TextFieldTableCell.forTableColumn(new FloatStringConverter()));
		savingsGoalAmount.setOnEditCommit(
			new EventHandler<CellEditEvent<Saving, Float>>(){
				@Override
				public void handle(CellEditEvent<Saving, Float> t) {			
					Saving editedSavingGoalAmount = ((Saving) t.getTableView().getItems().get(t.getTablePosition().getRow()));
					editedSavingGoalAmount.setGoal_amount(t.getNewValue());
					controller.updateSaving(editedSavingGoalAmount);
					initialize();
					}});
		
		savingsGoalDate.setCellFactory(dateCellFactory);
		savingsGoalDate.setOnEditCommit(
			(TableColumn.CellEditEvent<Saving, Date> t) -> {
				Saving editedSaving = ((Saving) t.getTableView().getItems()
						.get(t.getTablePosition().getRow()));
				java.sql.Date newDate = t.getNewValue();
				editedSaving.setGoalDate(newDate);
				controller.updateSaving(editedSaving);
				savingsTable.refresh();
			}
		);
		savingGoalList.getItems().addAll(controller.getSavingsList());
		savingGoalList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>()
        {
            public void changed(ObservableValue<? extends String> ov,
                    final String oldvalue, final String newvalue)
            {
            	Saving saving = controller.getSaving(savingGoalList.getValue());
            	savingGoalAmount.setText(String.valueOf(saving.getGoalAmount()));
        		savingSavedAmount.setText(String.valueOf(saving.getAmount()));
        }});
		
	}

	/**
	 * Method showing the view of the Add Savings in the Savings items section
	 * @param event ActionEvent that is handled
	 */
	@FXML
	public void showAddSavings(ActionEvent event) {
		AnchorPane showAddSavingsView = null;
		FXMLLoader loaderAddSavingsView = new FXMLLoader(getClass().getResource("/view/economy/EconomyAddSavings.fxml"));
		loaderAddSavingsView .setResources(myBundle.getBundle());
		try {
			showAddSavingsView = loaderAddSavingsView.load();
			} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			}
		
		economysavingsanchorpane.getChildren().setAll(showAddSavingsView);
		
	}
	
	/** 
	 * Method that tells controller to delete a saving
	 */
	@FXML
	public void deleteSaving() {
		if(inputCheck.confirmDeleting()) {
			controller.removeSaving();
		}
	}
	
	/** 
	 * Method that returns the selected saving
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
	
	@FXML
	public void updateSavingAmount() {
		if(inputCheck.isInputFloat(savingAddedAmount.getText())){
			if(!inputCheck.isInputEmpty(savingAddedAmount.getText())) {
				Saving editedSaving = controller.getSaving(savingGoalList.getValue());
				Float oldAmount = editedSaving.getAmount();
				Float difference = Float.parseFloat(savingAddedAmount.getText());
				if(controller.updateBalanceAmount(0-difference)) {
					editedSaving.setAmount(oldAmount+difference);
					controller.updateSaving(editedSaving);
				}
				else {
					inputCheck.alertNotEnoughBalance();
				}
				savingGoalList.getSelectionModel().clearSelection();
				savingGoalAmount.setText("");
				savingSavedAmount.setText("");
				savingAddedAmount.clear();
				initialize();
			}
			else {
				inputCheck.alertInputEmpty();
			}
		}
		else {
			inputCheck.alertInputNotFloat();
			
		}
	}
}
