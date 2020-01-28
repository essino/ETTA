package view;

import java.io.IOException;

import com.calendarfx.view.DetailedDayView;
import com.calendarfx.view.DetailedWeekView;
import com.calendarfx.view.MonthView;
import com.calendarfx.view.page.DayPage;
import com.calendarfx.view.page.MonthPage;
import com.calendarfx.view.page.WeekPage;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.SubScene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class CalendarGUI extends Application{

	Parent root2 = new BorderPane(); 

	@FXML
	BorderPane mainPane;

	@Override
	public void start(Stage primaryStage) throws Exception {
		Parent root2 = FXMLLoader.load(getClass().getResource("/view/CalendarView.fxml"));    
        Scene scene = new Scene(root2, 1000, 750);
		primaryStage.setScene(scene); 
		primaryStage.show();
	}
	
	 public static void main(String[] args) {
	        launch(args);
	    } 
	 
	public SubScene setMenu() {
		System.out.println("setmenu");
		AnchorPane menuLayout = new AnchorPane(); 
		FXMLLoader fxmlLoaderMenu = new FXMLLoader(getClass().getResource("/view/CalendarMenu.fxml"));
		try { 
			menuLayout = fxmlLoaderMenu.load(); 
		} catch (IOException e) { 

		} 
		SubScene menu = new SubScene(menuLayout, 200, 750);
		return menu;
	}

	public SubScene setDayView() {
		DetailedDayView calendarView = new DetailedDayView();   
        SubScene calendarScene = new SubScene(calendarView, 800, 750);
        return calendarScene;
	}
	
	@FXML
	public void showDayView() {
		System.out.println("showdayview");
		DayPage calendarView = new DayPage();
		//DetailedDayView calendarView = new DetailedDayView();   
        //SubScene calendarScene = new SubScene(calendarView, 800, 750);
        //mainPane.setCenter(calendarView);
		AnchorPane menuLayout = new AnchorPane(); 
		FXMLLoader fxmlLoaderMenu = new FXMLLoader(getClass().getResource("/view/BlablaView.fxml"));
		try { 
			menuLayout = fxmlLoaderMenu.load(); 
		} catch (IOException e) { 

		} 
		mainPane.setCenter(menuLayout);
	}

	@FXML
	public void showWeekView() {
		WeekPage calendarView = new WeekPage();
		//DetailedWeekView calendarView = new DetailedWeekView();   
        //SubScene calendarScene = new SubScene(calendarView, 800, 750);
        mainPane.setCenter(calendarView);
	}
	
	@FXML
	public void showMonthView() {
		MonthPage calendarView = new MonthPage();
		//MonthView calendarView = new MonthView();   
        //SubScene calendarScene = new SubScene(calendarView, 800, 750);
        mainPane.setCenter(calendarView);
	}
	

}
