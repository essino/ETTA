package fi.metropolia.OTP.group2.ETTA;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import model.Balance;
import model.BalanceDAO;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;

@TestMethodOrder(OrderAnnotation.class)
public class BalanceDAOTest {

	private BalanceDAO balanceDAO = new BalanceDAO();
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
	}
	
	@Test
	@Order(1)
	public void testUpdate() {
		Balance balance = balanceDAO.readBalance(id);
		balance.setBalance(0);
		balanceDAO.updateBalance(balance);
		assertEquals(0, balanceDAO.readBalance(id).getBalance(), "balance amount updating failed");
		
	}

}
