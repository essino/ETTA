package fi.metropolia.OTP.group2.ETTA;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.sql.Date;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;

import model.Category;
import model.CategoryDAO;

@TestMethodOrder(OrderAnnotation.class)
public class CategoryDAOTest {
	
	private CategoryDAO categoryDAO = new CategoryDAO();
	private String desc = "food";
	private Category category = new Category(desc);
	private int id = 1;

	@Test
	@Order(1)
	public void testCreate() {
		assertEquals(true, categoryDAO.createCategory(category), "Creation of category failed");
	}
	/*
	@Test
	@Order(2)
	public void testReadCategories() {
		assertEquals(1, categoryDAO.readCategories().length, "Reading all failed");
	}
	
	@Test
	@Order(3)
	public void testReadCategory() {
		assertEquals(desc, categoryDAO.readCategory(id).getDescription(), "Reading one failed (description)");
	}
	
	@Test
	@Order(4)
	public void testUpdate() {
		String newDesc = "salary";
		Category updatedCategory = new Category(newDesc);
		assertEquals(true, categoryDAO.updateCategory(updatedCategory), "Updating failed");
		assertEquals(newDesc, categoryDAO.readCategory(id).getDescription(), "Price updating failed");
	}
	
	@Test
	@Order(5)
	public void testDelete() {
		assertEquals(true, categoryDAO.deleteCategory(category.getDescription()), "Deleting failed");
	}
*/
}
