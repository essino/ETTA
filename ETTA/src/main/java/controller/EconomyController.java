package controller;

import model.Balance;
import model.BalanceDAO;
import view.BalanceOverviewGUI;

public class EconomyController {
	
	BalanceDAO balanceDao = new BalanceDAO(); 

	BalanceOverviewGUI balanceOverviewGUI; 

	/** 
	 * Constructor 
	 * @param balanceOverviewGUI 
	 */ 

	public EconomyController(BalanceOverviewGUI balanceOverviewGUI) { 

		this.balanceOverviewGUI= balanceOverviewGUI; 

	} 

	/** 
	 * Method that gets balance amount from BalanceDAO and gives it forward to BalanceOverviewGUI to display it on the page 
	 */ 
	public void getBalance() { 
		balanceOverviewGUI.setBalance(balanceDao.readBalance(1).getBalance()); 
		if(balanceDao.readBalance(1).getBalance() == 0.00) {
			balanceOverviewGUI.showSetBalance();
		}
		else if (balanceDao.readBalance(1).getBalance() != 0.00) {
			balanceOverviewGUI.hideSetBalance();
		}
	}
	
	public void updateBalance(float amount) {
		Balance balance = new Balance(1, amount);
		balanceDao.updateBalance(balance);
		getBalance();
	}

}
