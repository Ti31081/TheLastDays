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
        double random = Math.random();
        if (random < 0.33) {
            hoch = 2;
            if(grassblocks.get(grassblocks.size() - 2).getImageView().getY() >= 300){ 
            hoch = 3;
            }
        }
        else if (random < 0.5) {
            hoch = 4;
        }
        switch (hoch) {
            case 3:
                gras.getImageView().setY(grassblocks.get(grassblocks.size() - 2).getImageView().getY() - 100);
                break;
            case 1:
                int randomY = (int) (Math.random() * (550 - gras.getImageView().getY()) + gras.getImageView().getY());
                gras.getImageView().setY(randomY);
                break;
            case 2:
                gras.getImageView().setY(grassblocks.get(grassblocks.size() - 2).getImageView().getY());
                break;
            case 4:
                System.out.println("bewegender block");
                hochrunterBewegendeBlöcke(gras);
                break;
        }
    }

    private static void hochrunterBewegendeBlöcke(Grassblocks gras){
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
                if (gras.getImageView().getY() <= 270) {
                    bewegung = false;
                }
            }
        };
        bewegungTimer.start();
    }


    



}
