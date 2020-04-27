package res;

import java.io.FileInputStream;
import java.util.Locale;
import java.util.Properties;
import java.util.ResourceBundle;

import javafx.scene.control.Tab;

public class MyTab extends Tab{

	//MyBundle myBundle = new MyBundle();
	//MyBundle myBundle =MyBundle.getInstance();
	ResourceBundle bundle = MyBundle.getInstance().getBundle();
	public static final MyTab singleMyTab = new MyTab(); 
	public Tab mainPageTab;
	public Tab economyTab;
	public Tab calendarTab;
	public Tab wishlistTab;
	public Tab borrowedTab;
	public Tab contactsTab;
	public Tab settingsTab;
	
	private MyTab() {
		this.mainPageTab=new Tab(bundle.getString("pageMain"));
		this.economyTab = new Tab(bundle.getString("pageEconomy"));
		this.calendarTab = new Tab(bundle.getString("pageCalendar"));
		this.wishlistTab = new Tab(bundle.getString("pageWishlist"));
		this.borrowedTab = new Tab(bundle.getString("pageBorrowed"));
		this.contactsTab = new Tab(bundle.getString("pageContacts"));
		this.settingsTab = new Tab(bundle.getString("pageSettings"));
	}
	
	public static MyTab getMyTab() {
		return singleMyTab;
	}
	
	public void setTabName() {
		this.mainPageTab.setText(bundle.getString("pageMain"));
		this.economyTab.setText(bundle.getString("pageEconomy"));
		this.calendarTab.setText(bundle.getString("pageCalendar"));
		this.wishlistTab.setText(bundle.getString("pageWishlist"));
		this.borrowedTab.setText(bundle.getString("pageBorrowed"));
		this.contactsTab.setText(bundle.getString("pageContacts"));
		this.settingsTab.setText(bundle.getString("pageSettings"));
	}
}
