package view.economy;

import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;

import controller.EconomyController;
import controller.InputCheck;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.AnchorPane;
import javafx.util.Callback;
import javafx.util.converter.FloatStringConverter;
import model.Category;
import model.Transfer;
import res.MyBundle;
import javafx.event.EventHandler;
import javafx.scene.control.TableColumn.CellEditEvent;

public class EconomyOutcomeGUI {
	
	EconomyController controller = new EconomyController(this);
	MyBundle myBundle = new MyBundle();
	
	/**
	 * The list view from where adding, editing and deleting can be started in expencies
	 */
	@FXML	
	AnchorPane economyoutcomeaddanchorpane;
	/**
	 * The reference of TableView (expenses) will be injected by the FXML loader
	 */
	@FXML
    private TableView<Transfer> expenseTable;
	
	/**
	 * The reference of TableColumn (expense's description) will be injected by the FXML loader
	 */
    @FXML
    private TableColumn<Transfer, String> expenseDescription;
    
    /**
	 * The reference of TableColumn (expense's date) will be injected by the FXML loader
	 */
    @FXML
    private TableColumn<Transfer, Date> expenseDate;
    
    /**
	 * The reference of TableColumn (expense's amount) will be injected by the FXML loader
	 */
    @FXML
    private TableColumn<Transfer, Float> expenseAmount;
    
    /**
  	 * The reference of TableColumn (expense's category) will be injected by the FXML loader
  	 */
      @FXML
      private TableColumn<Category, String> expenseCategory;
      
      
      @FXML
      private DatePicker expenceStartDate;

      @FXML
      private DatePicker expenceEndDate;
      
      /**
    	 * The reference of InputCheck class used for checking user's input
    	 */
    	InputCheck inputCheck = new InputCheck();
      
    	Callback<TableColumn<Transfer, Date>, TableCell<Transfer, Date>> dateCellFactory = (TableColumn<Transfer, Date> param) -> new DateEditingCell(); 
      
      public EconomyOutcomeGUI() {
  		
  	}
	
