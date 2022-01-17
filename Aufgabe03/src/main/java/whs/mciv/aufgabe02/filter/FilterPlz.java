package whs.mciv.aufgabe02.filter;

import javafx.scene.control.Alert;
import javafx.scene.control.TextFormatter;
import whs.mciv.aufgabe02.BaseController;

import java.awt.*;
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

        switch (land) {
            case 'o':
                regexPlz = O_REGEX;
                break;
            case 'd':
            default:
                regexPlz = DEFAULT_REGEX;
                break;
        }

        String neu = tfc.getControlNewText();
        if (!neu.matches(regexPlz)) {
            BaseController.setMessage(Alert.AlertType.ERROR, "PLZ ist nicht richtig");
            Toolkit.getDefaultToolkit().beep();
        }

        if (!neu.matches(DEFAULT_REGEX)) {
            return null;
        }
        return tfc;
    }
}
