package controller;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Locale;

import com.calendarfx.model.Calendar;
import com.calendarfx.model.CalendarEvent;
import com.calendarfx.model.CalendarSource;
import com.calendarfx.model.Entry;
import com.calendarfx.view.CalendarView;

import javafx.collections.ObservableList;
import javafx.event.EventHandler;

import com.calendarfx.model.Calendar.Style;

import model.BorrowedThing;
import model.Event;
import model.EventDAO;
import model.Item;

/** 
 * Controller class for the calendar. This class is in charge of connecting EventDAO with other controllers and GUIs.  
 * @author Lena
 */
public class CalendarController {
	
	/**
	 * EventDAO used for accessing the database
	 */
	EventDAO eventDAO = new EventDAO();
	
	//used for tests
	public CalendarController(EventDAO eventDAO2) {
		this.eventDAO = eventDAO2;
	}

	/**
	 * Constructor to create controller for Calendar
	 */
	public CalendarController() {
		
	}

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
	 * Method that creates CalendarsSource with calendars. There are 6 calendars created: birthdays, kids, work, borrowed,
	 * wishlist and freetime. Each calendar gets it 's own style, so that they are shown with a different color in the calendar.
	 * After the calendars are created, the program reads events from the database for each of the Calendars,
	 * converts them into CalendarFX Entries and adds to the calendars in the CalendarSource. 
	 * @return CalendarSource calendarSource consisting of 6 calendars
	 */
	public CalendarSource getCalendarSource() {
		Calendar calendar2 = new Calendar("birthdays");
		calendar2.setStyle(Style.STYLE2);
		Calendar calendar3 = new Calendar("kids");
		calendar3.setStyle(Style.STYLE3);
		Calendar calendar4 = new Calendar("work");
		calendar4.setStyle(Style.STYLE4);
		Calendar calendar5 = new Calendar("borrowed");
		calendar5.setStyle(Style.STYLE5);
		Calendar calendar6 = new Calendar("wishlist");
		calendar6.setStyle(Style.STYLE6);
		Calendar calendar7 = new Calendar("free time");
		calendar7.setStyle(Style.STYLE7);
		
		CalendarSource myCalendarSource = new CalendarSource("My Calendars"); 
		myCalendarSource.getCalendars().addAll(calendar2, calendar3, calendar4, calendar5, calendar6, calendar7);
		ObservableList<Calendar> calendars = myCalendarSource.getCalendars();
		EventHandler<CalendarEvent> handler = evt -> handleCalendarEvent(evt);
		for(Calendar calendar : calendars) {
			Event [] events = eventDAO.readEventsFromOneCalendar("'" + calendar.getName() + "'", false);
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
	 * @param CalendarEvent event - creation, editing or deleting of the event
	 */
	public void handleCalendarEvent(CalendarEvent evt) {
		//calendar entry is removed from the calendar, event needs to me removed from the database
		if(evt.isEntryRemoved()) {
			Entry entry = evt.getEntry();
			Event newEvent = fromEntryToEvent(entry);
			eventDAO.deleteEvent(newEvent.getEvent_id());
		}
		//if an event can't be found in the database, it shows that calendar entry is added to the calendar, event needs to me added the database
		else if(checkIfEventExist(Integer.parseInt(evt.getEntry().getId()))==false) {
			Entry entry = evt.getEntry();
			Event newEvent = fromEntryToEvent(entry);
			eventDAO.createEvent(newEvent);
		}
		//calendar entry is updated in the calendar, event needs to me updated in the database
		else {
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
	 * Method that creates a CalendarFX Entry from Event from the database
	 * @param Event - event to be converted into Entry
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
		Calendar calendar = new Calendar(event.getCalendar());
		System.out.println(" calendar rivi 135 " + event.getCalendar());
		entry.setCalendar(calendar);
		System.out.println(" calendar rivi 137 " + entry.getCalendar().getName());
		return entry;
	 }
	  
	/** 
	 * Method that creates a database Event from a CalendarFX Entry
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
		try {
		event.setCalendar(entry.getCalendar().getName());
		}
		catch (NullPointerException e) {
			event.setCalendar("Default");
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
			Event [] events = eventDAO.readEventsFromOneCalendar("'" + defaultCalendar.getName() + "'", false);
			for (Event event2 : events) {
				Entry entry = fromEventToEntry(event2);
				entry.setCalendar(defaultCalendar);
				defaultCalendar.addEntry(entry);
			}
			
		 return calendarSource;
	 }

		/** 
		 * Boolean method that gets two Strings as parameters and updates a database  birthday Event 
		 * @param String oldname - the old name of the Person/the old title of the birthday event
		 * @param String newname - the new name of the Person/the newtitle of the birthday event
		 * @return true - if birthday event was successfully updated
		 * @return false - if updating birthday event didn't succeed 
		 */ 
	 //update birthday event if name changes
		public boolean updateBirthday(String oldName, String newName) {
			Event birthdayEvent = eventDAO.readBirthday(oldName);
			birthdayEvent.setTitle(newName);
			return eventDAO.updateEvent(birthdayEvent);
		}
		
		/** 
		 * Boolean method that gets two Strings as parameters and updates a database  birthday Event.
		 * Method first checks if there was a birthday event already. If there was none, a new birthday event is created. 
		 * @param String name - the name of the Person/the title of the birthday event
		 * @param Date oldDate - the old date of the Person's birthday
		 * @param Date newDate - the new date of the Person's birthday
		 * @return true - if birthday event was successfully updated
		 * @return false - if updating birthday event didn't succeed 
		 */ 
		//update birthday event if date changes
		public boolean updateBirthday(String name, Date oldDate, Date birthday) {
			Event birthdayEvent = eventDAO.readBirthday(name);
			//there was a birthday event already
			if(birthdayEvent!=null) {
				birthdayEvent.setStartDate(birthday);
				birthdayEvent.setEndDate(birthday);
				return eventDAO.updateEvent(birthdayEvent);
			}
			//no event before, let's create it
			else {
				return createBirthday(name, birthday);
			}
		}
		
		/** 
		 * Boolean method that gets a String containing old Item description and an Item that was updated as parameters and updates a database  wishlist Event.
		 * Method first checks if there was a wishlist event already.
		 * @param String oldDescription - the name of the Person/the title of the birthday event
		 * @param Item editedItem - the Item that was edited
		 * @return true - if wishlist event was successfully updated
		 * @return false - if updating wishlist event didn't succeed 
		 */
		//update wishlist event if item description changes
		public boolean updateWishlistDescription(String oldDescription, Item editedItem) {
			boolean updated = false;
			String oldEvent = "Buy " + oldDescription + " for " + editedItem.getPerson().getName();
			System.out.println("old wishlist event" + oldEvent);
			Event wishlistEvent = eventDAO.readWishlistEvent(oldEvent);
			if(wishlistEvent != null) {
				wishlistEvent.setTitle("Buy " + editedItem.getDescription() + " for " + editedItem.getPerson().getName());
				updated = eventDAO.updateEvent(wishlistEvent);
			}
			return updated;
		}

		/** 
		 * Boolean method that gets a String containing old name of a person whom an Item was for 
		 * and an Item that was updated as parameters and updates a database  wishlist Event.
		 * Method first checks if there was a wishlist event already.
		 * @param String oldName - the old name of the Person that the Item was for
		 * @param Item editedItem - the Item that was edited
		 * @return true - if wishlist event was successfully updated
		 * @return false - if updating wishlist event didn't succeed 
		 */
		//update wishlist event if person changes
		public boolean updateWishlistPerson(String oldName, Item editedItem) {
			boolean updated = false;
			String oldEvent = "Buy " + editedItem.getDescription() + " for " + oldName;
			Event wishlistEvent = eventDAO.readWishlistEvent(oldEvent);
			if(wishlistEvent != null) {
				wishlistEvent.setTitle("Buy " + editedItem.getDescription() + " for " + editedItem.getPerson().getName());
				updated = eventDAO.updateEvent(wishlistEvent);
			}
			return updated;
		}

		/** 
		 * Boolean method that gets a Date containing old date when an Item was needed to be bought
		 * and an Item that was updated as parameters and updates a database  wishlist Event.
		 * Method first checks if there was a wishlist event already.
		 * @param Date oldDate - the old date when the Item was needed to be bought
		 * @param Item editedItem - the Item that was edited
		 * @return true - if wishlist event was successfully updated
		 * @return false - if updating wishlist event didn't succeed 
		 */
		//update wishlist event if date changes
		public boolean updateWishlistDate(Date oldDate, Item editedItem) {
			boolean updated = false;
			String event = null;
			if (editedItem.getPerson() != null) {
				event = "Buy " + editedItem.getDescription() + " for " + editedItem.getPerson().getName();
			} else {
				event = "Buy " + editedItem.getDescription() + " for myself";
			}
			Event wishlistEvent = eventDAO.readWishlistEvent(event);
			if (wishlistEvent != null) {
				wishlistEvent.setStartDate(editedItem.getDateNeeded());
				wishlistEvent.setEndDate(editedItem.getDateNeeded());
				updated = eventDAO.updateEvent(wishlistEvent);
			}
			return updated;
		}
		
		/** 
		 * Boolean method that gets a String containing Person's name and a Date containing Person's birthday as parameters 
		 * and creates a database  birthday Event.
		 * Method first checks if there was a birthday event already. If there was none, a new birthday event is created. 
		 * @param String name - the name of the Person/the future title of the birthday event
		 * @param Date birthday - the date of the Person's birthday
		 * @return true - if birthday event was successfully created
		 * @return false - if creating birthday event didn't succeed 
		 */ 
		public boolean createBirthday(String name, Date birthday) {
			Event birthdayEvent = new Event();
			int lastEvent = eventDAO.readEvents().length; 
			int lastEventId =1;
			if(lastEvent != 0 ) {
				lastEventId = eventDAO.readEvents()[lastEvent-1].getEvent_id();
			}
			birthdayEvent = new Event();
			birthdayEvent.setTitle(name);
			birthdayEvent.setEvent_id(lastEventId+1);
			birthdayEvent.setCalendar("birthdays");
			birthdayEvent.setStartDate(birthday);
			birthdayEvent.setEndDate(birthday);
			birthdayEvent.setFullday(true);
			birthdayEvent.setRecurring(true);
			birthdayEvent.setRrule("RRULE:FREQ=YEARLY;");
			return eventDAO.createEvent(birthdayEvent);
		}

		/** 
		 * Boolean method that gets a Date containing old date when a borrowed thing should be returned
		 * and the borrowed thing that was updated as parameters and updates a database borrowed Event.
		 * Method first checks if there was a borrowed event already.
		 * @param Date oldDate - the old date when when a borrowed thing should be returned
		 * @param BorrowedThing the borrowed thing that was updated
		 * @return true - if borrowed event was successfully updated
		 * @return false - if updating borrowed event didn't succeed 
		 */
		//update borrowed event if date changes
		public boolean updateBorrowedDate(BorrowedThing thing) {
			boolean updated = false;
			String eventTitle = thing.getPerson().getName() + " should return " + thing.getDescription();
			Event borrowedEvent = eventDAO.readBorrowed(eventTitle);
			if(borrowedEvent != null) {
				borrowedEvent.setStartDate(thing.getReturnDate());
				borrowedEvent.setEndDate(thing.getReturnDate());
				updated = eventDAO.updateEvent(borrowedEvent);
			}
			return updated;
		}
		
		/** 
		 * Boolean method that gets a String containing old borrowed thing's description 
		 * and the borrowed thing that was updated as parameters and updates a database borrowed Event.
		 * Method first checks if there was a borrowed event already.
		 * @param String  - the old description of the updated borrowed thing 
		 * @param BorrowedThing the borrowed thing that was updated
		 * @return true - if borrowed event was successfully updated
		 * @return false - if updating borrowed event didn't succeed 
		 */
		//update borrowed event if borrowed thing's description changes
		public boolean updateBorrowedTitle(String oldTitle, BorrowedThing thing) {
			boolean updated = false;
			String eventTitle = thing.getPerson().getName() + " should return " + oldTitle;
			Event borrowedEvent = eventDAO.readBorrowed(eventTitle);
			if(borrowedEvent != null) {
				String newEvent = thing.getPerson().getName() + " should return " + thing.getDescription();
				borrowedEvent.setTitle(newEvent);
				updated = eventDAO.updateEvent(borrowedEvent);
			}
			return updated;
		}
}
