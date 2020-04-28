package res;

import java.util.Locale;
import java.util.ResourceBundle;

import controller.SettingsController;

public class MyBundle{

	public MyBundle() {
	}

    public ResourceBundle getBundle() {
    	Locale currentLocale = SettingsController.getInstance().getCurrentLocale();
    	String fileName = "res.TextResources_"+currentLocale;
    	ResourceBundle bundleNow = ResourceBundle.getBundle(fileName, currentLocale);
		return bundleNow;
    }
}
