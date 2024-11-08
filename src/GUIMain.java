
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;




public class GUIMain extends Application {
        @Override
    public void start(Stage primaryStage) {
        
       

        TextField testText = new TextField();
        testText.setPromptText("Test");

        Label testLabel = new Label();
        
        Button testButton = new Button("Test");
        


        // Set button action to change the label's text
        testButton.setOnAction(e -> {
            //hier code rein
        });

        VBox layout = new VBox(10, testButton, testLabel, testText);
        Scene scene = new Scene(layout, 400, 800);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Titel Test");
        primaryStage.show();
        

        
      
    }


    


    public static void main(String[] args) {
        launch(args);
        
        
    }
}