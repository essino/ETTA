package res;

import javafx.scene.control.Tab;

public class MyTab extends Tab{

	//MyBundle myBundle = new MyBundle();
	MyBundle myBundle =MyBundle.getInstance();
	public static final MyTab singleMyTab = new MyTab(); 
	public Tab mainPageTab;
	public Tab economyTab;
	public Tab calendarTab;
	public Tab wishlistTab;
	public Tab borrowedTab;
	public Tab contactsTab;
	public Tab settingsTab;
	
	private MyTab() {
		this.mainPageTab=new Tab(myBundle.getBundle().getString("pageMain"));
		this.economyTab = new Tab(myBundle.getBundle().getString("pageEconomy"));
		this.calendarTab = new Tab(myBundle.getBundle().getString("pageCalendar"));
		this.wishlistTab = new Tab(myBundle.getBundle().getString("pageWishlist"));
		this.borrowedTab = new Tab(myBundle.getBundle().getString("pageBorrowed"));
		this.contactsTab = new Tab(myBundle.getBundle().getString("pageContacts"));
		this.settingsTab = new Tab(myBundle.getBundle().getString("pageSettings"));
	}
	
	public static MyTab getMyTab() {
		return singleMyTab;
	}
	
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
