package view.settings;

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
	
	@FXML
	public void cancel() {
		
	}
	
	@FXML
	public void saveLanguage() {
		
	}
}
