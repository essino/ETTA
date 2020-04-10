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

	private static TransferDAO transferDAO = new TransferDAO(true);
	private String desc = "grocery shopping";
	private static CategoryDAO catDAO = new CategoryDAO(true);
	private static Category cat1 = new Category("food", false);
	private static Category cat2 = new Category("salary", true);
	private boolean income = false;
	private String str = "2020-02-09";
	private Date date = Date.valueOf(str);
	private float amount = 74;
	private Transfer transfer = new Transfer(desc, cat1, income, date, amount);
	
	@BeforeAll
	public static void createCategory() {
		catDAO.createCategory(cat1);
		catDAO.createCategory(cat2);
	}

	@Test
	@Order(1)
	public void testCreateTransfer() {
		assertEquals(true, transferDAO.createTransfer(transfer), "Creation of transfer failed");
	}
	
	@Test
	@Order(3)
	public void testReadTransfer() {
		assertEquals(date, transferDAO.readTransfer(1).getDate(), "Reading one failed (date)");
		assertEquals(amount, transferDAO.readTransfer(1).getAmount(), "Reading one failed (amount)");
		assertEquals(cat1.getDescription(), transferDAO.readTransfer(1).getCategory().getDescription(), "Reading one failed (category)");
	}
	
	@Test
	@Order(2)
	public void testReadTransfers() {
		assertEquals(1, transferDAO.readTransfers().length, "Reading all failed (1)");
		Transfer transfer2 = new Transfer("Ice cream", cat1, income, Date.valueOf("2020-03-21"), 3);
		assertEquals(true, transferDAO.createTransfer(transfer2), "Creation of transfer failed");
		assertEquals(2, transferDAO.readTransfers().length, "Reading all failed (2)");
	}
	
	@Test
	@Order(3)
	public void testReadSelectedTransfers() {
		Date startDate = Date.valueOf("2020-02-01");
		Date endDate = Date.valueOf("2020-03-01");
		assertEquals(1, transferDAO.readSeletedTransfers(startDate, endDate).length, "Reading selected transfers failed");
	}
	
	@Test
	@Order(4)
	public void testReadExpenses() {
		assertEquals(2, transferDAO.readExpenses().length, "Reading expenses failed");
	}
	
	@Test
	@Order(5)
	public void testReadIncome() {
		Transfer transfer3 = new Transfer("Work", cat2, true, date, 2500);
		assertEquals(true, transferDAO.createTransfer(transfer3), "Creation of transfer failed");
		assertEquals(1, transferDAO.readIncome().length, "Reading incomes failed");
	}
	
	@Test
	@Order(6)
	public void testUpdateTransfer() {
		Transfer updatedTransfer = transferDAO.readTransfer(1);
		updatedTransfer.setDate(Date.valueOf("2020-02-11"));
		assertEquals(true, transferDAO.updateTransfer(updatedTransfer), "Updating failed");
		assertEquals(Date.valueOf("2020-02-11"), transferDAO.readTransfer(1).getDate(), "Date updating failed");
	}
	
	@Test
	@Order(7)
	public void testDeleteTransfer() {
		assertEquals(true, transferDAO.deleteTransfer(1), "Deleting 1 failed");
		assertEquals(true, transferDAO.deleteTransfer(2), "Deleting 2 failed");
		assertEquals(true, transferDAO.deleteTransfer(3), "Deleting 3 failed");
		assertEquals(0, transferDAO.readTransfers().length, "Deleting all failed");
	}

}
