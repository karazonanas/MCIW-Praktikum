package whs.mciv.aufgabe04.filter;

import javafx.scene.control.TextFormatter;

import java.awt.*;
import java.util.function.UnaryOperator;

public class FilterPhoneNumber implements UnaryOperator<TextFormatter.Change> {
    public static final String PHONE_REGEX = "^($|\\+|\\d)[\\d()\\/-]{0,20}";

    @Override
    public TextFormatter.Change apply(TextFormatter.Change change) {
        String neu = change.getControlNewText();
        if (! neu.matches(PHONE_REGEX)) {
            Toolkit.getDefaultToolkit().beep();
            return null;
        }
        return change;
    }
}
