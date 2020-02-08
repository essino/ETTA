package controller;

import model.Balance;
import model.BalanceDAO;
import view.MainPageGUI;

public class MainViewController {
	BalanceDAO balanceDao = new BalanceDAO();
	MainPageGUI mainPageGUI;
	
	public MainViewController(MainPageGUI mainPageGUI) {
		this.mainPageGUI = mainPageGUI;
	}

	public void getBalance() {
		if(!checkIfBalanceExist()) {
			System.out.println("creation ei onnistunut");
		}
		mainPageGUI.setBalance(balanceDao.readBalance(1).getBalance());
	}

	public boolean checkIfBalanceExist() {
		if(balanceDao.readBalance(1) == null) {
			createBalance();
			return true;
		}
		return true;
	}
	
	public void createBalance() {
		Balance balance = new Balance(0);
		balanceDao.createBalance(balance);
	}
}
