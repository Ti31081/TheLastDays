import java.util.ArrayList;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Grassblocks {
    private static ArrayList<Grassblocks> grassblocks = new ArrayList<Grassblocks>();
    //private final Image grassImage = new Image("file:rsc/grass.png");
    private final Image grassImage = new Image("file:rsc/grass2.png");
    private ImageView imageView = new ImageView();
    private int id;

    public Grassblocks(int id) {
        this.id = id;
        this.imageView.setImage(grassImage);
        this.imageView.setFitWidth(100);
        this.imageView.setFitHeight(100);
        this.imageView.setPreserveRatio(true);
        this.imageView.setY(400);
        if (grassblocks.isEmpty()) {
            this.imageView.setX(0); // Erster Block startet bei X=0
        } else {
            this.imageView.setX(grassblocks.get(grassblocks.size() - 1).getImageView().getX() + 100);
        }
        grassblocks.add(this);
        Map.Erstellung(this);
    }

    public ImageView getImageView() {
        return imageView;
    }

    public int getID(){
        return id;
    }

    public static void verschiebeBlöcke(){
        if (!grassblocks.isEmpty()) {
            // Bewegt alle Grasblöcke um 5 Pixel nach links
            for(Grassblocks block : grassblocks){
                block.getImageView().setX(block.getImageView().getX() - 5); // Verändert die X-Koordinate um 5 Pixel
            }
            if(grassblocks.get(0).getImageView().getX() < -100){
                GUIMain.grassFromPaneRemove(grassblocks.get(0));
                grassblocks.remove(0);
                // Lösche den ersten Block, wenn er aus dem Bildschirm verfällt
            }
        }
        
    }
    

    public static double getLastX(){
        return grassblocks.get(grassblocks.size() - 1).getImageView().getX(); 
    }

    public static double getLastY(){
        return grassblocks.get(grassblocks.size() - 1).getImageView().getY(); 
    }

    public static ArrayList<Grassblocks> getGrassblocks() {
        return grassblocks;
    }
}
