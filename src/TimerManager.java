import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.layout.Pane;

public class TimerManager {
    private final Timeline timer;
    private int secondsElapsed;
    private final Label timerLabel;
    private final GUIMain gameInstance;
    private final Pane pane;

    public TimerManager(Stage primaryStage, Label timerLabel, GUIMain gameInstance, Pane pane) {
        this.timerLabel = timerLabel;
        this.gameInstance = gameInstance;
        this.pane = pane;
        this.secondsElapsed = 0;
        timer = new Timeline(new KeyFrame(Duration.seconds(1), event -> updateTimer()));
        timer.setCycleCount(Timeline.INDEFINITE);
    }

    public void startTimer() {
        System.out.println("Timer gestartet!");
        secondsElapsed = 0;
        updateLabel();
        timer.play();
        pane.getChildren().add(timerLabel);
        timerLabel.setLayoutX(10);
        timerLabel.setLayoutY(10);
    }

    private void updateTimer() {
        secondsElapsed++;
        updateLabel();
        if (secondsElapsed >= 20) { // Hier kannst du die Zeit einstellen die der Spieler hat, in sek (20 sek zum Testen)
            timer.stop();
            showEndScreen();
        }
    }

    private void updateLabel() {
        int minutes = secondsElapsed / 60;
        int seconds = secondsElapsed % 60;
        
        if (minutes > 0) {
            timerLabel.setText("Zeit: " + minutes + "m " + seconds + "s");
        } else {
            timerLabel.setText("Zeit: " + seconds + "s");
        }
    }

    private void showEndScreen() {
        System.out.println("Spielzeit vorbei! Zeige Endbildschirm.");
        
        VBox endScreen = new VBox();
        endScreen.setPrefSize(1200, 600);
        endScreen.setStyle("-fx-background-color: #FF0000;"); // Roter Hintergrund
        endScreen.setAlignment(javafx.geometry.Pos.CENTER);
        
        Label endLabel = new Label("GAME OVER");
        endLabel.setStyle("-fx-text-fill: white; -fx-font-size: 72px; -fx-font-weight: bold;");
        
        Button restartButton = new Button("NEUSTART");
        restartButton.setStyle("-fx-background-color: black; -fx-text-fill: white; -fx-font-size: 24px; -fx-border-color: white; -fx-border-width: 2px;");
        restartButton.setPrefWidth(200);
        restartButton.setPrefHeight(50);
        
        restartButton.setOnAction(e -> restartGame());
        
        endScreen.getChildren().addAll(endLabel, restartButton);
        pane.getChildren().clear(); // Spielfeld leeren
        pane.getChildren().add(endScreen); // Endbildschirm anzeigen
    }

    public void restartGame() {
        System.out.println("Spiel wird neu gestartet...");
        gameInstance.restartGame();
    }
    

    public void restartTimer() {
        System.out.println("Timer wird neugestartet...");
        secondsElapsed = 0;
        updateLabel();
        timer.playFromStart();
    }

    public void pauseTimer() {
        if (timer != null) timer.pause();
    }
    
    public void resumeTimer() {
        if (timer != null) timer.play();
    }
    
    
}
