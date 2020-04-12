package fi.metropolia.OTP.group2.ETTA;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import model.Event;
import model.EventDAO;
import model.Person;
import model.PersonDAO;

@TestMethodOrder(OrderAnnotation.class)
public class EventDAOTest {
	
	private static EventDAO eventDAO = new EventDAO(true);
	private static PersonDAO personDAO = new PersonDAO(true);
	private String str = "2020-03-05";
	private Date date = Date.valueOf(str);
	private Time startTime = Time.valueOf("19:00:00");
	private Time endTime = Time.valueOf("21:00:00");
	private Event event = new Event(1, "teatteri", false, date, date, startTime, endTime, false, "", "default");
	private LocalDate localDate = java.time.LocalDate.now();
	private Date today = Date.valueOf(localDate);
	private static Person lena = new Person("Lena", Date.valueOf("1980-07-23"), "lena@lena.com");

	@BeforeAll
	public static void createPerson() {
		personDAO.createPerson(lena);
	}
	
	@Test
	@Order(1)
	public void testCreate() {
		assertEquals(true, eventDAO.createEvent(event), "Creation of event failed");
	}
	
	@Test
	@Order(2)
	public void testReadEvent() {
		assertEquals("teatteri", eventDAO.readEvent(1).getTitle(), "Reading one failed (title)");
		assertEquals(date, eventDAO.readEvent(1).getEndDate(), "Reading one failed (date)");
		assertEquals(startTime, eventDAO.readEvent(1).getStartTime(), "Reading one failed (time)");
	}
	
	@Test
	@Order(3)
	public void testReadEvents() {
		assertEquals(1, eventDAO.readEvents().length, "Reading all failed (1)");
		Event event2 = new Event(2, "lounas", false, date, date, startTime, endTime, false, "", "default");
		assertEquals(true, eventDAO.createEvent(event2), "Creation of event failed");
		assertEquals(2, eventDAO.readEvents().length, "Reading all failed (2)");
	}
	
	@Test
	@Order(4)
	public void testReadEventsFromOneCalendar() {
		assertEquals(2, eventDAO.readEventsFromOneCalendar("default").length, "Reading from calendar failed");
	}
	
	@Test
	@Order(5)
	public void testReadTodaysEvents() {
		assertEquals(0, eventDAO.readTodaysEvents().length, "Reading today's events failed (0)");
		Event event3 = new Event(3, "bileet", false, today, today, startTime, endTime, false, "", "default");
		assertEquals(true, eventDAO.createEvent(event3), "Creation of event failed");
		assertEquals(1, eventDAO.readTodaysEvents().length, "Reading today's events failed (1)");
	}
	
	@Test
	@Order(6)
	public void testUpdate() {
		Date newDate = Date.valueOf("2020-04-05");
		Event updatedEvent = eventDAO.readEvent(1);
		updatedEvent.setStartDate(newDate);
		updatedEvent.setEndDate(newDate);
		assertEquals(true, eventDAO.updateEvent(updatedEvent), "Updating failed");
		assertEquals(newDate, eventDAO.readEvent(1).getEndDate(), "Date updating failed");
	}
	
	@Test
	@Order(7)
	@Disabled
	public void testUpdateWithTitle() {
		
	}
	
	@Test
	@Order(8)
	@Disabled
	public void testDeleteBirthday() {
		Event birthday = new Event();
		birthday.setEvent_id(4);
		birthday.setTitle(lena.getName());
		birthday.setLocation(null);
		birthday.setStartDate(lena.getBirthday());
		birthday.setEndDate(lena.getBirthday());
		birthday.setFullday(true);
		birthday.setRecurring(true);
		birthday.setRrule("RRULE:FREQ=YEARLY;");
		birthday.setCalendar("birthdays");
		assertEquals(true, eventDAO.createEvent(birthday), "Creation of birthday failed");
	}
	
	@Test
	@Order(9)
	public void testDelete() {
		assertEquals(true, eventDAO.deleteEvent(1), "Deleting 1 failed");
		assertEquals(true, eventDAO.deleteEvent(2), "Deleting 2 failed");
		assertEquals(true, eventDAO.deleteEvent(3), "Deleting 3 failed");
		assertEquals(0, eventDAO.readEvents().length, "Deleting all failed");
	}
	
}
