package view.economy;

import java.util.Locale;
import java.sql.Date;
import java.text.DateFormat;
import java.time.LocalDate;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableCell;
import model.Transfer;

/**
 * Class used for in-table editing of dates
 * Extends JavaFX class TableCell
 */
public class DateEditingCell extends TableCell<Transfer, java.sql.Date> {
	
	/**
	 * Reference to the date picker
	 */
    private DatePicker datePicker;
    
    /**
	 * Construction without parameters
	 */
    public DateEditingCell() {
    }

    /**
	 * Default locale
	 */
    Locale locale = Locale.getDefault();
    
    /**
	 * DateFormat for localizing the dates
	 */
    DateFormat df = DateFormat.getDateInstance(DateFormat.SHORT, locale);
    
    /**
     * Method for beginning date editing
     */
    @Override
    public void startEdit() {
        if (!isEmpty()) {
            super.startEdit();
            createDatePicker();
            setText(null);
            setGraphic(datePicker);
        }
    }

    /**
     * Method for canceling date editing
     */
    @Override
    public void cancelEdit() {
        super.cancelEdit();
        setText(getDate().toString());
        setGraphic(null);
    }

    /**
     * Method for updating the date
     * This method is called whenever the item in the cell is changed
     * @param item the date being updated
     * @param empty indicates if the cell is empty
     */
    @Override
    public void updateItem(java.sql.Date item, boolean empty) {
        super.updateItem(item, empty);

        if (empty) {
            setText(null);
            setGraphic(null);
        } else {
            if (isEditing()) {
                if (datePicker != null) {
                    datePicker.setValue(getDate().toLocalDate());
                }
                setText(null);
                setGraphic(datePicker);
            } else {
            	//formats the date
            	setText(df.format(getDate()));
            	setGraphic(null);
            }
        }
    }

    /**
     * Method for creating the date picker in which the date is selected by the user
     */
    private void createDatePicker() {
        datePicker = new DatePicker(getDate().toLocalDate());
        //sets datepicker on right size on screen
        datePicker.setMinWidth(this.getWidth() - this.getGraphicTextGap() * 2);
        datePicker.setOnAction((e) -> {
            System.out.println("Committed: " + datePicker.getValue().toString());
            commitEdit((Date) Date.valueOf(datePicker.getValue()));
        });
    }

    /**
     * Method for getting the chosen date
     * @return getItem() Date the value chosen in the date picker for the cell. If there is no value, the current date is returned
     */
    private Date getDate() {
    	if (getItem() == null) {
    		return Date.valueOf(LocalDate.now());
    	} else {
    		return getItem();
    	}
    }  
    
}
