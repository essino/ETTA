package fi.metropolia.OTP.group2.ETTA;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;

import model.Language;
import model.LanguageDAO;

//Tests for Language DAO class
@TestMethodOrder(OrderAnnotation.class)
public class LanguageDAOTest {
	
	//used test variables
	private LanguageDAO langDAO = new LanguageDAO(true);
	private Language english = new Language();
	private Language finnish = new Language(2, "Finnish", false);
	
	//Test for creating and choosing languages
	@Test
	@Order(1)
	public void testCreateLanguage() {
		english.setLanguage_id(1);
		english.setDescription("English");
		english.setChosen(true);
		assertEquals(true, langDAO.createLanguage(english), "Creating language failed");
		assertEquals(1, langDAO.readLanguage("English").getLanguage_id(), "Readian failed");
		assertEquals(true, langDAO.createLanguage(finnish), "Creating language failed");
	}
	
	//Test for reading languages
	@Test
	@Order(2)
	public void testReadLanguages() {
		assertEquals(2, langDAO.readLanguages().length, "Reading all languages failed");
	}
	
	//Test for finding a language with its id
	@Test
	@Order(3)
	public void testReadLanguageById() {
		assertEquals("English", langDAO.readLanguage(1).getDescription(), "Readiang language by id failed");
	}
	
	//Test for finding a language with its name
	@Test
	@Order(4)
	public void testReadLanguageByName() {
		assertEquals(true, langDAO.readLanguage("English").isChosen(), "Reading language by name failed");
	}
	
	//Test for getting the currently selected language
	@Test
	@Order(5)
	public void testGetSelectedLanguage() {
		assertEquals("English", langDAO.getSelectedLanguage().getDescription(), "Reading selected language failed");
	}
	
	//Test for updating the language selection
	//Unselect English and the chosen language and choose Finnish
	@Test
	@Order(6)
	public void testUpdateLanguage() {
		Language eng = langDAO.getSelectedLanguage();
		eng.setChosen(false);
		assertEquals(true, langDAO.updateLanguage(eng), "Updating language failed");
		assertEquals(null, langDAO.getSelectedLanguage(), "Updating language failed");
		Language fin = langDAO.readLanguage("Finnish");
		fin.setChosen(true);
		assertEquals(true, langDAO.updateLanguage(fin), "Updating language failed");
		assertEquals("Finnish", langDAO.getSelectedLanguage().getDescription(), "Updating language failed");
		assertEquals(true, langDAO.getSelectedLanguage().isChosen(), "Updating language failed");
	}
	
	//Test for deleting a language
	@Test
	@Order(7)
	public void testDeleteLanguage() {
		Language fin = langDAO.readLanguage("Finnish");
		assertEquals(true, langDAO.deleteLanguage(fin), "Deleting language failed");
		Language eng = langDAO.readLanguage("English");
		assertEquals(true, langDAO.deleteLanguage(eng), "Deleting language failed");
	}
}
