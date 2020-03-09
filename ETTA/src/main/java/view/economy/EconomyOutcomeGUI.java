package view.economy;

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
import model.Category;
import model.Transfer;

public class EconomyOutcomeGUI {
	
	EconomyController controller = new EconomyController(this);
	
	/**
	 * The list view from where adding, editing and deleting can be started in expencies
	 */
	@FXML	
	AnchorPane economyoutcomeaddanchorpane;
	/**
	 * The reference of TableView (expenses) will be injected by the FXML loader
	 */
	@FXML
    private TableView<Transfer> expenseTable;
	
	/**
	 * The reference of TableColumn (expense's description) will be injected by the FXML loader
	 */
    @FXML
    private TableColumn<Transfer, String> expenseDescription;
    
    /**
	 * The reference of TableColumn (expense's date) will be injected by the FXML loader
	 */
    @FXML
    private TableColumn<Transfer, Date> expenseDate;
    
    /**
	 * The reference of TableColumn (expense's amount) will be injected by the FXML loader
	 */
    @FXML
    private TableColumn<Transfer, Float> expenseAmount;
    
    /**
  	 * The reference of TableColumn (expense's category) will be injected by the FXML loader
  	 */
      @FXML
      private TableColumn<Category, String> expenseCategory;
      
	
	/**
	 * Method showing the view of the Add Expense in the Expenses items section
	 * @param event ActionEvent that is handled
	 */
	@FXML
	public void showAddOutcome(ActionEvent event) {
		AnchorPane showAddOutcomeView = null;
		FXMLLoader loaderAddOutcomeView = new FXMLLoader(getClass().getResource("/view/economy/EconomyAddOutcome.fxml"));
		try {
			showAddOutcomeView = loaderAddOutcomeView.load();
			} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			}
		//shows the loaded fxml file
		economyoutcomeaddanchorpane.getChildren().setAll(showAddOutcomeView);
	
	}
	
	/** 
	 * Method that initializes the view and gets the expenses  from the controller to display them on the page
	 */
	@FXML 
	public void initialize() { 
		expenseDescription.setCellValueFactory(
                new PropertyValueFactory<Transfer, String>("description"));
		expenseDate.setCellValueFactory(
                new PropertyValueFactory<Transfer, Date>("date"));
		expenseAmount.setCellValueFactory(
                new PropertyValueFactory<Transfer, Float>("amount"));
		expenseCategory.setCellValueFactory(
                new PropertyValueFactory<Category, String>("category"));
		ObservableList<Transfer> expenses =  FXCollections.observableArrayList(controller.getExpenses());
		expenseTable.setItems(expenses);
	}
	
	/** 
	 * Method that tells controller to delete an expense 
	 */
	@FXML
	public void deleteOutcome() {
		controller.removeExpense();
	}
	
	/** 
	 * Method that returns the selected expense
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
	 * Method that tells controller to edit an expense 
	 */
	@FXML
	public void editOutcome() {
		//expenseTable.setEditable(true);
		//controller.editExpense();
	}
}
