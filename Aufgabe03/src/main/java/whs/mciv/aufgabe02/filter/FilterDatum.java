package whs.mciv.aufgabe02.filter;

import java.time.LocalDate;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.util.Callback;

public class FilterDatum implements Callback<DatePicker, DateCell> {
    @Override
    public DateCell call(DatePicker datePicker) {
        DateCell dc = new DateCell() {
            @Override
            public void updateItem(LocalDate item, boolean empty) {
                super.updateItem(item, empty);

                if (item.isBefore(LocalDate.now())) {
                    setDisable(true);
                }
            }
        };

        return dc;
    }
}