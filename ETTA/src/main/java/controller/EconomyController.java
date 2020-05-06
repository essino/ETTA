package controller;

import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Date;

import model.Balance;
import view.economy.BalanceOverviewGUI;
import view.economy.EconomyAddIncomeGUI;
import view.economy.EconomyAddSavingGUI;
import view.economy.EconomySavingsGUI;
import view.economy.ITransferAddGUI;
import view.economy.ITransferGUI;
import view.economy.AbstractEconomyGUI;
import model.BalanceDAO;
import model.Category;
import model.CategoryDAO;
import model.Saving;
import model.SavingDAO;
import model.TransferDAO;
import model.Transfer;

/** 
 * Controller class for the Economy part. 
 * Controls the communication between GUIs of the Economy part and DAOs connected to them.
 * @author Lena, Anni  
 */
public class EconomyController {
	/**
	 * Reference to the BalanceOverviewGUI
	 */
	private BalanceOverviewGUI balanceOverviewGUI;

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
	 * Reference to the EconomyAddSavingGUI
	 */
	private EconomyAddSavingGUI economyAddSavingGUI;
	
	/**
	 * Reference to the EconomySavingGUI
	 */
	private EconomySavingsGUI economySavingGUI;
	
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
	 * Constructor without parameters
	 */ 
	public EconomyController() {
	}

	/** 
	 * Constructor 
	 * @param economySavingGUI 
	 */ 
	public EconomyController(EconomySavingsGUI economySavingsGUI) {
		this.economySavingGUI = economySavingsGUI;
	}

	//constructor used for tests
	public EconomyController(CategoryDAO categoryDAO2, TransferDAO transferDAO) {
		this.categoryDAO = categoryDAO2;
		this.transDAO = transferDAO; 
	}

	//constructor used for tests
	public EconomyController(BalanceDAO balanceDAO2) {
		this.balanceDao = balanceDAO2;
	}


	//constructor used for tests
	public EconomyController(SavingDAO savingDAO2) {
		this.savingDAO = savingDAO2;
	}
	
	/**
	 * 
	 * Method for updating a transfer's amount
	 */
	public void updateTransferAmount(Transfer editedTransfer) {
		float oldAmount = (transDAO.readTransfer(editedTransfer.getTransfer_id())).getAmount();
		float newAmount = editedTransfer.getAmount();
		float diff = newAmount-oldAmount;
		transDAO.updateTransfer(editedTransfer);
		updateBalanceAmount(diff);
	}
	
