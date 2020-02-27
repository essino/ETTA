package view;


import controller.WishlistController;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;


public class WishlistAddGUI {
	@FXML
	ChoiceBox<String> toWhom;
	
	WishlistController controller = new WishlistController();
	
	@FXML
	public void initialize() {
			toWhom.getItems().addAll(controller.personsList());
	}
}
