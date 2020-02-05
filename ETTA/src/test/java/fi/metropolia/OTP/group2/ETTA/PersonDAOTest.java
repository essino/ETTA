package fi.metropolia.OTP.group2.ETTA;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Date;
import java.time.LocalTime;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import model.Person;
import model.PersonDAO;

class PersonDAOTest {
	private PersonDAO personDAO = new PersonDAO();
	private String str = "1974-03-05";
	private Date date = Date.valueOf(str);
	private Person person = new Person("Risto", date, "risto@gmail.com");

	@Test
	public void testCreate() {
		assertEquals(true, personDAO.createPerson(person), "Creation of person failed");
	}
	
	@Test
	public void testRead() {
		assertEquals(true, personDAO.readPeople(), "Reading failed");
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
