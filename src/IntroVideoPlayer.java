import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;

import java.io.File;

public class IntroVideoPlayer {

    public static void startVideo(Stage primaryStage, Runnable onVideoFinished) {
        try {
            // Erstelle Media-Objekt
            String videoPath = new File("rsc/Story Anfang.mp4").toURI().toString();
            Media media = new Media(videoPath);
            
            // Erstelle MediaPlayer
            MediaPlayer mediaPlayer = new MediaPlayer(media);
            MediaView mediaView = new MediaView(mediaPlayer);
            
            // Setze Größe
            mediaView.setFitWidth(1200);
            mediaView.setFitHeight(600);
            
            // Erstelle Layout
            Pane root = new Pane(mediaView);
            
            // Skip-Button
            Button skipButton = new Button("Überspringen");
            skipButton.setStyle("-fx-background-color: rgba(0, 0, 0, 0.6); -fx-text-fill: white; -fx-font-size: 16;");
            skipButton.setLayoutX(1100);
            skipButton.setLayoutY(550);
            
            skipButton.setOnAction(e -> {
                mediaPlayer.stop();
                onVideoFinished.run();
            });
            
            root.getChildren().add(skipButton);
            
            // Erstelle Scene
            Scene scene = new Scene(root, 1200, 600);
            primaryStage.setScene(scene);
            primaryStage.show();
            
            // Videoende-Handler
            mediaPlayer.setOnEndOfMedia(() -> {
                onVideoFinished.run();
            });
            
            // Fehler-Handler
            mediaPlayer.setOnError(() -> {
                System.err.println("Video-Fehler: " + mediaPlayer.getError());
                onVideoFinished.run();
            });
            
            // Starte Video
            mediaPlayer.play();
            
        } catch (Exception e) {
            System.err.println("Fehler beim Video-Start: " + e.getMessage());
            e.printStackTrace();
            onVideoFinished.run();
        }
    }
}
