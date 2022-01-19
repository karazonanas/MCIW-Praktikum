package whs.mciv.aufgabe02.filter;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextFormatter;

import java.awt.*;
import java.util.function.UnaryOperator;

public class FilterIban implements UnaryOperator<TextFormatter.Change> {
    // Regex für laufende Prüfung
    public static final String IBAN_REGEX = "[A-Z]{0,2}\\d{0,2}\\s?(?:\\d{0,4}\\s?){0,3}\\d{0,4}(?:\\s?\\d{0,2})";
    // Regex für schlussendliche Prüfung
    public static final String IBAN_REGEX_FINAL = "[A-Z]{2}\\d{2}\\s?(?:\\d{4}\\s?){3}\\d{4}(?:\\s?\\d{2})";

    @Override
    public TextFormatter.Change apply(TextFormatter.Change change) {
            String neu = change.getControlNewText();

            if (! neu.matches(IBAN_REGEX)) {
                Toolkit.getDefaultToolkit().beep();

                Alert meldung = new Alert(Alert.AlertType.WARNING, "Eine IBAN fängt mit zwei Buchstaben an und besteht anschließend aus 19 Zahlen.", ButtonType.OK);
                meldung.setHeaderText("");
                meldung.setTitle("IBAN ist fehlerhaft eingeben");
                meldung.showAndWait();
                return null;
            }
            return change;
    }
}
