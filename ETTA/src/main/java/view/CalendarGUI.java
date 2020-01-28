package view;

import java.io.IOException;

import com.calendarfx.view.page.DayPage;
import com.calendarfx.view.page.MonthPage;
import com.calendarfx.view.page.WeekPage;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class CalendarGUI extends Application{

	Parent root = new BorderPane(); 

	@FXML
	BorderPane mainPane;
	
	@Override 
	public void start(Stage primaryStage) throws Exception {
		Parent root = FXMLLoader.load(getClass().getResource("/view/CalendarRoot.fxml"));
        Scene scene = new Scene(root, 1000, 750);
		primaryStage.setScene(scene); 
		primaryStage.show();
	}

	 public static void main(String[] args) {
	        launch(args);
	    } 

	@FXML
	public void showDayView() {
		System.out.println("showdayview");
		DayPage calendarView = new DayPage();
		/*
		AnchorPane menuLayout = new AnchorPane(); 
		FXMLLoader fxmlLoaderMenu = new FXMLLoader(getClass().getResource("/view/BlablaView.fxml"));
		try { 
			menuLayout = fxmlLoaderMenu.load(); 
		} catch (IOException e) { 

		} 
		manePane.setCenter(menuLayout);
		*/
		mainPane.setCenter(calendarView);
	}

	@FXML
	public void showWeekView() {
		WeekPage calendarView = new WeekPage();
        mainPane.setCenter(calendarView);
	}

	@FXML
	public void showMonthView() {
		MonthPage calendarView = new MonthPage();
        mainPane.setCenter(calendarView);
	}
}