package fi.metropolia.OTP.group2.ETTA;

import com.calendarfx.view.DetailedDayView;

import javafx.fxml.FXML;

import javafx.scene.SubScene;

import javafx.scene.layout.BorderPane;

import view.CalendarGUI;

public class CalendarMenuController {
	private CalendarGUI calendarGUI;
	BorderPane mainPane;
	
	public CalendarMenuController(CalendarGUI calendarGUI) {
		this.calendarGUI = calendarGUI;
	}
	
	@FXML
	public void showDayView() {
		DetailedDayView calendarView = new DetailedDayView();   
        SubScene calendarScene = new SubScene(calendarView, 800, 750);
        mainPane.setCenter(calendarScene);
	}

}
