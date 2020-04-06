package view.mainPage;

import java.io.IOException;

import com.calendarfx.view.CalendarView;

import controller.CalendarController;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
/**
 * GUI class relating to the main views of the pages
 */
public class MainViewGUI {
	
	CalendarController calendarController = new CalendarController();
	
	/**
	 * Method loading the main page view 
	 * @return BorderPane main page view
	 */
	public BorderPane mainPageView() {
		BorderPane borderPane = new BorderPane();
		FXMLLoader loader2 = new FXMLLoader(getClass().getResource("/view/mainPage/MainMainView.fxml"));
		AnchorPane content = null;
		try {
			content = loader2.load();
			
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
		FXMLLoader loaderEconomy  = new FXMLLoader(getClass().getResource("/view/economy/EconomyRoot.fxml"));
		try {
			borderPaneEconomy = loaderEconomy.load();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		FXMLLoader loaderBalance  = new FXMLLoader(getClass().getResource("/view/economy/EconomyBalanceOverview.fxml"));
		AnchorPane balanceOverview = null;
		try {
			balanceOverview = loaderBalance.load();
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
		FXMLLoader loaderWishlist  = new FXMLLoader(getClass().getResource("/view/wishlist/WishlistRoot.fxml"));
		try {
			borderPaneWishlist = loaderWishlist.load();
			} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			}
		AnchorPane wishlistView = null;
		FXMLLoader loaderWishlistView  = new FXMLLoader(getClass().getResource("/view/wishlist/WishlistView.fxml"));
		try {
			wishlistView = loaderWishlistView.load();
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
		FXMLLoader loaderBorrowed  = new FXMLLoader(getClass().getResource("/view/borrowed/BorrowedRoot.fxml"));
		try {
			borderPaneBorrowed = loaderBorrowed.load();
			} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			}
		AnchorPane borrowedView = null;
		FXMLLoader loaderBorrowedView  = new FXMLLoader(getClass().getResource("/view/borrowed/BorrowedView.fxml"));
		try {
			borrowedView = loaderBorrowedView.load();
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
		FXMLLoader loaderContacts  = new FXMLLoader(getClass().getResource("/view/contacts/ContactsRoot.fxml"));
		try {
			borderPaneContacts = loaderContacts.load();
			} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			}
		AnchorPane contactsView = null;
		FXMLLoader loaderContactsView  = new FXMLLoader(getClass().getResource("/view/contacts/ContactsView.fxml"));
		try {
			contactsView = loaderContactsView.load();
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
