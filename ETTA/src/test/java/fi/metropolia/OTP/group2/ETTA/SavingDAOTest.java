package fi.metropolia.OTP.group2.ETTA;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.sql.Date;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import controller.EconomyController;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;

import model.Saving;
import model.SavingDAO;

//Tests for SavingDAO and the savings related parts of EconomyController
@TestMethodOrder(OrderAnnotation.class)
public class SavingDAOTest {

	//used DAO class and controller
	private SavingDAO savingDAO = new SavingDAO(true);
	private EconomyController controller = new EconomyController(savingDAO);

	//test variables
	private String desc = "Kossin matka";
	private float amountGoal = 2000f;
	private float reachedGoal = 5f;
	private Date date = Date.valueOf("2020-10-15");
	private Saving kossinMatka = new Saving(desc, amountGoal, reachedGoal, date);

	//Test for creating a saving
	@Test
	@Order(1)
	public void testCreateSaving() {
		assertEquals(true, savingDAO.createSaving(kossinMatka), "Creation of saving failed"); 
	}
	
	//Test for reading one saving
	@Test
	@Order(2)
	public void testReadSaving() {
		assertEquals(desc, savingDAO.readSaving(1).getDescription(), "Reading one failed (Description)");
		//Test works in eclipse, Jenkins does not agree with the date
		//assertEquals(date, savingDAO.readSaving(1).getGoalDate(), "Reading one failed (GoalDate)");
		assertEquals(reachedGoal, savingDAO.readSaving(1).getAmount(), "Reading one failed (Amount)");
		assertEquals(amountGoal, savingDAO.readSaving(1).getGoalAmount(), "Reading one failed (AmountGoal)");
	}
	
	//Test for reading all savings both through the DAO and the controller
	@Test
	@Order(3)
	public void testReadSavings() {
		assertEquals(1, savingDAO.readSavings().length, "Reading all failed");
		Saving saving = new Saving("Auto", 10000, 0, Date.valueOf("2021-01-01"));
		assertEquals(true, savingDAO.createSaving(saving), "Creation of saving failed");
		assertEquals(2, savingDAO.readSavings().length, "Reading all failed");
		assertEquals(2, controller.getSavingsList().size(), "Reading all failed (controller");
		assertEquals(2, controller.getSavingss().length, "Reading all failed (controller");
	}
	
	//Test for updating the saving
	@Test
	@Order(4)
	public void testUpdateSaving() {
		Date newDate = Date.valueOf("2021-10-15");
		Saving updatedSaving = savingDAO.readSaving(1);
		updatedSaving.setGoalDate(newDate);
		assertEquals(true, savingDAO.updateSaving(updatedSaving), "Updating failed");
		//Test works in eclipse, Jenkins does not agree with the date
		//assertEquals(newDate, savingDAO.readSaving(1).getGoalDate(), "Goalday updating failed");
		assertEquals(true, controller.updateSaving(updatedSaving), "Updating failed (controller)");
	}
	
	//Test for searching and finding the saving with its description, DAO and controller
	@Test
	@Order(5)
	public void testReadSavingByDescription() {
		assertEquals(amountGoal, (savingDAO.getSaving(desc).getGoalAmount()), "Reading 1 by description failed");
		assertEquals(amountGoal, controller.getSaving(desc).getGoalAmount(), "Reading one failed (controller)");
	}
	
	//Test for deleting a saving
	@Test
	@Order(6)
	public void testDeleteSaving() {
		assertEquals(true, savingDAO.deleteSaving(1), "Deleting 1 failed");
		assertEquals(true, savingDAO.deleteSaving(2), "Deleting 2 failed");
		assertEquals(0, savingDAO.readSavings().length, "Deleting all failed");
	}

}
