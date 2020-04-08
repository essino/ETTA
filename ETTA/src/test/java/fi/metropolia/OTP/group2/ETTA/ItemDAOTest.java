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

		private ItemDAO itemDAO = new ItemDAO(true);
		private int id = 1;
		private String desc = "Ystävänpäiväkortti";
		private double price = 3.5;
		private String str = "2020-02-14";
		private Date dateNeeded = Date.valueOf(str);
		private String additionalInfo = null;
		private static Person tiina = new Person("Tiina", Date.valueOf("1997-06-17"), "tiina.vanhanen@metropolia.fi");
		private static  PersonDAO personDAO = new PersonDAO(true);
		private Item item = new Item(desc, tiina, price, dateNeeded, additionalInfo);

		@BeforeAll
		public static void createPerson() {
			personDAO.createPerson(tiina);
		}
		
		@Test
		@Order(1)
		public void testCreate() {
			assertEquals(true, itemDAO.createItem(item), "Creation of item failed");
		}
		
		@Test
		@Order(2)
		public void testReadItem() {
			assertEquals(desc, itemDAO.readItem(1).getDescription(), "Reading one failed (description)");
			assertEquals(price, itemDAO.readItem(1).getPrice(), "Reading one failed (price)");
			assertEquals(dateNeeded, itemDAO.readItem(1).getDateNeeded(), "Reading one failed (date)");
			assertEquals(tiina.getName(), itemDAO.readItem(1).getPerson().getName(), "Reading one failed (person)");
		}
		@Test
		@Order(3)
		public void testReadItemWithDesc() {
			assertEquals(price, itemDAO.readItem("Ystävänpäiväkortti").getPrice(), "Reading one with desc failed (price)");
			assertEquals(dateNeeded, itemDAO.readItem("Ystävänpäiväkortti").getDateNeeded(), "Reading one with desc failed (date)");
			assertEquals(tiina.getName(), itemDAO.readItem("Ystävänpäiväkortti").getPerson().getName(), "Reading one with desc failed (person)");
			assertEquals(null, itemDAO.readItem("Mekko"), "Reading with a desc that doesn't exitst failed");
		}
		
		@Test
		@Order(4)
		public void testReadItems() {
			assertEquals(1, itemDAO.readItems().length, "Reading all failed");
			Item item2 = new Item("Kirja", tiina, 10.0, dateNeeded, additionalInfo);
			assertEquals(true, itemDAO.createItem(item2), "Creation of item2 failed");
			assertEquals(2, itemDAO.readItems().length, "Reading all failed");
		}
		
		@Test
		@Order(5)
		public void testReadItemsByPerson() {
			assertEquals(2, itemDAO.readItemsByPerson(1).length, "Reading all Tiina's items failed");
		}
		
		@Test
		@Order(6)
		public void testReadOwnItems() {
			Item item2 = new Item("Paita", null, 49.99, dateNeeded, "Zalando");
			assertEquals(true, itemDAO.createItem(item2), "Creation of item failed");
			assertEquals(1, itemDAO.readOwnItems().length, "Reading all own items failed");
		}
		
		@Test
		@Order(7)
		public void testReadItemsByBought() {
			Item item2 = new Item("Lippu", tiina, 5.0, dateNeeded, additionalInfo, true);
			assertEquals(true, itemDAO.createItem(item2), "Creation of item failed");
			assertEquals(1, itemDAO.readItemsByBought(true).length, "Reading all bought items failed");
		}
		
		@Test
		@Order(8)
		public void testUpdate() {
			Item updatedItem = itemDAO.readItem(1);
			updatedItem.setPrice(2.3);
			assertEquals(true, itemDAO.updateItem(updatedItem), "Updating failed");
			assertEquals(2.3, itemDAO.readItem(1).getPrice(), "Price updating failed");
		}
		
		@Test
		@Order(9)
		public void testDelete() {
			assertEquals(true, itemDAO.deleteItem(1), "Deleting 1 failed");
			assertEquals(true, itemDAO.deleteItem(2), "Deleting 2 failed");
		}

}
