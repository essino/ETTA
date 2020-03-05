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
import model.Transfer;

public class EconomyIncomeGUI {

	EconomyController controller = new EconomyController(this);
	
	/**
	 * The list view from where adding, editing and deleting can be started in expencies
	 */
	@FXML	
	AnchorPane economyincomeaddanchorpane;
	/**
	 * The reference of TableView (expenses) will be injected by the FXML loader
	 */
	
	@FXML
	AnchorPane economyincomeanchorpane;
	
	@FXML
    private TableView<Transfer> incomeTable;
	
	/**
	 * The reference of TableColumn (expense's description) will be injected by the FXML loader
	 */
    @FXML
    private TableColumn<Transfer, String> incomeDescription;
    
    /**
	 * The reference of TableColumn (expense's date) will be injected by the FXML loader
	 */
    @FXML
    private TableColumn<Transfer, Date> incomeDate;
    
    /**
	 * The reference of TableColumn (expense's amount) will be injected by the FXML loader
	 */
    @FXML
    private TableColumn<Transfer, Float> incomeAmount;
	
	/**
	 * Method showing the view of the Add Expense in the Expenses items section
	 * @param event ActionEvent that is handled
	 */
    
    /**
	@FXML
	public void showAddIncome(ActionEvent event) {
		AnchorPane economyincomeaddanchorpane = null; 
		FXMLLoader loaderAddIncomeView  = new FXMLLoader(getClass().getResource("/view/EconomyAddIncome2.fxml")); 
		try {
			economyincomeaddanchorpane = loaderAddIncomeView.load();
			} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			}
		economyincomeanchorpane.getChildren().setAll(economyincomeaddanchorpane);
	}
	*/
	
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
		ObservableList<Transfer> income =  FXCollections.observableArrayList(controller.getIncome());
		incomeTable.setItems(income);
	}
	
	/** 
	 * Method that tells controller to delete an income 
	 */
	@FXML
	public void deleteIncome() {
		controller.removeExpense();
	}
	
	/** 
	 * Method that returns the selected expense
	 * @return Transfer that is selected
	 */
	public Transfer transferToDelete() {
		return incomeTable.getSelectionModel().getSelectedItem();
	}
	
	/** 
	 * Method that removes an expense from the tableView
	 * @param Transfer expense to be removed
	 */
	@FXML
	public void removeFromTable(Transfer transfer) {
		incomeTable.getItems().remove(transfer);
	}
}
