package view.economy;

import java.sql.Date;

public interface ITransferAddGUI {
	public float getTransferAmount();

	public String getCategoryName();

	public String getDescription();

	public Date getTransferDate();
}
