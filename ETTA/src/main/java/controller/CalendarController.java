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

import model.BorrowedThing;
import model.Event;
import model.EventDAO;
import model.Item;
import model.Person;

/** 
 * Controller class for the calendar. This class is in charge of connecting EventDAO with CalendarFX, other controllers and GUIs.  
 * @author Lena, Essi, Tiina
 */
public class CalendarController {
	
	/**
	 * EventDAO used for accessing the database
	 */
	private EventDAO eventDAO = new EventDAO();
	
	//used for tests
	/**
	 * Constructor to create controller for Calendar, used in tests
	 * @param eventDAO EventDAO 
	 */
	public CalendarController(EventDAO eventDAO2) {
		this.eventDAO = eventDAO2;
	}

	/**
	 * Constructor to create controller for Calendar without parameters
	 */
	public CalendarController() {
		
	}

	/** 
	 * Method that checks if the event already exists in the database.
	 * @param id - entry/event id
	 * @return false if the event doesn't exist in the database already, true if the event is in the database already
	 */
	public boolean checkIfEventExist(int id) {
		if(eventDAO.readEvent(id)==null) {
			return false;
		}
		return true;
	}
	
	/** 
	 * Method that creates CalendarsSource with calendars. There are 6 calendars created: birthdays, kids, work, borrowed,
	 * wishlist and freetime. Each calendar gets its own style, so that they are shown with a different color in the calendar.
	 * After the calendars are created, the program reads events from the database for each of the Calendars,
	 * converts them into CalendarFX Entries and adds to the calendars in the CalendarSource. 
	 * @return calendarSource CalendarSource consisting of 6 calendars
	 */
	public CalendarSource getCalendarSource() {
		//Calendars created and styles set
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
		
		//calendarsource created and calendars added
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
	 * @param evt CalendarEvent - creation, editing or deleting of the event
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
	 * @param dateToConvert LocalDate that will be converted
	 * @return Date converted from LocalDate
	 */
	public Date convertToDateViaSqlDate(LocalDate dateToConvert) {
	    return java.sql.Date.valueOf(dateToConvert);
	}
	
	/** 
	 * Method that converts LocalTime to sql Time
	 * @param localTime LocalTime that will be converted
	 * @return Time converted from LocalTime
	 */
	public static java.sql.Time toSqlTime(LocalTime localTime) {
		return java.sql.Time.valueOf(localTime);
		}
	
	/** 
	 * Method that creates a CalendarFX Entry used in the app from Event class from the database
	 * @param event - Event to be converted into Entry
	 * @return entry - Entry converted  from  Event
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
		entry.setCalendar(calendar);
		return entry;
	 }
	  
	/** 
	 * Method that creates a database class Event from a CalendarFX Entry used in the app
	 * @param entry - Entry to be converted into Event
	 * @return event - Event converted  from Entry
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
		 * @param calendarView - the calendarView for which the default calendarSource is for
		 * @return calendarSource - calendaresSource with the events from the default calendar 
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

	 //birthdays part
		/** 
		 * Boolean method that gets a String containing Person's name and a Date containing Person's birthday as parameters 
		 * and creates a database  birthday Event.
		 * Method first checks if there was a birthday event already. If there was none, a new birthday event is created. 
		 * @param name - String giving the name of the Person/the future title of the birthday event
		 * @param birthday - the date of the Person's birthday
		 * @return true - if birthday event was successfully created, false - if creating birthday event didn't succeed
		 */ 
		public boolean createBirthday(String name, Date birthday) {
			Event birthdayEvent = new Event();
			birthdayEvent = new Event();
			birthdayEvent.setTitle(name);
			birthdayEvent.setEvent_id(getNextId());
			birthdayEvent.setCalendar("birthdays");
			birthdayEvent.setStartDate(birthday);
			birthdayEvent.setEndDate(birthday);
			//birthday events are always fullday
			birthdayEvent.setFullday(true);
			//birthday events are always recurring, same day each year
			birthdayEvent.setRecurring(true);
			birthdayEvent.setRrule("RRULE:FREQ=YEARLY;");
			return eventDAO.createEvent(birthdayEvent);
		}
		
		/** 
		 * Boolean method that gets two Strings as parameters and updates a database  birthday Event 
		 * @param oldname - the old name of the Person/the old title of the birthday event
		 * @param newname - the new name of the Person/the newtitle of the birthday event
		 * @return true - if birthday event was successfully updated, false - if updating birthday event didn't succeed  
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
		 * @param name - the name of the Person/the title of the birthday event
		 * @param oldDate - the old date of the Person's birthday
		 * @param newDate - the new date of the Person's birthday
		 * @return true - if birthday event was successfully updated, false - if updating birthday event didn't succeed 
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
		
		//wishlist part
		/** 
		 * Boolean method that creates a new wishlist event
		 * @param item  - the new item from the wishlist the event will be created for
		 * @return true - if wishlist event was successfully created, false - if creating of the wishlist event didn't succeed 
		 */ 
		public boolean createWishlistEvent(Item item) {
			Event wishlistEvent = new Event();
			wishlistEvent.setEvent_id(getNextId());
			wishlistEvent.setStartDate(item.getDateNeeded());
			wishlistEvent.setEndDate(item.getDateNeeded());
			//wishlist events are always full day events
			wishlistEvent.setFullday(true);
			if (item.getPerson() != null) {
				wishlistEvent.setTitle("Buy " + item.getDescription() + " for " + item.getPerson().getName());
			} else {
				wishlistEvent.setTitle("Buy " + item.getDescription() + " for myself");
			}
			wishlistEvent.setLocation(null);
			//birthday events are not recurring by default, can be changed in the app in the calendar view
			wishlistEvent.setRecurring(false);
			wishlistEvent.setCalendar("wishlist");
			return eventDAO.createEvent(wishlistEvent); 
		}
		
		/** 
		 * Boolean method that gets a String containing old Item description and an Item that was updated as parameters and updates a database  wishlist Event.
		 * Method first checks if there was a wishlist event already.
		 * @param oldDescription - the name of the Person/the title of the birthday event
		 * @param editedItem - the Item that was edited
		 * @return true - if wishlist event was successfully updated, false - if updating wishlist event didn't succeed 
		 */
		//update wishlist event if item description changes
		public boolean updateWishlistDescription(String oldDescription, Item editedItem) {
			boolean updated = false;
			//hardcoded name of the wishlist event, must be changed if the database is localized
			String oldEvent = "Buy " + oldDescription + " for " + editedItem.getPerson().getName();
			System.out.println("old wishlist event" + oldEvent);
			Event wishlistEvent = eventDAO.readWishlistEvent(oldEvent);
			//update ecent if it existed already
			if(wishlistEvent != null) {
				wishlistEvent.setTitle("Buy " + editedItem.getDescription() + " for " + editedItem.getPerson().getName());
				updated = eventDAO.updateEvent(wishlistEvent);
			}
			//create event if there was none
			else {
				updated = createWishlistEvent(editedItem);
			}
			return updated;
		}

		/** 
		 * Boolean method that gets a String containing old name of a person whom an Item was for 
		 * and an Item that was updated as parameters and updates a database  wishlist Event.
		 * Method first checks if there was a wishlist event already.
		 * @param oldName - the old name of the Person that the Item was for
		 * @param editedItem - the Item that was edited
		 * @return true - if wishlist event was successfully updated, false - if updating wishlist event didn't succeed 
		 */
		//update wishlist event if person changes
		public boolean updateWishlistPerson(String oldName, Item editedItem) {
			boolean updated = false;
			//hardcoded name of the wishlist event, must be changed if the database is localized
			String oldEvent = "Buy " + editedItem.getDescription() + " for " + oldName;
			Event wishlistEvent = eventDAO.readWishlistEvent(oldEvent);
			if(wishlistEvent != null) {
				wishlistEvent.setTitle("Buy " + editedItem.getDescription() + " for " + editedItem.getPerson().getName());
				updated = eventDAO.updateEvent(wishlistEvent);
			}
			else {
				updated = createWishlistEvent(editedItem);
			}
			return updated;
		}

		/** 
		 * Boolean method that gets a Date containing old date when an Item was needed to be bought
		 * and an Item that was updated as parameters and updates a database  wishlist Event.
		 * Method first checks if there was a wishlist event already.
		 * @param oldDate - the old date when the Item was needed to be bought
		 * @param editedItem - the Item that was edited
		 * @return true - if wishlist event was successfully updated, false - if updating wishlist event didn't succeed 
		 */
		//update wishlist event if date changes
		public boolean updateWishlistDate(Date oldDate, Item editedItem) {
			boolean updated = false;
			String event = null;
			if (editedItem.getPerson() != null) {
				//hardcoded name of the wishlist event, must be changed if the database is localized
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
			else {
				updated = createWishlistEvent(editedItem);
			}
			return updated;
		}
		
		/** 
		 * Method for finding the event connected to the selected item from the database
		 * @param item the Item the event is connected to
		 * @return event - the found event from the database, null if the event wasn't found
		 */ 
		public Event findWishlistEvent(Item item) {
			String description = item.getDescription();
			//the person who has borrowed the item
			Person person = item.getPerson();
			//hardcoded name of the wishlist event, must be changed if the database is localized
			String eventTitle = "Buy " + description + " for " + person;
			Event[] events = eventDAO.readEvents();
			for (int i = 0; events.length > i; i++) {
				if (events[i].getTitle().equals(eventTitle)) {
					Event event = events[i];
					return event;
				} 
			}
			return null;
		}
		
//borrowed events

		/** 
		 * Method for creating a event relating to a borrowed item
		 * @param borrowedThing the item that the event concerns
		 * @return boolean indicating whether or not the event has been successfully created
		 */ 
		public boolean createBorrowedEvent(BorrowedThing borrowedThing) {
			Event borrowed = new Event();
			borrowed.setEvent_id(getNextId());
			//hardcoded name of the borrowed event, must be changed if the database is localized
			borrowed.setTitle(borrowedThing.getPerson().getName() + " should return " +  borrowedThing.getDescription());
			borrowed.setLocation(null);
			borrowed.setStartDate(borrowedThing.getReturnDate());
			borrowed.setEndDate(borrowedThing.getReturnDate());
			borrowed.setFullday(true);
			borrowed.setRecurring(false);
			borrowed.setCalendar("borrowed");
			return eventDAO.createEvent(borrowed);
		}
		/** 
		 * Boolean method that gets a Date containing old date when a borrowed thing should be returned
		 * and the borrowed thing that was updated as parameters and updates a database borrowed Event.
		 * Method first checks if there was a borrowed event already.
		 * @param thing the borrowed thing that was updated
		 * @return true - if borrowed event was successfully updated, false - if updating borrowed event didn't succeed 
		 */
		//update borrowed event if date changes
		public boolean updateBorrowedDate(BorrowedThing thing) {
			boolean updated = false;
			//hardcoded name of the borrowed event, must be changed if the database is localized
			String eventTitle = thing.getPerson().getName() + " should return " + thing.getDescription();
			Event borrowedEvent = eventDAO.readBorrowed(eventTitle);
			if(borrowedEvent != null) {
				borrowedEvent.setStartDate(thing.getReturnDate());
				borrowedEvent.setEndDate(thing.getReturnDate());
				updated = eventDAO.updateEvent(borrowedEvent);
			}
			else {
				updated = createBorrowedEvent(thing);
			}
			return updated;
		}
		
		/** 
		 * Boolean method that gets a String containing old borrowed thing's description 
		 * and the borrowed thing that was updated as parameters and updates a database borrowed Event.
		 * Method first checks if there was a borrowed event already.
		 * @param oldTitle  - the old description of the updated borrowed thing 
		 * @param thing the borrowed thing that was updated
		 * @return true - if borrowed event was successfully updated, false - if updating borrowed event didn't succeed
		 */
		//update borrowed event if borrowed thing's description changes
		public boolean updateBorrowedTitle(String oldTitle, BorrowedThing thing) {
			boolean updated = false;
			//hardcoded name of the borrowed event, must be changed if the database is localized
			String eventTitle = thing.getPerson().getName() + " should return " + oldTitle;
			Event borrowedEvent = eventDAO.readBorrowed(eventTitle);
			if(borrowedEvent != null) {
				//hardcoded name of the borrowed event, must be changed if the database is localized
				String newEvent = thing.getPerson().getName() + " should return " + thing.getDescription();
				borrowedEvent.setTitle(newEvent);
				updated = eventDAO.updateEvent(borrowedEvent);
			}
			else {
				updated = createBorrowedEvent(thing);
			}
			return updated;
		}
		
		//updating borrowed event if person changes
		/** 
		 * Method for updating the event is the person relating to it is changed
		 * @param oldPerson the person who is changed
		 * @param editedBorrowedThing the borrowed thing the event and borrower of which are being changed
		 * @return updated whether or not the event has been successfully updated
		 */
		public boolean updateBorrowedEventPerson(Person oldPerson, BorrowedThing editedBorrowedThing) {
			boolean updated = false;
			//hardcoded name of the borrowed event, must be changed if the database is localized
			String oldEvent = oldPerson.getName() + " should return " + editedBorrowedThing.getDescription();
			Event event = findBorrowedEvent(oldEvent);
			if (event!=null) {
				//hardcoded name of the borrowed event, must be changed if the database is localized
				event.setTitle(editedBorrowedThing.getPerson().getName() + " should return " + editedBorrowedThing.getDescription());
				updated = eventDAO.updateEvent(event);
			}
			else {
				updated = createBorrowedEvent(editedBorrowedThing);
			}
			return updated;
		}
		
		/** 
		 * Method for finding the borrowed event based on the name of the event
		 * @param description the name of the event, the event of which is being searched for
		 * @return loanEvent the event that has to do with the borrowing event
		 */
		public Event findBorrowedEvent(String description) {
			Event loanEvent = eventDAO.readBorrowed(description);
			return loanEvent;
		}
		//deletes borrowed event
		/** 
		 * Method for deleting the "should return" event from events
		 * @param borrowedThing -  BorrowedThing, the event of which is being searched for
		 * @return deleted boolean indicating whether or not the event has been successfully deleted
		 */
		public boolean deleteBorrowedEvent(BorrowedThing borrowedThing) {
			boolean deleted = false;
			Event event = findBorrowedEvent(borrowedThing);
			try {
				int eventID = event.getEvent_id();
				deleted = eventDAO.deleteEvent(eventID);
			//if the borrowed thing has been returned, the event relating to it has already been deleted
			} catch(NullPointerException e) {
				System.out.println("No borrowing event to delete");
			}
			return deleted;
		}
		
		//used for updating or deleting the right borrowed event - HARD CODED!
		/** 
		 * Method for finding the borrowed event based on the description of the borrowed item
		 * @param thing  -BorrowedThing, the event of which is being searched for
		 * @return event - the right event is returned or null if there was no event
		 */
		public Event findBorrowedEvent(BorrowedThing thing) {
			//the person who has borrowed the item
			Person loanPerson = thing.getPerson();
			//hardcoded name of the borrowed event, must be changed if the database is localized
			String eventTitle = loanPerson + " should return " + thing.getDescription();
			Event[] loanEvent = eventDAO.readEvents();
			for (int i = 0; loanEvent.length > i; i++) {
				if (loanEvent[i].getTitle().equals(eventTitle)) {
					Event event = loanEvent[i];
					return event;
				} 
			}
			return null;
		}
		
		/** 
		 * Method that gets the next possible ID for the events. First there is a check if there are some vents already. 
		 * If there are no events yet, 0 will be returned as the possible ID.
		 * If there are events already, the ID of the last event is increased by 1.
		 * This is because events added from the calendar get a random ID number from the CalendarFX.
		 * @return int the next possible id number
		 */ 
		private int getNextId() {
			int lastEvent = eventDAO.readEvents().length; 
			int lastEventId =1;
			if(lastEvent != 0 ) {
				lastEventId = eventDAO.readEvents()[lastEvent-1].getEvent_id();
			}
			return lastEventId+1;
		}
}
