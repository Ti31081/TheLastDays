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
    private TimerManager timerManager;  // Deklaration der TimerManager-Instanz

    public void start(Stage primaryStage) {
        
        System.out.println(charakter.getImageView());

         
         Image backgroundImage = new Image("file:rsc/background.png");
         ImageView background = new ImageView(backgroundImage);
         background.setFitWidth(1200);
         background.setFitHeight(600);
         pane.getChildren().add(background);
        pane.getChildren().add(charakter.getImageView());
        
        
        Label timerLabel = new Label("Zeit: 300s");
        timerManager = new TimerManager(primaryStage, timerLabel, this, pane);
        timerManager.startTimer(); // Start the timer
 
        
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
                case D:
                
                    charakter.startMovingRight();
                    if (charakter.getImage() != "manchenMoveR.png") {
                        charakter.setImage("manchenMoveR.png");
                    }
                    
                break;
                
                case A:
                charakter.startMovingLeft();
                if (charakter.getImage() != "manchenMoveL.png") {
                    charakter.setImage("manchenMoveL.png");
                }
                break;

                case SPACE:
                charakter.jumping();
                break;
            }
            
            
        });

        scene.setOnKeyReleased(event -> {
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
        });
        
        pane.requestFocus();
        primaryStage.setScene(scene);
        primaryStage.setTitle("Titel Test");
        primaryStage.show();
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
