package fi.metropolia.OTP.group2.ETTA;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;

import model.Category;
import model.CategoryDAO;

@TestMethodOrder(OrderAnnotation.class)
public class CategoryDAOTest {
	
	private static CategoryDAO categoryDAO = new CategoryDAO(true);
	private String desc = "veikkaus";
	private boolean income = true;
	private Category category = new Category(desc, income);

	@Test
	@Order(1)
	public void testCreate() {
		assertEquals(true, categoryDAO.createCategory(category), "Creation of category failed");
	}
	
	@Test
	@Order(2)
	public void testReadCategory() {
		assertEquals("veikkaus", categoryDAO.readCategory(1).getDescription(), "Reading one failed (description)");
		assertEquals(true, categoryDAO.readCategory(1).isCategory_type(), "Reading one failed (boolean)");
	}
	
	@Test
	@Order(3)
	public void testReadCategoryWithDesc() {
		assertEquals(true, categoryDAO.readCategory("veikkaus").isCategory_type(), "Reading one with desc failed (boolean)");
		assertEquals(null, categoryDAO.readCategory("lottovoitto"), "Reading with a desc that doesn't exist failed (description)");
	}
	
	@Test
	@Order(4)
	public void testReadIncomeCategories() {
		assertEquals(1, categoryDAO.readIncomeCategories().length, "Reading all income categories failed");
		Category category2 = new Category("palkka", true);
		assertEquals(true, categoryDAO.createCategory(category2), "Creation of category failed");
		assertEquals(2, categoryDAO.readIncomeCategories().length, "Reading all income categories failed");
	
	}
	
	@Test
	@Order(5)
	public void testreadExpenseCategories() {
		assertEquals(0, categoryDAO.readExpenseCategories().length, "Reading all expense categories failed");
		Category category3 = new Category("ruoka", false);
		assertEquals(true, categoryDAO.createCategory(category3), "Creation of category failed");
		assertEquals(1, categoryDAO.readExpenseCategories().length, "Reading all expense categories failed");
	}
	
	@Test
	@Order(6)
	public void testUpdate() {
		String newDesc = "synttärilahja";
		Category updatedCategory = categoryDAO.readCategory(1);
		updatedCategory.setDescription(newDesc);
		assertEquals(true, categoryDAO.updateCategory(updatedCategory), "Updating failed");
		assertEquals(newDesc, categoryDAO.readCategory(1).getDescription(), "Description updating failed");
	}

	@Test
	@Order(7)
	public void testDelete() {
		assertEquals(true, categoryDAO.deleteCategory(1), "Deleting 1 failed");
		assertEquals(true, categoryDAO.deleteCategory(2), "Deleting 2 failed");
		assertEquals(true, categoryDAO.deleteCategory(3), "Deleting 3 failed");
		assertEquals(0, categoryDAO.readIncomeCategories().length, "Deleting all incomes failed");
		assertEquals(0, categoryDAO.readExpenseCategories().length, "Deleting all expenses failed");
	}

}
