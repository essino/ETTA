package view.borrowed;

import java.sql.Date;
import java.text.DateFormat;
import java.time.LocalDate;
import java.util.Locale;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableCell;
import model.BorrowedThing;

/**
 * Class used for in-table editing of dates
 * Extends JavaFX class TableCell
 */
public class DateEditingCell extends TableCell<BorrowedThing, java.sql.Date> {
	
	/**
	 * Reference to the date picker
	 */
    private DatePicker datePicker;
    
    /**
	 * Default locale
	 */
    Locale locale = Locale.getDefault();
    
    /**
	 * DateFormat for localizing the dates
	 */
    DateFormat df = DateFormat.getDateInstance(DateFormat.SHORT, locale);
    
    /**
     * Constructor for the date editing cell
     */
    public DateEditingCell() {
    }
    
    /**
     * Method for beginning date editing
     */
    @Override
    public void startEdit() {
        if (!isEmpty()) {
        	//transition from a non-editing state into an editing state, if the cell is editable
            super.startEdit();
            //date picker created for selecting the new date
            createDatePicker();
            setText(null);
            //date picker shown
            setGraphic(datePicker);
        }
    }

    /**
     * Method for canceling date editing
     */
    @Override
    public void cancelEdit() {
    	//transition from an editing state into a non-editing state, without saving any user input
        super.cancelEdit();
        //setting the old value in the right format
        setText(df.format(getDate()));
        //date picker vanishes
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
                //date picker shown
                setGraphic(datePicker);
            } else {
            	//formats and sets the new date as a String when the editing of the cell is finished
            	setText(df.format(getDate()));
            	//date picker vanishes :)
            	setGraphic(null);
            }
        }
    }

    /**
     * Method for creating the date picker in which the date is selected by the user
     */
    private void createDatePicker() {
    	//date picker created
        datePicker = new DatePicker(getDate().toLocalDate());
        datePicker.setMinWidth(this.getWidth() - this.getGraphicTextGap() * 2);
        //ComboBox action invoked when the value is changed
        datePicker.setOnAction((e) -> {
            System.out.println("Committed: " + datePicker.getValue().toString());
            //Fires the appropriate events back to the backing UI control
            //begins the process of pushing this edit back to the relevant data source
            //Also begins the transition from an editing state into a non-editing state.
            commitEdit((Date) Date.valueOf(datePicker.getValue()));
        });
    }

    /**
     * Method for getting the chosen date
     * @return getItem() Date the value chosen in the date picker for the cell. If there is no value, the current date is returned
     */
    private Date getDate() {
    	//gets the item in the cell
    	if (getItem() == null) {
    		return Date.valueOf(LocalDate.now());
    	} else {
    		return getItem();
    	}
    }  
    
}
