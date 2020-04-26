package view.calendar;
import java.time.DayOfWeek;
import java.time.temporal.WeekFields;

import com.calendarfx.view.CalendarView;

import controller.CalendarController;
import res.MyBundle;

public class MyCalendarView extends CalendarView{
	
	CalendarController calendarController = new CalendarController();
	MyBundle myBundle = MyBundle.getInstance();

	public CalendarView getMyCalendarView() {
		CalendarView calendarView  = new CalendarView();
		System.out.println("new calendar");
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
