package res;

import java.util.Enumeration;
import java.util.Locale;
import java.util.ResourceBundle;

import model.Language;
import model.LanguageDAO;

public class MyBundle extends ResourceBundle{
	LanguageDAO langDao = new LanguageDAO();
	Locale locale;
	ResourceBundle bundle;
	
	@Override
	protected Object handleGetObject(String key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Enumeration<String> getKeys() {
		// TODO Auto-generated method stub
		return null;
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

}
