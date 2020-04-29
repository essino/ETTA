package view.economy;

import model.Transfer;

/**
 * Abstract super class for all the different economy table GUIs
 * @author Lena
 *
 */
public abstract class AbstractEconomyGUI {

	/**
	 * Abstract method for setting transfers to the table
	 * @param Treansfer[] transfers that will be shown in the table
	 */
	public abstract void setData(Transfer[] transfers);

}
