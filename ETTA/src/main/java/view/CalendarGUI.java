package view;

import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import com.calendarfx.model.Calendar;
import com.calendarfx.model.CalendarEvent;
import com.calendarfx.model.CalendarSource;
import com.calendarfx.model.Entry;
import com.calendarfx.view.DetailedDayView;
import com.calendarfx.view.page.DayPage;
import com.calendarfx.view.page.MonthPage;
import com.calendarfx.view.page.WeekPage;
import com.calendarfx.view.popover.EntryDetailsView;
import com.calendarfx.view.popover.EntryHeaderView;
import com.calendarfx.view.popover.EntryPopOverPane;
import com.calendarfx.view.popover.EntryPropertiesView;
import com.calendarfx.view.popover.PopOverContentPane;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.SubScene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
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
		//DayPage calendarView = new DayPage();
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
		Calendar birthdays = new Calendar("birthdays");
		EventHandler<CalendarEvent> handler = evt -> foo(evt);
		birthdays.addEventHandler(handler);
		
		CalendarSource myCalendarSource = new CalendarSource("My calendar");
		myCalendarSource.getCalendars().addAll(birthdays);
		calendarView.getCalendarSources().addAll(myCalendarSource);
		calendarView.setRequestedTime(LocalTime.now());
		Entry<String> lenaBirthday = new Entry<>("Lena");
		lenaBirthday.changeStartDate(LocalDate.now());
		birthdays.addEntry(lenaBirthday);
		//DetailedWeekView calendarView = new DetailedWeekView();   
        //SubScene calendarScene = new SubScene(calendarView, 800, 750);
        mainPane.setCenter(calendarView);
	}
	
	
	private void foo(CalendarEvent evt) {
		System.out.println(evt);
		System.out.println(evt.getEntry());
		System.out.println(evt.getEntry().getCalendar());
		System.out.println(evt.getEntry().getTitle());
		System.out.println(evt.getEntry().getEndDate());
		System.out.println(evt.getEntry().isFullDay());
		System.out.println(evt.getEntry().getLocation());
		System.out.println(evt.getEntry().getStartTime());
		System.out.println(evt.getOldText());
	}

	@FXML
	public void showMonthView() {
		//MonthPage calendarView = new MonthPage();
		Calendar days = new Calendar("days");
		Entry<Object> newEntry = new Entry<>("new entry");
		newEntry.changeStartDate(LocalDate.now());
		days.addEntry(newEntry);
		List<Calendar> calendars = new ArrayList();
		calendars.add(days);
		EntryHeaderView header = new EntryHeaderView(newEntry, calendars);
		EntryDetailsView newentry = new EntryDetailsView(newEntry);
		Pane viewPane = new Pane();
		viewPane.getChildren().addAll(header, newentry);
		//MonthView calendarView = new MonthView();   
        //SubScene calendarScene = new SubScene(calendarView, 800, 750);
        //mainPane.setCenter(calendarView);
		//mainPane.setTop(header);
		mainPane.setCenter(viewPane);
	}
	

}
