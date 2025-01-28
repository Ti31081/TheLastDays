import java.util.ArrayList;

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

public class GUIMain extends Application {
    
    private int quantity = 3;
    private static int grassblocksAnzahl = 0;
    private static int treeIDCounter = 1;
    private static int stoneIDCounter = 1;
    private static Player charakter = new Player("Tom");
    private static Pane pane = new Pane();
    private static Pane pane2 = new Pane();
    private Pane startScreenPane = new Pane();
    private boolean isGamePaused = false;

    public void start(Stage primaryStage) {
        
        System.out.println(charakter.getImageView());

        Image backgroundImage = new Image("file:rsc/background.png");
        ImageView background = new ImageView(backgroundImage);
        background.setFitWidth(1200);
        background.setFitHeight(600);
        pane.getChildren().add(background);
        
        pane.getChildren().add(charakter.getImageView());

        for (int i = 0; i < quantity; i++) {
            Grassblocks grassblock = new Grassblocks(grassblocksAnzahl);
            grassblocksAnzahl++;
            pane.getChildren().add(grassblock.getImageView());
        }

        setupStartScreen();

        Scene scene = new Scene(pane, 1200, 600);
        scene.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ESCAPE) {
                togglePause();
            }
            if (!isGamePaused) {
                switch (event.getCode()) {
                    case D:
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
        primaryStage.setTitle("Titel Test");
        primaryStage.show();
    }

    private void setupStartScreen() {
        startScreenPane.setPrefSize(1200, 600);
        startScreenPane.setStyle("-fx-background-color: rgba(0, 0, 0, 0.5);");
        Label pauseLabel = new Label("Game Paused");
        pauseLabel.setStyle("-fx-text-fill: white; -fx-font-size: 24px;");
        pauseLabel.setLayoutX(550);
        pauseLabel.setLayoutY(300);
        startScreenPane.getChildren().add(pauseLabel);
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
        if(charakter.getImageView().getX() + 120 >= Grassblocks.getLastX() + 70) {
                    
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
            Tree tree = new Tree(treeIDCounter, Grassblocks.getLastX() - 50, Grassblocks.getLastY() - 345);
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
            Stone stone = new Stone(Grassblocks.getLastX() -10 , Grassblocks.getLastY() - 40);
            pane.getChildren().add(stone.getImageView());
            System.out.println("Neuer Stein hinzugefügt");
        }
    }
    }
    public static boolean weiterBewegen(){
        if (charakter.getImageView().getX() + 90 >= 890) {
            Grassblocks.verschiebeBlöcke();
            Tree.verschiebeTrees();
            Stone.verschiebeSteine();
            grassPlazieren();
            return true;

        }
        else{
            return false;
        }
    }


}
