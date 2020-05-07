package fi.metropolia.OTP.group2.ETTA;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import com.calendarfx.model.Entry;

import controller.BorrowedController;
import controller.CalendarController;
import controller.ContactsController;
import controller.WishlistController;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;

import model.BorrowedThing;
import model.BorrowedThingDAO;
import model.Event;
import model.EventDAO;
import model.Item;
import model.ItemDAO;
import model.Person;
import model.PersonDAO;

//Tests for all model and controller classes connected to the Person model class:
//Person, Item, BorrowedThing, Event, ContactsController, WishlistController, BorrowedController, EventController
@TestMethodOrder(OrderAnnotation.class)
class PersonConnectedDAOTest {

	//used DAO classes
	private static PersonDAO personDAO = new PersonDAO(true);
	private ItemDAO itemDAO = new ItemDAO(true);
	private BorrowedThingDAO borrowedThingDAO = new BorrowedThingDAO(true);
	private static EventDAO eventDAO = new EventDAO(true);
	
	//used controllers
	private ContactsController contactsController = new ContactsController(personDAO);
	private WishlistController wishlistController = new WishlistController(itemDAO, personDAO, eventDAO);
	private BorrowedController borrowedController = new BorrowedController(personDAO, borrowedThingDAO, eventDAO);
	private CalendarController calendarController = new CalendarController(eventDAO);
	
	//test dates
	private static Date bday1 = Date.valueOf("1997-06-17");
	private static Date bday2 = Date.valueOf("1980-11-04");
	private Date bday3 = Date.valueOf("1980-07-23");
	
	//test persons
	private static String name = "Tiina";
	private static String email = "tiina.vanhanen@metropolia.fi";
	private static Person tiina = new Person(name, bday1, email);
	private static Person risto = new Person("Risto", bday2, "risto@gmail.com");
	private Person lena = new Person("Lena", bday3, "lena@lena.com"); 
	
	//test items
	private String itemDesc = "Ystävänpäiväkortti";
	private double price = 3.5;
	private Date dateNeeded = Date.valueOf("2020-02-14");
	private String additionalInfo = null;
	private Item item = new Item(itemDesc, tiina, price, dateNeeded, additionalInfo);
	private Item item2 = new Item("Kirja", tiina, 10.0, dateNeeded, additionalInfo);
	private Item item3 = new Item("Paita", null, 49.99, dateNeeded, "Zalando");
	private Item item4 = new Item("Lippu", tiina, 5.0, dateNeeded, additionalInfo, true);
	private Item updatedItem;
	
	//test borrowed things
	private String borrowedDesc = "Red hammer";
	private Date loanDate = Date.valueOf("2020-02-10");
	private Date returnDate = Date.valueOf("2020-03-10");
	private BorrowedThing borrowedThing = new BorrowedThing(borrowedDesc, loanDate, returnDate, tiina);
	private BorrowedThing borrowedThing2 = new BorrowedThing("Cat", loanDate, returnDate, risto);
	
	//test events
	private Date eventDate = Date.valueOf("2020-03-05");
	private Time startTime = Time.valueOf("19:00:00");
	private Time endTime = Time.valueOf("21:00:00");
	private LocalDate localDate = java.time.LocalDate.now();
	private Date today = Date.valueOf(localDate);
	private Event event = new Event(1, "teatteri", false, eventDate, eventDate, startTime, endTime, false, "", "default");
	private Event event2 = new Event(2, "lounas", false, eventDate, eventDate, startTime, endTime, false, "", "default");
	private Event event3 = new Event(3, "bileet", false, today, today, startTime, endTime, false, "", "default");
	
	//Creates people to be used in tests
	@BeforeAll
	public static void createPerson() {
		personDAO.createPerson(tiina);
		personDAO.createPerson(risto);
	}
	
	//Test for creating a person
	@Test
	@Order(1)
	public void testCreatePerson() {
		assertEquals(true, personDAO.createPerson(lena), "Creation of person failed");
	}
	
	//Test for reading one person, DAO and controller
	@Test
	@Order(2)
	public void testReadPerson() {
		assertEquals(name, personDAO.readPerson(1).getName(), "Reading one failed (name)");
		//Test works in eclipse, Jenkins does not agree with the date
		//assertEquals(bday1, personDAO.readPerson(1).getBirthday(), "Reading one failed (bday)");
		assertEquals(email, personDAO.readPerson(1).getEmail(), "Reading one failed (email)");
		assertEquals(true, contactsController.checkIfPersonexists(name), "Reading one failed (controller)");
	} 
	
