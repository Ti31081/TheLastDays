import java.util.ArrayList;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
public class Tree {
    private static ArrayList<Tree> trees = new ArrayList<Tree>();
    private final Image treeImage = new Image("file:rsc/BaumV2.png");
    private ImageView imageView = new ImageView();
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
    }

    public ImageView getImageView() {
        return imageView;
    }

    public static void verschiebeTrees(){
        if (!trees.isEmpty()) {
                
            // Bewegt alle Baumblöcke um 5 Pixel nach links
            for(Tree tree : trees){
                tree.getImageView().setX(tree.getImageView().getX() - 5); // Verändert die X-Koordinate um 5 Pixel
            }
            if(trees.get(0).getImageView().getX() < -300){
                trees.remove(0);
                
                // Lösche den ersten Block, wenn er aus dem Bildschirm verfällt
            }
        }
    }

    public static ArrayList<Tree> getTrees() {
        return trees;
    }

}
