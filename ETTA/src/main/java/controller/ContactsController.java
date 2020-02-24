package controller;

import java.io.IOException;
import java.sql.Date;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import model.Event;
import model.EventDAO;
import model.Person;
import model.PersonDAO;
import view.ContactsGUI;

public class ContactsController {
	
	private ContactsGUI conGUI;
	private PersonDAO perDAO = new PersonDAO();
	private EventDAO eventDAO = new EventDAO();
	
	public void savePerson() {
		String personName = conGUI.getPersonName();
		String personAddress = conGUI.getPersonAddress();
		String personEmail = conGUI.getPersonEmail();
		Date personBirthday = conGUI.getPersonBirthday();
		Person person = new Person (personName, personBirthday, personEmail);
		Boolean Person = perDAO.createPerson(person);
		if(personBirthday != null) {
			Event birthday = new Event();
			birthday.setTitle(personName);
			birthday.setStartDate(personBirthday);
			birthday.setEndDate(personBirthday);
			birthday.setFullday(true);
			birthday.setRecurring(true);
			birthday.setRrule("RRULE:FREQ=YEARLY;");
			birthday.setCalendar("birthdays");
			eventDAO.createEvent(birthday);
		}
	}
	
	public ContactsController(ContactsGUI conGUI) {
		this.conGUI = conGUI;
	}
	
}
