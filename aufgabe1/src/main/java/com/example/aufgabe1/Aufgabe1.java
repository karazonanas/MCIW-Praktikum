package com.example.aufgabe1;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBase;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.stage.Stage;

public class Aufgabe1 extends Application {

    private static final int WIDTH = 900;
    private static final int HEIGHT = 600;
    private static final Double[] STERN = {
            0.0, 16.5,
            18.2, 16.5,
            23.75, 0.0,
            29.3, 16.5,
            47.5, 16.5,
            33.0, 27.55,
            38.75, 45.0,
            23.75, 34.2,
            8.75, 45.0,
            14.5, 27.55
    };
    private static final Double[] DREIECK = {
            0.0, 0.0,
            40.0, 0.0,
            20.0, 40.0 };

    @Override
    public void start(Stage stage) throws Exception {

        // Museum
        Pane museumVBox = this.createVBox(
                10,
                Pos.CENTER,
                this.createRectangle(10, 10, 40, 40),
                this.createLabel("Museum")
        );
        // Info
        Pane infoVBox = this.createVBox(
                10,
                Pos.CENTER,
                this.createCircle(20),
                this.createLabel("Info")
        );
        // Sehenswürdigkeit
        Pane sehenswuerdigkeitVBox = this.createVBox(
                10,
                Pos.CENTER,
                this.createStar(),
                this.createLabel("Sehenswürdigkeit"));
        // Restaurant
        Pane restaurantVBox = this.createVBox(
                10,
                Pos.CENTER,
                this.createTriangle(),
                this.createLabel("Restaurant")
        );

        // Symbol-Box
        VBox poiSymbolBox = new VBox(25, museumVBox, infoVBox, sehenswuerdigkeitVBox, restaurantVBox);
        poiSymbolBox.setStyle("-fx-border-style: solid; -fx-border-color: black; -fx-border-width: 2; -fx-padding: 10");

        Pane linkeBox = this.createVBoxWithAnchor(
                10,
                100d,
                -1,
                50d,
                -1,
                Pos.TOP_LEFT,
                this.createLabel("Markierungen"),
                poiSymbolBox
        );

        // Editor-Box
        Pane editorBox = this.createVBoxWithAnchor(
                10,
                -1 ,
                300d,
                210d,
                -1,
                Pos.TOP_LEFT,
                this.createButton("+", 100),
                this.createButton("-", 100)
        );

        // Karten-Box
        Pane kartenBox = this.createVBoxWithAnchor(
                0,
                -1,
                100d,
                -1,
                50d,
                Pos.TOP_LEFT,
                this.getImage("berlin.png")
        );
        kartenBox.setStyle("-fx-border-style: solid; -fx-border-color: black; -fx-border-width: 2");

        this.prepareScene(stage, linkeBox, editorBox, kartenBox);
    }

    public static void main(String[] args) {
        launch(args);
    }

    private ImageView getImage(String imgPfad) {
        Image img = new Image(getClass().getResourceAsStream(imgPfad));
        return new ImageView(img);
    }

    /**
     * Erstelle Kreis
     *
     * @param diameter Durchmesser
     * @return Circle-Objekt
     */
    private Shape createCircle(int diameter)
    {
        return new Circle(diameter);
    }

    /**
     * Erstelle ein Rechteckt
     * @param marginLeft Außenabstand links
     * @param marginTop Außenabstand oben
     * @param width Breite
     * @param height Höhe
     * @return Erstelltes Rechteck-Shape
     */
    private Shape createRectangle(int marginLeft, int marginTop, int width, int height)
    {
        return new Rectangle(marginLeft, marginTop, width, height);
    }

    /**
     * Erstelle einen Stern-Shape
     *
     * @return Erstelle Stern-Shape
     */
    private Shape createStar()
    {
        Polygon starShape = new Polygon();
        starShape.getPoints().addAll(STERN);
        starShape.setFill(Color.AQUAMARINE);

        return starShape;
    }

    /**
     * Erstelle Dreieck
     *
     * @return Erstelltes Dreieck
     */
    private Shape createTriangle()
    {
        Polygon triangle = new Polygon();
        triangle.getPoints().addAll(DREIECK);
        triangle.setRotate(180);

        return triangle;
    }

    private ButtonBase createButton(String label, int minWidth)
    {
        Button button = new Button(label);
        if (minWidth != -1) button.setMinWidth(minWidth);

        return button;
    }

    /**
     * Erstelle Label
     *
     * @param label Label-Text
     * @return Erstelltes Label
     */
    private Label createLabel(String label)
    {
        return new Label(label);
    }

    /**
     * Erstelle eine VBox mit einem Label und einem Shape
     * @param padding Abstand zwischen den Items
     * @param position Positionierung der Items
     * @param items Zu verwendende Items
     * @return Die generierte VBox
     */
    private Pane createVBox(int padding, Pos position, Node... items) {
        VBox vbox = new VBox(padding, items);
        vbox.setAlignment(position);

        return vbox;
    }

    /**
     * Erstelle eine VBox mit einem Anchor
     * @param padding Innenabstand
     * @param top Anker oben
     * @param bottom Anker unten
     * @param left Anker links
     * @param right Anker rechts
     * @param items Die Items
     * @return VBox
     */
    private Pane createVBoxWithAnchor(int padding, double top, double bottom, double left, double right, Pos position, Node... items) {
        Pane vBox = this.createVBox(padding, position, items);

        if (left != -1) AnchorPane.setLeftAnchor(vBox, left);
        if (right != -1) AnchorPane.setRightAnchor(vBox, right);
        if (top != -1) AnchorPane.setTopAnchor(vBox, top);
        if (bottom != -1) AnchorPane.setBottomAnchor(vBox, bottom);

        return vBox;
    }

    /**
     *
     * @param stage
     * @param items
     * Erstellen der Scene und Vorbereiten & Anzeigen der Stage.
     */
    private void prepareScene(Stage stage, Node... items) {
        Pane pane = new AnchorPane();
        Scene scene = new Scene(pane, WIDTH, HEIGHT);
        stage.setScene(scene);
        stage.setTitle("POI Tool");
        stage.show();

        for(Node item : items) {
            pane.getChildren().add(item);
        }
    }
}
