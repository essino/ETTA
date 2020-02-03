package fi.metropolia.OTP.group2.ETTA;

import java.io.IOException;

import com.calendarfx.view.page.WeekPage;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


public class App extends Application
{
    public static void main( String[] args )
    {
    	launch(args);
    }
    
    public void start(Stage primaryStage) {

        TabPane tabPane = new TabPane();

        Tab tab1 = new Tab("Main Page");
        Tab tab2 = new Tab("Economy");
        Tab tab3 = new Tab("Calendar");
        Tab tab4 = new Tab("Wishlists");
        Tab tab5 = new Tab("Borrowed things");
        Tab tab6 = new Tab("Contacts");

        //main page view
        BorderPane borderPane = new BorderPane();
		FXMLLoader loader2 = new FXMLLoader(getClass().getResource("/view/MainMainView.fxml"));
		AnchorPane content = null;
		try {
			content = loader2.load();
		} catch (IOException e) {
			e.printStackTrace();
		}
		borderPane.setCenter(content);
		tab1.setContent(borderPane);
		
		//economy main view
		BorderPane borderPaneEconomy = new BorderPane();
		FXMLLoader loaderEconomy  = new FXMLLoader(getClass().getResource("/view/EconomyRoot.fxml"));
		try {
			borderPaneEconomy = loaderEconomy.load();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		FXMLLoader loaderBalance  = new FXMLLoader(getClass().getResource("/view/BalanceOverview.fxml"));
		AnchorPane balanceOverview = null;
		try {
			balanceOverview = loaderBalance.load();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		borderPaneEconomy.setCenter(balanceOverview);
		tab2.setContent(borderPaneEconomy);
		
		//calendar page view
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
		tab3.setContent(borderPaneCalendar);
		
		//economy main view
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
		tab4.setContent(borderPaneWishlist);
		
		//borrowed main view
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
		tab5.setContent(borderPaneBorrowed);
		
		//contacts main view
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
		tab6.setContent(borderPaneContacts);
		
        tabPane.getTabs().add(tab1);
        tabPane.getTabs().add(tab2);
        tabPane.getTabs().add(tab3);
        tabPane.getTabs().add(tab4);
        tabPane.getTabs().add(tab5);
        tabPane.getTabs().add(tab6);
        
        VBox vBox = new VBox(tabPane);
        Scene scene = new Scene(vBox);

        primaryStage.setScene(scene);
        primaryStage.setTitle("ETTA");

        primaryStage.show();
    }
}

