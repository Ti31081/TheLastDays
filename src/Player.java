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

}
