package whs.mciv.aufgabe02.filter;

import javafx.scene.control.TextFormatter;

import java.awt.*;
import java.util.function.UnaryOperator;

public class FilterPhoneNumber implements UnaryOperator<TextFormatter.Change> {
    public static final String PHONEREGEX = "^($|\\+|\\d)[\\d()\\/-]{0,20}";

    @Override
    public TextFormatter.Change apply(TextFormatter.Change change) {
        String neu = change.getControlNewText();
        if (! neu.matches(PHONEREGEX)) {
            Toolkit.getDefaultToolkit().beep();
            return null;
        }
        return change;
    }
}
