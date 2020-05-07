package view.economy;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import res.MyBundle;

/**
 * GUI class that is in charge of displaying the right fxml file depending on the needed part of the economy part of the app
 */
public class EconomyGUI {
	
	/**
	 * MyBundle object for setting the right resource bundle to localize the application
	 */
	MyBundle myBundle = new MyBundle();
	
	/**
	 * The menu view to which the alternative views in the Economy section are added
	 */
	@FXML
	BorderPane economyrootborderpane;
	
	public EconomyGUI() {
	}
	
	/**
	 * Method showing the balance view in the Economy items section
	 * @param event ActionEvent that is handled
	 */
	@FXML
	public void showBalance(ActionEvent event) {
		AnchorPane showBalanceView = null; 
		FXMLLoader loaderBalanceView  = new FXMLLoader(getClass().getResource("/view/economy/EconomyBalanceOverview.fxml")); 
		loaderBalanceView .setResources(myBundle.getBundle());
		try {
			showBalanceView = loaderBalanceView.load();
			} catch (IOException e) {
			e.printStackTrace();
			}
		economyrootborderpane.setCenter(showBalanceView);
	}
	
	/**
	 * Method showing the Income view in the Economy items section
	 * @param event ActionEvent that is handled
	 */
	@FXML
	public void showIncome(ActionEvent event) {
		AnchorPane showIncomeView = null; 
	
		FXMLLoader loaderIncomeView  = new FXMLLoader(getClass().getResource("/view/economy/EconomyIncome.fxml")); 
		loaderIncomeView .setResources(myBundle.getBundle());
		try {
			showIncomeView = loaderIncomeView.load();
			} catch (IOException e) {
			e.printStackTrace();
			}
		economyrootborderpane.setCenter(showIncomeView);
	}
	
	/**
	 * Method showing the Expenses view in the Economy items section
	 * @param event ActionEvent that is handled
	 */
	@FXML
	public void showExpenses(ActionEvent event) {
		AnchorPane showExpensesView = null; 
		FXMLLoader loaderExpensesView  = new FXMLLoader(getClass().getResource("/view/economy/EconomyOutcome.fxml")); 
		loaderExpensesView .setResources(myBundle.getBundle());
		try {
			showExpensesView = loaderExpensesView.load();
			} catch (IOException e) {
			e.printStackTrace();
			}
		economyrootborderpane.setCenter(showExpensesView);
	}
	
	/**
	 * Method showing the Savings view in the Economy items section
	 * @param event ActionEvent that is handled
	 */
	@FXML
	public void showSavings(ActionEvent event) {
		AnchorPane showSavingsView = null; 
		FXMLLoader loaderSavingsView  = new FXMLLoader(getClass().getResource("/view/economy/EconomySavings.fxml")); 
		loaderSavingsView .setResources(myBundle.getBundle());
		try {
			showSavingsView = loaderSavingsView.load();
			} catch (IOException e) {
			e.printStackTrace();
			}
		economyrootborderpane.setCenter(showSavingsView);
	}

}
