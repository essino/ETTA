package controller;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;

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
	/**
	 * EventDAO used for accessing the database
	 */
	EventDAO eventDAO = new EventDAO();
	
	/** 
	 * Method that checks if the event already exists in the database.
	 * @param id - entry/event id
	 * @return false if the event already exists in the database
	 * @return true if the event is in database already
	 */
	public boolean checkIfEventExist(int id) {
		if(eventDAO.readEvent(id)==null) {
			return false;
		}
		return true;
	}
	
	/** 
	 * Method that creates CalendarsSource with calendars
	 * @return CalendarSource 
	 */
	public CalendarSource getCalendarSource() {
		Calendar calendar2 = new Calendar("birthdays");
		calendar2.setStyle(Style.STYLE2);
		Calendar calendar3 = new Calendar("kids");
		calendar3.setStyle(Style.STYLE3);
		Calendar calendar4 = new Calendar("work");
		calendar4.setStyle(Style.STYLE4);
		Calendar calendar5 = new Calendar("wishlist");
		calendar5.setStyle(Style.STYLE5);
		Calendar calendar6 = new Calendar("free time");
		calendar6.setStyle(Style.STYLE6);
		Calendar calendar7 = new Calendar("borrowed");
		calendar7.setStyle(Style.STYLE7);
		
		CalendarSource myCalendarSource = new CalendarSource("My Calendars"); 
		myCalendarSource.getCalendars().addAll(calendar2, calendar3, calendar4, calendar5, calendar6, calendar7);
		ObservableList<Calendar> calendars = myCalendarSource.getCalendars();
		EventHandler<CalendarEvent> handler = evt -> handleCalendarEvent(evt);
		for(Calendar calendar : calendars) {
			Event [] events = eventDAO.readEventsFromOneCalendar("'" + calendar.getName() + "'");
			for (Event event : events) {
				Entry entry = fromEventToEntry(event);
				calendar.addEntry(entry);
			}
			calendar.addEventHandler(handler);
		}
        return myCalendarSource;
	}
	
	/** 
	 * Method that handles CalendarEvents
	 * @param event - Event that was created, edited or deleted
	 */
	public void handleCalendarEvent(CalendarEvent evt) {
		System.out.println("entryId 79 " + evt.getEntry().getId());
		//Entry entry = evt.getEntry();
		//Event newEvent = fromEntryToEvent(entry);
		//System.out.println("entryId 82 " + entry.getId());
		//System.out.println("eventId 83 " + newEvent.getEvent_id());
		if(evt.isEntryRemoved()) {
			System.out.println("entryId 85 " + evt.getEntry().getId());
			Entry entry = evt.getEntry();
			Event newEvent = fromEntryToEvent(entry);
			eventDAO.deleteEvent(newEvent.getEvent_id());
		}
		
		else if(checkIfEventExist(Integer.parseInt(evt.getEntry().getId()))==false) {
			System.out.println("entryId 92 " + evt.getEntry().getId());
			Entry entry = evt.getEntry();
			Event newEvent = fromEntryToNewEvent(entry);
			eventDAO.createEvent(newEvent);
		}
		else if(evt.isEntryAdded()) {
		
		}
		else {
			System.out.println("entryId 101 " + evt.getEntry().getId());
				Entry entry = evt.getEntry();
				Event newEvent = fromEntryToEvent(entry);
				eventDAO.updateEvent(newEvent);
			}

	}
	
	/** 
	 * Method that converts LocalDate to sql Date
	 * @param LocalDate that will be converted
	 * @return Date converted from LocalDate
	 */
	public Date convertToDateViaSqlDate(LocalDate dateToConvert) {
	    return java.sql.Date.valueOf(dateToConvert);
	}
	
	/** 
	 * Method that converts LocalTime to sql Time
	 * @param LocalTime that will be converted
	 * @return Time converted from LocalTime
	 */
	public static java.sql.Time toSqlTime(LocalTime localTime) {
		return java.sql.Time.valueOf(localTime);
		}
	
	/** 
	 * Method that creates an Entry from Event
	 * @param Event - event to be converted intoEntry
	 * @return Entry - entry converted  from  Event
	 */ 
	public Entry fromEventToEntry(Event event) {
		Entry entry = new Entry();
		entry.setTitle(event.getTitle());
		entry.setLocation(event.getLocation());
		entry.setInterval(event.getStartDate().toLocalDate(), event.getEndDate().toLocalDate());
		entry.setFullDay(event.isFullday());
		if(!event.isFullday()) {
			entry.setInterval(event.getStartTime().toLocalTime(), event.getEndTime().toLocalTime());
		}
		entry.setId(String.valueOf(event.getEvent_id()));
		entry.setRecurrenceRule(event.getRrule());
		System.out.println("eventId 136 " + event.getEvent_id());
		System.out.println("entryId 137 " + entry.getId());
		return entry;
	 }
	  
	/** 
	 * Method that creates an Event from Entry
	 * @param Entry - entry to be converted into Event
	 * @return Event - event converted  from Entry
	 */ 
	 public Event fromEntryToEvent(Entry entry) {
		Event event = new Event();
		Date startDate = convertToDateViaSqlDate(entry.getStartDate());
		Date endDate = convertToDateViaSqlDate(entry.getEndDate());
		Time startTime = toSqlTime(entry.getStartTime());
		Time endTime = toSqlTime(entry.getEndTime());
		event.setTitle(entry.getTitle());
		event.setLocation(entry.getLocation());
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
	 
	 public Event fromEntryToNewEvent(Entry entry) {
			Event event = new Event();
			Date startDate = convertToDateViaSqlDate(entry.getStartDate());
			Date endDate = convertToDateViaSqlDate(entry.getEndDate());
			Time startTime = toSqlTime(entry.getStartTime());
			Time endTime = toSqlTime(entry.getEndTime());
			event.setTitle(entry.getTitle());
			event.setLocation(entry.getLocation());
			event.setEndDate(endDate);
			event.setStartDate(startDate);
			event.setEndTime(endTime);
			event.setStartTime(startTime);
			event.setFullday(entry.isFullDay());
			event.setRecurring(entry.isRecurring());
			event.setRrule(entry.getRecurrenceRule());
			//event.setEvent_id(Integer.parseInt(entry.getId()));
			if(event.getCalendar()==null) {
				event.setCalendar("Default");
			}
			else {
				event.setCalendar(entry.getCalendar().getName());
			}
			return event;
		  }
	 
		/** 
		 * Method that returns default CalendarsSource with the default calendar
		 * @return CalendarSource 
		 */
	 public CalendarSource getDefaultCalendarSource(CalendarView calendarView) {
		 CalendarSource calendarSource = calendarView.getCalendarSources().get(0);
	        Calendar defaultCalendar = calendarSource.getCalendars().get(0);
	        EventHandler<CalendarEvent> handler = evt -> handleCalendarEvent(evt);
	        defaultCalendar.addEventHandler(handler);
			Event [] events = eventDAO.readEventsFromOneCalendar("'" + defaultCalendar.getName() + "'");
			for (Event event2 : events) {
				Entry entry = fromEventToEntry(event2);
				entry.setCalendar(defaultCalendar);
				defaultCalendar.addEntry(entry);
			}
			
		 return calendarSource;
	 }
}
