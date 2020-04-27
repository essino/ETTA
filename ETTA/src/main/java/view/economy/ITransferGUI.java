package view.economy;

import model.Transfer;

public interface ITransferGUI {
	public Transfer transferToDelete();

	public void removeFromTable(Transfer transferToDelete);
}
