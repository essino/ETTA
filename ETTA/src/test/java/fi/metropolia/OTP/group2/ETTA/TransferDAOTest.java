package fi.metropolia.OTP.group2.ETTA;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Date;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;

import model.Category;
import model.CategoryDAO;
import model.Transfer;
import model.TransferDAO;

@TestMethodOrder(OrderAnnotation.class)
class TransferDAOTest {
	
	private static TransferDAO transferDAO = new TransferDAO();
	//private int id = 1;
	private String desc = "shopping";
	private static Category cat = new Category("food", false);
	private static CategoryDAO catDAO = new CategoryDAO();
	private boolean income = false;
	private String str = "2020-02-09";
	private Date date = Date.valueOf(str);
	private float amount = 74;
	private Transfer transfer = new Transfer(desc, cat, income, date, amount);
	static int index = 0;
	static int length = 0;
	
	@BeforeAll
	public static void createCategory() {
		catDAO.createCategory(cat);
	}
	
	@AfterAll
	public static void clear() {
		length = catDAO.readCategories().length;
		index = catDAO.readCategories()[length-1].getCategory_id();
		assertEquals(true, catDAO.deleteCategory(index), "deleting failed");
	}

	@Test
	@Order(1)
	public void testCreateTransfer() {
		assertEquals(true, transferDAO.createTransfer(transfer), "Creation of transfer failed");
	}
	
	@Test
	@Order(2)
	public void testReadTransfers() {
		length = transferDAO.readTransfers().length;
		Transfer transfer2 = new Transfer("icecream", cat, income, date, 25);
		assertEquals(true, transferDAO.createTransfer(transfer2), "Creation of transfer failed");
		length++;
		assertEquals(length, transferDAO.readTransfers().length, "Reading all failed");
	}
	
	@Test
	@Order(3)
	public void testReadTransfer() {
		length = transferDAO.readTransfers().length;
		System.out.println("length " + length);
		index = transferDAO.readTransfers()[length-1].getTransfer_id();
		System.out.println("id" + index);
		//assertEquals(date, transferDAO.readTransfer(index).getDate(), "Reading one failed (date)");
		assertEquals(25, transferDAO.readTransfer(index).getAmount(), "Reading one failed (amount)");
	}
	
	@Test
	@Order(4)
	public void testUpdateTransfer() {
		length = transferDAO.readTransfers().length;
		System.out.println("length " + length);
		index = transferDAO.readTransfers()[length-1].getTransfer_id();
		Transfer updatedTransfer = transferDAO.readTransfer(index);
		updatedTransfer.setDate(Date.valueOf("2020-02-11"));
		assertEquals(true, transferDAO.updateTransfer(updatedTransfer), "Updating failed");
		//assertEquals(Date.valueOf("2020-02-11"), transferDAO.readTransfer(index).getDate(), "Date updating failed");
	}
	
	@Test
	@Order(5)
	public void testDeleteTransfer() {
		length = transferDAO.readTransfers().length;
		System.out.println("length " + length);
		index = transferDAO.readTransfers()[length-1].getTransfer_id();
		assertEquals(true, transferDAO.deleteTransfer(index), "Deleting failed");
		assertEquals(true, transferDAO.deleteTransfer(index-1), "Deleting  2 failed");
	}

}
