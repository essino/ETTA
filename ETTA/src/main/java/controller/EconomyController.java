package controller;


import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Date;

import model.Balance;
import view.EconomyGUI;
import view.EconomyIncomeGUI;
import view.EconomyOutcomeGUI;
import model.BalanceDAO;
import model.Category;
import model.CategoryDAO;
import view.BalanceOverviewGUI;
import view.EconomyAddOutcomeGUI;
import model.TransferDAO;
import model.Transfer;
import view.EconomyAddIncomeGUI;

public class EconomyController {
	/**
	 * Reference to the BalanceOverviewGUI
	 */
	private BalanceOverviewGUI balanceOverviewGUI;
	/**
	 * Reference to the EconomyGUI
	 */
	private EconomyGUI ecoGUI;
	/**
	 * Reference to the EconomyAddOutcomeGUI
	 */
	private EconomyAddOutcomeGUI addExpenceGUI;
	

	/**
	 * Reference to the EconomyAddcomeGUI
	 */
	//private EconomyAddIncomeGUI addIncomeGUI = new EconomyAddIncomeGUI ();
	

	/**
	 * Reference to the EconomyOutcomeGUI
	 */
	private EconomyOutcomeGUI expenceGUI;
	/**
	 * BalanceDAO used for accessing the database
	 */
	private BalanceDAO balanceDao = new BalanceDAO(); 
	/**
	 * TransferDAO used for accessing the database
	 */
	private TransferDAO transDAO = new TransferDAO();
	/**
	 * CategoryDAO used for accessing the database
	 */
	private CategoryDAO categoryDAO = new CategoryDAO();
	/**
	 * Reference to the EconomyAddIncomeGUI
	 */
	private EconomyAddIncomeGUI economyAddIncomeGUI;
	private EconomyIncomeGUI incomeGUI;
	
	public void saveTransfer() {


		String description = ecoGUI.getReason();
		float incomeAmount = ecoGUI.getIncomeAmount();
		Date incomeDate = ecoGUI.getIncomeDate();
		Category category = null;
		//Category category =ecoGUI.getCategory();
		Boolean income = true;
		Transfer transfer = new Transfer(description, category, income, incomeDate, incomeAmount);

		Boolean Transfer = transDAO.createTransfer(transfer);
	}
	
	//Pitääkö tätä olla
	
	/**
	public EconomyController(EconomyGUI ecoGUI) {
		this.ecoGUI = ecoGUI;


		//String description = ecoGUI.getReason();

		//String description = ecoGUI.getReason();


	}
	*/
	
	/** 
	 * Constructor 
	 * @param balanceOverviewGUI 
	 */ 

	public EconomyController(BalanceOverviewGUI balanceOverviewGUI) { 

		this.balanceOverviewGUI= balanceOverviewGUI; 
	
	} 

	/** 
	 * Constructor 
	 */ 
	public EconomyController() {
		// TODO Auto-generated constructor stub
	}

	/** 
	 * Constructor 
	 * @param  economyAddOutcomeGUI 
	 */ 
	public EconomyController(EconomyAddOutcomeGUI economyAddOutcomeGUI) {
		this.addExpenceGUI = economyAddOutcomeGUI;
	}
	

	
	public EconomyController(EconomyIncomeGUI economyIncomeGUI) {
		this.incomeGUI = economyIncomeGUI;
	}
	

	/** 
	 * Constructor 
	 * @param economyOutcomeGUI 
	 */ 
	public EconomyController(EconomyOutcomeGUI economyOutcomeGUI) {
		this.expenceGUI = economyOutcomeGUI;
	}

