package res;

import java.util.Locale;
import java.util.ResourceBundle;

import controller.SettingsController;

/**
 * MyBundle class that chooses the resource bundle depending on the chosen language
 * @author Lena
 */
public class MyBundle{

	/**
	 * Constructor without parameters
	 */
	public MyBundle() {
	}

	/**
	 * Method for getting the current resource bundle, depending on the locale chosen in the settings controller
	 * @return ResourceBundle the current resourceBundle
	 */
    public ResourceBundle getBundle() {
    	//settingsController is a singleton -> no need to go to the database to check which language is chosen, the data is kept in settingsController
    	Locale currentLocale = SettingsController.getInstance().getCurrentLocale();
    	//fi_FI and en_GB possible at the moment
    	String fileName = "res.TextResources_"+currentLocale;
    	ResourceBundle bundleNow = ResourceBundle.getBundle(fileName, currentLocale);
    	//locale needed t ex for the date format in date cells
    	Locale.setDefault(currentLocale);
		return bundleNow;
    }
}
