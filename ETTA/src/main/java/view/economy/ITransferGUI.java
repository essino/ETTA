package view.economy;

import java.sql.Date;

import model.Transfer;

public interface ITransferGUI {
	public Transfer transferToDelete();

	public void removeFromTable(Transfer transferToDelete);


}
