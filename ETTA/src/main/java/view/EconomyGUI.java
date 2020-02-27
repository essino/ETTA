package view;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

public class EconomyGUI {
	
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
	 * The list view from where adding, editing and deleting can be started in expencies
	 */
	@FXML	
	AnchorPane economyoutcomeaddanchorpane;
	
	/**
	 * The list view from where adding, editing and deleting can be started in savings
	 */
	@FXML
	AnchorPane economysavingsanchorpane;
	
	/**
	 * The list view from where adding, editing and deleting incomes
	 */
	AnchorPane economyincomeaddanchorpane;
	
	/**
	 * Method showing the balance view in the Economy items section
	 * @param event ActionEvent that is handled
	 */
	
	
	
	@FXML
	public void showBalance(ActionEvent event) {
		AnchorPane showBalanceView = null; //Luon anchorpanin showBalanceView
		FXMLLoader loaderBalanceView  = new FXMLLoader(getClass().getResource("/view/EconomyBalanceOverview.fxml")); //haen tiedot anchorpaniin
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
	
		FXMLLoader loaderIncomeView  = new FXMLLoader(getClass().getResource("/view/EconomyIncome.fxml")); 
		try {
			showIncomeView = loaderIncomeView.load();
			} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			}
		economyrootborderpane.setCenter(showIncomeView);
	}
	
	/**
	 * Method showing the Expencies view in the Economy items section
	 * @param event ActionEvent that is handled
	 */
	@FXML
	public void showExpencies(ActionEvent event) {
		AnchorPane showExpenciesView = null; 
		FXMLLoader loaderExpenciesView  = new FXMLLoader(getClass().getResource("/view/EconomyOutcome.fxml")); 
		try {
			showExpenciesView = loaderExpenciesView.load();
			} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			}
		economyrootborderpane.setCenter(showExpenciesView);
	}
	
	/**
	 * Method showing the Savings view in the Economy items section
	 * @param event ActionEvent that is handled
	 */
	@FXML
	public void showSavings(ActionEvent event) {
		AnchorPane showSavingsView = null; 
		FXMLLoader loaderSavingsView  = new FXMLLoader(getClass().getResource("/view/EconomySavings.fxml")); 
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
		FXMLLoader loaderAddIncomeView  = new FXMLLoader(getClass().getResource("/view/EconomyAddIncome2.fxml")); 
		try {
			showAddIncomeView = loaderAddIncomeView.load();
			} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			}
		economyincomeanchorpane.getChildren().setAll(showAddIncomeView);
	}
	
	/**
	 * Method showing the view of the Add Expence in the Expences items section
	 * @param event ActionEvent that is handled
	 */
	@FXML
	public void showAddOutcome(ActionEvent event) {
		AnchorPane showAddOutcomeView = null;
		FXMLLoader loaderAddOutcomeView = new FXMLLoader(getClass().getResource("/view/EconomyAddOutcome.fxml"));
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
	 * Method showing the view of the Add Savings in the Savings items section
	 * @param event ActionEvent that is handled
	 */
	@FXML
	public void showAddSavings(ActionEvent event) {
		AnchorPane showAddSavingsView = null;
		FXMLLoader loaderAddSavingsView = new FXMLLoader(getClass().getResource("/view/EconomyAddSavings.fxml"));
		try {
			showAddSavingsView = loaderAddSavingsView.load();
			} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			}
		//shows the loaded fxml file
		economysavingsanchorpane.getChildren().setAll(showAddSavingsView);
		
	}
	
	@FXML
	public void AddNewIncome(ActionEvent event) {
	
		AnchorPane showAddNewIncomeView = null;
		FXMLLoader loaderAddNewIncomeView = new FXMLLoader(getClass().getResource("/view/EconomyIncome.fxml"));
		try {
			showAddNewIncomeView = loaderAddNewIncomeView.load();
			} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			}
		//shows the loaded fxml file
		economyincomeaddanchorpane.getChildren().setAll(showAddNewIncomeView);
		
		
	}
	
	
	/**
	@FXML
	public void AddIncomeCancel(ActionEvent event) {
		AnchorPane AddIncomeCancelView = null;
		FXMLLoader loaderAddIncomeCancelView = new FXMLLoader(getClass().getResource("/view/EconomyIncome.fxml"));
		
		try {
			AddIncomeCancelView = loaderAddIncomeCancelView.load();
			} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			}
		//shows the loaded fxml file
		economyincomeaddanchorpane.getChildren().setAll(AddIncomeCancelView);
		
	}
*/
}
