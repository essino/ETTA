package controller;

import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Person;
import model.PersonDAO;


public class BorrowedController {
	PersonDAO personDAO = new PersonDAO();
	

	public ObservableList<String> personsList() {
		System.out.println("borrowed controller");
		Person[] people = personDAO.readPeople();
		ArrayList peopleNames = new ArrayList();
		for (Person person : people){
			peopleNames.add(person.getName());
		}
		ObservableList<String> names =  FXCollections.observableArrayList(peopleNames);
		System.out.println(names);
		return names;
	}
	
}
