package view;

import java.io.IOException;
import java.sql.Date;

import controller.EconomyController;
import controller.InputCheck;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import model.CategoryDAO;

/**
 * GUI class relating to the Expense adding section of economy page
 */
public class EconomyAddOutcomeGUI {
	
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
	AnchorPane addOutcomePane;

	/**
	 * The reference of TextField (expense's description) will be injected by the FXML loader
	 */
	@FXML
	private TextField outcomeDescription;
	
	/**
	 * The reference of TextField (expense's amount) will be injected by the FXML loader
	 */
	@FXML
	private TextField outcomeAmount;
	
	/**
	 * The reference of ChoiceBox (expense's categories list) will be injected by the FXML loader
	 */
	@FXML
	private ChoiceBox<String> outcomeCategoryList;
	
	/**
	 * The reference of DatePicker (expenses) will be injected by the FXML loader
	 */
	@FXML
	private DatePicker outcomeDate;
	
	EconomyController controller = new EconomyController(this);
	
	/** 
	 * Method that initializes the view - gets the categories from the EconomyController and displays them in the drop down list
	 */
	@FXML
	public void initialize() {
		outcomeCategoryList.getItems().addAll(controller.categoriesList());
	}
	
	/** 
	 * Method that gets input from outcomeDescription TextField and returns it
	 * @return String description of the expense
	 */
	@FXML
	public String getDescription(){
		return this.outcomeDescription.getText();
		}
	
	/** 
	 * Method that gets the chosen option from outcomeCategoryList ChoiceBox and returns it
	 * @return String category of the expense
	 */
	@FXML
	public String getCategoryName(){
		return this.outcomeCategoryList.getValue();
		}
	
	/** 
	 * Method that gets the chosen date from outcomeDate DatePicker and returns it
	 * @return Date date of the expense
	 * @return null if date is not selected ---??? onko edes mahdollista?
	 */
	@FXML
	public Date getExpenseDay() {
		try {
		return java.sql.Date.valueOf(outcomeDate.getValue());
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
	public float getExpenseAmount() {
		return Float.parseFloat(outcomeAmount.getText());
	}
	
	/**
	 * Method that tells controller to save the added expense if the input can be transfered into float
	 * and displays the view of the list of expenses in the Economy section after adding new expense 
	 */
	@FXML
	public void saveExpense() {
		if(inputCheck.isInputFloat(outcomeAmount.getText())) {
			controller.saveExpense();
			AnchorPane outcomeView = null; 
			FXMLLoader loaderOutcomeView  = new FXMLLoader(getClass().getResource("/view/EconomyOutcome.fxml")); 
			try {
				outcomeView = loaderOutcomeView.load();
				} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				}
			addOutcomePane.getChildren().setAll(outcomeView);
		}
		else {
			inputCheck.alertInputNotFloat();
		}	
	}
	
	/**
	 * Method that displays the view of the list of expenses in the Economy section if adding new expense is canceled
	 */
	@FXML
	public void cancelAdding() {
		AnchorPane outcomeView = null; 
		FXMLLoader loaderOutcomeView  = new FXMLLoader(getClass().getResource("/view/EconomyOutcome.fxml")); 
		try {
			outcomeView = loaderOutcomeView.load();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			addOutcomePane.getChildren().setAll(outcomeView);
		}
	
}
