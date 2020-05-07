package fi.metropolia.OTP.group2.ETTA;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TabPane.TabClosingPolicy;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import res.MyTab;
import view.mainPage.MainViewGUI;

/**
 * The main class for starting the application
 * @author Lena
 */
public class App extends Application
{
	/**
	 * MyTab class's instance used for getting the tabs 
	 */
	MyTab myTab = MyTab.getMyTab();
	
    public static void main( String[] args )
    {
    	launch(args);
    }
    
    public void start(Stage primaryStage) {
    	MainViewGUI mainViewGUI = new MainViewGUI();
    	 	
        TabPane tabPane = new TabPane();

        //main page tab
        Tab tab1 = myTab.mainPageTab;
      //economy tab
        Tab tab2 = myTab.economyTab;
      //calendar tab
        Tab tab3 = myTab.calendarTab;
      //wishlist tab
        Tab tab4 = myTab.wishlistTab;
      //borrowed tab
        Tab tab5 = myTab.borrowedTab;
      //contacts tab
        Tab tab6 = myTab.contactsTab;
      //settings tab
        Tab tab7 = myTab.settingsTab;

        //main page view 
        tab1.setContent(mainViewGUI.mainPageView());
      
      // main page displays always the main view if the user changed from another tab
      		tab1.setOnSelectionChanged(event -> {
      	        if (tab1.isSelected()) {
      	        	tab1.setContent(mainViewGUI.mainPageView());
      	            }
      	        });
		
		//economy page displays always the main economy view if the user changed from another tab
		tab2.setOnSelectionChanged(event -> {
	        if (tab2.isSelected()) {
	        	tab2.setContent(mainViewGUI.EconomyView());
	            }
	        });
		
		//calendar page displays always the main calendar view if the user changed from another tab
		tab3.setOnSelectionChanged(event -> {
	        if (tab3.isSelected()) {
	        	tab3.setContent(mainViewGUI.CalendarView());
	            }
	        });
		
		//wishlist page displays always the main wishlist view if the user changed from another tab
		tab4.setOnSelectionChanged(event -> {
	        if (tab4.isSelected()) {
	        	tab4.setContent(mainViewGUI.WishlistView());
	            }
	        });
		
		//borrowed page displays always the main  borrowed view if the user changed from another tab
		tab5.setOnSelectionChanged(event -> {
	        if (tab5.isSelected()) {
	        	tab5.setContent(mainViewGUI.BorrowedView());
	            }
	        });
		
		//contacts page displays always the main contacts view if the user changed from another tab
		tab6.setOnSelectionChanged(event -> {
	        if (tab6.isSelected()) {
	        	tab6.setContent(mainViewGUI.ContactsView());
	            }
	        });
		
		//settings page displays always the main settings view if the user changed from another tab
		tab7.setOnSelectionChanged(event -> {
	        if (tab7.isSelected()) {
	        	tab7.setContent(mainViewGUI.SettingsView());
	            }
	        });
		
        tabPane.getTabs().add(tab1);
        tabPane.getTabs().add(tab2);
        tabPane.getTabs().add(tab3);
        tabPane.getTabs().add(tab4);
        tabPane.getTabs().add(tab5);
        tabPane.getTabs().add(tab6);
        tabPane.getTabs().add(tab7);
        
        //user can closed tabs
        tabPane.setTabClosingPolicy(TabClosingPolicy.UNAVAILABLE);
        VBox vBox = new VBox(tabPane);
        Scene scene = new Scene(vBox);
        scene.getStylesheets().add(getClass().getResource("/res/newstyle.css").toExternalForm());
        primaryStage.setScene(scene);
        //should be localized if program is localized to a language with not cyrillic or latin alphabet
        primaryStage.setTitle("ETTA");

        primaryStage.show();
    }

}

