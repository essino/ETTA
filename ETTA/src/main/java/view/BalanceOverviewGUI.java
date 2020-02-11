package view;

import controller.EconomyController;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

/**
 * GUI class relating to the Balance main page section
 */
public class BalanceOverviewGUI {
	EconomyController controller; 

	@FXML 
	// The reference of label will be injected by the FXML loader 
	private Label amountBalance; 
	
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

	
	@FXML 
	public void initialize() { 
		controller.getBalance(); 
	}
}
