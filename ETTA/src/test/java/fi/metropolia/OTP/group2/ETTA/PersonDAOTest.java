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
	private int length = 0;
	private int index = 0;

	/*
	@BeforeEach
	public void createCategory() {
		Person person = new Person(name, date, email);
		PersonDAO pDAO = new PersonDAO();
		pDAO.createPerson(person);
	}
	*/
	
	@Test
	@Order(1)
	public void testCreate() {
		assertEquals(true, personDAO.createPerson(tiina), "Creation of person failed");
	}
	
	@Test
	@Order(2)
	public void testReadPeople() {
		length = personDAO.readPeople().length;
		Person lena = new Person("lena", Date.valueOf("1980-08-10"), "lena@lena.com");
		assertEquals(true, personDAO.createPerson(lena), "Creation of person failed");
		length++;
		assertEquals(length, personDAO.readPeople().length, "Reading all failed");
	}
	
	@Test
	@Order(3)
	public void testReadPerson() {
		length = personDAO.readPeople().length;
		index = personDAO.readPeople()[length-2].getPerson_id();
		assertEquals(date, personDAO.readPerson(index).getBirthday(), "Reading one failed (bday)");
		assertEquals(email, personDAO.readPerson(index).getEmail(), "Reading one failed (email)");
	}
	
	@Test
	@Order(4)
	public void testUpdate() {
		Date newDate = Date.valueOf("1990-03-16");
		length = personDAO.readPeople().length;
		index = personDAO.readPeople()[length-2].getPerson_id();
		Person updatedPerson = personDAO.readPerson(index);
		updatedPerson.setBirthday(newDate);
		assertEquals(true, personDAO.updatePerson(updatedPerson), "Updating failed");
		assertEquals(newDate, personDAO.readPerson(index).getBirthday(), "Bday updating failed");
	}
	
	@Test
	@Order(5)
	public void testDelete() {
		length = personDAO.readPeople().length;
		index = personDAO.readPeople()[length-2].getPerson_id();
		assertEquals(true, personDAO.deletePerson(index-1), "Deleting tiina failed");
		assertEquals(true, personDAO.deletePerson(index), "Deleting lena failed");
		
	}

}
