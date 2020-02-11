package controller;

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
	}

}
