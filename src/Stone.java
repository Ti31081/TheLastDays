import java.util.ArrayList;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.animation.AnimationTimer;

public class Stone {
    private static ArrayList<Stone> stones = new ArrayList<Stone>();
    private final Image stoneImage = new Image("file:rsc/stein.png");
    private ImageView imageView = new ImageView();
    private static int anzahlSteine = 0; 
    private AnimationTimer abbauTimer;
    private int abbauClicks = 5;

    Grassblocks grassblocks;

public Stone(double xpos, double ypos){
    this.imageView.setImage(stoneImage);
    this.imageView.setFitWidth(90);
    this.imageView.setFitHeight(90);
    this.imageView.setPreserveRatio(true);
    this.imageView.setY(ypos);
    this.imageView.setX(xpos);
    this.anzahlSteine++;
    startAbbauTimer();

    stones.add(this);
}
    
    private void startAbbauTimer() {
        abbauTimer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                prüfeAufAbbauClicks();
                
            }
        };
        abbauTimer.start();
    }
    private void prüfeAufAbbauClicks() {
        switch (abbauClicks) {
            case 4:
                this.imageView.setImage(new Image("file:rsc/BaumA1.png"));
            break;
            case 3:
                this.imageView.setImage(new Image("file:rsc/BaumA2.png"));
            break;
            case 2:
                this.imageView.setImage(new Image("file:rsc/BaumA3.png"));
            break;
            case 1:
                this.imageView.setImage(new Image("file:rsc/BaumA4.png"));
                
            break;
        
            default:
                break;
        }
        
    }
    
    public int getAbbauClicks(){
        return abbauClicks;
    }

    public void setAbbauClicks(int abbauClicks){
        this.abbauClicks = abbauClicks;
    }

public static void verschiebeSteine() {
    if (!stones.isEmpty()) {
        // Bewegt alle Steinblöcke um 5 Pixel nach links
        for (Stone stone : stones) {
            stone.getImageView().setX(stone.getImageView().getX() - 5);
            // Verändert die X-Koordinate um 5 Pixel
        }
        if (stones.get(0).getImageView().getX() < -300) {
            stones.remove(0);
            // Lösche den ersten Block, wenn er aus dem Bildschirm verfällt
        }
    }
}

public ImageView getImageView() {
    return imageView;
}

public static int getAnzahlSteine() {
    return anzahlSteine;
}

public static ArrayList<Stone> getStones(){
    return stones;
}

public double getX(){
    return imageView.getX();
}
}
