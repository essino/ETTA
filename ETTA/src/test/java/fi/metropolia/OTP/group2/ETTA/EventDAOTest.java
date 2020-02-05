package fi.metropolia.OTP.group2.ETTA;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.sql.Date;
import java.sql.Time;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import model.Event;
import model.EventDAO;

public class EventDAOTest {
	private EventDAO eventDAO = new EventDAO();
	private String str = "1974-03-05";
	private Date date = Date.valueOf(str);
	private int event_id = 1;
	private Time startTime = Time.valueOf("19:00:00");
	private Time endTime = Time.valueOf("21:00:00");
	
	private Event event = new Event(1, "teatteri", false, date, date, startTime, endTime, false, "", "default");

	@Test
	public void testCreate() {
		assertEquals(true, eventDAO.createEvent(event), "Creation of person failed");
	}
	
	@Test
	public void testReadEvents() {
		assertEquals(true, eventDAO.readEvents(), "Reading failed");
	}
	
	@Test
	public void testReadEvent() {
		assertEquals(true, eventDAO.readEvent(event_id), "Reading failed");
	}
	
	@Test
	@Disabled
	public void testUpdate() {
		fail("Not yet implemented");
	}
	
	@Test
	@Disabled
	public void testDelete() {
		fail("Not yet implemented");
	}
}
