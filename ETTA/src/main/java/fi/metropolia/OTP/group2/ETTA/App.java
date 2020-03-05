package fi.metropolia.OTP.group2.ETTA;

import controller.MainViewController;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TabPane.TabClosingPolicy;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import view.MainPageGUI;
import view.MainViewGUI;


public class App extends Application
{
	
    public static void main( String[] args )
    {
    	launch(args);
    }
    
    public void start(Stage primaryStage) {
    	MainViewGUI mainViewGUI = new MainViewGUI();
    	MainPageGUI mainPageGUI = new MainPageGUI();
    	MainViewController controller = new MainViewController(mainPageGUI);
    	
        TabPane tabPane = new TabPane();

        Tab tab1 = new Tab("Main Page");
        Tab tab2 = new Tab("Economy");
        Tab tab3 = new Tab("Calendar");
        Tab tab4 = new Tab("Wishlists");
        Tab tab5 = new Tab("Borrowed things");
        Tab tab6 = new Tab("Contacts");

        //main page view
        tab1.setContent(mainViewGUI.mainPageView());
      
      // main page view
      		tab1.setOnSelectionChanged(event -> {
      	        if (tab1.isSelected()) {
      	        	tab1.setContent(mainViewGUI.mainPageView());
      	            }
      	        });
		
		//economy main view
		tab2.setOnSelectionChanged(event -> {
	        if (tab2.isSelected()) {
	        	tab2.setContent(mainViewGUI.EconomyView());
	            }
	        });
		
		//calendar page view
		tab3.setOnSelectionChanged(event -> {
	        if (tab3.isSelected()) {
	        	tab3.setContent(mainViewGUI.CalendarView());
	            }
	        });
		
		//wishlist main view
		tab4.setOnSelectionChanged(event -> {
	        if (tab4.isSelected()) {
	        	tab4.setContent(mainViewGUI.WishlistView());
	            }
	        });
		
		//borrowed main view
		tab5.setOnSelectionChanged(event -> {
	        if (tab5.isSelected()) {
	        	tab5.setContent(mainViewGUI.BorrowedView());
	            }
	        });
		
		//contacts main view
		tab6.setOnSelectionChanged(event -> {
	        if (tab6.isSelected()) {
	        	tab6.setContent(mainViewGUI.ContactsView());
	            }
	        });
		
        tabPane.getTabs().add(tab1);
        tabPane.getTabs().add(tab2);
        tabPane.getTabs().add(tab3);
        tabPane.getTabs().add(tab4);
        tabPane.getTabs().add(tab5);
        tabPane.getTabs().add(tab6);
        
        tabPane.setTabClosingPolicy(TabClosingPolicy.UNAVAILABLE);
        VBox vBox = new VBox(tabPane);
        Scene scene = new Scene(vBox);

        primaryStage.setScene(scene);
        primaryStage.setTitle("ETTA");

        primaryStage.show();
    }
}

