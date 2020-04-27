package res;

import java.io.FileInputStream;
import java.util.Locale;
import java.util.Observable;
import java.util.Observer;
import java.util.Properties;
import java.util.ResourceBundle;

import controller.SettingsController;
import model.Language;
import model.LanguageDAO;

public class MyBundle implements Observer{
	public static final MyBundle single = new MyBundle();
	LanguageDAO langDao = new LanguageDAO();
	Locale locale;
	ResourceBundle bundle;
	SettingsController controller;
	
	private MyBundle() {
		this.bundle = getLanguage();
		this.controller = SettingsController.getInstance();
		System.out.println(this.controller.getSelectedLanguage());
		this.controller.addObserver(this);
	}

	public static MyBundle getInstance() {
		return single;
	}
	
	@Override
	public void update(Observable o, Object arg) {
		System.out.println("observer informed");
		if(o instanceof SettingsController) {
			this.bundle=getLanguage();
		}
		
	}
	
    public ResourceBundle getBundle() {
		return bundle;
	}

    private ResourceBundle getLanguage() {
    	ResourceBundle bundle = null;
    	String appConfigPath = "etta.properties";
    	Properties properties = new Properties();
    	Locale curLocale;
    	try {
    		properties.load(new FileInputStream(appConfigPath));
    		String language = properties.getProperty("language");
        	String country = properties.getProperty("country");
        	curLocale = new Locale(language, country);
        	Locale.setDefault(curLocale);
    	}
    	catch(Exception e) {
    		
    	}
    	 
    	try {
    		bundle = ResourceBundle.getBundle("TextResources", new Locale("fi", "FI"));
    	}
    	catch(Exception e) {
    		
    	}
    	
    	/*
    	ResourceBundle bundleNow;
    	String chosenLocale="";
    	if(langDao.getSelectedLanguage()==null) {
    		chosenLocale="English";
    	}
    	else {
    		Language language = langDao.getSelectedLanguage();
    		chosenLocale = language.getDescription();
    	}
		System.out.println("chosenlocale " + chosenLocale);
		if(chosenLocale.equals("Finnish")) {
			System.out.println("chosenlocale in finnish " + chosenLocale);
			locale  =new Locale("fi", "FI");
			Locale.setDefault(locale);
			bundleNow = ResourceBundle.getBundle("resources.TextResources_fi_FI", locale);
			}
		else {
			System.out.println("chosenlocale in english " + chosenLocale);
			locale  = new Locale("en", "GB");
			Locale.setDefault(locale);
			bundleNow = ResourceBundle.getBundle("resources.TextResources_en_GB", locale);
		}
		return bundleNow;
		*/
    return bundle;
    }
}
