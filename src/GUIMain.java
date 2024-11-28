import java.util.ArrayList;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class GUIMain extends Application {
    
    private int quantity = 3;
    private int grassblocksAnzahl = 0;

    public void start(Stage primaryStage) {
        
        

        
        

         // Abstand zwischen den Gras-Elementen
        Pane pane = new Pane();
        
        Player charakter = new Player("Tom");
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
                case D:
                //if (charakter.getImageView().getX() + 90 ) {
                    
                //}
                charakter.getImageView().setX(charakter.getImageView().getX() + 10);
                System.out.println(charakter.getImageView().getX() + 90 + "px");
                // Kollisionsprüfung hier einfügen
                

                if(charakter.getImageView().getX() + 100 >= Grassblocks.getLastX() + 70) {
                    
                    Grassblocks grass = new Grassblocks(grassblocksAnzahl);
                    
                    pane.getChildren().add(grass.getImageView());
                    System.out.println("Neues Gras hinzugefügt");
                    grassblocksAnzahl++;
                }
                break;
                case A:
                charakter.getImageView().setX(charakter.getImageView().getX() - 10);
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


}
