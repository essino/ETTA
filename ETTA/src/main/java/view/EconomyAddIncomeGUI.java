package view;

import java.io.IOException;
import java.sql.Date;

import controller.EconomyController;
import controller.InputCheck;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import model.CategoryDAO;

public class EconomyAddIncomeGUI {
	
	/**
	 * The reference of InputCheck class used for checking user's input
	 */
	InputCheck inputCheck = new InputCheck();
	
	/**
	 * CategoryDAO used for accessing the database
	 */
	CategoryDAO categoryDAO = new CategoryDAO();
	
	/**
	 * The list view from where adding, editing and deleting can be started
	 */
	@FXML
	AnchorPane economyincomeaddanchorpane;

	/**
	 * The reference of TextField (income's description) will be injected by the FXML loader
	 */
	@FXML
	private TextField incomeDescription;
	
	/**
	 * The reference of TextField (income's amount) will be injected by the FXML loader
	 */
	@FXML
	private TextField incomeAmount;
	
	/**
	 * The reference of ChoiceBox (incomes's categories list) will be injected by the FXML loader
	 */
	@FXML
	private ComboBox<String> incomeCategoryList;
	
	
	public EconomyAddIncomeGUI() {
		
	}
	
	/**
	 * The reference of DatePicker (incomes) will be injected by the FXML loader
	 */

	@FXML
	private DatePicker incomeDate;
	
	EconomyController controller = new EconomyController(this);
	
	/** 
	 * Method that initializes the view - gets the categories from the EconomyController and displays them in the drop down list
	 */
	@FXML
	public void initialize() {
		incomeCategoryList.getItems().addAll(controller.categoriesList());
	}
	
	/** 
	 * Method that gets input from outcomeDescription TextField and returns it
	 * @return String description of the expense
	 */
	@FXML
	public String getDescription(){
		return this.incomeDescription.getText();
		}
	
	/** 
	 * Method that gets the chosen option from outcomeCategoryList ChoiceBox and returns it
	 * @return String category of the expense
	 */
	@FXML
	public String getCategoryName(){
		return this.incomeCategoryList.getValue();
		}
	
	/** 
	 * Method that gets the chosen date from outcomeDate DatePicker and returns it
	 * @return Date date of the expense
	 * @return null if date is not selected ---??? onko edes mahdollista?
	 */
	@FXML
	public Date getIncomeDay() {
		try {
		return java.sql.Date.valueOf(incomeDate.getValue());
		}
		catch(NullPointerException e) {
			return null;
		}
	}
	
	/** 
	 * Method that gets the value from outcomeAmount TextField and returns it in float format
	 * @return Float amount of the expense
	 */
	@FXML
	public float getIncomeAmount() {

	

		return Float.parseFloat(incomeAmount.getText());
	}

	/**
	 * Method that tells controller to save the added income if the input can be transfered into float
	 * and displays the view of the list of incomes in the Economy section after adding new income 
	 */
	@FXML
	public void AddNewIncome() {
		if(inputCheck.isInputFloat(incomeAmount.getText())) {
			controller.saveIncome();
			AnchorPane incomeView = null; 
			FXMLLoader loaderIncomeView  = new FXMLLoader(getClass().getResource("/view/EconomyIncome.fxml")); 
			try {
				incomeView = loaderIncomeView.load();
				} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				}
			economyincomeaddanchorpane.getChildren().setAll(incomeView);
		}
		else {
			inputCheck.alertInputNotFloat();
		}	
	}
	
	/**
	 * Method that displays the view of the list of incomes in the Economy section if adding new income is canceled
	 */
	@FXML
	public void cancelAdding() {
		AnchorPane incomeView = null; 

		FXMLLoader loaderIncomeView  = new FXMLLoader(getClass().getResource("/view/EconomyIncome.fxml")); 

		try {
			incomeView = loaderIncomeView.load();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		economyincomeaddanchorpane.getChildren().setAll(incomeView);
		}

}
