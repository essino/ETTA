package fi.metropolia.OTP.group2.ETTA;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.sql.Date;
import java.sql.Time;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import model.Event;
import model.EventDAO;

@TestMethodOrder(OrderAnnotation.class)
public class EventDAOTest {
	private EventDAO eventDAO = new EventDAO();
	private String str = "1974-03-05";
	private Date date = Date.valueOf(str);
	private int event_id = 1;
	private Time startTime = Time.valueOf("19:00:00");
	private Time endTime = Time.valueOf("21:00:00");
	
	private Event event = new Event(1, "teatteri", false, date, date, startTime, endTime, false, "", "default");

	@Test
	@Order(1)
	public void testCreate() {
		assertEquals(true, eventDAO.createEvent(event), "Creation of person failed");
	}
	
	@Test
	@Order(2)
	public void testReadEvents() {
		assertEquals(1, eventDAO.readEvents().length, "Reading failed");
	}
	
	@Test
	@Order(3)
	public void testReadEvent() {
		assertEquals(date, eventDAO.readEvent(event_id).getEndDate(), "Reading failed");
	}
	
	@Test
	@Order(4)

	public void testUpdate() {
		date = Date.valueOf("2020-03-05");
		Event updatedEvent = new Event(1, "teatteri", false, date, date, startTime, endTime, false, "", "default");
		assertEquals(true, eventDAO.updateEvent(updatedEvent), "updating failed");
		assertEquals(date, eventDAO.readEvent(1).getEndDate(), "date updating failed");
		
	}
	
	@Test
	@Order(5)
	public void testDelete() {
		assertEquals(true, eventDAO.deleteEvent(event_id), "deleting failed");
	}
}