	/**
	 * 
	 * Method for updating a transfer
	 */
	public boolean updateTransfer(Transfer editedTransfer) {
		return transDAO.updateTransfer(editedTransfer);
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
	 * Method that gets new transfer's detail from  an addTransferGUI and gives the transfer to TransferDAO
	 * @param ITransferAddGUI class implementation
	 */ 
	public void saveTransfer(ITransferAddGUI gui) {
		Transfer transfer = new Transfer();
		if (gui.getClass()==EconomyAddIncomeGUI.class) {
			transfer.setAmount(gui.getTransferAmount());
			transfer.setIncome(true);
		}
		else {
			transfer.setAmount(0-Math.abs(gui.getTransferAmount()));
			transfer.setIncome(false);
		}
		Category category = categoryDAO.readCategory(gui.getCategoryName());
		transfer.setCategory(category);
		transfer.setDescription(gui.getDescription());
		transfer.setDate(gui.getTransferDate());
		transDAO.createTransfer(transfer);
		updateBalanceAmount(transfer.getAmount());
	}
	
	/** 
	 * Method that gets selected transfers from database
	 * @param gui AbstractEconomyGUI implementation
	 * @param start Date the start date of the time span from which the transfers are chosen
	 * @param end Date the end date of the time span from which the transfers are chosen
	 */ 
	public void getSelectedTransfers(AbstractEconomyGUI gui, Date start, Date end) {
		gui.setData(transDAO.readSeletedTransfers(start, end));
	}

	/** 
	 * Method that gets the selected transfer from ITransferAddGUI, 
	 * tells TransferDAO to delete the transfer from the database 
	 * and ITransferAddGUI to delete it from the tableView.
	 * @param ITransferAddGUI class implementation
	 */ 
	public void removeTransfer(ITransferGUI gui) {
		transDAO.deleteTransfer(gui.transferToDelete().getTransfer_id());
		updateBalanceAmount(0-gui.transferToDelete().getAmount());
		gui.removeFromTable(gui.transferToDelete());
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
		updateBalanceAmount(economySavingGUI.savingToDelete().getAmount());
		economySavingGUI.removeFromTable(economySavingGUI.savingToDelete());
	}


	/** 
	 * Boolean method that updates the saving in the database 
	 * @param Saving to update the database 
	 * @return true if the saving was updates successfully
	 * @return false if the updating of the saving didn't succeed
	 */
	public boolean updateSaving(Saving editedSavingDesc) {
		return savingDAO.updateSaving(editedSavingDesc);
	}
	
	/** 
	 * Method that gets balance amount from BalanceDAO and gives it forward to BalanceOverviewGUI to display it on the page 
	 */ 
	public float getBalanceAmount() { 
		return balanceDao.readBalance(1).getBalance(); 
	}

	/** 
	 * Boolean method that updates the balance amount 
	 * @param float amount that will be taken from or added to the balance
	 * @return true if the balance was updates successfully
	 * @return false if the updating of the balance didn't succeed
	 */
	public boolean updateBalanceAmount(float amount) {
		if(enoughBalance(amount)) {
			Balance balance = balanceDao.readBalance(1);
			float newAmount = balance.getBalance() + amount;
			balance.setBalance(newAmount);
			balanceDao.updateBalance(balance);
			return true;
		}
		else {
			return false;
		}
	}
	
	/** 
	 * Boolean method that there is enough money on the balance to create or update a Transfer or a Saving 
	 * @param float amount that will be taken from the balance
	 * @return true if the balance is enough 
	 * @return false if there is not enough money on the balance for the change
	 */
	public boolean enoughBalance(float amount) {
		Balance balance = balanceDao.readBalance(1);
		//adding the amount because the amount can be both positive and negative
		float newAmount = balance.getBalance() + amount;
		if(newAmount>=0) {
			return true;
		}
		return false;
	}

	/** 
	 * Method that gets Savings from SavingDAO and makes a list containing savings' descriptions only 
	 * @return ObservableList<String> names - list of savings' descriptions
	 */ 
	public ObservableList<String> getSavingsList() {
		Saving[] savings = savingDAO.readSavings();
		ArrayList<String> savingNames = new ArrayList<String>();
		for (Saving s : savings){
			savingNames.add(s.getDescription());
		}
		ObservableList<String> names =  FXCollections.observableArrayList(savingNames);
		return names;
	}

	/** 
	 * Method that gets a saving description as the parameter 
	 * and gives it forward to SavingsDAO to search for a saving with this description in the database
	 * @return Saving the found Saving from the database
	 */ 
	public Saving getSaving(String description) {
		Saving saving = savingDAO.getSaving(description);
		return saving;
	}

	/** 
	 * Boolean method that makes an income and an expence from a saving. The saving is also removed from the database. 
	 * This method gets called if a saving goal is achieved and user wants to mark that he's spent this money for this goal
	 * @param Saving the achieved Saving that should be deleted from the database
	 */
	public void moveSavingToExpense(Saving achievedSaving) {
		savingDAO.deleteSaving(achievedSaving.getSaving_id());

		fromSavingToIncome(achievedSaving);

		fromSavingToExpense(achievedSaving); 

		economySavingGUI.removeFromTable(achievedSaving);
	} 
	
	/** 
	 * Boolean method that makes an income Transfer using a saving's data. 
	 * This method gets called if a saving goal is achieved and user wants to mark that he has spent this money for this goal
	 * If there is no needed Category yet (Category with name "savings"), CategoryDAO will create a Category with this name.
	 * TransferDAO creates a new income Transfer.
	 * @param Saving the achieved Saving which data will be used for creating the expense transfer
	 * @return true if the transfer creating succeeded
	 * @return false if the creation of transfer didn't succeed
	 */
	public boolean fromSavingToIncome(Saving achievedSaving) {
		Category fromSaved = categoryDAO.readCategory("savings");
		//if category doesn't exist yet -> new category
		if(fromSaved==null) {
			fromSaved = new Category();
			fromSaved.setDescription("savings");
			//an income category -> true
			fromSaved.setCategory_type(true);
			categoryDAO.createCategory(fromSaved);
		}
		
		Transfer incomeFromSaved = new Transfer();
		incomeFromSaved.setDescription(achievedSaving.getDescription());
		incomeFromSaved.setAmount(achievedSaving.getAmount());
		incomeFromSaved.setCategory(fromSaved);
		incomeFromSaved.setIncome(true);
		incomeFromSaved.setDate(java.sql.Date.valueOf(java.time.LocalDate.now()));
		
		return transDAO.createTransfer(incomeFromSaved);
	}
	
	/** 
	 * Boolean method that makes an expense Transfer using a saving's data. 
	 * This method gets called if a saving goal is achieved and user wants to mark that he has spent this money for this goal
	 * If there is no needed Category yet (Category with name "achieved saving goal"), CategoryDAO will create a Category with this name.
	 * TransferDAO creates a new expense Transfer.
	 * @param Saving the achieved Saving which data will be used for creating the expense transfer
	 * @return true if the transfer creating succeeded
	 * @return false if the creation of transfer didn't succeed
	 */
	public boolean fromSavingToExpense(Saving achievedSaving) {
		Category paidFromSaved = categoryDAO.readCategory("achieved saving goal");
		//if category doesn't exist yet -> new category
		if(paidFromSaved==null) {
			paidFromSaved = new Category();
			paidFromSaved.setDescription("achieved saving goal");
			//an expense category -> false
			paidFromSaved.setCategory_type(false);
			categoryDAO.createCategory(paidFromSaved);
		}
		
		Transfer expenceFromSaved = new Transfer();
		expenceFromSaved.setDescription(achievedSaving.getDescription());
		expenceFromSaved.setAmount(0-achievedSaving.getAmount());
		expenceFromSaved.setCategory(paidFromSaved);
		expenceFromSaved.setIncome(false);
		expenceFromSaved.setDate(java.sql.Date.valueOf(java.time.LocalDate.now()));
		
		return transDAO.createTransfer(expenceFromSaved);
	}
}
