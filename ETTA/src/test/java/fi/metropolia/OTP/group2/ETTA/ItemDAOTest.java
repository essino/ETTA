package fi.metropolia.OTP.group2.ETTA;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Date;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;

import model.Item;
import model.ItemDAO;
import model.Person;
import model.PersonDAO;

@TestMethodOrder(OrderAnnotation.class)
class ItemDAOTest {
		private ItemDAO itemDAO = new ItemDAO();
		private int id = 40;
		private String desc = "Ystävänpäiväkortti";
		private double price = 3.5;
		private String str = "2020-02-14";
		private Date dateNeeded = Date.valueOf(str);
		private String additionalInfo = null;
		private static PersonDAO personDAO = new PersonDAO();
		private static Person lena = new Person("Lena", Date.valueOf("1980-08-10"), "elena.myllari@metropolia.fi");
		private Item item = new Item(desc, lena, price, dateNeeded, additionalInfo);
		
		@BeforeAll
		public static void createPerson() {
			personDAO.createPerson(lena);
		}

		@Test
		@Order(1)
		public void testCreate() {
			assertEquals(true, itemDAO.createItem(item), "Creation of item failed");
		}
		
		@Test
		@Order(2)
		public void testReadItems() {
			assertEquals(3, itemDAO.readItems().length, "Reading all failed");
		}
		
		@Test
		@Order(3)
		public void testReadItem() {
			assertEquals(desc, itemDAO.readItem(id).getDescription(), "Reading one failed (description)");
			assertEquals(price, itemDAO.readItem(id).getPrice(), "Reading one failed (price)");
			assertEquals(dateNeeded, itemDAO.readItem(id).getDateNeeded(), "Reading one failed (date)");
			assertEquals(lena, itemDAO.readItem(id).getPerson(), "Reading one failed (person)");
		}
		
		@Test
		@Order(4)
		public void testUpdate() {
			Item updatedItem = new Item(desc, lena, 1.56, dateNeeded, additionalInfo);
			assertEquals(true, itemDAO.updateItem(updatedItem), "Updating failed");
			assertEquals(price, itemDAO.readItem(id).getPrice(), "Price updating failed");
		}
		
		@Test
		@Order(5)
		public void testDelete() {
			assertEquals(true, itemDAO.deleteItem(id), "Deleting failed");
		}


}