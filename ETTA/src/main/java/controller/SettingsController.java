package controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

import org.apache.commons.io.FileUtils;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Category;
import model.Language;
import model.LanguageDAO;
import res.MyBundle;
import res.MyTab;
import view.settings.SettingsGUI;

public class SettingsController {
	SettingsGUI settingsGUI;
	LanguageDAO langDAO = new LanguageDAO();
	MyTab myTab = MyTab.getMyTab();
	MyBundle myBundle = new MyBundle();

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

	public void updateChoice(int selectedIndex) {
		Language oldLang = langDAO.unselectLanguage(true);
		Language newLang = langDAO.readLanguage(selectedIndex);
		newLang.setChosen(true);
		langDAO.selectLanguage(newLang);
		myTab.setTabName();
		
		File enFile = new File("./com/calendarfx/view/messages_en");
		File fiFile = new File("./com/calendarfx/view/messages_fi");
		try {
			System.out.println("en " + enFile.getCanonicalPath());
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			//e1.printStackTrace();
		}
		System.out.println("fi " + fiFile.getAbsolutePath());
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
				e.printStackTrace();
			}
		}
	}

}
