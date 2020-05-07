package view.economy;

import java.io.IOException;
import java.sql.Date;

import controller.EconomyController;
import controller.InputCheck;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import res.MyBundle;

/**
 * GUI class relating to the economy's saving's add section
 */
public class EconomyAddSavingGUI {
	
	/**
	 * MyBundle object for setting the right resource bundle to localize the application
	 */
	private MyBundle myBundle = new MyBundle();
	/**
	 * The reference of InputCheck class used for checking user's input
	 */
	private InputCheck inputCheck = new InputCheck();
	
	/**
	 * The list view from where adding, editing and deleting can be started
	 */
	@FXML
	private AnchorPane addSavingPane;
	
	/**
	 * The reference of TextField (saving's description) will be injected by the FXML loader
	 */
	@FXML
	private TextField savingDescription;
	
	/**
	 * The reference of TextField (saving's goal amount) will be injected by the FXML loader
	 */
	@FXML
	private TextField savingAmount;
	
	/**
	 * The reference of DatePicker (saving's goal date) will be injected by the FXML loader
	 */
	@FXML
	private DatePicker savingDate;
	
	/**
	 * Reference to the used EconomyController that gets this EconomyAddSavingsGUI as a parameter
	 */
	private EconomyController controller = new EconomyController(this);

	/** 
	 * Method that gets input from savingDescription TextField and returns it
	 * @return String description of the saving
	 */
	@FXML
	public String getDescription(){
		return this.savingDescription.getText();
		}
	
	/** 
	 * Method that gets the chosen date from savingDate DatePicker and returns it
	 * @return Date date of the saving's goal
	 * @return null if date is not selected 
	 */
	@FXML
	public Date getSavingDay() {
		try {
			return java.sql.Date.valueOf(savingDate.getValue());
		}
		catch(NullPointerException e) {
			return null;
		}
	}
	
	/** 
	 * Method that gets the value from savingAmount TextField and returns it in float format
	 * @return Float amount of the saving
	 */
	@FXML
	public float getSavingAmount() {
		return Float.parseFloat(savingAmount.getText());
	}
	
	/**
	 * Method that tells controller to save the added saving if the input can be transfered into float
	 * and displays the view of the list of savings in the Economy section after adding new saving
	 */
	@FXML
	public void saveSaving() {
		//check if user has given a number as the amount
		if(inputCheck.isInputFloat(savingAmount.getText())) {
			//check if there is a name of the saving's goal given
			if(!inputCheck.isInputEmpty(savingDescription.getText())) {
				//save new saving goal
				controller.saveNewSaving();
				//show tableview with the saving goals
				AnchorPane savingsView = null; 
				FXMLLoader loaderSavingsView  = new FXMLLoader(getClass().getResource("/view/economy/EconomySavings.fxml")); 
				loaderSavingsView .setResources(myBundle.getBundle());
				try {
					savingsView = loaderSavingsView.load();
				} catch (IOException e) {
					e.printStackTrace();
				}
				addSavingPane.getChildren().setAll(savingsView);
			}
			else {
				inputCheck.alertInputEmpty();
			}
	}
		else {
			inputCheck.alertInputNotFloat();
		}	
	}
	
	/**
	 * Method that displays the view of the list of savings in the Economy section if adding new saving is canceled
	 */
	@FXML
	public void cancelAdding() {
		AnchorPane savingView = null; 
		FXMLLoader loaderSavingsView  = new FXMLLoader(getClass().getResource("/view/economy/EconomySavings.fxml")); 
		loaderSavingsView .setResources(myBundle.getBundle());
		try {
			savingView = loaderSavingsView.load();
		} catch (IOException e) {
			e.printStackTrace();
		}
			addSavingPane.getChildren().setAll(savingView);
		}
	
}
