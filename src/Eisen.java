import java.util.ArrayList;
import javafx.animation.AnimationTimer;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
public class Eisen {
    private static ArrayList<Eisen> eisen = new ArrayList<Eisen>();
    private final Image eisenImage = new Image("file:rsc/eisenErz.png");
    private ImageView imageView = new ImageView();
    private static int anzahlEisen = 0; 
    Grassblocks grassblocks;
    private AnimationTimer abbauTimer;
    private int abbauClicks = 6;
    private boolean removed = false;


    

public Eisen (double xpos, double ypos){
    this.imageView.setImage(eisenImage);
    this.imageView.setFitWidth(90);
    this.imageView.setFitHeight(90);
    this.imageView.setPreserveRatio(true);
    this.imageView.setY(ypos);
    this.imageView.setX(xpos);
    this.anzahlEisen++;
    startAbbauTimer();
    eisen.add(this);
    

}
public void entfernen() {
    this.removed = true;
    GUIMain.eisenFromPaneRemove(this); // entfernt das Bild aus dem Pane
}


public static void verschiebeEisen() {
    if (!eisen.isEmpty()) {
        // Bewegt alle Steinblöcke um 5 Pixel nach links
        for (Eisen e : eisen) {
            e.getImageView().setX(e.getImageView().getX() - 5);
            // Verändert die X-Koordinate um 5 Pixel
        }
        if (eisen.get(0).getImageView().getX() < -300) {
            eisen.remove(0);
            // Lösche den ersten Block, wenn er aus dem Bildschirm verfällt
        }
        
    }
}

public ImageView getImageView() {
    return imageView;
}

public static int getAnzahlEisen() {
    return anzahlEisen;
}

public static ArrayList<Eisen> getEisen(){
    return eisen;
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

