package fi.metropolia.OTP.group2.ETTA;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;

import com.calendarfx.model.Calendar;
import com.calendarfx.model.CalendarSource;
import com.calendarfx.model.Calendar.Style;
import com.calendarfx.view.CalendarView;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.SubScene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class App extends Application
{
	@Override
	public void start(Stage primaryStage){
		// TODO Auto-generated method stub
		BorderPane root = new BorderPane();
		Scene scene = new Scene(root);
		AnchorPane menuLayout = new AnchorPane();
		//FXMLLoader fxmlLoaderMenu = new FXMLLoader(getClass().getResource("MainViewWithMenu.fxml"));
		FXMLLoader fxmlLoaderMenu = new FXMLLoader(getClass().getResource("EconomyMenu.fxml"));
		try {
			menuLayout = fxmlLoaderMenu.load();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		SubScene menu = new SubScene(menuLayout, 200, 750);
		root.setLeft(menu);

		AnchorPane savingsLayout = new AnchorPane();
		//FXMLLoader fxmlLoaderMenu = new FXMLLoader(getClass().getResource("MainViewWithMenu.fxml"));
		FXMLLoader fxmlLoaderSavings = new FXMLLoader(getClass().getResource("SavingsContent.fxml"));
		try {
			savingsLayout = fxmlLoaderSavings.load();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		SubScene savings = new SubScene(savingsLayout, 800, 750);
		root.setCenter(savings);
		/*
		CalendarView calendarView = new CalendarView(); 

        Calendar birthdays = new Calendar("Birthdays"); 
        Calendar holidays = new Calendar("Holidays");

        birthdays.setStyle(Style.STYLE1); 
        holidays.setStyle(Style.STYLE2);

        CalendarSource myCalendarSource = new CalendarSource("My Calendars"); 
        myCalendarSource.getCalendars().addAll(birthdays, holidays);

        calendarView.getCalendarSources().addAll(myCalendarSource); 

        calendarView.setRequestedTime(LocalTime.now());

        Thread updateTimeThread = new Thread("Calendar: Update Time Thread") {
                @Override
                public void run() {
                        while (true) {
                                Platform.runLater(() -> {
                                        calendarView.setToday(LocalDate.now());
                                        calendarView.setTime(LocalTime.now());
                                });

                                try {
                                        // update every 10 seconds
                                        sleep(10000);
                                } catch (InterruptedException e) {
                                        e.printStackTrace();
                                }

                        }
                };
        };

        updateTimeThread.setPriority(Thread.MIN_PRIORITY);
        updateTimeThread.setDaemon(true);
        updateTimeThread.start();

        SubScene calendarScene = new SubScene(calendarView, 800, 750);
        root.setCenter(calendarScene);
       */
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	 public static void main(String[] args) {
	        launch(args);
	    } 


}
