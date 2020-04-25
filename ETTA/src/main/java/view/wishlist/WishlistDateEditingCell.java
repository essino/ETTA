package view.wishlist;

import java.sql.Date;
import java.text.DateFormat;
import java.time.LocalDate;
import java.util.Locale;

import javafx.scene.control.DatePicker;
import javafx.scene.control.TableCell;
import model.Item;

public class WishlistDateEditingCell extends TableCell<Item, java.sql.Date> {
	
	private DatePicker datePicker;
    
	/**
	 * Default locale
	 */
	Locale locale = Locale.getDefault();

	/**
	 * DateFormat for localizing the dates
	 */
	DateFormat df = DateFormat.getDateInstance(DateFormat.SHORT, locale);
    
    public WishlistDateEditingCell() {
    	
    }

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
            	setText(df.format(getDate()));
                setGraphic(null);
            }
        }
    }

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

    private Date getDate() {
    	if (getItem() == null) {
    		return Date.valueOf(LocalDate.now());
    	} else {
    		return getItem();
    	}
    }  

}
