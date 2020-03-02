package controller;

import java.sql.Date;

import model.Balance;
import view.EconomyGUI;
import model.BalanceDAO;
import view.BalanceOverviewGUI;
import model.TransferDAO;
import model.Transfer;



public class EconomyController {
	
	BalanceDAO balanceDao = new BalanceDAO(); 

	BalanceOverviewGUI balanceOverviewGUI; 
	
	private EconomyGUI ecoGUI;
	private TransferDAO transDAO = new TransferDAO();

	
	public void saveTransfer() {
<<<<<<< HEAD
		String Description = ecoGUI.getReason();
		float IncomeAmount = ecoGUI.getIncomeAmount();
		Date IncomeDate = ecoGUI.getIncomeDate();
		String Category =ecoGUI.getCategory();
		
		Transfer transfer = new Transfer(Description, IncomeAmount, IncomeDate, Category);
		Boolean Transfer = transDAO.createTransfer(transfer);
	}
	
	//Pitääkö tätä olla
	public EconomyController(EconomyGUI ecoGUI) {
		this.ecoGUI = ecoGUI;
=======
		//String description = ecoGUI.getReason();
>>>>>>> branch 'master' of https://github.com/essino/ETTA.git
	}
	
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
	
	/** 
	 * Method that gets balance amount from BalanceOverviewGUI  and gives it forward to BalanceDAO  to update in the database. 
	 * @param amount float amount of the new balance
	 */
	public void updateBalance(float amount) {
		Balance balance = new Balance(1, amount);
		balanceDao.updateBalance(balance);
		getBalance();
	}

}
