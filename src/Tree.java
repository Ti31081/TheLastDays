import java.util.ArrayList;

import javafx.animation.AnimationTimer;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
public class Tree {
    private static ArrayList<Tree> trees = new ArrayList<Tree>();
    private AnimationTimer abbauTimer;
    private final Image treeImage = new Image("file:rsc/BaumV2.png");
    private ImageView imageView = new ImageView();
    private int abbauClicks = 5;
    private int id;

    public Tree(int id, double xpos, double ypos) {
        this.id = id;
        this.imageView.setImage(treeImage);
        this.imageView.setFitWidth(300);
        this.imageView.setFitHeight(350);
        this.imageView.setPreserveRatio(true);
        this.imageView.setY(ypos);
        this.imageView.setX(xpos);
        trees.add(this);
        startAbbauTimer();
    }

    public ImageView getImageView() {
        return imageView;
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

    public double getX(){
        return imageView.getX();
    }

    public static void verschiebeTrees(){
        if (!trees.isEmpty()) {
                
            // Bewegt alle Baumblöcke um 5 Pixel nach links
            for(Tree tree : trees){
                tree.getImageView().setX(tree.getImageView().getX() - 5); // Verändert die X-Koordinate um 5 Pixel
            }
            if(trees.get(0).getImageView().getX() < -300){
                GUIMain.treeFromPaneRemove(trees.get(0));
                trees.remove(0);
                
                // Lösche den ersten Block, wenn er aus dem Bildschirm verfällt
            }
        }
    }


    public static ArrayList<Tree> getTrees() {
        return trees;
    }

}
