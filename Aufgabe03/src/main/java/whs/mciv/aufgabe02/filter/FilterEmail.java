package whs.mciv.aufgabe02.filter;

import javafx.scene.control.Alert;
import javafx.scene.control.TextFormatter;
import whs.mciv.aufgabe02.BaseController;

import java.awt.*;
import java.util.function.UnaryOperator;

public class FilterEmail  implements UnaryOperator<TextFormatter.Change> {
    public static final String EMAIL_REGEX = "[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,4}";
    @Override
    public TextFormatter.Change apply(TextFormatter.Change change) {
        String neu = change.getControlNewText();

        /*
         * Überprüfe die E-Mail Adresse nur dann, wenn kein Fokus auf das entsprechende Feld liegt, da
         * sonst beim Tippen ein Alert geworfen wird
         */
        if (!neu.matches(EMAIL_REGEX) && change.getControlNewText().length() > 0 && !change.getControl().isFocused()) {
            Toolkit.getDefaultToolkit().beep();
            BaseController.setMessage(Alert.AlertType.WARNING, "Die angegebene E-Mail Adresse ist nicht valide!");
            change.getControl().requestFocus();
        }

        return change;
    }
}
