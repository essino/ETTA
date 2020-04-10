package fi.metropolia.OTP.group2.ETTA;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Date;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;

import model.BorrowedThing;
import model.BorrowedThingDAO;
import model.Person;
import model.PersonDAO;

@TestMethodOrder(OrderAnnotation.class)
public class BorrowedThingDAOTest {

	private BorrowedThingDAO borrowedThingDAO = new BorrowedThingDAO(true);
	private String description = "Red hammer";
	private String strLoan = "2020-02-10";
	private Date loanDate = Date.valueOf(strLoan);
	private String strReturn = "2020-03-10";
	private Date returnDate = Date.valueOf(strReturn);
	private static Person person = new Person("Tiina", Date.valueOf("1997-06-17"), "tiina.vanhanen@metropolia.fi");
	private static Person risto = new Person("Risto", Date.valueOf("1980-11-04"), "risto@gmail.com");
	private static PersonDAO personDAO = new PersonDAO(true);
	private BorrowedThing borrowedThing = new BorrowedThing(description, loanDate, returnDate, person);
	
	@BeforeAll
	public static void createPerson() {
		personDAO.createPerson(person);
		personDAO.createPerson(risto);
	}
	
	@Test
	@Order(1)
	public void testCreate() {
		assertEquals(true, borrowedThingDAO.createBorrowedThing(borrowedThing), "Creation of borrowed thing failed");
	}
	
	@Test
	@Order(2)
	public void testReadBorrowedThing() {
		assertEquals(description, borrowedThingDAO.readBorrowedThing(1).getDescription(), "Reading one failed (description)");
		assertEquals(loanDate, borrowedThingDAO.readBorrowedThing(1).getDateBorrowed(), "Reading one failed (loan date)");
		assertEquals(returnDate, borrowedThingDAO.readBorrowedThing(1).getReturnDate(), "Reading one failed (return date)");
		assertEquals(person.getName(), borrowedThingDAO.readBorrowedThing(1).getPerson().getName(), "Reading one failed (person)");
	}
	
	@Test
	@Order(3)
	public void testReadBorrowedThings() {
		assertEquals(1, borrowedThingDAO.readBorrowedThings().length, "Reading all failed");
		BorrowedThing borrowedThing2 = new BorrowedThing("Cat", loanDate, returnDate, risto);
		assertEquals(true, borrowedThingDAO.createBorrowedThing(borrowedThing2), "Creation of borrowed thing failed");
		assertEquals(2, borrowedThingDAO.readBorrowedThings().length, "Reading all failed");
	}
	
	@Test
	@Order(4)
	public void testReadBorrowedThingsByPerson() {
		assertEquals(1, borrowedThingDAO.readBorrowedThingsByPerson(1).length, "Reading Tiina's things failed");
		assertEquals(1, borrowedThingDAO.readBorrowedThingsByPerson(2).length, "Reading Risto's things failed");
		
	}
	
	@Test
	@Order(5)
	public void testUpdate() {
		BorrowedThing updatedBorrowedThing = borrowedThingDAO.readBorrowedThing(2);
		updatedBorrowedThing.setDescription("Black cat");
		assertEquals(true, borrowedThingDAO.updateBorrowedThing(updatedBorrowedThing), "Updating failed");
		assertEquals("Black cat", borrowedThingDAO.readBorrowedThing(2).getDescription(), "Description updating failed");
	}
	
	@Test
	@Order(6)
	public void testDelete() {
		assertEquals(true, borrowedThingDAO.deleteBorrowedThing(1), "Deleting failed");
		assertEquals(true, borrowedThingDAO.deleteBorrowedThing(2), "Deleting failed");
		assertEquals(0, borrowedThingDAO.readBorrowedThings().length, "Deleting all failed");
	}
	
}
