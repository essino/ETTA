package controller;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class InputCheck {
	
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

	public void alertInputNotFloat() {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Error");
		alert.setHeaderText("Wrong input type");
		alert.setContentText("Give the amount in numbers");
		alert.showAndWait();
	}
}
