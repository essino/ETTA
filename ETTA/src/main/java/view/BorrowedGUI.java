package view;

import java.io.IOException;
import java.sql.Date;

import controller.BorrowedController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.util.Callback;
import model.BorrowedThing;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn.CellDataFeatures;


/**
 * GUI class relating to the Borrowed items section
 */
public class BorrowedGUI {

	/**
	 * The menu view to which the alternative views in the Borrowed items section are added
	 */
	@FXML
	BorderPane borrowedRootBorderPane;
	
	/**
	 * The list view from where adding, editing and deleting can be started
	 */
	@FXML 
	AnchorPane borrowedviewanchorpane;
	
	BorrowedController controller;
	
	//essi trying out starts
	/*
	@FXML
	TableView<BorrowedThing> borrowedTable;
	
	@FXML
	TableColumn<BorrowedThing, String> borrowedThingDescr;
	
	@FXML
	TableColumn<BorrowedThing, Date> loanDate;
	
	@FXML
	TableColumn<BorrowedThing, Date> returnDate;
	
	@FXML
	TableColumn<BorrowedThing, String> borrowedBy;
	
	@FXML
	private TableColumn<BorrowedThing, Boolean> returned;
	*/
	
	public BorrowedGUI() {
		//controller = new BorrowedController(this);
	}
	
	//essi trying out ends
	
	/**
	 * Method showing the search view in the Borrowed items section
	 * @param event ActionEvent that is handled
	 */
	@FXML
	public void showBorrowedSearch(ActionEvent event) {
		AnchorPane borrowedSearch = null;
		FXMLLoader loaderBorrowedSearch  = new FXMLLoader(getClass().getResource("/view/BorrowedSearch.fxml"));
		try {
			borrowedSearch = loaderBorrowedSearch.load();
			} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			}
		borrowedRootBorderPane.setCenter(borrowedSearch);
	}
	
	/**
	 * Method showing the main view in the Borrowed items section
	 * @param event ActionEvent that is handled
	 */
	@FXML
	public void showBorrowedView(ActionEvent event) {
		AnchorPane borrowedView = null;
		FXMLLoader loaderBorrowedView = new FXMLLoader(getClass().getResource("/view/BorrowedView.fxml"));
		try {
			borrowedView = loaderBorrowedView.load();
			} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			}
		borrowedRootBorderPane.setCenter(borrowedView);
	}
	
	/**
	 * Method showing the view of the list of borrowed items in the Borrowed items section
	 * @param event ActionEvent that is handled
	 */
	@FXML
	public void showBorrowedAdd(ActionEvent event) {
		AnchorPane borrowedAdd = null;
		FXMLLoader loaderBorrowedAdd = new FXMLLoader(getClass().getResource("/view/BorrowedAdd.fxml"));
		try {
				borrowedAdd = loaderBorrowedAdd.load();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		//shows the loaded fxml file
		borrowedviewanchorpane.getChildren().setAll(borrowedAdd);
	}
	
	//essi trying out starts
	/*
	@FXML
	public void initialize() {
		borrowedThingDescr.setCellValueFactory(new PropertyValueFactory<BorrowedThing, String>("description")); 
		borrowedBy.setCellValueFactory(new Callback<CellDataFeatures<BorrowedThing, String>, ObservableValue<String>>() {
			public ObservableValue<String> call(CellDataFeatures<BorrowedThing, String> borrowedThing) {
		         // item.getValue() returns the Data instance for a particular TableView row
		         return new ReadOnlyObjectWrapper(borrowedThing.getValue().getPerson().getName());
			}
		});
		loanDate.setCellValueFactory(new PropertyValueFactory<BorrowedThing, Date>("dateBorrowed"));
		returnDate.setCellValueFactory(new PropertyValueFactory<BorrowedThing, Date>("returnDate"));
		returned.setCellValueFactory(new PropertyValueFactory<BorrowedThing, Boolean>("returned"));
		
		ObservableList<BorrowedThing> data = FXCollections.observableArrayList(controller.getBorrowedThings());
		borrowedTable.setItems(data);
	}
	*/
	//essi trying out ends
}

