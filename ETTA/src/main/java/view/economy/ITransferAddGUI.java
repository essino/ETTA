package view.economy;

import java.sql.Date;

/**
 * Interface for economy GUIs that add new transfers
 * @author Lena
 *
 */
public interface ITransferAddGUI {
	
	/**
	 * Method for getting the added transfer's amount 
	 * @return float the amount of the added transfer
	 */
	public float getTransferAmount();

	/**
	 * Method for getting the added transfer's category name
	 * @return String the category name of the added transfer
	 */
	public String getCategoryName();

	/**
	 * Method for getting the added transfer's description
	 * @return String the description of the added transfer
	 */
	public String getDescription();

	/**
	 * Method for getting the added transfer's date 
	 * @return Date the date of the added transfer
	 */
	public Date getTransferDate();
}
