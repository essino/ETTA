package view;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

public class ContactsGUI {
	@FXML 
	BorderPane contactsrootborderpane;
	
	@FXML
	public void showSearchView(ActionEvent event) {
		AnchorPane contactsSearchView = null;
		FXMLLoader loaderContactsSearchView  = new FXMLLoader(getClass().getResource("/view/ContactsSearch.fxml"));
		try {
			contactsSearchView = loaderContactsSearchView.load();
			} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			}
		contactsrootborderpane.setCenter(contactsSearchView);
	}
	
	@FXML
	public void showContactsView(ActionEvent event) {
		AnchorPane contactsView = null;
		FXMLLoader loaderContactsView  = new FXMLLoader(getClass().getResource("/view/ContactsView.fxml"));
		try {
			contactsView = loaderContactsView.load();
			} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			}
		contactsrootborderpane.setCenter(contactsView);
	}
}