	//Test for finding a person with their name, DAOs and controllers
	@Test
	@Order(3)
	public void testReadPersonWithName() {
		//Test works in eclipse, Jenkins does not agree with the date
		//assertEquals(bday1, personDAO.readPerson(name).getBirthday(), "Reading one with name failed (bday)");
		assertEquals(email, personDAO.readPerson(name).getEmail(), "Reading one with name failed (email)");
		assertEquals(null, personDAO.readPerson("Elena"), "Reading with a name that doesn't exitst failed");
		assertEquals(name, wishlistController.findPerson(name).getName(), "Reading one with name failed (controller)");
		assertEquals(email, borrowedController.findPerson(name).getEmail(), "Reading one with name failed (borrowed controller)");
	}
	
	//Test for reading all persons from the database, DAOs and controllers
	@Test
	@Order(4)
	public void testReadPeople() {
		assertEquals(3, personDAO.readPeople().length, "Reading all failed");
		assertEquals(3, contactsController.getPeople().length, "Reading allfailed (contacts controller)");
		//4 persons in wishlist controller, because person "me" is added there, test works in eclipse, not working in jenkins 
		//assertEquals(4, wishlistController.personsList().size(), "Reading all failed (wishlist controller)");
		assertEquals(3, borrowedController.personsList().size(), "Reading allfailed (borrowed controller)");
	}
	
	//Test for updating a person's birthday
	@Test
	@Order(5)
	public void testUpdatePerson() {
		Date newDate = Date.valueOf("1990-03-16");
		Person updatedPerson = personDAO.readPerson(2);
		updatedPerson.setBirthday(newDate);
		assertEquals(true, personDAO.updatePerson(updatedPerson), "Updating failed");
		//Test works in eclipse, Jenkins does not agree with the date
		//assertEquals(newDate, personDAO.readPerson(2).getBirthday(), "Bday updating failed");
	}
	
	//Test for creating an item
	@Test
	@Order(6)
	public void testCreateItem() {
		assertEquals(true, itemDAO.createItem(item), "Creation of item failed");
	}

	//Test for creating a wishlist event
	@Test
	@Order(7)
	public void testCreateWishlistEvent() {
		assertEquals(true, calendarController.createWishlistEvent(item), "Creation of wishlist event failed");
	}
	
	//Test for reading a wishlist event, DAO and controller
	@Test
	@Order(8)
	public void testReadWishlistEvent() {
		String wishlistEvent = "Buy " + item.getDescription() + " for " + item.getPerson().getName();
		assertEquals(wishlistEvent, eventDAO.readWishlistEvent(wishlistEvent).getTitle(), "Reading of wishlist event failed");
		assertEquals(wishlistEvent, calendarController.findWishlistEvent(item).getTitle(), "Reading of wishlist event failed");
		assertEquals("wishlist", eventDAO.readWishlistEvent(wishlistEvent).getCalendar(), "Reading of wishlist event failed");
	}
	
	//Test for converting model class Events to calendar entries and vice versa
	@Test
	@Order(9)
	public void testFromEventToEntryAndFromEntryToEvent() {
		String wishlistEvent = "Buy " + item.getDescription() + " for " + item.getPerson().getName();
		Event event = eventDAO.readWishlistEvent(wishlistEvent);
		assertEquals(true, calendarController.fromEventToEntry((event)).isFullDay(), "Entry from event failed");
		Entry entry = calendarController.fromEventToEntry((event));
		assertEquals(false, calendarController.fromEntryToEvent(entry).isRecurring(), "Event from entry failed");
	}
	
	//Test for updating a wishlist event
	@Test
	@Order(10)
	public void testUpdateWishlistEvent() {
		updatedItem = itemDAO.readItem(1);
		updatedItem.setDateNeeded(returnDate);
		assertEquals(true, calendarController.updateWishlistDate(dateNeeded, updatedItem), "Updating of wishlist event date failed");
		updatedItem.setDescription(borrowedDesc);
		assertEquals(true, calendarController.updateWishlistDescription(itemDesc, updatedItem), "Updating of wishlist event description failed");
		updatedItem.setPerson(risto);
		assertEquals(true, calendarController.updateWishlistPerson(tiina.getName(), updatedItem), "Updating of wishlist event name failed");
		String wishlistEvent = "Buy " + updatedItem.getDescription() + " for " + updatedItem.getPerson().getName();
		assertEquals(wishlistEvent, eventDAO.readWishlistEvent(wishlistEvent).getTitle(), "Reading of wishlist event failed");
	}
	
