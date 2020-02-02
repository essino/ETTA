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

        BorderPane borderPane = new BorderPane();
        FXMLLoader loaderMenu = new FXMLLoader(getClass().getResource("/view/MainMenu.fxml"));
		AnchorPane mainMenu = null;
		try {
			mainMenu = loaderMenu.load();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		borderPane.setLeft(mainMenu);
		FXMLLoader loader2 = new FXMLLoader(getClass().getResource("/view/MainMainView.fxml"));
		AnchorPane content = null;
		try {
			content = loader2.load();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		borderPane.setCenter(content);
		tab1.setContent(borderPane);
		
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
		
        tabPane.getTabs().add(tab1);
        tabPane.getTabs().add(tab2);
        tabPane.getTabs().add(tab3);
        tabPane.getTabs().add(tab4);

        
        VBox vBox = new VBox(tabPane);
        Scene scene = new Scene(vBox);

        primaryStage.setScene(scene);
        primaryStage.setTitle("ETTA");

        primaryStage.show();
    }
}

