package view;

import org.joda.time.LocalDate;

import controller.MainViewController;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

public class MainPageGUI  {

	MainViewController controller;
	
	@FXML
	// The reference of label will be injected by the FXML loader
	private Label amountBalance;
	
	@FXML
	// The reference of label will be injected by the FXML loader
	private Label todaysDate;
	
	BorderPane borderPane;
	AnchorPane content = null;

	public MainPageGUI() {
		controller = new MainViewController(this);
	}
	

	public void setBalance(double amount) {
		String balanceString = String.format("%.2f", amount);
		amountBalance.setText(balanceString);
	}
	
	@FXML
	public void initialize() {
		controller.getBalance();
		
		LocalDate date = LocalDate.now();
		String text = date.toString();
		todaysDate.setText(text);
	}

}
