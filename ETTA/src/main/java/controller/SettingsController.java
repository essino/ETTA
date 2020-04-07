package controller;

import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Category;
import model.Language;
import model.LanguageDAO;
import view.settings.SettingsGUI;

public class SettingsController {
	SettingsGUI settingsGUI;
	LanguageDAO langDAO = new LanguageDAO();

	public SettingsController(SettingsGUI settingsGUI) {
		this.settingsGUI=settingsGUI;
	}

	public ObservableList<String> languageList() {
		Language[] langs = langDAO.readLanguages();
		ArrayList<String> langNames = new ArrayList();
		for (Language lang : langs){
			langNames.add(lang.getDescription());
		}
		ObservableList<String> names =  FXCollections.observableArrayList(langNames);
		return names;
	}

}
