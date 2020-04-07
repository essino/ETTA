package fi.metropolia.OTP.group2.ETTA;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Date;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import com.mysql.cj.conf.BooleanProperty;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;

import model.Item;
import model.ItemDAO;
import model.Person;
import model.PersonDAO;

@TestMethodOrder(OrderAnnotation.class)
class ItemDAOTest {
/*
		private ItemDAO itemDAO = new ItemDAO(true);
		//private int id=1;
		private String desc = "Ystävänpäiväkortti";
		private double price = 3.5;
		private String str = "2020-02-14";
		private Date dateNeeded = Date.valueOf(str);
		private String additionalInfo = null;
		private static Person tiina = new Person("Tiina", Date.valueOf("1997-06-17"), "tiina.vanhanen@metropolia.fi");
		private static  PersonDAO personDAO = new PersonDAO(true);
		private Item item = new Item(desc, tiina, price, dateNeeded, additionalInfo);
		private int length = 0;
		private int id = 0;
	
		
		@BeforeAll
		public static void createPerson() {
			//personDAO.createPerson(tiina);
			assertEquals(true, personDAO.createPerson(tiina), "Creation of person failed");
		}
		
		@AfterAll
		public static void deletePerson() {
			int length = personDAO.readPeople().length;
			int id = personDAO.readPeople()[length-1].getPerson_id();
			assertEquals(true, personDAO.deletePerson(id), "Deleting person failed");
		}

		@Test
		@Order(1)
		public void testCreate() {
			assertEquals(true, itemDAO.createItem(item), "Creation of item failed");
		}
		
		@Test
		@Order(2)
		public void testReadItems() {
			length = itemDAO.readItems().length;
			Item item2 = new Item("kirja", tiina, 10, dateNeeded, additionalInfo);
			assertEquals(true, itemDAO.createItem(item2), "Creation of item2 failed");
			assertEquals(length+1, itemDAO.readItems().length, "Reading all failed");
		}
		
		@Test
		@Order(3)
		public void testReadItem() {
			length = itemDAO.readItems().length;
			id = itemDAO.readItems()[length-2].getItem_id();
			assertEquals(desc, itemDAO.readItem(id).getDescription(), "Reading one failed (description)");
			assertEquals(price, itemDAO.readItem(id).getPrice(), "Reading one failed (price)");
			assertEquals(dateNeeded, itemDAO.readItem(id).getDateNeeded(), "Reading one failed (date)");
			assertEquals(tiina.getName(), itemDAO.readItem(id).getPerson().getName(), "Reading one failed (person)");
		}
		
		@Test
		@Order(4)
		public void testUpdate() {
			length = itemDAO.readItems().length;
			id = itemDAO.readItems()[length-2].getItem_id();
			Item updatedItem = itemDAO.readItem(id);
			updatedItem.setPrice(2.3);
			assertEquals(true, itemDAO.updateItem(updatedItem), "Updating failed");
			assertEquals(2.3, itemDAO.readItem(item.getItem_id()).getPrice(), "Price updating failed");
		}
		
		@Test
		@Order(5)
		public void testDelete() {
			length = itemDAO.readItems().length;
			id = itemDAO.readItems()[length-2].getItem_id();
			assertEquals(true, itemDAO.deleteItem(id), "Deleting 1 failed");
			assertEquals(true, itemDAO.deleteItem(id+1), "Deleting 2 failed");
		}
*/

}
