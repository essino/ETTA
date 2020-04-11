package view.mainPage;

import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

import com.calendarfx.view.CalendarView;

import controller.CalendarController;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import model.Language;
import model.LanguageDAO;
/**
 * GUI class relating to the main views of the pages
 */
public class MainViewGUI {
	
	CalendarController calendarController = new CalendarController();
	LanguageDAO langDao = new LanguageDAO();
	Locale locale;
	ResourceBundle bundle;
	FXMLLoader loader;
	/*
	public MainViewGUI() {
	String chosenLocale = langDao.getSelectedLanguage().getDescription();
		if(chosenLocale=="Finnish") {
			locale  =new Locale("fi", "FI");
			bundle = ResourceBundle.getBundle("res.TextResources_fi_FI", locale);
			}
		else {
			locale  =new Locale("en", "GB");
			bundle = ResourceBundle.getBundle("res.TextResources_en_GB", locale);
		}
		
	}*/
	
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
			bundle = ResourceBundle.getBundle("res.TextResources_fi_FI", locale);
			}
		else {
			System.out.println("chosenlocale in english " + chosenLocale);
			locale  = new Locale("en", "GB");
			bundle = ResourceBundle.getBundle("res.TextResources_en_GB", locale);
		}
		return bundle;
	}
	/**
	 * Method loading the main page view 
	 * @return BorderPane main page view
	 */
	public BorderPane mainPageView() {
		BorderPane borderPane = new BorderPane();
		loader = new FXMLLoader(getClass().getResource("/view/mainPage/MainMainView.fxml"));
		AnchorPane content = null;
		loader.setResources(getBundle());
		try {
			content = loader.load();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		borderPane.setCenter(content);
		return borderPane;
	}
	/**
	 * Method loading the economy main page view - root with the button menu and the content
	 * @return BorderPane economy main page view
	 */
	public BorderPane EconomyView() {
		BorderPane borderPaneEconomy = new BorderPane();
		loader  = new FXMLLoader(getClass().getResource("/view/economy/EconomyRoot.fxml"));
		loader.setResources(getBundle());
		try {
			borderPaneEconomy = loader.load();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		loader  = new FXMLLoader(getClass().getResource("/view/economy/EconomyBalanceOverview.fxml"));
		AnchorPane balanceOverview = null;
		loader.setResources(getBundle());
		//loaderBalance.setResources(bundle);
		try {
			balanceOverview = loader.load();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		borderPaneEconomy.setCenter(balanceOverview);
		return borderPaneEconomy;
	}
	/**
	 * Method loading the wishlist main page view - root with the button menu and the content
	 * @return BorderPane wishlist main page view
	 */
	public BorderPane WishlistView() {
		BorderPane borderPaneWishlist = new BorderPane();
		loader = new FXMLLoader(getClass().getResource("/view/wishlist/WishlistRoot.fxml"));
		loader.setResources(bundle);
		try {
			borderPaneWishlist = loader.load();
			} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			}
		AnchorPane wishlistView = null;
		loader = new FXMLLoader(getClass().getResource("/view/wishlist/WishlistView.fxml"));
		loader.setResources(bundle);
		try {
			wishlistView = loader.load();
			} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			}
		borderPaneWishlist.setCenter(wishlistView);
		return borderPaneWishlist;
	}

	/**
	 * Method loading the borrowed things main page view - root with the button menu and the content
	 * @return BorderPane borrowed things main page view
	 */
	public BorderPane BorrowedView() {
		BorderPane borderPaneBorrowed = new BorderPane();
		loader = new FXMLLoader(getClass().getResource("/view/borrowed/BorrowedRoot.fxml"));
		loader.setResources(bundle);
		try {
			borderPaneBorrowed = loader.load();
			} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			}
		AnchorPane borrowedView = null;
		loader  = new FXMLLoader(getClass().getResource("/view/borrowed/BorrowedView.fxml"));
		loader.setResources(bundle);
		try {
			borrowedView = loader.load();
			} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			}
		borderPaneBorrowed.setCenter(borrowedView);
		return borderPaneBorrowed;
	}
	/**
	 * Method loading the contacts main page view - root with the button menu and the content
	 * @return BorderPane contacts main page view
	 */
	public BorderPane ContactsView() {
		BorderPane borderPaneContacts = new BorderPane();
		loader  = new FXMLLoader(getClass().getResource("/view/contacts/ContactsRoot.fxml"));
		loader.setResources(bundle);
		try {
			borderPaneContacts = loader.load();
			} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			}
		AnchorPane contactsView = null;
		loader  = new FXMLLoader(getClass().getResource("/view/contacts/ContactsView.fxml"));
		loader.setResources(bundle);
		try {
			contactsView = loader.load();
			} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			}
		borderPaneContacts.setCenter(contactsView);
		return borderPaneContacts;
	}
	/**
	 * Method loading the calendar main page view - root with the button menu and the content
	 * @return BorderPane calendar main page view
	 */
	public BorderPane CalendarView() {
		BorderPane borderPaneCalendar = new BorderPane();
		
		FXMLLoader loader  = new FXMLLoader(getClass().getResource("/view/calendar/CalendarRoot.fxml"));
		try {
			borderPaneCalendar = loader.load();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		CalendarView calendarView  = new CalendarView();
		calendarView.getCalendarSources().addAll(calendarController.getCalendarSource());
		calendarController.getDefaultCalendarSource(calendarView);
		borderPaneCalendar.setCenter(calendarView);
		return borderPaneCalendar;
	}
	/**
	 * Method loading the settings page view - root with the button menu and the content
	 * @return BorderPane settings page view
	 */
	public BorderPane SettingsView() {
		BorderPane borderPaneSettings = new BorderPane();
		FXMLLoader loaderSettings  = new FXMLLoader(getClass().getResource("/view/settings/Settings.fxml"));
		try {
			borderPaneSettings = loaderSettings.load();
			} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			}
		return borderPaneSettings;
	}
}
