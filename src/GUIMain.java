import java.util.ArrayList;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;


public class GUIMain extends Application {
    
    private int quantity = 3;
    private static int grassblocksAnzahl = 0;
    private static int treeIDCounter = 1;
    private static int stoneIDCounter = 1;
    private static int eisenIDCounter = 1;
    private static int schwarzpIDCounter = 1;
    private static Player charakter = new Player("Tom");
    private static Pane pane = new Pane();
    private static Pane pane2 = new Pane();
    private int elapsedTime = 0;
    private Label timeLabel; 
    private TimerManager timerManager;
    private Pane startScreenPane = new Pane();
    private static boolean isGamePaused = false;
    private boolean isInitialStart = true;
    private Button startButton;
    private Button restartButton;
    private Quests quests;



    private Timeline fallCheck;

    @Override
    public void start(Stage primaryStage) {
        // IntroVideoPlayer.startVideo(primaryStage, () -> {
        //     try {
        //         startGameAfterVideo(primaryStage);
        //     } catch (Exception e) {
        //         System.err.println("Fehler beim Starten des Spiels: " + e.getMessage());
        //         e.printStackTrace();
        //     }
        // });
        
        // Starte das Spiel direkt ohne Video
        try {
            startGameAfterVideo(primaryStage);
        } catch (Exception e) {
            System.err.println("Fehler beim Starten des Spiels: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    private void startGameAfterVideo(Stage primaryStage) {
        try {
            System.out.println(charakter.getImageView());
        
            // Lade Hintergrundbild mit Fehlerbehandlung
            Image backgroundImage = new Image("file:rsc/Background-2.0.3.png");
            if (backgroundImage.isError()) {
                throw new Exception("Hintergrundbild konnte nicht geladen werden");
            }
            
            ImageView background = new ImageView(backgroundImage);
            background.setFitWidth(1200);
            background.setFitHeight(600);
            pane.getChildren().add(background);
        
            pane.getChildren().add(charakter.getImageView());
            pane.getChildren().add(charakter.getInventory().gettextFieldINV());
        
            Sounds sound = new Sounds();
            
        
            timeLabel = new Label();
            timerManager = new TimerManager(primaryStage, timeLabel, this, pane);
            timerManager.startTimer();

            Quests quests = new Quests(charakter, timerManager);
        
            for (int i = 0; i < quantity; i++) {
                Grassblocks grassblock = new Grassblocks(grassblocksAnzahl);
                grassblocksAnzahl++;
                pane.getChildren().add(grassblock.getImageView());
            }
        
            setupStartScreen();
            isGamePaused = true; // Spiel ist am Anfang pausiert
            pauseGame();
        
            // Füge Timer für Fall-Überprüfung hinzu
            fallCheck = new Timeline(new KeyFrame(Duration.millis(100), e -> checkPlayerFall()));
            fallCheck.setCycleCount(Timeline.INDEFINITE);
            fallCheck.play();
        
            Scene scene = new Scene(pane, 1200, 600);
            scene.setOnKeyPressed(event -> {
                if (isGamePaused || startScreenPane.isVisible()) {
                    // ESC funktioniert trotzdem
                    if (event.getCode() == KeyCode.ESCAPE) {
                        togglePause();
                    }
                    return;
                }
            
                switch (event.getCode()) {
                    case D:
                        sound.playWalkSound();
                        charakter.startMovingRight();
//                        if (!charakter.getImage().equals("manchenMoveR.png")) {
//                            charakter.setImage("manchenMoveR.png");
//                        }
                        break;
                    case A:
                        charakter.startMovingLeft();
//                        if (!charakter.getImage().equals("manchenMoveL.png")) {
//                            charakter.setImage("manchenMoveL.png");
//                        }
                        break;
                    case SPACE:
                        sound.playJumpSound();
                        charakter.jumping();
                        break;
                    case E:
//                        charakter.setWerkzeug("axt");
                        charakter.etwasAbbauen();
//                        charakter.setWerkzeug("Spitzhacke");
//                        charakter.SteinAbbauen();
//                        charakter.EisenAbbauen();
//                        charakter.SchwarzpulverAbbauen();
                        break;
                    case Q:
                        charakter.getInventory().printWoodAmount();
                        charakter.getInventory().printStoneAmount();
                        charakter.getInventory().printEisenAmount();
                        charakter.getInventory().printSchwarzpulverAmount();
                        break;
                        case C:
                        if (charakter.getInventory().getWood() >= 70 && charakter.getInventory().getStein() >= 48) {
                            charakter.setSteinPicke(true);
                            charakter.getInventory().setWood(charakter.getInventory().getWood() - 70);
                            charakter.getInventory().setStein(charakter.getInventory().getStein() - 48);

                        }
                        if (charakter.getInventory().getEisen() >= 35 && charakter.getInventory().getSchwarzpulver() >= 28) {
                            charakter.setGun(true);
                        }
                        break;
                    case DIGIT1:
                        charakter.setWerkzeug("hand");
                        charakter.ImageAktualisieren();
                        charakter.aendereWidth(60);
                        pane.getChildren().remove(charakter.getImageView());
                        pane.getChildren().add(charakter.getImageView());
                        break;
                    case DIGIT2:
                        charakter.setWerkzeug("axt");
                        charakter.ImageAktualisieren();
                        charakter.aendereWidth(80);
                        pane.getChildren().remove(charakter.getImageView());
                        pane.getChildren().add(charakter.getImageView());
                        break;
                    case DIGIT3:
                        if (charakter.getSteinPicke()) {
                            charakter.setWerkzeug("PickeS");
                        }else{
                        charakter.setWerkzeug("PickeH");
                        }
                        charakter.ImageAktualisieren();
                        charakter.aendereWidth(80);
                        pane.getChildren().remove(charakter.getImageView());
                        pane.getChildren().add(charakter.getImageView());
                        break;
                }
            });
            
            scene.setOnKeyReleased(event -> {
                if (!isGamePaused) {
                    switch (event.getCode()) {
                        case D:
                            sound.stopAllSounds();
                            charakter.stopMovingRight();
//                            charakter.setImage("manchen2R.png");
                            break;
                        case A:
                            charakter.stopMovingLeft();
//                            charakter.setImage("manchen2L.png");
                            break;
                        case SPACE:
                            charakter.stopJumping();
                            break;
                    }
                }
            });
        
            pane.requestFocus();
            primaryStage.setScene(scene);
            primaryStage.setTitle("The Last Days");
            primaryStage.show();
        } catch (Exception e) {
            System.err.println("Fehler beim Starten des Spiels: " + e.getMessage());
            e.printStackTrace();
        }
    }
    

    private void setupStartScreen() {
        startScreenPane.setPrefSize(1200, 600);
        startScreenPane.setStyle("-fx-background-color: rgba(0, 0, 0, 0.5);");
    
        // Hintergrundbild
        Image backgroundImage = new Image("file:rsc/Startbildschirm.png");
        ImageView background = new ImageView(backgroundImage);
        background.setFitWidth(1200);
        background.setFitHeight(600);
    
        // START-Button
        Button startButton = new Button("START");
        styleMenuButton(startButton);
        startButton.setLayoutX(525);
        startButton.setLayoutY(320);
        startButton.setOnAction(e -> {
            startScreenPane.setVisible(false);
            isGamePaused = false;
            resumeGame();
            showGameElements(true);
            startButton.setVisible(false);
        });
    
        // NEUSTART-Button
        Button restartButton = new Button("NEUSTART");
        styleMenuButton(restartButton);
        restartButton.setLayoutX(525);
        restartButton.setLayoutY(320);
        restartButton.setVisible(false); // nur bei ESC sichtbar
        restartButton.setOnAction(e -> {
            startScreenPane.setVisible(false);
            isGamePaused = false;
            restartGame();
            showGameElements(true);
        });
    
        // BESTENLISTE-Button
        Button highscoreButton = new Button("BESTENLISTE");
        styleMenuButton(highscoreButton);
        highscoreButton.setLayoutX(525);
        highscoreButton.setLayoutY(370);
        highscoreButton.setOnAction(e -> {
            System.out.println("Bestenliste anzeigen (z. B. neues Fenster)");
        });
    
        // Alles zum Pane hinzufügen
        startScreenPane.getChildren().addAll(background, startButton, restartButton, highscoreButton);
        pane.getChildren().add(startScreenPane);
    
        // Speichern für ESC-Steuerung
        this.startButton = startButton;
        this.restartButton = restartButton;
    }
    
    
    
    
    private void styleMenuButton(Button button) {
        button.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-size: 20px; -fx-border-color: #5A3E1B; -fx-border-width: 2px;");
        button.setPrefWidth(150);
        button.setPrefHeight(40);
    }    
    
    private void showGameElements(boolean visible) {
        for (Grassblocks g : Grassblocks.getGrassblocks()) {
            g.getImageView().setVisible(visible);
        }
        for (Tree t : Tree.getTrees()) {
            t.getImageView().setVisible(visible);
        }
        for (Stone s : Stone.getSteine()) {
            s.getImageView().setVisible(visible);
        }
        for (Eisen e : Eisen.getEisen()) {
            e.getImageView().setVisible(visible);
        }
        for (Schwarzpulver sp : Schwarzpulver.getSchwarzpulver()) {
            sp.getImageView().setVisible(visible);
        }
        charakter.getImageView().setVisible(visible);
        timeLabel.setVisible(visible);
    }
    
    

    private void togglePause() {
        if (!startScreenPane.isVisible()) {
            isGamePaused = !isGamePaused;
            startScreenPane.setVisible(isGamePaused);
    
            if (isGamePaused) {
                if (restartButton != null) restartButton.setVisible(true);
                if (startButton != null) startButton.setVisible(false);
                pauseGame();
                showGameElements(false); // verstecke Objekte
            } else {
                if (restartButton != null) restartButton.setVisible(false);
                resumeGame();
                showGameElements(true); // zeige Objekte
            }
        }
    }
    
    
    

    private void pauseGame() {
        charakter.getCollision().pauseMovementTimer();
        if (timerManager != null) timerManager.pauseTimer();
    
        for (Tree tree : Tree.getTrees()) {
            tree.pauseAbbauTimer();
        }
    
        for (Eisen eisen : Eisen.getEisen()) {
            eisen.pauseAbbauTimer();
        }

        for (Schwarzpulver sp : Schwarzpulver.getSchwarzpulver()) {
            sp.pauseAbbauTimer();
        }

        // Pausiere den Fall-Check Timer
        if (fallCheck != null) {
            fallCheck.pause();
        }
    }
    
    
    private void resumeGame() {
        charakter.getCollision().resumeMovementTimer();
        if (timerManager != null) timerManager.resumeTimer();
    
        for (Tree tree : Tree.getTrees()) {
            tree.resumeAbbauTimer();
        }
    
        for (Stone stone : Stone.getSteine()) {
            stone.resumeAbbauTimer();
        }

        for (Eisen eisen : Eisen.getEisen()) {
            eisen.resumeAbbauTimer();
        }

        for (Schwarzpulver sp : Schwarzpulver.getSchwarzpulver()) {
            sp.resumeAbbauTimer();
        }

        // Starte den Fall-Check Timer wieder
        if (fallCheck != null) {
            fallCheck.play();
        }
    }
    

    public void restartGame() {
        System.out.println("Spiel wird neu gestartet...");
    
        // ❗ Stoppe alle Timer und pausierende Aktionen VOR dem Reset
        pauseGame();
    
        // ❗ Stoppe Bewegungen des Charakters
        if (charakter != null) {
            charakter.stopMovingLeft();
            charakter.stopMovingRight();
            charakter.stopJumping();
        }
    
        // ❗ Stoppe und leere alle vorhandenen Timer
        if (fallCheck != null) {
            fallCheck.stop();
            fallCheck = null;
        }
    
        // Reset Spielfeld & Daten
        pane.getChildren().clear();
        grassblocksAnzahl = 0;
        treeIDCounter = 1;
        stoneIDCounter = 1;
        eisenIDCounter = 1;
        schwarzpIDCounter = 1;
        charakter = new Player("Tom"); // Neuer Spieler
    
        // Listen wirklich leeren
        Grassblocks.getGrassblocks().clear();
        Tree.getTrees().clear();
        Stone.getSteine().clear();
        Eisen.getEisen().clear(); 
        Schwarzpulver.getSchwarzpulver().clear();
    
        // Hintergrund
        Image backgroundImage = new Image("file:rsc/Background-2.0.3.png");
        ImageView background = new ImageView(backgroundImage);
        background.setFitWidth(1200);
        background.setFitHeight(600);
        pane.getChildren().add(background);
    
        // Grasblöcke neu setzen
        for (int i = 0; i < quantity; i++) {
            Grassblocks grassblock = new Grassblocks(grassblocksAnzahl);
            grassblocksAnzahl++;
            pane.getChildren().add(grassblock.getImageView());
        }
    
        // Charakter auf ersten Block setzen
        ImageView playerView = charakter.getImageView();
        Grassblocks ersterBlock = Grassblocks.getGrassblocks().get(0);
        playerView.setX(ersterBlock.getImageView().getX());
        playerView.setY(ersterBlock.getImageView().getY() - playerView.getFitHeight());
        pane.getChildren().add(playerView);
        pane.getChildren().add(charakter.getInventory().gettextFieldINV());
    
        // Quests neu starten
        quests = new Quests(charakter, timerManager);
    
        // Timer & UI zurücksetzen
        timerManager.restartTimer();
        pane.getChildren().add(timeLabel);
        timeLabel.setLayoutX(10);
        timeLabel.setLayoutY(10);
    
        // 🆕 Fall-Check neu starten (nur 1x!)
        fallCheck = new Timeline(new KeyFrame(Duration.millis(100), e -> checkPlayerFall()));
        fallCheck.setCycleCount(Timeline.INDEFINITE);
        fallCheck.play();
    
        // Startbildschirm vorbereiten, aber nicht anzeigen
        setupStartScreen();
        isGamePaused = false;
    
        pane.requestFocus();
    }
    
    

    public static void main(String[] args) {
        launch(args);
    }

    public static void grassPlazieren(){
        if (isGamePaused) return; // Verhindere Spawning während Pause/Game Over
        
        if(charakter.getImageView().getX() + 100 >= Grassblocks.getLastX() + 100) {
                    
            Grassblocks grass = new Grassblocks(grassblocksAnzahl);
            
            pane.getChildren().add(grass.getImageView());
            System.out.println("Neues Gras hinzugefügt");
            grassblocksAnzahl++;
            if (grass.getBewegung() == false) {
                treePlazieren();
                steineSpawnen();
                eisenSpawnen();
                schwarzpSpawnen();
        
            }
            

        }
        
    }
    private static boolean istPositionBelegt(double x, double y) {
        double breite = 90;
        double höhe = 90;
    
        for (Stone s : Stone.getSteine()) {
            if (überlappt(x, y, breite, höhe, s.getX(), s.getImageView().getY(), breite, höhe)) {
                return true;
            }
        }
        for (Eisen e : Eisen.getEisen()) {
            if (überlappt(x, y, breite, höhe, e.getX(), e.getImageView().getY(), breite, höhe)) {
                return true;
            }
        }
        for (Schwarzpulver sp : Schwarzpulver.getSchwarzpulver()) {
            if (überlappt(x, y, breite, höhe, sp.getX(), sp.getImageView().getY(), breite, höhe)) {
                return true;
            }
        }
        return false;
    }
    
    private static boolean überlappt(double x1, double y1, double w1, double h1,
                                     double x2, double y2, double w2, double h2) {
        return x1 < x2 + w2 &&
               x1 + w1 > x2 &&
               y1 < y2 + h2 &&
               y1 + h1 > y2;
    }
    
    
    private static void treePlazieren(){
        if (isGamePaused) return; // Verhindere Spawning während Pause/Game Over
        
        if (Math.random() < 0.3) {
            Tree tree = new Tree(treeIDCounter, Grassblocks.getLastX() - 60, Grassblocks.getLastY() - 350);
            pane.getChildren().add(tree.getImageView());
            System.out.println("baum erstellt");
            treeIDCounter++;
        }
        
    }

    public static void treeFromPaneRemove(Tree tree){
        pane.getChildren().remove(tree.getImageView());
    }
    public static void grassFromPaneRemove(Grassblocks grassblock){
        pane.getChildren().remove(grassblock.getImageView());
    }
    public static void stoneFromPaneRemove(Stone stone){
        pane.getChildren().remove(stone.getImageView());
    }
    public static void eisenFromPaneRemove(Eisen eisen){
        pane.getChildren().remove(eisen.getImageView());
    }
    public static void schwarzpulverFromPaneRemove(Schwarzpulver schwarzp){
        pane.getChildren().remove(schwarzp.getImageView());
    }


    public static void steineSpawnen(){    
        if (isGamePaused) return;
    
        double x = Grassblocks.getLastX() + 8;
        double y = Grassblocks.getLastY() - 40;
    
        if (Stone.getAnzahlSteine() < 100 && Math.random() < 0.4) {
            if (!istPositionBelegt(x, y)) {
                Stone stone = new Stone(x, y);
                pane.getChildren().add(stone.getImageView());
                System.out.println("Neuer Stein hinzugefügt");
            }
        }
    }
    
    public static void eisenSpawnen(){    
        if (isGamePaused) return;
    
        double x = Grassblocks.getLastX() + 8;
        double y = Grassblocks.getLastY() - 60;
    
        if (Eisen.getAnzahlEisen() < 100 && Math.random() < 0.2) {
            if (!istPositionBelegt(x, y)) {
                Eisen eisen = new Eisen(x, y);
                pane.getChildren().add(eisen.getImageView());
                System.out.println("Neues Eisenerz hinzugefügt");
            }
        }
    }
    

    public static void schwarzpSpawnen(){    
        if (isGamePaused) return;
    
        double x = Grassblocks.getLastX() + 8;
        double y = Grassblocks.getLastY() - 70;
    
        if (Schwarzpulver.getAnzahlSchwarzpulver() < 100 && Math.random() < 0.1) {
            if (!istPositionBelegt(x, y)) {
                Schwarzpulver sp = new Schwarzpulver(x, y);
                pane.getChildren().add(sp.getImageView());
                System.out.println("Neues Schwarzpulver hinzugefügt");
            }
        }
    }
    
    public static boolean weiterBewegen(){
        if (charakter.getImageView().getX() + 110 >= 890) {
            // Prüfe für jeden Grassblock die seitliche Kollision
            
                double blockX = Grassblocks.getGrassblocks().get(Grassblocks.getGrassblocks().size() - 2).getImageView().getX();
                double blockY = Grassblocks.getGrassblocks().get(Grassblocks.getGrassblocks().size() - 2).getImageView().getY();
                double block2X = Grassblocks.getGrassblocks().get(Grassblocks.getGrassblocks().size() - 1).getImageView().getX();
                double block2Y = Grassblocks.getGrassblocks().get(Grassblocks.getGrassblocks().size() - 1).getImageView().getY();
                double charX = charakter.getImageView().getX();
                double charY = charakter.getImageView().getY();

                // Wenn der Charakter über dem Block ist (springt), keine seitliche Kollision
                if (charY + charakter.getImageView().getFitHeight() <= block2Y) {
                    Grassblocks.verschiebeBlöcke();
                    Tree.verschiebeTrees();
                    Stone.verschiebeSteine();
                    Eisen.verschiebeEisen();
                    Schwarzpulver.verschiebeSchwarzpulver();
                    grassPlazieren();
                    return true;
                }
                
                if (charakter.getImageView().getX() + 90 >= 890) {
                    Grassblocks.verschiebeBlöcke();
                    Tree.verschiebeTrees();
                    Stone.verschiebeSteine();
                    Eisen.verschiebeEisen();
                    Schwarzpulver.verschiebeSchwarzpulver();
                    grassPlazieren();
                    return true;
                }
        
                // Prüfe seitliche Kollision nur wenn der Charakter nicht über dem Block ist
                if (charX + 60 >= block2X && charX <= block2X + 100) {
                    return true;
                }
            
            return true;

        }
        else{
            return false;
        }
    }

    public static Pane getPane(){
        return pane;
    }

    private void checkPlayerFall() {
        // Prüfe, ob der Spieler unter den untersten Block gefallen ist
        if (!Grassblocks.getGrassblocks().isEmpty()) {
            double lowestBlockY = Grassblocks.getGrassblocks().get(0).getImageView().getY();
            if (charakter.getImageView().getY() > lowestBlockY + 100) { // 100 ist ein Puffer
                showGameOver();
            }
        }
    }

    public void showGameOver() {
        // Pausiere das Spiel
        isGamePaused = true;
        pauseGame();

        // Erstelle Game-Over-Scene
        Pane gameOverPane = new Pane();
        gameOverPane.setPrefSize(1200, 600);
        gameOverPane.setStyle("-fx-background-color: #FF0000;"); // Roter Hintergrund

        // Game-Over-Text
        Label gameOverLabel = new Label("GAME OVER");
        gameOverLabel.setStyle("-fx-text-fill: white; -fx-font-size: 72px; -fx-font-weight: bold;");
        gameOverLabel.setLayoutX(400);
        gameOverLabel.setLayoutY(200);

        // Restart-Button
        Button restartButton = new Button("NEUSTART");
        restartButton.setStyle("-fx-background-color: black; -fx-text-fill: white; -fx-font-size: 24px; -fx-border-color: white; -fx-border-width: 2px;");
        restartButton.setLayoutX(500);
        restartButton.setLayoutY(350);
        restartButton.setPrefWidth(200);
        restartButton.setPrefHeight(50);
        
        restartButton.setOnAction(e -> {
            gameOverPane.setVisible(false);
            isGamePaused = false; // Setze Pause zurück
            resumeGame(); // Starte das Spiel wieder
            restartGame();
        });

        gameOverPane.getChildren().addAll(gameOverLabel, restartButton);
        pane.getChildren().add(gameOverPane);
        gameOverPane.toFront(); // Damit es wirklich oben ist


        // Deaktiviere Tastatureingaben, aber lasse ESC zu
        Scene scene = pane.getScene();
        if (scene != null) {
            scene.setOnKeyPressed(event -> {
                if (event.getCode() == KeyCode.ESCAPE) {
                    togglePause();
                }
            });
            scene.setOnKeyReleased(null);
        }

        // Pausiere alle aktiven Timers
        pauseGame();
    }

}
