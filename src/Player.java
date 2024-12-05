import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Player {
    private final Image grafik = new Image("file:rsc/manchen.png");
    private String name;
    private ImageView playerView = new ImageView(); 
    private Collision collision;

    public Player(String name) {
        this.name = name;
        this.playerView.setImage(grafik);
        this.playerView.setFitHeight(200);
        this.playerView.setY(200);
        this.playerView.setFitWidth(60);
        this.collision = new Collision(this);
    }

    public ImageView getImageView(){
        return playerView;
    }

    public void jumping(){
        collision.startJump();
    }
    
    public void stopJumping(){
        collision.stopJump();
    }


    public void startMovingRight() {
        collision.startMovingRight();
    }

    public void startMovingLeft() {
        collision.startMovingLeft();
    }

    public void stopMovingRight() {
        collision.stopMovingRight();
    }

    public void stopMovingLeft() {
        collision.stopMovingLeft();
    }

}
