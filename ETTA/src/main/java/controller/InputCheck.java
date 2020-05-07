package controller;

import java.time.LocalDate;
import java.util.Date;
import java.util.Optional;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import model.Person;
import res.MyBundle;

/** 
 * Class for the checking the input of the user.  
 * 
 */
public class InputCheck {
	
	/**
	 * The used resource bundle
	 */
	MyBundle myBundle = new MyBundle();
	
	/** 
	 * Method that checks if user input can be transformed into float
	 * @param input string inputed by the user
	 * @return b boolean showing if the transform into float succeeded
	 */ 
	public boolean isInputFloat(String input) {
	    Boolean b= false;
	    if (!(input == null || input.length() == 0)) {
	        try {
	            Float f = Float.parseFloat(input);  
	            b=true;
	        } 
	        catch (NumberFormatException e) {  
	        }
	    }
	    return b;
	}
	
	/** 
	 * Method that alerts that user input can not be transformed into float
	 */
	public void alertInputNotFloat() {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle(myBundle.getBundle().getString("checkErrorTitle"));
		alert.setHeaderText(myBundle.getBundle().getString("checkInputTypeHeader"));
		alert.setContentText(myBundle.getBundle().getString("checkInputTypeContent"));
		alert.showAndWait();
	}
	
	/** 
	 * Method that checks if user input is empty
	 * @param input string inputed by the user
	 * @return b boolean showing if the input is empty
	 */ 
	public boolean isInputEmpty(String input) {
	    Boolean b= false;
	    if (input == null || input.length() == 0 || (input.trim()).length()==0) { 
	        b=true;
	    }
	    return b;
	}
	
	/** 
	 * Method that alerts that user input is empty
	 */
	public void alertInputEmpty() {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle(myBundle.getBundle().getString("checkErrorTitle"));
		alert.setHeaderText(myBundle.getBundle().getString("checkInputEmptyHeader"));
		alert.setContentText(myBundle.getBundle().getString("checkInputEmptyContent"));
		alert.showAndWait();
	}
	
	/** 
	 * Method that checks if loan date is before return data
	 * @param loanDate when the item is borrowed
	 * @param returnDate when the item is returned
	 * @return b boolean showing if the loan date is before return Date
	 */ 
	public boolean dateCheck(Date loanDate, Date returnDate) {
	    Boolean b = false;
	    if (loanDate == null || returnDate.after(loanDate) || returnDate.equals(loanDate)) {
	        b = true;
	    }
	    return b;
	}
	
	/** 
	 * Method that alerts that the loan date is after the return date
	 */
	public void alertDatesWrong() {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle(myBundle.getBundle().getString("checkErrorTitle"));
		alert.setHeaderText(myBundle.getBundle().getString("checkDatesHeader"));
		alert.setContentText(myBundle.getBundle().getString("checkDatesContent"));
		alert.showAndWait();
	}
	
	/** 
	 * Method that checks if a date is empty
	 * @param date date in question
	 * @return b boolean showing if the date is empty
	 */ 
	public boolean isDateEmpty(LocalDate date) {
	    Boolean b = false;
	    if (date == null) {
	        b = true;
	    }
	    return b;
	}
	
	/** 
	 * Method that asks the user to confirm deletion of data
	 * @return delete boolean indicating it's okay to delete something
	 */
	public boolean confirmDeleting() {
		boolean delete = false;
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle(myBundle.getBundle().getString("checkConfirmationTitle"));
		alert.setHeaderText(myBundle.getBundle().getString("checkDeletionHeader"));
		alert.setContentText(myBundle.getBundle().getString("checkDeletionContent"));
		
		ButtonType buttonOK = new ButtonType("OK", ButtonData.YES);
		ButtonType buttonTypeCancel = new ButtonType(myBundle.getBundle().getString("buttonCancel"), ButtonData.CANCEL_CLOSE);
		alert.getButtonTypes().setAll(buttonOK, buttonTypeCancel);
		
		Optional<ButtonType> result = alert.showAndWait();
		if (result.isPresent() && result.get() == buttonOK) {
			delete = true;
		 }
		return delete;
	}
	
