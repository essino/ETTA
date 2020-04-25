package model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="Language")
public class Language {
	@Id
	@Column
	private int language_id;
	
	@Column(length=50)
	private String description;
	
	@Column(name="Chosen")
	private boolean chosen;
	
	public Language() {
		
	}

	public int getLanguage_id() {
		return language_id;
	}

	public void setLanguage_id(int language_id) {
		this.language_id = language_id;
	}

	public String getDescription() {
		return description;
	}

	public Language(int id, String description, boolean chosen) {
		this.language_id=id;
		this.description = description;
		this.chosen = chosen;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public boolean isChosen() {
		return chosen;
	}

	public void setChosen(boolean chosen) {
		this.chosen = chosen;
	}
}
