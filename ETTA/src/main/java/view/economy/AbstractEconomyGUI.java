package view.economy;

import model.Transfer;

public abstract class AbstractEconomyGUI {

	public abstract void setData(Transfer[] readSeletedTransfers);

	public abstract Transfer transferToDelete();

	public abstract void removeFromTable(Transfer transferToDelete);
}