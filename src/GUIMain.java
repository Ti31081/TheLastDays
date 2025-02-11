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
    private static Player charakter = new Player("Tom");
    private static Pane pane = new Pane();
    private static Pane pane2 = new Pane();
    private int elapsedTime = 0;
    private Label timeLabel; 
    private Pane startScreenPane = new Pane();
    private boolean isGamePaused = false;

    public void start(Stage primaryStage) {
        
        System.out.println(charakter.getImageView());

        Image backgroundImage = new Image("file:rsc/Background-2.0.3.png");
        ImageView background = new ImageView(backgroundImage);
        background.setFitWidth(1200);
        background.setFitHeight(600);
        pane.getChildren().add(background);
        
        pane.getChildren().add(charakter.getImageView());

        Sounds sound = new Sounds();
        
        
        timeLabel = new Label("Time: 0:00");
        timeLabel.setLayoutX(10); // Position the label at the top left
        timeLabel.setLayoutY(10);
        pane.getChildren().add(timeLabel); // Add the label to the pane

        // Timer to update elapsed time every second
        Timeline timer = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
            elapsedTime++; // Increment elapsed time
            int minutes = elapsedTime / 60; // Calculate minutes
            int seconds = elapsedTime % 60; // Calculate seconds
            timeLabel.setText(String.format("Time: %d:%02d", minutes, seconds)); // Update the label text
        
        }));
        timer.setCycleCount(Timeline.INDEFINITE); // Ensure the timer runs indefinitely
        timer.play(); // Start the timer
 

            // HBox zuerst leeren und dann die neue Anzahl hinzufügen
            
            for (int i = 0; i < quantity; i++) {
                Grassblocks grassblock = new Grassblocks(grassblocksAnzahl);
                grassblocksAnzahl++;
                
                pane.getChildren().add(grassblock.getImageView());
            }

            setupStartScreen();
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
            if (event.getCode() == KeyCode.ESCAPE) {
                togglePause();
            }
            if (!isGamePaused) {
                switch (event.getCode()) {
                    case D:
                        sound.playWalkSound();
                        charakter.startMovingRight();
                        if (!charakter.getImage().equals("manchenMoveR.png")) {
                            charakter.setImage("manchenMoveR.png");
                        }
                        break;
                    case A:
                        charakter.startMovingLeft();
                        if (!charakter.getImage().equals("manchenMoveL.png")) {
                            charakter.setImage("manchenMoveL.png");
                        }
                        break;
                    case SPACE:
                        sound.playJumpSound();
                        charakter.jumping();
                        break;
                    case E:
                        charakter.setWerkzeug("axt");
                        charakter.etwasAbbauen();
                        break;
                    case Q:
                        charakter.getInventory().printWoodAmount();
                        break;
                }
            }
        });

        scene.setOnKeyReleased(event -> {
            if (!isGamePaused) {
                switch (event.getCode()) {
                    case D:
                        sound.stopAllSounds();
                        charakter.stopMovingRight();
                        charakter.setImage("manchen2R.png");
                        break;
                    case A:
                        charakter.stopMovingLeft();
                        charakter.setImage("manchen2L.png");
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
    }

    private void setupStartScreen() {
        startScreenPane.setPrefSize(1200, 600);
        startScreenPane.setStyle("-fx-background-color: rgba(0, 0, 0, 0.5);");
        Label pauseLabel = new Label("Game Paused");
        Image backgroundImage = new Image("file:rsc/Startbildschirm.png");
        ImageView background = new ImageView(backgroundImage);
        background.setFitWidth(1200);
        background.setFitHeight(600);
        pauseLabel.setStyle("-fx-text-fill: white; -fx-font-size: 24px;");
        pauseLabel.setLayoutX(550);
        pauseLabel.setLayoutY(300);

        startScreenPane.getChildren().add(background);
        startScreenPane.setVisible(false);
        pane.getChildren().remove(startScreenPane);
        pane.getChildren().add(startScreenPane);
    }

    private void togglePause() {
        isGamePaused = !isGamePaused;
        pane.getChildren().remove(startScreenPane);
        pane.getChildren().add(startScreenPane);
        startScreenPane.setVisible(isGamePaused);
        if (isGamePaused) {
            pauseGame();
        } else {
            resumeGame();
        }
    }

    private void pauseGame() {
        // Stop all AnimationTimers and other game logic
        charakter.getCollision().pauseMovementTimer();
        
        // Stop any other animations or timers related to Trees, Stones, and Grassblocks
        for (Tree tree : Tree.getTrees()) {
            tree.pauseAbbauTimer();
        }
        // Add logic to pause any other elements if needed
    }

    private void resumeGame() {
        // Restart all AnimationTimers and other game logic
        charakter.getCollision().resumeMovementTimer();
        
        // Resume any other animations or timers related to Trees, Stones, and Grassblocks
        for (Tree tree : Tree.getTrees()) {
            tree.resumeAbbauTimer();
        }
        // Add logic to resume any other elements if needed
    }

    public static void main(String[] args) {
        launch(args);
    }

    public static void grassPlazieren(){
        if(charakter.getImageView().getX() + 100 >= Grassblocks.getLastX() + 100) {
                    
            Grassblocks grass = new Grassblocks(grassblocksAnzahl);
            
            pane.getChildren().add(grass.getImageView());
            System.out.println("Neues Gras hinzugefügt");
            grassblocksAnzahl++;
            treePlazieren();
            steineSpawnen();

        }
        
    }
    private static void treePlazieren(){
        if (Math.random() < 0.2) {
            Tree tree = new Tree(treeIDCounter, Grassblocks.getLastX() - 60, Grassblocks.getLastY() - 345);
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
    public static void steineSpawnen(){    
        if(Stone.getAnzahlSteine() < 5){       //Steine sollen sich immer wieder spawnen
        if(Math.random() < 0.2){
            Stone stone = new Stone(Grassblocks.getLastX() + 8 , Grassblocks.getLastY() - 40);
            pane.getChildren().add(stone.getImageView());
            System.out.println("Neuer Stein hinzugefügt");
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


}
