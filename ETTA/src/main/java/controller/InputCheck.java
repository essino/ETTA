package controller;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;


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
	
	
	//TODO: add checking for Min and MAx values for integers and Floats
	//TODO: add checking for empty strings
}
