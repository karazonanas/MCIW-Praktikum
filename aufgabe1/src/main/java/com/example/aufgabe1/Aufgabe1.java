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
import javafx.scene.input.MouseEvent;
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
    private static final Color SHAPECOLOR = Color.GREEN;
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
    private double startSceneX, startSceneY;
    private double startCircleTranslateX, startCircleTranslateY;

    @Override
    public void start(Stage stage) throws Exception {
        this.selectedElement = null;
        // Museum
        Shape museum = this.createRectangle(10, 10, 40, 40, false);

        Pane museumVBox = this.createVBox(
                10,
                Pos.CENTER,
                museum,
                this.createLabel("Museum")
        );
        // Info
        Shape info = this.createCircle(20, false);
        Pane infoVBox = this.createVBox(
                10,
                Pos.CENTER,
                info,
                this.createLabel("Info")
        );

        // Sehenswürdigkeit
        Shape sehenswuerdigkeit = this.createPolygon(STERN, false, false);
        Pane sehenswuerdigkeitVBox = this.createVBox(
                10,
                Pos.CENTER,
                sehenswuerdigkeit,
                this.createLabel("Sehenswürdigkeit"));

        // Restaurant
        Shape restaurant = this.createPolygon(DREIECK, true, false);
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
        this.handlePlusButton(plusBtn);
        this.handleMinusButton(minusBtn);


        Pane editorBox = this.createVBoxWithAnchor(
                10,
                -1 ,
                300d,
                210d,
                -1,
                Pos.CENTER,
                plusBtn,
                minusBtn
        );

        // Karten-Box
        kartenBox.setStyle("-fx-border-style: solid; -fx-border-color: black; -fx-border-width: 2");

        this.prepareScene(stage, linkeBox, editorBox, kartenBox);
    }

    /**
     * ändert den Wert von this.selectedElement
     * @param geklicktesElement das zuletzt angeklickte Element
     */
    private void changeSelectedElement(Shape geklicktesElement) {
        if (this.selectedElement != null) {
            this.selectedElement.setFill(Color.BLACK);
        }

        geklicktesElement.setFill(SHAPECOLOR);
        this.selectedElement = geklicktesElement;
    }

    /**
     * erstellt ImageView
     * @param imgPfad Bildpfad
     * @return das ImageView
     */
    private ImageView getImage(String imgPfad) {
        Image img = new Image(getClass().getResource(imgPfad).toExternalForm());
        return new ImageView(img);
    }

    /**
     * Erstelle Kreis
     *
     * @param diameter Durchmesser
     * @return Circle-Objekt
     */
    private Shape createCircle(int diameter, boolean dragDrop) {
        Shape circle = new Circle(diameter);
        circle.setOnMouseClicked(e -> changeSelectedElement(circle));
        if (dragDrop)
            this.handleDragDrop(circle);
        return circle;
    }

    /**
     * Erstelle ein Rechteck
     * @param marginLeft Außenabstand links
     * @param marginTop Außenabstand oben
     * @param width Breite
     * @param height Höhe
     * @return Erstelltes Rechteck-Shape
     */
    private Shape createRectangle(double marginLeft, double marginTop, double width, double height, boolean dragDrop) {
        Shape rectangle = new Rectangle(marginLeft, marginTop, width, height);
        rectangle.setOnMouseClicked(e -> changeSelectedElement(rectangle));
        if (dragDrop)
            this.handleDragDrop(rectangle);
        return rectangle;
    }

    /**
     * Erstellt Polygone
     * @param points Punkte des Polygons
     * @param rotate180 wichtig für das Dreieck
     * @param dragDrop für das neu erstellte Polygon
     * @return Polygon (Dreieck, Stern)
     */
    private Shape createPolygon(Double[] points, boolean rotate180, boolean dragDrop) {
        Polygon randomPolygon = new Polygon();
        randomPolygon.getPoints().addAll(points);
        if (rotate180)
            randomPolygon.setRotate(180d);
        randomPolygon.setOnMouseClicked(e -> changeSelectedElement(randomPolygon));
        if (dragDrop)
            this.handleDragDrop(randomPolygon);

        return randomPolygon;
    }

    /**
     * Erstellt einen Button mit Beschriftung
     * @param label für den Button
     * @param minWidth die Mindestgröße des Buttons
     * @return ButtonBase
     */
    private ButtonBase createButton(String label, int minWidth) {
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
     * Erstellen der Scene und Vorbereiten & Anzeigen der Stage.
     * @param stage muss übergeben werden
     * @param items die in das neue Pane hinzugefügt werden müssen
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

    /**
     * handling Plus Button, um neue Elemente hinzuzufügen
     * @param plusBtn wenn geklickt, ausgewählte Element wird duplizieren
     */
    private void handlePlusButton(ButtonBase plusBtn) {
        EventHandler<ActionEvent> bKlick = new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                if (selectedElement instanceof Polygon) {
                    Polygon polygon = (Polygon) selectedElement;
                    Double[] points = new Double[polygon.getPoints().size()];
                    int i = 0;
                    for (double d : polygon.getPoints()) {
                        points[i] = d;
                        i++;
                    }
                    Shape newPolygon = createPolygon( points, polygon.getRotate() != 0, true);
                    anchorPaneKarte.getChildren().add(newPolygon);

                }
                else if (selectedElement instanceof Rectangle) {
                    Rectangle rectangle = (Rectangle) selectedElement;
                    Shape newRectangle = createRectangle(-1,-1,rectangle.getWidth(), rectangle.getHeight(), true);
                    anchorPaneKarte.getChildren().add(newRectangle);

                } else if (selectedElement instanceof Circle) {
                    Circle circle = (Circle) selectedElement;

                    Shape newCircle = createCircle((int)circle.getRadius(), true);
                    AnchorPane.setLeftAnchor(newCircle, 10d);
                    AnchorPane.setTopAnchor(newCircle, 10d);

                    anchorPaneKarte.getChildren().add(newCircle);
                }
            }
        };
        plusBtn.addEventHandler(ActionEvent.ACTION, bKlick);
    }

    /**
     * handling Minus Button, um ausgewählte Elemente in der Kartensicht zu löschen.
     * @param minusBtn wenn geklickt, ausgewählte Element löschen (nur Elemente, die auf der Karte liegen)
     */
    private void handleMinusButton (ButtonBase minusBtn) {
        minusBtn.setOnMouseClicked(click -> anchorPaneKarte.getChildren().remove(selectedElement));
        selectedElement = null;
    }

    /**
     * handling Drag & Drop, um es möglich zu machen Elemente zu verschieben.
     * @param shape die erstellte Figur
     */
    private void handleDragDrop(Shape shape) {
        EventHandler<MouseEvent> cPressed = mouseEvent -> {
            startSceneX = mouseEvent.getSceneX();
            startSceneY = mouseEvent.getSceneY();
            shape.setFill(SHAPECOLOR);

            startCircleTranslateX = shape.getTranslateX();
            startCircleTranslateY = shape.getTranslateY();

        };
        EventHandler<MouseEvent> cDragged = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {

                // berechnet die Veränderung der Mausposition
                double offsetX = event.getSceneX() - startSceneX;
                double offsetY = event.getSceneY() - startSceneY;

                // berechnet die neue Translation des Objekts durch Einbezug der veränderten Mausposition
                double stopCircleTranslateX = startCircleTranslateX + offsetX;
                double stopCircleTranslateY = startCircleTranslateY + offsetY;

                shape.setTranslateX(stopCircleTranslateX);
                shape.setTranslateY(stopCircleTranslateY);
            }
        };
        shape.setOnMousePressed(cPressed);
        shape.setOnMouseDragged(cDragged);

    }

    /**
     * die Basis-Methode
     * @param args wird an der Funktion launch() übergeben.
     */
    public static void main(String[] args) {
        launch(args);
    }
}
