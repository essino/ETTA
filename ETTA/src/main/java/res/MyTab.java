package res;

import javafx.scene.control.Tab;

/**
 * MyTab class extending Tab class. Used for getting the tabs for the application and updating their names
 * @author Lena
 */
public class MyTab extends Tab{

	/**
	 * The used resource bundle
	 */
	private MyBundle myBundle = new MyBundle();
	
	/**
	 * The instance of the MyTab class used to get the tabs
	 */
	public static final MyTab singleMyTab = new MyTab(); 
	
	/**
	 * The main page tab
	 */
	public Tab mainPageTab;
	
	/**
	 * The economy page tab
	 */
	public Tab economyTab;
	
	/**
	 * The calendar page tab
	 */
	public Tab calendarTab;
	
	/**
	 * The wishlist page tab
	 */
	public Tab wishlistTab;
	
	/**
	 * The borrowed items page tab
	 */
	public Tab borrowedTab;
	
	/**
	 * The contacts page tab
	 */
	public Tab contactsTab;
	
	/**
	 * The settings page tab
	 */
	public Tab settingsTab;
	
	/**
	 * Private constructor 
	 */
	private MyTab() {
		this.mainPageTab=new Tab(myBundle.getBundle().getString("pageMain"));
		this.economyTab = new Tab(myBundle.getBundle().getString("pageEconomy"));
		this.calendarTab = new Tab(myBundle.getBundle().getString("pageCalendar"));
		this.wishlistTab = new Tab(myBundle.getBundle().getString("pageWishlist"));
		this.borrowedTab = new Tab(myBundle.getBundle().getString("pageBorrowed"));
		this.contactsTab = new Tab(myBundle.getBundle().getString("pageContacts"));
		this.settingsTab = new Tab(myBundle.getBundle().getString("pageSettings"));
	}
	
	/**
	 * Method for getting the instance of the MyTab class
	 * @return MyTab instance of the MyTabclass
	 */
	public static MyTab getMyTab() {
		return singleMyTab;
	}
	
	/**
	 * Method for updating the names of the tabs, gets called if the language choice is updated
	 */
	public void setTabName() {
		this.mainPageTab.setText(myBundle.getBundle().getString("pageMain"));
		this.economyTab.setText(myBundle.getBundle().getString("pageEconomy"));
		this.calendarTab.setText(myBundle.getBundle().getString("pageCalendar"));
		this.wishlistTab.setText(myBundle.getBundle().getString("pageWishlist"));
		this.borrowedTab.setText(myBundle.getBundle().getString("pageBorrowed"));
		this.contactsTab.setText(myBundle.getBundle().getString("pageContacts"));
		this.settingsTab.setText(myBundle.getBundle().getString("pageSettings"));
	}
}
