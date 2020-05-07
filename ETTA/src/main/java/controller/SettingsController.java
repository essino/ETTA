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

/** 
 * A singleton controller class for the language settings. 
 * @author Lena
 */
public class SettingsController {
	/**
	 * the settingsController instance
	 */
	public static final SettingsController single = new SettingsController();

	/**
	 * LanguageDAO used for accessing the database
	 */
	private LanguageDAO langDAO = new LanguageDAO();

	/**
	 * Reference to the Locale
	 */
	private Locale currentLocale;
	
	/**
	 * Language object for adding English language to the database. English is the default language, so chosen is set to true
	 */
	Language english = new Language(1,"English", true);
	/**
	 * Language object for adding Finnish language to the database. English is the default language, so chosen is set to false
	 */
	Language finnish = new Language(2, "Finnish", false);

	/**
	 * Constructor. The current Locale is decided depending on the current chosen language
	 */
	private SettingsController() {
		this.currentLocale= getLocaleFromDatabase();
	}
	
	/**
	 * method for getting the instance of the SettingsController
	 * @return SettingsController the instance of the SettingsController
	 */
	public static SettingsController getInstance() {
		return single;
	}
	
	/**
	 * Method for getting the current Locale depending on the chosen language got from the database.
	 * @return Locale the current locale depending on the chosen language
	 */
	private Locale getLocaleFromDatabase() {
		if(getSelectedLanguage().equals("Finnish")) {
			return new Locale("fi", "FI");
			}
		else {
			return new Locale("en", "GB");
		}
	}
	
	/**
	 * Method for getting the current Locale
	 * @return Locale the current Locale
	 */
	public Locale getCurrentLocale() {
		return this.currentLocale;
	}

	/**
	 * Method for fetching all languages from the database
	 * @return names -  ObservableList<String> list containing the names of the languages from the database
	 */
	public ObservableList<String> languageList() {
		Language[] langs = langDAO.readLanguages();
		ArrayList<String> langNames = new ArrayList();
		for (Language lang : langs){
			langNames.add(lang.getDescription());
		}
		ObservableList<String> names =  FXCollections.observableArrayList(langNames);
		return names;
	}

	/**
	 * Method for updating which language is chosen
	 * @param newLangName String indicating the new chosen language
	 */
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
			}
		}
		else {
			try {
				FileUtils.copyFile(enFile, messages);
			} catch (IOException e) {
			}
		}
	}

	/**
	 * Method for fetching the selected language from the database. 
	 * If there are no languages in the database yet, a method adding languages to the database is called.
	 * @return String the name of the chosen language
	 */
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
	
	/** 
	 * Method that asks LanguageDAO to add Finnish and English language to the database. 
	 * The method is called if there are no languages in the database yet. 
	 */
	private void createLanguages() {
		langDAO.createLanguage(english);
		langDAO.createLanguage(finnish);
	}

}
