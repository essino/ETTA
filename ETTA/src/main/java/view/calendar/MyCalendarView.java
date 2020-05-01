package view.calendar;

import java.time.DayOfWeek;
import java.time.temporal.WeekFields;

import com.calendarfx.view.CalendarView;
import com.calendarfx.view.DateControl;

import controller.CalendarController;
import res.MyBundle;

public class MyCalendarView extends CalendarView{
	
	/**
	 * Reference to the used CalendarController
	 */
	CalendarController calendarController = new CalendarController();
	/**
	 * MyBundle object for setting the right resource bundle to localize the application
	 */
	MyBundle myBundle = new MyBundle();
	
	/** 
	 * Method that creates and returns a CalendarView - a full calendar view with multiple pages 
	 * for displaying a single day, a week,a month, and an entire year. CalendarSources are taken from the CalendarController.
	 * Dapanding of the current locale, weeks start from Monday or Sunday.
	 * @return CalendarView a full calendar view with multiple pages 
	 */
	public CalendarView getMyCalendarView() {
		CalendarView calendarView  = new CalendarView();
		calendarView.setShowAddCalendarButton(false);
		if(myBundle.getBundle().getString("language").equals("fi")) {
			calendarView.setWeekFields(WeekFields.of(DayOfWeek.MONDAY,1));
		}
		else {
			calendarView.setWeekFields(WeekFields.of(DayOfWeek.SUNDAY,1));
		}

		calendarView.getCalendarSources().addAll(calendarController.getCalendarSource());
		calendarController.getDefaultCalendarSource(calendarView);
		return calendarView;
	}
}
