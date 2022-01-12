package whs.mciv.aufgabe02.filter;

import javafx.scene.control.TextFormatter;

import java.awt.*;
import java.util.function.UnaryOperator;

public class FilterIban implements UnaryOperator<TextFormatter.Change> {
    public static final String ibanRegex = "^($|[A-Z]{0,2})\\d{0,20}";
    @Override
    public TextFormatter.Change apply(TextFormatter.Change change) {
            String neu = change.getControlNewText();
            if (! neu.matches(ibanRegex)) {
                Toolkit.getDefaultToolkit().beep();
                return null;
            }
            return change;
        }
}
