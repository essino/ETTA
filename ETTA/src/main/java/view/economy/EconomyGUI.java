package view.economy;

import java.io.IOException;
import java.sql.Date;

import org.hibernate.mapping.Value;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import controller.EconomyController;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

public class EconomyGUI {
	
	EconomyController controller;
	
	/**
	 * The menu view to which the alternative views in the Economy section are added
	 */
	@FXML
	BorderPane economyrootborderpane;
	
	/**
	 * The list view from where adding, editing and deleting can be started in incomes
	 */
	@FXML
	AnchorPane economyincomeanchorpane;
	
	

	
	/**
	 * The list view from where adding, editing and deleting can be started in savings
	 */
	@FXML
	AnchorPane economysavingsanchorpane;
	
	/**
	 * The list view from where adding, editing and deleting incomes
	 */
	@FXML
	AnchorPane economyincomeaddanchorpane;
	

	
	@FXML
	TextField description;
	
	@FXML
	TextField incomeAmount;
	
	@FXML
	ComboBox category;
	
	@FXML
	DatePicker incomeDate;
	
	public EconomyGUI() {
	}
	
	
	/**
	 * Method showing the balance view in the Economy items section
	 * @param event ActionEvent that is handled
	 */
	@FXML
	public void showBalance(ActionEvent event) {
		AnchorPane showBalanceView = null; //Luon anchorpanin showBalanceView
		FXMLLoader loaderBalanceView  = new FXMLLoader(getClass().getResource("/view/economy/EconomyBalanceOverview.fxml")); //haen tiedot anchorpaniin
		try {
			showBalanceView = loaderBalanceView.load();
			} catch (IOException e) {
			// TODO Auto-generated catch block
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
		try {
			showIncomeView = loaderIncomeView.load();
			} catch (IOException e) {
			// TODO Auto-generated catch block
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
		try {
			showExpensesView = loaderExpensesView.load();
			} catch (IOException e) {
			// TODO Auto-generated catch block
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
		try {
			showSavingsView = loaderSavingsView.load();
			} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			}
		economyrootborderpane.setCenter(showSavingsView);
	}
	
	
	
	
	
	
	/**
	 * Method showing the view of the Add Income in the Income items section
	 * @param event ActionEvent that is handled
	 */
	

	
	@FXML
	public void showAddIncome(ActionEvent event) {
		AnchorPane showAddIncomeView = null; 
		FXMLLoader loaderAddIncomeView  = new FXMLLoader(getClass().getResource("/view/economy/EconomyAddIncome2.fxml")); 
		try {
			showAddIncomeView = loaderAddIncomeView.load();
			} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			}
		economyincomeanchorpane.getChildren().setAll(showAddIncomeView);
	}
	

	@FXML
	public String getDescription() {
		return this.description.getText();
	}
	
	@FXML
	public float getIncomeAmount() {
		float incomeAmount = Float.parseFloat(this.incomeAmount.getText());
		return incomeAmount;
	}
	
	@FXML
	public Date getIncomeDate() {
		return Date.valueOf(this.incomeDate.getValue());
	}
	
	@FXML
	public String getCategory() {
		return category.getValue().toString();
	}

}
