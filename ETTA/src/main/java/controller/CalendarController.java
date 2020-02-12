package controller;

import java.io.IOException;
import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

import com.calendarfx.model.Calendar;
import com.calendarfx.model.CalendarEvent;
import com.calendarfx.model.CalendarSource;
import com.calendarfx.model.Entry;

import javafx.event.EventHandler;

import com.calendarfx.model.Calendar.Style;

import model.Event;
import model.EventDAO;

public class CalendarController {
	
	EventDAO eventDAO = new EventDAO();
	
	public boolean checkIfEventExist(int id) {
		if(eventDAO.readEvent(id)==null) {
			return false;
		}
		return true;
	}
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
		CalendarSource myCalendarSource = new CalendarSource("My Calendars"); 
		Event [] events = eventDAO.readEventsFromOneCalendar("'birthdays'");
		for (Event event : events) {
			Entry entry = fromEventToEntry(event);
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
		System.out.println(checkIfEventExist(Integer.parseInt(evt.getEntry().getId())));
		//if(evt.isEntryAdded() && 
		if(checkIfEventExist(Integer.parseInt(evt.getEntry().getId()))==false) {
			System.out.println("added");
			Entry entry = evt.getEntry();
			Event newEvent = fromEntryToEvent(entry);
			eventDAO.createEvent(newEvent);
		}
		else {
			System.out.println("changed");
		}
	
	}
	public Date convertToDateViaSqlDate(LocalDate dateToConvert) {
	    return java.sql.Date.valueOf(dateToConvert);
	}
	
	public static java.sql.Time toSqlTime(LocalTime localTime) {
		return java.sql.Time.valueOf(localTime);
		}
	
	public Entry fromEventToEntry(Event event) {
		Entry entry = new Entry();
		entry.setTitle(event.getTitle());
		entry.setInterval(event.getStartDate().toLocalDate(), event.getEndDate().toLocalDate());
		entry.setFullDay(event.isFullday());
		entry.setInterval(event.getStartTime().toLocalTime(), event.getEndTime().toLocalTime());
		entry.setId(String.valueOf(event.getEvent_id()));
		entry.setRecurrenceRule(event.getRrule());
		return entry;
	 }
	  
	 public Event fromEntryToEvent(Entry entry) {
		Event event = new Event();
		Date startDate = convertToDateViaSqlDate(entry.getStartDate());
		Date endDate = convertToDateViaSqlDate(entry.getEndDate());
		Time startTime = toSqlTime(entry.getStartTime());
		Time endTime = toSqlTime(entry.getEndTime());
		event.setTitle(entry.getTitle());
		event.setCalendar(entry.getCalendar().getName());
		event.setEndDate(endDate);
		event.setStartDate(startDate);
		event.setEndTime(endTime);
		event.setStartTime(startTime);
		event.setFullday(entry.isFullDay());
		event.setRecurring(entry.isRecurring());
		event.setRrule(entry.getRecurrenceRule());
		return event;
	  }
}
