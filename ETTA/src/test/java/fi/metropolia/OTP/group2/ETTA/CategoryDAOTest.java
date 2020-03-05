package fi.metropolia.OTP.group2.ETTA;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.sql.Date;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;

import model.Category;
import model.CategoryDAO;

@TestMethodOrder(OrderAnnotation.class)
public class CategoryDAOTest {
	
	private static CategoryDAO categoryDAO = new CategoryDAO();
	private String desc = "abracadabra";
	private boolean income = false;
	private Category category = new Category(desc, income);
	private int id = 1;
	private static int length = 0;
	private static int index = 0;

	@AfterAll
	public static void clear() {
		//length = categoryDAO.readCategories().length;
		//index = categoryDAO.readCategories()[length-1].getCategory_id();
		//assertEquals(true, categoryDAO.deleteCategory(index), "deleting failed");
	}
	@Test
	@Order(1)
	public void testCreate() {
		assertEquals(true, categoryDAO.createCategory(category), "Creation of category failed");
	}
	
	@Test
	@Order(2)
	public void testReadCategories() {
		length = categoryDAO.readIncomeCategories().length;
		Category category2 = new Category("veikkaus", true);
		assertEquals(true, categoryDAO.createCategory(category2), "Creation of category failed");
		length++;
		assertEquals(length, categoryDAO.readIncomeCategories().length, "Reading all failed");
	}
	
	@Test
	@Order(3)
	public void testReadCategory() {
		length = categoryDAO.readIncomeCategories().length;
		index = categoryDAO.readIncomeCategories()[length-1].getCategory_id();
		assertEquals("veikkaus", categoryDAO.readCategory(index).getDescription(), "Reading one failed (description)");
	}
	/*
	@Test
	@Order(4)
	public void testUpdate() {
		String newDesc = "salary";
		category.setDescription(newDesc);
		length = categoryDAO.readCategories().length;
		index = categoryDAO.readCategories()[length-1].getCategory_id();
		assertEquals(true, categoryDAO.updateCategory(category), "Updating failed");
		assertEquals(newDesc, categoryDAO.readCategory(index-1).getDescription(), "Description updating failed");
	}
	*/
	@Test
	@Order(5)
	public void testDelete() {
		length = categoryDAO.readIncomeCategories().length;
		System.out.println("length in deleting " + length);
		
		index = categoryDAO.readIncomeCategories()[length-1].getCategory_id();
		System.out.println("id in deleting " + index);
		assertEquals(true, categoryDAO.deleteCategory(index), "deleting failed");
		assertEquals(true, categoryDAO.deleteCategory(index-1), "Deleting failed");
	}

}
