package controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Observable;

import org.apache.commons.io.FileUtils;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Language;
import model.LanguageDAO;
import res.MyTab;
import view.settings.SettingsGUI;

public class SettingsController extends Observable{
	public static final SettingsController single = new SettingsController();
	SettingsGUI settingsGUI;
	LanguageDAO langDAO = new LanguageDAO();

	//MyBundle myBundle = new MyBundle();
	
	Language english = new Language(1,"English", true);
	Language finnish = new Language(2, "Finnish", false);

	private SettingsController() {
		
	}
	
	public static SettingsController getInstance() {
		return single;
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

	public void updateChoice(String newLangName) {
		Language oldLang = langDAO.getSelectedLanguage();
		oldLang.setChosen(false);
		langDAO.updateLanguage(oldLang);
		Language newLang = langDAO.readLanguage(newLangName);
		newLang.setChosen(true);
		langDAO.updateLanguage(newLang); 
		this.setChanged();
		this.notifyObservers();
		//updating tabs' names
		MyTab.getMyTab().setTabName();
		
		//calendarfx uses messages-named file, here we copy the file with the needed language into this file
		File enFile = new File("./com/calendarfx/view/messages_en");
		File fiFile = new File("./com/calendarfx/view/messages_fi");
		File messages = new File("./com/calendarfx/view/messages");
		if (newLang.getDescription().equals("Finnish")) {
			try {
				FileUtils.copyFile(fiFile, messages);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
			}
		}
		else {
			try {
				FileUtils.copyFile(enFile, messages);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
			}
		}
	}

	public String getSelectedLanguage() {
		Language selectedLanguage = langDAO.getSelectedLanguage();
		//none of the languages is selected
		if(selectedLanguage == null) {
			//no languages in the database yet
			if(langDAO.readLanguages().length==0) {
				createLanguages();
			}
			//english is the default language
			return english.getDescription();
		}
		return langDAO.getSelectedLanguage().getDescription();
	}
	
	private void createLanguages() {
		langDAO.createLanguage(english);
		langDAO.createLanguage(finnish);
	}

}
