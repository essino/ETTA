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
import com.calendarfx.view.CalendarView;

import javafx.collections.ObservableList;
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
		myCalendarSource.getCalendars().addAll(calendar2, calendar3, calendar4, calendar5, calendar6, calendar7);
		ObservableList<Calendar> calendars = myCalendarSource.getCalendars();
		EventHandler<CalendarEvent> handler = evt -> handleCalendarEvent(evt);
		for(Calendar calendar : calendars) {
			//System.out.println("calendar" + "'" + calendar.getName() + "'");
			Event [] events = eventDAO.readEventsFromOneCalendar("'" + calendar.getName() + "'");
			for (Event event : events) {
				Entry entry = fromEventToEntry(event);
				calendar.addEntry(entry);
			}
			calendar.addEventHandler(handler);
		}
		
        //System.out.println("calendars" + myCalendarSource.getCalendars());
        return myCalendarSource;
	}
	
	public void handleCalendarEvent(CalendarEvent evt) {
		if(evt.isEntryRemoved()) {
			Entry entry = evt.getEntry();
			Event newEvent = fromEntryToEvent(entry);
			eventDAO.deleteEvent(newEvent.getEvent_id());
		}
		//else if(evt.isEntryAdded()) {
		else if(checkIfEventExist(Integer.parseInt(evt.getEntry().getId()))==false) {
			System.out.println("added");
			Entry entry = evt.getEntry();
			Event newEvent = fromEntryToEvent(entry);
			eventDAO.createEvent(newEvent);
		}
		else {
				System.out.println("changed");
				Entry entry = evt.getEntry();
				Event newEvent = fromEntryToEvent(entry);
				eventDAO.updateEvent(newEvent);
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
		if(!event.isFullday()) {
			entry.setInterval(event.getStartTime().toLocalTime(), event.getEndTime().toLocalTime());
		}
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
		event.setEndDate(endDate);
		event.setStartDate(startDate);
		event.setEndTime(endTime);
		event.setStartTime(startTime);
		event.setFullday(entry.isFullDay());
		event.setRecurring(entry.isRecurring());
		event.setRrule(entry.getRecurrenceRule());
		event.setEvent_id(Integer.parseInt(entry.getId()));
		if(event.getCalendar()==null) {
			event.setCalendar("Default");
		}
		else {
			event.setCalendar(entry.getCalendar().getName());
		}
		return event;
	  }
	 
	 public CalendarSource getDeafultCalendarSource(CalendarView calendarView) {
		 CalendarSource calendarSource = calendarView.getCalendarSources().get(0);
	        Calendar defaultCalendar = calendarSource.getCalendars().get(0);
	        EventHandler<CalendarEvent> handler = evt -> handleCalendarEvent(evt);
	        defaultCalendar.addEventHandler(handler);
			Event [] events = eventDAO.readEventsFromOneCalendar("'" + defaultCalendar.getName() + "'");
			for (Event event2 : events) {
				System.out.println("ladataan deafult calenterista");
				Entry entry = fromEventToEntry(event2);
				entry.setCalendar(defaultCalendar);
				defaultCalendar.addEntry(entry);
			}
			
		 return calendarSource;
	 }
}
