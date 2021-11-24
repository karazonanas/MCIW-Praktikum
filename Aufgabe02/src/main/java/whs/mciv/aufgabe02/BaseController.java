package whs.mciv.aufgabe02;

import javafx.fxml.Initializable;
import javafx.stage.Stage;
import java.net.URL;
import java.util.ResourceBundle;

public class BaseController implements Initializable {

    protected Stage stage;
    protected boolean wurdeGespeichert = false;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public boolean wurdeGespeichert() {
        return this.wurdeGespeichert;
    }

}
