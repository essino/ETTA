package view.economy;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Locale;
import java.util.ResourceBundle;

import controller.EconomyController;
import controller.InputCheck;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.Pane;
import javafx.util.converter.FloatStringConverter;
import model.Category;
import model.Transfer;

/**
 * GUI class relating to the Balance main page section
 */
public class BalanceOverviewGUI {
	EconomyController controller;
	/**
	 * The reference of pane where user can input the start balance amount
	 */
	@FXML
	private Pane setBalancePane;
	/**
	 * The reference of textField where user inputs the start balance amount
	 */
	@FXML
	private TextField newBalance;
	/**
	 * The reference of label will be injected by the FXML loader
	 */
	@FXML 
	private Label amountBalance; 
	
	InputCheck inputCheck = new InputCheck();
	
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
	 * The reference of TableColumn (income's amount) will be injected by the FXML loader
	 */
    @FXML
    private TableColumn<Transfer, Float> incomeAmount;
    
    /**
  	 * The reference of TableColumn (income's date) will be injected by the FXML loader
  	 */
      @FXML
      private TableColumn<Transfer, Date> incomeDate;
      
	/** 
	 * Constructor 
	 */
	public BalanceOverviewGUI() { 
		controller = new EconomyController(this);
	} 
	
	

	
	/** 
	 * Method that gets balance amount and displays it on the page 
	 * @param amount double the balance amount
	 */ 
	public void setBalance(double amount) { 
		String balanceString = String.format("%.2f", amount); 
		amountBalance.setText(balanceString); 
	} 
	
	/** 
	 * Method that displays the pane where user can set the start balance amount  
	 */ 
	@FXML
	public void showSetBalance() {
		setBalancePane.setVisible(true);
	}
	
	/** 
	 * Method that hides the pane where user can set the start balance amount  
	 */ 
	@FXML
	public void hideSetBalance() {
		setBalancePane.setVisible(false);
	}
	
	/** 
	 * Method that initializes the view and gets the balance amount  from the controller
	 */
	@FXML 
	public void initialize() { 
		controller.getBalance(); 
		
		incomeDescription.setCellValueFactory(
                new PropertyValueFactory<Transfer, String>("description"));
		incomeDate.setCellValueFactory(
                new PropertyValueFactory<Transfer, Date>("date"));
		incomeAmount.setCellValueFactory(
                new PropertyValueFactory<Transfer, Float>("amount"));
		
		searchWeeksTransfers();		
		
		incomeTable.setEditable(true);
		incomeDescription.setCellValueFactory(new PropertyValueFactory<Transfer, String>("description")); 
		incomeAmount.setCellValueFactory(new PropertyValueFactory<Transfer, Float>("amount"));
		
		
	}
	
	
	
	/** 
	 * Method that saves the start balance amount inputed by the user. 
	 * First it gives the input to InputCheck class to check it.
	 * If the input is valid, it gives it to the controller for updating the database.
	 */ 
	@FXML
	public void saveBalance() {
		if(inputCheck.isInputFloat(newBalance.getText())) {
			controller.updateBalance(Float.parseFloat(newBalance.getText()));
		}
		else {
			inputCheck.alertInputNotFloat();
		}
	}
	
	public void searchWeeksTransfers() {
		LocalDate endDate = LocalDate.now();
		LocalDate startDate = endDate.minusDays(6);
		//LocalDate startDate = endDate.minus(7);
		System.out.println(startDate);
		System.out.println(endDate);
		//This gets all transfers from this period
		controller.getSelectedIncomes(java.sql.Date.valueOf(startDate), java.sql.Date.valueOf(endDate));
		//This gets all the incomes
		//Transfer[] incomes = controller.getIncomesSeletedDays(java.sql.Date.valueOf(startDate), java.sql.Date.valueOf(endDate));
		//incomeTable.setItems(FXCollections.observableArrayList(incomes));
	}
	
	@FXML
	public void setData(Transfer[] readSeletedTransfers) {
		//chooses only incomes from the list
		ArrayList<Transfer> incomes = new ArrayList<Transfer>();
		for (Transfer t: readSeletedTransfers) {
			if (t.isIncome() == true) {
				incomes.add(t);
			} 
		}
		Transfer[] incomesArr = new Transfer[incomes.size()];
		incomes.toArray(incomesArr);
		
		incomeTable.setItems(FXCollections.observableArrayList(incomesArr));
		
	}
}
