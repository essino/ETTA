package fi.metropolia.OTP.group2.ETTA;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.sql.Date;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;

import model.Saving;
import model.SavingDAO;

@TestMethodOrder(OrderAnnotation.class)
public class SavingDAOTest {

	private SavingDAO savingDAO = new SavingDAO(true);
	private String desc = "Kossin matka";
	private float amountGoal = 2000f;
	private float reachedGoal = 5f;
	private String str = "2020-10-15";
	private Date date = Date.valueOf(str);
	private Saving kossinMatka = new Saving(desc, amountGoal, reachedGoal, date);

	@Test
	@Order(1)
	public void testCreate() {
		assertEquals(true, savingDAO.createSaving(kossinMatka), "Creation of saving failed");
	}
	
	@Test
	@Order(2)
	public void testReadSaving() {
		assertEquals(desc, savingDAO.readSaving(1).getDescription(), "Reading one failed (Description)");
		assertEquals(date, savingDAO.readSaving(1).getGoalDate(), "Reading one failed (GoalDate)");
		assertEquals(reachedGoal, savingDAO.readSaving(1).getAmount(), "Reading one failed (Amount)");
		assertEquals(amountGoal, savingDAO.readSaving(1).getGoalAmount(), "Reading one failed (AmountGoal)");
	}
	
	@Test
	@Order(3)
	public void testReadSavings() {
		assertEquals(1, savingDAO.readSavings().length, "Reading all failed");
		Saving saving = new Saving("Auto", 10000, 0, Date.valueOf("2021-01-01"));
		assertEquals(true, savingDAO.createSaving(saving), "Creation of saving failed");
		assertEquals(2, savingDAO.readSavings().length, "Reading all failed");
	}
	
	@Test
	@Order(4)
	public void testUpdate() {
		Date newDate = Date.valueOf("2021-10-15");
		Saving updatedSaving = savingDAO.readSaving(1);
		updatedSaving.setGoalDate(newDate);
		assertEquals(true, savingDAO.updateSaving(updatedSaving), "Updating failed");
		assertEquals(newDate, savingDAO.readSaving(1).getGoalDate(), "Goalday updating failed");
	}
	
	@Test
	@Order(5)
	public void testDelete() {
		assertEquals(true, savingDAO.deleteSaving(1), "Deleting 1 failed");
		assertEquals(true, savingDAO.deleteSaving(2), "Deleting 2 failed");
		assertEquals(0, savingDAO.readSavings().length, "Deleting all failed");
	}

}
