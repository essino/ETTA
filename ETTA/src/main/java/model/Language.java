package model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="Language")
public class Language {
	
	/**
	 * unique id number
	 */
	@Id
	@Column
	private int language_id;
	
	/**
	 * String naming the language
	 */
	@Column(length=50)
	private String description;
	
	/**
	 * Boolean indicating if the language has been chosen by the user to be used
	 */
	@Column(name="Chosen")
	private boolean chosen;
	
	/**
	 * Construction without parameters
	 */
	public Language() {
		
	}

	/**
	 * Function to fetch the language's unique id number
	 * @return language_id id number referring to a certain language
	 */
	public int getLanguage_id() {
		return language_id;
	}

	/**
	 * Function to set a language id number
	 * @param language_id id number referring to a certain language
	 */
	public void setLanguage_id(int language_id) {
		this.language_id = language_id;
	}

	/**
	 * Function to fetch the language's unique id number
	 * @return description String naming the language
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Construction with parameters
	 * @param id unique id number (int)
	 * @param description String naming the language
	 * @param chosen boolean indicating whether or not the language is in use
	 */
	public Language(int id, String description, boolean chosen) {
		this.language_id=id;
		this.description = description;
		this.chosen = chosen;
	}

	/**
	 * Function to give a language a new description
	 * @param description String naming the language
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * Function used to find out whether a language is chosen to be in use or not
	 * @return chosen boolean indicating whether or not the language is in use
	 */
	public boolean isChosen() {
		return chosen;
	}

	/**
	 * Function to set a language in use
	 * @return chosen boolean indicating whether or not the language is in use
	 */
	public void setChosen(boolean chosen) {
		this.chosen = chosen;
	}
}