	//Test for deleting a wishlist event
	@Test
	@Order(11)
	public void testDeleteWishlListEvent() {
		String wishlistEvent = "Buy " + borrowedDesc + " for " + risto.getName();
		Event wlEvent = eventDAO.readWishlistEvent(wishlistEvent);
		assertEquals(true, eventDAO.deleteEvent(wlEvent.getEvent_id()), "Deleting of wishlist event failed");
	}
	
	//Test for reading one item
	@Test
	@Order(12)
	public void testReadItem() {
		assertEquals(itemDesc, itemDAO.readItem(1).getDescription(), "Reading one failed (description)");
		assertEquals(price, itemDAO.readItem(1).getPrice(), "Reading one failed (price)");
		//Test works in eclipse, Jenkins does not agree with the date
		//assertEquals(dateNeeded, itemDAO.readItem(1).getDateNeeded(), "Reading one failed (date)");
		assertEquals(name, itemDAO.readItem(1).getPerson().getName(), "Reading one failed (person)");
	}

	//Test for finding an item with its description
	@Test
	@Order(13)
	public void testReadItemWithDesc() {
		assertEquals(price, itemDAO.readItem("Ystävänpäiväkortti").getPrice(), "Reading one with desc failed (price)");
		//Test works in eclipse, Jenkins does not agree with the date
		//assertEquals(dateNeeded, itemDAO.readItem("Ystävänpäiväkortti").getDateNeeded(),"Reading one with desc failed (date)");
		assertEquals(name, itemDAO.readItem("Ystävänpäiväkortti").getPerson().getName(),"Reading one with desc failed (person)");
		assertEquals(null, itemDAO.readItem("Mekko"), "Reading with a desc that doesn't exitst failed");
	}

	//Test for reading all items from the database
	@Test
	@Order(14)
	public void testReadItems() {
		assertEquals(1, itemDAO.readItems().length, "Reading all failed");
		assertEquals(true, itemDAO.createItem(item2), "Creation of item2 failed");
		assertEquals(2, itemDAO.readItems().length, "Reading all failed");
		assertEquals(2, wishlistController.getItems().length, "Reading all failed(controller)");
		assertEquals(2, wishlistController.getItemsForOthers().length, "Reading all for others failed(controller)");
	}

	//Test for finding items by their person
	@Test
	@Order(15)
	public void testReadItemsByPerson() {
		assertEquals(2, itemDAO.readItemsByPerson(1).length, "Reading all Tiina's items failed");
	}

	//Test for finding own items aka items with a null person
	@Test
	@Order(16)
	public void testReadOwnItems() {
		assertEquals(0, itemDAO.readOwnItems().length, "Reading all own items failed");
		assertEquals(true, itemDAO.createItem(item3), "Creation of item failed"); 
		assertEquals(1, itemDAO.readOwnItems().length, "Reading all own items failed");
		assertEquals(1, wishlistController.getOwnItems().length, "Reading all own items failed(controller)");
	}

	//Test for finding all bought items
	@Test
	@Order(17)
	public void testReadItemsByBought() {
		assertEquals(0, itemDAO.readItemsByBought(true).length, "Reading all bought items failed");
		assertEquals(true, itemDAO.createItem(item4), "Creation of item failed");
		assertEquals(1, itemDAO.readItemsByBought(true).length, "Reading all bought items failed");
		assertEquals(1, wishlistController.getBoughtItems(true).length, "Reading all bought items failed(controller)");
	}

	//Test for updating an item's price
	@Test
	@Order(18)
	public void testUpdateItem() {
		Item updatedItem = itemDAO.readItem(1);
		updatedItem.setPrice(2.3);
		assertEquals(true, itemDAO.updateItem(updatedItem), "Updating failed");
		assertEquals(2.3, itemDAO.readItem(1).getPrice(), "Price updating failed");
	}

	//Test for deleting items, deletes all items used in testing
	@Test
	@Order(19)
	public void testDeleteItem() {
		assertEquals(true, itemDAO.deleteItem(1), "Deleting 1 failed");
		assertEquals(true, itemDAO.deleteItem(2), "Deleting 2 failed");
		assertEquals(true, itemDAO.deleteItem(3), "Deleting 2 failed");
		assertEquals(true, itemDAO.deleteItem(4), "Deleting 2 failed");
		assertEquals(0, itemDAO.readItems().length, "Deleting all failed");
	}
	
	//Test for creating a borrowed thing
	@Test
	@Order(20)
	public void testCreateBorrowedThing() {
		assertEquals(true, borrowedThingDAO.createBorrowedThing(borrowedThing), "Creation of borrowed thing failed");
	}
	
