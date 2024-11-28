import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Player {
    private final Image grafik = new Image("file:rsc/manchen.png");
    private String name;
    private ImageView playerView = new ImageView(); 

    public Player(String name) {
        this.name = name;
        this.playerView.setImage(grafik);
        this.playerView.setFitHeight(200);
        this.playerView.setY(200);
    }

    public ImageView getImageView(){
        return playerView;
    }

    public void jumping(){
        double startY = playerView.getY();
        double jumpHeight = -110; // Negative value for upward movement
        double jumpDuration = 400; // milliseconds
        
        javafx.animation.TranslateTransition jump = new javafx.animation.TranslateTransition();
        jump.setNode(playerView);
        jump.setDuration(javafx.util.Duration.millis(jumpDuration));
        jump.setByY(jumpHeight);
        jump.setCycleCount(2);
        jump.setAutoReverse(true);
        jump.play();
    }

}
