package view;

import java.io.IOException;
import java.sql.Date;

import controller.EconomyController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import model.Balance;
import model.Category;
import model.Transfer;

public class EconomyIncomeGUI {

	EconomyController controller = new EconomyController(this);
	
	/**
	 * The list view from where adding, editing and deleting can be started in incomes
	 */
	@FXML	
	AnchorPane economyincomeaddanchorpane;

	
	@FXML
	AnchorPane economyincomeanchorpane;
	
	/**
	 * The reference of TableView (incomes) will be injected by the FXML loader
	 */
	@FXML
    private TableView<Transfer> incomeTable;
	
	/**
	 * The reference of TableColumn (income's description) will be injected by the FXML loader
	 */
    @FXML
    private TableColumn<Transfer, String> incomeDescription;
    
    /**
	 * The reference of TableColumn (income's date) will be injected by the FXML loader
	 */
    @FXML
    private TableColumn<Transfer, Date> incomeDate;
    
    /**
	 * The reference of TableColumn (income's amount) will be injected by the FXML loader
	 */
    @FXML
    private TableColumn<Transfer, Float> incomeAmount;
    
    /**
  	 * The reference of TableColumn (income's category) will be injected by the FXML loader
  	 */
      @FXML
      private TableColumn<Category, String> incomeCategory;
	
	
    /**
	 * Method showing the view of the Add Income in the Incomes items section
	 * @param event ActionEvent that is handled
	 */
	@FXML
	public void showAddIncome(ActionEvent event) {
		AnchorPane showAddIncomeView = null;
		FXMLLoader loaderAddIncomeView = new FXMLLoader(getClass().getResource("/view/EconomyAddIncome2.fxml"));
		try {
			showAddIncomeView = loaderAddIncomeView.load();
			} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			}
		
		economyincomeanchorpane.getChildren().setAll(showAddIncomeView);
	
	}
	
	/** 
	 * Method that initializes the view and gets the incomes  from the controller to display them on the page
	 */
	@FXML 
	public void initialize() { 
		incomeDescription.setCellValueFactory(
                new PropertyValueFactory<Transfer, String>("description"));
		incomeDate.setCellValueFactory(
                new PropertyValueFactory<Transfer, Date>("date"));
		incomeAmount.setCellValueFactory(
                new PropertyValueFactory<Transfer, Float>("amount"));
		incomeCategory.setCellValueFactory(
                new PropertyValueFactory<Category, String>("category"));
		ObservableList<Transfer> incomes =  FXCollections.observableArrayList(controller.getIncomes());
		incomeTable.setItems(incomes);
	}
	
	

	
	/** 
	 * Method that tells controller to delete an income 
	 */
	@FXML
	public void deleteIncome() {
		controller.removeIncome();
	}
	
	/** 
	 * Method that returns the selected income
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
}