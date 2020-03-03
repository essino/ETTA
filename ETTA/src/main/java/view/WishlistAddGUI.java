package view;


import controller.WishlistController;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;


public class WishlistAddGUI {
	@FXML
	ComboBox<String> toWhom;
	
	WishlistController controller = new WishlistController();
	
	@FXML
	public void initialize() {
			toWhom.getItems().addAll(controller.personsList());
	}
}