	/**
	 * Method showing the view of the Add Expense in the Expenses items section
	 * @param event ActionEvent that is handled
	 */
	@FXML
	public void showAddOutcome(ActionEvent event) {
		AnchorPane showAddOutcomeView = null;
		FXMLLoader loaderAddOutcomeView = new FXMLLoader(getClass().getResource("/view/economy/EconomyAddOutcome.fxml"));
		loaderAddOutcomeView .setResources(myBundle.getBundle());
		try {
			showAddOutcomeView = loaderAddOutcomeView.load();
			} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			}
		//shows the loaded fxml file
		economyoutcomeaddanchorpane.getChildren().setAll(showAddOutcomeView);
	
	}
	
	@FXML
	public void showEditOutcome(ActionEvent event) {
		AnchorPane showAddOutcomeView = null;
		FXMLLoader loaderAddOutcomeView = new FXMLLoader(getClass().getResource("/view/economy/EconomyEditOutcome.fxml"));
		loaderAddOutcomeView .setResources(myBundle.getBundle());
		try {
			showAddOutcomeView = loaderAddOutcomeView.load();
			} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			}
		//shows the loaded fxml file
		economyoutcomeaddanchorpane.getChildren().setAll(showAddOutcomeView);
	
	}
	/**
	 * Method searching information of the seleted days on Expenses items section
	 * @param event ActionEvent that is handled
	 */
	@FXML
	public void searchExpence(ActionEvent event) {
		LocalDate startDate = expenceStartDate.getValue();
		LocalDate endDate = expenceEndDate.getValue();
		controller.getSeletedExpences(java.sql.Date.valueOf(startDate), java.sql.Date.valueOf(endDate));
	}
	
	/** 
	 * Method that initializes the view and gets the expenses  from the controller to display them on the page
	 *  Fetches the expenses items from the database, displays them in the table view, and enables in-table editing
	 */
	@FXML 
	public void initialize() { 
		expenseDescription.setCellValueFactory(
                new PropertyValueFactory<Transfer, String>("description"));
		expenseDate.setCellValueFactory(
                new PropertyValueFactory<Transfer, Date>("date"));
		expenseAmount.setCellValueFactory(
                new PropertyValueFactory<Transfer, Float>("amount"));
		expenseCategory.setCellValueFactory(
                new PropertyValueFactory<Category, String>("category"));
		ObservableList<Transfer> expenses =  FXCollections.observableArrayList(controller.getExpenses());
		expenseTable.setItems(expenses);
		
		expenseTable.setEditable(true);
		expenseDescription.setCellValueFactory(new PropertyValueFactory<Transfer, String>("description")); 
		
		expenseAmount.setCellValueFactory(new PropertyValueFactory<Transfer, Float>("amount"));
		
		expenseDate.setCellValueFactory(new PropertyValueFactory<Transfer, Date>("date"));
		
		expenseDescription.setCellFactory(TextFieldTableCell.<Transfer>forTableColumn());
		expenseDescription.setOnEditCommit(
			new EventHandler<CellEditEvent<Transfer, String>>(){
				@Override
				public void handle(CellEditEvent<Transfer, String> t) {
					Transfer editedOutcomeDesc = ((Transfer) t.getTableView().getItems().get(t.getTablePosition().getRow()));
					editedOutcomeDesc.setDescription(t.getNewValue());
					controller.updateOutcomeDesc(editedOutcomeDesc);
					expenseTable.refresh();
					}});
		
		expenseAmount.setCellFactory(TextFieldTableCell.forTableColumn(new FloatStringConverter()));
		expenseAmount.setOnEditCommit(
			new EventHandler<CellEditEvent<Transfer, Float>>(){
				@Override
				public void handle(CellEditEvent<Transfer, Float> t) {			
					Transfer editedOutcomeAmount = ((Transfer) t.getTableView().getItems().get(t.getTablePosition().getRow()));
					editedOutcomeAmount.setAmount(t.getNewValue());
					controller.updateOutcomeAmount(editedOutcomeAmount);
					expenseTable.refresh();
					}});
		
		expenseDate.setCellFactory(dateCellFactory);
		expenseDate.setOnEditCommit(
			new EventHandler<CellEditEvent<Transfer, Date>>(){
				@Override
				public void handle(CellEditEvent<Transfer, Date> t) {			
					Transfer editedOutcomeDate = ((Transfer) t.getTableView().getItems().get(t.getTablePosition().getRow()));
					editedOutcomeDate.setDate(t.getNewValue());
					controller.updateOutcomeDate(editedOutcomeDate);
					expenseTable.refresh();
					}});
		
	}
	
	/** 
	 * Method that tells controller to delete an expense 
	 */
	@FXML
	public void deleteOutcome() {
		if (inputCheck.confirmDeleting()) {
			controller.removeExpense();
			initialize();
		}
	}
	
	/** 
	 * Method that returns the selected expense
	 * @return Transfer that is selected
	 */
	public Transfer transferToDelete() {
		return expenseTable.getSelectionModel().getSelectedItem();
	}
	
	/** 
	 * Method that removes an expense from the tableView
	 * @param Transfer expense to be removed
	 */
	public void removeFromTable(Transfer transfer) {
		expenseTable.getItems().remove(transfer);
	}
	
	/** 
	 * Method that tells controller to edit an expense 
	 */
	@FXML
	public void editOutcome() {
		//expenseTable.setEditable(true);
		//controller.editExpense();
	}
	
	@FXML
	public Transfer getSelectedItem() {
		return expenseTable.getSelectionModel().getSelectedItem();
	}
	
	/** 
	 * Method set to data controller
	 */
	public void setData(Transfer[] readSeletedTransfers) {
		expenseTable.setItems(FXCollections.observableArrayList(readSeletedTransfers));

	}
}
