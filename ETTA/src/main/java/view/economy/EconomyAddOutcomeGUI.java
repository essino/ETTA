package view.economy;

import java.io.IOException;
import java.sql.Date;
import java.util.Observable;
import java.util.Observer;
import java.util.ResourceBundle;

import controller.EconomyController;
import controller.InputCheck;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ChoiceBox;
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
public class EconomyAddOutcomeGUI implements Observer{
	MyBundle myBundleInst = MyBundle.getInstance();
	MyBundle myBundle;
	ResourceBundle bundle;
	
	/**
	 * The reference of InputCheck class used for checking user's input
	 */
	InputCheck inputCheck = InputCheck.getInstance();
	
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
	
	EconomyController controller = new EconomyController(this);
	
	public EconomyAddOutcomeGUI() {
		this.myBundle = myBundleInst;
		this.bundle=myBundle.getBundle();
		this.myBundle.addObserver(this);
	}
	
	@Override
	public void update(Observable o, Object arg) {
		System.out.println("observer informed");
		if(o instanceof MyBundle) {
			this.bundle=myBundle.getBundle();
		}
		
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
                            setText("Add category...");
                        } else {
                            setText(item);
                        }
                    }
                }
            };

            cell.addEventFilter(MouseEvent.MOUSE_PRESSED, evt -> {
                if (cell.getItem().isEmpty() && ! cell.isEmpty()) {
                    TextInputDialog dialog = new TextInputDialog();
                    dialog.setTitle("New category");
                    dialog.setHeaderText("Add new category");
                    dialog.setContentText("Enter category name");
                    dialog.showAndWait().ifPresent(text -> {
                        int index = outcomeCategoryList.getItems().size()-1;
                        outcomeCategoryList.getItems().add(index, text);
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
	 * @return null if date is not selected ---??? onko edes mahdollista?
	 */
	@FXML
	public Date getExpenseDay() {
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
	public float getExpenseAmount() {
		return Math.abs(Float.parseFloat(outcomeAmount.getText()));
	}
	
	/**
	 * Method that tells controller to save the added expense if the input can be transfered into float
	 * and displays the view of the list of expenses in the Economy section after adding new expense 
	 */
	@FXML
	public void saveExpense() {
		if(inputCheck.isInputFloat(outcomeAmount.getText())) {
			
		
			if(!inputCheck.isInputEmpty(outcomeDescription.getText())) {
			controller.saveExpense();
			AnchorPane outcomeView = null; 
			FXMLLoader loaderOutcomeView  = new FXMLLoader(getClass().getResource("/view/economy/EconomyOutcome.fxml")); 
			loaderOutcomeView .setResources(bundle);
			try {
				outcomeView = loaderOutcomeView.load();
				} catch (IOException e) {
				// TODO Auto-generated catch block
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
		loaderOutcomeView .setResources(bundle);
		try {
			outcomeView = loaderOutcomeView.load();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			addOutcomePane.getChildren().setAll(outcomeView);
		}
	
	public EconomyAddOutcomeGUI(EconomyController controller){
		this.controller = controller;
	}
	
}
