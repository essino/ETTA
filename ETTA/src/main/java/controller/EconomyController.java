package controller;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

public class EconomyController {
	@FXML
	BorderPane economyrootborderpane;
	
	@FXML
	public void showBalance(ActionEvent event) {
		AnchorPane showBalanceView = null; //Luon anchorpanin showBalanceView
		FXMLLoader loaderBalanceView  = new FXMLLoader(getClass().getResource("/view/BalanceOverview.fxml")); //haen tiedot anchorpaniin
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
		FXMLLoader loaderIncomeView  = new FXMLLoader(getClass().getResource("/view/Income.fxml")); 
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
		FXMLLoader loaderExpenciesView  = new FXMLLoader(getClass().getResource("/view/Outcome.fxml")); 
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
		FXMLLoader loaderSavingsView  = new FXMLLoader(getClass().getResource("/view/Savings.fxml")); 
		try {
			showSavingsView = loaderSavingsView.load();
			} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			}
		economyrootborderpane.setCenter(showSavingsView);
	}
	
	

}