package view;



import controller.EconomyController;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

/**
 * GUI class relating to the Balance main page section
 */
public class BalanceOverviewGUI {
	EconomyController controller; 
	@FXML
	private Pane setBalancePane;
	@FXML
	private TextField newBalance;
	@FXML 
	// The reference of label will be injected by the FXML loader 
	private Label amountBalance; 
	
	public BalanceOverviewGUI() { 
		controller = new EconomyController(this);
	} 
	/** 
	 * Method that gets balance amount and displays it on the page 
	 * @param amount double the balance amount
	 */ 
	public void setBalance(double amount) { 
		String balanceString = String.format("%.2f", amount); 
		amountBalance.setText(balanceString); 
	} 
	
	@FXML
	public void showSetBalance() {
		setBalancePane.setVisible(true);
	}
	
	@FXML
	public void hideSetBalance() {
		setBalancePane.setVisible(false);
	}
	
	@FXML 
	public void initialize() { 
		controller.getBalance(); 
	}
	
	@FXML
	public void saveBalance() {
		controller.updateBalance(Float.parseFloat(newBalance.getText()));
	}
}
