package view;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

public class EconomyGUI {

	@FXML
	BorderPane economyrootborderpane;
	@FXML
	AnchorPane economyincomeanchorpane;
	@FXML	
	AnchorPane economyoutcomeaddanchorpane;
	@FXML
	AnchorPane economysavingsanchorpane;
	
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
	
}
