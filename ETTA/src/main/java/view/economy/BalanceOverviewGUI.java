package view.economy;

import java.sql.Date;
import java.text.DateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Locale;

import controller.EconomyController;
import controller.InputCheck;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import model.Transfer;
import res.MyBundle;

/**
 * GUI class relating to the Balance main page section
 */
public class BalanceOverviewGUI extends AbstractEconomyGUI{
	
	/**
	 * Reference to the used EconomyController 
	 */
	EconomyController controller;
	/**
	 * MyBundle object for setting the right resource bundle to localize the application
	 */
	MyBundle myBundle = new MyBundle();
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
	
	/**
	 * InputCheck object created to check the user's input
	 */
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
	 * Method that initializes the view and gets the balance amount, incomes and expences from last seven days  from the controller
	 */
	@FXML 
	public void initialize() { 
		controller.getBalance(); 
		
		Locale locale = Locale.getDefault();
	    DateFormat df = DateFormat.getDateInstance(DateFormat.SHORT, locale);
	    //descriptions set in the cells
		incomeDescription.setCellValueFactory(
                new PropertyValueFactory<Transfer, String>("description"));
		//income dates set in the cells
		incomeDate.setCellValueFactory(
                new PropertyValueFactory<Transfer, Date>("date"));
		incomeDate.setCellFactory(column -> {
	        TableCell<Transfer, Date> cell = new TableCell<Transfer, Date>() {
	            @Override
	            protected void updateItem(Date item, boolean empty) {
	                super.updateItem(item, empty);
	                if(empty) {
	                    setText(null);
	                }
	                else {
	                	setText(df.format(item));
	                }
	            }
	        };
	        return cell;
	    });
		//amount set in the cells
		incomeAmount.setCellValueFactory(
                new PropertyValueFactory<Transfer, Float>("amount"));
		//descriptions set in the cells
		expenseDescription.setCellValueFactory(
                new PropertyValueFactory<Transfer, String>("description"));
		//expenses dates set in the cells
		expenseDate.setCellValueFactory(
                new PropertyValueFactory<Transfer, Date>("date"));
		expenseDate.setCellFactory(column -> {
	        TableCell<Transfer, Date> cell = new TableCell<Transfer, Date>() {
	            @Override
	            protected void updateItem(Date item, boolean empty) {
	                super.updateItem(item, empty);
	                if(empty) {
	                    setText(null);
	                }
	                else {
	                	setText(df.format(item));
	                }
	            }
	        };
	        return cell;
	    });
		expenseAmount.setCellValueFactory(
                new PropertyValueFactory<Transfer, Float>("amount"));
		
		//search transfers from last seven days from database
		searchWeeksTransfers();	

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
	
	/** 
	 * Method search information of transfers from database. 
	 * endDate is today and startDate is week a go.
	 * 
	 */ 
	
	public void searchWeeksTransfers() {
		LocalDate endDate = LocalDate.now();
		LocalDate startDate = endDate.minusDays(6);
		//This gets all transfers from this period
		controller.getSelectedTransfers(this, java.sql.Date.valueOf(startDate), java.sql.Date.valueOf(endDate));
	}

	/** 
	 * Method sets data of incomes and expenses to own tables. 
	 * Gets both incomes and expenses and divides them into separate lists to display in separate tables
	 * @param Transfer [] list of the transfers
	 * 
	 */ 
	@FXML
	public void setData(Transfer[] transfers) {
		
		ArrayList<Transfer> incomes = new ArrayList<Transfer>();
		ArrayList<Transfer> expences = new ArrayList<Transfer>();
		for (Transfer t: transfers) {
			//chooses only incomes from the list
			if (t.isIncome() == true) {
				incomes.add(t);
			} 
			//chooses only expenses from the list
			else {
				expences.add(t);
			}
		}
		Transfer[] incomesArr = new Transfer[incomes.size()];
		incomes.toArray(incomesArr);
		Transfer[] expencesArr = new Transfer[expences.size()];
		expences.toArray(expencesArr);
		
		incomeTable.setItems(FXCollections.observableArrayList(incomesArr));
		expenseTable.setItems(FXCollections.observableArrayList(expencesArr));
	}

	
}
