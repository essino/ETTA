package view;

import org.joda.time.LocalDate;

import controller.EconomyController;
import controller.MainViewController;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class BalanceOverviewGUI {

	EconomyController controller;
	
	@FXML
	// The reference of label will be injected by the FXML loader
	private Label amountBalance;
	
	public BalanceOverviewGUI() {
		controller = new EconomyController(this);
	}
	

	public void setBalance(double amount) {
		String balanceString = String.format("%.2f", amount);
		amountBalance.setText(balanceString);
	}
	
	@FXML
	public void initialize() {
		controller.getBalance();
	}
}
