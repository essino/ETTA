package view;

import java.io.IOException;
import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

import com.calendarfx.model.Calendar;
import com.calendarfx.model.Calendar.Style;
import com.calendarfx.model.CalendarEvent;
import com.calendarfx.model.CalendarSource;
import com.calendarfx.model.Entry;
import com.calendarfx.view.CalendarView;
import com.calendarfx.view.page.DayPage;
import com.calendarfx.view.page.MonthPage;
import com.calendarfx.view.page.WeekPage;
import com.calendarfx.view.popover.EntryDetailsView;
import com.calendarfx.view.popover.EntryHeaderView;

import controller.CalendarController;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import model.Event;
import model.EventDAO;

/**
 * GUI class relating to the Calendar section
 */

public class CalendarGUI {
	
	EventDAO eventDAO = new EventDAO();
	CalendarController controller = new CalendarController();
	
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
	
	CalendarSource myCalendarSource;
	
	/**
	 * Method showing the day view in the Calendar section
	 * @param event ActionEvent that is handled
	 */
	@FXML
	public void showDayView(ActionEvent event) throws IOException {
		DayPage calendarView = new DayPage();
		mainPane.setCenter(calendarView);
		calendarView.getCalendarSources().addAll(controller.getCalendarSource());
	}

	/**
	 * Method showing the week view in the Calendar section
	 * @param event ActionEvent that is handled
	 */
	@FXML
	public void showWeekView(ActionEvent event) {
		WeekPage calendarView = new WeekPage();
        mainPane.setCenter(calendarView);
        calendarView.getCalendarSources().addAll(controller.getCalendarSource());
	}

	/**
	 * Method showing the month view in the Calendar section
	 * @param event ActionEvent that is handled
	 */
	@FXML
	public void showMonthView(ActionEvent event) {
		MonthPage calendarView = new MonthPage();
        mainPane.setCenter(calendarView);
        /*
        CalendarSource calendarSource = calendarView.getCalendarSources().get(0);
        Calendar defaultCalendar = calendarSource.getCalendars().get(0);
        EventHandler<CalendarEvent> handler = evt -> controller.handleCalendarEvent(evt);
        defaultCalendar.addEventHandler(handler);
		Event [] events = eventDAO.readEventsFromOneCalendar("'" + defaultCalendar.getName() + "'");
		System.out.println("default kalenterissa on " + events.length);
		for (Event event2 : events) {
			System.out.println("ladataan deafult calenterista");
			Entry entry = controller.fromEventToEntry(event2);
			System.out.println(event2.getTitle());
			System.out.println(event2.getCalendar());
			System.out.println(entry.getCalendar().getName());
			entry.setCalendar(defaultCalendar);
			defaultCalendar.addEntry(entry);
		}
		System.out.println("default calendar" + defaultCalendar.getName());
		//System.out.println("default calendars" + myCalendarSource.getCalendars());
		//EventHandler<CalendarEvent> handler = evt -> handleCalendarEvent(evt);
		//defaultCalendar.addEventHandler(handler);
		 * */
		 
        calendarView.getCalendarSources().addAll(controller.getCalendarSource());
        System.out.println("calendarSources" + calendarView.getCalendarSources());
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
	/*
	public CalendarSource getCalendarSource() {
		
		Calendar calendar2 = new Calendar("birthdays");
		calendar2.setStyle(Style.STYLE2);
		//calendar2.addEntries(eventDAO.readEventsFromOneCalendar("birthdays"));
		//calendar2.addEventHandler(CalendarEvent.ANY, evt -> handleEvent(evt));
		
		
		
		Calendar calendar3 = new Calendar("kids");
		calendar3.setStyle(Style.STYLE3);
		Calendar calendar4 = new Calendar("work");
		calendar4.setStyle(Style.STYLE4);
		Calendar calendar5 = new Calendar("health");
		calendar5.setStyle(Style.STYLE5);
		Calendar calendar6 = new Calendar("meetings");
		calendar6.setStyle(Style.STYLE6);
		Calendar calendar7 = new Calendar("culture");
		calendar7.setStyle(Style.STYLE7);
		myCalendarSource = new CalendarSource("My Calendars"); 
		Event [] events = eventDAO.readEventsFromOneCalendar("'birthdays'");
		for (Event event : events) {
			Entry entry = new Entry();
			entry.setTitle(event.getTitle());
			entry.setInterval(event.getStartDate().toLocalDate(), event.getEndDate().toLocalDate());
			entry.setFullDay(event.isFullday());
			entry.setInterval(event.getStartTime().toLocalTime(), event.getEndTime().toLocalTime());
			calendar2.addEntry(entry);
		}
		EventHandler<CalendarEvent> handler = evt -> handleCalendarEvent(evt);
		calendar2.addEventHandler(handler);
        myCalendarSource.getCalendars().addAll(calendar2, calendar3, calendar4, calendar5, calendar6, calendar7);
        System.out.println("calendars" + myCalendarSource.getCalendars());
        return myCalendarSource;
	}
	


	private void handleCalendarEvent(CalendarEvent evt) {
		System.out.println(evt.getEntry().getId() + " " + evt.getEntry().getTitle());
		System.out.println(controller.checkIfEventExist(Integer.parseInt(evt.getEntry().getId())));
		//if(evt.isEntryAdded() && 
		if(controller.checkIfEventExist(Integer.parseInt(evt.getEntry().getId()))==false) {
			System.out.println("added");
			Entry entry = evt.getEntry();
			Date startDate = convertToDateViaSqlDate(entry.getStartDate());
			Date endDate = convertToDateViaSqlDate(entry.getEndDate());
			Time startTime = toSqlTime(entry.getStartTime());
			Time endTime = toSqlTime(entry.getEndTime());
			Event newEvent = new Event(entry.getTitle(), entry.isFullDay(), startDate, endDate, startTime, endTime, entry.isRecurring(), entry.getRecurrenceRule(), entry.getCalendar().getName());
			eventDAO.createEvent(newEvent);
		}
		else {
			System.out.println("changed");
			/*
			Entry entry = evt.getEntry();
			Date startDate = convertToDateViaSqlDate(entry.getStartDate());
			Date endDate = convertToDateViaSqlDate(entry.getEndDate());
			Time startTime = toSqlTime(entry.getStartTime());
			Time endTime = toSqlTime(entry.getEndTime());
			Event newEvent = new Event(entry.getTitle(), entry.isFullDay(), startDate, endDate, startTime, endTime, entry.isRecurring(), entry.getRecurrenceRule(), entry.getCalendar().getName());
			System.out.println("event " + newEvent.getTitle());
			//eventDAO.createEvent(newEvent);
			
		}
	
	}
	public Date convertToDateViaSqlDate(LocalDate dateToConvert) {
	    return java.sql.Date.valueOf(dateToConvert);
	}
	  public static java.sql.Time toSqlTime(LocalTime localTime) {
		    return java.sql.Time.valueOf(localTime);
		  }
*/
}