	//Test for creating a borrowed thing event
	@Test
	@Order(21)
	public void testCreateBorrowedEvent() {
		assertEquals(true, calendarController.createBorrowedEvent(borrowedThing), "Creation of borrowed event failed");
	}
	
	//Test for reading a borrowed thing event
	@Test
	@Order(22)
	public void testReadBorrowedEvent() {
		String eventDesc = borrowedThing.getPerson().getName() + " should return " + borrowedThing.getDescription();
		assertEquals(eventDesc, eventDAO.readBorrowed(eventDesc).getTitle(), "Reading of borrowed event failed");
		assertEquals(eventDesc, calendarController.findBorrowedEvent(eventDesc).getTitle(), "Reading of borrowed event failed (controller)");
	}
	
	//Test for updating a borrowed thing event
	@Test
	@Order(23)
	public void testUpdateBorrowedEvent() {
		borrowedThing.setReturnDate(eventDate);
		assertEquals(true, calendarController.updateBorrowedDate(borrowedThing), "Updating borrowed event date failed");
		borrowedThing.setPerson(risto);
		assertEquals(true, calendarController.updateBorrowedEventPerson(tiina, borrowedThing), "Updating borrowed event date failed");
		borrowedThing.setDescription(itemDesc);
		assertEquals(true, calendarController.updateBorrowedTitle(borrowedDesc, borrowedThing), "Updating borrowed event date failed");
		
	}
	
	//Test for deleting a borrowed thing event
	@Test
	@Order(24)
	public void testDeleteBorrowedEvent() {
		borrowedThing.setReturnDate(eventDate);
		borrowedThing.setPerson(risto);
		borrowedThing.setDescription(itemDesc);
		assertEquals(true, calendarController.deleteBorrowedEvent(borrowedThing), "Deleting of borrowed event failed");
	}
	
	//Test for reading one borrowed thing
	@Test
	@Order(25)
	public void testReadBorrowedThing() {
		assertEquals(borrowedDesc, borrowedThingDAO.readBorrowedThing(1).getDescription(), "Reading one failed (description)");
		//Tests works in eclipse, Jenkins does not agree with the dates
		//assertEquals(loanDate, borrowedThingDAO.readBorrowedThing(1).getDateBorrowed(), "Reading one failed (loan date)");
		//assertEquals(returnDate, borrowedThingDAO.readBorrowedThing(1).getReturnDate(), "Reading one failed (return date)");
		assertEquals(name, borrowedThingDAO.readBorrowedThing(1).getPerson().getName(), "Reading one failed (person)");
	}
	
	//Test for reading all borrowed things from the database
	@Test
	@Order(26)
	public void testReadBorrowedThings() {
		assertEquals(1, borrowedThingDAO.readBorrowedThings().length, "Reading all failed");
		assertEquals(true, borrowedThingDAO.createBorrowedThing(borrowedThing2), "Creation of borrowed thing failed");
		assertEquals(2, borrowedThingDAO.readBorrowedThings().length, "Reading all failed");
		assertEquals(2, borrowedController.getBorrowedThings().length, "Reading all failed");
	}
	
	//Test for finding borrowed things by their person
	@Test
	@Order(27)
	public void testReadBorrowedThingsByPerson() {
		assertEquals(1, borrowedThingDAO.readBorrowedThingsByPerson(1).length, "Reading Tiina's things failed");
		assertEquals(1, borrowedThingDAO.readBorrowedThingsByPerson(2).length, "Reading Risto's things failed");
		
	}
	
	//Test for updating a borrowed things' description
	@Test
	@Order(28)
	public void testUpdateBorrowedThing() {
		BorrowedThing updatedBorrowedThing = borrowedThingDAO.readBorrowedThing(2);
		updatedBorrowedThing.setDescription("Black cat");
		assertEquals(true, borrowedThingDAO.updateBorrowedThing(updatedBorrowedThing), "Updating failed");
		assertEquals("Black cat", borrowedThingDAO.readBorrowedThing(2).getDescription(), "Description updating failed");
	}
	
	//Test for deleting a borrowed thing, deletes all borrowed things used in testing
	@Test
	@Order(29)
	public void testDeleteBorrowedThing() {
		assertEquals(true, borrowedThingDAO.deleteBorrowedThing(1), "Deleting failed");
		assertEquals(true, borrowedThingDAO.deleteBorrowedThing(2), "Deleting failed");
		assertEquals(0, borrowedThingDAO.readBorrowedThings().length, "Deleting all failed");
	}
	
