package controller;

import java.io.IOException;

import com.calendarfx.view.page.DayPage;
import com.calendarfx.view.page.MonthPage;
import com.calendarfx.view.page.WeekPage;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

public class CalendarController {
	@FXML 
	BorderPane mainPane;
	
	@FXML
	Button calendarButton;
	
	@FXML
	AnchorPane CalendarMenu;
	
	@FXML
	Button backToMain;
	
	@FXML
	Button CalendarDay;
	
	@FXML
	Button CalendarWeek;
	
	@FXML
	Button CalendarMonth;
	
	@FXML
	Button CalendarAdd;
	
	@FXML
	public void showDayView(ActionEvent event) throws IOException {
		DayPage calendarView = new DayPage();
		mainPane.setCenter(calendarView);
	}

	@FXML
	public void showWeekView(ActionEvent event) {
		WeekPage calendarView = new WeekPage();
        mainPane.setCenter(calendarView);
	}

	@FXML
	public void showMonthView(ActionEvent event) {
		MonthPage calendarView = new MonthPage();
        mainPane.setCenter(calendarView);
	}
	
}
