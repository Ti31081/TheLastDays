import java.util.ArrayList;
import javafx.animation.AnimationTimer;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
public class Schwarzpulver {
    private static ArrayList<Schwarzpulver> schwarzp = new ArrayList<Schwarzpulver>();
    private final Image schwarzImage = new Image("file:rsc/schwarzpulver.png");
    private ImageView imageView = new ImageView();
    private static int anzahlSchwarzpulver = 0; 
    Grassblocks grassblocks;
    private AnimationTimer abbauTimer;
    private int abbauClicks = 6;
    private boolean removed = false;


    

public Schwarzpulver (double xpos, double ypos){
    this.imageView.setImage(schwarzImage);
    this.imageView.setFitWidth(90);
    this.imageView.setFitHeight(90);
    this.imageView.setPreserveRatio(true);
    this.imageView.setY(ypos);
    this.imageView.setX(xpos);
    this.anzahlSchwarzpulver++;
    startAbbauTimer();
    schwarzp.add(this);
    

}
public void entfernen() {
    this.removed = true;
    GUIMain.schwarzpulverFromPaneRemove(this); // entfernt das Bild aus dem Pane
}


public static void verschiebeSchwarzpulver() {
    if (!schwarzp.isEmpty()) {
        // Bewegt alle Steinblöcke um 5 Pixel nach links
        for (Schwarzpulver sp : schwarzp) {
            sp.getImageView().setX(sp.getImageView().getX() - 5);
            // Verändert die X-Koordinate um 5 Pixel
        }
        if (schwarzp.get(0).getImageView().getX() < -300) {
            schwarzp.remove(0);
            // Lösche den ersten Block, wenn er aus dem Bildschirm verfällt
        }
        
    }
}

public ImageView getImageView() {
    return imageView;
}

public static int getAnzahlSchwarzpulver() {
    return anzahlSchwarzpulver;
}

public static ArrayList<Schwarzpulver> getSchwarzpulver(){
    return schwarzp;
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
            this.imageView.setImage(new Image("file:rsc/s1.png"));
            break;
        case 3:
            this.imageView.setImage(new Image("file:rsc/s2.png"));
            break;
        case 2:
            this.imageView.setImage(new Image("file:rsc/s3.png"));
            break;
        case 1:
            this.imageView.setImage(new Image("file:rsc/s4.png"));
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

public double getY(){
    return imageView.getY();
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

