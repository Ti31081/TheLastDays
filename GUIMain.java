

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;


import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class GUIMain extends Application {
    private int quantity = 3;
    private static int grassblocksAnzahl = 0;
    private static int treeIDCounter = 1;
    private static int stoneIDCounter = 1;
    private static Player charakter;
    private static Pane pane;
    private static Pane pane2;

    public void start(Stage primaryStage) {
        charakter = new Player("Tom");
        pane = new Pane();
        pane2 = new Pane();

        Image backgroundImage = new Image("file:rsc/background.png");
        ImageView background = new ImageView(backgroundImage);
        background.setFitWidth(1200);
        background.setFitHeight(600);
        pane.getChildren().add(background);

        // Abstand zwischen den Gras-Elementen

        //hallo ist ein test

        pane.getChildren().add(charakter.getImageView());


        // Funktion zur Aktualisierung der Gras-Elemente


        // HBox zuerst leeren und dann die neue Anzahl hinzufügen

        for (int i = 0; i < quantity; i++) {
            Grassblocks grassblock = new Grassblocks(grassblocksAnzahl);
            grassblocksAnzahl++;

            pane.getChildren().add(grassblock.getImageView());
        }
            /* 
            if(playerView.getX() + 60 >= grassElements.get(grassElements.size() - 1).getX() + 80) {
                quantity++;
                grassElements.add(new ImageView(grassImage));
                grassElements.get(grassElements.size() - 1).setX(grassElements.get(grassElements.size() - 2).getX() + 100);
                grassElements.get(grassElements.size() - 1).setY(400);
                pane.getChildren().add(grassElements.get(grassElements.size() - 1));
                System.out.println("hallo");
            }
            */


        Scene scene = new Scene(pane, 1200, 600);
        scene.setOnKeyPressed(event -> {
            switch (event.getCode()) {
                // ** verlässt mit esc
                case ESCAPE:
                    System.exit(0);
                    break;
                case D:
                    // ** sound beim laufen
                    GameAudio.playlaufenAudio();
                    charakter.startMovingRight();
                    if (charakter.getImage() != "manchenMoveR.png") {
                        charakter.setImage("manchenMoveR.png");
                    }
                    break;

                case A:
                    // ** play walking audio when the player starts moving
                    GameAudio.playlaufenAudio();
                    charakter.startMovingLeft();
                    if (charakter.getImage() != "manchenMoveL.png") {
                        charakter.setImage("manchenMoveL.png");
                    }
                    break;

                case SPACE:
                    // ** play jumping sound stoppt automatisch
                    GameAudio.playspringenAudio();
                    charakter.jumping();
                    break;

                case E:
                    charakter.setWerkzeug("axt");
                    charakter.etwasAbbauen();
                    break;

                case Q:
                    charakter.getInventory().printWoodAmount();
            }
        });

        scene.setOnKeyReleased(event -> {
            switch (event.getCode()) {
                case D:
                    // ** stop walking audio when the player stops moving
                    GameAudio.stoplaufenAudio();
                    charakter.stopMovingRight();
                    charakter.setImage("manchenR.png");
                    break;
                case A:
                    // ** stop walking audio when the player stops moving
                    GameAudio.stoplaufenAudio();
                    charakter.stopMovingLeft();
                    charakter.setImage("manchenL.png");
                    break;
                case SPACE:
                    charakter.stopJumping();
                    break;
            }
        });

        // ** show start screen
        showStartScreen();

        pane.requestFocus();
        primaryStage.setScene(scene);
        primaryStage.setTitle("Titel Test");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    public static void grassPlazieren() {
        if (charakter.getImageView().getX() + 120 >= Grassblocks.getLastX() + 70) {

            Grassblocks grass = new Grassblocks(grassblocksAnzahl);

            pane.getChildren().add(grass.getImageView());
            System.out.println("Neues Gras hinzugefügt");
            grassblocksAnzahl++;
            treePlazieren();
            steineSpawnen();

        }

    }

    private static void treePlazieren() {
        if (Math.random() < 0.2) {
            Tree tree = new Tree(treeIDCounter, Grassblocks.getLastX() - 50, Grassblocks.getLastY() - 345);
            // ** figur ersheint vor den baum 
            pane.getChildren().add(1, tree.getImageView());
            System.out.println("baum erstellt");
            treeIDCounter++;
        }
    }

    public static void treeFromPaneRemove(Tree tree) {
        pane.getChildren().remove(tree.getImageView());
    }

    public static void grassFromPaneRemove(Grassblocks grassblock) {
        pane.getChildren().remove(grassblock.getImageView());
    }

    public static void steineSpawnen() {
        if (Stone.getAnzahlSteine() < 5) {       //Steine sollen sich immer wieder spawnen
            if (Math.random() < 0.2) {
                Stone stone = new Stone(Grassblocks.getLastX() - 10, Grassblocks.getLastY() - 40);
                pane.getChildren().add(stone.getImageView());
                System.out.println("Neuer Stein hinzugefügt");
            }
        }
    }

    public static boolean weiterBewegen() {
        if (charakter.getImageView().getX() + 90 >= 890) {
            Grassblocks.verschiebeBlocke();
            Tree.verschiebeTrees();
            Stone.verschiebeSteine();
            grassPlazieren();
            return true;

        } else {
            return false;
        }
    }

    // ** Startbildshirm anzeigen lassen
    private void showStartScreen() {
        Alert startAlert = new Alert(Alert.AlertType.NONE);
        startAlert.setTitle("Startbildshirm");

        Label startLabel = new Label("The Last Days!");
        Button startButton = new Button("Start");
        startButton.setPrefHeight(100);
        startButton.setPrefWidth(200);
        
        // Erzeugen der kästchen (Startbildshirm) VB = vertikaler container
        VBox dialogContent = new VBox(startLabel, startButton);
        dialogContent.setAlignment(Pos.CENTER);
        dialogContent.setPrefHeight(700);
        dialogContent.setPrefWidth(900);

        // wenn der knopf betätigt wird spielt die musik und das spiel
        startButton.setOnAction(event -> {
            GameAudio.playStartAudio();
            startAlert.setResult(ButtonType.OK); // beendet den close
        });

        // beim betätigen der start button startet das spiel, hintergrundmusik wird gespielt,mit esc wird das spiel geschlossen
        startButton.setOnKeyPressed(keyEvent -> {
            switch (keyEvent.getCode()) {
                case ENTER:
                    GameAudio.playStartAudio();
                    startAlert.setResult(ButtonType.OK); // schließe den alert
                    break;
                case ESCAPE:
                    System.exit(0); // Beim betätigen der ESCAPE Taste wird das Spiel beendet (beim startbildshirm).
                    break;
            }
        });

        // der inhalt des startbildshirms
        startAlert.getDialogPane().setContent(dialogContent);

        // zeigt den alert und wartet
        startAlert.showAndWait();
    
    
    }
}
