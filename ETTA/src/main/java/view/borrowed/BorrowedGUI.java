package view.borrowed;

import java.io.IOException;
import java.sql.Date;
import java.util.Observable;
import java.util.Observer;
import java.util.ResourceBundle;

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
import res.MyBundle;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn.CellDataFeatures;


/**
 * GUI class relating to the Borrowed items section
 */
public class BorrowedGUI implements Observer{
	public static final BorrowedGUI single = new BorrowedGUI();
	/**
	 * MyBundle object for setting the right resource bundle to localize the application
	 */
	MyBundle myBundleInst = MyBundle.getInstance();
	//MyBundle myBundle;
	
	ResourceBundle bundle;
	
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
	
	/**
	 * the controller for borrowed things
	 */
	BorrowedController controller;
	
	/**
	 * an empty construtor for BorrowedGUI
	 */
	private BorrowedGUI() {
		//this.myBundle = myBundleInst;
		this.bundle=myBundleInst.getBundle();
		this.myBundleInst.addObserver(this);
	}
	public static BorrowedGUI getInstance() {
		return single;
	}
	@Override
	public void update(Observable o, Object arg) {
		System.out.println("observer informed");
		if(o instanceof MyBundle) {
			this.bundle=myBundleInst.getBundle();
		}
		
	}
	
	/**
	 * Method showing the search view in the Borrowed items section
	 * @param event ActionEvent that is handled
	 */
	@FXML
	public void showBorrowedSearch(ActionEvent event) {
		AnchorPane borrowedSearch = null;
		FXMLLoader loaderBorrowedSearch  = new FXMLLoader(getClass().getResource("/view/borrowed/BorrowedSearch.fxml"));
		loaderBorrowedSearch.setController(BorrowedSearchGUI.getInstance());
		loaderBorrowedSearch.setResources(bundle);
		try {
			System.out.println("Ra-ra-rasputin!");
			borrowedSearch = loaderBorrowedSearch.load();
			} catch (IOException e) {
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
		FXMLLoader loaderBorrowedView = new FXMLLoader(getClass().getResource("/view/borrowed/BorrowedView.fxml"));
		loaderBorrowedView.setController(BorrowedTableGUI.getInstance());
		loaderBorrowedView.setResources(bundle);
		try {
			borrowedView = loaderBorrowedView.load();
			} catch (IOException e) {
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
		FXMLLoader loaderBorrowedAdd = new FXMLLoader(getClass().getResource("/view/borrowed/BorrowedAdd.fxml"));
		loaderBorrowedAdd.setController(BorrowedAddGUI.getInstance());
		loaderBorrowedAdd.setResources(bundle);
		try {
				borrowedAdd = loaderBorrowedAdd.load();
			} catch (IOException e) { 
				e.printStackTrace();
			}
		//shows the loaded fxml file
		borrowedviewanchorpane.getChildren().setAll(borrowedAdd);
	}

	/**
	 * Method showing the view of returned items
	 * @param event ActionEvent that is handled
	 */
	@FXML
	public void showReturned(ActionEvent event) {
		AnchorPane borrowedReturned = null;
		FXMLLoader loaderBorrowedReturned = new FXMLLoader(getClass().getResource("/view/borrowed/BorrowedReturnedView.fxml"));
		loaderBorrowedReturned.setController(BorrowedReturnedTableGUI.getInstance());
		loaderBorrowedReturned.setResources(bundle);
		try {
			borrowedReturned = loaderBorrowedReturned.load();
			} catch (IOException e) {
			e.printStackTrace();
			}
		borrowedRootBorderPane.setCenter(borrowedReturned);
	}
}

