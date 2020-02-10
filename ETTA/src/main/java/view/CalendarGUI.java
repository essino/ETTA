package view;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;

import com.calendarfx.model.Calendar;
import com.calendarfx.model.Entry;
import com.calendarfx.view.page.DayPage;
import com.calendarfx.view.page.MonthPage;
import com.calendarfx.view.page.WeekPage;
import com.calendarfx.view.popover.EntryDetailsView;
import com.calendarfx.view.popover.EntryHeaderView;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

/**
 * GUI class relating to the Calendar section
 */

public class CalendarGUI {
	
	/**
	 * The menu view to which the alternative views in the Calendar section are added
	 */
	@FXML 
	BorderPane mainPane;
	
	@FXML
	Button calendarButton;
	
	@FXML
	AnchorPane CalendarMenu;
	
	@FXML
	AnchorPane CalendarAddPane;

	@FXML
	Button CalendarDay;
	
	@FXML
	Button CalendarWeek;
	
	@FXML
	Button CalendarMonth;
	
	@FXML
	Button CalendarAdd;
	
	@FXML
	VBox calendarNewEvent;
	
	/**
	 * Method showing the day view in the Calendar section
	 * @param event ActionEvent that is handled
	 */
	@FXML
	public void showDayView(ActionEvent event) throws IOException {
		DayPage calendarView = new DayPage();
		mainPane.setCenter(calendarView);
	}

	/**
	 * Method showing the week view in the Calendar section
	 * @param event ActionEvent that is handled
	 */
	@FXML
	public void showWeekView(ActionEvent event) {
		WeekPage calendarView = new WeekPage();
        mainPane.setCenter(calendarView);
	}

	/**
	 * Method showing the month view in the Calendar section
	 * @param event ActionEvent that is handled
	 */
	@FXML
	public void showMonthView(ActionEvent event) {
		MonthPage calendarView = new MonthPage();
        mainPane.setCenter(calendarView);
	}
	
	/**
	 * Method showing the add event view in the Calendar section
	 * @param event ActionEvent that is handled
	 */
	@FXML
	public void showAddView(ActionEvent event) {
		/*
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/CalendarAdd.fxml"));
		AnchorPane calendarAddView = null;
		try {
			calendarAddView = loader.load();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		Calendar days = new Calendar("days");
		Entry<Object> newEntry = new Entry<>("new entry");
		newEntry.changeStartDate(LocalDate.now());
		days.addEntry(newEntry);
		ArrayList<Calendar> calendars = new ArrayList();
		calendars.add(days);
		EntryHeaderView header = new EntryHeaderView(newEntry, calendars);
		header.setMaxWidth(400);
		EntryDetailsView newentry = new EntryDetailsView(newEntry);
		VBox addNewVBox = new VBox();
		Button addNewEventButton = new Button();
		addNewEventButton.setText("Save");
		addNewVBox.setPrefSize(400, 400);
		addNewVBox.getChildren().addAll(header, newentry, addNewEventButton);
		addNewVBox.setPadding(new Insets(10, 50, 50, 50));
		mainPane.setCenter(addNewVBox);
		//calendarNewEvent.getChildren().addAll(header, newentry);
	}
}
