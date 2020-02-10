package fi.metropolia.OTP.group2.ETTA;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.sql.Date;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import model.Person;
import model.PersonDAO;
import model.Saving;
import model.SavingDAO;

public class SavingDAOTest {

	private SavingDAO savingDAO = new SavingDAO();
	private String desc = "Kossin matka";
	private float amountGoal = 2000f;
	private float reachedGoal = 5f;
	private String str = "2020-10-15";
	private Date date = Date.valueOf(str);
	private Saving kossinMatka = new Saving(desc, amountGoal, reachedGoal, date);

	@Test
	public void testCreate() {
		assertEquals(true, savingDAO.createSaving(kossinMatka), "Creation of person failed");
	}
	
	@Test
	@Disabled
	public void testRead() {
		assertEquals(true, savingDAO.readSaving(), "Reading failed");
	}
	
	@Test
	@Disabled
	public void testUpdate() {
		fail("Not yet implemented");
	}
	
	@Test
	@Disabled
	public void testDelete() {
		fail("Not yet implemented");
	}
}
