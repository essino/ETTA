package controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Locale;

import org.apache.commons.io.FileUtils;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Language;
import model.LanguageDAO;
import res.MyTab;

public class SettingsController {
	public static final SettingsController single = new SettingsController();

	private LanguageDAO langDAO = new LanguageDAO();

	private Locale locale = new Locale("en", "GB");
	private Locale currentLocale;
	
	Language english = new Language(1,"English", true);
	Language finnish = new Language(2, "Finnish", false);

	private SettingsController() {
		this.currentLocale= getLocaleFromDatabase();
	}
	
	public static SettingsController getInstance() {
		return single;
	}
	
	private Locale getLocaleFromDatabase() {
		if(getSelectedLanguage().equals("Finnish")) {
			return new Locale("fi", "FI");
			}
		else {
			return new Locale("en", "GB");
		}
	}
	
	public Locale getCurrentLocale() {
		return this.currentLocale;
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
		if(newLangName.equals("Finnish")) {
			this.currentLocale  = new Locale("fi", "FI");
			}
		else {
			this.currentLocale = new Locale("en", "GB");
		}
		
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
