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

	private BorrowedThingDAO borrowedThingDAO = new BorrowedThingDAO();
	private int thing_id = 1;
	private String description = "The red hammer";
	private String strLoan = "2020-02-10";
	private Date loanDate = Date.valueOf(strLoan);
	private String strReturn = "2020-03-10";
	private Date returnDate = Date.valueOf(strReturn);
	private static PersonDAO personDAO = new PersonDAO();
	private static Person risto = new Person("Risto", Date.valueOf("1960-11-11"), "risto.reipas@gmail.com");
	private BorrowedThing borrowedThing = new BorrowedThing(description, loanDate, returnDate, risto);
	
	@BeforeAll
	public static void createPerson() {
		personDAO.createPerson(risto);
	}
	
	@Test
	@Order(1)
	public void testCreate() {
		assertEquals(true, borrowedThingDAO.createBorrowedThing(borrowedThing), "Creation of item failed");
	}
	
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
		assertEquals(risto, borrowedThingDAO.readBorrowedThing(thing_id).getPerson(), "Reading one failed (person)");
	}
	
	@Test
	@Order(4)
	public void testUpdate() {
		BorrowedThing updatedBorrowedThing = new BorrowedThing(description, loanDate, Date.valueOf("2021-12-12"), risto);
		assertEquals(true, borrowedThingDAO.updateBorrowedThing(updatedBorrowedThing), "Updating failed");
		assertEquals(returnDate, borrowedThingDAO.readBorrowedThing(thing_id).getReturnDate(), "Return Date updating failed");
	}
	
	@Test
	@Order(5)
	public void testDelete() {
		assertEquals(true, borrowedThingDAO.deleteBorrowedThing(thing_id), "Deleting failed");
	}
}
