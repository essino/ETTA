package fi.metropolia.OTP.group2.ETTA;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Date;

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

	private PersonDAO personDAO = new PersonDAO(true);
	private int id = 1;
	private String str = "1997-06-17";
	private Date date = Date.valueOf(str);
	private String name = "Jesper";
	private String email = "jesper@jesper.com";
	private Person jesper = new Person(name, date, email);

	@Test
	@Order(1)
	public void testCreatePerson() {
		assertEquals(true, personDAO.createPerson(jesper), "Creation of person failed");
	}
	
	@Test
	@Order(2)
	public void testReadPerson() {
		assertEquals(name, personDAO.readPerson(1).getName(), "Reading one failed (name)");
		assertEquals(date, personDAO.readPerson(1).getBirthday(), "Reading one failed (bday)");
		assertEquals(email, personDAO.readPerson(1).getEmail(), "Reading one failed (email)");
	}
	
	@Test
	@Order(3)
	public void testReadPersonWithName() {
		assertEquals(date, personDAO.readPerson(name).getBirthday(), "Reading one with name failed (bday)");
		assertEquals(email, personDAO.readPerson(name).getEmail(), "Reading one with name failed (email)");
		assertEquals(null, personDAO.readPerson("Tiina"), "Reading with a name that doesn't exitst failed");
	}
	
	@Test
	@Order(4)
	public void testReadPeople() {
		assertEquals(1, personDAO.readPeople().length, "Reading all failed (1)");
		Person lena = new Person("Lena", Date.valueOf("1980-08-10"), "lena@lena.com");
		assertEquals(true, personDAO.createPerson(lena), "Creation of person failed");
		assertEquals(2, personDAO.readPeople().length, "Reading all failed (2)");
	}
	
	@Test
	@Order(5)
	public void testUpdate() {
		Date newDate = Date.valueOf("1990-03-16");
		Person updatedPerson = personDAO.readPerson(1);
		updatedPerson.setBirthday(newDate);
		assertEquals(true, personDAO.updatePerson(updatedPerson), "Updating failed");
		assertEquals(newDate, personDAO.readPerson(1).getBirthday(), "Bday updating failed");
	}
	
	
	@Test
	@Order(6)
	public void testDelete() {
		assertEquals(true, personDAO.deletePerson(1), "Deleting jesper failed");
		assertEquals(true, personDAO.deletePerson(2), "Deleting lena failed");
	}

}
