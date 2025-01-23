import java.util.ArrayList;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
public class Stone {
    private static ArrayList<Stone> stone = new ArrayList<Stone>();
    private final Image stoneImage = new Image("file:rsc/stone.png");
    private ImageView imageView = new ImageView();
    private int xStein = 0;
    private int yStein = 0; 

public void Stone(){
    this.imageView.setImage(stoneImage);
    this.imageView.setFitWidth(50);
    this.imageView.setFitHeight(50);
    this.imageView.setY(yStein);
    this.imageView.setX(xStein);
    stone.add(this);

}

public void verschiebeSteine(){
    for(Stone stone : stone){
        stone.getImageView().setX(stone.getImageView().getX() +10 );
    }
}

public ImageView getImageView() {
    return imageView;


}
}

