package view.settings;

import controller.SettingsController;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import res.MyBundle;

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
	private Label chosenLanguage;
	
	@FXML
	private Label chosenLanguageLabel;
	
	@FXML
	private Label language;
	
	SettingsController controller = new SettingsController(this);
	
	MyBundle myBundle = new MyBundle();
	
	@FXML
	public void cancel() {
		
	}
	
	@FXML
	public void saveLanguage() {
		controller.updateChoice(languageList.getSelectionModel().getSelectedIndex());
		chosenLanguage.setText(controller.getSelectedLanguage());
		chosenLanguageLabel.setText(myBundle.getBundle().getString("settingsChosenLanguageLabel"));
		language.setText(myBundle.getBundle().getString("settingsLanguageLabel"));
	}
	
	@FXML 
	public void initialize() { 
		languageList.getItems().addAll(controller.languageList());
		chosenLanguage.setText(controller.getSelectedLanguage());
	}
}
