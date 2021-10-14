package com.example.aufgabe1;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.stage.Stage;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

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

    Set<Shape> shapes = new HashSet<>();


    @Override
    public void start(Stage stage) throws Exception {
        /* Hier wird eine AnchorPane erstellt. Dies ist ihr LayoutContainer für
        diese Aufgabe. Sie soll alle Elemente des POI Tools enthalten. */
        AnchorPane pane = new AnchorPane();



        Rectangle museum = new Rectangle(10, 10, 40, 40);   // Rechteck erzeugen
        Label museumLabel = new Label("Museum");
        VBox museumVBox = new VBox(10, museum, museumLabel);
        museumVBox.setAlignment(Pos.CENTER);

        // Info
        Circle info = new Circle(20);
        Label infoLabel = new Label("Info");
        VBox infoVBox = new VBox(10, info, infoLabel);
        infoVBox.setAlignment(Pos.CENTER);


        // Sehenswürdigkeit
        Polygon sehenswuerdigkeit = new Polygon();
        sehenswuerdigkeit.getPoints().addAll(STERN);
        sehenswuerdigkeit.setFill(Color.AQUAMARINE);
        Label sehenswuerdigkeitLabel = new Label("Sehenswürdigkeit");
        VBox sehenswuerdigkeitVBox = new VBox(10, sehenswuerdigkeit, sehenswuerdigkeitLabel);
        sehenswuerdigkeitVBox.setAlignment(Pos.CENTER);

        // Restaurant
        Polygon restaurant = new Polygon();
        restaurant.getPoints().addAll(DREIECK);
        restaurant.setRotate(180);
        Label restaurantLabel = new Label("Restaurant");
        VBox restaurantVBox = new VBox(10, restaurant, restaurantLabel);
        restaurantVBox.setAlignment(Pos.CENTER);


//        rect.setStroke(Color.DARKRED);                    // Rahmenfarbe
//        rect.setFill(Color.RED);                          // Füllfarbe
//        pane.getChildren().add(rect);                     // Hinzufügen zur AnchorPane

        // Erstellen der Scene und Vorbereiten & Anzeigen der Stage.
//        ImageView berlinKarte = getImage("berlin.png");
//        HBox berlinBox = new HBox(10, berlinKarte);
//        berlinBox.setStyle("-fx-border-style: solid; -fx-border-color: black; -fx-border-width: 2");
//        VBox markierungsBox = new VBox(40, museum, info, sehenswuerdigkeit, restaurant);
//        pane.getChildren().add(berlinBox);
//        pane.getChildren().add(markierungsBox);
//        AnchorPane.setRightAnchor(berlinBox, 50d);
//        AnchorPane.setBottomAnchor(berlinBox, 100d);
//        AnchorPane.setLeftAnchor(markierungsBox, 50d);
//        AnchorPane.setTopAnchor(markierungsBox, 100d);


        // Symbol-Box
        VBox poiSymbolBox = new VBox(25, museumVBox, infoVBox, sehenswuerdigkeitVBox, restaurantVBox);
        poiSymbolBox.setStyle("-fx-border-style: solid; -fx-border-color: black; -fx-border-width: 2; -fx-padding: 10");

        Label markierungenLabel = new Label("Markierungen");
        VBox linkeBox = new VBox(10, markierungenLabel, poiSymbolBox);
        AnchorPane.setLeftAnchor(linkeBox, 50d);
        AnchorPane.setTopAnchor(linkeBox, 100d);

        // Editor-Box
        Button add = new Button("+");
        add.setMinWidth(100);
        Button remove = new Button("-");
        remove.setMinWidth(100);
        VBox editorBox = new VBox(10, add, remove);
//        editorBox.setPrefHeight(HEIGHT);
        editorBox.setAlignment(Pos.CENTER);
        AnchorPane.setLeftAnchor(editorBox, 210d);
        AnchorPane.setBottomAnchor(editorBox, 300d);

        // Karten-Box
        ImageView berlinKarte = getImage("berlin.png");
        VBox kartenBox = new VBox(0, berlinKarte);
        kartenBox.setStyle("-fx-border-style: solid; -fx-border-color: black; -fx-border-width: 2");
        AnchorPane.setRightAnchor(kartenBox, 50d);
        AnchorPane.setBottomAnchor(kartenBox, 100d);

//        HBox masterBox = new HBox(10, poiSymbolBox, editorBox, kartenBox);
        pane.getChildren().add(linkeBox);
        pane.getChildren().add(editorBox);
        pane.getChildren().add(kartenBox);
//        AnchorPane.setTopAnchor(masterBox, 0.0);
//        AnchorPane.setRightAnchor(masterBox, 0.0);
//        AnchorPane.setBottomAnchor(masterBox, 0.0);
//        AnchorPane.setLeftAnchor(masterBox, 0.0);

        Scene scene = new Scene(pane, WIDTH, HEIGHT);
        stage.setScene(scene);
        stage.setTitle("POI Tool");
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

//    private VBox erstelleBox(Shape shape, String str) {
//
//    }

    private ImageView getImage(String imgPfad) {
        Image img = new Image(getClass().getResourceAsStream(imgPfad));
        return new ImageView(img);
    }
}
