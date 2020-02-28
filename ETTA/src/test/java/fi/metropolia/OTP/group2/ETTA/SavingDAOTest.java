package fi.metropolia.OTP.group2.ETTA;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.sql.Date;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;

import model.Person;
import model.PersonDAO;
import model.Saving;
import model.SavingDAO;

@TestMethodOrder(OrderAnnotation.class)
public class SavingDAOTest {

	private SavingDAO savingDAO = new SavingDAO();
	private String desc = "Kossin matka";
	private float amountGoal = 2000f;
	private float reachedGoal = 5f;
	private String str = "2020-10-15";
	private Date date = Date.valueOf(str);
	private Saving kossinMatka = new Saving(desc, amountGoal, reachedGoal, date);
	private int id = 1;

	@Test
	@Order(1)
	public void testCreate() {
		assertEquals(true, savingDAO.createSaving(kossinMatka), "Creation of person failed");
	}
	/*
	@Test
	@Order(2)
	public void testReadSavings() {
		assertEquals(1, savingDAO.readSavings().length, "Reading all failed");
	}
	
	@Test
	@Order(3)
	public void testReadSaving() {
		assertEquals(date, savingDAO.readSaving(id).getGoalDate(), "Reading one failed (GoalDate)");
		assertEquals(amountGoal, savingDAO.readSaving(id).getGoalAmount(), "Reading one failed (AmountGoal)");
	}
	
	@Test
	@Order(4)
	public void testUpdate() {
		Date newDate = Date.valueOf("2200-10-15");
		Saving updatedSaving = savingDAO.readSaving(id);
		updatedSaving.setGoalDate(newDate);
		assertEquals(true, savingDAO.updateSaving(updatedSaving), "Updating failed");
		assertEquals(newDate, savingDAO.readSaving(id).getGoalDate(), "Goalday updating failed");
	}
	
	@Test
	@Order(5)
	public void testDelete() {
		assertEquals(true, savingDAO.deleteSaving(id), "Deleting failed");
	}

	*/
}
