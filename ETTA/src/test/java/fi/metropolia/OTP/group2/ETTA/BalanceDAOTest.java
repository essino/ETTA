package fi.metropolia.OTP.group2.ETTA;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import controller.EconomyController;
import model.Balance;
import model.BalanceDAO;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;

@TestMethodOrder(OrderAnnotation.class)
public class BalanceDAOTest {

	private BalanceDAO balanceDAO = new BalanceDAO(true);
	private EconomyController controller = new EconomyController(balanceDAO);
	
	private Balance balance = new Balance(100);
	private int id = 1; 
	
	
	@Test
	@Order(1)
	public void testCreate() {
		assertEquals(true, balanceDAO.createBalance(balance), "Creation of balance failed");
	}
	
	@Test
	@Order(2)
	public void testReadBalance() {
		assertEquals(100, balanceDAO.readBalance(id).getBalance(), "Reading failed");
		assertEquals(true, controller.enoughBalance(-50), "Reading failed (controller)");
		assertEquals(false, controller.enoughBalance(-150), "Reading failed (controller)");
		assertEquals(100, controller.getBalanceAmount(), "Reading balance failed");
	}
	
	@Test
	@Order(3)
	public void testUpdate() {
		Balance balance = balanceDAO.readBalance(id);
		balance.setBalance(0);
		balanceDAO.updateBalance(balance);
		assertEquals(0, balanceDAO.readBalance(id).getBalance(), "balance amount updating failed");
		assertEquals(true, controller.updateBalanceAmount(20), "balance amount updating failed");
		assertEquals(20, balanceDAO.readBalance(id).getBalance(), "balance amount updating failed");
	}
	
}
