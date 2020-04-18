package view.borrowed;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Locale;
import java.util.ResourceBundle;

import javafx.scene.control.DatePicker;
import javafx.scene.control.TableCell;
import model.BorrowedThing;
import res.MyBundle;

/**
 * Class used for inline editing of dates
 * Extends JavaFX class TableCell
 */
public class DateEditingCell extends TableCell<BorrowedThing, java.sql.Date> {
	
	/**
	 * Reference to the datepicker
	 */
    private DatePicker datePicker;
    
    /**
	 * MyBundle object for formatting the dates in cells
	 */
    private static MyBundle myBundle = new MyBundle();
    
    /**
	 * new format object for Finnish date formatting
	 */
    private static SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");
    
    /**
	 * new format object for international date formatting
	 */
    private static SimpleDateFormat formatI = new SimpleDateFormat("dd/MM/yyyy");

    
    //changes language into English  
    //private final Locale myLocale = Locale.getDefault(Locale.Category.FORMAT);
    //Locale locale = Locale.getDefault();
    
    
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
            	//setText(getDate().toString());
            	//formats the date in the cell 
            	//System.out.println("We're the kids in a locale " + locale);
            	if (myBundle.getBundle().getBaseBundleName().equals("res.TextResources_en_GB")) {
            		setText(formatI.format(getDate()));
            	} else {
            		setText(format.format(getDate()));
            	}
            	setGraphic(null);
                
            }
        }
    }

    /**
     * Method for creating the date picker in which the date is selected by the user
     */
    private void createDatePicker() {
        datePicker = new DatePicker(getDate().toLocalDate());
        //changes the datepicker's language into English
        //datePicker.setOnShowing(e-> Locale.setDefault(Locale.Category.FORMAT,Locale.ENGLISH));
        datePicker.setMinWidth(this.getWidth() - this.getGraphicTextGap() * 2);
        datePicker.setOnAction((e) -> {
            System.out.println("Committed: " + datePicker.getValue().toString());
            commitEdit((Date) Date.valueOf(datePicker.getValue()));
        });
    }

    /**
     * Method for beginning date editing
     * @return Date the value of the date picker. If there is no value, the current date is added
     */
    private Date getDate() {
    	if (getItem() == null) {
    		return Date.valueOf(LocalDate.now());
    	} else {
    		return getItem();
    	}
    }  
    
}
