package view.contacts;

import java.sql.Date;
import java.text.DateFormat;
import java.util.Locale;

import javafx.scene.control.DatePicker;
import javafx.scene.control.TableCell;
import model.Person;


public class ContactsDateEditingCell extends TableCell<Person, java.sql.Date> {
private DatePicker datePicker;
    
    public ContactsDateEditingCell() {
    }
    
    /**
	 * Default locale
	 */
    Locale locale = Locale.getDefault();
    
    /**
	 * DateFormat for localizing the dates
	 */
    DateFormat df = DateFormat.getDateInstance(DateFormat.SHORT, locale);

    @Override
    public void startEdit() {
        if (!isEmpty()) {
            super.startEdit();
            createDatePicker();
            setText(null);
            setGraphic(datePicker);
        }
    }

    @Override
    public void cancelEdit() {
        super.cancelEdit();
        //onko tässä jotain
        setText(getDate().toString());
        setGraphic(null);
    }

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
            	if(getDate()==null) {
            		setText("");
            	}
            	else {
            		setText(df.format(getDate()));
            	}
                setGraphic(null);
            }
        }
    }

    private void createDatePicker() {
    	if(getDate()==null) {
    		datePicker = new DatePicker(java.time.LocalDate.now());
    	}
    	else {
    		datePicker = new DatePicker(getDate().toLocalDate());
    	}
        
        
        datePicker.setMinWidth(this.getWidth() - this.getGraphicTextGap() * 2);
        datePicker.setOnAction((e) -> {
            System.out.println("Committed: " + datePicker.getValue().toString());
            commitEdit((Date) Date.valueOf(datePicker.getValue()));
        });
    }

    private Date getDate() {
    	if (getItem() == null) {
    		return null;
    	} else {
    		return getItem();
    	}
    }  
}