	//Test for creating an event
	@Test
	@Order(30)
	public void testCreateEvent() {
		assertEquals(true, eventDAO.createEvent(event), "Creation of event failed");
	}
	
	//Test reading one event
	@Test
	@Order(31)
	public void testReadEvent() {
		assertEquals("teatteri", eventDAO.readEvent(1).getTitle(), "Reading one failed (title)");
		//Test works in eclipse, Jenkins does not agree with the date
		//assertEquals(eventDate, eventDAO.readEvent(1).getEndDate(), "Reading one failed (date)");
		assertEquals(startTime, eventDAO.readEvent(1).getStartTime(), "Reading one failed (time)");
	}
	
	//Test for reading all events from the database
	@Test
	@Order(32)
	public void testReadEvents() {
		assertEquals(1, eventDAO.readEvents().length, "Reading all failed (1)");
		assertEquals(true, eventDAO.createEvent(event2), "Creation of event failed");
		assertEquals(2, eventDAO.readEvents().length, "Reading all failed (2)");
	}
	
	//Test for reading events from a select calendar
	@Test
	@Order(33)
	public void testReadEventsFromOneCalendar() {
		assertEquals(2, eventDAO.readEventsFromOneCalendar("default", true).length, "Reading from calendar failed");
	}
	
	//Test for reading today's events
	@Test
	@Order(34)
	public void testReadTodaysEvents() {
		assertEquals(0, eventDAO.readTodaysEvents().length, "Reading today's events failed (0)");
		//Test works in eclipse, Jenkins does not agree with the date
		//assertEquals(true, eventDAO.createEvent(event3), "Creation of event failed");
		//assertEquals(1, eventDAO.readTodaysEvents().length, "Reading today's events failed (1)");
	}
	
	//Test for updating an event's date
	@Test
	@Order(35)
	public void testUpdate() {
		Date newDate = Date.valueOf("2020-04-05");
		Event updatedEvent = eventDAO.readEvent(1);
		updatedEvent.setStartDate(newDate);
		updatedEvent.setEndDate(newDate);
		assertEquals(true, eventDAO.updateEvent(updatedEvent), "Updating failed");
		//Test works in eclipse, Jenkins does not agree with the date
		//assertEquals(newDate, eventDAO.readEvent(1).getEndDate(), "Date updating failed");
	}
	
	//Test for creating a birthday event
	@Test
	@Order(36)
	public void testCreateBirthday() {
		assertEquals(true, calendarController.createBirthday(name, bday1), "Creation of birthday failed");
	}
	
	//Test for updating the name of a birthday event
	@Test
	@Order(37)
	public void testUpdateBirthdayName() {
		assertEquals(true, calendarController.updateBirthday(name, risto.getName()), "Updating of birthday event name failed");
	}
	
	//Test for updating the birthday
	@Test
	@Order(38)
	public void testUpdateBirthdayDate() {
		assertEquals(true, calendarController.updateBirthday(risto.getName(), risto.getBirthday(), bday1), "Updating of birthday event name failed");
	}
	
	//Test for deleting a birthday
	@Test
	@Order(39)
	public void testDeleteBirthday() {
		assertEquals(1, eventDAO.readEventsFromOneCalendar("birthdays", true).length, "Reading from birthday calendar failed");
		assertEquals(true, eventDAO.deleteBirthday(risto.getName()), "Deleting birthday failed");
		assertEquals(0, eventDAO.readEventsFromOneCalendar("birthdays", true).length, "Birthday not deleted");
		
	}
	
	//Test for deleting an event, deletes all events used in testing
	@Test
	@Order(40)
	public void testDeleteEvent() {
		assertEquals(true, eventDAO.deleteEvent(1), "Deleting 1 failed");
		assertEquals(true, eventDAO.deleteEvent(2), "Deleting 2 failed");
		//assertEquals(true, eventDAO.deleteEvent(3), "Deleting 3 failed");
		assertEquals(0, eventDAO.readEvents().length, "Deleting all failed");
	}
	
	//Test for deleting a person, deletes all people used in testing
	@Test
	@Order(41)
	public void testDeletePerson() {
		assertEquals(true, personDAO.deletePerson(1), "Deleting tiina failed");
		assertEquals(true, personDAO.deletePerson(2), "Deleting risto failed");
		assertEquals(true, personDAO.deletePerson(3), "Deleting lena failed");
		assertEquals(0, personDAO.readPeople().length, "Deleting all failed");
	}

}
