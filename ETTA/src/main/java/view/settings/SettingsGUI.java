package view.settings;

import controller.SettingsController;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.AnchorPane;

public class SettingsGUI {
	/**
	 * The list view from where adding, editing and deleting can be started
	 */
	@FXML
	AnchorPane settingsAnchorpane;
	
	/**
	 * The reference of ChoiceBox (languages list) will be injected by the FXML loader
	 */
	@FXML
	private ComboBox<String> languageList;
	
	SettingsController controller = new SettingsController(this);
	
	@FXML
	public void cancel() {
		
	}
	
	@FXML
	public void saveLanguage() {
		controller.updateChoice(languageList.getSelectionModel().getSelectedIndex());
		
	}
	
	@FXML 
	public void initialize() { 
		languageList.getItems().addAll(controller.languageList());
	}
}
