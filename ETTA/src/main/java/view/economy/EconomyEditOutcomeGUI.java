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
import model.Balance;
import model.Category;
import model.CategoryDAO;
import model.Transfer;

public class EconomyEditOutcomeGUI {
	
	
		
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
		AnchorPane economyoutcomeaddanchorpane;

		/**
		 * The reference of TextField (income's description) will be injected by the FXML loader
		 */
		@FXML
		private TextField outcomeDescription;
		
		/**
		 * The reference of TextField (income's amount) will be injected by the FXML loader
		 */
		@FXML
		private TextField outcomeAmount;
		
		/**
		 * The reference of ChoiceBox (incomes's categories list) will be injected by the FXML loader
		 */
		@FXML
		private ComboBox<String> outcomeCategoryList;
		
		
		public EconomyEditOutcomeGUI() {
			
		}
		
		/**
		 * The reference of DatePicker (incomes) will be injected by the FXML loader
		 */

		@FXML
		private DatePicker outcomeDate;
		
		EconomyController controller; // = new EconomyController(this);
		

		
		/** 
		 * Method that initializes the view - gets the categories from the EconomyController and displays them in the drop down list
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
	                    dialog.setContentText("Enter name");
	                    dialog.showAndWait().ifPresent(text -> {
	                        int index = outcomeCategoryList.getItems().size()-1;
	                        outcomeCategoryList.getItems().add(index, text);
	                        categoryDAO.createCategory(new Category(text, true));
	                        outcomeCategoryList.getSelectionModel().select(index);
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
			return this.outcomeDescription.getText();
			}
		
		/** 
		 * Method that gets the chosen option from incomeCategoryList ChoiceBox and returns it
		 * @return String category of the incomes
		 */
		@FXML
		public String getCategoryName(){
			return this.outcomeCategoryList.getValue();
			}
		
		/** 
		 * Method that gets the chosen date from incomeDate DatePicker and returns it
		 * @return Date date of the income
		
		 */
		@FXML
		public Date getOutcomeDay() {
			try {
			return java.sql.Date.valueOf(outcomeDate.getValue());
			}
			catch(NullPointerException e) {
				return null;
			}
		}
		
		/** 
		 * Method that gets the value from incomeAmount TextField and returns it in float format
		 * @return Float amount of the income
		 */
		@FXML
		public float getOutcomeAmount() {
			return Float.parseFloat(outcomeAmount.getText());
		}

		/**
		 * Method that tells controller to save the added income if the input can be transfered into float
		 * and displays the view of the list of incomes in the Economy section after adding new income 
		 */
		@FXML
		public void AddNewOutcome() {
			if(inputCheck.isInputFloat(outcomeAmount.getText())){
				
				if(!inputCheck.isInputEmpty(outcomeDescription.getText())) {
				controller.saveExpense();
				AnchorPane outcomeView = null; 
				FXMLLoader loaderOutcomeView  = new FXMLLoader(getClass().getResource("/view/economy/EconomyOutcome.fxml")); 
				try {
					outcomeView = loaderOutcomeView.load();
					} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					}
				economyoutcomeaddanchorpane.getChildren().setAll(outcomeView);
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
			AnchorPane outcomeView = null; 

			FXMLLoader loaderOutcomeView  = new FXMLLoader(getClass().getResource("/view/economy/EconomyOutcome.fxml")); 

			try {
				outcomeView = loaderOutcomeView.load();

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			economyoutcomeaddanchorpane.getChildren().setAll(outcomeView);
			}
		
		

	}


