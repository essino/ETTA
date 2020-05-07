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
 * GUI class relating to the Expense adding section of economy page
 */
public class EconomyAddOutcomeGUI implements ITransferAddGUI{
	
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
	AnchorPane addOutcomePane;

	/**
	 * The reference of TextField (expense's description) will be injected by the FXML loader
	 */
	@FXML
	private TextField outcomeDescription;
	
	/**
	 * The reference of TextField (expense's amount) will be injected by the FXML loader
	 */
	@FXML
	private TextField outcomeAmount;
	
	/**
	 * The reference of ChoiceBox (expense's categories list) will be injected by the FXML loader
	 */
	@FXML
	private ComboBox<String> outcomeCategoryList;
	
	/**
	 * The reference of DatePicker (expenses) will be injected by the FXML loader
	 */
	@FXML
	private DatePicker outcomeDate;
	
	/**
	 * Reference to the used EconomyController
	 */
	EconomyController controller = new EconomyController();
	
	/**
	 * Constructor with no parameters
	 */
	public EconomyAddOutcomeGUI() {
		
	}
	
	/** 
	 * Method that initializes the view - gets the expense categories from the EconomyController and displays them in the drop down list
	 */
	@FXML
	public void initialize() {
		outcomeCategoryList.getItems().addAll(controller.expenseCategoriesList());
		outcomeCategoryList.getItems().add("");
		outcomeCategoryList.setCellFactory(lv -> {
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
          //adding a new category
            cell.addEventFilter(MouseEvent.MOUSE_PRESSED, evt -> {
                if (cell.getItem().isEmpty() && ! cell.isEmpty()) {
                    TextInputDialog dialog = new TextInputDialog();
                    dialog.setTitle(myBundle.getBundle().getString("categoryNew"));
                    dialog.setHeaderText(myBundle.getBundle().getString("categoryAddNew"));
                    dialog.setContentText(myBundle.getBundle().getString("categoryAddNewName"));
                    dialog.showAndWait().ifPresent(text -> {
                        int index = outcomeCategoryList.getItems().size()-1;
                        outcomeCategoryList.getItems().add(index, text);
                        //an expense category -> boolean income is set to false
                        categoryDAO.createCategory(new Category(text, false));
                        outcomeCategoryList.getSelectionModel().select(index);
                    });
                    evt.consume();
                }
            });

            return cell ;
        });
	}
	
	/** 
	 * Method that gets input from outcomeDescription TextField and returns it
	 * @return String description of the expense
	 */
	@FXML
	public String getDescription(){
		return this.outcomeDescription.getText();
		}
	
	/** 
	 * Method that gets the chosen option from outcomeCategoryList ChoiceBox and returns it
	 * @return String category of the expense
	 */
	@FXML
	public String getCategoryName(){
		return this.outcomeCategoryList.getValue();
		}
	
	/** 
	 * Method that gets the chosen date from outcomeDate DatePicker and returns it
	 * @return Date date of the expense
	 * @return null if date is not selected 
	 */
	@FXML
	public Date getTransferDate() {
		try {
		return java.sql.Date.valueOf(outcomeDate.getValue());
		}
		catch(NullPointerException e) {
			return null;
		}
	}
	
	/** 
	 * Method that gets the value from outcomeAmount TextField and returns it in absolute value float format  
	 * @return 	Float amount of the expense
	 */
	@FXML
	public float getTransferAmount() {
		//making sure the expense will be a negative amount even if the user gave a positive number
		return 0-Math.abs(Float.parseFloat(outcomeAmount.getText()));
	}
	
	/**
	 * Method that tells controller to save the added expense if the input can be transfered into float
	 * and displays the view of the list of expenses in the Economy section after adding new expense 
	 */
	@FXML
	public void saveExpense() {
		//check if user has given a number as the amount
		if(inputCheck.isInputFloat(outcomeAmount.getText())) {
			//check if there is a name of the expences given
			if(!inputCheck.isInputEmpty(outcomeDescription.getText())) {
				//save expense
			controller.saveTransfer(this);
			//show tableview with the expences
			AnchorPane outcomeView = null; 
			FXMLLoader loaderOutcomeView  = new FXMLLoader(getClass().getResource("/view/economy/EconomyOutcome.fxml")); 
			loaderOutcomeView .setResources(myBundle.getBundle());
			try {
				outcomeView = loaderOutcomeView.load();
				} catch (IOException e) {
				e.printStackTrace();
				}
			addOutcomePane.getChildren().setAll(outcomeView);
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
	 * Method that displays the view of the list of expenses in the Economy section if adding new expense is canceled
	 */
	@FXML
	public void cancelAdding() {
		AnchorPane outcomeView = null; 
		FXMLLoader loaderOutcomeView  = new FXMLLoader(getClass().getResource("/view/economy/EconomyOutcome.fxml"));
		loaderOutcomeView .setResources(myBundle.getBundle());
		try {
			outcomeView = loaderOutcomeView.load();
		} catch (IOException e) {
			e.printStackTrace();
		}
			addOutcomePane.getChildren().setAll(outcomeView);
		}
	
	public EconomyAddOutcomeGUI(EconomyController controller){
		this.controller = controller;
	}
	
}
