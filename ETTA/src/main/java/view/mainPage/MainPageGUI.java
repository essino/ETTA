package view.mainPage;

import java.sql.Date;
import java.sql.Time;
import java.text.DateFormat;
import java.util.Locale;

import org.joda.time.LocalDate;

import controller.MainViewController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Event;
import model.EventDAO;

/**
 * GUI class relating to the main page. Displays the balance amount, today's date and today's events.
 * @author Lena
 */
public class MainPageGUI  {

	/**
	 * Reference to the used MainViewController
	 */
	MainViewController controller;
	
	/**
	 * EventDAO used for accessing the database
	 */
	EventDAO eventDAO = new EventDAO();
	
	/**
	 * The reference of Label for amount of the balance will be injected by the FXML loader
	 */
	@FXML
	private Label amountBalance;
	
	/**
	 * The reference of Label for today's date will be injected by the FXML loader
	 */
	@FXML
	private Label todaysDate;
	
	/**
	 * The reference of TableView with today's events will be injected by the FXML loader
	 */
	@FXML
    private TableView<Event> eventTable;
	
	/**
	 * The reference of TableColumn event's title will be injected by the FXML loader
	 */
    @FXML
    private TableColumn<Event, String> title;
	/**
	 * The reference of TableColumn event's start date will be injected by the FXML loader
	 */
    @FXML
    private TableColumn<Event, Date> startDate;
    
	/**
	 * The reference of TableColumn event's start time will be injected by the FXML loader
	 */
    @FXML
    private TableColumn<Event, Time> startTime;
	/**
	 * The reference of TableColumn event's end time will be injected by the FXML loader
	 */
    @FXML
    private TableColumn<Event, Time> endTime;
	
	/** 
	 * Constructor
	 * Creates the MainViewController, passes itself as a parameter to the controller
	 */ 
	public MainPageGUI() {
		controller = new MainViewController(this);
		
	}
	
	/** 
	 * Method that sets and displays the balance amount on the page 
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
		//gets today's date
		Locale locale = Locale.getDefault();
	    DateFormat df = DateFormat.getDateInstance(DateFormat.SHORT, locale);
		LocalDate localDate = LocalDate.now();
		String text = localDate.toString();
		java.util.Date sqlDate = java.sql.Date.valueOf(text);
		todaysDate.setText(df.format(sqlDate));
		//displays the title of the events
		title.setCellValueFactory(
                new PropertyValueFactory<Event, String>("title"));
		//displays the start date of the events
		startDate.setCellValueFactory(
                new PropertyValueFactory<Event, Date>("startDate"));
		startDate.setCellFactory(column -> {
	        TableCell<Event, Date> cell = new TableCell<Event, Date>() {
	            @Override
	            protected void updateItem(Date item, boolean empty) {
	                super.updateItem(item, empty);
	                //if there is no date, show empty cell
	                if(empty) {
	                    setText(null);
	                }
	                //if there is a date, format it
	                else {
	                	setText(df.format(item));
	                }
	            }
	        };
	        return cell;
	    });
		//displays the start time of the events
		startTime.setCellValueFactory(
                new PropertyValueFactory<Event, Time>("startTime"));
		//displays the end time of the events
		endTime.setCellValueFactory(
                new PropertyValueFactory<Event, Time>("endTime"));
		
		ObservableList<Event> events =  FXCollections.observableArrayList(eventDAO.readTodaysEvents());
		eventTable.setItems(events);
	}

}
