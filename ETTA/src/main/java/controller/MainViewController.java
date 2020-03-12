package controller;

import model.Balance;
import model.BalanceDAO;
import view.mainPage.MainPageGUI;
/** 
 * Controller class for the main page.  
 * 
 */
public class MainViewController {
	/**
	 * BalanceDAO used for accessing the database
	 */
	BalanceDAO balanceDao = new BalanceDAO();
	/**
	 * Reference to MainPageGUI
	 */
	MainPageGUI mainPageGUI;
	
	/** 
	 * Constructor 
	 * @param mainPageGUI 
	 */
	public MainViewController(MainPageGUI mainPageGUI) {
		this.mainPageGUI = mainPageGUI;
	}
	/** 
	 * Method that gets balance amount from BalanceDAO and gives it forward to BalanceOverviewGUI to display it on the page. 
	 */
	public void getBalance() {
		if(checkIfBalanceExist()) {
		mainPageGUI.setBalance(balanceDao.readBalance(1).getBalance());
		}
	}

	/** 
	 * Method that checks if the balance is already set in the database, if not - a new balance is created 
	 * @return true if balance is already set or after it is set 
	 */
	public boolean checkIfBalanceExist() { 
		if(balanceDao.readBalance(1) == null) { 
			createBalance(); 
			//return true;
		} 
		return true;
	} 
	
	/** 
	 * Method that tells BalanceDAO to create the balance to the database.  
	 */
	public void createBalance() { 
		System.out.println("creating balance");
			Balance balance = new Balance(0); 
			balanceDao.createBalance(balance);
	}
}
