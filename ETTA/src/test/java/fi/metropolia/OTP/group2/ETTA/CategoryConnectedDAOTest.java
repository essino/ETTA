package fi.metropolia.OTP.group2.ETTA;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.sql.Date;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import controller.EconomyController;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;

import model.Category;
import model.CategoryDAO;
import model.Transfer;
import model.TransferDAO;

@TestMethodOrder(OrderAnnotation.class)
public class CategoryConnectedDAOTest {
	
	private static CategoryDAO categoryDAO = new CategoryDAO(true);
	private static TransferDAO transferDAO = new TransferDAO(true);
	
	private EconomyController economyController = new EconomyController(categoryDAO, transferDAO);
	
	private static String catDesc = "lottery";
	private static boolean catIncome = true;
	private static Category category = new Category(catDesc, catIncome);
	private static Category cat1 = new Category("food", false);
	private static Category cat2 = new Category("salary", true);
	
	private String desc = "grocery shopping";
	private Date date = Date.valueOf("2020-02-09");
	private float amount = 74;
	private Transfer transfer = new Transfer(desc, cat1, false, date, amount);
	private Transfer transfer2 = new Transfer("Ice cream", cat1, false, Date.valueOf("2020-03-21"), 3);
	private Transfer transfer3 = new Transfer("Work", cat2, true, date, 2500);

	@BeforeAll
	public static void createCategory() {
		categoryDAO.createCategory(cat1);
		categoryDAO.createCategory(cat2);
	}
	
	@Test
	@Order(1)
	public void testCreateCategory() {
		assertEquals(true, categoryDAO.createCategory(category), "Creation of category failed");
	}
	
	@Test
	@Order(2)
	public void testReadCategory() {
		assertEquals(catDesc, categoryDAO.readCategory(3).getDescription(), "Reading one failed (description)");
		assertEquals(true, categoryDAO.readCategory(3).isCategory_type(), "Reading one failed (boolean)");
	}
	
	@Test
	@Order(3)
	public void testReadCategoryWithDesc() {
		assertEquals(true, categoryDAO.readCategory(catDesc).isCategory_type(), "Reading one with desc failed");
		assertEquals(null, categoryDAO.readCategory("jackpot"), "Reading with a desc that doesn't exist failed");
	}
	
	@Test
	@Order(4)
	public void testReadIncomeCategories() {
		assertEquals(2, categoryDAO.readIncomeCategories().length, "Reading all income categories failed");
		assertEquals(2, economyController.incomeCategoriesList().size(), "Reading all income categories failed (controller)");
	}
	
	@Test
	@Order(5)
	public void testreadExpenseCategories() {
		assertEquals(1, categoryDAO.readExpenseCategories().length, "Reading all expense categories failed");
		assertEquals(1, economyController.expenseCategoriesList().size(), "Reading all expense categories failed(controller");
	}
	
	@Test
	@Order(6)
	public void testUpdateCategory() {
		String newDesc = "gift";
		Category updatedCategory = categoryDAO.readCategory(3);
		updatedCategory.setDescription(newDesc);
		assertEquals(true, categoryDAO.updateCategory(updatedCategory), "Updating failed");
		assertEquals(newDesc, categoryDAO.readCategory(3).getDescription(), "Description updating failed");
	}

	@Test
	@Order(7)
	public void testCreateTransfer() {
		assertEquals(true, transferDAO.createTransfer(transfer), "Creation of transfer failed");
	}
	
	@Test
	@Order(8)
	public void testReadTransfer() {
		//assertEquals(date, transferDAO.readTransfer(1).getDate(), "Reading one failed (date)");
		assertEquals(amount, transferDAO.readTransfer(1).getAmount(), "Reading one failed (amount)");
		assertEquals(cat1.getDescription(), transferDAO.readTransfer(1).getCategory().getDescription(), "Reading one failed (category)");
	}
	
	@Test
	@Order(9)
	public void testReadTransfers() {
		assertEquals(1, transferDAO.readTransfers().length, "Reading all failed (1)");
		assertEquals(true, transferDAO.createTransfer(transfer2), "Creation of transfer failed");
		assertEquals(2, transferDAO.readTransfers().length, "Reading all failed (2)");
	}
	
	@Test
	@Order(10)
	public void testReadSelectedTransfers() {
		Date startDate = Date.valueOf("2020-02-01");
		Date endDate = Date.valueOf("2020-03-01");
		assertEquals(1, transferDAO.readSeletedTransfers(startDate, endDate).length, "Reading selected transfers failed");
	}
	
	@Test
	@Order(11)
	public void testReadExpenses() {
		assertEquals(2, transferDAO.readExpenses().length, "Reading expenses failed");
		assertEquals(2, economyController.getExpenses().length, "Reading expenses failed (controller)");
	}
	
	@Test
	@Order(12)
	public void testReadIncome() {
		assertEquals(true, transferDAO.createTransfer(transfer3), "Creation of transfer failed");
		assertEquals(1, transferDAO.readIncome().length, "Reading incomes failed");
		assertEquals(1, economyController.getIncomes().length, "Reading incomes failed(controller)");
	}
	
	@Test
	@Order(13)
	public void testUpdateTransfer() {
		Transfer updatedTransfer = transferDAO.readTransfer(1); 
		updatedTransfer.setDate(Date.valueOf("2020-02-11"));
		assertEquals(true, transferDAO.updateTransfer(updatedTransfer), "Updating failed");
		//assertEquals(Date.valueOf("2020-02-11"), transferDAO.readTransfer(1).getDate(), "Date updating failed");
	}
	
	@Test
	@Order(14)
	public void testDeleteTransfer() {
		assertEquals(true, transferDAO.deleteTransfer(1), "Deleting 1 failed");
		assertEquals(true, transferDAO.deleteTransfer(2), "Deleting 2 failed");
		assertEquals(true, transferDAO.deleteTransfer(3), "Deleting 3 failed");
		assertEquals(0, transferDAO.readTransfers().length, "Deleting all failed");
	}
	
	@Test
	@Order(15)
	public void testDeleteCategory() {
		assertEquals(true, categoryDAO.deleteCategory(1), "Deleting 1 failed"); 
		assertEquals(true, categoryDAO.deleteCategory(2), "Deleting 2 failed");
		assertEquals(true, categoryDAO.deleteCategory(3), "Deleting 3 failed");
		assertEquals(0, categoryDAO.readIncomeCategories().length, "Deleting all incomes failed");
		assertEquals(0, categoryDAO.readExpenseCategories().length, "Deleting all expenses failed");
	}

}
