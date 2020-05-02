package view.settings;

import controller.InputCheck;
import controller.SettingsController;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import res.MyBundle;

public class SettingsGUI {
	
	/**
	 * The Anchor pane for settings
	 */
	@FXML
	AnchorPane settingsAnchorpane;
	
	/**
	 * The reference of ComboBox (languages list) will be injected by the FXML loader
	 */
	@FXML
	private ComboBox<String> languageList;
	
	/**
	 * The language that is in use
	 */
	@FXML
	private Label chosenLanguage;
	
	/**
	 * The label next to which the chosen language appears
	 */
	@FXML
	private Label chosenLanguageLabel;
	
	/**
	 * The label next to the Combo box in which the the language is chosen
	 */
	@FXML
	private Label language;
	
	@FXML
	private Button buttonSaveLang;
	
	@FXML
	private Button buttonCancelLang;
	
	/**
	 * The settings controller 
	 */
	SettingsController controller = SettingsController.getInstance();
	
	/**
	 * MyBundle object for setting the right resource bundle to localize the application
	 */
	MyBundle myBundle = new MyBundle();
	
	/**
	 * Input check for making sure the user makes appropriate choices
	 */
	InputCheck check = new InputCheck();
	
	@FXML
	public void cancel() {
		
	}
	
	@FXML
	public void saveLanguage() {
		String lang = languageList.getSelectionModel().getSelectedItem();
		if (lang == null) {
			check.alertNothingSelectedLanguage();
		} else {
			controller.updateChoice(lang);
			chosenLanguage.setText(controller.getSelectedLanguage());
			chosenLanguageLabel.setText(myBundle.getBundle().getString("settingsChosenLanguageLabel"));
			language.setText(myBundle.getBundle().getString("settingsLanguageLabel"));
			buttonSaveLang.setText(myBundle.getBundle().getString("buttonSave"));
			buttonCancelLang.setText(myBundle.getBundle().getString("buttonCancel"));
		}
	}
	
	@FXML 
	public void initialize() { 
		languageList.getItems().addAll(controller.languageList());
		chosenLanguage.setText(controller.getSelectedLanguage());
	}
}
