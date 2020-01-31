package controller;

import java.io.IOException;

import com.calendarfx.view.page.DayPage;
import com.calendarfx.view.page.MonthPage;
import com.calendarfx.view.page.WeekPage;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import view.CalendarGUI;

public class MainViewController {

	@FXML 
	BorderPane mainViewPane;
	
	@FXML
	Button calendarButton;
	
	@FXML
	AnchorPane CalendarMenu;
	
	@FXML
	Button backToMain;
	
	@FXML
	Button CalendarDay;
	/*
	@FXML
	private void initialize() {
        CalendarDay.setOnAction((event) -> {
            try {
            	FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/MainMenu.fxml"));
        		AnchorPane menu = loader.load();
        		mainViewPane.setLeft(menu);
        		FXMLLoader loader2 = new FXMLLoader(getClass().getResource("/view/MainMainView.fxml"));
        		AnchorPane content = loader2.load();
        		((BorderPane)mainViewPane).setCenter(content);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
	*/
	
	@FXML
	public void goToMainPage(ActionEvent event) throws IOException {
		/*
		try {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/MainMenu.fxml"));
		AnchorPane menu = loader.load();
		mainViewPane.setLeft(menu);
		FXMLLoader loader2 = new FXMLLoader(getClass().getResource("/view/MainMainView.fxml"));
		AnchorPane content = loader2.load();
		((BorderPane)mainViewPane).setCenter(content);
		} catch(IOException e) {
			
		}
		*/
	}
	
	
	@FXML
	public void goToCalendar(ActionEvent event) throws IOException {
		/*
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/CalendarMenu.fxml"));
		AnchorPane menu = loader.load();
		((BorderPane)mainViewPane).setLeft(menu);
		WeekPage calendarView = new WeekPage();
		mainViewPane.setCenter(calendarView);
		*/
	}
	
	@FXML
	public void showDayView(ActionEvent event) throws IOException {

		DayPage calendarView = new DayPage();
		mainViewPane.setCenter(calendarView);
	}

	@FXML
	public void showWeekView(ActionEvent event) {
		WeekPage calendarView = new WeekPage();
        mainViewPane.setCenter(calendarView);
	}

	@FXML
	public void showMonthView(ActionEvent event) {
		MonthPage calendarView = new MonthPage();
        mainViewPane.setCenter(calendarView);
	}
	
}