	public EconomyController(EconomyAddIncomeGUI economyAddIncomeGUI) {
		this.economyAddIncomeGUI = economyAddIncomeGUI;
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
	
	/** 
	 * Method that gets expense Categories from CategoryDAO and makes a list containing categories' names only 
	 * @return ObservableList<String> names - list of categories' names
	 */ 
	public ObservableList<String> expenseCategoriesList() {
		Category[] categories = categoryDAO.readExpenseCategories();
		ArrayList categoryNames = new ArrayList();
		for (Category category : categories){
			categoryNames.add(category.getDescription());
		}
		ObservableList<String> names =  FXCollections.observableArrayList(categoryNames);
		return names;
		
	}
	
	/** 
	 * Method that gets  income Categories from CategoryDAO and makes a list containing categories' names only 
	 * @return ObservableList<String> names - list of categories' names
	 */ 
	public ObservableList<String> incomeCategoriesList() {
		Category[] categories = categoryDAO.readIncomeCategories();
		ArrayList categoryNames = new ArrayList();
		for (Category category : categories){
			categoryNames.add(category.getDescription());
		}
		ObservableList<String> names =  FXCollections.observableArrayList(categoryNames);
		return names;
		
	}
	/** 
	 * Method that gets new expense's detail from addExpenceGUI and gives the expense to TransferDAO
	 */ 
	public void saveExpense() {
		Transfer expense = new Transfer();
		expense.setAmount(0-addExpenceGUI.getExpenseAmount());
		Category category = categoryDAO.readCategory(addExpenceGUI.getCategoryName());
		expense.setCategory(category);
		expense.setDescription(addExpenceGUI.getDescription());
		expense.setIncome(false);
		expense.setDate(addExpenceGUI.getExpenseDay());
		transDAO.createTransfer(expense);
		Balance balance = balanceDao.readBalance(1);
		float newAmount = balance.getBalance() + expense.getAmount();
		balance.setBalance(newAmount);
		balanceDao.updateBalance(balance);
	}
	
	/** 
	 * Method that gets the selected expense from expenceGUI, 
	 * tells TransferDAO to delete the expense from the database 
	 * and expenceGUI to delete it from the tableView.
	 */ 
	public void removeExpense() {
		transDAO.deleteTransfer(expenceGUI.transferToDelete().getTransfer_id());
		Balance balance = balanceDao.readBalance(1);
		float newAmount = balance.getBalance() - expenceGUI.transferToDelete().getAmount();
		balance.setBalance(newAmount);
		balanceDao.updateBalance(balance);
		expenceGUI.removeFromTable(expenceGUI.transferToDelete());
	}
	
	/** 
	 * Method that gets the selected expense from expenceGUI, 
	 * tells TransferDAO to edit the expense in the database 
	 * and expenceGUI to display it from the tableView.
	 */ 
	public void editExpense() {

	}
	
	/** 
	 * Method that gets new income's detail from addExpenceGUI and gives the income to TransferDAO
	 */ 
	public void saveIncome() {
		Transfer income = new Transfer();
		System.out.println("Amount "+economyAddIncomeGUI.getIncomeAmount());
		income.setAmount(economyAddIncomeGUI.getIncomeAmount());
		//Category category = categoryDAO.readCategory(addExpenceGUI.getCategoryName());
		income.setCategory(null);
		income.setDescription(economyAddIncomeGUI.getDescription());
		income.setIncome(true);
		income.setDate(economyAddIncomeGUI.getIncomeDay());
		transDAO.createTransfer(income);
		Balance balance = balanceDao.readBalance(1);
		float newAmount = balance.getBalance() + income.getAmount();
		balance.setBalance(newAmount);
		balanceDao.updateBalance(balance);
	}
	
	
	/** 
	 * Method that gets Expenses from TransferDAO and makes a list containing expenses' details 
	 * @return ObservableList<String> names - list of expenses
	 */ 
	public Transfer[] getExpenses() {
		return transDAO.readExpenses();
	}
	
	public Transfer[] getIncome() {
		return transDAO.readIncome();
	}
	
	/** 
	 * Method that gets the selected income from economyIncomeGUI, 
	 * tells TransferDAO to delete the income from the database 
	 * and economyIncomeGUI to delete it from the tableView.
	 */ 
	public void removeIncome() {
		transDAO.deleteTransfer(incomeGUI.transferToDelete().getTransfer_id());
		Balance balance = balanceDao.readBalance(1);
		float newAmount = balance.getBalance() - incomeGUI.transferToDelete().getAmount();
		balance.setBalance(newAmount);
		balanceDao.updateBalance(balance);
		incomeGUI.removeFromTable(incomeGUI.transferToDelete());
	}

}
