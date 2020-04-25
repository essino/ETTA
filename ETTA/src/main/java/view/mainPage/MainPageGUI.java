package view.mainPage;

import java.sql.Date;
import java.sql.Time;

import org.joda.time.LocalDate;

import controller.MainViewController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import model.Event;
import model.EventDAO;

public class MainPageGUI {

	MainViewController controller;
	EventDAO eventDAO = new EventDAO();
	
	@FXML
	private Label amountBalance;
	
	@FXML
	private Label todaysDate;
	
	@FXML
    private TableView<Event> eventTable;
	
    @FXML
    private TableColumn<Event, String> title;
    @FXML
    private TableColumn<Event, Date> startDate;
    /*
    @FXML
    private TableColumn<Event, String> eventLocation;
    */
    @FXML
    private TableColumn<Event, Time> startTime;
    @FXML
    private TableColumn<Event, Time> endTime;
	
	BorderPane borderPane;
	AnchorPane content = null;

	/** 
	 * Constructor  
	 */ 
	public MainPageGUI() {
		controller = new MainViewController(this);
		
	}
	
	/** 
	 * Method that displays balance amount on the page 
	 * @param amount the balance amount
	 */
	public void setBalance(double amount) {
		String balanceString = String.format("%.2f", amount);
		amountBalance.setText(balanceString);
	}
	
	/** 
	 * Method that initializes the view, gets the balance, displays balance and today's date, 
	 * gets the events for today  from the eventDAO to display them on the page
	 */
	@FXML
	public void initialize() {
		controller.getBalance();
		LocalDate date = LocalDate.now();
		String text = date.toString();
		todaysDate.setText(text);
		title.setCellValueFactory(
                new PropertyValueFactory<Event, String>("title"));
		startDate.setCellValueFactory(
                new PropertyValueFactory<Event, Date>("startDate"));
		startTime.setCellValueFactory(
                new PropertyValueFactory<Event, Time>("startTime"));
		endTime.setCellValueFactory(
                new PropertyValueFactory<Event, Time>("endTime"));
		
		ObservableList<Event> events =  FXCollections.observableArrayList(eventDAO.readTodaysEvents());
		eventTable.setItems(events);
	}

}
