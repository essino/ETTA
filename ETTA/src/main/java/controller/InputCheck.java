package controller;

import java.time.LocalDate;
import java.util.Date;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import model.Person;

/** 
 * Class for the checking the input of the user.  
 * 
 */
public class InputCheck {
	
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
		alert.setTitle("Error");
		alert.setHeaderText("Wrong input type");
		alert.setContentText("Give the amount in numbers");
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
		alert.setTitle("Error");
		alert.setHeaderText("Input can't be empty");
		alert.setContentText("Give the needed information");
		alert.showAndWait();
	}
	//TODO: add checking for Min and MAx values for integers and Floats
	
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
	
	public void alertDatesWrong() {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Error");
		alert.setHeaderText("The return date is before the loan date.");
		alert.setContentText("Change the dates.");
		alert.showAndWait();
	}
	
	/** 
	 * Method that checks if loan date and return dates are ok
	 * @param loanDate when the item is borrowed
	 * @param returnDate when the item is returned
	 * @return b boolean showing if the dates are ok
	 */ 
	public boolean isDateEmpty(LocalDate date) {
	    Boolean b = false;
	    if (date == null) {
	        b = true;
	    }
	    return b;
	}
	
}


