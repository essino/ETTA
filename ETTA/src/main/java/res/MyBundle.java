package res;

import java.util.Locale;
import java.util.Observable;
import java.util.ResourceBundle;

import model.Language;
import model.LanguageDAO;

public class MyBundle extends Observable{
	public static final MyBundle single = new MyBundle();
	LanguageDAO langDao = new LanguageDAO();
	Locale locale;
	ResourceBundle bundle;
	
	private MyBundle() {
		
	}

	public static MyBundle getInstance() {
		return single;
	}
	
    public ResourceBundle getBundle() {
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
			bundle = ResourceBundle.getBundle("res.TextResources_fi_FI", locale);
			}
		else {
			System.out.println("chosenlocale in english " + chosenLocale);
			locale  = new Locale("en", "GB");
			Locale.setDefault(locale);
			bundle = ResourceBundle.getBundle("res.TextResources_en_GB", locale);
		}
		return bundle;
	}

    public void langChanged() {
    	//System.out.println("lang change");
    	//this.langChanged=change;
    	this.setChanged();
		this.notifyObservers();
		System.out.println("observers "+ this.countObservers());
		//langChanged = false;
    }

}
