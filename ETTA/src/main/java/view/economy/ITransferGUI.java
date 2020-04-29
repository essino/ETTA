package view.economy;

import model.Transfer;

/**
 * Interface for economy GUIs that delete transfers
 * @author Lena
 *
 */
public interface ITransferGUI {
	
	/**
	 * Method for getting the selected transfer that should be deleted from the table
	 * @return Transfer the selected transfer
	 */
	public Transfer transferToDelete();

	/**
	 * Method for removing a transfer from the table
	 */
	public void removeFromTable(Transfer transferToDelete);

}
