package fi.metropolia.OTP.group2.ETTA;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Date;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;

import model.Person;
import model.PersonDAO;

@TestMethodOrder(OrderAnnotation.class)
class PersonDAOTest {
	private PersonDAO personDAO = new PersonDAO();
	private int id = 1;
	private String str = "1997-06-17";
	private Date date = Date.valueOf(str);
	private String name = "Tiina";
	private String email = "tiina.vanhanen@metropolia.fi";
	private Person tiina = new Person(name, date, email);

	@BeforeEach
	public void createCategory() {
		Person person = new Person(name, date, email);
		PersonDAO pDAO = new PersonDAO();
		pDAO.createPerson(person);
	}
	
	@Test
	@Order(1)
	public void testCreate() {
		assertEquals(true, personDAO.createPerson(tiina), "Creation of person failed");
	}
	
	@Test
	@Order(2)
	public void testReadPeople() {
		assertEquals(1, personDAO.readPeople().length, "Reading all failed");
	}
	
	@Test
	@Order(3)
	public void testReadPerson() {
		assertEquals(date, personDAO.readPerson(id).getBirthday(), "Reading one failed (bday)");
		assertEquals(email, personDAO.readPerson(id).getEmail(), "Reading one failed (email)");
	}
	
	@Test
	@Order(4)
	public void testUpdate() {
		Date newDate = Date.valueOf("1990-03-16");
		Person updatedPerson = personDAO.readPerson(id);
		updatedPerson.setBirthday(newDate);
		assertEquals(true, personDAO.updatePerson(updatedPerson), "Updating failed");
		assertEquals(newDate, personDAO.readPerson(id).getBirthday(), "Bday updating failed");
	}
	
	@Test
	@Order(5)
	public void testDelete() {
		assertEquals(true, personDAO.deletePerson(id), "Deleting failed");
	}

}
