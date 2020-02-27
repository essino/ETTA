package fi.metropolia.OTP.group2.ETTA;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Date;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;

import model.Category;
import model.CategoryDAO;
import model.Transfer;
import model.TransferDAO;

@TestMethodOrder(OrderAnnotation.class)
class TransferDAOTest {
	/*
	private TransferDAO transferDAO = new TransferDAO();
	private int id = 1;
	private String desc = "shopping";
	private static Category cat = new Category("food");
	private static CategoryDAO catDAO = new CategoryDAO();
	private boolean income = false;
	private String str = "2020-02-09";
	private Date date = Date.valueOf(str);
	private float amount = 74;
	private Transfer transfer = new Transfer(desc, cat, income, date, amount);
	
	@BeforeAll
	public static void createCategory() {
		catDAO.createCategory(cat);
	}

	@Test
	@Order(1)
	public void testCreateTransfer() {
		assertEquals(true, transferDAO.createTransfer(transfer), "Creation of transfer failed");
	}
	
	@Test
	@Order(2)
	public void testReadTransfers() {
		assertEquals(23, transferDAO.readTransfers().length, "Reading all failed");
	}
	
	@Test
	@Order(3)
	public void testReadTransfer() {
		assertEquals(date, transferDAO.readTransfer(id).getDate(), "Reading one failed (date)");
		assertEquals(amount, transferDAO.readTransfer(id).getAmount(), "Reading one failed (amount)");
	}
	
	@Test
	@Order(4)
	public void testUpdateTransfer() {
		Transfer updatedTransfer = transferDAO.readTransfer(id);
		updatedTransfer.setDate(Date.valueOf("2020-02-11"));
		assertEquals(true, transferDAO.updateTransfer(updatedTransfer), "Updating failed");
		assertEquals(Date.valueOf("2020-02-11"), transferDAO.readTransfer(id).getDate(), "Date updating failed");
	}
	
	@Test
	@Order(5)
	public void testDeleteTransfer() {
		assertEquals(true, transferDAO.deleteTransfer(id), "Deleting failed");
	}
*/
}
