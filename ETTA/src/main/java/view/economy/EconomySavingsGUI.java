package view.economy;

import java.io.IOException;
import java.sql.Date;

import controller.EconomyController;
import controller.InputCheck;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import model.Category;
import model.Saving;
import model.Transfer;

public class EconomySavingsGUI {
	EconomyController controller = new EconomyController(this);
	
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
    private TableColumn<Saving, Float> savingsProgress;
    
	/**
	 * The reference of TableColumn (savings's goal date) will be injected by the FXML loader
	 */
    @FXML
    private TableColumn<Saving, Date> savingsGoalDate;

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
				new PropertyValueFactory<Saving, Float>("progress"));
		ObservableList<Saving> savings =  FXCollections.observableArrayList(controller.getSavingss());
		savingsTable.setItems(savings);
	}
	/**
	 * Method showing the view of the Add Savings in the Savings items section
	 * @param event ActionEvent that is handled
	 */
	@FXML
	public void showAddSavings(ActionEvent event) {
		AnchorPane showAddSavingsView = null;
		FXMLLoader loaderAddSavingsView = new FXMLLoader(getClass().getResource("/view/economy/EconomyAddSavings.fxml"));
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
	
	public void removeFromTable(Saving savingToDelete) {
		savingsTable.getItems().remove(savingToDelete);
		
	}
}
