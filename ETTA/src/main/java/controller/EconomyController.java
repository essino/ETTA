package controller;


import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.util.Callback;

import java.sql.Date;

import model.Balance;
import view.economy.BalanceOverviewGUI;
import view.economy.EconomyAddIncomeGUI;
import view.economy.EconomyAddOutcomeGUI;
import view.economy.EconomyAddSavingGUI;
import view.economy.EconomyGUI;
import view.economy.EconomyIncomeGUI;
import view.economy.EconomyOutcomeGUI;
import view.economy.EconomySavingsGUI;
import model.BalanceDAO;
import model.Category;
import model.CategoryDAO;
import model.Item;
import model.Saving;
import model.SavingDAO;
import model.TransferDAO;
import model.Transfer;

/** 
 * Controller class for the Economy part.  
 * 
 */
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
	 * SavingDAO used for accessing the database
	 */
	private SavingDAO savingDAO = new SavingDAO();
	
	/**
	 * Reference to the EconomyAddIncomeGUI
	 */
	private EconomyAddIncomeGUI economyAddIncomeGUI;
	
	/**
	 * Reference to the EconomyIncomeGUI
	 */
	private EconomyIncomeGUI incomeGUI;
	
	/**
	 * Reference to the EconomyAddSavingGUI
	 */
	private EconomyAddSavingGUI economyAddSavingGUI;
	
	/**
	 * Reference to the EconomySavingGUI
	 */
	private EconomySavingsGUI economySavingGUI;
	
	
	
	public Item selectedIncomeDesc = new Item();
	
	public Item selectedOutcomeDesc = new Item();
	
	/*
	public Item getIncomeDesc() {
		System.out.println("Selected income item " + incomeGUI.getSelectedItem().getDescription());
		return transDAO.readTransfers(incomeGUI.getSelectedItem().getDescription());
	}
	*/
	public void updateOutcomeDesc(Transfer editedOutcomeDesc) {
		transDAO.updateTransfer(editedOutcomeDesc);
	}
	
	
	public void updateIncomeDesc(Transfer editedIncomeDesc) {
		transDAO.updateTransfer(editedIncomeDesc);
	}
	
	public void updateIncomeAmount(Transfer editedIncomeAmount) {
		float oldAmount = (transDAO.readTransfer(editedIncomeAmount.getTransfer_id())).getAmount();
		float newAmount = editedIncomeAmount.getAmount();
		float diff = newAmount-oldAmount;
		transDAO.updateTransfer(editedIncomeAmount);
		Balance balance = balanceDao.readBalance(1);
		balance.setBalance(balance.getBalance() + diff);
		balanceDao.updateBalance(balance);
	}
	
	public void updateOutcomeAmount(Transfer editedOutcomeAmount) {
		float oldAmount = (transDAO.readTransfer(editedOutcomeAmount.getTransfer_id())).getAmount();
		float newAmount = editedOutcomeAmount.getAmount();
		float diff = newAmount-oldAmount;
		transDAO.updateTransfer(editedOutcomeAmount);
		Balance balance = balanceDao.readBalance(1);
		balance.setBalance(balance.getBalance() - diff);
		balanceDao.updateBalance(balance);
	}
	
	public void updateIncomeDate(Transfer editedIncomeDate) {
		//Date oldIncomeDate = (transDAO.readTransfer(editedIncomeDate.getTransfer_id())).getDate();
		//Date newIncomeDate = editedIncomeDate.getDate();
		transDAO.updateTransfer(editedIncomeDate);
	}
	
	public void saveTransfer() {
		String description = ecoGUI.getDescription();
		float incomeAmount = ecoGUI.getIncomeAmount();
		Date incomeDate = ecoGUI.getIncomeDate();
		Category category = null;
		Boolean income = true;
		Transfer transfer = new Transfer(description, category, income, incomeDate, incomeAmount);
		Boolean Transfer = transDAO.createTransfer(transfer);
	}
	
	/** 
	 * Constructor 
	 * @param balanceOverviewGUI 
	 */ 
	public EconomyController(BalanceOverviewGUI balanceOverviewGUI) { 
		this.balanceOverviewGUI= balanceOverviewGUI; 
	} 

	/** 
	 * Constructor 
	 * @param economyAddSavingGUI 
	 */ 
	public EconomyController(EconomyAddSavingGUI economyAddSavingGUI) { 
		this.economyAddSavingGUI= economyAddSavingGUI; 
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

	/** 
	 * Constructor 
	 * @param economyIncomeGUI 
	 */ 
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

	/** 
	 * Constructor 
	 * @param economyAddIncomeGUI 
	 */ 
	public EconomyController(EconomyAddIncomeGUI economyAddIncomeGUI) {
		this.economyAddIncomeGUI = economyAddIncomeGUI;
	}

	/** 
	 * Constructor 
	 * @param economySavingGUI 
	 */ 
	public EconomyController(EconomySavingsGUI economySavingsGUI) {
		this.economySavingGUI = economySavingsGUI;
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
	 * Method that gets income Categories from CategoryDAO and makes a list containing categories' names only 
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
	 * Method that gets new income's detail from addIncomeGUI and gives the incomes to TransferDAO
	 */ 
	public void saveIncome() {
		Transfer income = new Transfer();
		income.setAmount(economyAddIncomeGUI.getIncomeAmount());
		Category category = categoryDAO.readCategory(economyAddIncomeGUI.getCategoryName());
		income.setCategory(category);
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
	 */ 
	public void editExpense() {

	}
	

	
	
	/** 
	 * Method that gets Expenses from TransferDAO and makes a list containing expenses' details 
	 * @return Transfer [] - list of expenses
	 */ 
	public Transfer[] getExpenses() {
		return transDAO.readExpenses();
	}
	
	
	/** 
	 * Method that gets Incomes from TransferDAO and makes a list containing incomes details 
	 * @return Transfer[] - list of incomes
	 */ 
	public Transfer[] getIncomes() {
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

	/** 
	 * Method that gets new saving's detail from economyAddSavingGUI and gives the saving to SavingDAO
	 */
	public void saveNewSaving() {
		Saving saving = new Saving();
		saving.setGoal_amount(economyAddSavingGUI.getSavingAmount());
		saving.setDescription(economyAddSavingGUI.getDescription());
		saving.setGoalDate(economyAddSavingGUI.getSavingDay());
		saving.setProgress(0);
		savingDAO.createSaving(saving);
	}

	/** 
	 * Method that gets Savings from SavingDAO and makes a list containing savings' details 
	 * @return Savings[]  list of savings
	 */ 
	public Saving[] getSavingss() {
		return savingDAO.readSavings();
	}

	/** 
	 * Method that gets the selected saving from SavingGUI, 
	 * tells SavingDAO to delete the saving goal from the database 
	 * and SavingGUI to delete it from the tableView.
	 */ 
	public void removeSaving() {
		savingDAO.deleteSaving(economySavingGUI.savingToDelete().getSaving_id());
		Balance balance = balanceDao.readBalance(1);
		float newAmount = balance.getBalance() + economySavingGUI.savingToDelete().getAmount();
		balance.setBalance(newAmount);
		balanceDao.updateBalance(balance);
		economySavingGUI.removeFromTable(economySavingGUI.savingToDelete());
	}


	public void updateSaving(Saving editedSavingDesc) {
		savingDAO.updateSaving(editedSavingDesc);
	}

}
