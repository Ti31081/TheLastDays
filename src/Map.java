import javafx.animation.AnimationTimer;
import java.util.ArrayList;

public class Map {
    public static void Erstellung(Grassblocks gras){
        if(gras.getID() == 4 || gras.getID() == 5){
            gras.getImageView().setY(500);
        }
        if (gras.getID() > 8 && gras.getID() < 13) {
            gras.getImageView().setY(550);

        }
        
        if(gras.getID() == 13){
            bewegendeBlöckeDreizehn(gras);
        }

        if(gras.getID() > 15){
            randomHöhe(gras, Grassblocks.getGrassblocks());
        }


    }

    private static void bewegendeBlöckeDreizehn(Grassblocks gras){
    AnimationTimer bewegungTimer = new AnimationTimer() {
        boolean bewegung = false;
        @Override
        public void handle(long now) {
            if (!bewegung) {
            gras.getImageView().setY(gras.getImageView().getY() + 2);
            }
            else if (bewegung) {
                gras.getImageView().setY(gras.getImageView().getY() - 2);
            }
            if (gras.getImageView().getY() >= 570) {
                bewegung = true;
            }
            if (gras.getImageView().getY() <= 400) {
                bewegung = false;
            }
        }
    };
    bewegungTimer.start();
    }

    private static void randomHöhe(Grassblocks gras, ArrayList<Grassblocks> grassblocks) {
        int hoch = 1;
        if (Math.random() < 0.5) {
            hoch = 2;
            if(grassblocks.get(grassblocks.size() - 2).getImageView().getY() >= 300){ 
            hoch = 3;
            }
        }
        if (hoch == 3) {
            gras.getImageView().setY(grassblocks.get(grassblocks.size() - 2).getImageView().getY() - 100);
        }
        if (hoch == 1) {
            int randomY = (int) (Math.random() * (550 - gras.getImageView().getY()) + gras.getImageView().getY());
            gras.getImageView().setY(randomY);
        }
        if (hoch == 2) {
            gras.getImageView().setY(grassblocks.get(grassblocks.size() - 2).getImageView().getY());
        }
    }


    



}
