package controller;

import java.io.IOException;
import java.sql.Date;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import model.Person;
import model.PersonDAO;
import view.ContactsGUI;

public class ContactsController {
	
	private ContactsGUI conGUI;
	private PersonDAO perDAO = new PersonDAO();
	
	/**
	 * Method make person witch have information of name, address, e-mail and birthday
	 * 
	 */
	public void savePerson() {
		String PersonName = conGUI.getPersonName();
		String PersonAddress = conGUI.getPersonAddress();
		String PersonEmail = conGUI.getPersonEmail();
		Date PersonBirthday = conGUI.getPersonBirthday();
		
		Person person = new Person (PersonName, PersonBirthday, PersonEmail);
		
		Boolean Person = perDAO.createPerson(person);
	}
	
	
	public ContactsController(ContactsGUI conGUI) {
		this.conGUI = conGUI;
	}
	
}
