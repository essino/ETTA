package controller;

import java.io.IOException;

import com.calendarfx.view.page.WeekPage;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import model.Balance;
import model.BalanceDAO;
import view.MainViewGUI;

public class MainViewController {
	BalanceDAO balanceDao = new BalanceDAO();
	MainViewGUI mainViewGUI;
	
	//ei toimi
	public void getBalance() {
		Balance balance = new Balance(100);

	  balanceDao.createBalance(balance);
		mainViewGUI.setBalance(balanceDao.readBalance(0));
		
		
	}
}
