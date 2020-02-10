package view;

import java.io.IOException;

import com.calendarfx.view.page.WeekPage;

import controller.MainViewController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

public class MainViewGUI {
	
	public BorderPane mainPageView() {
		BorderPane borderPane = new BorderPane();
		FXMLLoader loader2 = new FXMLLoader(getClass().getResource("/view/MainMainView.fxml"));
		AnchorPane content = null;
		try {
			content = loader2.load();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		borderPane.setCenter(content);
		return borderPane;
	}
	
	public BorderPane EconomyView() {
		BorderPane borderPaneEconomy = new BorderPane();
		FXMLLoader loaderEconomy  = new FXMLLoader(getClass().getResource("/view/EconomyRoot.fxml"));
		try {
			borderPaneEconomy = loaderEconomy.load();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		FXMLLoader loaderBalance  = new FXMLLoader(getClass().getResource("/view/EconomyBalanceOverview.fxml"));
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

	public BorderPane WishlistView() {
		BorderPane borderPaneWishlist = new BorderPane();
		FXMLLoader loaderWishlist  = new FXMLLoader(getClass().getResource("/view/WishlistRoot.fxml"));
		try {
			borderPaneWishlist = loaderWishlist.load();
			} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			}
		AnchorPane wishlistView = null;
		FXMLLoader loaderWishlistView  = new FXMLLoader(getClass().getResource("/view/WishlistView.fxml"));
		try {
			wishlistView = loaderWishlistView.load();
			} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			}
		borderPaneWishlist.setCenter(wishlistView);
		return borderPaneWishlist;
	}

	public BorderPane BorrowedView() {
		BorderPane borderPaneBorrowed = new BorderPane();
		FXMLLoader loaderBorrowed  = new FXMLLoader(getClass().getResource("/view/BorrowedRoot.fxml"));
		try {
			borderPaneBorrowed = loaderBorrowed.load();
			} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			}
		AnchorPane borrowedView = null;
		FXMLLoader loaderBorrowedView  = new FXMLLoader(getClass().getResource("/view/BorrowedView.fxml"));
		try {
			borrowedView = loaderBorrowedView.load();
			} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			}
		borderPaneBorrowed.setCenter(borrowedView);
		return borderPaneBorrowed;
	}
	
	public BorderPane ContactsView() {
		BorderPane borderPaneContacts = new BorderPane();
		FXMLLoader loaderContacts  = new FXMLLoader(getClass().getResource("/view/ContactsRoot.fxml"));
		try {
			borderPaneContacts = loaderContacts.load();
			} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			}
		AnchorPane contactsView = null;
		FXMLLoader loaderContactsView  = new FXMLLoader(getClass().getResource("/view/ContactsView.fxml"));
		try {
			contactsView = loaderContactsView.load();
			} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			}
		borderPaneContacts.setCenter(contactsView);
		return borderPaneContacts;
	}
	
	public BorderPane CalendarView() {
		BorderPane borderPaneCalendar = new BorderPane();
		FXMLLoader loader  = new FXMLLoader(getClass().getResource("/view/CalendarRoot.fxml"));
		try {
			borderPaneCalendar = loader.load();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		WeekPage calendarView = new WeekPage();
		borderPaneCalendar.setCenter(calendarView);
		return borderPaneCalendar;
	}
		
}
