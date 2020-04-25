package fi.metropolia.OTP.group2.ETTA;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.sql.Date;
import java.time.LocalDate;

import org.junit.jupiter.api.Test;

import controller.InputCheck;

public class InputCheckTest {
	private InputCheck check = new InputCheck();
	private Date date1 = Date.valueOf("1980-11-04");
	private Date date2 = Date.valueOf("1980-07-23");
	private LocalDate date3;
	
	@Test
	public void testIsInputFloat() {
		assertEquals(true, check.isInputFloat("3.5"), "Float not recognized");
		assertEquals(false, check.isInputFloat("a"), "Not float recognized as float");
	}
	
	@Test
	public void testIsInputEmpty() {
		assertEquals(false, check.isInputEmpty("  a  "), "Not empty input marked as empty");
		assertEquals(true, check.isInputEmpty(""), "Empty input marked as not empty");
		assertEquals(true, check.isInputEmpty("  	"), "Empty input marked as not empty");
	}
	
	@Test
	public void testDateCheck() {
		assertEquals(true, check.dateCheck(date2, date1), "Dates checked wrong");
		assertEquals(false, check.dateCheck(date1, date2), "Dates check wrong");
	}
	
	@Test
	public void testIsDateEmpty() {
		assertEquals(true, check.isDateEmpty(date3), "Empty date check failed");
		assertEquals(false, check.isDateEmpty(LocalDate.now()), "Empty date check failed");
	}
}
