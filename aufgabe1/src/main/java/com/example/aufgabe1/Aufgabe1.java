package com.example.aufgabe1;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
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
    private Shape selectedElement;
    private Pane anchorPaneKarte = new AnchorPane(this.getImage("berlin.png"));
    private Pane kartenBox =  this.createVBoxWithAnchor(
            0,
            -1,
            100d,
            -1,
            50d,
            Pos.TOP_LEFT,
            this.anchorPaneKarte
    );

    @Override
    public void start(Stage stage) throws Exception {
        this.selectedElement = null;

        // Museum
        Shape museum = this.createRectangle(10, 10, 40, 40, true);

        Pane museumVBox = this.createVBox(
                10,
                Pos.CENTER,
                museum,
                this.createLabel("Museum")
        );
        // Info
        Shape info = this.createCircle(20, true);
        Pane infoVBox = this.createVBox(
                10,
                Pos.CENTER,
                info,
                this.createLabel("Info")
        );

        // Sehenswürdigkeit
        Shape sehenswuerdigkeit = this.createStar(true);
        Pane sehenswuerdigkeitVBox = this.createVBox(
                10,
                Pos.CENTER,
                sehenswuerdigkeit,
                this.createLabel("Sehenswürdigkeit"));

        // Restaurant
        Shape restaurant = this.createTriangle(true);
        Pane restaurantVBox = this.createVBox(
                10,
                Pos.CENTER,
                restaurant,
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
        ButtonBase plusBtn = this.createButton("+", 100);
        ButtonBase minusBtn = this.createButton("-", 100);

        EventHandler<ActionEvent> bKlick = new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                if (selectedElement instanceof Rectangle) {
                    Rectangle rectangle = (Rectangle) selectedElement;
//                    AnchorPane.setLeftAnchor(rectangle, 10d);
//                    AnchorPane.setTopAnchor(rectangle, 10d);
                    anchorPaneKarte.getChildren().add(new Rectangle(rectangle.getHeight(), rectangle.getHeight()));
                }
                else if (selectedElement instanceof Circle) {
                    Circle circle = (Circle) selectedElement;

                    Circle newCircle = new Circle(circle.getRadius());
                    AnchorPane.setLeftAnchor(newCircle, 10d);
                    AnchorPane.setTopAnchor(newCircle, 10d);

                    anchorPaneKarte.getChildren().add(newCircle);
                }
                else if (selectedElement instanceof Polygon) {
                    Polygon polygon = (Polygon) selectedElement;

                    Polygon newPolygon = new Polygon();
                    newPolygon.getPoints().addAll(polygon.getPoints());
                    newPolygon.setRotate(polygon.getRotate());

                    anchorPaneKarte.getChildren().add(newPolygon);
                }
            }
        };
        plusBtn.addEventHandler(ActionEvent.ACTION, bKlick);

        Pane editorBox = this.createVBoxWithAnchor(
                10,
                -1 ,
                300d,
                210d,
                -1,
                Pos.TOP_LEFT,
                plusBtn,
                minusBtn
        );

        // Karten-Box
        kartenBox.setStyle("-fx-border-style: solid; -fx-border-color: black; -fx-border-width: 2");

        this.prepareScene(stage, linkeBox, editorBox, kartenBox);
    }

    private void changeSelectedElement(Shape geklicktesElement)
    {
        if (this.selectedElement != null) {
            this.selectedElement.setFill(Color.BLACK);
        }

        geklicktesElement.setFill(Color.GREEN);
        this.selectedElement = geklicktesElement;
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
    private Shape createCircle(int diameter, boolean highlightable)
    {
        Shape circle = new Circle(diameter);
        if (highlightable)
            circle.setOnMouseClicked(e -> changeSelectedElement(circle));
        return circle;
    }

    /**
     * Erstelle ein Rechteckt
     * @param marginLeft Außenabstand links
     * @param marginTop Außenabstand oben
     * @param width Breite
     * @param height Höhe
     * @return Erstelltes Rechteck-Shape
     */
    private Shape createRectangle(double marginLeft, double marginTop, double width, double height, boolean highlightable)
    {
        Shape rectangle = new Rectangle(marginLeft, marginTop, width, height);
        if (highlightable)
            rectangle.setOnMouseClicked(e -> changeSelectedElement(rectangle));
        return rectangle;
    }

    /**
     * Erstelle einen Stern-Shape
     *
     * @return Erstelle Stern-Shape
     */
    private Shape createStar(boolean highlightable)
    {
        Polygon starShape = new Polygon();
        starShape.getPoints().addAll(STERN);
        if (highlightable)
            starShape.setOnMouseClicked(e -> changeSelectedElement(starShape));

        return starShape;
    }

    /**
     * Erstelle Dreieck
     *
     * @return Erstelltes Dreieck
     */
    private Shape createTriangle(boolean highlightable)
    {
        Polygon triangle = new Polygon();
        triangle.getPoints().addAll(DREIECK);
        triangle.setRotate(180);

        if (highlightable)
            triangle.setOnMouseClicked(e -> changeSelectedElement(triangle));

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
     * @param stage muss übergeben werden
     * @param items die in das neue Pane hinzugefügt werden müssen
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
