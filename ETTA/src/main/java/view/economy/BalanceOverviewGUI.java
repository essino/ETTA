package view.economy;

import java.util.Locale;
import java.util.ResourceBundle;

import controller.EconomyController;
import controller.InputCheck;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

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
}
