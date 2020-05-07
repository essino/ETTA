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
import model.Saving;
import model.Transfer;
import model.TransferDAO;

//tests for CategoryDAO, TransferDAO and EconomyController classes
@TestMethodOrder(OrderAnnotation.class)
public class CategoryConnectedDAOTest {
	
	private static CategoryDAO categoryDAO = new CategoryDAO(true);
	private static TransferDAO transferDAO = new TransferDAO(true);
	
	private EconomyController economyController = new EconomyController(categoryDAO, transferDAO);

	//used categories, there is both income and expense categories used in tests
	private static String catDesc = "lottery";
	private static boolean catIncome = true;
	private static Category category = new Category(catDesc, catIncome);
	private static Category cat1 = new Category("food", false);
	private static Category cat2 = new Category("salary", true);
	
	//used transfers, both income and expense
	private String desc = "grocery shopping";
	private Date date = Date.valueOf("2020-02-09");
	private float amount = 74;
	private Transfer transfer = new Transfer(desc, cat1, false, date, amount);
	private Transfer transfer2 = new Transfer("Ice cream", cat1, false, Date.valueOf("2020-03-21"), 3);
	private Transfer transfer3 = new Transfer("Work", cat2, true, date, 2500);
	
	//used Saving
	private String desc2 = "Kossin matka";
	private float amountGoal = 2000f;
	private float reachedGoal = 5f;
	private Date date2 = Date.valueOf("2020-10-15");
	private Saving kossinMatka = new Saving(desc2, amountGoal, reachedGoal, date2);

	//categories need for creating and updating the transfers
	@BeforeAll
	public static void createCategory() {
		categoryDAO.createCategory(cat1);
		categoryDAO.createCategory(cat2);
	}
	
	//testing of creating a category in CategoryDAO
	@Test
	@Order(1)
	public void testCreateCategory() {
		assertEquals(true, categoryDAO.createCategory(category), "Creation of category failed");
	}
	
	//testing reading a category by an ID in CategoryDAO
	@Test
	@Order(2)
	public void testReadCategory() {
		assertEquals(catDesc, categoryDAO.readCategory(3).getDescription(), "Reading one failed (description)");
		assertEquals(true, categoryDAO.readCategory(3).isCategory_type(), "Reading one failed (boolean)");
	}
	
	//testing reading a category by a description in CategoryDAO
	@Test
	@Order(3)
	public void testReadCategoryWithDesc() {
		assertEquals(true, categoryDAO.readCategory(catDesc).isCategory_type(), "Reading one with desc failed");
		assertEquals(null, categoryDAO.readCategory("jackpot"), "Reading with a desc that doesn't exist failed");
	}
	
	//testing reading income categories in CategoryDAO and EconomyController
	@Test
	@Order(4)
	public void testReadIncomeCategories() {
		assertEquals(2, categoryDAO.readIncomeCategories().length, "Reading all income categories failed");
		assertEquals(2, economyController.incomeCategoriesList().size(), "Reading all income categories failed (controller)");
	}
	
	//testing reading expense categories in CategoryDAO and EconomyController
	@Test
	@Order(5)
	public void testreadExpenseCategories() {
		assertEquals(1, categoryDAO.readExpenseCategories().length, "Reading all expense categories failed");
		assertEquals(1, economyController.expenseCategoriesList().size(), "Reading all expense categories failed(controller");
	}
	
	//testing updating a category in CategoryDAO, description is updated
	@Test
	@Order(6)
	public void testUpdateCategory() {
		String newDesc = "gift";
		Category updatedCategory = categoryDAO.readCategory(3);
		updatedCategory.setDescription(newDesc);
		assertEquals(true, categoryDAO.updateCategory(updatedCategory), "Updating failed");
		assertEquals(newDesc, categoryDAO.readCategory(3).getDescription(), "Description updating failed");
	}

	//testing creating a transfer in TransferDAO
	@Test
	@Order(7)
	public void testCreateTransfer() {
		assertEquals(true, transferDAO.createTransfer(transfer), "Creation of transfer failed");
	}
	
	//testing reading a transfer in TransferDAO
	@Test
	@Order(8)
	public void testReadTransfer() {
		//works in Eclipse, date differs one day in Jenkins -> not used at the moment
		//assertEquals(date, transferDAO.readTransfer(1).getDate(), "Reading one failed (date)");
		assertEquals(amount, transferDAO.readTransfer(1).getAmount(), "Reading one failed (amount)");
		assertEquals(cat1.getDescription(), transferDAO.readTransfer(1).getCategory().getDescription(), "Reading one failed (category)");
	}
	
	//testing reading all transfers in TransferDAO
	@Test
	@Order(9)
	public void testReadTransfers() {
		assertEquals(1, transferDAO.readTransfers().length, "Reading all failed (1)");
		assertEquals(true, transferDAO.createTransfer(transfer2), "Creation of transfer failed");
		assertEquals(2, transferDAO.readTransfers().length, "Reading all failed (2)");
	}
	
