package view.wishlist;

import java.sql.Date;
import java.text.DateFormat;
import java.util.Locale;

import javafx.scene.control.DatePicker;
import javafx.scene.control.TableCell;
import model.Item;

/**
 * A table cell for wishlist dates that enables in-table editing of the dates
 * Extends JavaFX TableCell
 */
public class WishlistDateEditingCell extends TableCell<Item, java.sql.Date> {
	
	/**
	 * Reference to the used DatePicker
	 */
	private DatePicker datePicker;
    
	/**
	 * Default locale
	 */
	private Locale locale = Locale.getDefault();

	/**
	 * DateFormat for localizing the dates
	 */
	private DateFormat df = DateFormat.getDateInstance(DateFormat.SHORT, locale);
    
	/**
	 * Constructor without parameters
	 */
	public WishlistDateEditingCell() {
    	
    }

    /**
     * Method for transitioning the cell to an editable state
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
     * Method for returning to a non-editable state without saving any user input
     */
    @Override
    public void cancelEdit() {
        super.cancelEdit();
        setText(getDate().toString());
        setGraphic(null);
    }

    /**
     * Method for updating the date in the table cell
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
            	if (getDate()==null) {
            		setText(null);
            	} else {
            		setText(df.format(getDate()));
            	}
                setGraphic(null);
            }
        }
    }

    /**
     * Method for creating the date picker in which the user can select the date
     */
    private void createDatePicker() {
    	if (getDate()==null) {
    		datePicker = new DatePicker(java.time.LocalDate.now());
    	} else {
    		datePicker = new DatePicker(getDate().toLocalDate());
    	}
        datePicker.setMinWidth(this.getWidth() - this.getGraphicTextGap() * 2);
        datePicker.setOnAction((e) -> {
            commitEdit((Date) Date.valueOf(datePicker.getValue()));
        });
    }

    /**
     * Method for getting the date from the data picker
     * @return Date the selected date.  
     * @return null if there is no date is selected
     */
    private Date getDate() {
    	if (getItem() == null) {
    		return null;
    	} else {
    		return getItem();
    	}
    }  

}
