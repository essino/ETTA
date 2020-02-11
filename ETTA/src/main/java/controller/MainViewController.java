package controller;

import model.BalanceDAO;
import view.MainPageGUI;

public class MainViewController {
	BalanceDAO balanceDao = new BalanceDAO();
	MainPageGUI mainPageGUI;
	
	public MainViewController(MainPageGUI mainPageGUI) {
		this.mainPageGUI = mainPageGUI;
	}

	public void getBalance() {
		mainPageGUI.setBalance(balanceDao.readBalance(1).getBalance());
	}

	
}