	/** 
	 * Method that asks the user to confirm that they want to mark an item as returned
	 * @return wantReturn boolean indicating it's okay (or not) to mark an item as returned
	 */
	public boolean confirmReturn() {
		boolean wantReturn = false;
		Alert alert = new Alert(AlertType.CONFIRMATION);
		
		alert.setTitle(myBundle.getBundle().getString("checkConfirmationTitle"));
		alert.setHeaderText(myBundle.getBundle().getString("checkReturnHeader"));
		alert.setContentText(myBundle.getBundle().getString("checkReturnContent"));
		
		ButtonType buttonOK = new ButtonType("OK", ButtonData.YES);
		ButtonType buttonTypeCancel = new ButtonType(myBundle.getBundle().getString("buttonCancel"), ButtonData.CANCEL_CLOSE);
		alert.getButtonTypes().setAll(buttonOK, buttonTypeCancel);
		
		Optional<ButtonType> result = alert.showAndWait();
		if (result.isPresent() && result.get() == buttonOK) {
			wantReturn = true;
		 } else {
			wantReturn = false; 
		 }
		return wantReturn;
	}
	
	/** 
	 * Method that alerts that there is not enough balance
	 */
	public void alertNotEnoughBalance() {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle(myBundle.getBundle().getString("checkErrorTitle"));
		alert.setHeaderText(myBundle.getBundle().getString("checkBalanceHeader"));
		alert.setContentText(myBundle.getBundle().getString("checkBalanceContent"));
		alert.showAndWait();
	}

	/** 
	 * Method that alerts in case the user is about to add a person who is already on the contact list
	 */
	public void alertPersonExists() {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle(myBundle.getBundle().getString("checkErrorTitle"));
		alert.setHeaderText(myBundle.getBundle().getString("checkPersonHeader"));
		alert.setContentText(myBundle.getBundle().getString("checkPersonContent"));
		alert.showAndWait();
	}

	/** 
	 * Method that asks the user to confirm that the saving goal has been achieved
	 * @return delete boolean indicating it's okay (or not) to delete the saving goal from the list
	 */
	public boolean confirmSavingAchieved() {
		boolean delete = false;
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle(myBundle.getBundle().getString("checkConfirmationTitle"));
		alert.setHeaderText(myBundle.getBundle().getString("checkSavingHeader"));
		alert.setContentText(myBundle.getBundle().getString("checkSavingContent"));
		
		ButtonType buttonOK = new ButtonType("OK", ButtonData.YES);
		ButtonType buttonTypeCancel = new ButtonType(myBundle.getBundle().getString("buttonCancel"), ButtonData.CANCEL_CLOSE);
		alert.getButtonTypes().setAll(buttonOK, buttonTypeCancel);
		
		Optional<ButtonType> result = alert.showAndWait();
		if (result.isPresent() && result.get() == buttonOK) {
			delete = true;
		 }
		return delete;
	}
	
	/** 
	 * Method that alerts the user that nothing is selected in the table
	 */
	public void alertNothingSelected() {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle(myBundle.getBundle().getString("checkErrorTitle"));
		alert.setHeaderText(myBundle.getBundle().getString("checkNothingSelectedHeader"));
		alert.setContentText(myBundle.getBundle().getString("checkNothingSelectedContent"));
		alert.showAndWait();
	}
	
	/** 
	 * Method that alerts the user that nothing is selected in a combo box
	 */
	public void alertNothingComboBox() {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle(myBundle.getBundle().getString("checkErrorTitle"));
		alert.setHeaderText(myBundle.getBundle().getString("checkNothingSelectedHeader"));
		alert.setContentText(myBundle.getBundle().getString("checkNothingSelectedCombo"));
		alert.showAndWait();
	}
	
	/** 
	 * Method that creates the pop-up in which the marking an item bought is confirmed
	 * @return confirmed boolean indicating if the user confirms the action or not
	 */
	public boolean confrimMarkBought() {
		boolean confirmed = false;
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle(myBundle.getBundle().getString("checkConfirmationTitle"));
		alert.setHeaderText(myBundle.getBundle().getString("checkMarkBoughtHeader"));
		alert.setContentText(myBundle.getBundle().getString("checkMarkBoughtContent"));
		
		ButtonType buttonOK = new ButtonType("OK", ButtonData.YES);
		ButtonType buttonTypeCancel = new ButtonType(myBundle.getBundle().getString("buttonCancel"), ButtonData.CANCEL_CLOSE);
		alert.getButtonTypes().setAll(buttonOK, buttonTypeCancel);
		
		Optional<ButtonType> result = alert.showAndWait();
		if (result.isPresent() && result.get() == buttonOK) {
			confirmed = true;
		 }
		return confirmed;
	}
}