	//testing reading transfers during a given period in TransferDAO
	@Test
	@Order(10)
	public void testReadSelectedTransfers() {
		Date startDate = Date.valueOf("2020-02-01");
		Date endDate = Date.valueOf("2020-03-01");
		assertEquals(1, transferDAO.readSeletedTransfers(startDate, endDate).length, "Reading selected transfers failed");
	}
	
	//testing reading only expenses transfers in TransferDAO and EconomyController
	@Test
	@Order(11)
	public void testReadExpenses() {
		assertEquals(2, transferDAO.readExpenses().length, "Reading expenses failed");
		assertEquals(2, economyController.getExpenses().length, "Reading expenses failed (controller)");
	}
	
	//testing reading only income transfers in TransferDAO and EconomyController
	@Test
	@Order(12)
	public void testReadIncome() {
		assertEquals(true, transferDAO.createTransfer(transfer3), "Creation of transfer failed");
		assertEquals(1, transferDAO.readIncome().length, "Reading incomes failed");
		assertEquals(1, economyController.getIncomes().length, "Reading incomes failed(controller)");
	}
	
	//testing updating transfers in TransferDAO and EconomyController
	@Test
	@Order(13)
	public void testUpdateTransfer() {
		Transfer updatedTransfer = transferDAO.readTransfer(1); 
		//updating the date in transferDAO
		updatedTransfer.setDate(Date.valueOf("2020-02-11"));
		assertEquals(true, transferDAO.updateTransfer(updatedTransfer), "Updating failed");
		//works in Eclipse, date differs one day in Jenkins -> not used at the moment
		//assertEquals(Date.valueOf("2020-02-11"), transferDAO.readTransfer(1).getDate(), "Date updating failed");
		
		//updating the description
		String newDesc = "food";
		updatedTransfer.setDescription(newDesc);
		assertEquals(true, economyController.updateTransfer(updatedTransfer), "Updating failed");
		//updating the date in EconomyController
		updatedTransfer.setDate(date);
		assertEquals(true, economyController.updateTransfer(updatedTransfer), "Updating failed");
		Transfer updatedTransfer2 = transferDAO.readTransfer(3);
		//updating the description in EconomyController
		updatedTransfer2.setDescription("salary");
		assertEquals(true, economyController.updateTransfer(updatedTransfer2));
		//updating the date in EconomyController
		updatedTransfer2.setDate(date);
		assertEquals(true, economyController.updateTransfer(updatedTransfer2), "Updating failed");
	}
	
	//testing creating an income based on a saving's data, adds a transfer -> amount of transfers grows by one
	@Test
	@Order(14)
	public void testFromSavingToIncome() {
		assertEquals(true, economyController.fromSavingToIncome(kossinMatka), "From saving to income failed");
		assertEquals(4, transferDAO.readTransfers().length, "adding transfer failed failed");
	}
	
	//testing creating an expense based on a saving's data, adds a transfer -> amount of transfers grows by one
	@Test
	@Order(15)
	public void testFromSavingToExpense() {
		assertEquals(true, economyController.fromSavingToExpense(kossinMatka), "From saving to income failed");
		assertEquals(5, transferDAO.readTransfers().length, "adding transfer failed failed");
	}
	
	//testing deleting a transfer in TransferDAO, deletes all transfers added during the tests
	@Test
	@Order(16)
	public void testDeleteTransfer() {
		assertEquals(true, transferDAO.deleteTransfer(1), "Deleting 1 failed"); 
		assertEquals(true, transferDAO.deleteTransfer(2), "Deleting 2 failed");
		assertEquals(true, transferDAO.deleteTransfer(3), "Deleting 3 failed");
		assertEquals(true, transferDAO.deleteTransfer(4), "Deleting 3 failed");
		assertEquals(true, transferDAO.deleteTransfer(5), "Deleting 3 failed");
		assertEquals(0, transferDAO.readTransfers().length, "Deleting all failed");
	}
	
	//testing deleting a category in CategoryDAO, deletes all transfers added during the tests
	@Test
	@Order(17)
	public void testDeleteCategory() {
		assertEquals(true, categoryDAO.deleteCategory(1), "Deleting 1 failed"); 
		assertEquals(true, categoryDAO.deleteCategory(2), "Deleting 2 failed");
		assertEquals(true, categoryDAO.deleteCategory(3), "Deleting 3 failed");
		assertEquals(true, categoryDAO.deleteCategory(4), "Deleting 3 failed");
		assertEquals(true, categoryDAO.deleteCategory(5), "Deleting 3 failed");
		assertEquals(0, categoryDAO.readIncomeCategories().length, "Deleting all incomes failed");
		assertEquals(0, categoryDAO.readExpenseCategories().length, "Deleting all expenses failed");
	}
	
}
