package whs.mciv.aufgabe04.filter;

import javafx.scene.control.Alert;
import javafx.scene.control.TextFormatter;
import whs.mciv.aufgabe04.BaseController;

import java.awt.*;
import java.util.Objects;
import java.util.function.UnaryOperator;

public class FilterPlz implements UnaryOperator<TextFormatter.Change> {

    char land;
    public static final String DEFAULT_REGEX = "[0-9]{0,5}";
    public static final String O_REGEX = "[0-9]{0,4}";

    public FilterPlz (char land) {
        this.land = land;
    }

    @Override
    public TextFormatter.Change apply(TextFormatter.Change tfc) {

        String regexPlz;
        int numberLimit;
        String country;

        switch (land) {
            case 'o':
                regexPlz = O_REGEX;
                numberLimit = 4;
                country = "Österreich";
                break;
            case 'd':
            default:
                regexPlz = DEFAULT_REGEX;
                numberLimit = 5;
                country = "Deutschland";
                break;
        }

        String neu = tfc.getControlNewText();

        if (!neu.matches(regexPlz) && !Objects.equals(tfc.getControlText(), tfc.getControlNewText())) {
            Toolkit.getDefaultToolkit().beep();
            BaseController.setMessage(Alert.AlertType.WARNING, "Die Postleitzahl in " + country + " muss aus " + numberLimit + " Zahlen bestehen!");

            return null;
        }

        if (!neu.matches(DEFAULT_REGEX)) {
            return null;
        }

        return tfc;
    }
}
