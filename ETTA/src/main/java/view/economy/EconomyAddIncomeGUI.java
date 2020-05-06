package view.economy;

import java.io.IOException;
import java.sql.Date;

import controller.EconomyController;
import controller.InputCheck;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListCell;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import model.Category;
import model.CategoryDAO;
import res.MyBundle;

/**
 * GUI class relating to the economy's income add section
 */
public class EconomyAddIncomeGUI implements ITransferAddGUI{
	
	/**
	 * MyBundle object for setting the right resource bundle to localize the application
	 */
	MyBundle myBundle = new MyBundle();
	/**
	 * The reference of InputCheck class used for checking user's input
	 */
	InputCheck inputCheck = new InputCheck();
	
	/**
	 * CategoryDAO used for accessing the database
	 */
	CategoryDAO categoryDAO = new CategoryDAO();
	
	/**
	 * The list view from where adding, editing and deleting can be started
	 */
	@FXML
	AnchorPane economyincomeaddanchorpane;

	/**
	 * The reference of TextField (income's description) will be injected by the FXML loader
	 */
	@FXML
	private TextField incomeDescription;
	
	/**
	 * The reference of TextField (income's amount) will be injected by the FXML loader
	 */
	@FXML
	private TextField incomeAmount;
	
	/**
	 * The reference of ComboBox (incomes's categories list) will be injected by the FXML loader
	 */
	@FXML
	private ComboBox<String> incomeCategoryList;
		
	/**
	 * The reference of DatePicker (incomes) will be injected by the FXML loader
	 */
	@FXML
	private DatePicker incomeDate;
	
	/**
	 * Reference to the used EconomyController 
	 */
	EconomyController controller = new EconomyController();
	
	/**
	 * Constructor with no parameters
	 */
	public EconomyAddIncomeGUI() {
		
	}
	
	/**
	 * Constructor
	 * @param EconomyController
	 */
	public EconomyAddIncomeGUI(EconomyController controller){
		this.controller = controller;
	}
	
	/** 
	 * Method that initializes the view - gets the categories from the EconomyController and displays them in the drop down list
	 */
	@FXML
	public void initialize() {
		incomeCategoryList.getItems().addAll(controller.incomeCategoriesList());
		incomeCategoryList.getItems().add("");
		incomeCategoryList.setCellFactory(lv -> {
            ListCell<String> cell = new ListCell<String>() {
                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setText(null);
                    } else {
                        if (item.isEmpty()) {
                            setText(myBundle.getBundle().getString("categoryAddNewNewCategory"));
                        } else {
                            setText(item);
                        }
                    }
                }
            };
            //adding new category
            cell.addEventFilter(MouseEvent.MOUSE_PRESSED, evt -> {
                if (cell.getItem().isEmpty() && ! cell.isEmpty()) {
                    TextInputDialog dialog = new TextInputDialog();
                    dialog.setTitle(myBundle.getBundle().getString("categoryNew"));
                    dialog.setHeaderText(myBundle.getBundle().getString("categoryAddNew"));
                    dialog.setContentText(myBundle.getBundle().getString("categoryAddNewName"));
                    dialog.showAndWait().ifPresent(text -> {
                        int index = incomeCategoryList.getItems().size()-1;
                        incomeCategoryList.getItems().add(index, text);
                      //an income category -> boolean income is set to true
                        categoryDAO.createCategory(new Category(text, true));
                        incomeCategoryList.getSelectionModel().select(index);
                    });
                    evt.consume();
                }
            });
            return cell ;
        });
	}
	
	/** 
	 * Method that gets input from incomeDescription TextField and returns it
	 * @return String description of the incomes
	 */
	@FXML
	public String getDescription(){
		return this.incomeDescription.getText();
		}
	
	/** 
	 * Method that gets the chosen option from incomeCategoryList ChoiceBox and returns it
	 * @return String category of the income
	 */
	@FXML
	public String getCategoryName(){
		return this.incomeCategoryList.getValue();
		}
	
	/** 
	 * Method that gets the chosen date from incomeDate DatePicker and returns it
	 * @return Date date of the income
	 * @return null if no date is chosen
	 */
	@FXML
	public Date getTransferDate() {
		try {
		return java.sql.Date.valueOf(incomeDate.getValue());
		}
		catch(NullPointerException e) {
			return null;
		}
	}
	
	/** 
	 * Method that gets the value from incomeAmount TextField and returns it in absolute value float format
	 * @return Float amount of the income
	 */
	@FXML
	public float getTransferAmount() {
		//if the user gave a negative amount it will be transfered into positive, because income can't be less then zero
		return Math.abs(Float.parseFloat(incomeAmount.getText()));
	}

	/**
	 * Method that tells controller to save the added income if the input can be transfered into float
	 * and displays the view of the list of incomes in the Economy section after adding new income 
	 * If amount is not number or that is null give method alert to user
	 */
	@FXML
	public void AddNewIncome() {
		//check if user has given a number as the amount
		if(inputCheck.isInputFloat(incomeAmount.getText())){
			//check if there is a name of the transfer given
			if(!inputCheck.isInputEmpty(incomeDescription.getText())) {
				//save new income transfer
				controller.saveTransfer(this);
				//show tableview with the incomes
				AnchorPane incomeView = null; 
				FXMLLoader loaderIncomeView  = new FXMLLoader(getClass().getResource("/view/economy/EconomyIncome.fxml"));
				loaderIncomeView .setResources(myBundle.getBundle());
				try {
					incomeView = loaderIncomeView.load();
				} catch (IOException e) {
					e.printStackTrace();
				}
				economyincomeaddanchorpane.getChildren().setAll(incomeView);
			}
			else {
				inputCheck.alertInputEmpty();
			}	
		}
		else {
			inputCheck.alertInputNotFloat();
		}	
	}
	
	/**
	 * Method that displays the view of the list of incomes in the Economy section if adding new income is canceled
	 */
	@FXML
	public void cancelAdding() {
		AnchorPane incomeView = null; 
		FXMLLoader loaderIncomeView  = new FXMLLoader(getClass().getResource("/view/economy/EconomyIncome.fxml")); 
		loaderIncomeView .setResources(myBundle.getBundle());
		try {
			incomeView = loaderIncomeView.load();

		} catch (IOException e) {
			e.printStackTrace();
		}
		economyincomeaddanchorpane.getChildren().setAll(incomeView);
		}
	


}
