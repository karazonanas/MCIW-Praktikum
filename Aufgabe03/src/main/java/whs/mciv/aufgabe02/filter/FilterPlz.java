package whs.mciv.aufgabe02.filter;

import javafx.scene.control.TextFormatter;

import java.awt.*;
import java.util.function.UnaryOperator;

public class FilterPlz implements UnaryOperator<TextFormatter.Change> {

    char land;
    public static final String DEFAULTREGEX = "[0-9]{0,5}";
    public static final String OREGEX = "[0-9]{0,4}";

    public FilterPlz (char land) {
        this.land = land;
    }

    @Override
    public TextFormatter.Change apply(TextFormatter.Change tfc) {

        String regexPlz;

        switch (land) {
            case 'o': regexPlz = OREGEX;break;
            default: regexPlz = DEFAULTREGEX;break;
        }

        String neu = tfc.getControlNewText();
        if (!neu.matches(regexPlz)) {
            Toolkit.getDefaultToolkit().beep();
        }

        if (!neu.matches(DEFAULTREGEX)) {
            return null;
        }
        return tfc;
    }
}
