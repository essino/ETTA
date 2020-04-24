package view.economy;

import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;

import org.hibernate.mapping.Value;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.util.converter.FloatStringConverter;
import model.Transfer;
import res.MyBundle;
import controller.EconomyController;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;

public class EconomyGUI {
	
	EconomyController controller;
	
	MyBundle myBundle = new MyBundle();
	
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
	/**
	 * The reference of TableView (incomes) will be injected by the FXML loader
	 */
	@FXML
    private TableView<Transfer> incomeTable;
	
	  /**
  	 * The reference of TableView (expenses) will be injected by the FXML loader
  	 */
  	@FXML
      private TableView<Transfer> expenseTable;

	
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
		loaderBalanceView .setResources(myBundle.getBundle());
		
	
		
		//Method searching information of the last seven days on incomes items section
		/*LocalDate endDate = LocalDate.now();
		LocalDate startDate = endDate.minusDays(6);
		//LocalDate startDate = endDate.minus(7);
		System.out.println(startDate);
		System.out.println(endDate);
		controller.getSeletedIncomes(java.sql.Date.valueOf(startDate), java.sql.Date.valueOf(endDate));*/
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
		loaderIncomeView .setResources(myBundle.getBundle());
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
		loaderExpensesView .setResources(myBundle.getBundle());
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
		loaderSavingsView .setResources(myBundle.getBundle());
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
	
	/** 
	 * Method set to data controller
	 */
	@FXML
	public void setData(Transfer[] readSeletedTransfers) {
		incomeTable.setItems(FXCollections.observableArrayList(readSeletedTransfers));
		
	}
	

}
