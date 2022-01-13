package whs.mciv.aufgabe02.filter;

import javafx.scene.control.TextFormatter;

import java.awt.*;
import java.util.function.UnaryOperator;

public class FilterEmail  implements UnaryOperator<TextFormatter.Change> {
    public static final String EMAIL_REGEX = "^[^@]+@[^@]+\\.[^@]+$";
    @Override
    public TextFormatter.Change apply(TextFormatter.Change change) {
        String neu = change.getControlNewText();
        if (!neu.matches(EMAIL_REGEX) && ! change.isContentChange() && change.getControlText().length() > 0) {
            Toolkit.getDefaultToolkit().beep();
        }
        return change;
    }
}
