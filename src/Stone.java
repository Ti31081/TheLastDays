import java.util.ArrayList;
import javafx.animation.AnimationTimer;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
public class Stone {
    private static ArrayList<Stone> stones = new ArrayList<Stone>();
    private final Image stoneImage = new Image("file:rsc/stein2.png");
    private ImageView imageView = new ImageView();
    private static int anzahlSteine = 0; 
    Grassblocks grassblocks;
    private AnimationTimer abbauTimer;
    private int abbauClicks = 5;
    private boolean removed = false;


    

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
public void entfernen() {
    this.removed = true;
    GUIMain.stoneFromPaneRemove(this); // entfernt das Bild aus dem Pane
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

public static ArrayList<Stone> getSteine() {
    return stones;
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
            this.imageView.setImage(new Image("file:rsc/stein1.png"));
            break;
        case 3:
            this.imageView.setImage(new Image("file:rsc/stein22.png"));
            break;
        case 2:
            this.imageView.setImage(new Image("file:rsc/stein3.png"));
            break;
        case 1:
            this.imageView.setImage(new Image("file:rsc/stein4.png"));
            break;
    }

    if (abbauClicks <= 0) {
        entfernen(); // ⬅️ hier wird der Stein endgültig "entfernt"
    }
}


public int getAbbauClicks(){
    return abbauClicks;
}

public void setAbbauClicks(int abbauClicks){
    this.abbauClicks = abbauClicks;
}

public double getX(){
return imageView.getX();
}

public void pauseAbbauTimer() {
    if (abbauTimer != null) abbauTimer.stop(); // ✅
}

public void resumeAbbauTimer() {
    if (abbauTimer != null) abbauTimer.start(); // ✅
}

public boolean isRemoved() {
    return removed;
}




}

