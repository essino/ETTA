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
import model.Item;
import model.Person;
import model.PersonDAO;

@TestMethodOrder(OrderAnnotation.class)
public class BorrowedThingDAOTest {

	private BorrowedThingDAO borrowedThingDAO = new BorrowedThingDAO();
	private int thing_id = 1;
	private String description = "The red hammer";
	private String strLoan = "2020-02-10";
	private Date loanDate = Date.valueOf(strLoan);
	private String strReturn = "2020-03-10";
	private Date returnDate = Date.valueOf(strReturn);
	private static Person person = new Person("Tiina", Date.valueOf("1997-06-17"), "tiina.vanhanen@metropolia.fi");
	private static PersonDAO personDAO = new PersonDAO();
	private BorrowedThing borrowedThing = new BorrowedThing(description, loanDate, returnDate, person);
	
	@BeforeAll
	public static void createCategory() {
		personDAO.createPerson(person);
	}
	
	@Test
	@Order(1)
	public void testCreate() {
		assertEquals(true, borrowedThingDAO.createBorrowedThing(borrowedThing), "Creation of item failed");
	}
	/*
	@Test
	@Order(2)
	public void testReadBorrowedThings() {
		assertEquals(1, borrowedThingDAO.readBorrowedThings().length, "Reading all failed");
	}
	
	@Test
	@Order(3)
	public void testReadBorrowedThing() {
		assertEquals(description, borrowedThingDAO.readBorrowedThing(thing_id).getDescription(), "Reading one failed (description)");
		assertEquals(loanDate, borrowedThingDAO.readBorrowedThing(thing_id).getDateBorrowed(), "Reading one failed (loan date)");
		assertEquals(returnDate, borrowedThingDAO.readBorrowedThing(thing_id).getReturnDate(), "Reading one failed (return date)");
		assertEquals(person.getName(), borrowedThingDAO.readBorrowedThing(thing_id).getPerson().getName(), "Reading one failed (person)");
	}
	
	@Test
	@Order(4)
	public void testUpdate() {
		BorrowedThing updatedBorrowedThing = borrowedThingDAO.readBorrowedThing(thing_id);
		updatedBorrowedThing.setDescription("The black cat");
		assertEquals(true, borrowedThingDAO.updateBorrowedThing(updatedBorrowedThing), "Updating failed");
		assertEquals("The black cat", borrowedThingDAO.readBorrowedThing(thing_id).getDescription(), "Return Date updating failed");
	}
	
	@Test
	@Order(5)
	public void testDelete() {
		assertEquals(true, borrowedThingDAO.deleteBorrowedThing(thing_id), "Deleting failed");
	}
*/
}
