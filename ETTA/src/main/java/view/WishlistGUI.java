package view;

import java.io.IOException;
import java.sql.Date;

import controller.WishlistController;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.util.Callback;
import model.Item;
import model.Person;

/**
 * GUI class relating to the Wish section
 */
public class WishlistGUI {
	
	/**
	 * The menu view to which the alternative views in the Wish section are added
	 */
	@FXML
	BorderPane wishmainpane;
	
	/**
	 * Method showing the Wishlist view in the Wish section
	 * @param event ActionEvent that is handled
	 */
	@FXML
	public void showAll(ActionEvent event) {
		AnchorPane allView = null;
		FXMLLoader loaderWishlistAll  = new FXMLLoader(getClass().getResource("/view/WishlistView.fxml"));
		try {
			allView = loaderWishlistAll.load();
			} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			}
		wishmainpane.setCenter(allView);
	}
}